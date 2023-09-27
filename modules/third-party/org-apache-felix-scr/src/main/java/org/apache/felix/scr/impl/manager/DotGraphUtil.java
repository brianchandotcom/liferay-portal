/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package org.apache.felix.scr.impl.manager;

import java.io.File;
import java.io.IOException;

import java.nio.file.Files;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.felix.scr.impl.metadata.ComponentMetadata;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Shuyang Zhou
 */
public class DotGraphUtil {

	public static void endRegister(
		AbstractComponentManager<?> abstractComponentManager) {

		Deque<Long> componentIdDeque = _componentIdDequeThreadLocal.get();

		componentIdDeque.pop();
	}

	public static void startRegister(
		AbstractComponentManager<?> abstractComponentManager) {

		Long id = abstractComponentManager.getId();

		Deque<Long> componentIdDeque = _componentIdDequeThreadLocal.get();

		Long parentId = componentIdDeque.peek();

		if (parentId == null) {
			parentId = _ROOT_ID;
		}

		componentIdDeque.push(id);

		ComponentMetadata componentMetadata =
			abstractComponentManager.getComponentMetadata();

		_nodes.put(id, _createLabel(componentMetadata.getName()));

		_edges.add(new AbstractMap.SimpleImmutableEntry<>(parentId, id));
	}

	public static void writeDotGraph(BundleContext bundleContext)
		throws IOException {

		StringBuilder sb = new StringBuilder();

		sb.append("strict digraph SCR {\n");

		for (Map.Entry<Long, String> entry : _nodes.entrySet()) {
			sb.append("\t");
			sb.append(entry.getKey());
			sb.append("[label=\"");
			sb.append(entry.getValue());
			sb.append("\"];\n");
		}

		sb.append("\n");

		for (Map.Entry<Long, Long> entry : _edges) {
			sb.append("\t");
			sb.append(entry.getKey());
			sb.append("->");
			sb.append(entry.getValue());
			sb.append(";\n");
		}

		sb.append("}");

		String dotGraphContent = sb.toString();

		File dotFile = bundleContext.getDataFile("scr.dot");

		Files.write(dotFile.toPath(), dotGraphContent.getBytes("UTF-8"));

		System.out.println(
			"Output SCR dot file at " + dotFile.getCanonicalPath());

		File svgFile = new File(dotFile.getParent(), "scr.svg");

		String command = "dot -Tsvg " + dotFile.getCanonicalPath();

		ProcessBuilder processBuilder = new ProcessBuilder();

		processBuilder.command(command.split(" "));

		processBuilder.redirectOutput(svgFile);

		try {
			Process process = processBuilder.start();

			System.out.println("Executing " + command);

			process.waitFor();

			System.out.println(
				"Output SCR svg file at " + svgFile.getCanonicalPath());
		}
		catch (Exception exception) {
			System.out.println(
				"Unable to execute command : \"" + command + "\". Due to " +
					exception.getMessage() +
						"\nPlease install dot from https://graphviz.org/");
		}
	}

	private static String _createLabel(String name) {
		StringBuilder sb = new StringBuilder();

		sb.append(name);

		Deque<Long> componentIdDeque = _componentIdDequeThreadLocal.get();

		sb.append("::{depth=");
		sb.append(componentIdDeque.size());
		sb.append(", order=");
		sb.append(_counter.getAndIncrement());
		sb.append("}");

		return sb.toString();
	}

	private static final boolean _ENABLED = Boolean.getBoolean(
		"scr.dot.graph.enabled");

	private static final Long _ROOT_ID = -2L;

	private static final ThreadLocal<Deque<Long>> _componentIdDequeThreadLocal =
		new ThreadLocal<Deque<Long>>() {

			@Override
			protected Deque<Long> initialValue() {
				return new LinkedList<>();
			}

		};

	private static final AtomicLong _counter = new AtomicLong();
	private static final List<Map.Entry<Long, Long>> _edges = new ArrayList<>();
	private static final Map<Long, String> _nodes = new HashMap<>();

	static {
		if (_ENABLED) {
			_nodes.put(_ROOT_ID, _createLabel("ROOT"));

			Bundle bundle = FrameworkUtil.getBundle(DotGraphUtil.class);

			BundleContext bundleContext = bundle.getBundleContext();

			Dictionary<String, Object> properties = new Hashtable<>();

			properties.put("osgi.command.scope", "scr");
			properties.put("osgi.command.function", "dotGraph");

			bundleContext.registerService(
				Object.class,
				new Object() {

					public void dotGraph() throws IOException {
						writeDotGraph(bundleContext);
					}

				},
				properties);
		}
	}

}
/* @generated */
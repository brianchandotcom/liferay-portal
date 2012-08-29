/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;

import java.io.File;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Tomas Polesovsky
 */
public class ExtRegistry {

	public static final List<String> IGNORED_FILES =
		Arrays.asList(new String[] {
			"log4j.dtd", "service.xml", "sql"+File.separator
		});
	public static final List<String> SUPPORTED_MERGING_FILES =
		Arrays.asList(new String[] {
			"ext-model-hints.xml", "ext-spring.xml", "ext-hbm.xml",
			"portal-log4j-ext.xml", "content/Language-ext",
			"portal-ext.properties", "tiles-defs-ext.xml",
			"struts-config-ext.xml", "liferay-portlet-ext.xml",
			"liferay-look-and-feel-ext.xml", "liferay-layout-templates-ext.xml",
			"portlet-ext.xml", "liferay-display-ext.xml",
			"remoting-servlet-ext.xml", "web.xml"
		});

	public static Map<String, Set<String>> getConflicts(
			ServletContext servletContext)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		Set<String> files = _readExtFiles(
			servletContext, "/WEB-INF/ext-" + servletContextName + ".xml");

		Map<String, Set<String>> conflicts = new HashMap<String, Set<String>>();

		for (String curServletContextName : _extMap.keySet()) {
			ExtRegistryInfo extRegistryInfo = _extMap.get(
				curServletContextName);

			Set<String> curFiles = extRegistryInfo.getFiles();

			for (String file : files) {
				if (!curFiles.contains(file)) {
					continue;
				}

				Set<String> conflictFiles = conflicts.get(
					curServletContextName);

				if (conflictFiles == null) {
					conflictFiles = new TreeSet<String>();

					conflicts.put(curServletContextName, conflictFiles);
				}

				conflictFiles.add(file);
			}
		}

		return conflicts;
	}

	public static Set<String> getFiles(String servletContextName) {
		return Collections.unmodifiableSet(_extMap.get(servletContextName)
			.getFiles());
	}

	public static Set<String> getServletContextNames() {
		return Collections.unmodifiableSet(_extMap.keySet());
	}

	public static Set<ServletContext> getServletContexts() {
		Set<ServletContext> result = new HashSet<ServletContext>(
			_extMap.size());

		for (ExtRegistryInfo info : _extMap.values()) {
			if (info.getServletContext() != null) {
				result.add(info.getServletContext());
			}
		}

		return Collections.unmodifiableSet(result);
	}

	public static boolean isIgnoredFile(String name) {
		if (isMergedFile(name)) {
			return true;
		}

		for (String ignoredFile : IGNORED_FILES) {
			if (name.contains(ignoredFile)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isMergedFile(String name) {
		for (String mergedFile : SUPPORTED_MERGING_FILES) {
			if (name.contains(mergedFile)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isRegistered(String servletContextName) {
		if (_extMap.containsKey(servletContextName)) {
			return true;
		}
		else {
			return false;
		}
	}

	public static void registerExt(ServletContext servletContext)
		throws Exception {

		String servletContextName = servletContext.getServletContextName();

		Set<String> files = _readExtFiles(
			servletContext, "/WEB-INF/ext-" + servletContextName + ".xml");

		_extMap.put(
			servletContextName, new ExtRegistryInfo(servletContext, files));
	}

	public static void registerPortal(ServletContext servletContext)
		throws Exception {

		Set<String> resourcePaths = servletContext.getResourcePaths("/WEB-INF");

		if ((resourcePaths == null) || resourcePaths.isEmpty()) {
			return;
		}

		for (String resourcePath : resourcePaths) {
			if (resourcePath.startsWith("/WEB-INF/ext-") &&
				resourcePath.endsWith("-ext.xml")) {

				String servletContextName = resourcePath.substring(
					13, resourcePath.length() - 4);

				Set<String> files = _readExtFiles(servletContext, resourcePath);

				_extMap.put(
					servletContextName, new ExtRegistryInfo(null, files));
			}
		}
	}

	public static void updateRegisteredServletContext(
		String servletContextName, ServletContext ctx) {

		if (isRegistered(servletContextName)) {
			_extMap.get(servletContextName).setServletContext(ctx);
		}
	}

	private static Set<String> _readExtFiles(
			ServletContext servletContext, String resourcePath)
		throws Exception {

		Set<String> files = new TreeSet<String>();

		Document document = SAXReaderUtil.read(
			servletContext.getResourceAsStream(resourcePath));

		Element rootElement = document.getRootElement();

		Element filesElement = rootElement.element("files");

		List<Element> fileElements = filesElement.elements("file");

		for (Element fileElement : fileElements) {
			String fileName = fileElement.getText();
			if (!isIgnoredFile(fileName)) {
				files.add(fileName);
			}
		}

		return files;
	}

	private static Map<String, ExtRegistryInfo> _extMap =
		Collections.synchronizedMap(new HashMap<String, ExtRegistryInfo>());

	private static class ExtRegistryInfo {

		public ExtRegistryInfo(
			ServletContext servletContext, Set<String> files) {

			this.servletContext = servletContext;
			this.files = files;
		}

		public Set<String> getFiles() {
			return files;
		}

		public ServletContext getServletContext() {
			return servletContext;
		}

		public void setFiles(Set<String> files) {
			this.files = files;
		}

		public void setServletContext(ServletContext servletContext) {
			this.servletContext = servletContext;
		}

		private Set<String> files;
		private ServletContext servletContext;
	}

}
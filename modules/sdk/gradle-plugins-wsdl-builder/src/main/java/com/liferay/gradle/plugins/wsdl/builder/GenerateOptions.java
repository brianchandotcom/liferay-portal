/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.wsdl.builder;

import com.liferay.gradle.util.GradleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.gradle.api.tasks.Input;

/**
 * @author Andrea Di Giorgi
 */
public class GenerateOptions {

	@Input
	public Databinding getDatabinding() {
		return _databinding;
	}

	@Input
	public Map<?, ?> getMappings() {
		return _mappings;
	}

	@Input
	public boolean isBackwordCompatible() {
		return _backwordCompatible;
	}

	@Input
	public boolean isFlattenFiles() {
		return _flattenFiles;
	}

	@Input
	public boolean isGenerateAll() {
		return _generateAll;
	}

	@Input
	public boolean isNoWrapped() {
		return _noWrapped;
	}

	@Input
	public boolean isServerSide() {
		return _serverSide;
	}

	public boolean isVerbose() {
		return _verbose;
	}

	public GenerateOptions mapping(Object namespace, Object packageName) {
		_mappings.put(namespace, packageName);

		return this;
	}

	public GenerateOptions mappings(Map<?, ?> mappings) {
		_mappings.putAll(mappings);

		return this;
	}

	public void setBackwordCompatible(boolean backwordCompatible) {
		_backwordCompatible = backwordCompatible;
	}

	public void setDatabinding(Databinding databinding) {
		_databinding = databinding;
	}

	public void setFlattenFiles(boolean flattenFiles) {
		_flattenFiles = flattenFiles;
	}

	public void setGenerateAll(boolean generateAll) {
		_generateAll = generateAll;
	}

	public void setMappings(Map<?, ?> mappings) {
		_mappings.clear();

		mappings(mappings);
	}

	public void setNoWrapped(boolean noWrapped) {
		_noWrapped = noWrapped;
	}

	public void setServerSide(boolean serverSide) {
		_serverSide = serverSide;
	}

	public void setVerbose(boolean verbose) {
		_verbose = verbose;
	}

	public enum Databinding {

		ADB, JIBX, NONE, XMLBEANS

	}

	protected List<String> getArgs() {
		List<String> args = new ArrayList<>();

		if (isBackwordCompatible()) {
			args.add("--backword-compatible");
		}

		Databinding databinding = getDatabinding();

		if (databinding != null) {
			args.add("--databinding-method");

			String name = databinding.name();

			args.add(name.toLowerCase());
		}

		if (isFlattenFiles()) {
			args.add("--flatten-files");
		}

		if (isGenerateAll()) {
			args.add("--generate-all");
		}

		if (isNoWrapped()) {
			args.add("--noWrapped");
		}

		Map<?, ?> mappings = getMappings();

		if (!mappings.isEmpty()) {
			for (Map.Entry<?, ?> entry : mappings.entrySet()) {
				args.add("--NStoPkg");

				String namespace = GradleUtil.toString(entry.getKey());
				String packageName = GradleUtil.toString(entry.getValue());

				args.add(namespace + "=" + packageName);
			}
		}

		if (isServerSide()) {
			args.add("--server-side");
		}

		if (isVerbose()) {
			args.add("--verbose");
		}

		return args;
	}

	private boolean _backwordCompatible;
	private Databinding _databinding;
	private boolean _flattenFiles;
	private boolean _generateAll;
	private final Map<Object, Object> _mappings = new TreeMap<>();
	private boolean _noWrapped;
	private boolean _serverSide;
	private boolean _verbose;

}
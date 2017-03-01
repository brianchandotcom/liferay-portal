/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.gradle.plugins.node.tasks;

import com.liferay.gradle.util.GradleUtil;

import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gradle.api.tasks.Input;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class NpmShrinkwrapTask extends ExecuteNpmTask {

	public NpmShrinkwrapTask excludeDependencies(
		Iterable<?> excludedDependencies) {

		GUtil.addToCollection(_excludedDependencies, excludedDependencies);

		return this;
	}

	public NpmShrinkwrapTask excludeDependencies(
		Object... excludedDependencies) {

		return excludeDependencies(Arrays.asList(excludedDependencies));
	}

	@Override
	public void executeNode() throws Exception {
		super.executeNode();

		_removeExcludedDependencies();
	}

	@Input
	public List<String> getExcludedDependencies() {
		return GradleUtil.toStringList(_excludedDependencies);
	}

	@Input
	public boolean isIncludeDevDependencies() {
		return _includeDevDependencies;
	}

	public void setExcludedDependencies(Iterable<?> excludedDependencies) {
		_excludedDependencies.clear();

		excludeDependencies(excludedDependencies);
	}

	public void setExcludedDependencies(Object... excludedDependencies) {
		setExcludedDependencies(Arrays.asList(excludedDependencies));
	}

	public void setIncludeDevDependencies(boolean includeDevDepenencies) {
		_includeDevDependencies = includeDevDepenencies;
	}

	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add("shrinkwrap");

		if (isIncludeDevDependencies()) {
			completeArgs.add("--dev");
		}

		return completeArgs;
	}

	private void _removeExcludedDependencies() throws IOException {
		List<String> excludedDependencies = getExcludedDependencies();

		if (excludedDependencies.isEmpty()) {
			return;
		}

		File shrinkwrapJsonFile = new File(
			getWorkingDir(), "npm-shrinkwrap.json");

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> shrinkwrap = (Map<String, Object>)jsonSlurper.parse(
			shrinkwrapJsonFile);

		_removeExcludedDependencies(shrinkwrap, excludedDependencies);

		String shrinkwrapJSON = JsonOutput.prettyPrint(
			JsonOutput.toJson(shrinkwrap));

		shrinkwrapJSON = shrinkwrapJSON.replace(_FOUR_SPACES, "\t");

		Files.write(
			shrinkwrapJsonFile.toPath(),
			shrinkwrapJSON.getBytes(StandardCharsets.UTF_8));
	}

	private void _removeExcludedDependencies(
		Map<String, Object> map, Iterable<String> excludedDependencies) {

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			if (!(value instanceof Map<?, ?>)) {
				continue;
			}

			Map<String, Object> valueMap = (Map<String, Object>)value;

			if (key.equals("dependencies")) {
				for (String excludedDependency : excludedDependencies) {
					valueMap.remove(excludedDependency);
				}
			}

			_removeExcludedDependencies(valueMap, excludedDependencies);
		}
	}

	private static final String _FOUR_SPACES;

	static {
		char[] spaces = new char[4];

		Arrays.fill(spaces, ' ');

		_FOUR_SPACES = new String(spaces);
	}

	private final Set<Object> _excludedDependencies = new LinkedHashSet<>();
	private boolean _includeDevDependencies = true;

}
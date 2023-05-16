/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.node.task;

import com.liferay.gradle.util.GUtil;
import com.liferay.gradle.util.GradleUtil;

import groovy.json.JsonOutput;
import groovy.json.JsonSlurper;

import java.io.File;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Task;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;

/**
 * @author Andrea Di Giorgi
 */
@CacheableTask
public class NpmShrinkwrapTask extends ExecutePackageManagerTask {

	public NpmShrinkwrapTask() {
		onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					File packageJsonFile = new File(
						getWorkingDir(), "package.json");

					return packageJsonFile.exists();
				}

			});
	}

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

		File shrinkwrapJsonFile = new File(
			getWorkingDir(), "npm-shrinkwrap.json");

		JsonSlurper jsonSlurper = new JsonSlurper();

		Map<String, Object> shrinkwrap = (Map<String, Object>)jsonSlurper.parse(
			shrinkwrapJsonFile);

		List<String> excludedDependencies = getExcludedDependencies();

		if (!excludedDependencies.isEmpty()) {
			_removeExcludedDependencies(shrinkwrap, getExcludedDependencies());
		}

		String shrinkwrapJSON = JsonOutput.prettyPrint(
			JsonOutput.toJson(shrinkwrap));

		shrinkwrapJSON = shrinkwrapJSON.replace(_FOUR_SPACES, "\t");

		Files.write(
			shrinkwrapJsonFile.toPath(),
			shrinkwrapJSON.getBytes(StandardCharsets.UTF_8));
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

	@Internal
	@Override
	protected List<String> getCompleteArgs() {
		List<String> completeArgs = super.getCompleteArgs();

		completeArgs.add("shrinkwrap");

		if (isIncludeDevDependencies()) {
			completeArgs.add("--dev");
		}

		return completeArgs;
	}

	private void _removeExcludedDependencies(
		Map<String, Object> map, Iterable<String> excludedDependencies) {

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();

			if (!(value instanceof Map<?, ?>)) {
				continue;
			}

			Map<String, Object> valueMap = (Map<String, Object>)value;

			String key = entry.getKey();

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
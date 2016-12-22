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

package com.liferay.gradle.plugins.dependency.checker;

import com.liferay.gradle.plugins.dependency.checker.internal.DependencyChecker;
import com.liferay.gradle.plugins.dependency.checker.internal.DependencyKey;
import com.liferay.gradle.plugins.dependency.checker.internal.impl.MaxAgeDependencyCheckerImpl;
import com.liferay.gradle.util.GradleUtil;

import java.io.IOException;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.gradle.StartParameter;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.UncheckedIOException;
import org.gradle.api.invocation.Gradle;
import org.gradle.api.logging.Logger;
import org.gradle.util.ConfigureUtil;

/**
 * @author Andrea Di Giorgi
 */
public class DependencyCheckerExtension {

	public DependencyCheckerExtension(Project project) {
		_ignoreFailures = GradleUtil.getProperty(
			project, "dependencyCheckerIgnoreFailures", true);
		_project = project;
	}

	public void check(
		String configuration, String group, String name, String version) {

		Gradle gradle = _project.getGradle();
		Logger logger = _project.getLogger();

		StartParameter startParameter = gradle.getStartParameter();

		if (startParameter.isOffline()) {
			if (logger.isWarnEnabled()) {
				logger.warn("Build is offline, dependency check disabled");
			}

			return;
		}

		DependencyKey dependencyKey = new DependencyKey();

		dependencyKey.setConfiguration(configuration);
		dependencyKey.setGroup(group);
		dependencyKey.setName(name);

		DependencyChecker dependencyChecker = _dependencyCheckers.get(
			dependencyKey);

		if (dependencyChecker == null) {
			if (logger.isDebugEnabled()) {
				logger.debug(
					"No dependency checkers are defined for \"{}:{}:{}\" in " +
						"configuration \"{}\"",
					group, name, version, configuration);
			}

			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
				"Checking dependency \"{}:{}:{}\" in configuration \"{}\"",
				group, name, version, configuration);
		}

		try {
			dependencyChecker.check(group, name, version);
		}
		catch (Exception e) {
			if (!isIgnoreFailures()) {
				if (e instanceof IOException) {
					throw new UncheckedIOException(e);
				}

				if (e instanceof RuntimeException) {
					throw (RuntimeException)e;
				}

				throw new GradleException(e.getMessage(), e);
			}

			if (e instanceof DependencyCheckerException) {
				System.out.println(e.getMessage());
			}
			else {
				logger.error(
					"Unable to check dependency '" + group + ":" + name + ":" +
						version + "' in configuration '" + configuration + "'",
					e);
			}
		}
	}

	public boolean isIgnoreFailures() {
		return _ignoreFailures;
	}

	public void maxAge(Map<?, ?> args) {
		_add(
			new MaxAgeDependencyCheckerImpl(_project.getLogger()), args,
			"maxAge", "throwError");
	}

	public void setIgnoreFailures(boolean ignoreFailures) {
		_ignoreFailures = ignoreFailures;
	}

	private DependencyChecker _add(
		DependencyChecker dependencyChecker, Map<?, ?> args,
		String... mandatoryKeys) {

		Map<Object, Object> dependencyKeyArgs = _extractArgs(
			args, _dependencyKeyMandatoryKeys);

		DependencyKey dependencyKey = ConfigureUtil.configureByMap(
			dependencyKeyArgs, new DependencyKey(),
			_dependencyKeyMandatoryKeys);

		dependencyChecker = ConfigureUtil.configureByMap(
			args, dependencyChecker, Arrays.asList(mandatoryKeys));

		_dependencyCheckers.put(dependencyKey, dependencyChecker);

		return dependencyChecker;
	}

	private Map<Object, Object> _extractArgs(
		Map<?, ?> args, Collection<?> keys) {

		Map<Object, Object> extractedArgs = new HashMap<>();

		for (Object key : keys) {
			if (args.containsKey(key)) {
				Object value = args.remove(key);

				extractedArgs.put(key, value);
			}
		}

		return extractedArgs;
	}

	private static final Collection<String> _dependencyKeyMandatoryKeys =
		Arrays.asList("configuration", "group", "name");

	private final Map<DependencyKey, DependencyChecker> _dependencyCheckers =
		new HashMap<>();
	private boolean _ignoreFailures;
	private final Project _project;

}
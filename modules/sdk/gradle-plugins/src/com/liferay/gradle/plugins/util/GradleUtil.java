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

package com.liferay.gradle.plugins.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.ResolvableDependencies;
import org.gradle.api.artifacts.dsl.DependencyHandler;
import org.gradle.api.plugins.ExtensionContainer;

/**
 * @author Andrea Di Giorgi
 */
public class GradleUtil {

	public static Configuration addConfiguration(Project project, String name) {
		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.create(name);
	}

	public static Dependency addDependency(
		Project project, String configurationName, String group, String name,
		String version) {

		return addDependency(
			project, configurationName, group, name, version, true);
	}

	public static Dependency addDependency(
		Project project, String configurationName, String group, String name,
		String version, boolean transitive) {

		Map<String, Object> dependencyNotation = new HashMap<>();

		dependencyNotation.put("group", group);
		dependencyNotation.put("name", name);
		dependencyNotation.put("version", version);
		dependencyNotation.put("transitive", transitive);

		DependencyHandler dependencyHandler = project.getDependencies();

		return dependencyHandler.add(configurationName, dependencyNotation);
	}

	public static void executeIfEmpty(
		final Configuration configuration, final Action<Configuration> action) {

		ResolvableDependencies resolvableDependencies =
			configuration.getIncoming();

		resolvableDependencies.beforeResolve(
			new Action<ResolvableDependencies>() {

				@Override
				public void execute(
					ResolvableDependencies resolvableDependencies) {

					Set<Dependency> dependencies =
						configuration.getDependencies();

					if (dependencies.isEmpty()) {
						action.execute(configuration);
					}
				}

			});
	}

	public static Configuration getConfiguration(Project project, String name) {
		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		return configurationContainer.getByName(name);
	}

	public static <T> T getExtension(Project project, Class<T> clazz) {
		ExtensionContainer extensionContainer = project.getExtensions();

		return extensionContainer.getByType(clazz);
	}

}
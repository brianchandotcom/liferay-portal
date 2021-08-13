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

package com.liferay.gradle.plugins.target.platform.internal.util;

import java.util.List;

import org.gradle.api.Action;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.dsl.DependencyHandler;

/**
 * @author Gregory Amerson
 */
public class TargetPlatformPluginUtil {

	public static void configureTargetPlatform(
		Project project, List<String> configurationNames,
		final Configuration targetPlatformBomsConfiguration) {

		ConfigurationContainer configurationContainer =
			project.getConfigurations();

		final DependencyHandler dependencyHandler = project.getDependencies();

		configurationContainer.all(
			new Action<Configuration>() {

				@Override
				public void execute(Configuration configuration) {
					String name = configuration.getName();

					if (!configurationNames.contains(name)) {
						return;
					}

					if (name.equals("frontendCSSCommon") ||
						name.equals("originalModule") ||
						name.equals("parentThemes") ||
						name.equals("portalCommonCSS") ||
						name.equals("providedModules")) {

						configuration.setTransitive(true);
					}

					DependencySet dependencySet =
						targetPlatformBomsConfiguration.getDependencies();

					dependencySet.all(
						new Action<Dependency>() {

							@Override
							public void execute(Dependency dependency) {
								StringBuilder sb = new StringBuilder();

								sb.append(dependency.getGroup());
								sb.append(':');
								sb.append(dependency.getName());
								sb.append(':');
								sb.append(dependency.getVersion());

								Dependency platformDependency =
									dependencyHandler.platform(sb.toString());

								dependencyHandler.add(
									configuration.getName(),
									platformDependency);
							}

						});
				}

			});
	}

}
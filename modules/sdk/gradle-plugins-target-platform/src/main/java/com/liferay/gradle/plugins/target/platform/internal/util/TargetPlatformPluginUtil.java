/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
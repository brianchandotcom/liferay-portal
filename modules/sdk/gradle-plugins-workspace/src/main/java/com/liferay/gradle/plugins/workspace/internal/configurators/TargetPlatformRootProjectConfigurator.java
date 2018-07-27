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

package com.liferay.gradle.plugins.workspace.internal.configurators;

import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.target.platform.TargetPlatformIDEPlugin;
import com.liferay.gradle.plugins.target.platform.TargetPlatformPlugin;
import com.liferay.gradle.plugins.target.platform.extensions.TargetPlatformExtension;
import com.liferay.gradle.plugins.target.platform.extensions.TargetPlatformIDEExtension;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.configurators.RootProjectConfigurator;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.specs.Spec;

/**
 * @author Andrea Di Giorgi
 */
public class TargetPlatformRootProjectConfigurator implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE =
		new TargetPlatformRootProjectConfigurator();

	@Override
	public void apply(final Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		final String targetPlatformVersion =
			workspaceExtension.getTargetPlatformVersion();

		if (Validator.isNull(targetPlatformVersion)) {
			return;
		}

		Matcher matcher = _externalVersionPattern.matcher(
			targetPlatformVersion);

		String normalizedTargetPlatformVersion = null;

		if (matcher.matches()) {
			StringBuilder sb = new StringBuilder();

			sb.append(matcher.group(1));
			sb.append('.');
			sb.append(matcher.group(2));
			sb.append('.');

			String label = matcher.group(3);

			try {
				int labelNumber = Integer.parseInt(matcher.group(4));

				if (label.startsWith("GA")) {
					sb.append(labelNumber - 1);
				}
				else if (label.startsWith("sp")) {
					sb.append(labelNumber);
				}
			}
			catch (NumberFormatException nfe) {
				throw new GradleException(
					"Invalid version property value", nfe);
			}

			normalizedTargetPlatformVersion = sb.toString();
		}
		else {
			normalizedTargetPlatformVersion = targetPlatformVersion;
		}

		GradleUtil.applyPlugin(project, TargetPlatformIDEPlugin.class);

		_configureConfigurationBundles(project);
		_configureTargetPlatform(project);
		_configureTargetPlatformIDE(project);

		_addDependenciesTargetPlatformBoms(
			project, normalizedTargetPlatformVersion);
		_addDependenciesTargetPlatformDistro(
			project, normalizedTargetPlatformVersion);
	}

	private TargetPlatformRootProjectConfigurator() {
	}

	private void _addDependenciesTargetPlatformBoms(
		Project project, String targetPlatformVersion) {

		Matcher dxpVersion = _dxpVersionPattern.matcher(targetPlatformVersion);

		final String artifactId;

		if (dxpVersion.matches()) {
			artifactId = _ARTIFACT_ID_RELEASE_DXP_BOM;
		}
		else {
			artifactId = _ARTIFACT_ID_RELEASE_PORTAL_BOM;
		}

		GradleUtil.addDependency(
			project,
			TargetPlatformPlugin.TARGET_PLATFORM_BOMS_CONFIGURATION_NAME,
			_GROUP_ID_PORTAL, artifactId, targetPlatformVersion);
		GradleUtil.addDependency(
			project,
			TargetPlatformPlugin.TARGET_PLATFORM_BOMS_CONFIGURATION_NAME,
			_GROUP_ID_PORTAL, artifactId, targetPlatformVersion);
	}

	private void _addDependenciesTargetPlatformDistro(
		final Project project, final String targetPlatformVersion) {

		Configuration configuration = GradleUtil.getConfiguration(
			project,
			TargetPlatformPlugin.TARGET_PLATFORM_DISTRO_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					Matcher dxpMatcher = _dxpVersionPattern.matcher(
						targetPlatformVersion);

					final String artifactId;

					if (dxpMatcher.matches()) {
						artifactId = _ARTIFACT_ID_RELEASE_DXP_DISTRO;
					}
					else {
						artifactId = _ARTIFACT_ID_RELEASE_PORTAL_DISTRO;
					}

					GradleUtil.addDependency(
						project,
						TargetPlatformPlugin.
							TARGET_PLATFORM_DISTRO_CONFIGURATION_NAME,
						_GROUP_ID_PORTAL, artifactId, targetPlatformVersion);
				}

			});
	}

	private void _configureConfigurationBundles(Project project) {
		Configuration configuration = GradleUtil.getConfiguration(
			project,
			TargetPlatformPlugin.TARGET_PLATFORM_BUNDLES_CONFIGURATION_NAME);

		Configuration providedModulesConfiguration =
			GradleUtil.getConfiguration(
				project,
				RootProjectConfigurator.PROVIDED_MODULES_CONFIGURATION_NAME);

		configuration.extendsFrom(providedModulesConfiguration);
	}

	private void _configureTargetPlatform(Project project) {
		TargetPlatformExtension targetPlatformExtension =
			GradleUtil.getExtension(project, TargetPlatformExtension.class);

		targetPlatformExtension.resolveOnlyIf(
			new Spec<Project>() {

				@Override
				public boolean isSatisfiedBy(Project project) {
					String projectName = project.getName();

					if (projectName.endsWith("-test")) {
						return false;
					}

					return true;
				}

			});

		targetPlatformExtension.resolveOnlyIf(
			new Spec<Project>() {

				@Override
				public boolean isSatisfiedBy(Project project) {
					PluginContainer pluginContainer = project.getPlugins();

					if (pluginContainer.hasPlugin(LiferayOSGiPlugin.class)) {
						return true;
					}

					return false;
				}

			});
	}

	private void _configureTargetPlatformIDE(Project project) {
		TargetPlatformIDEExtension targetPlatformIDEExtension =
			GradleUtil.getExtension(project, TargetPlatformIDEExtension.class);

		targetPlatformIDEExtension.includeGroups(
			"com.liferay", "com.liferay.portal");
	}

	private static final String _ARTIFACT_ID_RELEASE_DXP_BOM =
		"release.dxp.bom";

	private static final String _ARTIFACT_ID_RELEASE_DXP_DISTRO =
		"release.dxp.distro";

	private static final String _ARTIFACT_ID_RELEASE_PORTAL_BOM =
		"release.portal.bom";

	private static final String _ARTIFACT_ID_RELEASE_PORTAL_DISTRO =
		"release.portal.distro";

	private static final String _GROUP_ID_PORTAL = "com.liferay.portal";

	private static final Pattern _dxpVersionPattern = Pattern.compile(
		"7\\.[0-2]\\.1[0-9](\\.[0-9]+)?");

	private final Pattern _externalVersionPattern = Pattern.compile(
		"([0-9]+)\\.([0-9]+)-([A-Za-z]+)([0-9]+)");

}
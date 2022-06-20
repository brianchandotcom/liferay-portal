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
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.VersionUtil;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.plugins.ExtensionAware;
import org.gradle.api.plugins.PluginContainer;
import org.gradle.api.specs.Spec;

/**
 * @author Andrea Di Giorgi
 * @author Raymond Augé
 */
public class TargetPlatformRootProjectConfigurator implements Plugin<Project> {

	public static final Plugin<Project> INSTANCE =
		new TargetPlatformRootProjectConfigurator();

	@Override
	public void apply(Project project) {
		WorkspaceExtension workspaceExtension = GradleUtil.getExtension(
			(ExtensionAware)project.getGradle(), WorkspaceExtension.class);

		String targetPlatformVersion =
			workspaceExtension.getTargetPlatformVersion();

		if (Validator.isNull(targetPlatformVersion)) {
			return;
		}

		GradleUtil.applyPlugin(project, TargetPlatformIDEPlugin.class);

		_configureTargetPlatform(project);

		String normalizedTargetPlatformVersion =
			VersionUtil.normalizeTargetPlatformVersion(targetPlatformVersion);

		_addDependenciesTargetPlatformBoms(
			project, normalizedTargetPlatformVersion);
		_addDependenciesTargetPlatformDistro(
			project, normalizedTargetPlatformVersion);
	}

	private TargetPlatformRootProjectConfigurator() {
	}

	private void _addDependenciesTargetPlatformBoms(
		Project project, String targetPlatformVersion) {

		String bomArtifactId = null;
		String bomCompileOnlyArtifactId = null;
		String bomThirdPartyArtifactId = null;

		if (VersionUtil.isDXPVersion(targetPlatformVersion)) {
			bomArtifactId = _ARTIFACT_ID_RELEASE_DXP_BOM;
			bomCompileOnlyArtifactId =
				_ARTIFACT_ID_RELEASE_DXP_BOM_COMPILE_ONLY;
			bomThirdPartyArtifactId = _ARTIFACT_ID_RELEASE_DXP_BOM_THIRD_PARTY;
		}
		else {
			bomArtifactId = _ARTIFACT_ID_RELEASE_PORTAL_BOM;
			bomCompileOnlyArtifactId =
				_ARTIFACT_ID_RELEASE_PORTAL_BOM_COMPILE_ONLY;
			bomThirdPartyArtifactId =
				_ARTIFACT_ID_RELEASE_PORTAL_BOM_THIRD_PARTY;
		}

		GradleUtil.addDependency(
			project,
			TargetPlatformPlugin.TARGET_PLATFORM_BOMS_CONFIGURATION_NAME,
			_GROUP_ID_LIFERAY_PORTAL, bomThirdPartyArtifactId,
			targetPlatformVersion);

		GradleUtil.addDependency(
			project,
			TargetPlatformPlugin.TARGET_PLATFORM_BOMS_CONFIGURATION_NAME,
			_GROUP_ID_LIFERAY_PORTAL, bomArtifactId, targetPlatformVersion);

		GradleUtil.addDependency(
			project,
			TargetPlatformPlugin.TARGET_PLATFORM_BOMS_CONFIGURATION_NAME,
			_GROUP_ID_LIFERAY_PORTAL, bomCompileOnlyArtifactId,
			targetPlatformVersion);
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
					String artifactId = _ARTIFACT_ID_RELEASE_PORTAL_DISTRO;

					if (VersionUtil.isDXPVersion(targetPlatformVersion)) {
						artifactId = _ARTIFACT_ID_RELEASE_DXP_DISTRO;
					}

					GradleUtil.addDependency(
						project,
						TargetPlatformPlugin.
							TARGET_PLATFORM_DISTRO_CONFIGURATION_NAME,
						_GROUP_ID_LIFERAY_PORTAL, artifactId,
						targetPlatformVersion);
				}

			});
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

	private static final String _ARTIFACT_ID_RELEASE_DXP_BOM =
		"release.dxp.bom";

	private static final String _ARTIFACT_ID_RELEASE_DXP_BOM_COMPILE_ONLY =
		"release.dxp.bom.compile.only";

	private static final String _ARTIFACT_ID_RELEASE_DXP_BOM_THIRD_PARTY =
		"release.dxp.bom.third.party";

	private static final String _ARTIFACT_ID_RELEASE_DXP_DISTRO =
		"release.dxp.distro";

	private static final String _ARTIFACT_ID_RELEASE_PORTAL_BOM =
		"release.portal.bom";

	private static final String _ARTIFACT_ID_RELEASE_PORTAL_BOM_COMPILE_ONLY =
		"release.portal.bom.compile.only";

	private static final String _ARTIFACT_ID_RELEASE_PORTAL_BOM_THIRD_PARTY =
		"release.portal.bom.third.party";

	private static final String _ARTIFACT_ID_RELEASE_PORTAL_DISTRO =
		"release.portal.distro";

	private static final String _GROUP_ID_LIFERAY_PORTAL = "com.liferay.portal";

}
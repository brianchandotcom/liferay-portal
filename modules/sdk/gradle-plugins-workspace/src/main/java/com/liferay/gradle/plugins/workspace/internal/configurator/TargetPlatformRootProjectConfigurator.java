/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.configurator;

import com.liferay.gradle.plugins.LiferayOSGiPlugin;
import com.liferay.gradle.plugins.target.platform.TargetPlatformIDEPlugin;
import com.liferay.gradle.plugins.target.platform.TargetPlatformPlugin;
import com.liferay.gradle.plugins.target.platform.extensions.TargetPlatformExtension;
import com.liferay.gradle.plugins.workspace.WorkspaceExtension;
import com.liferay.gradle.plugins.workspace.WorkspacePlugin;
import com.liferay.gradle.plugins.workspace.internal.util.GradleUtil;
import com.liferay.gradle.plugins.workspace.internal.util.StringUtil;
import com.liferay.gradle.plugins.workspace.internal.util.VersionUtil;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.logging.Logger;
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

		String product = "portal";

		if (VersionUtil.isDXPVersion(normalizedTargetPlatformVersion)) {
			product = "dxp";
		}

		_addDependenciesTargetPlatformBoms(
			project, product, normalizedTargetPlatformVersion);
		_addDependenciesTargetPlatformDistro(
			project, product, normalizedTargetPlatformVersion);
	}

	private TargetPlatformRootProjectConfigurator() {
	}

	private void _addDependenciesTargetPlatformBoms(
		Project project, String productName, String version) {

		_applyToTargetPlatform(project, productName, "bom", version);
		_applyToTargetPlatform(
			project, productName, "bom.compile.only", version);

		if (GradleUtil.toBoolean(
				GradleUtil.getProperty(
					project,
					WorkspacePlugin.PROPERTY_PREFIX + "use.test.boms"))) {

			_applyToTargetPlatform(project, productName, "bom.test", version);
		}

		_applyToTargetPlatform(
			project, productName, "bom.third.party", version);
	}

	private void _addDependenciesTargetPlatformDistro(
		final Project project, final String productName, final String version) {

		Configuration configuration = GradleUtil.getConfiguration(
			project,
			TargetPlatformPlugin.TARGET_PLATFORM_DISTRO_CONFIGURATION_NAME);

		configuration.defaultDependencies(
			new Action<DependencySet>() {

				@Override
				public void execute(DependencySet dependencySet) {
					GradleUtil.addDependency(
						project,
						TargetPlatformPlugin.
							TARGET_PLATFORM_DISTRO_CONFIGURATION_NAME,
						_GROUP_ID_LIFERAY_PORTAL,
						StringUtil.concat("release.", productName, ".distro"),
						version);
				}

			});
	}

	private void _applyToTargetPlatform(
		Project project, String product, String name, String version) {

		String artifactName = StringUtil.concat("release.", product, ".", name);

		Logger logger = project.getLogger();

		if (logger.isInfoEnabled()) {
			logger.info(
				StringUtil.concat(
					"Applying artifact ", artifactName, " version ", version,
					" to target platform for project ", project.getPath()));
		}

		GradleUtil.addDependency(
			project,
			TargetPlatformPlugin.TARGET_PLATFORM_BOMS_CONFIGURATION_NAME,
			_GROUP_ID_LIFERAY_PORTAL, artifactName, version);
	}

	private void _configureTargetPlatform(Project project) {
		TargetPlatformExtension targetPlatformExtension =
			GradleUtil.getExtension(project, TargetPlatformExtension.class);

		targetPlatformExtension.resolveOnlyIf(
			new Spec<Project>() {

				@Override
				public boolean isSatisfiedBy(Project project) {
					PluginContainer pluginContainer = project.getPlugins();

					return pluginContainer.hasPlugin(LiferayOSGiPlugin.class);
				}

			});
	}

	private static final String _GROUP_ID_LIFERAY_PORTAL = "com.liferay.portal";

}
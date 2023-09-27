/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins;

import com.liferay.gradle.plugins.css.builder.BuildCSSTask;
import com.liferay.gradle.plugins.css.builder.CSSBuilderPlugin;
import com.liferay.gradle.plugins.internal.util.GradleUtil;
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.util.Validator;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Andrea Di Giorgi
 */
public class CSSBuilderDefaultsPlugin
	extends BaseDefaultsPlugin<CSSBuilderPlugin> {

	public static final Plugin<Project> INSTANCE =
		new CSSBuilderDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, CSSBuilderPlugin cssBuilderPlugin) {

		// Dependencies

		PortalTools.addPortalToolDependencies(
			project, CSSBuilderPlugin.CSS_BUILDER_CONFIGURATION_NAME,
			PortalTools.GROUP, _PORTAL_TOOL_NAME);

		PortalTools.addPortalToolDependencies(
			project, CSSBuilderPlugin.PORTAL_COMMON_CSS_CONFIGURATION_NAME,
			PortalTools.GROUP, _FRONTEND_COMMON_CSS_NAME);

		// Containers

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BuildCSSTask.class,
			new Action<BuildCSSTask>() {

				@Override
				public void execute(BuildCSSTask buildCSSTask) {
					_configureTaskBuildCSS(buildCSSTask);
				}

			});
	}

	@Override
	protected Class<CSSBuilderPlugin> getPluginClass() {
		return CSSBuilderPlugin.class;
	}

	private CSSBuilderDefaultsPlugin() {
	}

	private void _configureTaskBuildCSS(BuildCSSTask buildCSSTask) {
		Project project = buildCSSTask.getProject();

		File docrootDir = project.file("docroot");

		if (docrootDir.exists()) {
			buildCSSTask.setBaseDir(docrootDir);
		}

		String generateSourceMap = GradleUtil.getProperty(
			project, "sass.generate.source.map", (String)null);

		if (Validator.isNotNull(generateSourceMap)) {
			buildCSSTask.setGenerateSourceMap(
				Boolean.parseBoolean(generateSourceMap));
		}

		String precision = GradleUtil.getProperty(
			project, "sass.precision", (String)null);

		if (Validator.isNotNull(precision)) {
			buildCSSTask.setPrecision(precision);
		}

		buildCSSTask.setSassCompilerClassName(
			GradleUtil.getProperty(
				project, "sass.compiler.class.name", (String)null));
	}

	private static final String _FRONTEND_COMMON_CSS_NAME =
		"com.liferay.frontend.css.common";

	private static final String _PORTAL_TOOL_NAME = "com.liferay.css.builder";

}
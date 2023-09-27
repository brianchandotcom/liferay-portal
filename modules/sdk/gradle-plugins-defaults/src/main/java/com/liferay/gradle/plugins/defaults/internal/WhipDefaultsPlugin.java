/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.internal;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.util.PortalTools;
import com.liferay.gradle.plugins.whip.WhipExtension;
import com.liferay.gradle.plugins.whip.WhipPlugin;
import com.liferay.gradle.plugins.whip.WhipTaskExtension;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;
import org.gradle.api.tasks.testing.Test;

/**
 * @author Andrea Di Giorgi
 */
public class WhipDefaultsPlugin extends BaseDefaultsPlugin<WhipPlugin> {

	public static final Plugin<Project> INSTANCE = new WhipDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(Project project, WhipPlugin whipPlugin) {

		// Extensions

		WhipExtension whipExtension = GradleUtil.getExtension(
			project, WhipExtension.class);

		_configureExtensionWhip(project, whipExtension);

		// Other

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			Test.class,
			new Action<Test>() {

				@Override
				public void execute(Test test) {
					_configureTaskTest(test);
				}

			});
	}

	@Override
	protected Class<WhipPlugin> getPluginClass() {
		return WhipPlugin.class;
	}

	private WhipDefaultsPlugin() {
	}

	private void _configureExtensionWhip(
		Project project, WhipExtension whipExtension) {

		String version = PortalTools.getVersion(project, _PORTAL_TOOL_NAME);

		if (Validator.isNotNull(version)) {
			whipExtension.setVersion(version);
		}
	}

	private void _configureTaskTest(Test test) {
		WhipTaskExtension whipTaskExtension = GradleUtil.getExtension(
			test, WhipTaskExtension.class);

		whipTaskExtension.excludes(
			".*Test", ".*Test\\$.*", ".*\\$Proxy.*", "com/liferay/whip/.*");
		whipTaskExtension.includes("com/liferay/.*");
	}

	private static final String _PORTAL_TOOL_NAME = "com.liferay.whip";

}
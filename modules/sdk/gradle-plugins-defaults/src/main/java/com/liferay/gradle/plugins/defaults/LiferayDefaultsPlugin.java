/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults;

import com.liferay.gradle.plugins.LiferayPlugin;
import com.liferay.gradle.plugins.defaults.internal.JavaDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.LicenseReportDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayBaseDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayCIPatcherPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayCIPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayProfileDXPPlugin;
import com.liferay.gradle.plugins.defaults.internal.LiferayRelengPlugin;
import com.liferay.gradle.plugins.defaults.internal.MavenPublishDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.NodeDefaultsPlugin;
import com.liferay.gradle.plugins.defaults.internal.util.CIUtil;
import com.liferay.gradle.plugins.defaults.internal.util.LiferayRelengUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class LiferayDefaultsPlugin extends LiferayPlugin {

	@Override
	public void apply(Project project) {
		super.apply(project);

		if (Boolean.getBoolean("license.report.enabled")) {
			LicenseReportDefaultsPlugin.INSTANCE.apply(project);
		}

		JavaDefaultsPlugin.INSTANCE.apply(project);
		LiferayBaseDefaultsPlugin.INSTANCE.apply(project);
		MavenPublishDefaultsPlugin.INSTANCE.apply(project);
		NodeDefaultsPlugin.INSTANCE.apply(project);

		String buildProfile = System.getProperty("build.profile");

		if (Validator.isNull(buildProfile)) {
			File relengDir = LiferayRelengUtil.getRelengDir(project);

			if (relengDir != null) {
				LiferayRelengPlugin.INSTANCE.apply(project);
			}
		}

		String projectPath = project.getPath();

		if (projectPath.startsWith(":dxp:") &&
			!CIUtil.isRunningInCIEnvironment()) {

			LiferayProfileDXPPlugin.INSTANCE.apply(project);
		}

		if (CIUtil.isRunningInCIEnvironment()) {
			LiferayCIPlugin.INSTANCE.apply(project);
		}

		if (CIUtil.isRunningInCIPatcherEnvironment()) {
			LiferayCIPatcherPlugin.INSTANCE.apply(project);
		}
	}

	@Override
	protected Class<? extends Plugin<Project>> getAntPluginClass() {
		return LiferayAntDefaultsPlugin.class;
	}

	@Override
	protected Class<? extends Plugin<Project>> getOSGiPluginClass() {
		return LiferayOSGiDefaultsPlugin.class;
	}

	@Override
	protected Class<? extends Plugin<Project>> getThemePluginClass() {
		return LiferayThemeDefaultsPlugin.class;
	}

}
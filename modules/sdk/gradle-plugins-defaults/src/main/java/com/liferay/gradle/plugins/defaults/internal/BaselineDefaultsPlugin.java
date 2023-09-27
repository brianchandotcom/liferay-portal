/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.defaults.internal;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.baseline.BaselinePlugin;
import com.liferay.gradle.plugins.baseline.BaselineTask;
import com.liferay.gradle.plugins.extensions.BundleExtension;
import com.liferay.gradle.plugins.util.BndUtil;
import com.liferay.gradle.util.Validator;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.specs.Spec;
import org.gradle.api.tasks.TaskContainer;

/**
 * @author Peter Shin
 */
public class BaselineDefaultsPlugin extends BaseDefaultsPlugin<BaselinePlugin> {

	public static final Plugin<Project> INSTANCE = new BaselineDefaultsPlugin();

	@Override
	protected void applyPluginDefaults(
		Project project, BaselinePlugin baselinePlugin) {

		BundleExtension bundleExtension = BndUtil.getBundleExtension(
			project.getExtensions());

		_configureTasksBaseline(project, bundleExtension);
	}

	@Override
	protected Class<BaselinePlugin> getPluginClass() {
		return BaselinePlugin.class;
	}

	private BaselineDefaultsPlugin() {
	}

	private void _configureTaskBaseline(
		final BundleExtension bundleExtension, BaselineTask baselineTask) {

		baselineTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					String exportPackage = bundleExtension.getInstruction(
						Constants.EXPORT_PACKAGE);

					if (Validator.isNull(exportPackage)) {
						return false;
					}

					return true;
				}

			});
	}

	private void _configureTasksBaseline(
		Project project, final BundleExtension bundleExtension) {

		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BaselineTask.class,
			new Action<BaselineTask>() {

				@Override
				public void execute(BaselineTask baselineTask) {
					_configureTaskBaseline(bundleExtension, baselineTask);
				}

			});
	}

}
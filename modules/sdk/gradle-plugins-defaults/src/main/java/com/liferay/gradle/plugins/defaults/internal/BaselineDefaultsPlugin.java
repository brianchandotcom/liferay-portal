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

package com.liferay.gradle.plugins.defaults.internal;

import aQute.bnd.osgi.Constants;

import com.liferay.gradle.plugins.BaseDefaultsPlugin;
import com.liferay.gradle.plugins.baseline.BaselinePlugin;
import com.liferay.gradle.plugins.baseline.BaselineTask;
import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.plugins.util.BndUtil;
import com.liferay.gradle.util.Validator;

import java.util.Map;

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

		_configureTasksBaseline(project);
	}

	@Override
	protected Class<BaselinePlugin> getPluginClass() {
		return BaselinePlugin.class;
	}

	private BaselineDefaultsPlugin() {
	}

	private void _configureTaskBaseline(BaselineTask baselineTask) {
		baselineTask.onlyIf(
			new Spec<Task>() {

				@Override
				public boolean isSatisfiedBy(Task task) {
					Map<String, Object> bundleInstructions =
						BndUtil.getInstructions(task.getProject());

					String exportPackage = GradleUtil.toString(
						bundleInstructions.get(Constants.EXPORT_PACKAGE));

					if (Validator.isNull(exportPackage)) {
						return false;
					}

					return true;
				}

			});
	}

	private void _configureTasksBaseline(Project project) {
		TaskContainer taskContainer = project.getTasks();

		taskContainer.withType(
			BaselineTask.class,
			new Action<BaselineTask>() {

				@Override
				public void execute(BaselineTask baselineTask) {
					_configureTaskBaseline(baselineTask);
				}

			});
	}

}
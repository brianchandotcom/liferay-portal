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

package com.liferay.gradle.plugins.cache.task;

import com.liferay.gradle.plugins.cache.CacheExtension;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;

import java.io.File;

import java.util.Set;

import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.Copy;

/**
 * @author Andrea Di Giorgi
 */
public class TaskCacheApplicator {

	public void apply(CacheExtension cacheExtension, TaskCache taskCache) {
		Task task = taskCache.getTask();

		boolean upToDate = false;

		if (cacheExtension.isForcedCache()) {
			File cacheDir = taskCache.getCacheDir();

			if (!cacheDir.exists()) {
				throw new GradleException("Unable to find " + cacheDir);
			}

			upToDate = true;
		}
		else {
			upToDate = taskCache.isUpToDate();
		}

		if (upToDate) {
			applyUpToDate(taskCache, task);
		}
		else {
			applyOutOfDate(taskCache, task);
		}
	}

	protected void applyOutOfDate(final TaskCache taskCache, Task task) {
		if (_logger.isInfoEnabled()) {
			_logger.info(task + " is out-of-date");
		}

		if (taskCache.isFailIfOutOfDate()) {
			throw new GradleException(
				"Unable to build " + taskCache.getProject() + ": " + task +
					" is out-of-date and failIsOutOfDate is true");
		}

		Copy copy = createSaveCacheTask(taskCache);

		task.finalizedBy(copy);
	}

	protected void applyUpToDate(final TaskCache taskCache, Task task) {
		if (_logger.isInfoEnabled()) {
			_logger.info(task + " is up-to-date");
		}

		removeSkippedTaskDependencies(taskCache, task);

		Copy copy = createRestoreCacheTask(taskCache);

		task.dependsOn(copy);

		task.setEnabled(false);
	}

	protected Copy createRestoreCacheTask(TaskCache taskCache) {
		Project project = taskCache.getProject();

		String taskName =
			"restore" + StringUtil.capitalize(taskCache.getName()) + "Cache";

		Copy copy = GradleUtil.addTask(project, taskName, Copy.class);

		copy.from(taskCache.getCacheDir());
		copy.into(taskCache.getBaseDir());

		return copy;
	}

	protected Copy createSaveCacheTask(TaskCache taskCache) {
		String taskName =
			"save" + StringUtil.capitalize(taskCache.getName()) + "Cache";

		Copy copy = GradleUtil.addTask(
			taskCache.getProject(), taskName, Copy.class);

		copy.dependsOn(
			BasePlugin.CLEAN_TASK_NAME + StringUtil.capitalize(taskName));
		copy.from(taskCache.getFiles());
		copy.into(taskCache.getCacheDir());

		return copy;
	}

	protected void removeSkippedTaskDependencies(
		TaskCache taskCache, Task task) {

		Set<Object> taskDependencies = task.getDependsOn();

		Set<Task> skippedTaskDependencies =
			taskCache.getSkippedTaskDependencies();

		if (skippedTaskDependencies.isEmpty()) {
			taskDependencies.clear();

			if (_logger.isInfoEnabled()) {
				_logger.info("Removed all dependencies from " + task);
			}
		}
		else {
			for (Task taskDependency : skippedTaskDependencies) {
				boolean removed = taskDependencies.remove(taskDependency);

				if (!removed) {
					removed = taskDependencies.remove(taskDependency.getName());
				}

				if (removed) {
					if (_logger.isInfoEnabled()) {
						_logger.info(
							"Removed dependency " + taskDependency + " from " +
								task);
					}
				}
				else if (_logger.isWarnEnabled()) {
					_logger.warn(
						"Unable to remove skipped task dependency " +
							taskDependency.getPath());
				}
			}
		}
	}

	private static final Logger _logger = Logging.getLogger(
		TaskCacheApplicator.class);

}
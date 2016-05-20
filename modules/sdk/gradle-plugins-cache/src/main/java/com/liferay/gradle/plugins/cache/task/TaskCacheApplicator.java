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
import com.liferay.gradle.plugins.cache.util.FileUtil;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.StringUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.Copy;
import org.gradle.util.Clock;

/**
 * @author Andrea Di Giorgi
 */
public class TaskCacheApplicator {

	public void apply(CacheExtension cacheExtension, TaskCache taskCache) {
		Task task = taskCache.getTask();

		boolean upToDate = false;

		String currentDigest = getCurrentDigest(taskCache);

		if (_logger.isInfoEnabled()) {
			_logger.info("Current digest is " + currentDigest);
		}

		if (cacheExtension.isForcedCache()) {
			File cacheDir = taskCache.getCacheDir();

			if (!cacheDir.exists()) {
				throw new GradleException("Unable to find " + cacheDir);
			}

			upToDate = true;
		}
		else if (taskCache.isDisabled()) {
			if (_logger.isLifecycleEnabled()) {
				_logger.lifecycle("Cache for " + task + " is disabled");
			}
		}
		else {
			String cachedDigest = getCachedDigest(taskCache);

			if (_logger.isInfoEnabled()) {
				if (Validator.isNull(cachedDigest)) {
					_logger.info("No cached digest has been found");
				}
				else {
					_logger.info("Cached digest is " + cachedDigest);
				}
			}

			if (cachedDigest.equals(currentDigest)) {
				upToDate = true;
			}
		}

		if (upToDate) {
			applyUpToDate(taskCache, task);
		}
		else {
			applyOutOfDate(taskCache, task, currentDigest);
		}
	}

	protected void applyOutOfDate(
		final TaskCache taskCache, Task task, final String currentDigest) {

		if (_logger.isInfoEnabled()) {
			_logger.info(task + " is out-of-date");
		}

		Copy copy = createSaveCacheTask(taskCache);

		copy.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					File digestFile = new File(
						taskCache.getCacheDir(), _DIGEST_FILE_NAME);

					try {
						Files.write(
							digestFile.toPath(),
							currentDigest.getBytes(StandardCharsets.UTF_8));

						if (_logger.isInfoEnabled()) {
							_logger.info(
								"Updated digest file to " + currentDigest);
						}
					}
					catch (IOException ioe) {
						throw new GradleException(
							"Unable to write digest file", ioe);
					}
				}

			});

		task.finalizedBy(copy);
	}

	protected void applyUpToDate(TaskCache taskCache, Task task) {
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

		copy.exclude(_DIGEST_FILE_NAME);
		copy.from(taskCache.getCacheDir());
		copy.into(taskCache.getBaseDir());
		copy.setDescription(
			"Restores the cached output files of " + taskCache.getTask() + ".");

		return copy;
	}

	protected Copy createSaveCacheTask(TaskCache taskCache) {
		String taskName =
			"save" + StringUtil.capitalize(taskCache.getName()) + "Cache";

		Copy copy = GradleUtil.addTask(
			taskCache.getProject(), taskName, Copy.class);

		Task task = taskCache.getTask();

		copy.dependsOn(
			task, BasePlugin.CLEAN_TASK_NAME + StringUtil.capitalize(taskName));
		copy.from(taskCache.getFiles());
		copy.into(taskCache.getCacheDir());
		copy.setDescription("Caches the output files of " + task + ".");

		return copy;
	}

	protected String getCachedDigest(TaskCache taskCache) {
		try {
			File digestFile = new File(
				taskCache.getCacheDir(), _DIGEST_FILE_NAME);

			if (!digestFile.exists()) {
				return "";
			}

			return new String(
				Files.readAllBytes(digestFile.toPath()),
				StandardCharsets.UTF_8);
		}
		catch (IOException ioe) {
			throw new GradleException("Unable to read digest file", ioe);
		}
	}

	protected String getCurrentDigest(TaskCache taskCache) {
		Clock clock = null;

		if (_logger.isInfoEnabled()) {
			clock = new Clock();
		}

		StringBuilder sb = new StringBuilder();

		Set<File> testFiles = null;

		try {
			testFiles = FileUtil.flattenAndSort(
				taskCache.getTestFiles(), taskCache.getBaseDir());
		}
		catch (IOException ioe) {
			throw new GradleException("Unable to flatten test files", ioe);
		}

		for (File testFile : testFiles) {
			if (!testFile.exists()) {
				continue;
			}

			if (".DS_Store".equals(testFile.getName())) {
				continue;
			}

			sb.append(FileUtil.getDigest(testFile));
			sb.append(_DIGEST_SEPARATOR);
		}

		if (sb.length() == 0) {
			throw new GradleException("At least one test file is required");
		}

		sb.setLength(sb.length() - 1);

		if (_logger.isInfoEnabled() && (clock != null)) {
			_logger.info(
				"Getting the current digest took " + clock.getTimeInMs() +
					" ms");
		}

		return sb.toString();
	}

	protected void removeSkippedTaskDependencies(
		TaskCache taskCache, Task task) {

		Set<Object> taskDependencies = task.getDependsOn();

		Set<Object> skippedTaskDependencies =
			taskCache.getSkippedTaskDependencies();

		if (skippedTaskDependencies.isEmpty()) {
			taskDependencies.clear();

			if (_logger.isInfoEnabled()) {
				_logger.info("Removed all dependencies from " + task);
			}
		}
		else {
			for (Object taskDependency : skippedTaskDependencies) {
				boolean removed = taskDependencies.remove(taskDependency);

				if (!removed && (taskDependency instanceof Task)) {
					Task taskDependencyTask = (Task)taskDependency;

					removed = taskDependencies.remove(
						taskDependencyTask.getName());
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
							taskDependency);
				}
			}
		}
	}

	private static final String _DIGEST_FILE_NAME = ".digest";

	private static final char _DIGEST_SEPARATOR = '-';

	private static final Logger _logger = Logging.getLogger(
		TaskCacheApplicator.class);

}
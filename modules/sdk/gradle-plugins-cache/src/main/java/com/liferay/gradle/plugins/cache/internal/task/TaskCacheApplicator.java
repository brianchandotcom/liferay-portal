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

package com.liferay.gradle.plugins.cache.internal.task;

import com.liferay.gradle.plugins.cache.CacheExtension;
import com.liferay.gradle.plugins.cache.internal.util.FileUtil;
import com.liferay.gradle.plugins.cache.internal.util.StringUtil;
import com.liferay.gradle.plugins.cache.task.TaskCache;
import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import java.util.Set;

import org.gradle.api.Action;
import org.gradle.api.GradleException;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.logging.Logger;
import org.gradle.api.plugins.BasePlugin;
import org.gradle.api.tasks.Copy;

/**
 * @author Andrea Di Giorgi
 */
public class TaskCacheApplicator {

	public static final String DIGEST_FILE_NAME = ".digest";

	public void apply(CacheExtension cacheExtension, TaskCache taskCache) {
		Task task = taskCache.getTask();

		Logger logger = task.getLogger();

		boolean upToDate = false;

		String currentDigest = _getCurrentDigest(taskCache);

		if (logger.isInfoEnabled()) {
			logger.info("Current digest is {}", currentDigest);
		}

		if (cacheExtension.isForcedCache()) {
			File cacheDir = taskCache.getCacheDir();

			if (!cacheDir.exists()) {
				throw new GradleException("Unable to find " + cacheDir);
			}

			upToDate = true;
		}
		else if (taskCache.isDisabled()) {
			if (logger.isLifecycleEnabled()) {
				logger.lifecycle("Cache for {} is disabled", task);
			}
		}
		else {
			String cachedDigest = _getCachedDigest(taskCache);

			if (logger.isInfoEnabled()) {
				if (Validator.isNull(cachedDigest)) {
					logger.info("No cached digest has been found");
				}
				else {
					logger.info("Cached digest is {}", cachedDigest);
				}
			}

			if (cachedDigest.equals(currentDigest)) {
				upToDate = true;
			}
		}

		if (upToDate) {
			_applyUpToDate(taskCache, task);
		}
		else {
			_applyOutOfDate(taskCache, task, currentDigest);
		}

		_createRefreshDigestTask(taskCache);
	}

	private void _applyOutOfDate(
		final TaskCache taskCache, Task task, final String currentDigest) {

		Logger logger = task.getLogger();

		if (logger.isInfoEnabled()) {
			logger.info("{} is out-of-date", task);
		}

		Copy copy = _createSaveCacheTask(taskCache);

		copy.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					_writeDigestFile(taskCache, currentDigest);
				}

			});

		task.finalizedBy(copy);
	}

	private void _applyUpToDate(TaskCache taskCache, Task task) {
		Logger logger = task.getLogger();

		if (logger.isInfoEnabled()) {
			logger.info("{} is up-to-date", task);
		}

		_removeSkippedTaskDependencies(taskCache, task);

		Copy copy = _createRestoreCacheTask(taskCache);

		task.dependsOn(copy);

		task.setEnabled(false);
	}

	private Task _createRefreshDigestTask(final TaskCache taskCache) {
		Project project = taskCache.getProject();

		Task task = project.task(taskCache.getRefreshDigestTaskName());

		task.doLast(
			new Action<Task>() {

				@Override
				public void execute(Task task) {
					String digest = _getCurrentDigest(taskCache);

					_writeDigestFile(taskCache, digest);
				}

			});

		task.setDescription("Refresh the digest for " + taskCache);

		return task;
	}

	private Copy _createRestoreCacheTask(TaskCache taskCache) {
		Project project = taskCache.getProject();

		Copy copy = GradleUtil.addTask(
			project, taskCache.getRestoreCacheTaskName(), Copy.class);

		copy.exclude(DIGEST_FILE_NAME);
		copy.from(taskCache.getCacheDir());
		copy.into(taskCache.getBaseDir());
		copy.setDescription(
			"Restores the cached output files of " + taskCache.getTask() + ".");

		return copy;
	}

	private Copy _createSaveCacheTask(TaskCache taskCache) {
		String taskName = taskCache.getSaveCacheTaskName();

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

	private String _getCachedDigest(TaskCache taskCache) {
		try {
			File digestFile = new File(
				taskCache.getCacheDir(), DIGEST_FILE_NAME);

			if (!digestFile.exists()) {
				return "";
			}

			return new String(
				Files.readAllBytes(digestFile.toPath()),
				StandardCharsets.UTF_8);
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

	private String _getCurrentDigest(TaskCache taskCache) {
		return FileUtil.getDigest(
			taskCache.getProject(), taskCache.getTestFiles(),
			taskCache.isExcludeIgnoredTestFiles());
	}

	private void _removeSkippedTaskDependencies(
		TaskCache taskCache, Task task) {

		Logger logger = task.getLogger();

		Set<Object> taskDependencies = task.getDependsOn();

		Set<Object> skippedTaskDependencies =
			taskCache.getSkippedTaskDependencies();

		if (skippedTaskDependencies.isEmpty()) {
			taskDependencies.clear();

			if (logger.isInfoEnabled()) {
				logger.info("Removed all dependencies from {}", task);
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
					if (logger.isInfoEnabled()) {
						logger.info(
							"Removed dependency {} from {}", taskDependency,
							task);
					}
				}
				else if (logger.isWarnEnabled()) {
					logger.warn(
						"Unable to remove skipped task dependency {}",
						taskDependency);
				}
			}
		}
	}

	private void _writeDigestFile(TaskCache taskCache, String digest) {
		Task task = taskCache.getTask();

		Logger logger = task.getLogger();

		File digestFile = new File(taskCache.getCacheDir(), DIGEST_FILE_NAME);

		try {
			Files.write(
				digestFile.toPath(), digest.getBytes(StandardCharsets.UTF_8));

			if (logger.isInfoEnabled()) {
				logger.info("Updated digest file to {}", digest);
			}
		}
		catch (IOException ioe) {
			throw new UncheckedIOException(ioe);
		}
	}

}
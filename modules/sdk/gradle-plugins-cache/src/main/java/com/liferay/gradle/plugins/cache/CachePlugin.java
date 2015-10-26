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

package com.liferay.gradle.plugins.cache;

import com.liferay.gradle.plugins.cache.task.TaskCache;
import com.liferay.gradle.plugins.cache.task.TaskCacheApplicator;
import com.liferay.gradle.util.GradleUtil;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class CachePlugin implements Plugin<Project> {

	public static final String EXTENSION_NAME = "cache";

	@Override
	public void apply(Project project) {
		final CacheExtension cacheExtension = GradleUtil.addExtension(
			project, EXTENSION_NAME, CacheExtension.class);

		project.afterEvaluate(
			new Action<Project>() {

				@Override
				public void execute(Project project) {
					applyTaskCaches(cacheExtension);
				}

			});
	}

	protected void applyTaskCaches(CacheExtension cacheExtension) {
		for (TaskCache taskCache : cacheExtension.getTasks()) {
			_taskCacheApplicator.apply(cacheExtension, taskCache);
		}
	}

	private static final TaskCacheApplicator _taskCacheApplicator =
		new TaskCacheApplicator();

}
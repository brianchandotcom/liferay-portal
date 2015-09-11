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
import com.liferay.gradle.plugins.cache.task.TaskCacheFactory;

import groovy.lang.Closure;

import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Project;
import org.gradle.api.Task;

/**
 * @author Andrea Di Giorgi
 */
public class CacheExtension {

	public CacheExtension(Project project) {
		_taskCaches = project.container(
			TaskCache.class, new TaskCacheFactory(project));
	}

	public NamedDomainObjectContainer<TaskCache> getTasks() {
		return _taskCaches;
	}

	public TaskCache task(String name, Closure<Void> closure) {
		return _taskCaches.create(name, closure);
	}

	public TaskCache task(Task task, Closure<Void> closure) {
		return task(task.getName(), closure);
	}

	private final NamedDomainObjectContainer<TaskCache> _taskCaches;

}
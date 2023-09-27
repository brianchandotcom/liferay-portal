/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public boolean isForcedCache() {
		return _forcedCache;
	}

	public void setForcedCache(boolean forcedCache) {
		_forcedCache = forcedCache;
	}

	public TaskCache task(String name, Closure<Void> closure) {
		return _taskCaches.create(name, closure);
	}

	public TaskCache task(Task task, Closure<Void> closure) {
		return task(task.getName(), closure);
	}

	private boolean _forcedCache;
	private final NamedDomainObjectContainer<TaskCache> _taskCaches;

}
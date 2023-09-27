/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.cache.task;

import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.Project;

/**
 * @author Andrea Di Giorgi
 */
public class TaskCacheFactory implements NamedDomainObjectFactory<TaskCache> {

	public TaskCacheFactory(Project project) {
		_project = project;
	}

	@Override
	public TaskCache create(String name) {
		return new TaskCache(name, _project);
	}

	private final Project _project;

}
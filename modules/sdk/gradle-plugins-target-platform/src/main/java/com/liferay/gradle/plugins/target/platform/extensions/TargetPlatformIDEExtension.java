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

package com.liferay.gradle.plugins.target.platform.extensions;

import com.liferay.gradle.plugins.target.platform.internal.util.GradleUtil;

import java.util.Map;

import org.gradle.api.Project;

/**
 * @author Gregory Amerson
 */
public class TargetPlatformIDEExtension {

	public TargetPlatformIDEExtension(Project project) {
		Map<String, ?> properties = project.getProperties();

		Object indexSources = properties.get("target.platform.index.sources");

		if (indexSources != null) {
			setIndexSources(indexSources);
		}
	}

	public boolean isIndexSources() {
		return GradleUtil.toBoolean(_indexSources);
	}

	public void setIndexSources(Object indexSources) {
		_indexSources = indexSources;
	}

	private Object _indexSources;

}
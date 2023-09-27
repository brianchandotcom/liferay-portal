/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
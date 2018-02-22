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

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.util.GUtil;

/**
 * @author Gregory Amerson
 */
public class TargetPlatformIDEExtension {

	public TargetPlatformIDEExtension(Project project) {
	}

	public Set<?> getIncludeGroups() {
		return _includeGroups;
	}

	public TargetPlatformIDEExtension includeGroups(Iterable<?> includeGroups) {
		GUtil.addToCollection(_includeGroups, includeGroups);

		return this;
	}

	public TargetPlatformIDEExtension includeGroups(Object... includeGroups) {
		return includeGroups(Arrays.asList(includeGroups));
	}

	public void setIncludeGroups(Iterable<?> includeGroups) {
		_includeGroups.clear();

		includeGroups(includeGroups);
	}

	public void setIncludeGroups(Object... includeGroups) {
		setIncludeGroups(Arrays.asList(includeGroups));
	}

	private final Set<Object> _includeGroups = new LinkedHashSet<>();

}
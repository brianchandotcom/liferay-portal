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

package com.liferay.gradle.plugins.jsdoc;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 * @author Peter Shin
 */
public class AppJSDocConfigurationExtension {

	public AppJSDocConfigurationExtension(Project project) {
		_subprojects.addAll(project.getSubprojects());
	}

	public Set<Project> getSubprojects() {
		return _subprojects;
	}

	public void setSubprojects(Iterable<Project> subprojects) {
		_subprojects.clear();

		subprojects(subprojects);
	}

	public void setSubprojects(Project... subprojects) {
		setSubprojects(Arrays.asList(subprojects));
	}

	public AppJSDocConfigurationExtension subprojects(
		Iterable<Project> subprojects) {

		GUtil.addToCollection(_subprojects, subprojects);

		return this;
	}

	public AppJSDocConfigurationExtension subprojects(Project... subprojects) {
		return subprojects(Arrays.asList(subprojects));
	}

	private final Set<Project> _subprojects = new LinkedHashSet<>();

}
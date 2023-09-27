/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.jsdoc;

import com.liferay.gradle.util.GUtil;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gradle.api.Project;

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
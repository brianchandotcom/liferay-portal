/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.app.javadoc.builder;

import com.liferay.gradle.util.GUtil;
import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gradle.api.JavaVersion;
import org.gradle.api.Project;
import org.gradle.api.specs.AndSpec;
import org.gradle.api.specs.Spec;

/**
 * @author Andrea Di Giorgi
 */
public class AppJavadocBuilderExtension {

	public AppJavadocBuilderExtension(Project project) {
		JavaVersion javaVersion = JavaVersion.current();

		if (javaVersion.isJava8Compatible()) {
			_doclintDisabled = true;
		}

		_groupNameClosure = new Closure<String>(project) {

			@SuppressWarnings("unused")
			public String doCall(Project subproject) {
				String groupName = subproject.getDescription();

				if (Validator.isNull(groupName)) {
					groupName = subproject.getName();
				}

				return groupName;
			}

		};

		_subprojects.addAll(project.getSubprojects());
	}

	public Closure<String> getGroupNameClosure() {
		return _groupNameClosure;
	}

	public Spec<Project> getOnlyIf() {
		return _onlyIfSpec;
	}

	public Set<Project> getSubprojects() {
		return _subprojects;
	}

	public boolean isCopyTags() {
		return _copyTags;
	}

	public boolean isDoclintDisabled() {
		return _doclintDisabled;
	}

	public boolean isGroupPackages() {
		return _groupPackages;
	}

	public AppJavadocBuilderExtension onlyIf(Closure<Boolean> onlyIfClosure) {
		_onlyIfSpec = _onlyIfSpec.and(onlyIfClosure);

		return this;
	}

	public AppJavadocBuilderExtension onlyIf(Spec<Project> onlyIfSpec) {
		_onlyIfSpec = _onlyIfSpec.and(onlyIfSpec);

		return this;
	}

	public void setCopyTags(boolean copyTags) {
		_copyTags = copyTags;
	}

	public void setDoclintDisabled(boolean doclintDisabled) {
		_doclintDisabled = doclintDisabled;
	}

	public void setGroupNameClosure(Closure<String> groupNameClosure) {
		_groupNameClosure = groupNameClosure;
	}

	public void setGroupPackages(boolean groupPackages) {
		_groupPackages = groupPackages;
	}

	public void setOnlyIf(Closure<Boolean> onlyIfClosure) {
		_onlyIfSpec = new AndSpec<>();

		_onlyIfSpec.and(onlyIfClosure);
	}

	public void setOnlyIf(Spec<Project> onlyIfSpec) {
		_onlyIfSpec = new AndSpec<>(onlyIfSpec);
	}

	public void setSubprojects(Iterable<Project> subprojects) {
		_subprojects.clear();

		subprojects(subprojects);
	}

	public void setSubprojects(Project... subprojects) {
		setSubprojects(Arrays.asList(subprojects));
	}

	public AppJavadocBuilderExtension subprojects(
		Iterable<Project> subprojects) {

		GUtil.addToCollection(_subprojects, subprojects);

		return this;
	}

	public AppJavadocBuilderExtension subprojects(Project... subprojects) {
		return subprojects(Arrays.asList(subprojects));
	}

	private boolean _copyTags = true;
	private boolean _doclintDisabled;
	private Closure<String> _groupNameClosure;
	private boolean _groupPackages = true;
	private AndSpec<Project> _onlyIfSpec = new AndSpec<>();
	private final Set<Project> _subprojects = new LinkedHashSet<>();

}
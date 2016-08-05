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

package com.liferay.gradle.plugins.app.javadoc.builder;

import com.liferay.gradle.util.Validator;

import groovy.lang.Closure;

import org.gradle.api.JavaVersion;
import org.gradle.api.Project;

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
	}

	public Closure<String> getGroupNameClosure() {
		return _groupNameClosure;
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

	private boolean _copyTags = true;
	private boolean _doclintDisabled;
	private Closure<String> _groupNameClosure;
	private boolean _groupPackages = true;

}
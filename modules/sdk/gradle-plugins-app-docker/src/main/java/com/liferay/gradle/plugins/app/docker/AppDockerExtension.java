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

package com.liferay.gradle.plugins.app.docker;

import com.liferay.gradle.util.GradleUtil;

import groovy.lang.Closure;

import java.io.File;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.gradle.api.Project;
import org.gradle.api.specs.AndSpec;
import org.gradle.api.specs.Spec;
import org.gradle.util.CollectionUtils;
import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class AppDockerExtension {

	public AppDockerExtension(Project project) {
		_project = project;

		_imageName = new Callable<String>() {

			@Override
			public String call() throws Exception {
				return _project.getName();
			}

		};

		_inputDir = _project.getName() + "-docker";

		_imageUser = new Callable<Object>() {

			@Override
			public Object call() throws Exception {
				return _project.getGroup();
			}

		};

		_subprojects = CollectionUtils.filter(
			_project.getSubprojects(),
			new Spec<Project>() {

				@Override
				public boolean isSatisfiedBy(Project project) {
					Set<Project> subprojects = project.getSubprojects();

					if (subprojects.isEmpty()) {
						return true;
					}

					return false;
				}

			});
	}

	public String getImageName() {
		return GradleUtil.toString(_imageName);
	}

	public List<Object> getImageTags() {
		return _imageTags;
	}

	public String getImageUser() {
		return GradleUtil.toString(_imageUser);
	}

	public File getInputDir() {
		return GradleUtil.toFile(_project, _inputDir);
	}

	public Spec<Project> getOnlyIf() {
		return _onlyIfSpec;
	}

	public Set<Project> getSubprojects() {
		return _subprojects;
	}

	public AppDockerExtension imageTags(Iterable<?> imageTags) {
		GUtil.addToCollection(_imageTags, imageTags);

		return this;
	}

	public AppDockerExtension imageTags(Object... imageTags) {
		return imageTags(Arrays.asList(imageTags));
	}

	public AppDockerExtension onlyIf(Closure<Boolean> onlyIfClosure) {
		_onlyIfSpec = _onlyIfSpec.and(onlyIfClosure);

		return this;
	}

	public AppDockerExtension onlyIf(Spec<Project> onlyIfSpec) {
		_onlyIfSpec = _onlyIfSpec.and(onlyIfSpec);

		return this;
	}

	public void setImageName(Object imageName) {
		_imageName = imageName;
	}

	public void setImageTags(Iterable<?> imageTags) {
		_imageTags.clear();

		imageTags(imageTags);
	}

	public void setImageTags(Object... imageTags) {
		setImageTags(Arrays.asList(imageTags));
	}

	public void setImageUser(Object imageUser) {
		_imageUser = imageUser;
	}

	public void setInputDir(Object inputDir) {
		_inputDir = inputDir;
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

	public AppDockerExtension subprojects(Iterable<Project> subprojects) {
		GUtil.addToCollection(_subprojects, subprojects);

		return this;
	}

	public AppDockerExtension subprojects(Project... subprojects) {
		return subprojects(Arrays.asList(subprojects));
	}

	private Object _imageName;
	private final List<Object> _imageTags = new ArrayList<>();
	private Object _imageUser;
	private Object _inputDir;
	private AndSpec<Project> _onlyIfSpec = new AndSpec<>();
	private final Project _project;
	private final Set<Project> _subprojects;

}
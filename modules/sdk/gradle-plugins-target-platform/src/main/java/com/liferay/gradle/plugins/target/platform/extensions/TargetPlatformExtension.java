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

import groovy.lang.Closure;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.gradle.api.Project;
import org.gradle.api.specs.AndSpec;
import org.gradle.api.specs.Spec;
import org.gradle.util.GUtil;

/**
 * @author Gregory Amerson
 */
public class TargetPlatformExtension {

	public TargetPlatformExtension(Project project) {
		_subprojects.addAll(project.getSubprojects());
	}

	public Spec<Project> getOnlyIf() {
		return _onlyIfSpec;
	}

	public Spec<Project> getResolveOnlyIf() {
		return _resolveOnlyIfSpec;
	}

	public Set<Project> getSubprojects() {
		return _subprojects;
	}

	public boolean isIgnoreResolveFailures() {
		return GradleUtil.toBoolean(_ignoreResolveFailures);
	}

	public TargetPlatformExtension onlyIf(Closure<Boolean> onlyIfClosure) {
		_onlyIfSpec = _onlyIfSpec.and(onlyIfClosure);

		return this;
	}

	public TargetPlatformExtension onlyIf(Spec<Project> onlyIfSpec) {
		_onlyIfSpec = _onlyIfSpec.and(onlyIfSpec);

		return this;
	}

	public TargetPlatformExtension resolveOnlyIf(
		Closure<Boolean> resolveOnlyIfClosure) {

		_resolveOnlyIfSpec = _resolveOnlyIfSpec.and(resolveOnlyIfClosure);

		return this;
	}

	public TargetPlatformExtension resolveOnlyIf(
		Spec<Project> resolveOnlyIfSpec) {

		_resolveOnlyIfSpec = _resolveOnlyIfSpec.and(resolveOnlyIfSpec);

		return this;
	}

	public void setIgnoreResolveFailures(Object ignoreResolveFailures) {
		_ignoreResolveFailures = ignoreResolveFailures;
	}

	public void setOnlyIf(Closure<Boolean> onlyIfClosure) {
		_onlyIfSpec = new AndSpec<>();

		_onlyIfSpec.and(onlyIfClosure);
	}

	public void setOnlyIf(Spec<Project> onlyIfSpec) {
		_onlyIfSpec = new AndSpec<>(onlyIfSpec);
	}

	public void setResolveOnlyIf(Closure<Boolean> resolveOnlyIfClosure) {
		_resolveOnlyIfSpec = new AndSpec<>();

		_resolveOnlyIfSpec.and(resolveOnlyIfClosure);
	}

	public void setResolveOnlyIf(Spec<Project> resolveOnlyIfSpec) {
		_resolveOnlyIfSpec = new AndSpec<>(resolveOnlyIfSpec);
	}

	public void setSubprojects(Iterable<Project> subprojects) {
		_subprojects.clear();

		subprojects(subprojects);
	}

	public void setSubprojects(Project... subprojects) {
		setSubprojects(Arrays.asList(subprojects));
	}

	public TargetPlatformExtension subprojects(Iterable<Project> subprojects) {
		GUtil.addToCollection(_subprojects, subprojects);

		return this;
	}

	public TargetPlatformExtension subprojects(Project... subprojects) {
		return subprojects(Arrays.asList(subprojects));
	}

	private Object _ignoreResolveFailures;
	private AndSpec<Project> _onlyIfSpec = new AndSpec<>();
	private AndSpec<Project> _resolveOnlyIfSpec = new AndSpec<>();
	private final Set<Project> _subprojects = new LinkedHashSet<>();

}
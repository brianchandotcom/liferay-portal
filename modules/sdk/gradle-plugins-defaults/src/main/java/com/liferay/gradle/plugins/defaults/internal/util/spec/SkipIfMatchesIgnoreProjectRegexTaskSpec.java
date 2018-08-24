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

package com.liferay.gradle.plugins.defaults.internal.util.spec;

import com.liferay.gradle.plugins.defaults.internal.util.GradleUtil;
import com.liferay.gradle.util.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.specs.Spec;

/**
 * @author Peter Shin
 */
public class SkipIfMatchesIgnoreProjectRegexTaskSpec implements Spec<Task> {

	@Override
	public boolean isSatisfiedBy(Task task) {
		String ignoreProjectRegex = GradleUtil.getTaskPrefixedProperty(
			task, "ignore.project.regex");

		if (Validator.isNull(ignoreProjectRegex)) {
			return true;
		}

		Pattern pattern = Pattern.compile(ignoreProjectRegex);

		Project project = task.getProject();

		Matcher matcher = pattern.matcher(project.getName());

		if (!matcher.find()) {
			return true;
		}

		return false;
	}

}
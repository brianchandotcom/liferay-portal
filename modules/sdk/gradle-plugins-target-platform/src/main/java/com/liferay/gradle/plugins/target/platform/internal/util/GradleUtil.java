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

package com.liferay.gradle.plugins.target.platform.internal.util;

import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;

/**
 * @author Gregory Amerson
 */
public class GradleUtil extends com.liferay.gradle.util.GradleUtil {

	public static void addDependency(
		Configuration configuration, Dependency dependency) {

		DependencySet dependencySet = configuration.getDependencies();

		dependencySet.add(dependency);
	}

	public static boolean toBoolean(Object object) {
		object = toObject(object);

		if (object instanceof Boolean) {
			return (Boolean)object;
		}

		if (object instanceof String) {
			return Boolean.parseBoolean((String)object);
		}

		return false;
	}

}
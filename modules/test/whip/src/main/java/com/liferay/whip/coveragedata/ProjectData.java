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

package com.liferay.whip.coveragedata;

/**
 * @author Shuyang Zhou
 */
public class ProjectData
	extends CoverageDataContainer<String, ClassData, ProjectData> {

	public ClassData getClassData(String className) {
		ClassData classData = children.get(className);

		if (classData == null) {
			throw new IllegalStateException(
				"No instrument data for class " + className);
		}

		return classData;
	}

	public ClassData getOrCreateClassData(String className) {
		ClassData classData = children.get(className);

		if (classData == null) {
			classData = new ClassData(className);

			ClassData previousClassData = children.putIfAbsent(
				className, classData);

			if (previousClassData != null) {
				classData = previousClassData;
			}
		}

		return classData;
	}

	private static final long serialVersionUID = 1;

}
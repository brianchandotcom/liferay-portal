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

package com.liferay.bundle.support.internal.util;

import java.io.File;

import org.apache.maven.project.MavenProject;

/**
 * @author David Truong
 */
public class MavenUtil {

	public static MavenProject getRootParent(MavenProject project) {
		if (project.hasParent()) {
			return getRootParent(project.getParent());
		}
		else return project;
	}

	public static File getRootParentBaseDir(MavenProject project) {
		MavenProject rootProject = getRootParent(project);

		return rootProject.getBasedir();
	}

}
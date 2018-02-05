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

package com.liferay.gradle.plugins.tasks;

import java.io.File;

import org.gradle.api.Project;
import org.gradle.api.tasks.JavaExec;
import org.gradle.api.tasks.OutputFile;

/**
 * @author David Truong
 * @author Andrea Di Giorgi
 */
public class BuildExtInfoTask extends JavaExec {

	public BuildExtInfoTask() {
		setMain("com.liferay.portal.tools.ExtInfoBuilder");
		setMaxHeapSize("256m");
	}

	@OutputFile
	public File getOutput() {
		Project project = getProject();

		return new File(getTemporaryDir(), "ext-" + project.getName() + ".xml");
	}

}
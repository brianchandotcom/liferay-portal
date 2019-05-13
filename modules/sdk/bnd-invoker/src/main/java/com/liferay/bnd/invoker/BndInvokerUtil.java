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

package com.liferay.bnd.invoker;

import aQute.bnd.ant.BndTask;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.util.Properties;

import org.apache.tools.ant.Project;

/**
 * @author Shuyang Zhou
 */
public class BndInvokerUtil {

	public static void invoke(
			String propertiesString, File baseDir, File output)
		throws IOException {

		BndTask bndTask = new BndTask();

		Project project = new Project();

		project.setBaseDir(baseDir);

		Properties properties = new Properties();

		try (Reader reader = new StringReader(propertiesString)) {
			properties.load(reader);
		}

		for (String key : properties.stringPropertyNames()) {
			project.setProperty(key, properties.getProperty(key));
		}

		bndTask.setProject(project);

		bndTask.setClasspath("classes");
		bndTask.setExceptions(true);
		bndTask.setFiles("bnd.bnd");
		bndTask.setOutput(output);

		bndTask.execute();
	}

}
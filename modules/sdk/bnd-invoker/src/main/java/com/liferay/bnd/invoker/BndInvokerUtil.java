/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
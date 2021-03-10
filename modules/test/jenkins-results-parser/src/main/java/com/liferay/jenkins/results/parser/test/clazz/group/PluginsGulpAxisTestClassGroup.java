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

package com.liferay.jenkins.results.parser.test.clazz.group;

import java.io.File;

import java.util.List;

/**
 * @author Michael Hashimoto
 */
public class PluginsGulpAxisTestClassGroup extends AxisTestClassGroup {

	@Override
	public File getTestBaseDir() {
		List<TestClass> testClasses = getTestClasses();

		if (testClasses.isEmpty()) {
			return null;
		}

		TestClass testClass = testClasses.get(0);

		File testBaseDir = testClass.getTestClassFile();

		if ((testBaseDir == null) || !testBaseDir.exists()) {
			return null;
		}

		return testBaseDir;
	}

	protected PluginsGulpAxisTestClassGroup(
		PluginsGulpBatchTestClassGroup pluginsGulpBatchTestClassGroup) {

		super(pluginsGulpBatchTestClassGroup);
	}

}
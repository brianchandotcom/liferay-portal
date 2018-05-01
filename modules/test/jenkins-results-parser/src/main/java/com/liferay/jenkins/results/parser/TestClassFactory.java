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

package com.liferay.jenkins.results.parser;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Michael Hashimoto
 */
public class TestClassFactory {

	public static JunitTestClass newJunitTestClass(
		GitWorkingDirectory gitWorkingDirectory, File file, File srcFile) {

		if (_junitTestClass.containsKey(file)) {
			return _junitTestClass.get(file);
		}

		JunitTestClass junitTestClass = new JunitTestClass(
			gitWorkingDirectory, file, srcFile);

		_junitTestClass.put(file, junitTestClass);

		return junitTestClass;
	}

	private static final Map<File, JunitTestClass> _junitTestClass =
		new HashMap<>();

}
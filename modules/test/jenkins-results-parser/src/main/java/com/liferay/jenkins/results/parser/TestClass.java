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

/**
 * @author Michael Hashimoto
 */
public class TestClass implements Comparable {

	public TestClass(File file) {
		_file = file;
	}

	@Override
	public int compareTo(Object o) {
		TestClass testClass = (TestClass)o;

		return _file.compareTo(testClass.getFile());
	}

	public File getFile() {
		return _file;
	}

	private final File _file;

}
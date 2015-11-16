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

package com.liferay.arquillian.extension.junit.bridge.junit.test.dependencies;

import java.io.BufferedWriter;
import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class TestItemHelper {

	public TestItemHelper(Class<?> clazz) {
		_path = Paths.get(
			System.getProperty("user.home"), clazz.getName() + ".log");
	}

	public List<String> read() throws IOException {
		try {
			return Files.readAllLines(_path, Charset.defaultCharset());
		}
		finally {
			Files.delete(_path);
		}
	}

	public void write(String s) throws IOException {
		try (BufferedWriter bufferedWriter = Files.newBufferedWriter(
			_path, Charset.defaultCharset(), StandardOpenOption.APPEND,
			StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {

			bufferedWriter.write(s);
			bufferedWriter.newLine();
		}
	}

	private final Path _path;

}
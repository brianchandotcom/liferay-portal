/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.arquillian.extension.junit.bridge.junit.test.item;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;

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

		try {
			Files.deleteIfExists(_path);
		}
		catch (IOException ioException) {
			throw new UncheckedIOException(ioException);
		}
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
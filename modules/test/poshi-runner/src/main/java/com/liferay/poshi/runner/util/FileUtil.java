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

package com.liferay.poshi.runner.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class FileUtil {

	public static void copyDirectory(File sourceDir, File destinationDir)
		throws IOException {

		FileUtils.copyDirectory(sourceDir, destinationDir);
	}

	public static void copyDirectory(
			String sourceDirName, String destinationDirName)
		throws IOException {

		copyDirectory(new File(sourceDirName), new File(destinationDirName));
	}

	public static boolean exists(File file) {
		return file.exists();
	}

	public static boolean exists(String fileName) {
		File file = new File(fileName);

		return exists(file);
	}

	public static String getSeparator() {
		return File.separator;
	}

	public static String read(File file) throws IOException {
		return FileUtils.readFileToString(file);
	}

	public static String read(String fileName) throws IOException {
		File file = new File(fileName);

		return read(file);
	}

	public static void write(File file, String s) throws IOException {
		FileUtils.writeStringToFile(file, s);
	}

	public static void write(String fileName, String s) throws IOException {
		File file = new File(fileName);

		write(file, s);
	}

}
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

package com.liferay.gradle.plugins.go.internal.util;

import java.io.File;
import java.io.FileFilter;

/**
 * @author Peter Shin
 */
public class FileUtil extends com.liferay.gradle.util.FileUtil {

	public static File[] getFiles(File dir, final String extension) {
		return dir.listFiles(
			new FileFilter() {

				@Override
				public boolean accept(File file) {
					if (file.isDirectory()) {
						return false;
					}

					String fileName = file.getName();

					if (!fileName.endsWith("." + extension)) {
						return false;
					}

					return true;
				}

			});
	}

	public static String getSimpleName(File file) {
		if (file == null) {
			return null;
		}

		String fileName = file.getName();

		int pos = fileName.lastIndexOf(".");

		if (pos == -1) {
			return fileName;
		}

		return fileName.substring(0, pos);
	}

}
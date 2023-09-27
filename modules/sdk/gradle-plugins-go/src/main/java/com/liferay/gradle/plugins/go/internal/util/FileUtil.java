/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.soap.repository.connector.operation;

import com.liferay.petra.string.StringPool;

/**
 * @author Iván Zaera
 */
public final class PathUtil {

	public static String buildPath(String folderPath, String name) {
		validatePath(folderPath);

		validateName(name);

		if (folderPath.equals(StringPool.SLASH)) {
			return StringPool.SLASH + name;
		}

		return folderPath + StringPool.SLASH + name;
	}

	public static String getExtension(String path) {
		int pos = path.lastIndexOf(StringPool.PERIOD);

		if (pos == -1) {
			return StringPool.BLANK;
		}

		return path.substring(pos + 1);
	}

	public static String getName(String path) {
		validatePath(path);

		if (path.equals(StringPool.SLASH)) {
			return StringPool.SLASH;
		}

		int pos = path.lastIndexOf(StringPool.SLASH);

		return path.substring(pos + 1);
	}

	public static String getNameWithoutExtension(String path) {
		String name = getName(path);

		int pos = name.lastIndexOf(StringPool.PERIOD);

		if (pos == -1) {
			return name;
		}

		return name.substring(0, pos);
	}

	public static String getParentFolderPath(String path) {
		validatePath(path);

		int pos = path.lastIndexOf(StringPool.SLASH);

		if (pos == 0) {
			return StringPool.SLASH;
		}

		return path.substring(0, pos);
	}

	public static void validateName(String name) {
		if ((name == null) || name.contains(StringPool.SLASH)) {
			throw new IllegalArgumentException(
				"Invalid file or folder name " + name);
		}
	}

	public static void validatePath(String path) {
		if ((path == null) ||
			(!path.equals(StringPool.SLASH) &&
			 (!path.startsWith(StringPool.SLASH) ||
			  path.endsWith(StringPool.SLASH)))) {

			throw new IllegalArgumentException("Invalid path " + path);
		}
	}

}
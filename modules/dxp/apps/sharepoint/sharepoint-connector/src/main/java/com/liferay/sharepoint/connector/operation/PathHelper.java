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

package com.liferay.sharepoint.connector.operation;

import com.liferay.portal.kernel.util.StringPool;

/**
 * @author Iván Zaera
 */
public class PathHelper {

	public String buildPath(String folderPath, String name) {
		validatePath(folderPath);

		validateName(name);

		if (folderPath.equals(StringPool.SLASH)) {
			return StringPool.SLASH + name;
		}

		return folderPath + StringPool.SLASH + name;
	}

	public String getExtension(String path) {
		int pos = path.lastIndexOf(StringPool.PERIOD);

		if (pos == -1) {
			return StringPool.BLANK;
		}

		return path.substring(pos + 1);
	}

	public String getName(String path) {
		validatePath(path);

		if (path.equals(StringPool.SLASH)) {
			return StringPool.SLASH;
		}

		int pos = path.lastIndexOf(StringPool.SLASH);

		return path.substring(pos + 1);
	}

	public String getNameWithoutExtension(String path) {
		String name = getName(path);

		int pos = name.lastIndexOf(StringPool.PERIOD);

		if (pos == -1) {
			return name;
		}

		return name.substring(0, pos);
	}

	public String getParentFolderPath(String path) {
		validatePath(path);

		int pos = path.lastIndexOf(StringPool.SLASH);

		if (pos == 0) {
			return StringPool.SLASH;
		}

		return path.substring(0, pos);
	}

	public void validateName(String name) {
		if ((name == null) || name.contains(StringPool.SLASH)) {
			throw new IllegalArgumentException(
				"Invalid file or folder name " + name);
		}
	}

	public void validatePath(String path) {
		if ((path == null) ||
			(!path.equals(StringPool.SLASH) &&
			 (!path.startsWith(StringPool.SLASH) ||
			  path.endsWith(StringPool.SLASH)))) {

			throw new IllegalArgumentException("Invalid path " + path);
		}
	}

}
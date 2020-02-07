/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.connector.operation;

import com.liferay.petra.string.StringPool;

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
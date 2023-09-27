/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.petra.string.StringPool;

/**
 * @author Brian Wing Shun Chan
 */
public class PathUtil {

	public static String toUnixPath(String path) {
		return StringUtil.replace(
			path, new String[] {StringPool.BACK_SLASH, StringPool.DOUBLE_SLASH},
			new String[] {StringPool.SLASH, StringPool.SLASH});
	}

	public static String toWindowsPath(String path) {
		return StringUtil.replace(
			path, new String[] {StringPool.SLASH, StringPool.DOUBLE_BACK_SLASH},
			new String[] {StringPool.BACK_SLASH, StringPool.BACK_SLASH});
	}

}
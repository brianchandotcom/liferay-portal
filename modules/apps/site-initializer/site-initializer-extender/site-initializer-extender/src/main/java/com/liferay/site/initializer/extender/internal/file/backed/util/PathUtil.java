/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.initializer.extender.internal.file.backed.util;

/**
 * @author Shuyang Zhou
 */
public class PathUtil {

	public static String removePrefix(String path) {
		if (path.startsWith("/site-initializer/")) {
			path = path.substring("/site-initializer/".length());
		}

		return path;
	}

}
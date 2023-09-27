/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.upgrade.client.util;

/**
 * @author Andrea Di Giorgi
 */
public class StringUtil {

	public static String join(Iterable<?> objects, char separator) {
		StringBuilder sb = new StringBuilder();

		int i = 0;

		for (Object object : objects) {
			if (i > 0) {
				sb.append(separator);
			}

			sb.append(object);

			i++;
		}

		return sb.toString();
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.util;

/**
 * @author Hugo Huijser
 */
public class XMLSourceUtil {

	public static boolean isInsideCDATAMarkup(String content, int pos) {
		String s = content.substring(pos);

		int x = s.indexOf("]]>");

		if (x == -1) {
			return false;
		}

		s = s.substring(0, x);

		if (!s.contains("<![CDATA[")) {
			return true;
		}

		return false;
	}

	public static boolean isInsideComment(String content, int pos) {
		String s = content.substring(pos);

		int x = s.indexOf("-->");

		if (x == -1) {
			return false;
		}

		s = s.substring(0, x);

		if (!s.contains("<!--")) {
			return true;
		}

		return false;
	}

}
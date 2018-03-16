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

package com.liferay.source.formatter.checks.util;

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

}
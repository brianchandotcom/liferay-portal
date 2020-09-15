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

package com.liferay.poshi.runner.util;

import java.net.URLEncoder;

/**
 * @author Kenji Heigel
 */
public class URLUtil {

	public static String encode(String s, String encoding) throws Exception {
		return URLEncoder.encode(s, encoding);
	}

	public static String encodeUTF8(String s) throws Exception {
		return encode(s, "UTF-8");
	}

}
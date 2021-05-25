/**
 * Copyright (c) 2000-2021 Liferay, Inc. All rights reserved.
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

package com.liferay.mule.internal.util;

/**
 * @author Igor Beslic
 */
public class StringUtil {

	public static String replace(String pattern, String... tplArgs) {
		String replaced = pattern;

		for (int i = 0; i < tplArgs.length; i = i + 2) {
			replaced = replaced.replace(tplArgs[i], tplArgs[i + 1]);
		}

		return replaced;
	}

	private StringUtil() {
	}

}
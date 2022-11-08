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

package com.liferay.object.entry.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Igor Beslic
 */
public class ObjectEntryNameUtil {

	public static String fromTechnicalName(String name) {
		Matcher matcher = _pattern.matcher(name);

		if (matcher.matches()) {
			return matcher.group(1);
		}

		return name;
	}

	public static String toTechnicalName(long companyId, String name) {
		return "C_" + name.trim() + companyId;
	}

	private static final Pattern _pattern = Pattern.compile(
		"(C_[a-zA-Z]+)\\d+");

}
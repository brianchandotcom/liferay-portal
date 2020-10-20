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

package com.liferay.asset.info.display.contributor.field.util;

import com.liferay.info.display.contributor.InfoDisplayField;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Jürgen Kappler
 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
 */
@Deprecated
public class ExpandoInfoDisplayFieldProviderUtil {

	public static List<InfoDisplayField> getExpandoInfoDisplayFields(
		String className, Locale locale) {

		throw new UnsupportedOperationException();
	}

	public static Map<String, Object> getExpandoInfoDisplayFieldsValues(
		String className, Object displayObject, Locale locale) {

		throw new UnsupportedOperationException();
	}

}
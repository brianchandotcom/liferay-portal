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

package com.liferay.gradle.plugins.lang.builder.internal.util;

import com.liferay.gradle.util.GradleUtil;
import com.liferay.gradle.util.Validator;

/**
 * @author Andrea Di Giorgi
 */
public class StringUtil extends com.liferay.gradle.util.StringUtil {

	public static String merge(Iterable<?> iterable, String separator) {
		StringBuilder sb = new StringBuilder();

		boolean first = true;

		for (Object object : iterable) {
			if (!first) {
				sb.append(separator);
			}

			first = false;

			String s = GradleUtil.toString(object);

			if (Validator.isNotNull(s)) {
				sb.append(s);
			}
		}

		return sb.toString();
	}

}
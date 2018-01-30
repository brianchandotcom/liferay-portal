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

package com.liferay.portal.bundle.blacklist.internal;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthew Tambara
 */
public class ParamUtil {

	public static Map<String, String[]> getParameterMap(String location) {
		int index = location.indexOf(CharPool.QUESTION);

		if (index == -1) {
			return Collections.emptyMap();
		}

		String queryString = location.substring(index + 1);

		if (Validator.isNull(queryString)) {
			return Collections.emptyMap();
		}

		String[] parameters = StringUtil.split(queryString, CharPool.AMPERSAND);

		Map<String, String[]> parameterMap = new HashMap<>();

		for (String parameter : parameters) {
			if (parameter.length() > 0) {
				String[] kvp = StringUtil.split(parameter, CharPool.EQUAL);

				if (kvp.length == 0) {
					continue;
				}

				String key = kvp[0];

				String value = StringPool.BLANK;

				if (kvp.length > 1) {
					value = kvp[1];
				}

				String[] values = parameterMap.get(key);

				if (values == null) {
					parameterMap.put(key, new String[] {value});
				}
				else {
					parameterMap.put(key, ArrayUtil.append(values, value));
				}
			}
		}

		return parameterMap;
	}

}
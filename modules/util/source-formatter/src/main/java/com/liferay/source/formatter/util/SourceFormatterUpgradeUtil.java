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

package com.liferay.source.formatter.util;

import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Simon Jiang
 */
public class SourceFormatterUpgradeUtil {

	public static Tuple getTypeNamesTuple(String fileName, String category) {
		JSONObject jsonObject = null;

		try {
			ClassLoader classLoader =
				SourceFormatterUpgradeUtil.class.getClassLoader();

			String content = StringUtil.read(
				classLoader.getResourceAsStream("dependencies/" + fileName));

			if (Validator.isNotNull(content)) {
				jsonObject = new JSONObjectImpl(content);
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		if (jsonObject == null) {
			jsonObject = new JSONObjectImpl();

			jsonObject.put(category, new JSONArrayImpl());
		}

		return new Tuple(jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SourceFormatterUpgradeUtil.class);

}
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

package com.liferay.source.formatter.check;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Hugo Huijser
 */
public class JSONStylingCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
		String fileName, String absolutePath, String content) {

		if (Validator.isNull(content)) {
			return StringPool.BLANK;
		}

		try {
			if (StringUtil.startsWith(
					StringUtil.trim(content), StringPool.OPEN_BRACKET)) {

				return JSONUtil.toString(new JSONArrayImpl(content));
			}

			if (content.endsWith("\n") && fileName.endsWith("/package.json")) {
				return JSONUtil.toString(new JSONObjectImpl(content)) + "\n";
			}

			return JSONUtil.toString(new JSONObjectImpl(content));
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return content;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONStylingCheck.class);

}
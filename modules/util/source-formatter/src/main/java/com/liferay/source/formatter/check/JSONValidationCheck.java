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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.tools.ToolsUtil;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Hugo Huijser
 */
public class JSONValidationCheck extends BaseFileCheck {

	@Override
	protected String doProcess(
			String fileName, String absolutePath, String content)
		throws IOException {

		if (Validator.isNull(content)) {
			return content;
		}

		content = _removeJSONComments(content);

		try {
			if (StringUtil.startsWith(
					StringUtil.trim(content), StringPool.OPEN_BRACKET)) {

				new JSONArray(content);
			}
			else {
				new JSONObject(content);
			}
		}
		catch (JSONException jsonException) {
			addMessage(fileName, jsonException.getMessage());
		}

		return content;
	}

	private String _removeJSONComments(String content) throws IOException {
		int x = -1;

		while (true) {
			x = content.indexOf("/*", x + 1);

			if (x == -1) {
				break;
			}

			if (ToolsUtil.isInsideQuotes(content, x)) {
				continue;
			}

			int y = content.indexOf("*/", x + 2);

			return content.substring(0, x) + content.substring(y + 2);
		}

		x = -1;

		while (true) {
			x = content.indexOf("//", x + 1);

			if (x == -1) {
				break;
			}

			if (ToolsUtil.isInsideQuotes(content, x)) {
				continue;
			}

			int y = content.indexOf("\n", x);

			if (y != -1) {
				return content.substring(0, x) + content.substring(y);
			}

			return content.substring(0, x);
		}

		return content;
	}

}
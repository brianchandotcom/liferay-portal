/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
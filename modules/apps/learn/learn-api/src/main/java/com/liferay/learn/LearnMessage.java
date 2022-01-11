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

package com.liferay.learn;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Brian Wing Shun Chan
 */
public class LearnMessage {

	public LearnMessage(JSONObject jsonObject, String key, String languageId) {
		if (jsonObject.length() == 0) {
			return;
		}

		JSONObject keyJSONObject = jsonObject.getJSONObject(key);

		if (keyJSONObject == null) {
			return;
		}

		JSONObject languageIdJSONObject = keyJSONObject.getJSONObject(
			languageId);

		if (languageIdJSONObject == null) {
			if (languageId.equals("en_US")) {
				return;
			}

			languageIdJSONObject = keyJSONObject.getJSONObject("en_US");

			if (languageIdJSONObject == null) {
				return;
			}
		}

		_message = languageIdJSONObject.getString("message");
		_url = languageIdJSONObject.getString("url");

		_html = StringBundler.concat(
			"<a href=\"", _url, "\" target=\"_blank\">", _message, "</a>");
	}

	public String getHTML() {
		return _html;
	}

	public String getMessage() {
		return _message;
	}

	public String getURL() {
		return _url;
	}

	private String _html = StringPool.BLANK;
	private String _message = StringPool.BLANK;
	private String _url = StringPool.BLANK;

}
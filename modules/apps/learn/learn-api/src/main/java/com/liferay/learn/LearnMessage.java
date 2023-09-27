/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			"<a class=\"text-underline\" href=\"", _url,
			"\" target=\"_blank\">", _message, "</a>");
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
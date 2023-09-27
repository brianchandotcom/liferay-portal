/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.info.type;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

/**
 * @author Eudaldo Alonso
 */
public class WebURL {

	public WebURL(String url) {
		_url = url;
	}

	public String getURL() {
		return _url;
	}

	public boolean isNofollow() {
		return _nofollow;
	}

	public void setNofollow(Boolean nofollow) {
		_nofollow = nofollow;
	}

	public JSONObject toJSONObject() {
		JSONObject jsonObject = JSONUtil.put("url", _url);

		if (_nofollow != null) {
			jsonObject.put("nofollow", _nofollow);
		}

		return jsonObject;
	}

	@Override
	public String toString() {
		return getURL();
	}

	private Boolean _nofollow;
	private final String _url;

}
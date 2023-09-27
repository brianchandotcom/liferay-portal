/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Objects;

/**
 * @author David Arques
 */
public class ReferringURL {

	public ReferringURL(int trafficAmount, String url) {
		_trafficAmount = trafficAmount;
		_url = url;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ReferringURL)) {
			return false;
		}

		ReferringURL referringURL = (ReferringURL)object;

		if (Objects.equals(_trafficAmount, referringURL._trafficAmount) &&
			Objects.equals(_url, referringURL._url)) {

			return true;
		}

		return false;
	}

	public int getTrafficAmount() {
		return _trafficAmount;
	}

	public String getUrl() {
		return _url;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_trafficAmount, _url);
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"trafficAmount", _trafficAmount
		).put(
			"url", _url
		);
	}

	@Override
	public String toString() {
		return JSONUtil.put(
			"trafficAmount", _trafficAmount
		).put(
			"url", _url
		).toString();
	}

	private final int _trafficAmount;
	private final String _url;

}
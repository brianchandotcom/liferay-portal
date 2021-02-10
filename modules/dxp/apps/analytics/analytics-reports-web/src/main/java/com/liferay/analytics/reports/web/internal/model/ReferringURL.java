/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
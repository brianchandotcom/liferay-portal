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
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;
import java.util.ResourceBundle;

/**
 * @author David Arques
 */
public class ReferringSocialMedia {

	public ReferringSocialMedia(String name, int trafficAmount) {
		if (Validator.isNull(name)) {
			throw new IllegalArgumentException("Name is null");
		}

		_name = name;
		_trafficAmount = trafficAmount;
	}

	public String getName() {
		return _name;
	}

	public int getTrafficAmount() {
		return _trafficAmount;
	}

	public JSONObject toJSONObject(ResourceBundle resourceBundle) {
		String title = StringUtil.upperCaseFirstLetter(_name);

		if (Objects.equals("other", _name)) {
			title = ResourceBundleUtil.getString(resourceBundle, _name);
		}

		return JSONUtil.put(
			"name", _name
		).put(
			"title", title
		).put(
			"trafficAmount", _trafficAmount
		);
	}

	@Override
	public String toString() {
		return JSONUtil.put(
			"name", _name
		).put(
			"trafficAmount", _trafficAmount
		).toString();
	}

	private final String _name;
	private final int _trafficAmount;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (Objects.equals(_name, "other")) {
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
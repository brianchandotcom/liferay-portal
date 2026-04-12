/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Objects;

/**
 * @author Thiago Buarque
 */
public class PageExperience {

	public PageExperience() {
	}

	public PageExperience(String id, String name) {
		_id = id;
		_name = name;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof PageExperience)) {
			return false;
		}

		PageExperience pageExperience = (PageExperience)object;

		if (Objects.equals(_id, pageExperience._id) &&
			Objects.equals(_name, pageExperience._name)) {

			return true;
		}

		return false;
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_id, _name);
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"id", _id
		).put(
			"name", _name
		);
	}

	@Override
	public String toString() {
		return toJSONObject().toString();
	}

	private String _id;
	private String _name;

}
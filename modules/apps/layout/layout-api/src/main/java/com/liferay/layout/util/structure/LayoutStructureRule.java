/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.util.structure;

import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Objects;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutStructureRule {

	public static LayoutStructureRule of(JSONObject jsonObject) {
		return new LayoutStructureRule(
			jsonObject.getString("id"), jsonObject.getString("name"));
	}

	public LayoutStructureRule(String id, String name) {
		_id = id;
		_name = name;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LayoutStructureRule)) {
			return false;
		}

		LayoutStructureRule layoutStructureRule = (LayoutStructureRule)object;

		if (Objects.equals(_id, layoutStructureRule._id) &&
			Objects.equals(_name, layoutStructureRule._name)) {

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
		return HashUtil.hash(0, getId());
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public JSONObject toJSONObject() {
		return JSONUtil.put(
			"id", getId()
		).put(
			"name", getName()
		);
	}

	@Override
	public String toString() {
		JSONObject jsonObject = toJSONObject();

		return jsonObject.toString();
	}

	private String _id;
	private String _name;

}
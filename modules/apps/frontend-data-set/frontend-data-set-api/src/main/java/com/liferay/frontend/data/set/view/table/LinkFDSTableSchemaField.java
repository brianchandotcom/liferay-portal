/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.view.table;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Kevin Tan
 */
public class LinkFDSTableSchemaField extends FDSTableSchemaField {

	public String getDecoration() {
		return _decoration;
	}

	public String getDisplayType() {
		return _displayType;
	}

	public void setDecoration(String decoration) {
		_decoration = decoration;
	}

	public void setDisplayType(String displayType) {
		_displayType = displayType;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = super.toJSONObject();

		return jsonObject.put(
			"decoration", getDecoration()
		).put(
			"displayType", getDisplayType()
		);
	}

	private String _decoration;
	private String _displayType;

}
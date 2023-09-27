/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.view.table;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Guilherme Camacho
 */
public class BaseDateFDSTableSchemaField extends FDSTableSchemaField {

	public JSONObject getFormat() {
		return _formatJSONObject;
	}

	public void setFormat(JSONObject formatJSONObject) {
		_formatJSONObject = formatJSONObject;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = super.toJSONObject();

		return jsonObject.put("format", getFormat());
	}

	private JSONObject _formatJSONObject;

}
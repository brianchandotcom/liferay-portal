/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.view.table;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Marcela Cunha
 */
public class StringFDSTableSchemaField extends FDSTableSchemaField {

	public boolean isTruncate() {
		return _truncate;
	}

	public void setTruncate(boolean truncate) {
		_truncate = truncate;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = super.toJSONObject();

		return jsonObject.put("truncate", isTruncate());
	}

	private boolean _truncate;

}
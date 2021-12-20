/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.frontend.data.set.view.table;

import com.liferay.portal.kernel.json.JSONObject;

/**
 * @author Marcela Cunha
 */
public class ClobFDSTableSchemaField extends FDSTableSchemaField {

	public boolean isTruncate() {
		return _truncate;
	}

	public void setTruncate(boolean truncate) {
		_truncate = truncate;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject fdsTableSchemaFieldJSONObject = super.toJSONObject();

		return fdsTableSchemaFieldJSONObject.put("truncate", isTruncate());
	}

	private boolean _truncate;

}
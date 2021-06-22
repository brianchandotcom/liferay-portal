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

package com.liferay.json.storage.model.impl;

import com.liferay.json.storage.constants.JSONStorageEntryConstants;
import com.liferay.petra.string.StringPool;

/**
 * @author Preston Crary
 */
public class JSONStorageEntryImpl extends JSONStorageEntryBaseImpl {

	@Override
	public Object getValue() {
		int type = getType();

		if (type == JSONStorageEntryConstants.TYPE_VALUE_LONG) {
			return getValueLong();
		}

		if (type == JSONStorageEntryConstants.TYPE_VALUE_LONG_QUOTED) {
			return String.valueOf(getValueLong());
		}

		if (type == JSONStorageEntryConstants.TYPE_VALUE_STRING) {
			return getValueString();
		}

		return null;
	}

	@Override
	public void setValue(Object value) {
		int type = getType();

		if ((type == JSONStorageEntryConstants.TYPE_VALUE_LONG) ||
			(type == JSONStorageEntryConstants.TYPE_VALUE_LONG_QUOTED)) {

			setValueLong((Long)value);
		}
		else {
			setValueLong(0);
		}

		if (type == JSONStorageEntryConstants.TYPE_VALUE_STRING) {
			setValueString((String)value);
		}
		else {
			setValueString(StringPool.BLANK);
		}
	}

}
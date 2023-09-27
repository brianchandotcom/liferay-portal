/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
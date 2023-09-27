/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.model.impl;

import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;

/**
 * @author Leonardo Barros
 */
public class DDLFormRecordImpl implements DDLFormRecord {

	public DDLFormRecordImpl(DDLRecord record) {
		_record = record;
	}

	@Override
	public DDLRecord getDDLRecord() {
		return _record;
	}

	private final DDLRecord _record;

}
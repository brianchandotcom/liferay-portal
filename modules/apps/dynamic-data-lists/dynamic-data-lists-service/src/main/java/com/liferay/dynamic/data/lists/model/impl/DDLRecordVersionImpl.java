/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.lists.model.impl;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DDLRecordVersionImpl extends DDLRecordVersionBaseImpl {

	@Override
	public DDMFormValues getDDMFormValues() throws StorageException {
		return DDLRecordLocalServiceUtil.getDDMFormValues(getDDMStorageId());
	}

	@Override
	public DDLRecord getRecord() throws PortalException {
		return DDLRecordLocalServiceUtil.getRecord(getRecordId());
	}

	@Override
	public DDLRecordSet getRecordSet() throws PortalException {
		return DDLRecordSetLocalServiceUtil.getRecordSet(getRecordSetId());
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Brian Wing Shun Chan
 */
public class DDMFormInstanceRecordImpl extends DDMFormInstanceRecordBaseImpl {

	@Override
	public DDMFormValues getDDMFormValues() throws PortalException {
		DDMFormInstanceRecordVersion latestFormInstanceRecordVersion =
			getLatestFormInstanceRecordVersion();

		return latestFormInstanceRecordVersion.getDDMFormValues();
	}

	@Override
	public DDMFormInstance getFormInstance() throws PortalException {
		return DDMFormInstanceLocalServiceUtil.getFormInstance(
			getFormInstanceId());
	}

	@Override
	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion()
		throws PortalException {

		return getFormInstanceRecordVersion(getVersion());
	}

	@Override
	public DDMFormInstanceRecordVersion getFormInstanceRecordVersion(
			String version)
		throws PortalException {

		return DDMFormInstanceRecordVersionLocalServiceUtil.
			getFormInstanceRecordVersion(getFormInstanceRecordId(), version);
	}

	@Override
	public DDMFormInstanceRecordVersion getLatestFormInstanceRecordVersion()
		throws PortalException {

		return DDMFormInstanceRecordVersionLocalServiceUtil.
			getLatestFormInstanceRecordVersion(getFormInstanceRecordId());
	}

	@Override
	public int getStatus() throws PortalException {
		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			getFormInstanceRecordVersion();

		return ddmFormInstanceRecordVersion.getStatus();
	}

	@Override
	public String getStorageType() throws PortalException {
		DDMFormInstance ddmFormInstance = getFormInstance();

		return ddmFormInstance.getStorageType();
	}

}
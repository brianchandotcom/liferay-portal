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

}
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

package com.liferay.portal.reports.engine.console.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.reports.engine.console.service.permission.ReportsActionKeys;
import com.liferay.portal.reports.engine.constants.ReportsEngineDestinationNames;

/**
 * @author Brian Wing Shun Chan
 * @author Gavin Wan
 */
@ProviderType
public class EntryImpl extends EntryBaseImpl {

	public EntryImpl() {
	}

	@Override
	public String getAttachmentsDir() {
		return "reports/".concat(String.valueOf(getEntryId()));
	}

	@Override
	public String[] getAttachmentsFiles() throws PortalException {
		return DLStoreUtil.getFileNames(
			getCompanyId(), CompanyConstants.SYSTEM, getAttachmentsDir());
	}

	@Override
	public String getJobName() {
		return ReportsActionKeys.ADD_REPORT.concat(
			String.valueOf(getEntryId()));
	}

	@Override
	public TZSRecurrence getRecurrenceObj() {
		return (TZSRecurrence)JSONFactoryUtil.deserialize(getRecurrence());
	}

	@Override
	public String getSchedulerRequestName() {
		return ReportsEngineDestinationNames.REPORT_REQUEST.concat(
			StringPool.SLASH).concat(String.valueOf(getEntryId()));
	}

}
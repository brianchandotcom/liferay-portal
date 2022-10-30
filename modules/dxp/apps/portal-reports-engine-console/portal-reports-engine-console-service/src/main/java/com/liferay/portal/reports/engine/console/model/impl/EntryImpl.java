/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.model.impl;

import com.liferay.document.library.kernel.store.DLStoreUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.reports.engine.console.internal.constants.ReportsEngineDestinationNames;
import com.liferay.portal.reports.engine.console.service.permission.ReportsActionKeys;

/**
 * @author Brian Wing Shun Chan
 * @author Gavin Wan
 */
public class EntryImpl extends EntryBaseImpl {

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
		return StringBundler.concat(
			ReportsEngineDestinationNames.REPORT_REQUEST, StringPool.SLASH,
			getEntryId());
	}

}
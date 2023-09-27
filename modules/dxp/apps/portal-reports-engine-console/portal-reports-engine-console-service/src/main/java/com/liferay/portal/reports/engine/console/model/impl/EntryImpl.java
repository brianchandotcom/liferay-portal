/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.model.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cal.TZSRecurrence;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.reports.engine.console.internal.constants.ReportsEngineDestinationNames;
import com.liferay.portal.reports.engine.console.service.EntryLocalServiceUtil;
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
	public String[] getAttachmentsFileNames() throws PortalException {
		return EntryLocalServiceUtil.getAttachmentsFileNames(this);
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
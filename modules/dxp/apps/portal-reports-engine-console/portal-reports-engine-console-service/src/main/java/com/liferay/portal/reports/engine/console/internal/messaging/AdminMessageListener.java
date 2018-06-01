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

package com.liferay.portal.reports.engine.console.internal.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.reports.engine.ReportGenerationException;
import com.liferay.portal.reports.engine.ReportResultContainer;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;
import com.liferay.portal.reports.engine.console.status.ReportStatus;

/**
 * @author Gavin Wan
 */
public class AdminMessageListener extends BaseMessageListener {

	public AdminMessageListener(EntryLocalService entryLocalService) {
		_entryLocalService = entryLocalService;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		ReportResultContainer reportResultContainer =
			(ReportResultContainer)message.getPayload();

		long entryId = GetterUtil.getLong(message.getResponseId());

		if (reportResultContainer.hasError()) {
			ReportGenerationException reportGenerationException =
				reportResultContainer.getReportGenerationException();

			_entryLocalService.updateEntryStatus(
				entryId, ReportStatus.ERROR,
				reportGenerationException.getMessage());
		}
		else {
			_entryLocalService.updateEntry(
				entryId, reportResultContainer.getReportName(),
				reportResultContainer.getResults());
		}
	}

	private final EntryLocalService _entryLocalService;

}
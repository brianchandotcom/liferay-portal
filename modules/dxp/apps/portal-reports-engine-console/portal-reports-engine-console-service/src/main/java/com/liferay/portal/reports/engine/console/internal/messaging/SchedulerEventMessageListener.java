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
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.reports.engine.console.internal.constants.ReportsEngineConsoleDestinationNames;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gavin Wan
 */
@Component(
	property = "destination.name=" + ReportsEngineConsoleDestinationNames.REPORTS_SCHEDULER_EVENT,
	service = MessageListener.class
)
public class SchedulerEventMessageListener extends BaseMessageListener {

	@Override
	protected void doReceive(Message message) throws Exception {
		long entryId = message.getLong("entryId");
		String reportName = message.getString("reportName");

		_entryLocalService.generateReport(entryId, reportName);
	}

	@Reference
	private EntryLocalService _entryLocalService;

}
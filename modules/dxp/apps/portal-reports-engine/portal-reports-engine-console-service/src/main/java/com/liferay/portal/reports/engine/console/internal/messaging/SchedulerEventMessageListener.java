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

package com.liferay.portal.reports.engine.console.internal.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.reports.engine.console.service.EntryLocalService;

/**
 * @author Gavin Wan
 */
public class SchedulerEventMessageListener extends BaseMessageListener {

	public SchedulerEventMessageListener(EntryLocalService entryLocalService) {
		_entryLocalService = entryLocalService;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		long entryId = message.getLong("entryId");
		String reportName = message.getString("reportName");

		_entryLocalService.generateReport(entryId, reportName);
	}

	private final EntryLocalService _entryLocalService;

}
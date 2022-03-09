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

package com.liferay.analytics.message.sender.internal.helper;

import com.liferay.analytics.message.sender.helper.AnalyticsDXPEntityDispatchTriggerHelper;
import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.executor.DispatchTaskClusterMode;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.service.UserLocalService;

import java.time.LocalDateTime;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(
	immediate = true, service = AnalyticsDXPEntityDispatchTriggerHelper.class
)
public class AnalyticsDXPEntityDispatchTriggerHelperImpl
	implements AnalyticsDXPEntityDispatchTriggerHelper {

	@Override
	public void createDispatchTriggersIfNotExist(long companyId)
		throws Exception {

		for (String dispatchTriggerName : _DISPATCH_TRIGGER_NAMES) {
			DispatchTrigger dispatchTrigger =
				_dispatchTriggerLocalService.fetchDispatchTrigger(
					companyId, dispatchTriggerName);

			if (dispatchTrigger == null) {
				dispatchTrigger =
					_dispatchTriggerLocalService.addDispatchTrigger(
						_userLocalService.getDefaultUserId(companyId),
						dispatchTriggerName, null, dispatchTriggerName, false);

				LocalDateTime localDateTime = LocalDateTime.now();

				_dispatchTriggerLocalService.updateDispatchTrigger(
					dispatchTrigger.getDispatchTriggerId(), true,
					_CRON_EXPRESSION, DispatchTaskClusterMode.NOT_APPLICABLE, 0,
					0, 0, 0, 0, true, false, localDateTime.getMonthValue(),
					localDateTime.getDayOfMonth(), localDateTime.getYear(),
					localDateTime.getHour(), localDateTime.getMinute());
			}
		}
	}

	@Override
	public void syncNow(long companyId) throws Exception {
		for (String dispatchTriggerName : _DISPATCH_TRIGGER_NAMES) {
			DispatchTrigger dispatchTrigger =
				_dispatchTriggerLocalService.fetchDispatchTrigger(
					companyId, dispatchTriggerName);

			if (dispatchTrigger == null) {
				return;
			}

			Message message = new Message();

			message.setPayload(
				JSONUtil.put(
					"dispatchTriggerId", dispatchTrigger.getDispatchTriggerId()
				).toString());

			_destination.send(message);
		}
	}

	private static final String _CRON_EXPRESSION = "0 * * * *";

	private static final String[] _DISPATCH_TRIGGER_NAMES = {
		"upload-expando-column-analytics-dxp-entities",
		"upload-group-analytics-dxp-entities",
		"upload-organization-analytics-dxp-entities",
		"upload-role-analytics-dxp-entities",
		"upload-team-analytics-dxp-entities",
		"upload-user-analytics-dxp-entities",
		"upload-user-group-analytics-dxp-entities"
	};

	@Reference(
		target = "(destination.name=" + DispatchConstants.EXECUTOR_DESTINATION_NAME + ")"
	)
	private Destination _destination;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
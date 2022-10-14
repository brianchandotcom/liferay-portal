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

package com.liferay.analytics.batch.exportimport.internal;

import com.liferay.analytics.batch.exportimport.AnalyticsDXPEntityBatchExporter;
import com.liferay.dispatch.constants.DispatchConstants;
import com.liferay.dispatch.executor.DispatchTaskClusterMode;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.service.UserLocalService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(service = AnalyticsDXPEntityBatchExporter.class)
public class AnalyticsDXPEntityBatchExporterImpl
	implements AnalyticsDXPEntityBatchExporter {

	@Override
	public void export(long companyId) throws Exception {
		for (String dispatchTriggerName : _DISPATCH_TRIGGER_NAMES) {
			DispatchTrigger dispatchTrigger =
				_dispatchTriggerLocalService.fetchDispatchTrigger(
					companyId, dispatchTriggerName);

			if (dispatchTrigger == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find dispatch trigger with name " +
							dispatchTriggerName);
				}

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

	@Override
	public void refreshExportTrigger(long companyId, String dispatchTriggerName)
		throws Exception {

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.fetchDispatchTrigger(
				companyId, dispatchTriggerName);

		if (dispatchTrigger == null) {
			return;
		}

		Date nextFireDate = dispatchTrigger.getNextFireDate();

		Instant instant = nextFireDate.toInstant();

		ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));

		_dispatchTriggerLocalService.deleteDispatchTrigger(dispatchTrigger);

		_addDispatchTrigger(
			companyId, dispatchTriggerName, zonedDateTime.toLocalDateTime());
	}

	@Override
	public void scheduleExportTriggers(long companyId) throws Exception {
		for (String dispatchTriggerName : _DISPATCH_TRIGGER_NAMES) {
			DispatchTrigger dispatchTrigger =
				_dispatchTriggerLocalService.fetchDispatchTrigger(
					companyId, dispatchTriggerName);

			if (dispatchTrigger != null) {
				continue;
			}

			_addDispatchTrigger(
				companyId, dispatchTriggerName, LocalDateTime.now());
		}
	}

	@Override
	public void unscheduleExportTriggers(long companyId) throws Exception {
		for (String dispatchTriggerName : _DISPATCH_TRIGGER_NAMES) {
			DispatchTrigger dispatchTrigger =
				_dispatchTriggerLocalService.fetchDispatchTrigger(
					companyId, dispatchTriggerName);

			if (dispatchTrigger == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find dispatch trigger with name " +
							dispatchTriggerName);
				}

				continue;
			}

			_dispatchTriggerLocalService.deleteDispatchTrigger(dispatchTrigger);
		}
	}

	private DispatchTrigger _addDispatchTrigger(
			long companyId, String dispatchTriggerName,
			LocalDateTime localDateTime)
		throws Exception {

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.addDispatchTrigger(
				null, _userLocalService.getDefaultUserId(companyId),
				dispatchTriggerName, null, dispatchTriggerName, false);

		return _dispatchTriggerLocalService.updateDispatchTrigger(
			dispatchTrigger.getDispatchTriggerId(), true, _CRON_EXPRESSION,
			DispatchTaskClusterMode.NOT_APPLICABLE, 0, 0, 0, 0, 0, true, false,
			localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth(),
			localDateTime.getYear(), localDateTime.getHour(),
			localDateTime.getMinute(), "UTC");
	}

	private static final String _CRON_EXPRESSION = "0 0 * * * ?";

	private static final String[] _DISPATCH_TRIGGER_NAMES = {
		"export-account-entry-analytics-dxp-entities",
		"export-account-group-analytics-dxp-entities",
		"export-analytics-association-analytics-dxp-entities",
		"export-analytics-delete-message-analytics-dxp-entities",
		"export-expando-column-analytics-dxp-entities",
		"export-group-analytics-dxp-entities",
		"export-organization-analytics-dxp-entities",
		"export-role-analytics-dxp-entities",
		"export-team-analytics-dxp-entities",
		"export-user-analytics-dxp-entities",
		"export-user-group-analytics-dxp-entities"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		AnalyticsDXPEntityBatchExporterImpl.class);

	@Reference(
		target = "(destination.name=" + DispatchConstants.EXECUTOR_DESTINATION_NAME + ")"
	)
	private Destination _destination;

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
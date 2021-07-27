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

package com.liferay.portal.security.sso.openid.connect.internal.scheduler;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration;
import com.liferay.portal.security.sso.openid.connect.constants.OpenIdConnectConstants;
import com.liferay.portal.security.sso.openid.connect.internal.constants.OpenIdConnectDestinationNames;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	configurationPid = "com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = OpenIdConnectTokenRefreshScheduler.class
)
public class OpenIdConnectTokenRefreshScheduler {

	public void reschedule(
			long lifeTime, Date refreshDate, long openIdConnectSessionId)
		throws SchedulerException {

		if (_tokenRefreshOffset > lifeTime) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The configured token refresh offset is larger than " +
						"newly issued access token life time");
			}

			return;
		}

		long startTimeOffset = lifeTime - _tokenRefreshOffset;

		SchedulerResponse schedulerResponse =
			_schedulerEngineHelper.getScheduledJob(
				String.valueOf(openIdConnectSessionId),
				OpenIdConnectConstants.SERVICE_NAME,
				StorageType.MEMORY_CLUSTERED);

		Trigger trigger = schedulerResponse.getTrigger();

		Date startDate = trigger.getStartDate();

		startDate.setTime(refreshDate.getTime() + (startTimeOffset * 1000));

		_schedulerEngineHelper.update(trigger, StorageType.MEMORY_CLUSTERED);
	}

	public void schedule(
			long lifeTime, Date refreshDate, long openIdConnectSessionId)
		throws SchedulerException {

		if (_tokenRefreshOffset > lifeTime) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"The configured token refresh offset is larger than " +
						"newly issued access token life time");
			}

			return;
		}

		long startTimeOffset = lifeTime - _tokenRefreshOffset;

		Trigger trigger = _triggerFactory.createTrigger(
			String.valueOf(openIdConnectSessionId),
			OpenIdConnectConstants.SERVICE_NAME,
			new Date(refreshDate.getTime() + (startTimeOffset * 1000)), null,
			(int)lifeTime, TimeUnit.SECOND);

		_schedulerEngineHelper.schedule(
			trigger, StorageType.MEMORY_CLUSTERED, null,
			OpenIdConnectDestinationNames.OPENID_CONNECT_TOKEN_REFRESH,
			openIdConnectSessionId, 0);
	}

	public void unschedule(long openIdConnectSessionId)
		throws SchedulerException {

		_schedulerEngineHelper.delete(
			String.valueOf(openIdConnectSessionId),
			OpenIdConnectConstants.SERVICE_NAME, StorageType.MEMORY_CLUSTERED);
	}

	@Activate
	protected void activate(Map<String, Object> properties) throws Exception {
		modified(properties);
	}

	@Deactivate
	protected void deactivate() throws Exception {
		_schedulerEngineHelper.delete(
			OpenIdConnectConstants.SERVICE_NAME, StorageType.MEMORY_CLUSTERED);
	}

	@Modified
	protected void modified(Map<String, Object> properties) throws Exception {
		OpenIdConnectConfiguration openIdConnectConfiguration =
			ConfigurableUtil.createConfigurable(
				OpenIdConnectConfiguration.class, properties);

		if (openIdConnectConfiguration.tokenRefreshOffset() < 30) {
			throw new IllegalArgumentException(
				"Token refresh offset needs at least 30 seconds");
		}

		if (_tokenRefreshOffset ==
				openIdConnectConfiguration.tokenRefreshOffset()) {

			return;
		}

		// Offset is changed, reschedule every openIdConnect token refresh.
		// For each token refresh, access token lifetime is not changed.

		for (SchedulerResponse schedulerResponse :
				_schedulerEngineHelper.getScheduledJobs(
					OpenIdConnectConstants.SERVICE_NAME,
					StorageType.MEMORY_CLUSTERED)) {

			Trigger trigger = schedulerResponse.getTrigger();

			Date startDate = trigger.getStartDate();

			Date nextFireDate = trigger.getFireDateAfter(new Date());

			long startTimeOffset =
				_tokenRefreshOffset -
					openIdConnectConfiguration.tokenRefreshOffset();

			startDate.setTime(
				nextFireDate.getTime() + (startTimeOffset * 1000));

			_schedulerEngineHelper.update(
				trigger, StorageType.MEMORY_CLUSTERED);
		}

		_tokenRefreshOffset = openIdConnectConfiguration.tokenRefreshOffset();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OpenIdConnectTokenRefreshScheduler.class);

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private volatile int _tokenRefreshOffset = 30;

	@Reference
	private TriggerFactory _triggerFactory;

}
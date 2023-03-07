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

package com.liferay.analytics.settings.internal.messaging;

import com.liferay.analytics.message.sender.client.AnalyticsMessageSenderClient;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = SchedulerJobConfiguration.class)
public class CheckAnalyticsConnectionsMessageListener
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () -> {
			Map<Long, AnalyticsConfiguration> analyticsConfigurations =
				_analyticsConfigurationRegistry.getAnalyticsConfigurations();

			if (analyticsConfigurations.isEmpty()) {
				return;
			}

			for (Map.Entry<Long, AnalyticsConfiguration>
					analyticsConfigurationEntry :
						analyticsConfigurations.entrySet()) {

				try {
					_analyticsMessageSenderClient.validateConnection(
						analyticsConfigurationEntry.getKey());
				}
				catch (Exception exception) {
					_log.error(
						"Unable to connect Analytics Cloud for company " +
							analyticsConfigurationEntry.getKey(),
						exception);

					throw exception;
				}
			}
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			15, TimeUnit.MINUTE);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckAnalyticsConnectionsMessageListener.class);

	@Reference
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

	@Reference
	private AnalyticsMessageSenderClient _analyticsMessageSenderClient;

}
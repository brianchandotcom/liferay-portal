/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.settings.internal.scheduler;

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
public class CheckAnalyticsConnectionsSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
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
		CheckAnalyticsConnectionsSchedulerJobConfiguration.class);

	@Reference
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

	@Reference
	private AnalyticsMessageSenderClient _analyticsMessageSenderClient;

}
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

package com.liferay.analytics.message.sender.internal.scheduler;

import com.liferay.analytics.message.sender.internal.messaging.AnalyticsMessagesHelper;
import com.liferay.analytics.settings.configuration.AnalyticsConfigurationRegistry;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.service.CompanyLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = SchedulerJobConfiguration.class)
public class SendAnalyticsMessagesSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	public UnsafeConsumer<Long, Exception> getCompanyJobExecutor() {
		return companyId -> {
			if (_isDisabled()) {
				return;
			}

			_analyticsMessagesHelper.send(companyId);
		};
	}

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () -> {
			if (_isDisabled()) {
				return;
			}

			_companyLocalService.forEachCompanyId(
				companyId -> _analyticsMessagesHelper.send(companyId));
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			1, TimeUnit.HOUR);
	}

	private boolean _isDisabled() {
		if (FeatureFlagManagerUtil.isEnabled("LRAC-10632") ||
			!_analyticsConfigurationRegistry.isActive()) {

			return true;
		}

		return false;
	}

	@Reference
	private AnalyticsConfigurationRegistry _analyticsConfigurationRegistry;

	@Reference
	private AnalyticsMessagesHelper _analyticsMessagesHelper;

	@Reference
	private CompanyLocalService _companyLocalService;

}
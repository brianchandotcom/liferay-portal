/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	@Override
	public UnsafeConsumer<Long, Exception>
		getCompanyJobExecutorUnsafeConsumer() {

		return companyId -> {
			if (_isDisabled()) {
				return;
			}

			_analyticsMessagesHelper.send(companyId);
		};
	}

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
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
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.scheduler;

import com.liferay.commerce.configuration.CommerceSubscriptionConfiguration;
import com.liferay.commerce.service.CommerceSubscriptionEntryLocalService;
import com.liferay.commerce.subscription.CommerceSubscriptionEntryHelper;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	configurationPid = "com.liferay.commerce.configuration.CommerceSubscriptionConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckCommerceSubscriptionEntrySchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			try {
				_commerceSubscriptionEntryHelper.checkSubscriptionEntriesStatus(
					_commerceSubscriptionEntryLocalService.
						getCommerceSubscriptionEntriesToRenew());
			}
			catch (Exception exception) {
				_log.error(exception);
			}

			try {
				_commerceSubscriptionEntryHelper.
					checkDeliverySubscriptionEntriesStatus(
						_commerceSubscriptionEntryLocalService.
							getCommerceDeliverySubscriptionEntriesToRenew());
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_commerceSubscriptionConfiguration.renewalCheckIntervalMinutes(),
			TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_commerceSubscriptionConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceSubscriptionConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckCommerceSubscriptionEntrySchedulerJobConfiguration.class);

	private CommerceSubscriptionConfiguration
		_commerceSubscriptionConfiguration;

	@Reference
	private CommerceSubscriptionEntryHelper _commerceSubscriptionEntryHelper;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

}
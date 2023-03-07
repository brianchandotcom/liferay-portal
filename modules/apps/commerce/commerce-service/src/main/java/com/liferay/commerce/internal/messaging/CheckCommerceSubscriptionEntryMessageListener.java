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

package com.liferay.commerce.internal.messaging;

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
public class CheckCommerceSubscriptionEntryMessageListener
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
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
		CheckCommerceSubscriptionEntryMessageListener.class);

	private CommerceSubscriptionConfiguration
		_commerceSubscriptionConfiguration;

	@Reference
	private CommerceSubscriptionEntryHelper _commerceSubscriptionEntryHelper;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

}
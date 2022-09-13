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
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerEntry;
import com.liferay.portal.kernel.scheduler.SchedulerEntryImpl;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.scheduler.TriggerFactory;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	configurationPid = "com.liferay.commerce.configuration.CommerceSubscriptionConfiguration",
	enabled = false, immediate = true,
	service = CheckCommerceSubscriptionEntryMessageListener.class
)
public class CheckCommerceSubscriptionEntryMessageListener
	extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		CommerceSubscriptionConfiguration commerceSubscriptionConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceSubscriptionConfiguration.class, properties);

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null,
			commerceSubscriptionConfiguration.renewalCheckIntervalMinutes(),
			TimeUnit.MINUTE);

		SchedulerEntry schedulerEntry = new SchedulerEntryImpl(
			className, trigger);

		_schedulerEngineHelper.register(
			this, schedulerEntry, DestinationNames.SCHEDULER_DISPATCH);
	}

	@Deactivate
	protected void deactivate() {
		_schedulerEngineHelper.unregister(this);
	}

	@Override
	protected void doReceive(Message message) throws Exception {
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
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckCommerceSubscriptionEntryMessageListener.class);

	@Reference
	private CommerceSubscriptionEntryHelper _commerceSubscriptionEntryHelper;

	@Reference
	private CommerceSubscriptionEntryLocalService
		_commerceSubscriptionEntryLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}
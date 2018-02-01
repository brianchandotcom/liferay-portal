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

package com.liferay.commerce.currency.web.internal.messaging;

import com.liferay.commerce.currency.service.CommerceCurrencyLocalService;
import com.liferay.commerce.currency.util.ExchangeRateProviderRegistry;
import com.liferay.commerce.currency.web.internal.configuration.ExchangeRateProviderGroupServiceConfiguration;
import com.liferay.commerce.currency.web.internal.constants.CommerceCurrencyExchangeRateConstants;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = CommerceCurrencyExchangeRateConstants.SERVICE_NAME,
	immediate = true, service = UpdateExchangeRateMessageListener.class
)
public class UpdateExchangeRateMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(Map<String, Object> properties) {
		Class<?> clazz = getClass();

		String className = clazz.getName();

		_exchangeRateProviderGroupServiceConfiguration =
			ConfigurableUtil.createConfigurable(
				ExchangeRateProviderGroupServiceConfiguration.class,
				properties);

		Trigger trigger = _triggerFactory.createTrigger(
			className, className, null, null,
			_exchangeRateProviderGroupServiceConfiguration.updateInterval(),
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
		if (!_exchangeRateProviderGroupServiceConfiguration.autoUpdate()) {
			return;
		}

		String exchangeRateProviderKey =
			_exchangeRateProviderGroupServiceConfiguration.
				defaultExchangeRateProviderKey();

		_commerceCurrencyLocalService.updateExchangeRates(
			_exchangeRateProviderGroupServiceConfiguration.groupId(),
			exchangeRateProviderKey);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	@Reference
	private CommerceCurrencyLocalService _commerceCurrencyLocalService;

	private ExchangeRateProviderGroupServiceConfiguration
		_exchangeRateProviderGroupServiceConfiguration;

	@Reference
	private ExchangeRateProviderRegistry _exchangeRateProviderRegistry;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	@Reference
	private TriggerFactory _triggerFactory;

}
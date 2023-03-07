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

import com.liferay.commerce.configuration.CommerceOrderTypeConfiguration;
import com.liferay.commerce.service.CommerceOrderTypeLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.configuration.CommerceOrderTypeConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckCommerceOrderTypeMessageListener
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return _commerceOrderTypeLocalService::checkCommerceOrderTypes;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_commerceOrderTypeConfiguration.checkInterval(), TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_commerceOrderTypeConfiguration = ConfigurableUtil.createConfigurable(
			CommerceOrderTypeConfiguration.class, properties);
	}

	private CommerceOrderTypeConfiguration _commerceOrderTypeConfiguration;

	@Reference
	private CommerceOrderTypeLocalService _commerceOrderTypeLocalService;

}
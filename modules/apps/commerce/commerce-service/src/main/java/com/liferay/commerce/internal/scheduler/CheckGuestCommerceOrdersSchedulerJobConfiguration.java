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

package com.liferay.commerce.internal.scheduler;

import com.liferay.account.constants.AccountConstants;
import com.liferay.commerce.configuration.CommerceOrderConfiguration;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.util.Time;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.configuration.CommerceOrderConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckGuestCommerceOrdersSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			int deleteInterval = _commerceOrderConfiguration.deleteInterval();

			Date createDate = new Date(
				System.currentTimeMillis() - (deleteInterval * Time.MINUTE));

			_commerceOrderLocalService.deleteCommerceOrdersByAccountId(
				AccountConstants.ACCOUNT_ENTRY_ID_GUEST, createDate,
				CommerceOrderConstants.ORDER_STATUS_OPEN);
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_commerceOrderConfiguration.checkInterval(), TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_commerceOrderConfiguration = ConfigurableUtil.createConfigurable(
			CommerceOrderConfiguration.class, properties);
	}

	private volatile CommerceOrderConfiguration _commerceOrderConfiguration;

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

}
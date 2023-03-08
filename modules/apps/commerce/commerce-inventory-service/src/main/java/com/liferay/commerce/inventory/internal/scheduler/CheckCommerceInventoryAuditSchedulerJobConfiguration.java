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

package com.liferay.commerce.inventory.internal.scheduler;

import com.liferay.commerce.inventory.configuration.CommerceInventorySystemConfiguration;
import com.liferay.commerce.inventory.service.CommerceInventoryAuditLocalService;
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
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.inventory.configuration.CommerceInventorySystemConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckCommerceInventoryAuditSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () -> {
			int deleteAuditMonthInterval =
				_commerceInventorySystemConfiguration.
					deleteAuditMonthInterval();

			Date date = new Date(
				System.currentTimeMillis() -
					(deleteAuditMonthInterval * Time.MONTH));

			_commerceInventoryAuditLocalService.checkCommerceInventoryAudit(
				date);
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_commerceInventorySystemConfiguration.
				checkCommerceInventoryAuditQuantityInterval(),
			TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_commerceInventorySystemConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceInventorySystemConfiguration.class, properties);
	}

	@Reference
	private CommerceInventoryAuditLocalService
		_commerceInventoryAuditLocalService;

	private CommerceInventorySystemConfiguration
		_commerceInventorySystemConfiguration;

}
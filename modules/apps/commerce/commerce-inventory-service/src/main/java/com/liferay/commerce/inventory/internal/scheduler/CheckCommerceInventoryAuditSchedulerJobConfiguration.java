/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
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
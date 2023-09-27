/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.scheduler;

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
public class CheckCommerceOrderTypeSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
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
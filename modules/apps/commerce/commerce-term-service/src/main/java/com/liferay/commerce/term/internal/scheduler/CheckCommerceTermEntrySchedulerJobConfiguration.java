/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.internal.scheduler;

import com.liferay.commerce.term.configuration.CommerceTermEntryConfiguration;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
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
	configurationPid = "com.liferay.commerce.term.configuration.CommerceTermEntryConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckCommerceTermEntrySchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return _commerceTermEntryLocalService::checkCommerceTermEntries;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_commerceTermEntryConfiguration.checkInterval(), TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_commerceTermEntryConfiguration = ConfigurableUtil.createConfigurable(
			CommerceTermEntryConfiguration.class, properties);
	}

	private CommerceTermEntryConfiguration _commerceTermEntryConfiguration;

	@Reference
	private CommerceTermEntryLocalService _commerceTermEntryLocalService;

}
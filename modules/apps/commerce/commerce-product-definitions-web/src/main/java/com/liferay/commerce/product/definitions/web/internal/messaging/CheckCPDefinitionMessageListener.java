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

package com.liferay.commerce.product.definitions.web.internal.messaging;

import com.liferay.commerce.product.definitions.web.internal.configuration.CPDefinitionConfiguration;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
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
 * @author Guy Wandji
 * @author Andrea Di Giorgi
 */
@Component(
	configurationPid = "com.liferay.commerce.product.definitions.web.internal.configuration.CPDefinitionConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckCPDefinitionMessageListener
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return _cpDefinitionLocalService::checkCPDefinitions;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_cpDefinitionConfiguration.checkInterval(), TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_cpDefinitionConfiguration = ConfigurableUtil.createConfigurable(
			CPDefinitionConfiguration.class, properties);
	}

	private CPDefinitionConfiguration _cpDefinitionConfiguration;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

}
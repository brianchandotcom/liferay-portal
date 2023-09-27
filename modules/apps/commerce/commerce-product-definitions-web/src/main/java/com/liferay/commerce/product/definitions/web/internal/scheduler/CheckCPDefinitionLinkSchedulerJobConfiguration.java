/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.definitions.web.internal.scheduler;

import com.liferay.commerce.product.definitions.web.internal.configuration.CPDefinitionLinkConfiguration;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalService;
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
	configurationPid = "com.liferay.commerce.product.definitions.web.internal.configuration.CPDefinitionLinkConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckCPDefinitionLinkSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return _cpDefinitionLinkLocalService::checkCPDefinitionLinks;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_cpDefinitionLinkConfiguration.checkInterval(), TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_cpDefinitionLinkConfiguration = ConfigurableUtil.createConfigurable(
			CPDefinitionLinkConfiguration.class, properties);
	}

	private CPDefinitionLinkConfiguration _cpDefinitionLinkConfiguration;

	@Reference
	private CPDefinitionLinkLocalService _cpDefinitionLinkLocalService;

}
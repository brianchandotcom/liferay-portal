/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.scheduler;

import com.liferay.message.boards.configuration.MBConfiguration;
import com.liferay.message.boards.service.MBBanLocalService;
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
 * @author Michael Young
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.message.boards.configuration.MBConfiguration",
	service = SchedulerJobConfiguration.class
)
public class ExpireBanSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return _mbBanLocalService::expireBans;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return _triggerConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		MBConfiguration mbConfiguration = ConfigurableUtil.createConfigurable(
			MBConfiguration.class, properties);

		_triggerConfiguration = TriggerConfiguration.createTriggerConfiguration(
			mbConfiguration.expireBanJobInterval(), TimeUnit.MINUTE);
	}

	@Reference
	private MBBanLocalService _mbBanLocalService;

	private TriggerConfiguration _triggerConfiguration;

}
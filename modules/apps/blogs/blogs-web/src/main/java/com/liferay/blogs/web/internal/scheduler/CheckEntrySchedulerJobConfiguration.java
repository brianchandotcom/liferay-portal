/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.scheduler;

import com.liferay.blogs.configuration.BlogsConfiguration;
import com.liferay.blogs.service.BlogsEntryLocalService;
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
 * @author Zsolt Berentey
 */
@Component(
	configurationPid = "com.liferay.blogs.configuration.BlogsConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckEntrySchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return _blogsEntryLocalService::checkEntries;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return _triggerConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		BlogsConfiguration blogsConfiguration =
			ConfigurableUtil.createConfigurable(
				BlogsConfiguration.class, properties);

		_triggerConfiguration = TriggerConfiguration.createTriggerConfiguration(
			blogsConfiguration.entryCheckInterval(), TimeUnit.MINUTE);
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	private TriggerConfiguration _triggerConfiguration;

}
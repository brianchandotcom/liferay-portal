/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.web.internal.scheduler;

import com.liferay.blogs.configuration.BlogsConfiguration;
import com.liferay.blogs.linkback.LinkbackConsumer;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.linkback.LinkbackProducerUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 * @author Tina Tian
 */
@Component(
	configurationPid = "com.liferay.blogs.configuration.BlogsConfiguration",
	service = SchedulerJobConfiguration.class
)
public class LinkbackSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			_linkbackConsumer.verifyNewTrackbacks();

			LinkbackProducerUtil.sendQueuedPingbacks();
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_blogsConfiguration.linkbackJobInterval(), TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_blogsConfiguration = ConfigurableUtil.createConfigurable(
			BlogsConfiguration.class, properties);
	}

	private BlogsConfiguration _blogsConfiguration;

	@Reference
	private LinkbackConsumer _linkbackConsumer;

}
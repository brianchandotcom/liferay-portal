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

package com.liferay.asset.publisher.web.internal.messaging;

import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.publisher.web.internal.messaging.helper.AssetEntriesCheckerHelper;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the ability for a scheduled task to send email notifications when
 * new asset entries are added to an Asset Publisher portlet instance that has
 * subscribers. The scheduled task uses the <code>check.interval</code> property
 * to define the execution interval (in hours).
 *
 * @author Roberto Díaz
 * @author Sergio González
 */
@Component(
	configurationPid = "com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CheckAssetEntryMessageListener
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return _assetEntriesCheckerUtil::checkAssetEntries;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		String checkCronExpression =
			_assetPublisherWebConfiguration.checkCronExpression();

		if (Validator.isNotNull(checkCronExpression)) {
			try {
				_triggerFactory.createTrigger(
					getName(), getName(), null, null, checkCronExpression);

				return TriggerConfiguration.createTriggerConfiguration(
					checkCronExpression);
			}
			catch (RuntimeException runtimeException) {
				if (_log.isWarnEnabled()) {
					_log.warn(runtimeException);
				}
			}
		}

		return TriggerConfiguration.createTriggerConfiguration(
			_assetPublisherWebConfiguration.checkInterval(), TimeUnit.HOUR);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_assetPublisherWebConfiguration = ConfigurableUtil.createConfigurable(
			AssetPublisherWebConfiguration.class, properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CheckAssetEntryMessageListener.class);

	@Reference
	private AssetEntriesCheckerHelper _assetEntriesCheckerUtil;

	private AssetPublisherWebConfiguration _assetPublisherWebConfiguration;

	@Reference
	private TriggerFactory _triggerFactory;

}
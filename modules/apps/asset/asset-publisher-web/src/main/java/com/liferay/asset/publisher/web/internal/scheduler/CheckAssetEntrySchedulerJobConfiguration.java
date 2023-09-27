/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.publisher.web.internal.scheduler;

import com.liferay.asset.publisher.web.internal.configuration.AssetPublisherWebConfiguration;
import com.liferay.asset.publisher.web.internal.scheduler.helper.AssetEntriesCheckerHelper;
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
public class CheckAssetEntrySchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
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
		CheckAssetEntrySchedulerJobConfiguration.class);

	@Reference
	private AssetEntriesCheckerHelper _assetEntriesCheckerUtil;

	private AssetPublisherWebConfiguration _assetPublisherWebConfiguration;

	@Reference
	private TriggerFactory _triggerFactory;

}
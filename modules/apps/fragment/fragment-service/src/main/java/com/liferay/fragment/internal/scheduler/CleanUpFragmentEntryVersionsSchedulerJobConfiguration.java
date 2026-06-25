/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.scheduler;

import com.liferay.fragment.configuration.FragmentEntryVersionConfiguration;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.petra.function.UnsafeConsumer;
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
 * @author Georgel Pop
 */
@Component(
	configurationPid = "com.liferay.fragment.configuration.FragmentEntryVersionConfiguration",
	service = SchedulerJobConfiguration.class
)
public class CleanUpFragmentEntryVersionsSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeConsumer<Long, Exception>
		getCompanyJobExecutorUnsafeConsumer() {

		return _fragmentEntryLocalService::cleanUpFragmentEntryVersions;
	}

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return _fragmentEntryLocalService::cleanUpFragmentEntryVersions;
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return _triggerConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_fragmentEntryVersionConfiguration =
			ConfigurableUtil.createConfigurable(
				FragmentEntryVersionConfiguration.class, properties);

		_triggerConfiguration = _getTriggerConfiguration();
	}

	private TriggerConfiguration _getTriggerConfiguration() {
		String cleanUpCronExpression =
			_fragmentEntryVersionConfiguration.cleanUpCronExpression();

		if (Validator.isNotNull(cleanUpCronExpression)) {
			try {
				_triggerFactory.createTrigger(
					getName(), getName(), null, null, cleanUpCronExpression);

				return TriggerConfiguration.createTriggerConfiguration(
					cleanUpCronExpression);
			}
			catch (RuntimeException runtimeException) {
				if (_log.isWarnEnabled()) {
					_log.warn(runtimeException);
				}
			}
		}

		return TriggerConfiguration.createTriggerConfiguration(1, TimeUnit.DAY);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CleanUpFragmentEntryVersionsSchedulerJobConfiguration.class);

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	private FragmentEntryVersionConfiguration
		_fragmentEntryVersionConfiguration;
	private TriggerConfiguration _triggerConfiguration;

	@Reference
	private TriggerFactory _triggerFactory;

}
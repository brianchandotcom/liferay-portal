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

package com.liferay.portal.scheduler.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.scheduler.internal.configuration.SchedulerEngineHelperConfiguration;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Michael C. Han
 * @author Alberto Chaparro
 */
public class SchedulerEngineHelperConfigurationUpgradeProcess
	extends UpgradeProcess {

	public SchedulerEngineHelperConfigurationUpgradeProcess(
		ConfigurationAdmin configurationAdmin, Props props) {

		_configurationAdmin = configurationAdmin;
		_props = props;
	}

	@Override
	public void doUpgrade() throws Exception {
		String audiMessageScheduleJobString = _props.get(
			_LEGACY_AUDIT_MESSAGE_SCHEDULER_JOB);

		if (Validator.isNull(audiMessageScheduleJobString)) {
			return;
		}

		Configuration configuration = _configurationAdmin.getConfiguration(
			SchedulerEngineHelperConfiguration.class.getName(),
			StringPool.QUESTION);

		configuration.update(
			HashMapDictionaryBuilder.<String, Object>put(
				_AUDIT_SCHEDULER_JOB_ENABLED,
				GetterUtil.getBoolean(audiMessageScheduleJobString)
			).build());
	}

	private static final String _AUDIT_SCHEDULER_JOB_ENABLED =
		"auditSchedulerJobEnabled";

	private static final String _LEGACY_AUDIT_MESSAGE_SCHEDULER_JOB =
		"audit.message.scheduler.job";

	private final ConfigurationAdmin _configurationAdmin;
	private final Props _props;

}
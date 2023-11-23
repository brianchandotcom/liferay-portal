/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.internal.scheduler;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.lang.ThreadContextClassLoaderUtil;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.saml.persistence.service.SamlSpAuthRequestLocalService;
import com.liferay.saml.runtime.configuration.SamlConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Christopher Kian
 */
@Component(
	configurationPid = "com.liferay.saml.runtime.configuration.SamlConfiguration",
	service = SchedulerJobConfiguration.class
)
public class SamlSpAuthRequestSchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			try (SafeCloseable safeCloseable =
					ThreadContextClassLoaderUtil.swap(
						SamlSpAuthRequestSchedulerJobConfiguration.class.
							getClassLoader())) {

				_samlSpAuthRequestLocalService.
					deleteExpiredSamlSpAuthRequests();
			}
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return _triggerConfiguration;
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		SamlConfiguration samlConfiguration =
			ConfigurableUtil.createConfigurable(
				SamlConfiguration.class, properties);

		_triggerConfiguration = TriggerConfiguration.createTriggerConfiguration(
			samlConfiguration.getSpAuthRequestCheckInterval(), TimeUnit.MINUTE);
	}

	@Reference
	private SamlSpAuthRequestLocalService _samlSpAuthRequestLocalService;

	private TriggerConfiguration _triggerConfiguration;

}
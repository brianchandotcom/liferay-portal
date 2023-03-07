/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.internal.messaging;

import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.saml.persistence.service.SamlIdpSsoSessionLocalService;
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
public class SamlIdpSsoSessionMessageListener
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutor() {
		return () -> {
			Thread currentThread = Thread.currentThread();

			ClassLoader classLoader = currentThread.getContextClassLoader();

			try {
				currentThread.setContextClassLoader(
					SamlIdpSsoSessionMessageListener.class.getClassLoader());

				_samlIdpSsoSessionLocalService.
					deleteExpiredSamlIdpSsoSessions();
			}
			finally {
				currentThread.setContextClassLoader(classLoader);
			}
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			_samlConfiguration.getIdpSsoSessionCheckInterval(),
			TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_samlConfiguration = ConfigurableUtil.createConfigurable(
			SamlConfiguration.class, properties);
	}

	private SamlConfiguration _samlConfiguration;

	@Reference
	private SamlIdpSsoSessionLocalService _samlIdpSsoSessionLocalService;

}
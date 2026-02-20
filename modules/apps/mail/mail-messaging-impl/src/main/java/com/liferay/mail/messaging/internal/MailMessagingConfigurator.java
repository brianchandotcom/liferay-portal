/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.mail.messaging.internal;

import com.liferay.mail.settings.configuration.MailSettingSystemConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dante Wang
 */
@Component(
	configurationPid = "com.liferay.mail.settings.configuration.MailSettingSystemConfiguration",
	service = {}
)
public class MailMessagingConfigurator {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_mailSettingSystemConfiguration = ConfigurableUtil.createConfigurable(
			MailSettingSystemConfiguration.class, properties);

		_serviceRegistration = _registerDestination(
			bundleContext, _mailSettingSystemConfiguration);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Modified
	protected void modified(
		BundleContext bundleContext, Map<String, Object> properties) {

		MailSettingSystemConfiguration mailSettingSystemConfiguration =
			ConfigurableUtil.createConfigurable(
				MailSettingSystemConfiguration.class, properties);

		if ((mailSettingSystemConfiguration.workersCoreSize() ==
				_mailSettingSystemConfiguration.workersCoreSize()) &&
			(mailSettingSystemConfiguration.workersMaxSize() ==
				_mailSettingSystemConfiguration.workersMaxSize())) {

			return;
		}

		_serviceRegistration.unregister();

		_serviceRegistration = _registerDestination(
			bundleContext, mailSettingSystemConfiguration);

		_mailSettingSystemConfiguration = mailSettingSystemConfiguration;
	}

	private ServiceRegistration<Destination> _registerDestination(
		BundleContext bundleContext,
		MailSettingSystemConfiguration mailSettingSystemConfiguration) {

		DestinationConfiguration destinationConfiguration =
			DestinationConfiguration.createParallelDestinationConfiguration(
				DestinationNames.MAIL);

		destinationConfiguration.setWorkersCoreSize(
			mailSettingSystemConfiguration.workersCoreSize());
		destinationConfiguration.setWorkersMaxSize(
			mailSettingSystemConfiguration.workersMaxSize());

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		return bundleContext.registerService(
			Destination.class, destination,
			HashMapDictionaryBuilder.put(
				"destination.name", destination.getName()
			).build());
	}

	@Reference
	private DestinationFactory _destinationFactory;

	private volatile MailSettingSystemConfiguration
		_mailSettingSystemConfiguration;
	private volatile ServiceRegistration<Destination> _serviceRegistration;

}
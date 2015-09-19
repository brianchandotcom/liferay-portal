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

package com.liferay.portal.ldap.messaging;

import aQute.bnd.annotation.component.Activate;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.scheduler.IntervalTrigger;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.Trigger;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.ldap.configuration.LDAPConfiguration;
import com.liferay.portal.ldap.settings.LDAPConfigurationSettingsUtil;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.ldap.configuration.LDAPConfiguration",
	immediate = true
)
public class MessagingConfigurator {

	@Activate
	protected void activate(ComponentContext componentContext)
		throws Exception {

		BundleContext bundleContext = componentContext.getBundleContext();

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SERIAL,
				DestinationNames.SCHEDULED_USER_LDAP_IMPORT);

		_serviceRegistration = bundleContext.registerService(
			DestinationConfiguration.class, destinationConfiguration,
			new HashMapDictionary<String, Object>());

		LDAPConfiguration ldapConfiguration =
			_ldapConfigurationSettingsUtil.getLDAPConfiguration();

		try {
			_schedulerEngineHelper.unschedule(
				UserImportMessageListener.class.getName(),
				UserImportMessageListener.class.getName(),
				StorageType.MEMORY_CLUSTERED);
		}
		catch (SchedulerException e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to unschedule " +
						UserImportMessageListener.class.getName(),
					e);
			}
		}

		int interval = ldapConfiguration.importInterval();

		Trigger trigger = new IntervalTrigger(
			UserImportMessageListener.class.getName(),
			UserImportMessageListener.class.getName(), interval,
			TimeUnit.MINUTE);

		_schedulerEngineHelper.schedule(
			trigger, StorageType.MEMORY_CLUSTERED,
			UserImportMessageListener.class.getName(),
			DestinationNames.SCHEDULED_USER_LDAP_IMPORT, null, 5);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}

		_serviceRegistration = null;

		try {
			_schedulerEngineHelper.unschedule(
				UserImportMessageListener.class.getName(),
				UserImportMessageListener.class.getName(),
				StorageType.MEMORY_CLUSTERED);
		}
		catch (SchedulerException e) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to unschedule " +
						UserImportMessageListener.class.getName(),
					e);
			}
		}
	}

	@Reference(unbind = "-")
	protected void setLdapConfigurationSettingsUtil(
		LDAPConfigurationSettingsUtil ldapConfigurationSettingsUtil) {

		_ldapConfigurationSettingsUtil = ldapConfigurationSettingsUtil;
	}

	@Reference(unbind = "-")
	protected void setSchedulerEngineHelper(
		SchedulerEngineHelper schedulerEngineHelper) {

		_schedulerEngineHelper = schedulerEngineHelper;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MessagingConfigurator.class);

	private LDAPConfigurationSettingsUtil _ldapConfigurationSettingsUtil;
	private SchedulerEngineHelper _schedulerEngineHelper;
	private ServiceRegistration<DestinationConfiguration> _serviceRegistration;

}
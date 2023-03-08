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

package com.liferay.notification.internal.instance.lifecycle;

import com.liferay.notification.internal.configuration.NotificationQueueConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.Collections;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedServiceFactory;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Murilo Stodolni
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class NotificationQueuePortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Registered portal instance " + company);
		}

		_updateComponentInstance(
			company.getCompanyId(),
			_configurationProvider.getCompanyConfiguration(
				NotificationQueueConfiguration.class, company.getCompanyId()));
	}

	@Override
	public void portalInstanceUnregistered(Company company) {
		ComponentInstance<?> componentInstance = _componentInstances.remove(
			company.getCompanyId());

		if (componentInstance != null) {
			componentInstance.dispose();
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			ManagedServiceFactory.class,
			new NotificationQueueConfigurationManagedServiceFactory(),
			HashMapDictionaryBuilder.<String, Object>put(
				Constants.SERVICE_PID,
				"com.liferay.notification.internal.configuration." +
					"NotificationQueueConfiguration.scoped"
			).build());
	}

	@Deactivate
	protected void deactivate() {
		for (ComponentInstance<?> componentInstance :
				_componentInstances.values()) {

			componentInstance.dispose();
		}

		_serviceRegistration.unregister();
	}

	private void _updateComponentInstance(
		long companyId,
		NotificationQueueConfiguration notificationQueueConfiguration) {

		_componentInstances.compute(
			companyId,
			(key, value) -> {
				if (value != null) {
					value.dispose();
				}

				return _componentFactory.newInstance(
					HashMapDictionaryBuilder.<String, Object>put(
						"companyId", companyId
					).put(
						"configuration", notificationQueueConfiguration
					).build());
			});
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationQueuePortalInstanceLifecycleListener.class);

	@Reference(
		target = "(component.factory=com.liferay.notification.internal.scheduler.CheckNotificationQueueEntrySchedulerJobConfiguration)"
	)
	private ComponentFactory<?> _componentFactory;

	private final Map<Long, ComponentInstance<?>> _componentInstances =
		new ConcurrentHashMap<>();

	@Reference
	private ConfigurationProvider _configurationProvider;

	private ServiceRegistration<ManagedServiceFactory> _serviceRegistration;

	private class NotificationQueueConfigurationManagedServiceFactory
		implements ManagedServiceFactory {

		@Override
		public void deleted(String pid) {
			Long companyId = _companyIds.remove(pid);

			if (companyId == null) {
				return;
			}

			_updateComponentInstance(
				companyId, _defaultNotificationQueueConfiguration);
		}

		@Override
		public String getName() {
			return NotificationQueueConfigurationManagedServiceFactory.class.
				getName();
		}

		@Override
		public void updated(String pid, Dictionary<String, ?> dictionary) {
			long companyId = GetterUtil.getLong(dictionary.get("companyId"));

			_companyIds.put(pid, companyId);

			_updateComponentInstance(
				companyId,
				ConfigurableUtil.createConfigurable(
					NotificationQueueConfiguration.class, dictionary));
		}

		private NotificationQueueConfigurationManagedServiceFactory() {
			_defaultNotificationQueueConfiguration =
				ConfigurableUtil.createConfigurable(
					NotificationQueueConfiguration.class,
					Collections.emptyMap());
		}

		private final Map<String, Long> _companyIds = new ConcurrentHashMap<>();
		private final NotificationQueueConfiguration
			_defaultNotificationQueueConfiguration;

	}

}
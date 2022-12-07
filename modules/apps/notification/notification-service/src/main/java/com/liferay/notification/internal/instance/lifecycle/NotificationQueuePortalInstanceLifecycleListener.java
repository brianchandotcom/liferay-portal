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

import com.liferay.notification.constants.NotificationConstants;
import com.liferay.notification.internal.messaging.CheckNotificationQueueEntryMessageListener;
import com.liferay.notification.service.NotificationQueueEntryLocalService;
import com.liferay.notification.type.NotificationTypeServiceTracker;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.TriggerFactory;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
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
	public void portalInstanceRegistered(Company company) {
		if (_log.isDebugEnabled()) {
			_log.debug("Registered portal instance " + company);
		}

		long companyId = company.getCompanyId();

		_serviceRegistrations.computeIfAbsent(
			companyId,
			key -> _bundleContext.registerService(
				MessageListener.class,
				new CheckNotificationQueueEntryMessageListener(
					companyId, _configurationProvider,
					_notificationQueueEntryLocalService,
					_notificationTypeServiceTracker, _schedulerEngineHelper,
					_triggerFactory),
				HashMapDictionaryBuilder.<String, Object>put(
					"model.class.name", NotificationConstants.RESOURCE_NAME
				).build()));
	}

	@Override
	public void portalInstanceUnregistered(Company company) {
		long companyId = company.getCompanyId();

		_unregisterService(companyId, _serviceRegistrations.get(companyId));

		_serviceRegistrations.remove(companyId);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistrations.forEach(this::_unregisterService);

		_serviceRegistrations.clear();
	}

	private String _getClassName(long companyId) {
		return StringBundler.concat(
			CheckNotificationQueueEntryMessageListener.class.getName(),
			StringPool.POUND, companyId);
	}

	private void _unregisterService(
		long companyId, ServiceRegistration<?> serviceRegistration) {

		_schedulerEngineHelper.unregister(_getClassName(companyId));

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NotificationQueuePortalInstanceLifecycleListener.class);

	private BundleContext _bundleContext;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private NotificationQueueEntryLocalService
		_notificationQueueEntryLocalService;

	@Reference
	private NotificationTypeServiceTracker _notificationTypeServiceTracker;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private final Map<Long, ServiceRegistration<?>> _serviceRegistrations =
		new HashMap<>();

	@Reference
	private TriggerFactory _triggerFactory;

}
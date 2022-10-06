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

package com.liferay.notification.internal.type;

import com.liferay.notification.type.LegacyNotificationType;
import com.liferay.notification.util.LegacyNotificationTypeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Gustavo Lima
 */
@Component(service = LegacyNotificationTypeRegistry.class)
public class LegacyNotificationTypeRegistryImpl
	implements LegacyNotificationTypeRegistry {

	@Override
	public LegacyNotificationType getNotificationType(String key) {
		ServiceWrapper<LegacyNotificationType> notificationTypeServiceWrapper =
			_serviceTrackerMap.getService(key);

		if (notificationTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("No notification type registered with key " + key);
			}

			return null;
		}

		return notificationTypeServiceWrapper.getService();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, LegacyNotificationType.class,
			"notification.type.key",
			ServiceTrackerCustomizerFactory.
				<LegacyNotificationType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LegacyNotificationTypeRegistryImpl.class);

	private ServiceTrackerMap<String, ServiceWrapper<LegacyNotificationType>>
		_serviceTrackerMap;

}
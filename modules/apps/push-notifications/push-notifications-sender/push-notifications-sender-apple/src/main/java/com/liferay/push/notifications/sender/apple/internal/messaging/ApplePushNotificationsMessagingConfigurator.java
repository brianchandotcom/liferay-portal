/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.push.notifications.sender.apple.internal.messaging;

import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.push.notifications.constants.PushNotificationsDestinationNames;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Bruno Farache
 */
@Component(
	enabled = false, service = ApplePushNotificationsMessagingConfigurator.class
)
public class ApplePushNotificationsMessagingConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			MessageListener.class,
			new ApplePushNotificationsResponseMessageListener(),
			MapUtil.singletonDictionary(
				"destination.name",
				PushNotificationsDestinationNames.PUSH_NOTIFICATION_RESPONSE));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	private ServiceRegistration<MessageListener> _serviceRegistration;

}
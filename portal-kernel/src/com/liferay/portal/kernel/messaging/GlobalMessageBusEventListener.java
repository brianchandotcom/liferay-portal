/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Michael C. Han
 */
public class GlobalMessageBusEventListener implements MessageBusEventListener {

	@Override
	public void destinationAdded(Destination destination) {
		String destinationName = destination.getName();

		if (!_ignoredDestinations.contains(destinationName)) {
			BundleContext bundleContext = SystemBundleUtil.getBundleContext();

			_serviceRegistrationsMap.put(
				destinationName,
				bundleContext.registerService(
					MessageListener.class, _messageListener,
					MapUtil.singletonDictionary(
						"destination.name", destinationName)));
		}
	}

	@Override
	public void destinationRemoved(Destination destination) {
		String destinationName = destination.getName();

		if (!_ignoredDestinations.contains(destinationName)) {
			ServiceRegistration<MessageListener> serviceRegistration =
				_serviceRegistrationsMap.remove(destinationName);

			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	public void setIgnoredDestinations(List<String> ignoredDestinations) {
		_ignoredDestinations = SetUtil.fromList(ignoredDestinations);
	}

	public void setMessageListener(MessageListener messageListener) {
		_messageListener = messageListener;
	}

	private Set<String> _ignoredDestinations;
	private MessageListener _messageListener;
	private final Map<String, ServiceRegistration<MessageListener>>
		_serviceRegistrationsMap = new ConcurrentHashMap<>();

}
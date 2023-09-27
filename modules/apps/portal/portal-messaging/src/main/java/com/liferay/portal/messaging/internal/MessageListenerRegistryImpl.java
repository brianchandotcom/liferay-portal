/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.messaging.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.messaging.InvokerMessageListener;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerRegistry;

import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Dante Wang
 */
@Component(service = MessageListenerRegistry.class)
public class MessageListenerRegistryImpl implements MessageListenerRegistry {

	public List<MessageListener> getMessageListeners(String destinationName) {
		List<MessageListener> messageListeners =
			_messageListenerServiceTrackerMap.getService(destinationName);

		if (messageListeners == null) {
			return Collections.emptyList();
		}

		return messageListeners;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_messageListenerServiceTrackerMap =
			ServiceTrackerMapFactory.openMultiValueMap(
				bundleContext, MessageListener.class, "destination.name",
				new ServiceTrackerCustomizer
					<MessageListener, MessageListener>() {

					@Override
					public MessageListener addingService(
						ServiceReference<MessageListener> serviceReference) {

						return new InvokerMessageListener(
							bundleContext.getService(serviceReference));
					}

					@Override
					public void modifiedService(
						ServiceReference<MessageListener> serviceReference,
						MessageListener messageListener) {
					}

					@Override
					public void removedService(
						ServiceReference<MessageListener> serviceReference,
						MessageListener messageListener) {

						bundleContext.ungetService(serviceReference);
					}

				});
	}

	@Deactivate
	protected void deactivate() {
		_messageListenerServiceTrackerMap.close();
	}

	private ServiceTrackerMap<String, List<MessageListener>>
		_messageListenerServiceTrackerMap;

}
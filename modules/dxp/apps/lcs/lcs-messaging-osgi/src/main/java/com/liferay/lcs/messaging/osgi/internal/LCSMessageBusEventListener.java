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

package com.liferay.lcs.messaging.osgi.internal;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusEventListener;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Riccardo Ferrari
 */
@Component(immediate = true, service = MessageBusEventListener.class)
public class LCSMessageBusEventListener implements MessageBusEventListener {

	@Activate
	@Modified
	public void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_refreshMessageListeners();
	}

	@Deactivate
	public void deactivate() {
		Collection<Destination> destinations = _messageBus.getDestinations();

		for (Destination destination : destinations) {
			destinationRemoved(destination);
		}
	}

	@Override
	public void destinationAdded(Destination destination) {
		if (!_isOSBLCSDestination(destination.getName())) {
			return;
		}

		_registerMessageRouterListeners(destination);
	}

	@Override
	public void destinationRemoved(Destination destination) {
		for (MessageRouterMessageListener messageRouterMessageListener :
				_messageRouterMessageListeners) {

			destination.unregister(messageRouterMessageListener);
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "unsetMessageRouterMessageListener",
		updated = "setMessageRouterMessageListener"
	)
	public void setMessageRouterMessageListener(
		MessageRouterMessageListener messageRouterMessageListener,
		Map<String, Object> properties) {

		_messageRouterMessageListeners.add(messageRouterMessageListener);

		_refreshMessageListeners();
	}

	public void unsetMessageRouterMessageListener(
		MessageRouterMessageListener messageRouterMessageListener,
		Map<String, Object> properties) {

		_messageRouterMessageListeners.remove(messageRouterMessageListener);

		for (Destination destination : _messageBus.getDestinations()) {
			destination.unregister(messageRouterMessageListener);
		}
	}

	private boolean _isOSBLCSDestination(String destinationName) {
		try {
			Collection<ServiceReference<Destination>> serviceReferences =
				_bundleContext.getServiceReferences(
					Destination.class,
					"(destination.name=" + destinationName + ")");

			if (serviceReferences.size() > 1) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						destinationName + " has multiple service references");
				}
			}

			for (ServiceReference serviceReference : serviceReferences) {
				if (Boolean.TRUE.equals(
						serviceReference.getProperty(
							RemoteDestinationType.OSB_LCS))) {

					return true;
				}
			}
		}
		catch (InvalidSyntaxException ise) {
			_log.error(ise);
		}

		return false;
	}

	private void _refreshMessageListeners() {
		if (_messageBus == null) {
			return;
		}

		for (Destination destination : _messageBus.getDestinations()) {
			destinationAdded(destination);
		}
	}

	private void _registerMessageRouterListeners(Destination destination) {
		for (MessageRouterMessageListener messageRouterMessageListener :
				_messageRouterMessageListeners) {

			destination.register(messageRouterMessageListener);

			if (_log.isDebugEnabled()) {
				Class<?> clazz = messageRouterMessageListener.getClass();

				_log.debug(
					"Attached listener " + clazz.getName() + " to " +
						destination.getName());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LCSMessageBusEventListener.class);

	private BundleContext _bundleContext;

	@Reference
	private MessageBus _messageBus;

	private final List<MessageRouterMessageListener>
		_messageRouterMessageListeners = new CopyOnWriteArrayList<>();

}
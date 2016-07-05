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

package com.liferay.portal.kernel.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.sender.SingleDestinationMessageSenderFactoryUtil;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.security.pacl.permission.PortalMessageBusPermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

/**
 * @author Michael C. Han
 * @author Raymond Augé
 */
public class MessageBusUtil {

	public static void addDestination(Destination destination) {
		getMessageBus().addDestination(destination);
	}

	public static Message createResponseMessage(Message requestMessage) {
		Message responseMessage = new Message();

		responseMessage.setDestinationName(
			requestMessage.getResponseDestinationName());
		responseMessage.setResponseId(requestMessage.getResponseId());

		return responseMessage;
	}

	public static Message createResponseMessage(
		Message requestMessage, Object payload) {

		Message responseMessage = createResponseMessage(requestMessage);

		responseMessage.setPayload(payload);

		return responseMessage;
	}

	public static Destination getDestination(String destinationName) {
		return getMessageBus().getDestination(destinationName);
	}

	/**
	 * @deprecated As of 7.1.0, with no direct replacement.
	 */
	@Deprecated
	public static MessageBusUtil getInstance() {
		PortalRuntimePermission.checkGetBeanProperty(MessageBusUtil.class);

		return new MessageBusUtil();
	}

	public static MessageBus getMessageBus() {
		try {
			while (!_initialized && (_serviceTracker.getService() == null)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Waiting for a PortalExecutorManager");
				}

				Thread.sleep(500);
			}
		}
		catch (InterruptedException ie) {
			throw new IllegalStateException(
				"Unable to initialize MessageBusUtil", ie);
		}

		return _messageBus;
	}

	public static boolean hasMessageListener(String destination) {
		return getMessageBus().hasMessageListener(destination);
	}

	public static void registerMessageListener(
		String destinationName, MessageListener messageListener) {

		PortalMessageBusPermission.checkListen(destinationName);

		getMessageBus().registerMessageListener(
			destinationName, messageListener);
	}

	public static void removeDestination(String destinationName) {
		getMessageBus().removeDestination(destinationName);
	}

	public static void sendMessage(String destinationName, Message message) {
		PortalMessageBusPermission.checkSend(destinationName);

		getMessageBus().sendMessage(destinationName, message);
	}

	public static void sendMessage(String destinationName, Object payload) {
		PortalMessageBusPermission.checkSend(destinationName);

		Message message = new Message();

		message.setPayload(payload);

		getMessageBus().sendMessage(destinationName, message);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Message message)
		throws MessageBusException {

		PortalMessageBusPermission.checkSend(destinationName);

		SynchronousMessageSender synchronousMessageSender =
			SingleDestinationMessageSenderFactoryUtil.
				getSynchronousMessageSender(_synchronousMessageSenderMode);

		return synchronousMessageSender.send(destinationName, message);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Message message, long timeout)
		throws MessageBusException {

		PortalMessageBusPermission.checkSend(destinationName);

		SynchronousMessageSender synchronousMessageSender =
			SingleDestinationMessageSenderFactoryUtil.
				getSynchronousMessageSender(_synchronousMessageSenderMode);

		return synchronousMessageSender.send(destinationName, message, timeout);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Object payload)
		throws MessageBusException {

		return sendSynchronousMessage(destinationName, payload, null);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Object payload, long timeout)
		throws MessageBusException {

		return sendSynchronousMessage(destinationName, payload, null, timeout);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Object payload,
			String responseDestinationName)
		throws MessageBusException {

		PortalMessageBusPermission.checkSend(destinationName);

		Message message = new Message();

		message.setResponseDestinationName(responseDestinationName);
		message.setPayload(payload);

		return sendSynchronousMessage(destinationName, message);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Object payload,
			String responseDestinationName, long timeout)
		throws MessageBusException {

		PortalMessageBusPermission.checkSend(destinationName);

		Message message = new Message();

		message.setResponseDestinationName(responseDestinationName);
		message.setPayload(payload);

		return sendSynchronousMessage(destinationName, message, timeout);
	}

	public static void shutdown() {
		PortalRuntimePermission.checkGetBeanProperty(MessageBusUtil.class);

		getMessageBus().shutdown();
	}

	public static void shutdown(boolean force) {
		PortalRuntimePermission.checkGetBeanProperty(MessageBusUtil.class);

		getMessageBus().shutdown(force);
	}

	public static boolean unregisterMessageListener(
		String destinationName, MessageListener messageListener) {

		PortalMessageBusPermission.checkListen(destinationName);

		return getMessageBus().unregisterMessageListener(
			destinationName, messageListener);
	}

	public void setSynchronousMessageSenderMode(
		SynchronousMessageSender.Mode synchronousMessageSenderMode) {

		_synchronousMessageSenderMode = synchronousMessageSenderMode;
	}

	private static final Log _log = LogFactoryUtil.getLog(MessageBusUtil.class);

	private static final MessageBusUtil _instance = new MessageBusUtil();

	private static volatile boolean _initialized;
	private static final MessageBus _messageBus =
		ProxyFactory.newServiceTrackedInstance(MessageBus.class);
	private static final ServiceTracker<MessageBus, MessageBus> _serviceTracker;
	private static SynchronousMessageSender.Mode _synchronousMessageSenderMode;

	static {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			MessageBus.class, new MessageBusServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private static class MessageBusServiceTrackerCustomizer
		implements ServiceTrackerCustomizer<MessageBus, MessageBus> {

		@Override
		public MessageBus addingService(
			ServiceReference<MessageBus> serviceReference) {

			_initialized = true;

			return null;
		}

		@Override
		public void modifiedService(
			ServiceReference<MessageBus> serviceReference,
			MessageBus messageBus) {
		}

		@Override
		public void removedService(
			ServiceReference<MessageBus> serviceReference, MessageBus service) {
		}

	}

}
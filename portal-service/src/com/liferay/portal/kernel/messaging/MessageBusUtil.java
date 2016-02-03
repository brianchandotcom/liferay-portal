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
		getInstance()._addDestination(destination);
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
		return getInstance()._getDestination(destinationName);
	}

	public static MessageBusUtil getInstance() {
		PortalRuntimePermission.checkGetBeanProperty(MessageBusUtil.class);

		return _instance;
	}

	public static MessageBus getMessageBus() {
		return _instance._getMessageBus();
	}

	public static boolean hasMessageListener(String destination) {
		return getInstance()._hasMessageListener(destination);
	}

	public static void registerMessageListener(
		String destinationName, MessageListener messageListener) {

		getInstance()._registerMessageListener(
			destinationName, messageListener);
	}

	public static void removeDestination(String destinationName) {
		getInstance()._removeDestination(destinationName);
	}

	public static void sendMessage(String destinationName, Message message) {
		getInstance()._sendMessage(destinationName, message);
	}

	public static void sendMessage(String destinationName, Object payload) {
		getInstance()._sendMessage(destinationName, payload);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Message message)
		throws MessageBusException {

		return getInstance()._sendSynchronousMessage(destinationName, message);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Message message, long timeout)
		throws MessageBusException {

		return getInstance()._sendSynchronousMessage(
			destinationName, message, timeout);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Object payload)
		throws MessageBusException {

		return getInstance()._sendSynchronousMessage(
			destinationName, payload, null);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Object payload, long timeout)
		throws MessageBusException {

		return getInstance()._sendSynchronousMessage(
			destinationName, payload, null, timeout);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Object payload,
			String responseDestinationName)
		throws MessageBusException {

		return getInstance()._sendSynchronousMessage(
			destinationName, payload, responseDestinationName);
	}

	public static Object sendSynchronousMessage(
			String destinationName, Object payload,
			String responseDestinationName, long timeout)
		throws MessageBusException {

		return getInstance()._sendSynchronousMessage(
			destinationName, payload, responseDestinationName, timeout);
	}

	public static void shutdown() {
		getInstance()._shutdown();
	}

	public static void shutdown(boolean force) {
		getInstance()._shutdown(force);
	}

	public static boolean unregisterMessageListener(
		String destinationName, MessageListener messageListener) {

		PortalMessageBusPermission.checkListen(destinationName);

		return getInstance()._unregisterMessageListener(
			destinationName, messageListener);
	}

	public MessageBusUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			MessageBus.class, new MessageBusServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	public void setSynchronousMessageSenderMode(
		SynchronousMessageSender.Mode synchronousMessageSenderMode) {

		_synchronousMessageSenderMode = synchronousMessageSenderMode;
	}

	private void _addDestination(Destination destination) {
		_getMessageBus().addDestination(destination);
	}

	private Destination _getDestination(String destinationName) {
		return _getMessageBus().getDestination(destinationName);
	}

	private MessageBus _getMessageBus() {
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

	private boolean _hasMessageListener(String destinationName) {
		return _getMessageBus().hasMessageListener(destinationName);
	}

	private void _registerMessageListener(
		String destinationName, MessageListener messageListener) {

		PortalMessageBusPermission.checkListen(destinationName);

		_getMessageBus().registerMessageListener(
			destinationName, messageListener);
	}

	private void _removeDestination(String destinationName) {
		_getMessageBus().removeDestination(destinationName);
	}

	private void _sendMessage(String destinationName, Message message) {
		PortalMessageBusPermission.checkSend(destinationName);

		_getMessageBus().sendMessage(destinationName, message);
	}

	private void _sendMessage(String destinationName, Object payload) {
		PortalMessageBusPermission.checkSend(destinationName);

		Message message = new Message();

		message.setPayload(payload);

		_sendMessage(destinationName, message);
	}

	private Object _sendSynchronousMessage(
			String destinationName, Message message)
		throws MessageBusException {

		PortalMessageBusPermission.checkSend(destinationName);

		SynchronousMessageSender synchronousMessageSender =
			SingleDestinationMessageSenderFactoryUtil.
				getSynchronousMessageSender(_synchronousMessageSenderMode);

		return synchronousMessageSender.send(destinationName, message);
	}

	private Object _sendSynchronousMessage(
			String destinationName, Message message, long timeout)
		throws MessageBusException {

		PortalMessageBusPermission.checkSend(destinationName);

		SynchronousMessageSender synchronousMessageSender =
			SingleDestinationMessageSenderFactoryUtil.
				getSynchronousMessageSender(_synchronousMessageSenderMode);

		return synchronousMessageSender.send(destinationName, message, timeout);
	}

	private Object _sendSynchronousMessage(
			String destinationName, Object payload,
			String responseDestinationName)
		throws MessageBusException {

		PortalMessageBusPermission.checkSend(destinationName);

		Message message = new Message();

		message.setResponseDestinationName(responseDestinationName);
		message.setPayload(payload);

		return _sendSynchronousMessage(destinationName, message);
	}

	private Object _sendSynchronousMessage(
			String destinationName, Object payload,
			String responseDestinationName, long timeout)
		throws MessageBusException {

		PortalMessageBusPermission.checkSend(destinationName);

		Message message = new Message();

		message.setResponseDestinationName(responseDestinationName);
		message.setPayload(payload);

		return _sendSynchronousMessage(destinationName, message, timeout);
	}

	private void _shutdown() {
		PortalRuntimePermission.checkGetBeanProperty(MessageBusUtil.class);

		_getMessageBus().shutdown();
	}

	private void _shutdown(boolean force) {
		PortalRuntimePermission.checkGetBeanProperty(MessageBusUtil.class);

		_getMessageBus().shutdown(force);
	}

	private boolean _unregisterMessageListener(
		String destinationName, MessageListener messageListener) {

		return _getMessageBus().unregisterMessageListener(
			destinationName, messageListener);
	}

	private static final Log _log = LogFactoryUtil.getLog(MessageBusUtil.class);

	private static final MessageBusUtil _instance = new MessageBusUtil();

	private static SynchronousMessageSender.Mode _synchronousMessageSenderMode;

	private volatile boolean _initialized;
	private final MessageBus _messageBus =
		ProxyFactory.newServiceTrackedInstance(MessageBus.class);
	private final ServiceTracker<MessageBus, MessageBus> _serviceTracker;

	private class MessageBusServiceTrackerCustomizer
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
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.security.pacl.PACLConstants;
import com.liferay.portal.kernel.security.pacl.permission.PortalMessageBusPermission;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.security.Permission;

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

	public static MessageBusUtil getInstance() {
		PortalRuntimePermission.checkGetBeanProperty(MessageBusUtil.class);

		return _instance;
	}

	public static MessageBus getMessageBus() {
		return getInstance()._messageBus;
	}

	public static MessageSender getMessageSender() {
		return getInstance()._messageSender;
	}

	public static boolean hasMessageListener(String destination) {
		return getInstance()._hasMessageListener(destination);
	}

	public static void init(
		MessageBus messageBus, MessageSender messageSender,
		SynchronousMessageSender synchronousMessageSender) {

		getInstance()._init(
			messageBus, messageSender, synchronousMessageSender);
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

		return getInstance()._unregisterMessageListener(
			destinationName, messageListener);
	}

	private MessageBusUtil() {
	}

	private void _addDestination(Destination destination) {
		_messageBus.addDestination(destination);
	}

	private boolean _hasMessageListener(String destinationName) {
		return _messageBus.hasMessageListener(destinationName);
	}

	private void _init(
		MessageBus messageBus, MessageSender messageSender,
		SynchronousMessageSender synchronousMessageSender) {

		_messageBus = messageBus;
		_messageSender = messageSender;
		_synchronousMessageSender = synchronousMessageSender;
	}

	private void _registerMessageListener(
		String destinationName, MessageListener messageListener) {

		_messageBus.registerMessageListener(destinationName, messageListener);
	}

	private void _removeDestination(String destinationName) {
		_messageBus.removeDestination(destinationName);
	}

	private void _sendMessage(String destinationName, Message message) {
		SecurityManager securityManager = System.getSecurityManager();

		if (securityManager != null) {
			Permission permission = new PortalMessageBusPermission(
				PACLConstants.PORTAL_MESSAGE_BUS_PERMISSION_SEND,
				destinationName);

			securityManager.checkPermission(permission);
		}

		_messageBus.sendMessage(destinationName, message);
	}

	private void _sendMessage(String destinationName, Object payload) {
		Message message = new Message();

		message.setPayload(payload);

		_sendMessage(destinationName, message);
	}

	private Object _sendSynchronousMessage(
			String destinationName, Message message)
		throws MessageBusException {

		return _synchronousMessageSender.send(destinationName, message);
	}

	private Object _sendSynchronousMessage(
			String destinationName, Message message, long timeout)
		throws MessageBusException {

		return _synchronousMessageSender.send(
			destinationName, message, timeout);
	}

	private Object _sendSynchronousMessage(
			String destinationName, Object payload,
			String responseDestinationName)
		throws MessageBusException {

		Message message = new Message();

		message.setResponseDestinationName(responseDestinationName);
		message.setPayload(payload);

		return _sendSynchronousMessage(destinationName, message);
	}

	private Object _sendSynchronousMessage(
			String destinationName, Object payload,
			String responseDestinationName, long timeout)
		throws MessageBusException {

		Message message = new Message();

		message.setResponseDestinationName(responseDestinationName);
		message.setPayload(payload);

		return _sendSynchronousMessage(destinationName, message, timeout);
	}

	private void _shutdown() {
		_messageBus.shutdown();
	}

	private void _shutdown(boolean force) {
		_messageBus.shutdown(force);
	}

	private boolean _unregisterMessageListener(
		String destinationName, MessageListener messageListener) {

		return _messageBus.unregisterMessageListener(
			destinationName, messageListener);
	}

	private static MessageBusUtil _instance = new MessageBusUtil();

	private MessageBus _messageBus;
	private MessageSender _messageSender;
	private SynchronousMessageSender _synchronousMessageSender;

}
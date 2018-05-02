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

package com.liferay.portal.kernel.nio.intraband.messaging;

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.messaging.Message;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.nio.ByteBuffer;

import java.util.ArrayList;

/**
 * @author Shuyang Zhou
 */
public class MessageRoutingBag implements Externalizable {

	public static final String MESSAGE_ROUTING_BAG = "MESSAGE_ROUTING_BAG";

	public static MessageRoutingBag fromByteArray(byte[] data)
		throws ClassNotFoundException {

		MessageRoutingBag messageRoutingBag = new MessageRoutingBag();

		Deserializer deserializer = new Deserializer(ByteBuffer.wrap(data));

		messageRoutingBag._destinationName = deserializer.readString();
		messageRoutingBag._messageData = deserializer.readObject();
		messageRoutingBag._routingDowncast = deserializer.readBoolean();
		messageRoutingBag._routingTrace = deserializer.readObject();
		messageRoutingBag._synchronizedBridge = deserializer.readBoolean();

		return messageRoutingBag;
	}

	public MessageRoutingBag() {
	}

	public MessageRoutingBag(Message message, boolean synchronizedBridge) {
		_destinationName = message.getDestinationName();
		_message = message;
		_synchronizedBridge = synchronizedBridge;
	}

	public void appendRoutingId(String routingId) {
		_routingTrace.add(routingId);
	}

	public String getDestinationName() {
		return _destinationName;
	}

	public Message getMessage() throws ClassNotFoundException {
		if (_message == null) {
			_message = Message.fromByteArray(_messageData);

			_message.put(MESSAGE_ROUTING_BAG, this);

			_messageData = null;
		}

		return _message;
	}

	public byte[] getMessageData() {
		if (_messageData == null) {
			_message.remove(MESSAGE_ROUTING_BAG);

			try {
				_messageData = _message.toByteArray();
			}
			finally {
				_message.put(MESSAGE_ROUTING_BAG, this);

				_message = null;
			}
		}

		return _messageData;
	}

	public boolean isRoutingDowncast() {
		return _routingDowncast;
	}

	public boolean isSynchronizedBridge() {
		return _synchronizedBridge;
	}

	public boolean isVisited(String routingId) {
		return _routingTrace.contains(routingId);
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		_destinationName = objectInput.readUTF();
		_messageData = (byte[])objectInput.readObject();
		_routingDowncast = objectInput.readBoolean();
		_routingTrace = (ArrayList<String>)objectInput.readObject();
		_synchronizedBridge = objectInput.readBoolean();
	}

	public void setMessage(Message message) {
		_message = message;

		message.put(MESSAGE_ROUTING_BAG, this);
	}

	public void setRoutingDowncast(boolean routingDowncast) {
		_routingDowncast = routingDowncast;
	}

	public byte[] toByteArray() {
		Serializer serializer = new Serializer();

		serializer.writeString(_destinationName);
		serializer.writeObject(getMessageData());
		serializer.writeBoolean(_routingDowncast);
		serializer.writeObject(_routingTrace);
		serializer.writeBoolean(_synchronizedBridge);

		ByteBuffer byteBuffer = serializer.toByteBuffer();

		return byteBuffer.array();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeUTF(_destinationName);
		objectOutput.writeObject(getMessageData());
		objectOutput.writeBoolean(_routingDowncast);
		objectOutput.writeObject(_routingTrace);
		objectOutput.writeBoolean(_synchronizedBridge);
	}

	private String _destinationName;
	private Message _message;
	private byte[] _messageData;
	private boolean _routingDowncast;
	private ArrayList<String> _routingTrace = new ArrayList<>();
	private boolean _synchronizedBridge;

}
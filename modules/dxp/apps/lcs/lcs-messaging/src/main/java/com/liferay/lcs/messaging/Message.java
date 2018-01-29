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

package com.liferay.lcs.messaging;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.Serializable;

/**
 * Represents a Liferay Cloud Services Protocol message.
 *
 * @author  Miguel Pastor
 * @author  Ivica Cardic
 * @author  Igor Beslic
 * @version 2.1.1
 * @since   LCS 0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(
	include = JsonTypeInfo.As.PROPERTY, property = "class",
	use = JsonTypeInfo.Id.CLASS
)
public abstract class Message implements Serializable {

	public static final String PROTOCOL_VERSION_1_0 = "1.0";

	public static final String PROTOCOL_VERSION_1_1 = "1.1";

	public static final String PROTOCOL_VERSION_1_2 = "1.2";

	public static final String PROTOCOL_VERSION_1_3 = "1.3";

	public static final String PROTOCOL_VERSION_1_4 = "1.4";

	public static final String PROTOCOL_VERSION_1_5 = "1.5";

	public static final String PROTOCOL_VERSION_1_6 = "1.6";

	public static final String PROTOCOL_VERSION_1_7 = "1.7";

	public static final String PROTOCOL_VERSION_1_8 = "1.8";

	public static final String PROTOCOL_VERSION_CURRENT = PROTOCOL_VERSION_1_8;

	public static <T extends Message> T createMessage(String json) {
		return createMessage(json, Message.class);
	}

	@SuppressWarnings("unchecked")
	public static <T extends Message> T createMessage(
		String json, Class clazz) {

		try {
			return (T)_objectMapper.readValue(json, clazz);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}
	}

	/**
	 * Returns the message's creation time in milliseconds.
	 *
	 * @return the message's creation time in milliseconds
	 * @since  LCS 0.1
	 */
	public long getCreateTime() {
		return _createTime;
	}

	/**
	 * Returns the message's delivery tag.
	 *
	 * @return the message's delivery tag
	 * @see    #setDeliveryTag(long)
	 * @since  LCS 0.1
	 */
	@JsonIgnore
	public long getDeliveryTag() {
		return _deliveryTag;
	}

	public String getErrorMessage() {
		return _errorMessage;
	}

	public int getErrorStatus() {
		return _errorStatus;
	}

	/**
	 * Returns the message creator's cluster node key.
	 *
	 * @return the message creator's cluster node key
	 * @since  LCS 0.1
	 */
	public String getKey() {
		return _key;
	}

	/**
	 * Returns the message's protocol version.
	 *
	 * @return the message's protocol version
	 * @since  LCS 0.1
	 */
	public String getProtocolVersion() {
		return _protocolVersion;
	}

	/**
	 * Returns the message's queue name.
	 *
	 * @return the message's queue name
	 * @see    #setQueueName(String)
	 * @since  LCS 0.1
	 */
	@JsonIgnore
	public String getQueueName() {
		return _queueName;
	}

	@JsonIgnore
	public String getSignatureString() {
		StringBuilder sb = new StringBuilder();

		Class<?> messageClass = getClass();

		sb.append(messageClass.getName());

		sb.append(getCreateTime());
		sb.append(getKey());
		sb.append(getProtocolVersion());

		return sb.toString();
	}

	@JsonIgnore
	@SuppressWarnings("unchecked")
	public <T extends TransportMetadata> T getTransportMetadata() {
		return (T)_transportMetadata;
	}

	/**
	 * Sets the message's creation time in milliseconds.
	 *
	 * @param createTime the creation time in milliseconds
	 * @since LCS 0.1
	 */
	public void setCreateTime(long createTime) {
		_createTime = createTime;

		_toString = null;
	}

	/**
	 * Sets the message's delivery tag. The delivery tag is used by some message
	 * queue implementations to set the sequence number for the confirmed
	 * message.
	 *
	 * @param deliveryTag the message's delivery tag
	 * @since LCS 0.1
	 */
	public void setDeliveryTag(long deliveryTag) {
		_deliveryTag = deliveryTag;
	}

	public void setErrorMessage(String errorMessage) {
		_errorMessage = errorMessage;
	}

	public void setErrorStatus(int errorStatus) {
		_errorStatus = errorStatus;
	}

	/**
	 * Sets the message creator's cluster node key.
	 *
	 * @param key the message creator's cluster node key
	 * @since LCS 0.1
	 */
	public void setKey(String key) {
		_key = key;

		_toString = null;
	}

	/**
	 * Sets the message's protocol version.
	 *
	 * @param protocolVersion the message's protocol version
	 * @since LCS 0.1
	 */
	public void setProtocolVersion(String protocolVersion) {
		_protocolVersion = protocolVersion;
	}

	/**
	 * Sets the message's queue name. The queue name is associated with the
	 * underlying message queue implementation where the message was read from.
	 *
	 * @param queueName the message's queue name
	 * @since LCS 0.1
	 */
	public void setQueueName(String queueName) {
		_queueName = queueName;
	}

	public void setTransportMetadata(TransportMetadata transportMetadata) {
		_transportMetadata = transportMetadata;
	}

	public String toJSON() {
		try {
			return _objectMapper.writeValueAsString(this);
		}
		catch (JsonProcessingException jpe) {
			throw new RuntimeException(jpe);
		}
	}

	@Override
	public String toString() {
		if (_toString != null) {
			return _toString;
		}

		StringBuilder sb = new StringBuilder(11);

		sb.append("{className=");

		Class<?> clazz = getClass();

		sb.append(clazz.getName());

		sb.append(", createTime=");
		sb.append(_createTime);
		sb.append(", key=");
		sb.append(_key);

		sb.append("}");

		_toString = sb.toString();

		return _toString;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	static {
		_objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

		TypeFactory typeFactory = TypeFactory.defaultInstance();

		typeFactory = typeFactory.withClassLoader(
			Message.class.getClassLoader());

		_objectMapper.setTypeFactory(typeFactory);
	}

	private long _createTime = System.currentTimeMillis();
	private long _deliveryTag;
	private String _errorMessage;
	private int _errorStatus;
	private String _key;
	private String _protocolVersion = PROTOCOL_VERSION_CURRENT;
	private String _queueName;
	private String _toString;
	private TransportMetadata _transportMetadata;

}
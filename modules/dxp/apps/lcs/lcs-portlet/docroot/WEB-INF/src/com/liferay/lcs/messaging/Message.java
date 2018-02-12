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

import java.io.Serializable;

import java.util.Map;
import java.util.TreeMap;

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

	public static final String KEY_BUILD_NUMBER = "buildNumber";

	public static final String KEY_CLUSTER_EXECUTOR_ENABLED =
		"clusterExecutorEnabled";

	public static final String KEY_COMPANY_IDS_WEB_IDS = "companyIdsWebIds";

	public static final String KEY_DEREGISTER = "deregister";

	public static final String KEY_ERROR = "error";

	public static final String KEY_HANDSHAKE_EXPIRED_ERROR =
		"handshakeExpiredError";

	public static final String KEY_HASH_CODE = "hashCode";

	public static final String KEY_HEARTBEAT_INTERVAL = "heartbeatInterval";

	public static final String KEY_LATEST_LCS_PORTLET_BUILD_NUMBER =
		"latestLCSPortletBuildNumber";

	public static final String KEY_LCS_PORTLET_BUILD_NUMBER =
		"lcsPortletBuildNumber";

	public static final String KEY_METRICS_LCS_SERVICE_STATUS =
		"metricsLCSServiceEnabled";

	public static final String KEY_MONITORING_STATUS = "monitoringStatus";

	public static final String KEY_PATCHES_LCS_SERVICE_STATUS =
		"patchesLCSServiceEnabled";

	public static final String KEY_PATCHING_TOOL_STATUS = "patchingToolStatus";

	public static final String KEY_PATCHING_TOOL_VERSION =
		"patchingToolVersion";

	public static final String KEY_PORTAL_EDITION = "portalEdition";

	public static final String KEY_PORTAL_PROPERTIES_LCS_SERVICE_STATUS =
		"portalPropertiesLCSServiceEnabled";

	public static final String KEY_PUBLIC_KEY_CERTIFICATE = "pkCertificate";

	public static final String KEY_RESPONSE = "response";

	public static final String KEY_SERVER_MANUALLY_SHUTDOWN =
		"serverManuallyShutdown";

	public static final String KEY_SERVER_UNEXPECTEDLY_SHUTDOWN =
		"serverUnexpectedlyShutdown";

	public static final String KEY_SIGN_OFF = "signOff";

	public static final String KEY_SIGNATURE = "signature";

	public static final String KEY_SITE_NAMES_LCS_SERVICE_STATUS =
		"siteNamesLCSServiceEnabled";

	public static final String KEY_UPTIMES = "uptimes";

	/**
	 * Returns <code>true</code> if the message's key-value map contains the
	 * key.
	 *
	 * @param  key the key to check
	 * @return <code>true</code> if the message's key-value map contains the
	 *         key; <code>false</code> otherwise
	 * @since  LCS 0.1
	 */
	public boolean contains(String key) {
		if (_values == null) {
			return false;
		}

		return _values.containsKey(key);
	}

	/**
	 * Returns the value associated with the key from the message's key-value
	 * map, or <code>null</code> if the map is <code>null</code> or does not
	 * have the key.
	 *
	 * @param  key the key associated with the value to get
	 * @return the value associated with the key from the message's key-value
	 *         map, or <code>null</code> if the map is <code>null</code> or does
	 *         not have the key
	 * @since  LCS 0.1
	 */
	public Object get(String key) {
		if (_values == null) {
			return null;
		}

		return _values.get(key);
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
	 * Returns the message creator's cluster node key.
	 *
	 * @return the message creator's cluster node key
	 * @since  LCS 0.1
	 */
	public String getKey() {
		return _key;
	}

	/**
	 * Returns the message's payload.
	 *
	 * @return the message's payload
	 * @since  LCS 0.1
	 */
	public Object getPayload() {
		return _payload;
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
	public <T extends TransportMetadata> T getTransportMetadata() {
		return (T)_transportMetadata;
	}

	/**
	 * Returns the message's key-value map, or a new message key-value map if
	 * the current one is <code>null</code>.
	 *
	 * @return the message's key-value map, or a new message key-value map if
	 *         the current one is <code>null</code>
	 * @since  LCS 0.1
	 */
	public Map<String, Object> getValues() {
		if (_values == null) {
			_values = new TreeMap<>();
		}

		return _values;
	}

	/**
	 * Puts the value into the message's key-value map associating it with the
	 * key. If the value is <code>null</code>, any existing mapping for the key
	 * is removed from the message key-value map.
	 *
	 * @param key the key to associate with the value
	 * @param value the value to put in the key-value map
	 * @since LCS 0.1
	 */
	public void put(String key, Object value) {
		if (value == null) {
			if (_values != null) {
				_values.remove(key);
			}

			_toString = null;

			return;
		}

		if (_values == null) {
			_values = new TreeMap<>();
		}

		_values.put(key, value);

		_toString = null;
	}

	/**
	 * Removes the key-value mapping associated with the key from the message's
	 * key-value map.
	 *
	 * @param key the key of the value to remove
	 * @since LCS 0.1
	 */
	public void remove(String key) {
		if (_values != null) {
			_values.remove(key);

			_toString = null;
		}
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
	 * Sets the message's payload.
	 *
	 * @param payload the message's payload
	 * @since LCS 0.1
	 */
	public void setPayload(Object payload) {
		_payload = payload;

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

	/**
	 * Sets the message's key-value map.
	 *
	 * @param values the key-value map
	 * @since LCS 0.1
	 */
	public void setValues(Map<String, Object> values) {
		_values = values;

		_toString = null;
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
		sb.append(", payload=");
		sb.append(_payload);
		sb.append(", values=");

		if (_values != null) {
			sb.append(_values.toString());
		}

		sb.append("}");

		_toString = sb.toString();

		return _toString;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

	static {
		_objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	private long _createTime = System.currentTimeMillis();
	private String _key;
	private Object _payload;
	private String _protocolVersion = "1.7";
	private String _queueName;
	private String _toString;
	private TransportMetadata _transportMetadata;
	private Map<String, Object> _values;

}
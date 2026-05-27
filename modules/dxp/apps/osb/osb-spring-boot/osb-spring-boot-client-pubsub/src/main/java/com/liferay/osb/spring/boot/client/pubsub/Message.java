/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.spring.boot.client.pubsub;

import com.liferay.petra.string.StringBundler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Amos Fong
 * @author Kyle Bischof
 */
public class Message {

	public Message() {
	}

	public Message(Map<String, Object> attributes, Object payload) {
		setAttributes(attributes);
		setPayload(payload);
	}

	public Message(Object payload) {
		setPayload(payload);
	}

	public Message(
		String topic, Map<String, Object> attributes, Object payload) {

		setTopic(topic);
		setAttributes(attributes);
		setPayload(payload);
	}

	public Object get(String key) {
		if (_attributes == null) {
			return null;
		}

		return _attributes.get(key);
	}

	public Map<String, Object> getAttributes() {
		return _attributes;
	}

	public String getDestinationName() {
		return _topic;
	}

	public Object getPayload() {
		return _payload;
	}

	public Map<String, String> getStringAttributes() {
		Map<String, String> stringAttributes = new HashMap<>();

		if (_attributes != null) {
			for (Map.Entry<String, Object> entry : _attributes.entrySet()) {
				stringAttributes.put(
					entry.getKey(), String.valueOf(entry.getValue()));
			}
		}

		return stringAttributes;
	}

	public String getTopic() {
		return _topic;
	}

	public void put(String key, Object value) {
		if (_attributes == null) {
			_attributes = new HashMap<>();
		}

		_attributes.put(key, value);
	}

	public void setAttributes(Map<String, Object> attributes) {
		_attributes = attributes;
	}

	public void setDestinationName(String destinationName) {
		_topic = destinationName;
	}

	public void setPayload(Object payload) {
		_payload = payload;
	}

	public void setStringAttributes(Map<String, String> attributes) {
		_attributes = new HashMap<>(attributes);
	}

	public void setTopic(String topic) {
		_topic = topic;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{topic=");
		sb.append(_topic);
		sb.append(", attributes=");
		sb.append(_attributes);
		sb.append(", payload=");
		sb.append(_payload);
		sb.append("}");

		return sb.toString();
	}

	private Map<String, Object> _attributes;
	private Object _payload;
	private String _topic;

}
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.distributed.messaging;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MapUtil;

import java.io.InputStream;

import java.nio.ByteBuffer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Amos Fong
 */
public class Message extends com.liferay.portal.kernel.messaging.Message {

	public static Message fromInputStream(InputStream inputStream)
		throws Exception {

		byte[] bytes = FileUtil.getBytes(inputStream);

		Deserializer deserializer = new Deserializer(ByteBuffer.wrap(bytes));

		return deserializer.readObject();
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

	public Map<String, Object> getAttributes() {
		return getValues();
	}

	public Map<String, String> getStringAttributes() {
		Map<String, String> stringAttributes = new HashMap<>();

		Map<String, Object> attributes = getValues();

		if (attributes != null) {
			for (Map.Entry<String, Object> entry : attributes.entrySet()) {
				stringAttributes.put(
					entry.getKey(), String.valueOf(entry.getValue()));
			}
		}

		return stringAttributes;
	}

	public String getTopic() {
		return getDestinationName();
	}

	public void setAttributes(Map<String, Object> attributes) {
		setValues(attributes);
	}

	public void setStringAttributes(Map<String, String> attributes) {
		setValues(new HashMap<String, Object>(attributes));
	}

	public void setTopic(String topic) {
		setDestinationName(topic);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{topic=");
		sb.append(getTopic());
		sb.append(", attributes=");
		sb.append(MapUtil.toString(getAttributes()));
		sb.append(", payload=");
		sb.append(getPayload());
		sb.append("}");

		return sb.toString();
	}

}
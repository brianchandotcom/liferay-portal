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

package com.liferay.portal.kernel.poller;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultPollerResponse implements PollerResponse {

	@Override
	public void close(
		Message message, PollerHeader pollerHeader, String portletId,
		String chunkId) {

		_closed = true;

		_pollerHeader = pollerHeader;
		_portletId = portletId;
		_chunkId = chunkId;

		String responseDestinationName = message.getResponseDestinationName();

		if (Validator.isNull(responseDestinationName)) {
			return;
		}

		Message responseMessage = MessageBusUtil.createResponseMessage(message);

		responseMessage.setPayload(this);

		MessageBusUtil.sendMessage(responseDestinationName, responseMessage);
	}

	@Override
	public PollerHeader getPollerHeader() {
		return _pollerHeader;
	}

	@Override
	public String getPortletId() {
		return _portletId;
	}

	@Override
	public boolean isEmpty() {
		return _parameterMap.isEmpty();
	}

	@Override
	public void setParameter(String name, JSONArray value)
		throws PollerResponseClosedException {

		if (_closed) {
			throw new PollerResponseClosedException();
		}

		_parameterMap.put(name, value);
	}

	@Override
	public void setParameter(String name, JSONObject value)
		throws PollerResponseClosedException {

		if (_closed) {
			throw new PollerResponseClosedException();
		}

		_parameterMap.put(name, value);
	}

	@Override
	public void setParameter(String name, String value)
		throws PollerResponseClosedException {

		if (_closed) {
			throw new PollerResponseClosedException();
		}

		_parameterMap.put(name, value);
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject pollerResponseJSONObject = JSONUtil.put(
			"portletId", _portletId);

		if (Validator.isNotNull(_chunkId)) {
			pollerResponseJSONObject.put("chunkId", _chunkId);
		}

		JSONObject dataJSONObject = JSONFactoryUtil.createJSONObject();

		for (Map.Entry<String, Object> entry : _parameterMap.entrySet()) {
			String name = entry.getKey();
			Object value = entry.getValue();

			if (value instanceof JSONArray) {
				dataJSONObject.put(name, (JSONArray)value);
			}
			else if (value instanceof JSONObject) {
				dataJSONObject.put(name, (JSONObject)value);
			}
			else {
				dataJSONObject.put(name, String.valueOf(value));
			}
		}

		pollerResponseJSONObject.put("data", dataJSONObject);

		return pollerResponseJSONObject;
	}

	private String _chunkId;
	private volatile boolean _closed;
	private final Map<String, Object> _parameterMap = new ConcurrentHashMap<>();
	private PollerHeader _pollerHeader;
	private String _portletId;

}
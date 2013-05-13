/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class DefaultPollerResponse implements PollerResponse {

	public DefaultPollerResponse(
		PollerHeader pollerHeader, String portletId, String chunkId) {

		_pollerHeader = pollerHeader;
		_portletId = portletId;
		_chunkId = chunkId;
	}

	@Override
	public synchronized void close() {
		if (Validator.isNotNull(_responseMessage)) {
			MessageBusUtil.sendMessage(
				_responseMessage.getDestinationName(), _responseMessage);

			_responseMessage = null;
		}
	}

	@Override
	public void createResponseMessage(Message message) {
		String responseDestinationName = message.getResponseDestinationName();

		if (Validator.isNull(responseDestinationName)) {
			return;
		}

		_responseMessage = MessageBusUtil.createResponseMessage(message);

		_responseMessage.setPayload(this);
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
	public synchronized void setParameter(String name, JSONArray value)
		throws PollerResponseClosedException {

		if (_responseMessage == null) {
			throw new PollerResponseClosedException();
		}

		_parameterMap.put(name, value);
	}

	@Override
	public synchronized void setParameter(String name, JSONObject value)
		throws PollerResponseClosedException {

		if (_responseMessage == null) {
			throw new PollerResponseClosedException();
		}

		_parameterMap.put(name, value);
	}

	@Override
	public void setParameter(String name, String value)
		throws PollerResponseClosedException {

		synchronized (this) {
			if (_responseMessage == null) {
				throw new PollerResponseClosedException();
			}

			_parameterMap.put(name, value);
		}
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject pollerResponseJSONObject =
			JSONFactoryUtil.createJSONObject();

		pollerResponseJSONObject.put("portletId", _portletId);

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
	private Map<String, Object> _parameterMap = new HashMap<String, Object>();
	private PollerHeader _pollerHeader;
	private String _portletId;
	private Message _responseMessage;

}
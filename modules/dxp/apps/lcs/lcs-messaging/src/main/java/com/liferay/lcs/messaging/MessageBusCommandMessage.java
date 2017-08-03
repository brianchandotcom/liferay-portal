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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Riccardo Ferrari
 */
public class MessageBusCommandMessage extends CommandMessage {

	public String getDestinationName() {
		return _destinationName;
	}

	public Object getPayload() {
		return _payload;
	}

	public String getResponse() {
		return _response;
	}

	public String getResponseDestinationName() {
		return _responseDestinationName;
	}

	public String getResponseId() {
		return _responseId;
	}

	public Map<String, Object> getValues() {
		return _values;
	}

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	public void setPayload(Object payload) {
		_payload = payload;
	}

	public void setResponse(String response) {
		_response = response;
	}

	public void setResponseDestinationName(String responseDestinationName) {
		_responseDestinationName = responseDestinationName;
	}

	public void setResponseId(String responseId) {
		_responseId = responseId;
	}

	public void setValues(Map<String, Object> values) {
		_values = values;
	}

	private String _destinationName;
	private Object _payload;
	private String _response;
	private String _responseDestinationName;
	private String _responseId;
	private Map<String, Object> _values = new HashMap<String, Object>();

}
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

/**
 * @author Riccardo Ferrari
 */
public class MessageBusMessage extends Message {

	public String getDestinationName() {
		return _destinationName;
	}

	@Override
	public String getPayload() {
		return (String)super.getPayload();
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

	public void setDestinationName(String destinationName) {
		_destinationName = destinationName;
	}

	public void setPayload(String payload) {
		super.setPayload(payload);
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

	private String _destinationName;
	private String _response;
	private String _responseDestinationName;
	private String _responseId;

}
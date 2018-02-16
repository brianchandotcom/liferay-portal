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

package com.liferay.lcs.service;

import com.liferay.lcs.messaging.Message;
import com.liferay.petra.json.web.service.client.JSONWebServiceInvocationException;
import com.liferay.petra.json.web.service.client.JSONWebServiceSerializeException;
import com.liferay.petra.json.web.service.client.JSONWebServiceTransportException;

import java.io.IOException;

import java.util.List;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public interface LCSGatewayService {

	public void deleteMessages(String key)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceTransportException;

	public List<Message> getMessages(String key)
		throws JSONWebServiceInvocationException,
			   JSONWebServiceSerializeException,
			   JSONWebServiceTransportException;

	public void sendMessage(Message message)
		throws IOException,
			   JSONWebServiceInvocationException,
			   JSONWebServiceTransportException;

	public boolean testLCSGatewayAvailability();

}
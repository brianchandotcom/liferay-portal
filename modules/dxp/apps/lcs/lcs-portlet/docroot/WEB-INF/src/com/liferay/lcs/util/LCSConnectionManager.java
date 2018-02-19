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

package com.liferay.lcs.util;

import com.liferay.lcs.exception.CompressionException;
import com.liferay.lcs.messaging.Message;
import com.liferay.petra.json.web.service.client.JSONWebServiceException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public interface LCSConnectionManager {

	public void deleteMessages(String key) throws JSONWebServiceException;

	public Map<String, String> getLCSConnectionMetadata();

	public List<Message> getMessages(String key) throws JSONWebServiceException;

	public boolean isLCSGatewayAvailable();

	public boolean isReady();

	public boolean isShutdownRequested();

	public void onHandshakeSuccess();

	public void onPortletDeployed();

	public void putLCSConnectionMetadata(String key, String value);

	public void sendMessage(Message message)
		throws CompressionException, JSONWebServiceException;

	public void setLCSGatewayAvailable(boolean lcsGatewayAvailable);

	public void setReady(boolean ready);

	public void setShutdownRequested(boolean shutdownRequested);

	public Future<?> start();

	public Future<?> stop(
		boolean delayReconnect, boolean signedOff,
		boolean serverManuallyShutdown);

}
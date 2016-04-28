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

import com.liferay.lcs.messaging.Message;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public interface LCSConnectionManager {

	public void deleteMessages(String key) throws PortalException;

	public void deregister();

	public Map<String, String> getLCSConnectionMetadata();

	public List<Message> getMessages(String key) throws PortalException;

	public boolean isHandshakeExpired();

	public boolean isLCSGatewayAvailable();

	public boolean isPending();

	public boolean isReady();

	public void onHandshakeSuccess();

	public void onSignOff();

	public void putLCSConnectionMetadata(String key, String value);

	public Future<?> restart();

	public void sendMessage(Message message) throws PortalException;

	public void setHandshakeExpired(boolean handshakeExpired);

	public void setLCSGatewayAvailable(boolean lcsGatewayAvailable);

	public void setPending(boolean pending);

	public void setReady(boolean ready);

	public Future<?> start();

	public Future<?> stop();

	public Future<?> stop(boolean deregister, boolean serverManuallyShutdown);

}
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

package com.liferay.petra.salesforce.client.streaming;

import com.liferay.petra.salesforce.client.SalesforceClient;

import com.sforce.ws.ConnectionException;

import org.cometd.bayeux.Channel;

/**
 * @author Brian Wing Shun Chan
 * @author Rachael Koestartyo
 * @author Peter Shin
 */
public interface SalesforceStreamingClient extends SalesforceClient {

	public boolean connect() throws ConnectionException;

	public boolean disconnect();

	public Channel getChannel(String name);

	public int getTransportTimeout();

	public void setTransportTimeout(int transportTimeout);

}
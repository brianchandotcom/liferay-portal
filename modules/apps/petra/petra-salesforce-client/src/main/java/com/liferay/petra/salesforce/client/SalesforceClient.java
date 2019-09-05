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

package com.liferay.petra.salesforce.client;

import com.sforce.ws.ConnectorConfig;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Shin
 * @author Rachael Koestartyo
 */
public interface SalesforceClient {

	public String getAuthEndpoint();

	public int getConnectionTimeout();

	public ConnectorConfig getConnectorConfig();

	public String getPassword();

	public int getReadTimeout();

	public String getServiceEndpoint();

	public String getSessionId();

	public String getUserName();

	public boolean isDebugEnabled();

	public void setAuthEndpoint(String authEndpoint);

	public void setConnectionTimeout(int connectionTimeout);

	public void setDebugEnabled(boolean debugEnabled);

	public void setPassword(String password);

	public void setReadTimeout(int readTimeout);

	public void setServiceEndpoint(String serviceEndpoint);

	public void setSessionId(String sessionId);

	public void setUserName(String userName);

}
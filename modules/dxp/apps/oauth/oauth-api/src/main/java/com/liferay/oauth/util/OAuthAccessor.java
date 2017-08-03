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

package com.liferay.oauth.util;

import java.io.Serializable;

/**
 * @author Ivica Cardic
 */
public interface OAuthAccessor extends Serializable {

	public String getAccessToken();

	public OAuthConsumer getOAuthConsumer();

	public Object getProperty(String name);

	public String getRequestToken();

	public String getTokenSecret();

	public Object getWrappedOAuthAccessor();

	public void setAccessToken(String accesToken);

	public void setProperty(String name, Object value);

	public void setRequestToken(String requestToken);

	public void setTokenSecret(String tokenSecret);

}
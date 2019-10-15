/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
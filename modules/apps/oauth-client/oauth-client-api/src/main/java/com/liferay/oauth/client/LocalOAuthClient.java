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

package com.liferay.oauth.client;

import com.liferay.oauth2.provider.model.OAuth2Application;

/**
 * Primarily used by LXC to retrieve OAuth tokens from Portal OAuth Provider
 * on a same instance for extension consumptions.
 *
 * @author Arthur Chan
 */
public interface LocalOAuthClient {

	/**
	 * Retrieve OAuth tokens from Portal OAuth Provider on a same instance.
	 *
	 * @param userId the user who gives the grant
	 * @param oAuth2Application oAuth2Application containing client information
	 * @return OAuth tokens or null if there was a token generation error
	 */
	public String requestTokens(
		long userId, OAuth2Application oAuth2Application);

}
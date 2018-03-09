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

package com.liferay.lcs.oauth;

/**
 * @author Igor Beslic
 */
public enum OAuthExceptionType {

	CONSUMER_KEY_REFUSED("oAuthConsumerKeyRefused"),
	TIMESTAMP_REFUSED("oAuthTimestampRefused"),
	TOKEN_REJECTED("oAuthTokenRejected");

	public String getKey() {
		return _key;
	}

	private OAuthExceptionType(String key) {
		_key = key;
	}

	private final String _key;

}
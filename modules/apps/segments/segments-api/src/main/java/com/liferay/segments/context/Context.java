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

package com.liferay.segments.context;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a context to segment users based on their session criteria.
 *
 * @author Eduardo García
 */
public class Context {

	public static final String BROWSER = "browser";

	public static final String COOKIES = "cookies";

	public static final String DEVICE_BRAND = "deviceBrand";

	public static final String DEVICE_MODEL = "deviceModel";

	public static final String DEVICE_SCREEN_RESOLUTION_HEIGHT =
		"deviceScreenResolutionHeight";

	public static final String DEVICE_SCREEN_RESOLUTION_WIDTH =
		"deviceScreenResolutionWidth";

	public static final String HOSTNAME = "hostname";

	public static final String LANGUAGE_ID = "languageId";

	public static final String LAST_SIGN_IN_DATE_TIME = "lastSignInDateTime";

	public static final String LOCAL_DATE = "localDate";

	public static final String REFERRER_URL = "referrerURL";

	public static final String REQUEST_PARAMETERS = "requestParameters";

	public static final String SIGNED_IN = "signedIn";

	public static final String URL = "url";

	public static final String USER_AGENT = "userAgent";

	public Serializable get(String key) {
		return _map.get(key);
	}

	public void put(String key, Serializable value) {
		_map.put(key, value);
	}

	private final Map<String, Serializable> _map = new HashMap<>();

}
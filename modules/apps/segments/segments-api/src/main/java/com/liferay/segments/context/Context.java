/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.context;

import java.io.Serializable;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Represents a context to segment users based on their session criteria.
 *
 * @author Eduardo García
 */
public class Context
	extends AbstractMap<String, Serializable>
	implements Map<String, Serializable> {

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

	@Override
	public Set<Entry<String, Serializable>> entrySet() {
		return _map.entrySet();
	}

	@Override
	public Serializable put(String key, Serializable value) {
		return _map.put(key, value);
	}

	private final Map<String, Serializable> _map = new HashMap<>();

}
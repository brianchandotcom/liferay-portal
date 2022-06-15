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

package com.liferay.portal.kernel.cookies.constants;

import com.liferay.portal.kernel.util.Time;

/**
 * @author Tamas Molnar
 */
public class CookiesConstants {

	public static final int CONSENT_TYPE_FUNCTIONAL = 1;

	public static final int CONSENT_TYPE_NECESSARY = 2;

	public static final int CONSENT_TYPE_PERFORMANCE = 3;

	public static final int CONSENT_TYPE_PERSONALIZATION = 4;

	public static final int MAX_AGE = (int)(Time.YEAR / 1000);

	public static final String NAME_COMMERCE_CONTINUE_AS_GUEST =
		"COMMERCE_CONTINUE_AS_GUEST";

	public static final String NAME_COMPANY_ID = "COMPANY_ID";

	public static final String NAME_CONSENT_TYPE_FUNCTIONAL =
		"CONSENT_TYPE_FUNCTIONAL";

	public static final String NAME_CONSENT_TYPE_NECESSARY =
		"CONSENT_TYPE_NECESSARY";

	public static final String NAME_CONSENT_TYPE_PERFORMANCE =
		"CONSENT_TYPE_PERFORMANCE";

	public static final String NAME_CONSENT_TYPE_PERSONALIZATION =
		"CONSENT_TYPE_PERSONALIZATION";

	public static final String NAME_COOKIE_SUPPORT = "COOKIE_SUPPORT";

	public static final String NAME_GUEST_LANGUAGE_ID = "GUEST_LANGUAGE_ID";

	public static final String NAME_ID = "ID";

	public static final String NAME_JSESSIONID = "JSESSIONID";

	public static final String NAME_LOGIN = "LOGIN";

	public static final String NAME_PASSWORD = "PASSWORD";

	public static final String NAME_REMEMBER_ME = "REMEMBER_ME";

	public static final String NAME_REMOTE_PREFERENCE_PREFIX =
		"REMOTE_PREFERENCE_";

	public static final String NAME_USER_CONSENT_CONFIGURED =
		"USER_CONSENT_CONFIGURED";

	public static final String NAME_USER_UUID = "USER_UUID";

}
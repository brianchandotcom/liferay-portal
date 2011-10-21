/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.util.BaseTestCase;
import com.liferay.portal.util.TestPropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class BaseServiceHttpTestCase extends BaseTestCase {

	protected HttpPrincipal getHttpPrincipal(long userId) {
		return getHttpPrincipal(userId, true);
	}

	protected HttpPrincipal getHttpPrincipal(
		long userId, boolean authenticated) {

		HttpPrincipal httpPrincipal = null;

		if (authenticated) {
			httpPrincipal = new HttpPrincipal(
				TestPropsValues.PORTAL_URL, String.valueOf(userId),
				TestPropsValues.USER_PASSWORD);
		}
		else {
			httpPrincipal = new HttpPrincipal(TestPropsValues.PORTAL_URL);
		}

		return httpPrincipal;
	}

}
/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.util.TestPropsValues;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author Brian Wing Shun Chan
 */
public class BaseServiceSoapTestCase {

	protected URL getURL(long userId, boolean authenticated, String serviceName)
		throws MalformedURLException {

		String url = TestPropsValues.PORTAL_URL;

		if (authenticated) {
			String password = TestPropsValues.USER_PASSWORD;

			int pos = url.indexOf("://");

			String protocol = url.substring(0, pos + 3);
			String host = url.substring(pos + 3);

			url =
				protocol + userId + ":" + password + "@" + host +
					"/tunnel-web/secure/axis/" + serviceName;
		}
		else {
			url += "/tunnel-web/axis/" + serviceName;
		}

		return new URL(url);
	}

	protected URL getURL(long userId, String serviceName)
		throws MalformedURLException {

		return getURL(userId, true, serviceName);
	}

}
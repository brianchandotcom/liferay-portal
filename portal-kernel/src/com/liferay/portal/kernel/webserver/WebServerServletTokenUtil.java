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

package com.liferay.portal.kernel.webserver;

/**
 * @author Brian Wing Shun Chan
 * @since  6.1, replaced com.liferay.portal.kernel.servlet.ImageServletTokenUtil
 */
public class WebServerServletTokenUtil {

	public static String getToken(long imageId) {
		return getWebServerServletToken().getToken(imageId);
	}

	public static WebServerServletToken getWebServerServletToken() {
		return _webServerServletToken;
	}

	public static void resetToken(long imageId) {
		getWebServerServletToken().resetToken(imageId);
	}

	public void setWebServerServletToken(
		WebServerServletToken webServerServletToken) {

		_webServerServletToken = webServerServletToken;
	}

	private static WebServerServletToken _webServerServletToken;

}
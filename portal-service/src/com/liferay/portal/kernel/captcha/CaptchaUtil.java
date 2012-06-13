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

package com.liferay.portal.kernel.captcha;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class CaptchaUtil {

	public static void check(HttpServletRequest request)
		throws CaptchaException {

		getCaptcha().check(request);
	}

	public static void check(PortletRequest portletRequest)
		throws CaptchaException {

		getCaptcha().check(portletRequest);
	}

	public static Captcha getCaptcha() {
		PortalRuntimePermission.checkGetBeanProperty(CaptchaUtil.class);

		return _captcha;
	}

	public static String getTaglibPath() {
		return getCaptcha().getTaglibPath();
	}

	public static boolean isEnabled(HttpServletRequest request)
		throws CaptchaException {

		return getCaptcha().isEnabled(request);
	}

	public static boolean isEnabled(PortletRequest portletRequest)
		throws CaptchaException {

		return getCaptcha().isEnabled(portletRequest);
	}

	public static void serveImage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		getCaptcha().serveImage(request, response);
	}

	public static void serveImage(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		getCaptcha().serveImage(resourceRequest, resourceResponse);
	}

	public void setCaptcha(Captcha captcha) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_captcha = captcha;
	}

	private static Captcha _captcha;

}
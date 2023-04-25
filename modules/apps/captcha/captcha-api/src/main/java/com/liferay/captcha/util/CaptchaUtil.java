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

package com.liferay.captcha.util;

import com.liferay.captcha.provider.CaptchaProvider;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaException;

import java.io.IOException;

import javax.portlet.PortletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Pei-Jung Lan
 */
public class CaptchaUtil {

	public static void check(HttpServletRequest httpServletRequest)
		throws CaptchaException {

		getCaptcha().check(httpServletRequest);
	}

	public static void check(PortletRequest portletRequest)
		throws CaptchaException {

		getCaptcha().check(portletRequest);
	}

	public static Captcha getCaptcha() {
		CaptchaProvider captchaProvider = _captchaProviderSnapshot.get();

		return captchaProvider.getCaptcha();
	}

	public static String getTaglibPath() {
		return getCaptcha().getTaglibPath();
	}

	public static boolean isEnabled(HttpServletRequest httpServletRequest) {
		return getCaptcha().isEnabled(httpServletRequest);
	}

	public static boolean isEnabled(PortletRequest portletRequest) {
		return getCaptcha().isEnabled(portletRequest);
	}

	public static void serveImage(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		getCaptcha().serveImage(httpServletRequest, httpServletResponse);
	}

	private static final Snapshot<CaptchaProvider> _captchaProviderSnapshot =
		new Snapshot<>(CaptchaUtil.class, CaptchaProvider.class);

}
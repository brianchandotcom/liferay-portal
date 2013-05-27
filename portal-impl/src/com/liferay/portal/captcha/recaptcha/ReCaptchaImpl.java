/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.captcha.recaptcha;

import com.liferay.portal.captcha.simplecaptcha.SimpleCaptchaImpl;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Tagnaouti Boubker
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 * @author Daniel Sanz
 */
public class ReCaptchaImpl extends SimpleCaptchaImpl {

	@Override
	public String getTaglibPath() {
		return _TAGLIB_PATH;
	}

	@Override
	public void serveImage(
		HttpServletRequest request, HttpServletResponse response) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void serveImage(
		ResourceRequest resourceRequest, ResourceResponse resourceResponse) {

		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean validateChallenge(HttpServletRequest request)
		throws CaptchaException {

		String reCaptchaChallenge = ParamUtil.getString(
			request, "recaptcha_challenge_field");
		String reCaptchaResponse = ParamUtil.getString(
			request, "recaptcha_response_field");

		Http.Options options = new Http.Options();

		options.addPart("challenge", reCaptchaChallenge);

		try {
			options.addPart(
				"privatekey",
				PrefsPropsUtil.getString(
					PropsKeys.CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE,
					PropsValues.CAPTCHA_ENGINE_RECAPTCHA_KEY_PRIVATE));
		}
		catch (SystemException se) {
			_log.error(se, se);
		}

		options.addPart("remoteip", request.getRemoteAddr());
		options.addPart("response", reCaptchaResponse);
		options.setLocation(
			HttpUtil.protocolize(
				PropsValues.CAPTCHA_ENGINE_RECAPTCHA_URL_VERIFY,
				request.isSecure()));
		options.setPost(true);

		String content = null;

		try {
			content = HttpUtil.URLtoString(options);
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);

			throw new CaptchaTextException();
		}

		if (content == null) {
			_log.error("reCAPTCHA did not return a result");

			throw new CaptchaTextException();
		}

		String[] messages = content.split("\r?\n");

		if (messages.length < 1) {
			_log.error("reCAPTCHA did not return a valid result: " + content);

			throw new CaptchaTextException();
		}

		return GetterUtil.getBoolean(messages[0]);
	}

	@Override
	protected boolean validateChallenge(PortletRequest portletRequest)
		throws CaptchaException {

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			portletRequest);

		return validateChallenge(request);
	}

	private static final String _TAGLIB_PATH =
		"/html/taglib/ui/captcha/recaptcha.jsp";

	private static Log _log = LogFactoryUtil.getLog(ReCaptchaImpl.class);

}
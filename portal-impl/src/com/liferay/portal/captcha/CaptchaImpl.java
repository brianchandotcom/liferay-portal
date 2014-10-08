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

package com.liferay.portal.captcha;

import com.liferay.portal.kernel.captcha.Captcha;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class CaptchaImpl implements Captcha {

	@Override
	public void check(HttpServletRequest request) throws CaptchaException {
		_initialize();

		_captcha.check(request);
	}

	@Override
	public void check(PortletRequest portletRequest) throws CaptchaException {
		_initialize();

		_captcha.check(portletRequest);
	}

	@Override
	public String getTaglibPath() {
		_initialize();

		return _captcha.getTaglibPath();
	}

	@Override
	public boolean isEnabled(HttpServletRequest request)
		throws CaptchaException {

		_initialize();

		return _captcha.isEnabled(request);
	}

	@Override
	public boolean isEnabled(PortletRequest portletRequest)
		throws CaptchaException {

		_initialize();

		return _captcha.isEnabled(portletRequest);
	}

	@Override
	public void serveImage(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		_initialize();

		_captcha.serveImage(request, response);
	}

	@Override
	public void serveImage(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException {

		_initialize();

		_captcha.serveImage(resourceRequest, resourceResponse);
	}

	public void setCaptcha(Captcha captcha) {
		_initialize();

		if (captcha == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Restoring " + ClassUtil.getClassName(_originalCaptcha));
			}

			_captcha = _originalCaptcha;
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("Setting " + ClassUtil.getClassName(captcha));
			}

			_captcha = captcha;
		}
	}

	private void _initialize() {
		if (_captcha != null) {
			return;
		}

		synchronized (this) {
			if (_captcha != null) {
				return;
			}

			try {
				String captchaClassName = PrefsPropsUtil.getString(
					PropsKeys.CAPTCHA_ENGINE_IMPL,
					PropsValues.CAPTCHA_ENGINE_IMPL);

				if (_log.isInfoEnabled()) {
					_log.info("Initializing " + captchaClassName);
				}

				_captcha = (Captcha)InstanceFactory.newInstance(
					ClassLoaderUtil.getPortalClassLoader(), captchaClassName);

				_originalCaptcha = _captcha;
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(CaptchaImpl.class);

	private volatile Captcha _captcha;
	private Captcha _originalCaptcha;

}
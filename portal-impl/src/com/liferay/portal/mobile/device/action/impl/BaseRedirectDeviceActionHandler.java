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

package com.liferay.portal.mobile.device.action.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.action.DeviceProfileActionHandler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mobile.model.DeviceProfileAction;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public abstract class BaseRedirectDeviceActionHandler
	implements DeviceProfileActionHandler {

	public void applyDeviceAction(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			DeviceProfileAction deviceProfileAction)
		throws PortalException, SystemException {

		String url = getURL(
			httpServletRequest, httpServletResponse, deviceProfileAction);

		if (Validator.isNotNull(url)) {
			String requestURL = httpServletRequest.getRequestURL().toString();

			if (StringUtil.contains(requestURL, url)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Skipping redirect. Current URL contains redirect URL");
				}

				return;
			}

			try {
				httpServletResponse.sendRedirect(url);
			}
			catch (IOException ioe) {
				throw new PortalException(
					"Unable to send redirect for url: " + url, ioe);
			}
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info("No URL to redirect to located");
			}
		}
	}

	protected abstract String getURL(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			DeviceProfileAction deviceProfileAction)
		throws PortalException, SystemException;

	private static Log _log = LogFactoryUtil.getLog(
		BaseRedirectDeviceActionHandler.class);
}
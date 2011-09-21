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

package com.liferay.portal.mobile.device.rulegroup.action.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mobile.device.rulegroup.action.ActionHandler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.mobiledevicerules.model.MDRAction;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Edward Han
 */
public abstract class BaseRedirectActionHandler
	implements ActionHandler {

	public void applyAction(
			MDRAction action, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException, SystemException {

		String url = getURL(action, request, response);

		if (Validator.isNull(url)) {
			if (_log.isInfoEnabled()) {
				_log.info("No URL to redirect to located");
			}
		}
		else {
			String requestURL = request.getRequestURL().toString();

			if (StringUtil.contains(requestURL, url)) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Skipping redirect. Current URL contains redirect URL");
				}

				return;
			}

			try {
				response.sendRedirect(url);
			}
			catch (IOException ioe) {
				throw new PortalException(
					"Unable to send redirect for url: " + url, ioe);
			}
		}
	}

	protected abstract String getURL(
			MDRAction action, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException, SystemException;

	private static Log _log = LogFactoryUtil.getLog(
		BaseRedirectActionHandler.class);

}
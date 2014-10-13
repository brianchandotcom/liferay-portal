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

package com.liferay.portal.mobile.device.rulegroup.action.impl;

import com.liferay.portal.kernel.exception.PortalException;
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
public abstract class BaseRedirectActionHandler implements ActionHandler {

	@Override
	public void applyAction(
			MDRAction mdrAction, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException {

		String url = getURL(mdrAction, request, response);

		if (Validator.isNull(url)) {
			if (_log.isInfoEnabled()) {
				_log.info("URL is null");
			}

			return;
		}

		String requestURL = String.valueOf(request.getRequestURL());

		if (StringUtil.contains(requestURL, url)) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skipping redirect. Current URL contains redirect URL.");
			}

			return;
		}

		try {
			response.sendRedirect(url);
		}
		catch (IOException ioe) {
			throw new PortalException("Unable to redirect to " + url, ioe);
		}
	}

	protected abstract String getURL(
			MDRAction mdrAction, HttpServletRequest request,
			HttpServletResponse response)
		throws PortalException;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseRedirectActionHandler.class);

}
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

package com.liferay.portal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * This action ensures that all requests are secure. Extend this and override
 * the <code>isRequiresSecure</code> method to programmatically decide when a
 * request requires HTTPS.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class SecureRequestAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			if (request.isSecure()) {
				return;
			}

			if (!isRequiresSecure(request)) {
				return;
			}

			if (response.isCommitted()) {
				return;
			}

			String redirect = getRedirect(request);

			if (_log.isDebugEnabled()) {
				_log.debug("Redirect " + redirect);
			}

			if (redirect != null) {
				response.sendRedirect(redirect);
			}
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected String getRedirect(HttpServletRequest request) {
		String unsecureCompleteURL = PortalUtil.getCurrentCompleteURL(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Unsecure URL " + unsecureCompleteURL);
		}

		String secureCompleteURL = StringUtil.replaceFirst(
			unsecureCompleteURL, Http.HTTP_WITH_SLASH, Http.HTTPS_WITH_SLASH);

		if (_log.isDebugEnabled()) {
			_log.debug("Secure URL " + secureCompleteURL);
		}

		if (unsecureCompleteURL.equals(secureCompleteURL)) {
			return null;
		}

		return secureCompleteURL;
	}

	protected boolean isRequiresSecure(HttpServletRequest request) {
		return _REQUIRES_SECURE;
	}

	private static final boolean _REQUIRES_SECURE = true;

	private static final Log _log = LogFactoryUtil.getLog(
		SecureRequestAction.class);

}
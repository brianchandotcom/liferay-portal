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

package com.liferay.portal.kernel.servlet.taglib.ui;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public abstract class BaseJSPFormNavigatorEntry<T>
	extends BaseFormNavigatorEntry<T> implements FormNavigatorEntry<T> {

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		ServletContext servletContext = getServletContext(request);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(getJspPath());

		try {
			requestDispatcher.include(request, response);
		}
		catch (ServletException se) {
			_log.error("Unable to include JSP " + getJspPath(), se);

			throw new IOException("Unable to include " + getJspPath(), se);
		}
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected abstract String getJspPath();

	protected ServletContext getServletContext(HttpServletRequest request) {
		if (_servletContext != null) {
			return _servletContext;
		}

		String portletId = PortalUtil.getPortletId(request);

		if (Validator.isNotNull(portletId)) {
			String rootPortletId = PortletIdCodec.decodePortletName(portletId);

			PortletBag portletBag = PortletBagPool.get(rootPortletId);

			return portletBag.getServletContext();
		}

		return (ServletContext)request.getAttribute(WebKeys.CTX);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseJSPFormNavigatorEntry.class);

	private ServletContext _servletContext;

}
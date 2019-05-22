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

package com.liferay.frontend.taglib.servlet.taglib.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = JSPRenderer.class)
public class JSPRenderer {

	public void renderJSP(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String path)
		throws IOException {

		renderJSP(
			getServletContext(httpServletRequest), httpServletRequest,
			httpServletResponse, path);
	}

	public void renderJSP(
			ServletContext servletContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String path)
		throws IOException {

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException se) {
			_log.error("Unable to render JSP " + path, se);

			throw new IOException("Unable to render " + path, se);
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x), with no direct replacement
	 * @param servletContext
	 */
	@Deprecated
	public void setServletContext(ServletContext servletContext) {
	}

	protected ServletContext getServletContext(
		HttpServletRequest httpServletRequest) {

		String portletId = _portal.getPortletId(httpServletRequest);

		if (Validator.isNotNull(portletId)) {
			String rootPortletId = PortletIdCodec.decodePortletName(portletId);

			PortletBag portletBag = PortletBagPool.get(rootPortletId);

			return portletBag.getServletContext();
		}

		return (ServletContext)httpServletRequest.getAttribute(WebKeys.CTX);
	}

	private static final Log _log = LogFactoryUtil.getLog(JSPRenderer.class);

	@Reference
	private Portal _portal;

}
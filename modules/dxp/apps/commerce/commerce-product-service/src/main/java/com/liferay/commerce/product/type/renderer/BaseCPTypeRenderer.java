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

package com.liferay.commerce.product.type.renderer;

import com.liferay.commerce.product.type.CPTypeRenderer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletConstants;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
public abstract class BaseCPTypeRenderer implements CPTypeRenderer {

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

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected ServletContext getServletContext(
		HttpServletRequest httpServletRequest) {

		if (_servletContext != null) {
			return _servletContext;
		}

		String portletId = portal.getPortletId(httpServletRequest);

		if (Validator.isNotNull(portletId)) {
			String rootPortletId = PortletConstants.getRootPortletId(portletId);

			PortletBag portletBag = PortletBagPool.get(rootPortletId);

			return portletBag.getServletContext();
		}

		return (ServletContext)httpServletRequest.getAttribute(WebKeys.CTX);
	}

	@Reference
	protected Portal portal;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseCPTypeRenderer.class);

	private ServletContext _servletContext;

}
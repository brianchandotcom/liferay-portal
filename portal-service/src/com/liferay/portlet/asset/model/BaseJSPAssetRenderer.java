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

package com.liferay.portlet.asset.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletBag;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public abstract class BaseJSPAssetRenderer<T>
	extends BaseAssetRenderer<T> implements AssetRenderer<T> {

	public abstract String getJspPath(
		HttpServletRequest request, String template);

	@Override
	public boolean include(
			HttpServletRequest request, HttpServletResponse response,
			String template)
		throws Exception {

		ServletContext servletContext = getServletContext();

		String jspPath = getJspPath(request, template);

		if (Validator.isNull(jspPath)) {
			return false;
		}

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(jspPath);

		try {
			requestDispatcher.include(request, response);

			return true;
		}
		catch (ServletException se) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to include JSP", se);
			}

			throw new IOException("Unable to include " + jspPath, se);
		}
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected ServletContext getServletContext() {
		if (_servletContext != null) {
			return _servletContext;
		}

		String portletId = getAssetRendererFactory().getPortletId();

		PortletBag portletBag = PortletBagPool.get(portletId);

		return portletBag.getServletContext();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseJSPAssetRenderer.class);

	private ServletContext _servletContext;

}
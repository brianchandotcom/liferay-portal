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

package com.liferay.portal.servlet.taglib.ui;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.taglib.ui.FormNavigatorEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.spring.context.PortalContextLoaderListener;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Sergio González
 */
public abstract class BaseFormNavigatorEntry<T>
	implements FormNavigatorEntry<T> {

	@Override
	public abstract String getCategoryKey();

	@Override
	public abstract String getFormNavigatorId();

	@Override
	public abstract String getKey();

	@Override
	public abstract String getLabel(Locale locale);

	@Override
	public boolean isVisible(User user, T formModelBean) {
		return true;
	}

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		ServletContext servletContext = getServletContext(request);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(getJspPath());

		try {
			requestDispatcher.include(request, response);
		}
		catch (ServletException se) {
			if (_log.isErrorEnabled()) {
				_log.error("Unable to include JSP", se);
			}

			throw new IOException("Unable to include JSP", se);
		}
	}

	protected abstract String getJspPath();

	protected ServletContext getServletContext(HttpServletRequest request) {
		return ServletContextPool.get(
			PortalContextLoaderListener.getPortalServletContextName());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseFormNavigatorEntry.class);

}
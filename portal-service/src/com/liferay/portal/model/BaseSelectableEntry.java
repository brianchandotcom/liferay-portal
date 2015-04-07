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

package com.liferay.portal.model;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Julio Camarero
 */
public abstract class BaseSelectableEntry implements SelectableEntry {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SelectableEntry)) {
			return false;
		}

		SelectableEntry selectableEntry = (SelectableEntry)obj;

		String key = selectableEntry.getKey();

		if (getKey() == key) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public String getIcon() {
		return _DEFAUTL_ICON;
	}

	public String getJSPPath() {
		return null;
	}

	@Override
	public String getKey() {
		return getClass().getSimpleName();
	}

	@Override
	public Double getWeight() {
		return 10.0;
	}

	@Override
	public void include(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(getJSPPath());

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

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	private static final String _DEFAUTL_ICON = "circle-blank";

	private static final Log _log = LogFactoryUtil.getLog(
		BaseSelectableEntry.class);

	private ServletContext _servletContext;

}
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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.servlet.DirectServletContext;
import com.liferay.portal.kernel.servlet.PipingServletResponse;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class JSPSearchEntry extends SearchEntry {

	@Override
	public Object clone() {
		JSPSearchEntry jspSearchEntry = new JSPSearchEntry();

		BeanPropertiesUtil.copyProperties(this, jspSearchEntry);

		return jspSearchEntry;
	}

	public String getPath() {
		return _path;
	}

	public HttpServletRequest getRequest() {
		return _request;
	}

	public HttpServletResponse getResponse() {
		return _response;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	@Override
	public void print(PageContext pageContext) throws Exception {
		if (_servletContext != null) {
			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(_path);

			_request.setAttribute(WebKeys.SERVLET_PATH, _path);

			requestDispatcher.include(
				_request, new PipingServletResponse(pageContext));
		}
		else {
			pageContext.include(_path);
		}
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setRequest(HttpServletRequest request) {
		_request = request;
	}

	public void setResponse(HttpServletResponse response) {
		_response = response;
	}

	public void setServletContext(ServletContext servletContext) {
		if (_DIRECT_SERVLET_CONTEXT_ENABLED) {
			_servletContext = new DirectServletContext(servletContext);
		}
		else {
			_servletContext = servletContext;
		}
	}

	private static final boolean _DIRECT_SERVLET_CONTEXT_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.DIRECT_SERVLET_CONTEXT_ENABLED));

	private String _path;
	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private ServletContext _servletContext;

}
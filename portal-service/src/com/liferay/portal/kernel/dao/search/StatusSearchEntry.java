/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.servlet.DirectRequestDispatcherFactoryUtil;
import com.liferay.portal.kernel.servlet.PipingServletResponse;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class StatusSearchEntry extends TextSearchEntry {

	@Override
	public Object clone() {
		StatusSearchEntry jspSearchEntry = new StatusSearchEntry();

		BeanPropertiesUtil.copyProperties(this, jspSearchEntry);

		return jspSearchEntry;
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

	public int getStatus() {
		return _status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	@Override
	public void print(PageContext pageContext) throws Exception {
		if (_request == null) {
			_request = (HttpServletRequest)pageContext.getRequest();
		}

		_request.setAttribute(
			"liferay-ui:search-container-column-status:status", _status);
		_request.setAttribute(
			"liferay-ui:search-container-column-status:statusByUserId",
			_statusByUserId);
		_request.setAttribute(
			"liferay-ui:search-container-column-status:statusDate",
			_statusDate);

		if (_servletContext != null) {
			RequestDispatcher requestDispatcher =
				DirectRequestDispatcherFactoryUtil.getRequestDispatcher(
					_servletContext, _PAGE);

			requestDispatcher.include(
				_request, new PipingServletResponse(pageContext));
		}
		else {
			pageContext.include(_PAGE);
		}
	}

	public void setRequest(HttpServletRequest request) {
		_request = request;
	}

	public void setResponse(HttpServletResponse response) {
		_response = response;
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	private static final String _PAGE =
		"/html/taglib/ui/search_container/status.jsp";

	private HttpServletRequest _request;
	private HttpServletResponse _response;
	private ServletContext _servletContext;
	private int _status;
	private long _statusByUserId;
	private Date _statusDate;

}
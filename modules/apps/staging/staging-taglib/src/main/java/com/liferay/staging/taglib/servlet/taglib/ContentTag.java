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

package com.liferay.staging.taglib.servlet.taglib;

import com.liferay.staging.taglib.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Levente Hudák
 */
public class ContentTag extends IncludeTag {

	public void setCmd(String cmd) {
		_cmd = cmd;
	}

	public void setDisableInputs(boolean disableInputs) {
		_disableInputs = disableInputs;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setParameterMap(Map<String, String[]> parameterMap) {
		_parameterMap = parameterMap;
	}

	public void setType(String type) {
		_type = type;
	}

	@Override
	protected void cleanUp() {
		_cmd = null;
		_disableInputs = false;
		_parameterMap = null;
		_type = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-staging:content:cmd", _cmd);
		request.setAttribute(
			"liferay-staging:content:disableInputs", _disableInputs);
		request.setAttribute(
			"liferay-staging:content:parameterMap", _parameterMap);
		request.setAttribute("liferay-staging:content:type", _type);
	}

	private static final String _PAGE = "/content/page.jsp";

	private String _cmd;
	private boolean _disableInputs;
	private Map<String, String[]> _parameterMap;
	private String _type;

}
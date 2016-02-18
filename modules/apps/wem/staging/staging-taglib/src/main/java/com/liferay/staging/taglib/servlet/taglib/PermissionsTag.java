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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.staging.taglib.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.Collections;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Mate Thurzo
 */
public class PermissionsTag extends IncludeTag {

	public void setAction(String action) {
		if (action == null) {
			_action = StringPool.BLANK;
		}
		else {
			_action = action;
		}
	}

	public void setDescriptionCSSClass(String descriptionCSSClass) {
		if (descriptionCSSClass == null) {
			_descriptionCSSClass = StringPool.BLANK;
		}
		else {
			_descriptionCSSClass = descriptionCSSClass;
		}
	}

	public void setDisableInputs(boolean disableInputs) {
		_disableInputs = disableInputs;
	}

	public void setGlobal(boolean global) {
		_global = global;
	}

	public void setLabelCSSClass(String labelCSSClass) {
		if (labelCSSClass == null) {
			_labelCSSClass = StringPool.BLANK;
		}
		else {
			_labelCSSClass = labelCSSClass;
		}
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setParameterMap(Map<String, String[]> parameterMap) {
		if (parameterMap != null) {
			_parameterMap = parameterMap;
		}
		else {
			_parameterMap = Collections.emptyMap();
		}
	}

	@Override
	protected void cleanUp() {
		_action = StringPool.BLANK;
		_descriptionCSSClass = StringPool.BLANK;
		_disableInputs = false;
		_global = false;
		_labelCSSClass = StringPool.BLANK;
		_parameterMap = Collections.emptyMap();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-staging:permissions:action", _action);
		request.setAttribute(
			"liferay-staging:permissions:descriptionCSSClass",
			_descriptionCSSClass);
		request.setAttribute(
			"liferay-staging:permissions:disableInputs", _disableInputs);
		request.setAttribute("liferay-staging:deletions:global", _global);
		request.setAttribute(
			"liferay-staging:permissions:labelCSSClass", _labelCSSClass);
		request.setAttribute(
			"liferay-staging:permissions:parameterMap", _parameterMap);
	}

	private static final String _PAGE = "/permissions/page.jsp";

	private String _action = StringPool.BLANK;
	private String _descriptionCSSClass = StringPool.BLANK;
	private boolean _disableInputs;
	private boolean _global;
	private String _labelCSSClass = StringPool.BLANK;
	private Map<String, String[]> _parameterMap = Collections.emptyMap();

}
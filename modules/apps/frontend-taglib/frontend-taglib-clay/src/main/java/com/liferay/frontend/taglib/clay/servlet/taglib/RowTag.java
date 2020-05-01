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

package com.liferay.frontend.taglib.clay.servlet.taglib;

import com.liferay.frontend.taglib.clay.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Chema Balsas
 */
public class RowTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public String getClassName() {
		return _className;
	}

	public String getId() {
		return _id;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_id = null;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</div>");

		return EVAL_BODY_INCLUDE;
	}

	@Override
	protected int processStartTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div");

		jspWriter.write(" class=\"");
		jspWriter.write(_getClassName().toString());
		jspWriter.write("\"");

		if (Validator.isNotNull(_id)) {
			jspWriter.write(" id=\"");
			jspWriter.write(_id);
			jspWriter.write("\"");
		}

		jspWriter.write(">");

		return EVAL_BODY_INCLUDE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute("clay:container:className", _className);
		httpServletRequest.setAttribute("clay:container:id", _id);
	}

	private String _getClassName() {
		Set className = new LinkedHashSet();

		className.add("row");

		if (Validator.isNotNull(_className)) {
			className.addAll(StringUtil.split(_className, CharPool.SPACE));
		}

		return StringUtil.merge(className, StringPool.SPACE);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:row:";

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _END_PAGE = "/row/end.jsp";

	private static final String _START_PAGE = "/row/start.jsp";

	private String _className;
	private String _id;

}
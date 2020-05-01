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
public class ColTag extends IncludeTag {

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

	public String getLg() {
		return _lg;
	}

	public String getMd() {
		return _md;
	}

	public String getSize() {
		return _size;
	}

	public String getSm() {
		return _sm;
	}

	public String getXl() {
		return _xl;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLg(String lg) {
		_lg = lg;
	}

	public void setMd(String md) {
		_md = md;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setSize(String size) {
		_size = size;
	}

	public void setSm(String sm) {
		_sm = sm;
	}

	public void setXl(String xl) {
		_xl = xl;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_className = null;
		_id = null;
		_lg = null;
		_md = null;
		_size = null;
		_sm = null;
		_xl = null;
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
		httpServletRequest.setAttribute("clay:col:className", _className);
		httpServletRequest.setAttribute("clay:col:id", _id);
		httpServletRequest.setAttribute("clay:col:lg", _size);
		httpServletRequest.setAttribute("clay:col:md", _size);
		httpServletRequest.setAttribute("clay:col:size", _size);
		httpServletRequest.setAttribute("clay:col:sm", _size);
		httpServletRequest.setAttribute("clay:col:xl", _size);
	}

	private String _getClassName() {
		Set className = new LinkedHashSet();

		if (Validator.isNotNull(_size)) {
			className.add("col-" + _size);
		}

		if (Validator.isNotNull(_lg)) {
			className.add("col-lg-" + _lg);
		}

		if (Validator.isNotNull(_md)) {
			className.add("col-md-" + _md);
		}

		if (Validator.isNotNull(_sm)) {
			className.add("col-sm-" + _sm);
		}

		if (Validator.isNotNull(_xl)) {
			className.add("col-xl-" + _xl);
		}

		if (className.isEmpty()) {
			className.add("col");
		}

		if (Validator.isNotNull(_className)) {
			className.addAll(StringUtil.split(_className, CharPool.SPACE));
		}

		return StringUtil.merge(className, StringPool.SPACE);
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:col:";

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final String _END_PAGE = "/col/end.jsp";

	private static final String _START_PAGE = "/col/start.jsp";

	private String _className;
	private String _id;
	private String _lg;
	private String _md;
	private String _size;
	private String _sm;
	private String _xl;

}
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

package com.liferay.frontend.taglib.clay.internal.servlet.taglib;

import com.liferay.frontend.taglib.clay.internal.servlet.ServletContextUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.taglib.util.InlineUtil;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

/**
 * @author Chema Balsas
 */
public class BaseContainerTag extends IncludeTag {

	public String getClassName() {
		return _className;
	}

	public String getContainerElement() {
		return _containerElement;
	}

	public String getId() {
		return _id;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public void setContainerElement(String containerElement) {
		_containerElement = containerElement;
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
		_containerElement = "div";
		_id = null;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	protected String processClassName(Set<String> className) {
		if (Validator.isNotNull(_className)) {
			className.addAll(StringUtil.split(_className, CharPool.SPACE));
		}

		return StringUtil.merge(className, StringPool.SPACE);
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</");
		jspWriter.write(_containerElement);
		jspWriter.write(">");

		return EVAL_BODY_INCLUDE;
	}

	@Override
	protected int processStartTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<");
		jspWriter.write(_containerElement);
		jspWriter.write(" class=\"");
		jspWriter.write(processClassName(new LinkedHashSet<>()));
		jspWriter.write("\"");

		if (Validator.isNotNull(_id)) {
			jspWriter.write(" id=\"");
			jspWriter.write(_id);
			jspWriter.write("\"");
		}

		_writeDynamicAttributes(jspWriter);

		jspWriter.write(">");

		return EVAL_BODY_INCLUDE;
	}

	private void _writeDynamicAttributes(JspWriter jspWriter) throws Exception {
		String dynamicAttributesString = InlineUtil.buildDynamicAttributes(
			getDynamicAttributes());

		if (!dynamicAttributesString.isEmpty()) {
			jspWriter.write(dynamicAttributesString);
		}
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private String _className;
	private String _containerElement = "div";
	private String _id;

}
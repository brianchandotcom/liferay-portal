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

package com.liferay.frontend.taglib.servlet.taglib;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;
import com.liferay.taglib.util.InlineUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;

/**
 * @author Eudaldo Alonso
 */
public class ButtonRowTag extends IncludeTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setId(String id) {
		_id = id;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cssClass = null;
		_id = null;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</div>");

		return EVAL_PAGE;
	}

	@Override
	protected int processStartTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"sheet-footer ");

		String cssClass = _cssClass;

		if (cssClass != null) {
			jspWriter.write(cssClass);
		}

		jspWriter.write("\" ");

		String id = _id;

		if (id != null) {
			jspWriter.write("id=\"");
			jspWriter.write(id);
			jspWriter.write("\" ");
		}

		jspWriter.write(
			InlineUtil.buildDynamicAttributes(getDynamicAttributes()));

		jspWriter.write(">");

		return EVAL_BODY_INCLUDE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay.isStatePopUp()) {
			String cssClass = "dialog-footer";

			if (_cssClass != null) {
				cssClass = cssClass + StringPool.SPACE + _cssClass;
			}

			setCssClass(cssClass);
		}
	}

	private static final String _ATTRIBUTE_NAMESPACE =
		"liferay-frontend:button-row:";

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private String _cssClass;
	private String _id;

}
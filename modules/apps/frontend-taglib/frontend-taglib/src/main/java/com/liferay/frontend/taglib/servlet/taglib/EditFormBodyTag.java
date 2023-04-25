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

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

/**
 * @author Eudaldo Alonso
 */
public class EditFormBodyTag extends IncludeTag {

	@Override
	protected int processEndTag() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("</div>");

		if (!themeDisplay.isStatePopUp()) {
			return EVAL_BODY_INCLUDE;
		}

		jspWriter.write("</div>");

		return EVAL_BODY_INCLUDE;
	}

	@Override
	protected int processStartTag() throws Exception {
		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"lfr-form-content\">");

		if (!themeDisplay.isStatePopUp()) {
			return EVAL_BODY_INCLUDE;
		}

		EditFormTag editFormTag = (EditFormTag)findAncestorWithClass(
			this, EditFormTag.class);

		if ((editFormTag != null) && !editFormTag.isFluid() &&
			!themeDisplay.isStatePopUp()) {

			jspWriter.write("<div class=\"sheet sheet-lg\">");
		}
		else {
			jspWriter.write("<div class=\"c-pt-3 c-px-3\">");
		}

		return EVAL_BODY_INCLUDE;
	}

}
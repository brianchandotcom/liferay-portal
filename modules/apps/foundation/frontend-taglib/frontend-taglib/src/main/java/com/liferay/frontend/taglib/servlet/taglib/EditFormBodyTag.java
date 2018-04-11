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

import javax.servlet.jsp.JspWriter;

/**
 * @author Eudaldo Alonso
 */
public class EditFormBodyTag extends IncludeTag {

	@Override
	protected int processEndTag() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
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
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write("<div class=\"lfr-form-content\">");

		if (!themeDisplay.isStatePopUp()) {
			return EVAL_BODY_INCLUDE;
		}

		EditFormTag editFormTag = (EditFormTag)findAncestorWithClass(
			this, EditFormTag.class);

		String cssClass = "sheet";

		if ((editFormTag != null) && !editFormTag.isFluid()) {
			cssClass = "sheet sheet-lg";
		}

		jspWriter.write("<div class=\"" + cssClass + "\">");

		return EVAL_BODY_INCLUDE;
	}

}
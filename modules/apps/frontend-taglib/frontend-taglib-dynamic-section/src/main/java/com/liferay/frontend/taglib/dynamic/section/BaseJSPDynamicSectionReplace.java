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

package com.liferay.frontend.taglib.dynamic.section;

import com.liferay.petra.string.StringPool;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Matthew Tambara
 */
public abstract class BaseJSPDynamicSectionReplace
	implements DynamicSectionReplace {

	@Override
	public String replace(PageContext pageContext)
		throws IOException, ServletException {

		ServletContext servletContext = getServletContext();

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(getJspPath());

		requestDispatcher.include(
			pageContext.getRequest(),
			new PipingServletResponse(
				(HttpServletResponse)pageContext.getResponse(),
				pageContext.getOut()));

		return StringPool.BLANK;
	}

	protected abstract String getJspPath();

	protected abstract ServletContext getServletContext();

}
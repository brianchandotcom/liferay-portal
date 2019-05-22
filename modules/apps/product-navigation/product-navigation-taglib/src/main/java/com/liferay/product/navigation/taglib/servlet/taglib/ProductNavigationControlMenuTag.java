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

package com.liferay.product.navigation.taglib.servlet.taglib;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.product.navigation.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class ProductNavigationControlMenuTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void includePage(
			String page, HttpServletResponse httpServletResponse)
		throws IOException, ServletException {

		String layoutMode = ParamUtil.getString(
			getOriginalServletRequest(), "p_l_mode", Constants.VIEW);

		if (layoutMode.equals(Constants.PREVIEW)) {
			return;
		}

		super.includePage(page, httpServletResponse);
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return false;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
	}

	private static final String _PAGE = "/control_menu/page.jsp";

}
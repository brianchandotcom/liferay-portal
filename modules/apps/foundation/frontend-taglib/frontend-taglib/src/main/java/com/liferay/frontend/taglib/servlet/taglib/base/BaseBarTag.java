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

package com.liferay.frontend.taglib.servlet.taglib.base;

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.frontend.taglib.servlet.taglib.BarTag;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.jsp.PageContext;

/**
 * @author Roberto Díaz
 */
public abstract class BaseBarTag extends IncludeTag implements BarTag {

	public void setButtons(String buttons) {
		this.buttons = buttons;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		buttons = null;
	}

	protected String buttons;

}
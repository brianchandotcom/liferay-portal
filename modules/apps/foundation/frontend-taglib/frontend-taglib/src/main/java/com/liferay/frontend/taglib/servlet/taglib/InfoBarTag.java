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

import com.liferay.frontend.taglib.servlet.taglib.base.BaseBarTag;

import javax.servlet.jsp.JspException;

/**
 * @author Roberto Díaz
 */
public class InfoBarTag extends BaseBarTag {

	@Override
	public int doEndTag() throws JspException {
		request.setAttribute("liferay-frontend:info-bar:buttons", buttons);
		request.setAttribute("liferay-frontend:info-bar:fixed", _fixed);

		return super.doEndTag();
	}

	public void setFixed(boolean fixed) {
		_fixed = fixed;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_fixed = false;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	private static final String _END_PAGE = "/info_bar/end.jsp";

	private static final String _START_PAGE = "/info_bar/start.jsp";

	private boolean _fixed;

}
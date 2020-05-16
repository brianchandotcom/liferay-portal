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

import com.liferay.frontend.taglib.clay.internal.servlet.taglib.BaseContainerTag;

import java.util.Set;

import javax.servlet.jsp.JspException;

/**
 * @author Chema Balsas
 */
public class ContentColTag extends BaseContainerTag {

	@Override
	public int doStartTag() throws JspException {
		setAttributeNamespace(_ATTRIBUTE_NAMESPACE);

		return super.doStartTag();
	}

	public boolean getExpand() {
		return _expand;
	}

	public boolean getGutters() {
		return _gutters;
	}

	public void setExpand(boolean expand) {
		_expand = expand;
	}

	public void setGutters(boolean gutters) {
		_gutters = gutters;
	}

	@Override
	protected String _getClassName(Set className) {
		className.add("autofit-col");

		if (_expand) {
			className.add("autofit-col-expand");
		}

		if (_gutters) {
			className.add("autofit-col-gutters");
		}

		return super._getClassName(className);
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_expand = false;
		_gutters = false;
	}

	@Override
	protected String getEndPage() {
		return _END_PAGE;
	}

	@Override
	protected String getStartPage() {
		return _START_PAGE;
	}

	private static final String _ATTRIBUTE_NAMESPACE = "clay:content-col:";

	private static final String _END_PAGE = "/content_col/end.jsp";

	private static final String _START_PAGE = "/content_col/start.jsp";

	private boolean _expand;
	private boolean _gutters;

}
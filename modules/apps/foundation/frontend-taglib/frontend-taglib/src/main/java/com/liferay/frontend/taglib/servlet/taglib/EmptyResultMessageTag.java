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

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.frontend.taglib.servlet.taglib.util.EmptyResultMessageKeys;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class EmptyResultMessageTag extends IncludeTag {

	public void setActionDropdownItems(List<DropdownItem> actionDropdownItems) {
		_actionDropdownItems = actionDropdownItems;
	}

	public void setAnimationType(
		EmptyResultMessageKeys.AnimationType animationType) {

		_animationType = animationType;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public void setElementType(String elementType) {
		_elementType = elementType;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_actionDropdownItems = null;
		_animationType = EmptyResultMessageKeys.AnimationType.EMPTY;
		_description = null;
		_elementType = "element";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-frontend:empty-result-message:actionDropdownItems",
			_actionDropdownItems);
		request.setAttribute(
			"liferay-frontend:empty-result-message:animationTypeCssClass",
			EmptyResultMessageKeys.getAnimationTypeCssClass(_animationType));
		request.setAttribute(
			"liferay-frontend:empty-result-message:description", _description);
		request.setAttribute(
			"liferay-frontend:empty-result-message:elementType", _elementType);
	}

	private static final String _PAGE = "/empty_result_message/page.jsp";

	private List<DropdownItem> _actionDropdownItems;
	private EmptyResultMessageKeys.AnimationType _animationType =
		EmptyResultMessageKeys.AnimationType.EMPTY;
	private String _description;
	private String _elementType = "element";

}
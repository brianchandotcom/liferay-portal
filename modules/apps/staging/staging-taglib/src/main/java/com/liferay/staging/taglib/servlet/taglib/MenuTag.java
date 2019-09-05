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

package com.liferay.staging.taglib.servlet.taglib;

import com.liferay.staging.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 */
public class MenuTag extends IncludeTag {

	public String getCssClass() {
		return _cssClass;
	}

	public long getLayoutSetBranchId() {
		return _layoutSetBranchId;
	}

	public long getSelPlid() {
		return _selPlid;
	}

	public boolean isOnlyActions() {
		return _onlyActions;
	}

	public boolean isShowManageBranches() {
		return _showManageBranches;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutSetBranchId = layoutSetBranchId;
	}

	public void setOnlyActions(boolean onlyActions) {
		_onlyActions = onlyActions;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setSelPlid(long selPlid) {
		_selPlid = selPlid;
	}

	public void setShowManageBranches(boolean showManageBranches) {
		_showManageBranches = showManageBranches;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_cssClass = null;
		_layoutSetBranchId = 0;
		_onlyActions = false;
		_selPlid = 0;
		_showManageBranches = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-staging:menu:cssClass", _cssClass);
		httpServletRequest.setAttribute(
			"liferay-staging:menu:layoutSetBranchId",
			String.valueOf(_layoutSetBranchId));
		httpServletRequest.setAttribute(
			"liferay-staging:menu:onlyActions", String.valueOf(_onlyActions));
		httpServletRequest.setAttribute(
			"liferay-staging:menu:selPlid", String.valueOf(_selPlid));
		httpServletRequest.setAttribute(
			"liferay-staging:menu:showManageBranches",
			String.valueOf(_showManageBranches));
	}

	private static final String _PAGE = "/menu/page.jsp";

	private String _cssClass;
	private long _layoutSetBranchId;
	private boolean _onlyActions;
	private long _selPlid;
	private boolean _showManageBranches;

}
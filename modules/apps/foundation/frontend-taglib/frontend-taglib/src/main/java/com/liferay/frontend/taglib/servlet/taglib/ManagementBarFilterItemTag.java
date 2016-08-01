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

import com.liferay.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.taglib.util.IncludeTag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

/**
 * @author Eudaldo Alonso
 */
public class ManagementBarFilterItemTag extends IncludeTag {

	@Override
	public int doStartTag() {
		return EVAL_BODY_INCLUDE;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLabel(String label) {
		_label = label;
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		servletContext = ServletContextUtil.getServletContext();
	}

	public void setUrl(String url) {
		_url = url;
	}

	@Override
	protected void cleanUp() {
		_active = false;
		_id = null;
		_label = null;
		_url = null;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		ManagementBarNavigationTag managementBarNavigationTag =
			(ManagementBarNavigationTag)findAncestorWithClass(
				this, ManagementBarNavigationTag.class);

		List<ManagementBarFilterItem> managementBarFilterItems =
			managementBarNavigationTag.getManagementBarFilterItems();

		if (managementBarFilterItems != null) {
			ManagementBarFilterItem managementBarFilterItem =
				new ManagementBarFilterItem(_active, _id, _label, _url);

			managementBarFilterItems.add(managementBarFilterItem);
		}
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private boolean _active;
	private String _id;
	private String _label;
	private String _url;

}
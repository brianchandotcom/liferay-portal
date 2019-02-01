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

package com.liferay.layout.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.SearchContainerManagementToolbarDisplayContext;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectThemeManagementToolbarDisplayContext
	extends SearchContainerManagementToolbarDisplayContext {

	public SelectThemeManagementToolbarDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		HttpServletRequest request,
		SelectThemeDisplayContext selectThemeDisplayContext) {

		super(
			liferayPortletRequest, liferayPortletResponse, request,
			selectThemeDisplayContext.getThemesSearchContainer());

		_selectThemeDisplayContext = selectThemeDisplayContext;
	}

	@Override
	public String getComponentId() {
		return "siteAdminWebManagementToolbar";
	}

	@Override
	public List<ViewTypeItem> getViewTypeItems() {
		return new ViewTypeItemList(
			getPortletURL(), _selectThemeDisplayContext.getDisplayStyle()) {

			{
				addCardViewTypeItem();
				addListViewTypeItem();
				addTableViewTypeItem();
			}

		};
	}

	@Override
	public Boolean isSelectable() {
		return false;
	}

	@Override
	protected String[] getNavigationKeys() {
		return new String[] {"all"};
	}

	@Override
	protected String[] getOrderByKeys() {
		return new String[] {"name"};
	}

	private final SelectThemeDisplayContext _selectThemeDisplayContext;

}
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

package com.liferay.user.associated.data.web.internal.display.context;

import com.liferay.background.task.kernel.util.comparator.BackgroundTaskComparatorFactoryUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.user.associated.data.web.internal.export.background.task.UADExportBackgroundTaskManagerUtil;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class UADExportProcessDisplayContext {

	public UADExportProcessDisplayContext(
		HttpServletRequest request, RenderResponse renderResponse) {

		_request = request;
		_renderResponse = renderResponse;
	}

	public int getBackgroundTaskStatus(String status) {
		if (status.equals("failed")) {
			return BackgroundTaskConstants.STATUS_FAILED;
		}
		else if (status.equals("in-progress")) {
			return BackgroundTaskConstants.STATUS_IN_PROGRESS;
		}
		else if (status.equals("successful")) {
			return BackgroundTaskConstants.STATUS_SUCCESSFUL;
		}

		return 0;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_request, "navigation", "all");

		return _navigation;
	}

	public String getOrderByCol() {
		if (_orderByCol != null) {
			return _orderByCol;
		}

		_orderByCol = ParamUtil.getString(
			_request, "orderByCol", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (_orderByType != null) {
			return _orderByType;
		}

		_orderByType = ParamUtil.getString(_request, "orderByType", "desc");

		return _orderByType;
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/view_uad_export_processes");

		User selectedUser = PortalUtil.getSelectedUser(_request);

		portletURL.setParameter(
			"p_u_i_d", String.valueOf(selectedUser.getUserId()));

		portletURL.setParameter("navigation", getNavigation());

		return portletURL;
	}

	public SearchContainer getSearchContainer() throws PortalException {
		if (_searchContainer != null) {
			return _searchContainer;
		}

		PortletRequest portletRequest = (PortletRequest)_request.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);

		SearchContainer searchContainer = new SearchContainer(
			portletRequest, getPortletURL(), null,
			"no-personal-data-export-processes-were-found");

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());

		OrderByComparator<BackgroundTask> orderByComparator =
			BackgroundTaskComparatorFactoryUtil.
				getBackgroundTaskOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByComparator(orderByComparator);

		String navigation = getNavigation();

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		User selectedUser = PortalUtil.getSelectedUser(_request);

		if (navigation.equals("failed") || navigation.equals("in-progress") ||
			navigation.equals("successful")) {

			int status = getBackgroundTaskStatus(navigation);

			searchContainer.setTotal(
				UADExportBackgroundTaskManagerUtil.getBackgroundTasksCount(
					themeDisplay.getScopeGroupId(), selectedUser.getUserId(),
					status));

			searchContainer.setResults(
				UADExportBackgroundTaskManagerUtil.getBackgroundTasks(
					themeDisplay.getScopeGroupId(), selectedUser.getUserId(),
					status, searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator));
		}
		else {
			searchContainer.setTotal(
				UADExportBackgroundTaskManagerUtil.getBackgroundTasksCount(
					themeDisplay.getScopeGroupId(), selectedUser.getUserId()));

			searchContainer.setResults(
				UADExportBackgroundTaskManagerUtil.getBackgroundTasks(
					themeDisplay.getScopeGroupId(), selectedUser.getUserId(),
					searchContainer.getStart(), searchContainer.getEnd(),
					orderByComparator));
		}

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final RenderResponse _renderResponse;
	private final HttpServletRequest _request;
	private SearchContainer _searchContainer;

}
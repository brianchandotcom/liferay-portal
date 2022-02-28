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

package com.liferay.exportimport.web.internal.display.context;

import com.liferay.background.task.kernel.util.comparator.BackgroundTaskComparatorFactoryUtil;
import com.liferay.exportimport.constants.ExportImportPortletKeys;
import com.liferay.exportimport.kernel.background.task.BackgroundTaskExecutorNames;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.SearchDisplayStyleUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Mariano Álvaro Sáiz
 */
public class ExportLayoutsProcessesDisplayContext {

	public ExportLayoutsProcessesDisplayContext(
			HttpServletRequest httpServletRequest,
			LiferayPortletResponse liferayPortletResponse)
		throws PortalException {

		_httpServletRequest = httpServletRequest;
		_liferayPortletResponse = liferayPortletResponse;

		_portletRequest = (PortletRequest)_httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
	}

	public String getDisplayStyle() {
		if (Validator.isNotNull(_displayStyle)) {
			return _displayStyle;
		}

		_displayStyle = SearchDisplayStyleUtil.getDisplayStyle(
			_httpServletRequest, ExportImportPortletKeys.EXPORT,
			"layouts-processes-display-style", StringPool.BLANK);

		return _displayStyle;
	}

	public long getGroupId() {
		if (_groupId != null) {
			return _groupId;
		}

		_groupId = ParamUtil.getLong(_httpServletRequest, "groupId");

		return _groupId;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(_httpServletRequest, "navigation");

		return _navigation;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest, ExportImportPortletKeys.EXPORT,
			"layouts-processes-order-by-col", StringPool.BLANK);

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest, ExportImportPortletKeys.EXPORT,
			"layouts-processes-order-by-type", StringPool.BLANK);

		return _orderByType;
	}

	public PortletURL getPortletURL() {
		if (_portletURL != null) {
			return _portletURL;
		}

		_portletURL = PortletURLBuilder.createRenderURL(
			_liferayPortletResponse
		).setMVCRenderCommandName(
			"/export_import/view_export_layouts"
		).setNavigation(
			getNavigation()
		).setParameter(
			"displayStyle",
			ParamUtil.getString(_httpServletRequest, "displayStyle")
		).setParameter(
			"groupId", ParamUtil.getLong(_httpServletRequest, "groupId")
		).setParameter(
			"orderByCol", ParamUtil.getString(_httpServletRequest, "orderByCol")
		).setParameter(
			"orderByType",
			ParamUtil.getString(_httpServletRequest, "orderByType")
		).setParameter(
			"privateLayout",
			ParamUtil.getBoolean(_httpServletRequest, "privateLayout")
		).setParameter(
			"searchContainerId", getSearchContainerId()
		).buildPortletURL();

		return _portletURL;
	}

	public SearchContainer<BackgroundTask> getSearchContainer()
		throws Exception {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		_searchContainer = new SearchContainer<>(
			_portletRequest, getPortletURL(), null,
			"no-export-processes-were-found");

		_searchContainer.setId(getSearchContainerId());
		_searchContainer.setOrderByCol(getOrderByCol());
		_searchContainer.setOrderByComparator(
			BackgroundTaskComparatorFactoryUtil.
				getBackgroundTaskOrderByComparator(
					getOrderByCol(), getOrderByType()));
		_searchContainer.setOrderByType(getOrderByType());

		if (isNavigationHome()) {
			_searchContainer.setResultsAndTotal(
				() -> BackgroundTaskManagerUtil.getBackgroundTasks(
					getGroupId(),
					BackgroundTaskExecutorNames.
						LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR,
					_searchContainer.getStart(), _searchContainer.getEnd(),
					_searchContainer.getOrderByComparator()),
				BackgroundTaskManagerUtil.getBackgroundTasksCount(
					getGroupId(),
					BackgroundTaskExecutorNames.
						LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR));
		}
		else {
			_searchContainer.setResultsAndTotal(
				() -> BackgroundTaskManagerUtil.getBackgroundTasks(
					getGroupId(),
					BackgroundTaskExecutorNames.
						LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR,
					isNavigationCompleted(), _searchContainer.getStart(),
					_searchContainer.getEnd(),
					_searchContainer.getOrderByComparator()),
				BackgroundTaskManagerUtil.getBackgroundTasksCount(
					getGroupId(),
					BackgroundTaskExecutorNames.
						LAYOUT_EXPORT_BACKGROUND_TASK_EXECUTOR,
					isNavigationCompleted()));
		}

		_searchContainer.setRowChecker(
			new EmptyOnClickRowChecker(_liferayPortletResponse));

		return _searchContainer;
	}

	public String getSearchContainerId() {
		if (_searchContainerId != null) {
			return _searchContainerId;
		}

		_searchContainerId = ParamUtil.getString(
			_httpServletRequest, "searchContainerId");

		return _searchContainerId;
	}

	public boolean isNavigationCompleted() {
		if (Objects.equals(getNavigation(), "completed")) {
			return true;
		}

		return false;
	}

	public boolean isNavigationHome() {
		if (Objects.equals(getNavigation(), "all")) {
			return true;
		}

		return false;
	}

	private String _displayStyle;
	private Long _groupId;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final PortletRequest _portletRequest;
	private PortletURL _portletURL;
	private SearchContainer<BackgroundTask> _searchContainer;
	private String _searchContainerId;

}
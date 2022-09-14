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

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskConstants;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.portlet.SearchOrderByUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.user.associated.data.constants.UserAssociatedDataPortletKeys;
import com.liferay.user.associated.data.web.internal.export.background.task.UADExportBackgroundTaskManagerUtil;

import java.io.Serializable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Pei-Jung Lan
 */
public class UADExportProcessDisplayContext {

	public UADExportProcessDisplayContext(
		HttpServletRequest httpServletRequest, RenderResponse renderResponse) {

		_httpServletRequest = httpServletRequest;
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

	public Comparator<BackgroundTask> getComparator(
		String orderByCol, String orderByType) {

		Comparator<BackgroundTask> comparator = Comparator.comparing(
			BackgroundTask::getCreateDate);

		if (orderByCol.equals("name")) {
			comparator =
				(BackgroundTask backgroundTask1,
				 BackgroundTask backgroundTask2) -> {

					Map<String, Serializable> taskContextMap1 =
						backgroundTask1.getTaskContextMap();
					Map<String, Serializable> taskContextMap2 =
						backgroundTask2.getTaskContextMap();

					String applicationKey1 = (String)taskContextMap1.get(
						"applicationKey");
					String applicationKey2 = (String)taskContextMap2.get(
						"applicationKey");

					return applicationKey1.compareTo(applicationKey2);
				};
		}

		if (orderByType.equals("desc")) {
			comparator = comparator.reversed();
		}

		return comparator;
	}

	public String getNavigation() {
		if (_navigation != null) {
			return _navigation;
		}

		_navigation = ParamUtil.getString(
			_httpServletRequest, "navigation", "all");

		return _navigation;
	}

	public String getOrderByCol() {
		if (Validator.isNotNull(_orderByCol)) {
			return _orderByCol;
		}

		_orderByCol = SearchOrderByUtil.getOrderByCol(
			_httpServletRequest,
			UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
			"export-order-by-col", "create-date");

		return _orderByCol;
	}

	public String getOrderByType() {
		if (Validator.isNotNull(_orderByType)) {
			return _orderByType;
		}

		_orderByType = SearchOrderByUtil.getOrderByType(
			_httpServletRequest,
			UserAssociatedDataPortletKeys.USER_ASSOCIATED_DATA,
			"export-order-by-type", "desc");

		return _orderByType;
	}

	public PortletURL getPortletURL() throws PortalException {
		PortletRequest portletRequest =
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);
		PortletResponse portletResponse =
			(PortletResponse)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		return PortletURLBuilder.create(
			PortletURLUtil.getCurrent(
				PortalUtil.getLiferayPortletRequest(portletRequest),
				PortalUtil.getLiferayPortletResponse(portletResponse))
		).setMVCRenderCommandName(
			"/user_associated_data/view_uad_export_processes"
		).setNavigation(
			getNavigation()
		).setParameter(
			"orderByCol", getOrderByCol()
		).setParameter(
			"orderByType", getOrderByType()
		).setParameter(
			"p_u_i_d",
			() -> {
				User selectedUser = PortalUtil.getSelectedUser(
					_httpServletRequest);

				return selectedUser.getUserId();
			}
		).buildPortletURL();
	}

	public SearchContainer<BackgroundTask> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		PortletRequest portletRequest =
			(PortletRequest)_httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		SearchContainer<BackgroundTask> searchContainer = new SearchContainer(
			portletRequest, getPortletURL(), null,
			"no-personal-data-export-processes-were-found");

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByType(getOrderByType());

		String navigation = getNavigation();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		User selectedUser = PortalUtil.getSelectedUser(_httpServletRequest);

		if (navigation.equals("failed") || navigation.equals("in-progress") ||
			navigation.equals("successful")) {

			int status = getBackgroundTaskStatus(navigation);

			searchContainer.setResultsAndTotal(
				() -> {
					List<BackgroundTask> results =
						UADExportBackgroundTaskManagerUtil.getBackgroundTasks(
							themeDisplay.getScopeGroupId(),
							selectedUser.getUserId(), status);

					Stream<BackgroundTask> backgroundTaskStream =
						results.stream();

					return backgroundTaskStream.sorted(
						getComparator(
							searchContainer.getOrderByCol(),
							searchContainer.getOrderByType())
					).skip(
						searchContainer.getStart()
					).limit(
						searchContainer.getDelta()
					).collect(
						Collectors.toList()
					);
				},
				UADExportBackgroundTaskManagerUtil.getBackgroundTasksCount(
					themeDisplay.getScopeGroupId(), selectedUser.getUserId(),
					status));
		}
		else {
			searchContainer.setResultsAndTotal(
				() -> {
					List<BackgroundTask> results =
						UADExportBackgroundTaskManagerUtil.getBackgroundTasks(
							themeDisplay.getScopeGroupId(),
							selectedUser.getUserId());

					Stream<BackgroundTask> backgroundTaskStream =
						results.stream();

					return backgroundTaskStream.sorted(
						getComparator(
							searchContainer.getOrderByCol(),
							searchContainer.getOrderByType())
					).skip(
						searchContainer.getStart()
					).limit(
						searchContainer.getDelta()
					).collect(
						Collectors.toList()
					);
				},
				UADExportBackgroundTaskManagerUtil.getBackgroundTasksCount(
					themeDisplay.getScopeGroupId(), selectedUser.getUserId()));
		}

		_searchContainer = searchContainer;

		return _searchContainer;
	}

	private final HttpServletRequest _httpServletRequest;
	private String _navigation;
	private String _orderByCol;
	private String _orderByType;
	private final RenderResponse _renderResponse;
	private SearchContainer<BackgroundTask> _searchContainer;

}
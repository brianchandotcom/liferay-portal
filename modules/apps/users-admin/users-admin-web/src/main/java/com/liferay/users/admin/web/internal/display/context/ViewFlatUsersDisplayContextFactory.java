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

package com.liferay.users.admin.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.display.context.ManagementToolbarDisplayContext;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;
import com.liferay.users.admin.management.toolbar.FilterContributor;
import com.liferay.users.admin.web.internal.constants.UsersAdminWebKeys;
import com.liferay.users.admin.web.internal.util.DisplayStyleUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class ViewFlatUsersDisplayContextFactory {

	public static ViewFlatUsersDisplayContext create(
		HttpServletRequest httpServletRequest, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		ViewFlatUsersDisplayContext viewFlatUsersDisplayContext =
			new ViewFlatUsersDisplayContext();

		viewFlatUsersDisplayContext.setDisplayStyle(
			DisplayStyleUtil.getDisplayStyle(renderRequest, "list"));

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(renderRequest);
		LiferayPortletResponse liferayPortletResponse =
			PortalUtil.getLiferayPortletResponse(renderResponse);

		UserSearch searchContainer = _createSearchContainer(
			renderRequest, renderResponse);

		UserSearchTerms userSearchTerms =
			(UserSearchTerms)searchContainer.getSearchTerms();

		ManagementToolbarDisplayContext managementToolbarDisplayContext =
			new ViewFlatUsersManagementToolbarDisplayContext(
				liferayPortletRequest, liferayPortletResponse, searchContainer,
				_isShowDeleteButton(userSearchTerms),
				_isShowRestoreButton(userSearchTerms));

		Optional<FilterContributor[]> filterContributorsOptional =
			_getFilterContributorsOptional(httpServletRequest);

		if (filterContributorsOptional.isPresent()) {
			managementToolbarDisplayContext =
				new FiltersManagementToolbarDisplayContextWrapper(
					filterContributorsOptional.get(), httpServletRequest,
					liferayPortletRequest, liferayPortletResponse,
					managementToolbarDisplayContext);
		}

		viewFlatUsersDisplayContext.setManagementToolbarDisplayContext(
			managementToolbarDisplayContext);

		viewFlatUsersDisplayContext.setSearchContainer(searchContainer);
		viewFlatUsersDisplayContext.setStatus(userSearchTerms.getStatus());
		viewFlatUsersDisplayContext.setToolbarItem(
			ParamUtil.getString(
				httpServletRequest, "toolbarItem", "view-all-users"));
		viewFlatUsersDisplayContext.setUsersListView(
			GetterUtil.getString(
				httpServletRequest.getAttribute("view.jsp-usersListView")));
		viewFlatUsersDisplayContext.setViewUsersRedirect(
			GetterUtil.getString(
				httpServletRequest.getAttribute("view.jsp-viewUsersRedirect")));

		return viewFlatUsersDisplayContext;
	}

	private static UserSearch _createSearchContainer(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);

		int status = GetterUtil.getInteger(
			httpServletRequest.getAttribute("view.jsp-status"));

		String navigation = ParamUtil.getString(
			httpServletRequest, "navigation", "active");

		if (navigation.equals("active")) {
			status = WorkflowConstants.STATUS_APPROVED;
		}
		else if (navigation.equals("inactive")) {
			status = WorkflowConstants.STATUS_INACTIVE;
		}

		PortletURL portletURL = PortletURLBuilder.create(
			(PortletURL)httpServletRequest.getAttribute("view.jsp-portletURL")
		).setNavigation(
			navigation
		).buildPortletURL();

		UserSearch userSearch = new UserSearch(
			renderRequest, "cur2", portletURL);

		userSearch.setId("users");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		UserSearchTerms searchTerms =
			(UserSearchTerms)userSearch.getSearchTerms();

		searchTerms.setStatus(status);

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		Optional<FilterContributor[]> filterContributorsOptional =
			_getFilterContributorsOptional(httpServletRequest);

		if (filterContributorsOptional.isPresent()) {
			for (FilterContributor filterContributor :
					filterContributorsOptional.get()) {

				params.putAll(
					filterContributor.getSearchParameters(
						ParamUtil.getString(
							httpServletRequest,
							filterContributor.getParameter(),
							filterContributor.getDefaultValue())));
			}
		}

		int total = UserLocalServiceUtil.searchCount(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			searchTerms.getStatus(), params);

		userSearch.setTotal(total);

		List<User> results = UserLocalServiceUtil.search(
			themeDisplay.getCompanyId(), searchTerms.getKeywords(),
			searchTerms.getStatus(), params, userSearch.getStart(),
			userSearch.getEnd(), userSearch.getOrderByComparator());

		userSearch.setResults(results);

		if (!results.isEmpty() &&
			(_isShowDeleteButton(searchTerms) ||
			 _isShowRestoreButton(searchTerms))) {

			RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

			rowChecker.setRowIds("rowIdsUser");

			userSearch.setRowChecker(rowChecker);
		}

		return userSearch;
	}

	private static Optional<FilterContributor[]> _getFilterContributorsOptional(
		HttpServletRequest httpServletRequest) {

		return Optional.ofNullable(
			(FilterContributor[])httpServletRequest.getAttribute(
				UsersAdminWebKeys.MANAGEMENT_TOOLBAR_FILTER_CONTRIBUTORS));
	}

	private static boolean _isShowDeleteButton(
		UserSearchTerms userSearchTerms) {

		if ((userSearchTerms.getStatus() != WorkflowConstants.STATUS_ANY) &&
			(userSearchTerms.isActive() ||
			 (!userSearchTerms.isActive() && PropsValues.USERS_DELETE))) {

			return true;
		}

		return false;
	}

	private static boolean _isShowRestoreButton(
		UserSearchTerms userSearchTerms) {

		if ((userSearchTerms.getStatus() != WorkflowConstants.STATUS_ANY) &&
			!userSearchTerms.isActive()) {

			return true;
		}

		return false;
	}

}
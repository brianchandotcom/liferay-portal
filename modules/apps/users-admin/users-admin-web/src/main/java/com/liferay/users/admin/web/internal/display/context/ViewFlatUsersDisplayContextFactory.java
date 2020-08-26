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

import com.liferay.account.constants.AccountConstants;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Drew Brokke
 */
public class ViewFlatUsersDisplayContextFactory {

	public static ViewFlatUsersDisplayContext create(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		ViewFlatUsersDisplayContext viewFlatUsersDisplayContext =
			new ViewFlatUsersDisplayContext();

		LiferayPortletRequest liferayPortletRequest =
			PortalUtil.getLiferayPortletRequest(renderRequest);

		HttpServletRequest httpServletRequest =
			liferayPortletRequest.getOriginalHttpServletRequest();

		String displayStyle = ParamUtil.getString(
			httpServletRequest, "displayStyle");

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				liferayPortletRequest);

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				UsersAdminPortletKeys.USERS_ADMIN, "display-style", "list");
		}
		else {
			portalPreferences.setValue(
				UsersAdminPortletKeys.USERS_ADMIN, "display-style",
				displayStyle);

			httpServletRequest.setAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE, Boolean.TRUE);
		}

		viewFlatUsersDisplayContext.setDisplayStyle(displayStyle);

		UserSearch searchContainer = _createSearchContainer(
			renderRequest, renderResponse);

		UserSearchTerms userSearchTerms =
			(UserSearchTerms)searchContainer.getSearchTerms();

		viewFlatUsersDisplayContext.setManagementToolbarDisplayContext(
			new ViewFlatUsersManagementToolbarDisplayContext(
				liferayPortletRequest,
				PortalUtil.getLiferayPortletResponse(renderResponse),
				searchContainer, isShowDeleteButton(userSearchTerms),
				isShowRestoreButton(userSearchTerms)));

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

	protected static boolean isShowDeleteButton(
		UserSearchTerms userSearchTerms) {

		if ((userSearchTerms.getStatus() != WorkflowConstants.STATUS_ANY) &&
			(userSearchTerms.isActive() ||
			 (!userSearchTerms.isActive() && PropsValues.USERS_DELETE))) {

			return true;
		}

		return false;
	}

	protected static boolean isShowRestoreButton(
		UserSearchTerms userSearchTerms) {

		if ((userSearchTerms.getStatus() != WorkflowConstants.STATUS_ANY) &&
			!userSearchTerms.isActive()) {

			return true;
		}

		return false;
	}

	private static UserSearch _createSearchContainer(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(renderRequest);

		PortletURL portletURL = (PortletURL)httpServletRequest.getAttribute(
			"view.jsp-portletURL");

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

		portletURL.setParameter("navigation", navigation);

		UserSearch userSearch = new UserSearch(
			renderRequest, "cur2", portletURL);

		userSearch.setId("users");

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		UserSearchTerms searchTerms =
			(UserSearchTerms)userSearch.getSearchTerms();

		searchTerms.setStatus(status);

		String domain = ParamUtil.getString(
			httpServletRequest, "domain", "company-users");

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		if (domain.equals("company-users")) {
			params.put("accountEntryIds", new Long[0]);
		}
		else if (domain.equals("account-users")) {
			params.put(
				"accountEntryIds",
				new Long[] {AccountConstants.ACCOUNT_ENTRY_ID_ANY});
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
			(isShowDeleteButton(searchTerms) ||
			 isShowRestoreButton(searchTerms))) {

			RowChecker rowChecker = new EmptyOnClickRowChecker(renderResponse);

			rowChecker.setRowIds("rowIdsUser");

			userSearch.setRowChecker(rowChecker);
		}

		return userSearch;
	}

}
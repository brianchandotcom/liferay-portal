/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.usersadmin.action;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.struts.ActionConstants;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;
import com.liferay.portlet.usersadmin.util.UsersAdminUtil;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 */
public class ExportUsersAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			String csv = getUsersCSV(actionRequest, actionResponse);

			String fileName = "users.csv";
			byte[] bytes = csv.getBytes();

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			ServletResponseUtil.sendFile(
				request, response, fileName, bytes, ContentTypes.TEXT_CSV_UTF8);

			setForward(actionRequest, ActionConstants.COMMON_NULL);
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass());

			setForward(actionRequest, "portlet.users_admin.error");
		}
	}

	protected String getUserCSV(User user) {
		StringBundler sb = new StringBundler(
			PropsValues.USERS_EXPORT_CSV_FIELDS.length * 2);

		for (int i = 0; i < PropsValues.USERS_EXPORT_CSV_FIELDS.length; i++) {
			String field = PropsValues.USERS_EXPORT_CSV_FIELDS[i];

			if (field.equals("fullName")) {
				sb.append(CSVUtil.encode(user.getFullName()));
			}
			else if (field.startsWith("expando:")) {
				String attributeName = field.substring(8);

				ExpandoBridge expandoBridge = user.getExpandoBridge();

				sb.append(
					CSVUtil.encode(expandoBridge.getAttribute(attributeName)));
			}
			else {
				sb.append(
					CSVUtil.encode(BeanPropertiesUtil.getString(user, field)));
			}

			if ((i + 1) < PropsValues.USERS_EXPORT_CSV_FIELDS.length) {
				sb.append(StringPool.COMMA);
			}
		}

		sb.append(StringPool.NEW_LINE);

		return sb.toString();
	}

	protected List<User> getUsers(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		boolean exportAllUsers = PortalPermissionUtil.contains(
			permissionChecker, ActionKeys.EXPORT_USER);

		if (!exportAllUsers &&
			!PortletPermissionUtil.contains(
				permissionChecker, PortletKeys.USERS_ADMIN,
				ActionKeys.EXPORT_USER)) {

			return Collections.emptyList();
		}

		PortletURL portletURL =
			((ActionResponseImpl)actionResponse).createRenderURL(
				PortletKeys.USERS_ADMIN);

		UserSearch userSearch = new UserSearch(actionRequest, portletURL);

		UserSearchTerms searchTerms =
			(UserSearchTerms)userSearch.getSearchTerms();

		LinkedHashMap<String, Object> params =
			new LinkedHashMap<String, Object>();

		long organizationId = searchTerms.getOrganizationId();

		if (organizationId > 0) {
			params.put("usersOrgs", new Long(organizationId));
		}
		else if (!exportAllUsers) {
			User user = themeDisplay.getUser();

			Long[] organizationIds = ArrayUtil.toArray(
				user.getOrganizationIds(true));

			if (organizationIds.length > 0) {
				params.put("usersOrgs", organizationIds);
			}
		}

		long roleId = searchTerms.getRoleId();

		if (roleId > 0) {
			params.put("usersRoles", new Long(roleId));
		}

		long userGroupId = searchTerms.getUserGroupId();

		if (userGroupId > 0) {
			params.put("usersUserGroups", new Long(userGroupId));
		}

		if (PropsValues.USERS_INDEXER_ENABLED &&
			PropsValues.USERS_SEARCH_WITH_INDEX) {

			params.put("expandoAttributes", searchTerms.getKeywords());

			Hits hits = null;

			if (searchTerms.isAdvancedSearch()) {
				hits = UserLocalServiceUtil.search(
					themeDisplay.getCompanyId(), searchTerms.getFirstName(),
					searchTerms.getMiddleName(), searchTerms.getLastName(),
					searchTerms.getScreenName(), searchTerms.getEmailAddress(),
					searchTerms.getStatus(), params,
					searchTerms.isAndOperator(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, (Sort)null);
			}
			else {
				hits = UserLocalServiceUtil.search(
					themeDisplay.getCompanyId(), searchTerms.getKeywords(),
					searchTerms.getStatus(), params, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, (Sort)null);
			}

			Tuple tuple = UsersAdminUtil.getUsers(hits);

			return (List<User>)tuple.getObject(0);
		}
		else {
			if (searchTerms.isAdvancedSearch()) {
				return UserLocalServiceUtil.search(
					themeDisplay.getCompanyId(), searchTerms.getFirstName(),
					searchTerms.getMiddleName(), searchTerms.getLastName(),
					searchTerms.getScreenName(), searchTerms.getEmailAddress(),
					searchTerms.getStatus(), params,
					searchTerms.isAndOperator(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, (OrderByComparator)null);
			}
			else {
				return UserLocalServiceUtil.search(
					themeDisplay.getCompanyId(), searchTerms.getKeywords(),
					searchTerms.getStatus(), params, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, (OrderByComparator)null);
			}
		}
	}

	protected String getUsersCSV(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		List<User> users = getUsers(actionRequest, actionResponse);

		if (users.isEmpty()) {
			return StringPool.BLANK;
		}

		String exportProgressId = ParamUtil.getString(
			actionRequest, "exportProgressId");

		ProgressTracker progressTracker = new ProgressTracker(
			actionRequest, exportProgressId);

		progressTracker.start();

		int percentage = 10;
		int total = users.size();

		progressTracker.setPercent(percentage);

		StringBundler sb = new StringBundler(users.size());

		for (int i = 0; i < users.size(); i++ ) {
			User user = users.get(i);

			sb.append(getUserCSV(user));

			percentage = Math.min(10 + (i * 90) / total, 99);

			progressTracker.setPercent(percentage);
		}

		progressTracker.finish();

		return sb.toString();
	}

}
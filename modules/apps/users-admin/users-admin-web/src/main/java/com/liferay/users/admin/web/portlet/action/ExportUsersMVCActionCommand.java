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

package com.liferay.users.admin.web.portlet.action;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.portlet.DynamicActionRequest;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Mika Koivisto
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + UsersAdminPortletKeys.USERS_ADMIN,
		"mvc.command.name=/users_admin/export_users"
	},
	service = MVCActionCommand.class
)
public class ExportUsersMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			hideDefaultSuccessMessage(actionRequest);

			String keywords = ParamUtil.getString(actionRequest, "keywords");

			if (Validator.isNotNull(keywords)) {
				DynamicActionRequest dynamicActionRequest =
					new DynamicActionRequest(actionRequest);

				dynamicActionRequest.setParameter("keywords", StringPool.BLANK);

				actionRequest = dynamicActionRequest;
			}

			String csv = getUsersCSV(actionRequest, actionResponse);

			String fileName = "users.csv";
			byte[] bytes = csv.getBytes();

			HttpServletRequest request = PortalUtil.getHttpServletRequest(
				actionRequest);
			HttpServletResponse response = PortalUtil.getHttpServletResponse(
				actionResponse);

			ServletResponseUtil.sendFile(
				request, response, fileName, bytes, ContentTypes.TEXT_CSV_UTF8);

			actionResponse.setRenderParameter("mvcPath", "/null.jsp");
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
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
				permissionChecker, UsersAdminPortletKeys.USERS_ADMIN,
				ActionKeys.EXPORT_USER)) {

			return Collections.emptyList();
		}

		PortletURL portletURL =
			((ActionResponseImpl)actionResponse).createRenderURL(
				UsersAdminPortletKeys.USERS_ADMIN);

		UserSearch userSearch = new UserSearch(actionRequest, portletURL);

		UserSearchTerms searchTerms =
			(UserSearchTerms)userSearch.getSearchTerms();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		long organizationId = searchTerms.getOrganizationId();

		if (organizationId > 0) {
			params.put("usersOrgs", Long.valueOf(organizationId));
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
			params.put("usersRoles", Long.valueOf(roleId));
		}

		long userGroupId = searchTerms.getUserGroupId();

		if (userGroupId > 0) {
			params.put("usersUserGroups", Long.valueOf(userGroupId));
		}

		Indexer<?> indexer = IndexerRegistryUtil.nullSafeGetIndexer(User.class);

		if (indexer.isIndexerEnabled() && PropsValues.USERS_SEARCH_WITH_INDEX) {
			params.put("expandoAttributes", searchTerms.getKeywords());
		}

		if (searchTerms.isAdvancedSearch()) {
			return _userLocalService.search(
				themeDisplay.getCompanyId(), searchTerms.getFirstName(),
				searchTerms.getMiddleName(), searchTerms.getLastName(),
				searchTerms.getScreenName(), searchTerms.getEmailAddress(),
				searchTerms.getStatus(), params, searchTerms.isAndOperator(),
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				(OrderByComparator<User>)null);
		}
		else {
			return _userLocalService.search(
				themeDisplay.getCompanyId(), searchTerms.getKeywords(),
				searchTerms.getStatus(), params, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, (OrderByComparator<User>)null);
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

		ProgressTracker progressTracker = new ProgressTracker(exportProgressId);

		progressTracker.start(actionRequest);

		int percentage = 10;
		int total = users.size();

		progressTracker.setPercent(percentage);

		StringBundler sb = new StringBundler(users.size());

		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);

			sb.append(getUserCSV(user));

			percentage = Math.min(10 + (i * 90) / total, 99);

			progressTracker.setPercent(percentage);
		}

		progressTracker.finish(actionRequest);

		return sb.toString();
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private UserLocalService _userLocalService;

}
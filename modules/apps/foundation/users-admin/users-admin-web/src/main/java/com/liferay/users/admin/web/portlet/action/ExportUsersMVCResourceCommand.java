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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.DynamicResourceRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CSVUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.usersadmin.search.UserSearch;
import com.liferay.portlet.usersadmin.search.UserSearchTerms;
import com.liferay.users.admin.constants.UsersAdminPortletKeys;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

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
	service = MVCResourceCommand.class
)
public class ExportUsersMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		try {
			SessionMessages.add(
				resourceRequest,
				_portal.getPortletId(resourceRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);

			String keywords = ParamUtil.getString(resourceRequest, "keywords");

			if (Validator.isNotNull(keywords)) {
				DynamicResourceRequest dynamicResourceRequest =
					new DynamicResourceRequest(resourceRequest);

				dynamicResourceRequest.setParameter(
					"keywords", StringPool.BLANK);

				resourceRequest = dynamicResourceRequest;
			}

			String csv = getUsersCSV(resourceRequest, resourceResponse);

			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse, "users.csv", csv.getBytes(),
				ContentTypes.TEXT_CSV_UTF8);
		}
		catch (Exception e) {
			SessionErrors.add(resourceRequest, e.getClass());

			_log.error(e, e);
		}
	}

	protected String getUserCSV(User user) {
		StringBundler sb = new StringBundler(
			PropsValues.USERS_EXPORT_CSV_FIELDS.length * 2);

		for (int i = 0; i < PropsValues.USERS_EXPORT_CSV_FIELDS.length; i++) {
			String field = PropsValues.USERS_EXPORT_CSV_FIELDS[i];

			if (field.contains("Date")) {
				Date date = (Date)BeanPropertiesUtil.getObject(user, field);

				if (date instanceof Timestamp) {
					date = new Date(date.getTime());
				}

				sb.append(CSVUtil.encode(String.valueOf(date)));
			}
			else if (field.startsWith("expando:")) {
				String attributeName = field.substring(8);

				ExpandoBridge expandoBridge = user.getExpandoBridge();

				sb.append(
					CSVUtil.encode(expandoBridge.getAttribute(attributeName)));
			}
			else if (field.equals("fullName")) {
				sb.append(CSVUtil.encode(user.getFullName()));
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
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
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

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(resourceResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL(
			UsersAdminPortletKeys.USERS_ADMIN);

		UserSearch userSearch = new UserSearch(resourceRequest, portletURL);

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
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		List<User> users = getUsers(resourceRequest, resourceResponse);

		if (users.isEmpty()) {
			return StringPool.BLANK;
		}

		String exportProgressId = ParamUtil.getString(
			resourceRequest, "exportProgressId");

		ProgressTracker progressTracker = new ProgressTracker(exportProgressId);

		progressTracker.start(resourceRequest);

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

		progressTracker.finish(resourceRequest);

		return sb.toString();
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportUsersMVCResourceCommand.class);

	@Reference
	private Portal _portal;

	private UserLocalService _userLocalService;

}
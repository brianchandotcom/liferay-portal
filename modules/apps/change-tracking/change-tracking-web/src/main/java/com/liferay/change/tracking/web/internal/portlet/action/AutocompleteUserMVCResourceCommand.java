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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/autocomplete_user"
	},
	service = MVCResourceCommand.class
)
public class AutocompleteUserMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			_getUsersJSONArray(httpServletRequest));
	}

	private List<User> _getUsers(
		HttpServletRequest httpServletRequest, ThemeDisplay themeDisplay) {

		String query = ParamUtil.getString(httpServletRequest, "query");

		if (Validator.isNull(query)) {
			return Collections.emptyList();
		}

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			return _userLocalService.search(
				themeDisplay.getCompanyId(), query,
				WorkflowConstants.STATUS_APPROVED, new LinkedHashMap<>(), 0, 20,
				new UserScreenNameComparator());
		}

		User user = themeDisplay.getUser();

		if (ArrayUtil.isEmpty(user.getGroupIds())) {
			return Collections.emptyList();
		}

		return _userLocalService.searchSocial(
			themeDisplay.getCompanyId(), user.getGroupIds(), query, 0, 20,
			new UserScreenNameComparator());
	}

	private JSONArray _getUsersJSONArray(HttpServletRequest httpServletRequest)
		throws Exception {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		for (User user : _getUsers(httpServletRequest, themeDisplay)) {
			if (user.isDefaultUser() ||
				(themeDisplay.getUserId() == user.getUserId())) {

				continue;
			}

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			boolean hasPermission = false;

			if (_portletPermission.contains(
					permissionChecker, CTPortletKeys.PUBLICATIONS,
					ActionKeys.ACCESS_IN_CONTROL_PANEL) &&
				_portletPermission.contains(
					permissionChecker, CTPortletKeys.PUBLICATIONS,
					ActionKeys.VIEW)) {

				hasPermission = true;
			}

			JSONObject jsonObject = JSONUtil.put(
				"emailAddress", user.getEmailAddress()
			).put(
				"fullName", user.getFullName()
			).put(
				"hasPermission", hasPermission
			);

			String portraitURL = StringPool.BLANK;

			if (user.getPortraitId() > 0) {
				portraitURL = user.getPortraitURL(themeDisplay);
			}

			jsonObject.put(
				"portraitURL", portraitURL
			).put(
				"userId", Long.valueOf(user.getUserId())
			);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Reference
	private Portal _portal;

	@Reference
	private PortletPermission _portletPermission;

	@Reference
	private UserLocalService _userLocalService;

}
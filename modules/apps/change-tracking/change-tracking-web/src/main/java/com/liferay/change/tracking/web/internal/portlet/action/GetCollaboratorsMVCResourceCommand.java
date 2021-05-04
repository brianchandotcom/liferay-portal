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

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.constants.PublicationRoleConstants;
import com.liferay.change.tracking.web.internal.display.BasePersistenceRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.io.Serializable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
		"mvc.command.name=/change_tracking/get_collaborators"
	},
	service = MVCResourceCommand.class
)
public class GetCollaboratorsMVCResourceCommand extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortalException {

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ParamUtil.getLong(resourceRequest, "ctCollectionId"));

		if (ctCollection == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONFactoryUtil.createJSONArray());

			return;
		}

		Group group = ctCollection.getGroup();

		if (group == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONFactoryUtil.createJSONArray());

			return;
		}

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.getUserGroupRolesByGroup(
				group.getGroupId());

		Set<Long> roleIds = new HashSet<>();
		Set<Long> userIds = new HashSet<>();

		for (UserGroupRole userGroupRole : userGroupRoles) {
			roleIds.add(userGroupRole.getRoleId());
			userIds.add(userGroupRole.getUserId());
		}

		Map<Serializable, Role> roleMap =
			_basePersistenceRegistry.fetchBaseModelMap(
				_portal.getClassNameId(Role.class.getName()), roleIds);
		Map<Serializable, User> userMap =
			_basePersistenceRegistry.fetchBaseModelMap(
				_portal.getClassNameId(User.class.getName()), userIds);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		for (UserGroupRole userGroupRole : userGroupRoles) {
			Role role = roleMap.get(userGroupRole.getRoleId());
			User user = userMap.get(userGroupRole.getUserId());

			if ((role == null) || (user == null) ||
				(user.getUserId() == ctCollection.getUserId())) {

				continue;
			}

			String portraitURL = StringPool.BLANK;

			if (user.getPortraitId() > 0) {
				portraitURL = user.getPortraitURL(themeDisplay);
			}

			jsonArray.put(
				JSONUtil.put(
					"emailAddress", user.getEmailAddress()
				).put(
					"fullName", user.getFullName()
				).put(
					"isCurrentUser",
					user.getUserId() == themeDisplay.getUserId()
				).put(
					"isOwner", false
				).put(
					"portraitURL", portraitURL
				).put(
					"roleId",
					PublicationRoleConstants.getNameRole(role.getName())
				).put(
					"roleLabel",
					_language.get(
						httpServletRequest,
						PublicationRoleConstants.getNameLabel(role.getName()))
				).put(
					"userId", Long.valueOf(user.getUserId())
				));
		}

		User owner = _userLocalService.fetchUser(ctCollection.getUserId());

		if (owner != null) {
			String portraitURL = StringPool.BLANK;

			if (owner.getPortraitId() > 0) {
				portraitURL = owner.getPortraitURL(themeDisplay);
			}

			jsonArray.put(
				JSONUtil.put(
					"emailAddress", owner.getEmailAddress()
				).put(
					"fullName", owner.getFullName()
				).put(
					"isCurrentUser",
					owner.getUserId() == themeDisplay.getUserId()
				).put(
					"isOwner", true
				).put(
					"portraitURL", portraitURL
				).put(
					"userId", Long.valueOf(owner.getUserId())
				));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	@Reference
	private BasePersistenceRegistry _basePersistenceRegistry;

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPermission _portletPermission;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
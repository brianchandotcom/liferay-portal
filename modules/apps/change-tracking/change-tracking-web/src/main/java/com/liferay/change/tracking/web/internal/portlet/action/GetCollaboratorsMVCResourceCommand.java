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

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.web.internal.constants.PublicationRoleConstants;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleTable;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.UserGroupRoleTable;
import com.liferay.portal.kernel.model.UserTable;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
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
				_jsonFactory.createJSONArray());

			return;
		}

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

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
					"userId", owner.getUserId()
				));
		}

		Group group = _groupLocalService.fetchGroup(
			ctCollection.getCompanyId(),
			_portal.getClassNameId(CTCollection.class),
			ctCollection.getCtCollectionId());

		if (group == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse, jsonArray);

			return;
		}

		Map<Long, Role> roleMap = new HashMap<>();

		for (Role role :
				_roleLocalService.<List<Role>>dslQuery(
					DSLQueryFactoryUtil.select(
						RoleTable.INSTANCE
					).from(
						RoleTable.INSTANCE
					).innerJoinON(
						UserGroupRoleTable.INSTANCE,
						UserGroupRoleTable.INSTANCE.roleId.eq(
							RoleTable.INSTANCE.roleId)
					).where(
						UserGroupRoleTable.INSTANCE.groupId.eq(
							group.getGroupId())
					))) {

			roleMap.put(role.getRoleId(), role);
		}

		Map<Long, User> userMap = new HashMap<>();

		for (User user :
				_userLocalService.<List<User>>dslQuery(
					DSLQueryFactoryUtil.select(
						UserTable.INSTANCE
					).from(
						UserTable.INSTANCE
					).innerJoinON(
						UserGroupRoleTable.INSTANCE,
						UserGroupRoleTable.INSTANCE.userId.eq(
							UserTable.INSTANCE.userId)
					).where(
						UserGroupRoleTable.INSTANCE.groupId.eq(
							group.getGroupId())
					))) {

			userMap.put(user.getUserId(), user);
		}

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		for (UserGroupRole userGroupRole :
				_userGroupRoleLocalService.getUserGroupRolesByGroup(
					group.getGroupId())) {

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
					"roleLabel",
					_language.get(
						httpServletRequest, _getNameLabel(role.getName()))
				).put(
					"roleValue", _getNameRole(role.getName())
				).put(
					"userId", user.getUserId()
				));
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse, jsonArray);
	}

	private String _getNameLabel(String name) {
		if (name.equals(PublicationRoleConstants.NAME_ADMIN)) {
			return PublicationRoleConstants.LABEL_ADMIN;
		}
		else if (name.equals(PublicationRoleConstants.NAME_EDITOR)) {
			return PublicationRoleConstants.LABEL_EDITOR;
		}
		else if (name.equals(PublicationRoleConstants.NAME_PUBLISHER)) {
			return PublicationRoleConstants.LABEL_PUBLISHER;
		}

		return PublicationRoleConstants.LABEL_VIEWER;
	}

	private int _getNameRole(String name) {
		if (name.equals(PublicationRoleConstants.NAME_ADMIN)) {
			return PublicationRoleConstants.ROLE_ADMIN;
		}
		else if (name.equals(PublicationRoleConstants.NAME_EDITOR)) {
			return PublicationRoleConstants.ROLE_EDITOR;
		}
		else if (name.equals(PublicationRoleConstants.NAME_PUBLISHER)) {
			return PublicationRoleConstants.ROLE_PUBLISHER;
		}

		return PublicationRoleConstants.ROLE_VIEWER;
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
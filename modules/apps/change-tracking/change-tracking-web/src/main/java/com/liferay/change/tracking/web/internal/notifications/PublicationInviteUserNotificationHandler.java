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

package com.liferay.change.tracking.web.internal.notifications;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.web.internal.constants.PublicationRoleConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.notifications.UserNotificationHandler;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	property = "javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
	service = UserNotificationHandler.class
)
public class PublicationInviteUserNotificationHandler
	extends BaseUserNotificationHandler {

	public PublicationInviteUserNotificationHandler() {
		setPortletId(CTPortletKeys.PUBLICATIONS);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		long ctCollectionId = jsonObject.getLong("classPK");

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		int roleValue = jsonObject.getInt("roleValue");

		Group group = null;

		if (ctCollection != null) {
			group = _groupLocalService.fetchGroup(
				ctCollection.getCompanyId(),
				_portal.getClassNameId(CTCollection.class), ctCollectionId);
		}

		Role role = null;

		if (group != null) {
			role = _roleLocalService.fetchRole(
				ctCollection.getCompanyId(),
				PublicationRoleConstants.getRoleName(roleValue));
		}

		UserGroupRole userGroupRole = null;

		if (role != null) {
			userGroupRole = _userGroupRoleLocalService.fetchUserGroupRole(
				userNotificationEvent.getUserId(), group.getGroupId(),
				role.getRoleId());
		}

		if (userGroupRole == null) {
			_userNotificationEventLocalService.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}

		String userName = jsonObject.getString("userName");

		return _language.format(
			serviceContext.getLocale(), "x-has-invited-you-to-work-on-x-as-a-x",
			new Object[] {
				userName, ctCollection.getName(),
				_language.get(
					serviceContext.getLocale(), _getRoleLabel(roleValue))
			},
			false);
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws PortalException {

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			userNotificationEvent.getPayload());

		long ctCollectionId = jsonObject.getLong("classPK");

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if (ctCollection == null) {
			return null;
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				serviceContext.getRequest(), serviceContext.getScopeGroup(),
				CTPortletKeys.PUBLICATIONS, 0, 0, PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/change_tracking/view_changes"
		).setParameter(
			"ctCollectionId", ctCollectionId
		).buildString();
	}

	private String _getRoleLabel(int role) {
		if (role == PublicationRoleConstants.ROLE_ADMIN) {
			return PublicationRoleConstants.LABEL_ADMIN;
		}
		else if (role == PublicationRoleConstants.ROLE_EDITOR) {
			return PublicationRoleConstants.LABEL_EDITOR;
		}
		else if (role == PublicationRoleConstants.ROLE_PUBLISHER) {
			return PublicationRoleConstants.LABEL_PUBLISHER;
		}

		return PublicationRoleConstants.LABEL_VIEWER;
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
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}
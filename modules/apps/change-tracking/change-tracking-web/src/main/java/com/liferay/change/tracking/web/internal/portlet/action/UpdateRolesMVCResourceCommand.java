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
import com.liferay.change.tracking.web.internal.security.permission.resource.CTCollectionPermission;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletException;
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
		"mvc.command.name=/change_tracking/update_roles"
	},
	service = AopService.class
)
public class UpdateRolesMVCResourceCommand
	extends BaseMVCResourceCommand implements AopService, MVCResourceCommand {

	@Override
	@Transactional(
		propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class
	)
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		return super.serveResource(resourceRequest, resourceResponse);
	}

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortalException {

		HttpServletRequest httpServletRequest = portal.getHttpServletRequest(
			resourceRequest);

		CTCollection ctCollection = ctCollectionLocalService.fetchCTCollection(
			ParamUtil.getLong(resourceRequest, "ctCollectionId"));

		if (ctCollection == null) {
			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"errorMessage",
					language.get(
						httpServletRequest,
						"this-publication-no-longer-exists")));

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!CTCollectionPermission.contains(
				themeDisplay.getPermissionChecker(), ctCollection,
				ActionKeys.PERMISSIONS)) {

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"errorMessage",
					language.get(
						httpServletRequest,
						"you-do-not-have-permission-to-update-permissions-" +
							"for-this-publication")));

			return;
		}

		int[] roleValues = ParamUtil.getIntegerValues(
			resourceRequest, "roleValues");

		long[] userIds = ParamUtil.getLongValues(resourceRequest, "userIds");

		userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, ctCollection.getGroupId());

		for (int i = 0; i < userIds.length; i++) {
			if (roleValues[i] < 0) {
				continue;
			}

			Role role = getRole(roleValues[i], themeDisplay);

			userGroupRoleLocalService.addUserGroupRole(
				userIds[i], ctCollection.getGroupId(), role.getRoleId());

			sendNotificationEvent(
				ctCollection, userIds[i], roleValues[i], themeDisplay);
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"successMessage",
				language.get(
					httpServletRequest,
					"permissions-were-updated-successfully")));
	}

	protected Role getRole(int roleValue, ThemeDisplay themeDisplay)
		throws PortalException {

		String name = PublicationRoleConstants.getRoleName(roleValue);

		Role role = roleLocalService.fetchRole(
			themeDisplay.getCompanyId(), name);

		if (role == null) {
			role = roleLocalService.addRole(
				themeDisplay.getDefaultUserId(), null, 0, name,
				HashMapBuilder.put(
					LocaleUtil.getDefault(), name
				).build(),
				null, RoleConstants.TYPE_PUBLICATION, null, null);

			for (String actionId :
					PublicationRoleConstants.getModelResourceActions(
						roleValue)) {

				resourcePermissionLocalService.addResourcePermission(
					themeDisplay.getCompanyId(), CTCollection.class.getName(),
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					role.getRoleId(), actionId);
			}
		}

		return role;
	}

	protected void sendNotificationEvent(
			CTCollection ctCollection, long receiverUserId, int roleValue,
			ThemeDisplay themeDisplay)
		throws PortalException {

		if (UserNotificationManagerUtil.isDeliver(
				receiverUserId, CTPortletKeys.PUBLICATIONS, 0,
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

			User user = themeDisplay.getUser();

			NotificationEvent notificationEvent =
				NotificationEventFactoryUtil.createNotificationEvent(
					System.currentTimeMillis(), CTPortletKeys.PUBLICATIONS,
					JSONUtil.put(
						"classPK", ctCollection.getCtCollectionId()
					).put(
						"roleValue", roleValue
					).put(
						"userId", user.getUserId()
					).put(
						"userName", user.getFullName()
					));

			notificationEvent.setDeliveryType(
				UserNotificationDeliveryConstants.TYPE_WEBSITE);

			userNotificationEventLocalService.addUserNotificationEvent(
				receiverUserId, notificationEvent);
		}
	}

	@Reference
	protected CTCollectionLocalService ctCollectionLocalService;

	@Reference
	protected Language language;

	@Reference
	protected Portal portal;

	@Reference
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Reference
	protected RoleLocalService roleLocalService;

	@Reference
	protected UserGroupRoleLocalService userGroupRoleLocalService;

	@Reference
	protected UserNotificationEventLocalService
		userNotificationEventLocalService;

}
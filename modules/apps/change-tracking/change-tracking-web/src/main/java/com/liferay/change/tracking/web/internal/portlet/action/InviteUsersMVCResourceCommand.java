/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.web.internal.constants.PublicationRoleConstants;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTCollectionPermission;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserNotificationEventLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/invite_users"
	},
	service = MVCResourceCommand.class
)
public class InviteUsersMVCResourceCommand
	extends BaseTransactionalMVCResourceCommand {

	@Override
	public boolean serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			return super.serveResource(resourceRequest, resourceResponse);
		}
	}

	@Override
	protected void doTransactionalCommand(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws IOException, PortalException {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			resourceRequest);

		long ctCollectionId = ParamUtil.getLong(
			resourceRequest, "ctCollectionId");

		CTCollection ctCollection = _ctCollectionLocalService.fetchCTCollection(
			ctCollectionId);

		if ((ctCollection == null) &&
			(ctCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION)) {

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"errorMessage",
					_language.get(
						httpServletRequest,
						"this-publication-no-longer-exists")));

			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if ((ctCollection != null) &&
			!CTCollectionPermission.contains(
				themeDisplay.getPermissionChecker(), ctCollection,
				ActionKeys.PERMISSIONS)) {

			JSONPortletResponseUtil.writeJSON(
				resourceRequest, resourceResponse,
				JSONUtil.put(
					"errorMessage",
					_language.get(
						httpServletRequest,
						"you-do-not-have-permission-to-invite-users-to-this-" +
							"publication")));

			return;
		}

		Group group = null;

		if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			group = _groupLocalService.fetchUserGroup(
				themeDisplay.getCompanyId(), themeDisplay.getUserId());
		}
		else {
			group = _groupLocalService.fetchGroup(
				ctCollection.getCompanyId(),
				_portal.getClassNameId(CTCollection.class),
				ctCollection.getCtCollectionId());
		}

		if (group == null) {
			long userId = 0;
			String className = null;
			long classPK = 0;
			Map<Locale, String> nameMap = null;

			if (ctCollectionId == CTConstants.CT_COLLECTION_ID_PRODUCTION) {
				userId = themeDisplay.getGuestUserId();
				className = null;
				classPK = 0;
				nameMap = new HashMap<>();
			}
			else {
				userId = ctCollection.getUserId();
				className = CTCollection.class.getName();
				classPK = ctCollection.getCtCollectionId();
				nameMap = HashMapBuilder.put(
					LocaleUtil.getDefault(), ctCollection.getName()
				).build();
			}

			group = _groupLocalService.addGroup(
				userId, GroupConstants.DEFAULT_PARENT_GROUP_ID, className,
				classPK, GroupConstants.DEFAULT_LIVE_GROUP_ID, nameMap, null,
				GroupConstants.TYPE_SITE_PRIVATE, false,
				GroupConstants.DEFAULT_MEMBERSHIP_RESTRICTION, null, false,
				true, null);
		}

		long[] publicationsUserRoleUserIds = ParamUtil.getLongValues(
			resourceRequest, "publicationsUserRoleUserIds");

		if (publicationsUserRoleUserIds.length > 0) {
			Role publicationsUserRole = _roleLocalService.getRole(
				themeDisplay.getCompanyId(), RoleConstants.PUBLICATIONS_USER);

			_userLocalService.addRoleUsers(
				publicationsUserRole.getRoleId(), publicationsUserRoleUserIds);
		}

		int[] roleValues = ParamUtil.getIntegerValues(
			resourceRequest, "roleValues");

		long[] userIds = ParamUtil.getLongValues(resourceRequest, "userIds");

		for (int i = 0; i < userIds.length; i++) {
			List<UserGroupRole> userGroupRoles =
				_userGroupRoleLocalService.getUserGroupRoles(
					userIds[i], group.getGroupId());

			if (roleValues[i] < 0) {
				for (UserGroupRole userGroupRole : userGroupRoles) {
					_userGroupRoleLocalService.deleteUserGroupRole(
						userGroupRole);
				}

				continue;
			}

			Role role = _getRole(roleValues[i], themeDisplay);

			boolean addUserGroupRole = true;

			for (UserGroupRole userGroupRole : userGroupRoles) {
				if (userGroupRole.getRoleId() == role.getRoleId()) {
					addUserGroupRole = false;
				}
				else {
					_userGroupRoleLocalService.deleteUserGroupRole(
						userGroupRole);
				}
			}

			if (addUserGroupRole) {
				_userGroupRoleLocalService.addUserGroupRole(
					userIds[i], group.getGroupId(), role.getRoleId());
			}

			if (userGroupRoles.isEmpty()) {
				_sendNotificationEvent(
					ctCollectionId, userIds[i], roleValues[i], themeDisplay);
			}
		}

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			JSONUtil.put(
				"successMessage",
				_language.get(
					httpServletRequest, "users-were-invited-successfully")));
	}

	private String[] _getModelResourceActions(int role) {
		if (role == PublicationRoleConstants.ROLE_ADMIN) {
			return new String[] {
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW,
				CTActionKeys.PUBLISH
			};
		}
		else if (role == PublicationRoleConstants.ROLE_EDITOR) {
			return new String[] {ActionKeys.UPDATE, ActionKeys.VIEW};
		}
		else if (role == PublicationRoleConstants.ROLE_PUBLISHER) {
			return new String[] {
				ActionKeys.UPDATE, ActionKeys.VIEW, CTActionKeys.PUBLISH
			};
		}

		return new String[] {ActionKeys.VIEW};
	}

	private Role _getRole(int roleValue, ThemeDisplay themeDisplay)
		throws PortalException {

		String name = PublicationRoleConstants.getRoleName(roleValue);

		Role role = _roleLocalService.fetchRole(
			themeDisplay.getCompanyId(), name);

		if (role == null) {
			role = _roleLocalService.addRole(
				themeDisplay.getGuestUserId(), null, 0, name, null, null,
				RoleConstants.TYPE_PUBLICATIONS, null, null);

			for (String actionId : _getModelResourceActions(roleValue)) {
				_resourcePermissionLocalService.addResourcePermission(
					themeDisplay.getCompanyId(), CTCollection.class.getName(),
					ResourceConstants.SCOPE_GROUP_TEMPLATE,
					String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
					role.getRoleId(), actionId);
			}
		}

		return role;
	}

	private void _sendNotificationEvent(
			long ctCollectionId, long receiverUserId, int roleValue,
			ThemeDisplay themeDisplay)
		throws PortalException {

		if (UserNotificationManagerUtil.isDeliver(
				receiverUserId, CTPortletKeys.PUBLICATIONS, 0,
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
				UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

			User user = themeDisplay.getUser();

			NotificationEvent notificationEvent = new NotificationEvent(
				System.currentTimeMillis(), CTPortletKeys.PUBLICATIONS,
				JSONUtil.put(
					"classPK", ctCollectionId
				).put(
					"roleValue", roleValue
				).put(
					"userId", user.getUserId()
				).put(
					"userName", user.getFullName()
				));

			notificationEvent.setDeliveryType(
				UserNotificationDeliveryConstants.TYPE_WEBSITE);

			_userNotificationEventLocalService.addUserNotificationEvent(
				receiverUserId, notificationEvent);
		}
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private UserNotificationEventLocalService
		_userNotificationEventLocalService;

}
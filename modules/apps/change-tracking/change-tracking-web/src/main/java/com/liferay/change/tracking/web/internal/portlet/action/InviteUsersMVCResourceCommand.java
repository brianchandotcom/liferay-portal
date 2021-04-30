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

import com.liferay.change.tracking.constants.CTActionKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.constants.PublicationRoleConstants;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTCollectionPermission;
import com.liferay.petra.lang.SafeClosable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/invite_users"
	},
	service = AopService.class
)
public class InviteUsersMVCResourceCommand
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
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_addPublicationRole(
			new String[] {ActionKeys.UPDATE, ActionKeys.VIEW},
			PublicationRoleConstants.NAME_EDIT, resourceRequest, themeDisplay);
		_addPublicationRole(
			new String[] {
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW
			},
			PublicationRoleConstants.NAME_PERMISSIONS, resourceRequest,
			themeDisplay);
		_addPublicationRole(
			new String[] {
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW,
				CTActionKeys.PUBLISH
			},
			PublicationRoleConstants.NAME_PUBLISH, resourceRequest,
			themeDisplay);
		_addPublicationRole(
			new String[] {ActionKeys.VIEW}, PublicationRoleConstants.NAME_VIEW,
			resourceRequest, themeDisplay);

		CTCollection ctCollection = _ctCollectionLocalService.getCTCollection(
			ParamUtil.getLong(resourceRequest, "ctCollectionId"));

		CTCollectionPermission.contains(
			themeDisplay.getPermissionChecker(), ctCollection,
			ActionKeys.PERMISSIONS);

		Role role = _roleLocalService.getRole(
			themeDisplay.getCompanyId(),
			PublicationRoleConstants.getRoleName(
				ParamUtil.getInteger(resourceRequest, "roleId")));

		long[] userIds = ParamUtil.getLongValues(resourceRequest, "userIds");

		_userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, ctCollection.getGroupId());

		for (long userId : userIds) {
			_userGroupRoleLocalService.addUserGroupRole(
				userId, ctCollection.getGroupId(), role.getRoleId());
		}
	}

	private void _addPublicationRole(
			String[] actionIds, String name, ResourceRequest resourceRequest,
			ThemeDisplay themeDisplay)
		throws PortalException {

		Role role = _roleLocalService.fetchRole(
			themeDisplay.getCompanyId(), name);

		if (role == null) {
			try (SafeClosable safeClosable =
					CTCollectionThreadLocal.setCTCollectionId(0)) {

				role = _roleLocalService.addRole(
					themeDisplay.getDefaultUserId(), null, 0, name,
					HashMapBuilder.put(
						LocaleUtil.getDefault(), name
					).build(),
					null, RoleConstants.TYPE_PUBLICATION, StringPool.BLANK,
					ServiceContextFactory.getInstance(resourceRequest));

				for (String actionId : actionIds) {
					_resourcePermissionLocalService.addResourcePermission(
						themeDisplay.getCompanyId(),
						CTCollection.class.getName(),
						ResourceConstants.SCOPE_GROUP_TEMPLATE,
						String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
						role.getRoleId(), actionId);
				}
			}
		}
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}
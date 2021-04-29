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

import com.liferay.change.tracking.constants.PublicationRoleConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.change.tracking.web.internal.security.permission.resource.CTCollectionPermission;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
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
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CTCollection ctCollection = _ctCollectionLocalService.getCTCollection(
			ParamUtil.getLong(resourceRequest, "ctCollectionId"));

		CTCollectionPermission.contains(
			themeDisplay.getPermissionChecker(), ctCollection,
			ActionKeys.PERMISSIONS);

		int[] roles = ParamUtil.getIntegerValues(resourceRequest, "roles");

		long[] userIds = ParamUtil.getLongValues(resourceRequest, "userIds");

		_userGroupRoleLocalService.deleteUserGroupRoles(
			userIds, ctCollection.getGroupId());

		for (int i = 0; i < userIds.length; i++) {
			if (roles[i] < 0) {
				continue;
			}

			Role role = _roleLocalService.fetchRole(
				themeDisplay.getCompanyId(),
				PublicationRoleConstants.getRoleName(roles[i]));

			_userGroupRoleLocalService.addUserGroupRole(
				userIds[i], ctCollection.getGroupId(), role.getRoleId());
		}
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
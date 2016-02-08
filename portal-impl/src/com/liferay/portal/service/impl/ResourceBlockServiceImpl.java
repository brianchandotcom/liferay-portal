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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.service.base.ResourceBlockServiceBaseImpl;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service for accessing and updating resource blocks. Its
 * methods include permission checks.
 *
 * @author Connor McKay
 */
public class ResourceBlockServiceImpl extends ResourceBlockServiceBaseImpl {

	@Override
	public void addCompanyScopePermission(
			long scopeGroupId, long companyId, String name, long roleId,
			String actionId)
		throws PortalException {

		permissionService.checkPermission(
			scopeGroupId, Role.class.getName(), roleId);

		resourceBlockLocalService.addCompanyScopePermission(
			companyId, name, roleId, actionId);
	}

	@Override
	public void addGroupScopePermission(
			long scopeGroupId, long companyId, long groupId, String name,
			long roleId, String actionId)
		throws PortalException {

		permissionService.checkPermission(
			scopeGroupId, Role.class.getName(), roleId);

		resourceBlockLocalService.addGroupScopePermission(
			companyId, groupId, name, roleId, actionId);
	}

	@Override
	public void addIndividualScopePermission(
			long companyId, long groupId, String name, long primKey,
			long roleId, String actionId)
		throws PortalException {

		permissionService.checkPermission(groupId, name, primKey);

		resourceBlockLocalService.addIndividualScopePermission(
			companyId, groupId, name, primKey, roleId, actionId);
	}

	@Override
	public void removeAllGroupScopePermissions(
			long scopeGroupId, long companyId, String name, long roleId,
			String actionId)
		throws PortalException {

		permissionService.checkPermission(
			scopeGroupId, Role.class.getName(), roleId);

		resourceBlockLocalService.removeAllGroupScopePermissions(
			companyId, name, roleId, actionId);
	}

	@Override
	public void removeCompanyScopePermission(
			long scopeGroupId, long companyId, String name, long roleId,
			String actionId)
		throws PortalException {

		permissionService.checkPermission(
			scopeGroupId, Role.class.getName(), roleId);

		resourceBlockLocalService.removeCompanyScopePermission(
			companyId, name, roleId, actionId);
	}

	@Override
	public void removeGroupScopePermission(
			long scopeGroupId, long companyId, long groupId, String name,
			long roleId, String actionId)
		throws PortalException {

		permissionService.checkPermission(
			scopeGroupId, Role.class.getName(), roleId);

		resourceBlockLocalService.removeGroupScopePermission(
			companyId, groupId, name, roleId, actionId);
	}

	@Override
	public void removeIndividualScopePermission(
			long companyId, long groupId, String name, long primKey,
			long roleId, String actionId)
		throws PortalException {

		permissionService.checkPermission(groupId, name, primKey);

		resourceBlockLocalService.removeIndividualScopePermission(
			companyId, groupId, name, primKey, roleId, actionId);
	}

	@Override
	public void setCompanyScopePermissions(
			long scopeGroupId, long companyId, String name, long roleId,
			List<String> actionIds)
		throws PortalException {

		permissionService.checkPermission(
			scopeGroupId, Role.class.getName(), roleId);

		resourceBlockLocalService.setCompanyScopePermissions(
			companyId, name, roleId, actionIds);
	}

	@Override
	public void setGroupScopePermissions(
			long scopeGroupId, long companyId, long groupId, String name,
			long roleId, List<String> actionIds)
		throws PortalException {

		permissionService.checkPermission(
			scopeGroupId, Role.class.getName(), roleId);

		resourceBlockLocalService.setGroupScopePermissions(
			companyId, groupId, name, roleId, actionIds);
	}

	@Override
	public void setIndividualScopePermissions(
			long companyId, long groupId, String name, long primKey,
			long roleId, List<String> actionIds)
		throws PortalException {

		permissionService.checkPermission(groupId, name, primKey);

		resourceBlockLocalService.setIndividualScopePermissions(
			companyId, groupId, name, primKey, roleId, actionIds);
	}

	@Override
	public void setIndividualScopePermissions(
			long companyId, long groupId, String name, long primKey,
			Map<Long, String[]> roleIdsToActionIds)
		throws PortalException {

		permissionService.checkPermission(groupId, name, primKey);

		resourceBlockLocalService.setIndividualScopePermissions(
			companyId, groupId, name, primKey, roleIdsToActionIds);
	}

}
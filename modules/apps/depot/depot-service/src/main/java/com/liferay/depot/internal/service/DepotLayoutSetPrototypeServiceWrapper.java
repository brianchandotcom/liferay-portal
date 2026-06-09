/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.service;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.auth.GuestOrUserUtil;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeServiceWrapper;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.PermissionCacheUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = ServiceWrapper.class)
public class DepotLayoutSetPrototypeServiceWrapper
	extends LayoutSetPrototypeServiceWrapper {

	@Override
	public List<LayoutSetPrototype> search(
		long companyId, Boolean active, int start, int end,
		OrderByComparator<LayoutSetPrototype> orderByComparator) {

		try {
			if (_hasCMSAdministratorRole(companyId) ||
				_isDepotGroupAdminOrOwner(companyId)) {

				return _layoutSetPrototypeLocalService.search(
					companyId, active, start, end, orderByComparator);
			}

			return super.search(
				companyId, active, start, end, orderByComparator);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return super.search(
				companyId, active, start, end, orderByComparator);
		}
	}

	@Override
	public int searchCount(long companyId, Boolean active) {
		try {
			if (_hasCMSAdministratorRole(companyId) ||
				_isDepotGroupAdminOrOwner(companyId)) {

				return _layoutSetPrototypeLocalService.searchCount(
					companyId, active);
			}

			return super.searchCount(companyId, active);
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return super.searchCount(companyId, active);
		}
	}

	private boolean _hasCMSAdministratorRole(long companyId)
		throws PortalException {

		Boolean value = PermissionCacheUtil.getUserPrimaryKeyRole(
			GuestOrUserUtil.getUserId(), companyId,
			RoleConstants.CMS_ADMINISTRATOR);

		if (value == null) {
			value = _roleLocalService.hasUserRole(
				GuestOrUserUtil.getUserId(), companyId,
				RoleConstants.CMS_ADMINISTRATOR, true);

			PermissionCacheUtil.putUserPrimaryKeyRole(
				GuestOrUserUtil.getUserId(), companyId,
				RoleConstants.CMS_ADMINISTRATOR, value);
		}

		return value;
	}

	private boolean _isDepotGroupAdminOrOwner(long companyId)
		throws PortalException {

		Role assetLibraryAdministratorRole = _roleLocalService.getRole(
			companyId, DepotRolesConstants.ASSET_LIBRARY_ADMINISTRATOR);
		Role assetLibraryOwnerRole = _roleLocalService.getRole(
			companyId, DepotRolesConstants.ASSET_LIBRARY_OWNER);

		for (UserGroupRole userGroupRole :
				_userGroupRoleLocalService.getUserGroupRoles(
					GuestOrUserUtil.getUserId())) {

			long roleId = userGroupRole.getRoleId();

			if ((roleId == assetLibraryAdministratorRole.getRoleId()) ||
				(roleId == assetLibraryOwnerRole.getRoleId())) {

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotLayoutSetPrototypeServiceWrapper.class);

	@Reference
	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.asset.library.internal.resource.v1_0;

import com.liferay.headless.asset.library.dto.v1_0.Role;
import com.liferay.headless.asset.library.resource.v1_0.RoleResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.NoSuchGroupException;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.RoleService;
import com.liferay.portal.kernel.service.UserGroupRoleService;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.pagination.Page;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Roberto Díaz
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/role.properties",
	scope = ServiceScope.PROTOTYPE, service = RoleResource.class
)
public class RoleResourceImpl extends BaseRoleResourceImpl {

	@Override
	public Page<Role>
			getAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCodeRolesPage(
				String assetLibraryExternalReferenceCode,
				String userAccountExternalReferenceCode)
		throws Exception {

		Group group = _getGroup(assetLibraryExternalReferenceCode);
		User user = _userService.getUserByExternalReferenceCode(
			userAccountExternalReferenceCode, contextCompany.getCompanyId());

		return getAssetLibraryUserAccountRolesPage(
			group.getGroupId(), user.getUserId());
	}

	@Override
	public Page<Role> getAssetLibraryUserAccountRolesPage(
			Long assetLibraryId, Long userAccountId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		if (!_groupService.hasUserGroup(userAccountId, assetLibraryId)) {
			throw new NoSuchUserException(
				StringBundler.concat(
					"User ", userAccountId, " is not associated to group ",
					assetLibraryId));
		}

		return Page.of(
			transform(
				_roleService.getUserGroupRoles(userAccountId, assetLibraryId),
				this::_toRole));
	}

	@Override
	public Page<Role>
			putAssetLibraryByExternalReferenceCodeAssetLibraryExternalReferenceCodeUserAccountByExternalReferenceCodeUserAccountExternalReferenceCodeRolesPage(
				String assetLibraryExternalReferenceCode,
				String userAccountExternalReferenceCode, Role[] roles)
		throws Exception {

		Group group = _getGroup(assetLibraryExternalReferenceCode);
		User user = _userService.getUserByExternalReferenceCode(
			userAccountExternalReferenceCode, contextCompany.getCompanyId());

		return putAssetLibraryUserAccountRolesPage(
			group.getGroupId(), user.getUserId(), roles);
	}

	@Override
	public Page<Role> putAssetLibraryUserAccountRolesPage(
			Long assetLibraryId, Long userAccountId, Role[] roles)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled("LPD-17564")) {
			throw new UnsupportedOperationException();
		}

		if (!_groupService.hasUserGroup(userAccountId, assetLibraryId)) {
			throw new NoSuchUserException(
				StringBundler.concat(
					"User ", userAccountId, " is not associated to group ",
					assetLibraryId));
		}

		long[] roleIdsArray = ListUtil.toLongArray(
			_roleService.getUserGroupRoles(userAccountId, assetLibraryId),
			com.liferay.portal.kernel.model.Role.ROLE_ID_ACCESSOR);

		_userGroupRoleService.deleteUserGroupRoles(
			userAccountId, assetLibraryId, roleIdsArray);

		long[] roleIds = new long[0];

		for (Role role : roles) {
			com.liferay.portal.kernel.model.Role persistedRole =
				_roleService.getRole(
					contextCompany.getCompanyId(), role.getName());

			roleIds = ArrayUtil.append(roleIds, persistedRole.getRoleId());
		}

		_userGroupRoleService.addUserGroupRoles(
			userAccountId, assetLibraryId, roleIds);

		return Page.of(
			transform(
				_roleService.getUserGroupRoles(userAccountId, assetLibraryId),
				this::_toRole));
	}

	private Group _getGroup(String externalReferenceCode) throws Exception {
		Group group = _groupService.fetchGroupByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		if (group == null) {
			throw new NoSuchGroupException(
				"No group exists with external reference code " +
					externalReferenceCode);
		}

		return group;
	}

	private Role _toRole(com.liferay.portal.kernel.model.Role role)
		throws PortalException {

		return new Role() {
			{
				setExternalReferenceCode(role::getExternalReferenceCode);
				setId(role::getRoleId);
				setName(role::getName);
				setRoleType(role::getType);
			}
		};
	}

	@Reference
	private GroupService _groupService;

	@Reference
	private RoleService _roleService;

	@Reference
	private UserGroupRoleService _userGroupRoleService;

	@Reference
	private UserService _userService;

}
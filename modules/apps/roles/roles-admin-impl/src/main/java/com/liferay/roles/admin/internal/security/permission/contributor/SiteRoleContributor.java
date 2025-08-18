/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.roles.admin.internal.security.permission.contributor;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.function.UnsafePredicate;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.security.permission.contributor.RoleCollection;
import com.liferay.portal.kernel.security.permission.contributor.RoleContributor;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.Collection;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(service = RoleContributor.class)
public class SiteRoleContributor implements RoleContributor {

	@Override
	public void contribute(RoleCollection roleCollection) {
		try {
			if (!FeatureFlagManagerUtil.isEnabled(
					roleCollection.getCompanyId(), "LPD-17564") ||
				(roleCollection.getGroupId() <= 0)) {

				return;
			}

			Group group = _groupLocalService.getGroup(
				roleCollection.getGroupId());

			if (group.isCMS() && _isSpaceDepotEntryMember(group, roleCollection)) {
				Role role = _roleLocalService.getRole(
					roleCollection.getCompanyId(), RoleConstants.SITE_MEMBER);

				roleCollection.addRoleId(role.getRoleId());
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private boolean _isGroupDepotEntrySpace(Group group)
		throws PortalException {

		if (!group.isDepot()) {
			return false;
		}

		DepotEntry depotEntry = _depotEntryLocalService.getGroupDepotEntry(
			group.getGroupId());

		return depotEntry.getType() == DepotConstants.TYPE_SPACE;
	}

	private boolean _isSpaceDepotEntryMember(
			Group group, RoleCollection roleCollection)
		throws PortalException {

		User user = roleCollection.getUser();

		if (group.getCompanyId() != user.getCompanyId()) {
			return false;
		}

		if (_exists(user.getGroups(), this::_isGroupDepotEntrySpace)) {
			return true;
		}

		UserBag userBag = roleCollection.getUserBag();

		Collection<Role> roles = userBag.getRoles();

		List<UserGroupRole> userGroupRoles =
			_userGroupRoleLocalService.getUserGroupRoles(user.getUserId());

		for (String roleName : _ASSET_LIBRARY_ROLES) {
			Role role = _roleLocalService.fetchRole(
				group.getCompanyId(), roleName);

			if (roles.contains(role) ||
				_exists(
					userGroupRoles,
					userGroupRole ->
						(userGroupRole.getRoleId() == role.getRoleId()) &&
							_isGroupDepotEntrySpace(
								userGroupRole.getGroup()))) {

				return true;
			}
		}

		return false;
	}

	private <T, E extends Exception> boolean _exists(
		List<T> list, UnsafePredicate<T, E> unsafePredicate) {

		return ListUtil.exists(
			list,
			object -> {
				try {
					return unsafePredicate.test(object);
				}
				catch (Exception exception) {
					return ReflectionUtil.throwException(exception);
				}
			});
	}

	private static final String[] _ASSET_LIBRARY_ROLES = {
		DepotRolesConstants.ASSET_LIBRARY_CONTENT_REVIEWER,
		DepotRolesConstants.ASSET_LIBRARY_OWNER
	};

	private static final Log _log = LogFactoryUtil.getLog(
		SiteRoleContributor.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

}
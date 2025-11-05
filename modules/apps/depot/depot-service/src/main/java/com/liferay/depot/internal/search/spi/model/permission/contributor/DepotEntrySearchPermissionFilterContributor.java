/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.search.spi.model.permission.contributor;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.spi.model.permission.contributor.SearchPermissionFilterContributor;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Galluzzi
 */
@Component(service = SearchPermissionFilterContributor.class)
public class DepotEntrySearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		if (!FeatureFlagManagerUtil.isEnabled(companyId, "LPD-17564") ||
			(userId == 0) ||
			!Objects.equals(className, DepotEntry.class.getName())) {

			return;
		}

		String[] depotGroupIds = _getDepotGroupIds(userId);

		if (depotGroupIds.length == 0) {
			return;
		}

		Role role = _roleLocalService.fetchRole(
			companyId, DepotRolesConstants.ASSET_LIBRARY_MEMBER);

		if (role == null) {
			return;
		}

		for (String depotGroupId : depotGroupIds) {
			TermsFilter termsFilter = new TermsFilter("groupRoleId");

			termsFilter.addValues(
				depotGroupId + StringPool.DASH + role.getRoleId());

			booleanFilter.add(termsFilter, BooleanClauseOccur.SHOULD);
		}
	}

	private String[] _getDepotGroupIds(long userId) {
		Set<Long> groupIds = new HashSet<>();

		for (long groupId :
				_depotEntryLocalService.getDepotEntryGroupIds(
					CompanyThreadLocal.getCompanyId(),
					DepotConstants.TYPE_SPACE)) {

			for (UserGroup userGroup :
					_userGroupLocalService.getGroupUserGroups(groupId)) {

				if (_userGroupLocalService.hasUserUserGroup(
						userId, userGroup.getUserGroupId())) {

					groupIds.add(groupId);
				}
			}
		}

		return ArrayUtil.toStringArray(groupIds);
	}

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}
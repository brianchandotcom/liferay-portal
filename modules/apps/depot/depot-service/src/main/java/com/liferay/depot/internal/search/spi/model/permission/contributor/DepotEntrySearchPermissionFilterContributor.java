/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.internal.search.spi.model.permission.contributor;

import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.search.spi.model.permission.contributor.SearchPermissionFilterContributor;

import java.util.HashSet;
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

		try {
			if (userId == 0) {
				return;
			}

			Role role = _roleLocalService.fetchRole(
				companyId, DepotRolesConstants.ASSET_LIBRARY_MEMBER);

			if (role == null) {
				return;
			}

			for (long groupId :
					_getDepotGroupIds(
						permissionChecker.getCompanyId(), userId)) {

				TermsFilter termsFilter = new TermsFilter("groupRoleId");

				termsFilter.addValues(
					groupId + StringPool.DASH + role.getRoleId());

				booleanFilter.add(termsFilter, BooleanClauseOccur.SHOULD);
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private Set<Long> _getDepotGroupIds(long companyId, long userId)
		throws PortalException {

		Set<Long> groupIds = new HashSet<>();

		ActionableDynamicQuery actionableDynamicQuery =
			_depotEntryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> dynamicQuery.add(
				RestrictionsFactoryUtil.eq("companyId", companyId)));
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<DepotEntry>)
				depotEntry -> {
					for (UserGroup userGroup :
							_userGroupLocalService.getGroupUserGroups(
								depotEntry.getGroupId())) {

						if (_userGroupLocalService.hasUserUserGroup(
								userId, userGroup.getUserGroupId())) {

							groupIds.add(depotEntry.getGroupId());
						}
					}
				});
		actionableDynamicQuery.performActions();

		return groupIds;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DepotEntrySearchPermissionFilterContributor.class);

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}
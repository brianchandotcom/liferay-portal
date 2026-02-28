/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.permission.contributor;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.constants.DepotRolesConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.service.DepotEntryGroupRelLocalService;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.UserBag;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.search.spi.model.permission.contributor.SearchPermissionFilterContributor;

import java.util.HashSet;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joshua Cords
 */
@Component(service = SearchPermissionFilterContributor.class)
public class AssetLibraryMemberRoleSearchPermissionFilterContributor
	implements SearchPermissionFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, long companyId, long[] groupIds,
		long userId, PermissionChecker permissionChecker, String className) {

		if ((booleanFilter == null) || (permissionChecker == null)) {
			return;
		}

		try {
			_contribute(
				booleanFilter, companyId, permissionChecker.getUserBag());
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}
	}

	private void _add(BooleanFilter booleanFilter, TermsFilter termsFilter) {
		if (!termsFilter.isEmpty()) {
			booleanFilter.add(termsFilter, BooleanClauseOccur.SHOULD);
		}
	}

	private void _addGroup(
		Group group, Role assetLibraryMemberRole,
		TermsFilter groupIdsTermsFilter, TermsFilter groupRolesTermsFilter) {

		if (group == null) {
			return;
		}

		groupIdsTermsFilter.addValue(String.valueOf(group.getGroupId()));

		groupRolesTermsFilter.addValue(
			StringBundler.concat(
				group.getGroupId(), StringPool.DASH,
				assetLibraryMemberRole.getRoleId()));
	}

	private void _addInheritedDepotGroups(
			Group group, Role assetLibraryMemberRole,
			TermsFilter groupIdsTermsFilter, TermsFilter groupRolesTermsFilter,
			Set<Long> inheritedDepotGroupIds, UserBag userBag)
		throws Exception {

		if (group == null) {
			return;
		}

		for (DepotEntryGroupRel depotEntryGroupRel :
				_depotEntryGroupRelLocalService.getDepotEntryGroupRels(
					group.getGroupId(), DepotConstants.TYPE_ANY,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS)) {

			DepotEntry depotEntry = _depotEntryLocalService.fetchDepotEntry(
				depotEntryGroupRel.getDepotEntryId());

			if (depotEntry == null) {
				continue;
			}

			long depotGroupId = depotEntry.getGroupId();

			if (!inheritedDepotGroupIds.add(depotGroupId)) {
				continue;
			}

			Group depotGroup = _groupLocalService.fetchGroup(depotGroupId);

			if ((depotGroup == null) || userBag.hasUserGroup(depotGroup)) {
				continue;
			}

			_addGroup(
				depotGroup, assetLibraryMemberRole, groupIdsTermsFilter,
				groupRolesTermsFilter);

			_addGroup(
				depotGroup.getStagingGroup(), assetLibraryMemberRole,
				groupIdsTermsFilter, groupRolesTermsFilter);
		}
	}

	private void _contribute(
			BooleanFilter booleanFilter, long companyId, UserBag userBag)
		throws Exception {

		if ((booleanFilter == null) || (userBag == null)) {
			return;
		}

		Role assetLibraryMemberRole = _roleLocalService.fetchRole(
			companyId, DepotRolesConstants.ASSET_LIBRARY_MEMBER);

		if (assetLibraryMemberRole == null) {
			return;
		}

		TermsFilter groupIdsTermsFilter = new TermsFilter(Field.GROUP_ID);
		TermsFilter groupRolesTermsFilter = new TermsFilter(
			Field.GROUP_ROLE_ID);

		Set<Long> inheritedDepotGroupIds = new HashSet<>();

		for (Group group : userBag.getGroups()) {
			_addInheritedDepotGroups(
				group, assetLibraryMemberRole, groupIdsTermsFilter,
				groupRolesTermsFilter, inheritedDepotGroupIds, userBag);
		}

		_add(booleanFilter, groupIdsTermsFilter);
		_add(booleanFilter, groupRolesTermsFilter);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetLibraryMemberRoleSearchPermissionFilterContributor.class);

	@Reference
	private DepotEntryGroupRelLocalService _depotEntryGroupRelLocalService;

	@Reference
	private DepotEntryLocalService _depotEntryLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}
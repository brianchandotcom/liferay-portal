/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.util;

import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.service.DepotEntryLocalServiceUtil;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Pedro Leite
 */
public class UserSelectionFDSFilterUtil {

	public static Set<User> getUsers() {
		List<Long> depotEntryGroupIds = TransformUtil.transform(
			DepotEntryLocalServiceUtil.getDepotEntryGroupIds(
				CompanyThreadLocal.getCompanyId(), DepotConstants.TYPE_SPACE),
			groupId -> {
				if (!_isAssetLibraryAdminOrAssetLibraryMember(groupId)) {
					return null;
				}

				return groupId;
			});

		if (depotEntryGroupIds.isEmpty()) {
			return new TreeSet<>();
		}

		Set<User> users = new TreeSet<>(
			Comparator.comparing(User::getFullName));

		users.addAll(
			UserLocalServiceUtil.searchBySocial(
				CompanyThreadLocal.getCompanyId(),
				ArrayUtil.toLongArray(depotEntryGroupIds), null, null,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		long[] userGroupIds = _getUserGroupIds(depotEntryGroupIds);

		if (userGroupIds.length > 0) {
			users.addAll(
				UserLocalServiceUtil.searchBySocial(
					CompanyThreadLocal.getCompanyId(), null, userGroupIds, null,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS));
		}

		return users;
	}

	private static long[] _getUserGroupIds(List<Long> groupIds) {
		Set<Long> userGroupIds = new HashSet<>();

		for (long groupId : groupIds) {
			userGroupIds.addAll(
				TransformUtil.transform(
					UserGroupLocalServiceUtil.getGroupUserGroups(groupId),
					UserGroup::getUserGroupId));
		}

		return ArrayUtil.toLongArray(userGroupIds);
	}

	private static boolean _isAssetLibraryAdminOrAssetLibraryMember(
		long groupId) {

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (permissionChecker.isGroupAdmin(groupId)) {
			return true;
		}

		return GroupLocalServiceUtil.hasUserGroup(
			PrincipalThreadLocal.getUserId(), groupId);
	}

}
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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Pedro Leite
 */
public class CMSUserUtil {

	public static Set<User> getUsers() {
		return getUsers(null, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public static Set<User> getUsers(String keywords, int start, int end) {
		List<Long> depotEntryGroupIds = TransformUtil.transform(
			DepotEntryLocalServiceUtil.getDepotEntryGroupIds(
				CompanyThreadLocal.getCompanyId(), DepotConstants.TYPE_SPACE),
			groupId -> {
				PermissionChecker permissionChecker =
					PermissionThreadLocal.getPermissionChecker();

				if (permissionChecker.isGroupAdmin(groupId) ||
					GroupLocalServiceUtil.hasUserGroup(
						PrincipalThreadLocal.getUserId(), groupId)) {

					return groupId;
				}

				return null;
			});

		if (ListUtil.isEmpty(depotEntryGroupIds)) {
			return Collections.emptySet();
		}

		Set<User> users = new TreeSet<>(
			Comparator.comparing(User::getFullName));

		users.addAll(
			UserLocalServiceUtil.searchBySocial(
				CompanyThreadLocal.getCompanyId(),
				ArrayUtil.toLongArray(depotEntryGroupIds), null, keywords,
				start, end));

		Set<Long> depotEntryUserGroupIds = new HashSet<>();

		for (long depotEntryGroupId : depotEntryGroupIds) {
			depotEntryUserGroupIds.addAll(
				TransformUtil.transform(
					UserGroupLocalServiceUtil.getGroupUserGroups(
						depotEntryGroupId),
					UserGroup::getUserGroupId));
		}

		if (SetUtil.isNotEmpty(depotEntryUserGroupIds)) {
			users.addAll(
				UserLocalServiceUtil.searchBySocial(
					CompanyThreadLocal.getCompanyId(), null,
					ArrayUtil.toLongArray(depotEntryUserGroupIds), keywords,
					start, end));
		}

		return users;
	}

}
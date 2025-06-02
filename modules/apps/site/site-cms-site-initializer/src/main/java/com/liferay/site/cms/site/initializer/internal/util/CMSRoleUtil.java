/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.util;

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.tags.constants.AssetTagsAdminPortletKeys;
import com.liferay.depot.model.DepotEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.kernel.exception.NoSuchUserException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.Arrays;
import java.util.Map;

/**
 * @author Jürgen Kappler
 */
public class CMSRoleUtil {

	public static Role getOrAddCMSAdministratorRoleAndPermissions(
			long companyId)
		throws Exception {

		String name = RoleConstants.CMS_ADMINISTRATOR;

		Role role = RoleLocalServiceUtil.fetchRole(companyId, name);

		if (role != null) {
			return role;
		}

		long userId = _getAdminUserId(companyId);

		role = RoleLocalServiceUtil.addRole(
			null, userId, null, 0, name, null, null, RoleConstants.TYPE_REGULAR,
			null, null);

		_addResourcePermissions(
			companyId,
			HashMapBuilder.put(
				AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN,
				new String[] {
					ActionKeys.ACCESS_IN_CONTROL_PANEL,
					ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS,
					ActionKeys.PREFERENCES, ActionKeys.VIEW
				}
			).put(
				AssetCategory.class.getName(),
				new String[] {
					ActionKeys.ADD_CATEGORY, ActionKeys.DELETE,
					ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW
				}
			).put(
				AssetTagsAdminPortletKeys.ASSET_TAGS_ADMIN,
				new String[] {
					ActionKeys.ACCESS_IN_CONTROL_PANEL,
					ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS,
					ActionKeys.PREFERENCES, ActionKeys.VIEW
				}
			).put(
				AssetVocabulary.class.getName(),
				new String[] {
					ActionKeys.DELETE, ActionKeys.PERMISSIONS,
					ActionKeys.UPDATE, ActionKeys.VIEW
				}
			).put(
				DepotEntry.class.getName(),
				new String[] {
					ActionKeys.DELETE, ActionKeys.PERMISSIONS,
					ActionKeys.UPDATE, ActionKeys.VIEW
				}
			).put(
				PortletKeys.PORTAL, new String[] {ActionKeys.VIEW_CONTROL_PANEL}
			).put(
				"com.liferay.asset.categories",
				new String[] {
					ActionKeys.ADD_CATEGORY, ActionKeys.ADD_VOCABULARY,
					ActionKeys.PERMISSIONS
				}
			).put(
				"com.liferay.asset.tags",
				new String[] {
					ActionKeys.MANAGE_TAG, ActionKeys.PERMISSIONS,
					ActionKeys.SUBSCRIBE
				}
			).put(
				"com.liferay.depot", new String[] {"ADD_DEPOT_ENTRY"}
			).build(),
			role);

		return role;
	}

	private static void _addResourcePermissions(
			long companyId, Map<String, String[]> resourceActionIds, Role role)
		throws Exception {

		for (Map.Entry<String, String[]> entry : resourceActionIds.entrySet()) {
			try {
				DBPartitionUtil.forEachCompanyId(
					company ->
						ResourceActionLocalServiceUtil.checkResourceActions(
							entry.getKey(), Arrays.asList(entry.getValue())));
			}
			catch (Exception exception) {
				throw new PortalException(exception);
			}

			for (String actionId : entry.getValue()) {
				ResourcePermissionLocalServiceUtil.addResourcePermission(
					companyId, entry.getKey(), ResourceConstants.SCOPE_COMPANY,
					String.valueOf(companyId), role.getRoleId(), actionId);
			}
		}
	}

	private static long _getAdminUserId(long companyId) throws Exception {
		Role role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.ADMINISTRATOR);

		long[] userIds = UserLocalServiceUtil.getRoleUserIds(role.getRoleId());

		if (userIds.length == 0) {
			throw new NoSuchUserException(
				StringBundler.concat(
					"No user exists in company ", companyId, " with role ",
					role.getName()));
		}

		return userIds[0];
	}

}
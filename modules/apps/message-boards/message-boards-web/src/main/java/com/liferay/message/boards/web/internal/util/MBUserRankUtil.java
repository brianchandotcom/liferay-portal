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

package com.liferay.message.boards.web.internal.util;

import com.liferay.message.boards.model.MBStatsUser;
import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Sergio González
 */
public class MBUserRankUtil {

	public static String[] getUserRank(
			MBGroupServiceSettings mbGroupServiceSettings, String languageId,
			MBStatsUser statsUser)
		throws Exception {

		String[] rank = {StringPool.BLANK, StringPool.BLANK};

		int maxPosts = 0;

		Group group = GroupLocalServiceUtil.getGroup(statsUser.getGroupId());

		long companyId = group.getCompanyId();

		String[] ranks = mbGroupServiceSettings.getRanks(languageId);

		for (String curRank : ranks) {
			String[] kvp = StringUtil.split(curRank, CharPool.EQUAL);

			String kvpName = kvp[0];

			String kvpPosts = kvp[1];

			String[] curRankValueKvp = StringUtil.split(
				kvpPosts, CharPool.COLON);

			if (curRankValueKvp.length <= 1) {
				int posts = GetterUtil.getInteger(kvpPosts);

				if ((posts <= statsUser.getMessageCount()) &&
					(posts >= maxPosts)) {

					rank[0] = kvpName;
					maxPosts = posts;
				}
			}
			else {
				String entityType = curRankValueKvp[0];
				String entityValue = curRankValueKvp[1];

				try {
					if (_isEntityRank(
							companyId, statsUser, entityType, entityValue)) {

						rank[1] = curRank;

						break;
					}
				}
				catch (Exception e) {
					if (_log.isWarnEnabled()) {
						_log.warn(e, e);
					}
				}
			}
		}

		return rank;
	}

	private static boolean _isEntityRank(
			long companyId, MBStatsUser statsUser, String entityType,
			String entityValue)
		throws Exception {

		long userId = statsUser.getUserId();

		if (entityType.equals("organization-role") ||
			entityType.equals("site-role")) {

			long groupId = statsUser.getGroupId();
			Role role = RoleLocalServiceUtil.getRole(companyId, entityValue);

			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, groupId, role.getRoleId(), true)) {

				return true;
			}
		}
		else if (entityType.equals("organization")) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(
					companyId, entityValue);

			if (OrganizationLocalServiceUtil.hasUserOrganization(
					userId, organization.getOrganizationId(), false, false)) {

				return true;
			}
		}
		else if (entityType.equals("regular-role")) {
			if (RoleLocalServiceUtil.hasUserRole(
					userId, companyId, entityValue, true)) {

				return true;
			}
		}
		else if (entityType.equals("user-group")) {
			UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(
				companyId, entityValue);

			if (UserLocalServiceUtil.hasUserGroupUser(
					userGroup.getUserGroupId(), userId)) {

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(MBUserRankUtil.class);

}
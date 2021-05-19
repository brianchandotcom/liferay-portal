/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.akismet.internal.util;

import com.liferay.message.boards.settings.MBGroupServiceSettings;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Luis Miguel Barcos
 */
public class MBUserRankUtil {

	public static String[] getUserRank(
			MBGroupServiceSettings mbGroupServiceSettings, String languageId,
			Object[] statsUser)
		throws PortalException {

		String[] rank = {StringPool.BLANK, StringPool.BLANK};

		int maxPosts = 0;

		long userId = (Long)statsUser[0];

		User user = UserLocalServiceUtil.getUser(userId);

		long companyId = user.getCompanyId();

		String[] ranks = mbGroupServiceSettings.getRanks(languageId);

		long messageCount = (Long)statsUser[1];

		for (String curRank : ranks) {
			String[] kvp = StringUtil.split(curRank, CharPool.EQUAL);

			String kvpPosts = kvp[1];

			String[] curRankValueKvp = StringUtil.split(
				kvpPosts, CharPool.COLON);

			if (curRankValueKvp.length <= 1) {
				int posts = GetterUtil.getInteger(kvpPosts);

				if ((posts <= messageCount) && (posts >= maxPosts)) {
					rank[0] = kvp[0];
					maxPosts = posts;
				}
			}
			else {
				String entityType = curRankValueKvp[0];
				String entityValue = curRankValueKvp[1];

				try {
					if (_isEntityRank(
							companyId, user, entityType, entityValue)) {

						rank[1] = curRank;

						break;
					}
				}
				catch (Exception exception) {
					if (_log.isWarnEnabled()) {
						_log.warn(exception, exception);
					}
				}
			}
		}

		return rank;
	}

	private static boolean _isEntityRank(
			long companyId, User statsUser, String entityType,
			String entityValue)
		throws Exception {

		long userId = statsUser.getUserId();

		if (entityType.equals("organization-role") ||
			entityType.equals("site-role")) {

			Role role = RoleLocalServiceUtil.getRole(companyId, entityValue);

			if (UserGroupRoleLocalServiceUtil.hasUserGroupRole(
					userId, statsUser.getGroupId(), role.getRoleId(), true)) {

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
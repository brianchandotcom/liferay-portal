/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.scope.util;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Pedro Tavares
 */
public class GroupUtil {

	public static Long getGroupId(
		long companyId, String siteKey, GroupLocalService groupLocalService) {

		Long groupId = com.liferay.portal.vulcan.util.GroupUtil.getGroupId(
			companyId, siteKey, groupLocalService);

		if (groupId != null) {
			return groupId;
		}

		Group group = groupLocalService.fetchGroup(companyId, siteKey);

		if (group == null) {
			group = groupLocalService.fetchGroup(GetterUtil.getLong(siteKey));
		}

		if ((group != null) && group.isUserGroup()) {
			return group.getGroupId();
		}

		return null;
	}

}
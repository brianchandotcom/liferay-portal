/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;

/**
 * @author Georgel Pop
 */
public class ScopeUtil {

	public static Group getItemGroup(
		long companyId, String itemScopeExternalReferenceCode,
		long scopeGroupId) {

		if (Validator.isNull(itemScopeExternalReferenceCode)) {
			return GroupLocalServiceUtil.fetchGroup(scopeGroupId);
		}

		return GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
			itemScopeExternalReferenceCode, companyId);
	}

	public static Long getItemGroupId(
		long companyId, String itemScopeExternalReferenceCode,
		long scopeGroupId) {

		if (Validator.isNull(itemScopeExternalReferenceCode)) {
			return scopeGroupId;
		}

		Group group = getItemGroup(
			companyId, itemScopeExternalReferenceCode, scopeGroupId);

		if (group == null) {
			return null;
		}

		return group.getGroupId();
	}

	public static String getItemScopeExternalReferenceCode(
			String itemScopeExternalReferenceCode, long scopeGroupId)
		throws PortalException {

		if (Validator.isNull(itemScopeExternalReferenceCode)) {
			return null;
		}

		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		if (StringUtil.equals(
				itemScopeExternalReferenceCode,
				group.getExternalReferenceCode())) {

			return null;
		}

		return itemScopeExternalReferenceCode;
	}

}
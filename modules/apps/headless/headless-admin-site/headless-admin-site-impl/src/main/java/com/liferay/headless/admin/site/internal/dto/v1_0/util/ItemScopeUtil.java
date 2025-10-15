/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.headless.admin.site.dto.v1_0.Scope;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Rubén Pulido
 */
public class ItemScopeUtil {

	public static Scope getItemScope(long itemScopeGroupId, long scopeGroupId)
		throws Exception {

		if (itemScopeGroupId == scopeGroupId) {
			return null;
		}

		return _getItemScope(GroupLocalServiceUtil.getGroup(itemScopeGroupId));
	}

	public static Scope getItemScope(
			long companyId, String itemExternalReferenceCode, long scopeGroupId)
		throws PortalException {

		if (Validator.isNull(itemExternalReferenceCode)) {
			return null;
		}

		Group group = GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
			itemExternalReferenceCode, companyId);

		if ((group == null) || (group.getGroupId() == scopeGroupId)) {
			return null;
		}

		return _getItemScope(group);
	}

	public static String getItemScopeExternalReferenceCode(
			Scope itemScope, long scopeGroupId)
		throws PortalException {

		if (itemScope == null) {
			return null;
		}

		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		if (StringUtil.equals(
				itemScope.getExternalReferenceCode(),
				group.getExternalReferenceCode())) {

			return null;
		}

		return itemScope.getExternalReferenceCode();
	}

	private static Scope _getItemScope(Group group) {
		return new Scope() {
			{
				setExternalReferenceCode(group::getExternalReferenceCode);
				setType(
					() -> {
						if (group.getType() == GroupConstants.TYPE_DEPOT) {
							return Type.ASSET_LIBRARY;
						}

						return Type.SITE;
					});
			}
		};
	}

}
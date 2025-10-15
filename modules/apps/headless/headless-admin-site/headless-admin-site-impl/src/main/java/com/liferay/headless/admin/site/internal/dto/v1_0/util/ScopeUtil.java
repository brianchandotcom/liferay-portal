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
public class ScopeUtil {

	public static Scope getScope(long groupId, long scopeGroupId)
		throws Exception {

		if (groupId == scopeGroupId) {
			return null;
		}

		return _getScope(GroupLocalServiceUtil.getGroup(scopeGroupId));
	}

	public static Scope getScope(
			long companyId, String externalReferenceCode, long scopeGroupId)
		throws PortalException {

		if (Validator.isNull(externalReferenceCode)) {
			return null;
		}

		Group group = GroupLocalServiceUtil.getGroupByExternalReferenceCode(
			externalReferenceCode, companyId);

		if (group.getGroupId() == scopeGroupId) {
			return null;
		}

		return _getScope(group);
	}

	public static String getScopeExternalReferenceCode(
			Scope scope, long scopeGroupId)
		throws PortalException {

		if (scope == null) {
			return null;
		}

		Group group = GroupLocalServiceUtil.getGroup(scopeGroupId);

		if (StringUtil.equals(
				scope.getExternalReferenceCode(),
				group.getExternalReferenceCode())) {

			return null;
		}

		return scope.getExternalReferenceCode();
	}

	private static Scope _getScope(Group group) {
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
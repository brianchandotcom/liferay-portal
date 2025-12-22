/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.internal.dto.v1_0.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.util.ScopeUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.scope.Scope;

/**
 * @author Rubén Pulido
 */
public class ItemScopeUtil {

	public static Long getItemGroupId(
		long companyId, Scope scope, long scopeGroupId) {

		String externalReferenceCode = null;

		if (scope != null) {
			externalReferenceCode = scope.getExternalReferenceCode();
		}

		return ScopeUtil.getItemGroupId(
			companyId, externalReferenceCode, scopeGroupId);
	}

	public static Scope getItemScope(long itemScopeGroupId, long scopeGroupId)
		throws Exception {

		if (scopeGroupId == itemScopeGroupId) {
			return null;
		}

		Group group = GroupLocalServiceUtil.getGroup(itemScopeGroupId);

		Scope.Type type = (group.getType() == GroupConstants.TYPE_DEPOT) ?
			Scope.Type.ASSET_LIBRARY : Scope.Type.SITE;

		return Scope.ofReference(group.getExternalReferenceCode(), type);
	}

	public static Scope getItemScope(
		long companyId, String itemGroupExternalReferenceCode,
		long scopeGroupId) {

		if (Validator.isNull(itemGroupExternalReferenceCode)) {
			return null;
		}

		Group group = GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
			itemGroupExternalReferenceCode, companyId);

		if (group == null) {
			return Scope.ofReference(
				itemGroupExternalReferenceCode, Scope.Type.SITE);
		}

		if (group.getGroupId() == scopeGroupId) {
			return null;
		}

		Scope.Type type = (group.getType() == GroupConstants.TYPE_DEPOT) ?
			Scope.Type.ASSET_LIBRARY : Scope.Type.SITE;

		return Scope.ofReference(group.getExternalReferenceCode(), type);
	}

	public static String getItemScopeExternalReferenceCode(
			Scope itemScope, long scopeGroupId)
		throws PortalException {

		String externalReferenceCode = null;

		if (itemScope != null) {
			externalReferenceCode = itemScope.getExternalReferenceCode();
		}

		return ScopeUtil.getItemScopeExternalReferenceCode(
			externalReferenceCode, scopeGroupId);
	}

}
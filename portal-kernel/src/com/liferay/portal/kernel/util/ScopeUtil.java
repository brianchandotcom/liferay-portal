/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;

/**
 * @author Georgel Pop
 */
public class ScopeUtil {

	public static Long getItemGroupId(
		long companyId, String itemScopeExternalReferenceCode,
		long scopeGroupId) {

		if (Validator.isNull(itemScopeExternalReferenceCode)) {
			return scopeGroupId;
		}

		Group group = GroupLocalServiceUtil.fetchGroupByExternalReferenceCode(
			itemScopeExternalReferenceCode, companyId);

		if (group == null) {
			return null;
		}

		return group.getGroupId();
	}

	public static String getItemScopeExternalReferenceCode(
			long itemScopeGroupId, long scopeGroupId)
		throws PortalException {

		if ((itemScopeGroupId == 0) || (scopeGroupId == itemScopeGroupId)) {
			return null;
		}

		Group group = GroupLocalServiceUtil.getGroup(itemScopeGroupId);

		return group.getExternalReferenceCode();
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

	public static long getScopeGroupId(long scopeGroupId) {
		if (scopeGroupId > 0) {
			return scopeGroupId;
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if ((serviceContext != null) &&
			(serviceContext.getScopeGroupId() > 0)) {

			return serviceContext.getScopeGroupId();
		}

		Long groupId = GroupThreadLocal.getGroupId();

		if (groupId != null) {
			return groupId;
		}

		throw new IllegalStateException(
			"Neither service context thread local nor group thread local are " +
				"initialized");
	}

}
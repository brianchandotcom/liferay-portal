/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.web.internal.util;

import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalService;
import com.liferay.commerce.product.type.grouped.util.GroupedCPTypeHelper;
import com.liferay.commerce.product.type.grouped.util.comparator.CPDefinitionGroupedEntryPriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = GroupedCPTypeHelper.class)
public class GroupedCPTypeHelperImpl implements GroupedCPTypeHelper {

	@Override
	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntry(
			long commerceAccountId, long commerceChannelGroupId,
			long cpDefinitionId)
		throws PortalException {

		if (_commerceProductViewPermission.contains(
				PermissionThreadLocal.getPermissionChecker(), commerceAccountId,
				commerceChannelGroupId, cpDefinitionId)) {

			return _cpDefinitionGroupedEntryLocalService.
				getCPDefinitionGroupedEntries(
					cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					new CPDefinitionGroupedEntryPriorityComparator());
		}

		return Collections.emptyList();
	}

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CPDefinitionGroupedEntryLocalService
		_cpDefinitionGroupedEntryLocalService;

}
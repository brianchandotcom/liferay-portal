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
@Component(
	enabled = false, immediate = true, service = GroupedCPTypeHelper.class
)
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
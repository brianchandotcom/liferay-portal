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

package com.liferay.commerce.product.type.grouped.service.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.base.CPDefinitionGroupedEntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CPDefinitionGroupedEntryServiceImpl
	extends CPDefinitionGroupedEntryServiceBaseImpl {

	@Override
	public void addCPDefinitionGroupedEntries(
			long cpDefinitionId, long[] entryCPDefinitionIds,
			ServiceContext serviceContext)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		for (long entryCPDefinitionId : entryCPDefinitionIds) {
			_cpDefinitionModelResourcePermission.check(
				getPermissionChecker(), entryCPDefinitionId, ActionKeys.VIEW);
		}

		cpDefinitionGroupedEntryLocalService.addCPDefinitionGroupedEntries(
			cpDefinitionId, entryCPDefinitionIds, serviceContext);
	}

	@Override
	public CPDefinitionGroupedEntry deleteCPDefinitionGroupedEntry(
			long cpDefinitionGroupedEntryId)
		throws PortalException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
			cpDefinitionGroupedEntryPersistence.findByPrimaryKey(
				cpDefinitionGroupedEntryId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionGroupedEntry.getCPDefinitionId(), ActionKeys.UPDATE);

		return
			cpDefinitionGroupedEntryLocalService.deleteCPDefinitionGroupedEntry(
				cpDefinitionGroupedEntry);
	}

	@Override
	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
			long cpDefinitionId, int start, int end,
			OrderByComparator<CPDefinitionGroupedEntry> orderByComparator)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return
			cpDefinitionGroupedEntryLocalService.getCPDefinitionGroupedEntries(
				cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionGroupedEntriesCount(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return cpDefinitionGroupedEntryLocalService.
			getCPDefinitionGroupedEntriesCount(cpDefinitionId);
	}

	@Override
	public CPDefinitionGroupedEntry getCPDefinitionGroupedEntry(
			long cpDefinitionGroupedEntryId)
		throws PortalException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
			cpDefinitionGroupedEntryLocalService.getCPDefinitionGroupedEntry(
				cpDefinitionGroupedEntryId);

		if (cpDefinitionGroupedEntry != null) {
			_cpDefinitionModelResourcePermission.check(
				getPermissionChecker(),
				cpDefinitionGroupedEntry.getCPDefinitionId(), ActionKeys.VIEW);
			_cpDefinitionModelResourcePermission.check(
				getPermissionChecker(),
				cpDefinitionGroupedEntry.getEntryCPDefinitionId(),
				ActionKeys.VIEW);
		}

		return cpDefinitionGroupedEntry;
	}

	@Override
	public CPDefinitionGroupedEntry updateCPDefinitionGroupedEntry(
			long cpDefinitionGroupedEntryId, double priority, int quantity)
		throws PortalException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
			cpDefinitionGroupedEntryPersistence.findByPrimaryKey(
				cpDefinitionGroupedEntryId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionGroupedEntry.getCPDefinitionId(), ActionKeys.UPDATE);
		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionGroupedEntry.getEntryCPDefinitionId(), ActionKeys.VIEW);

		return
			cpDefinitionGroupedEntryLocalService.updateCPDefinitionGroupedEntry(
				cpDefinitionGroupedEntryId, priority, quantity);
	}

	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPDefinitionGroupedEntryServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

}
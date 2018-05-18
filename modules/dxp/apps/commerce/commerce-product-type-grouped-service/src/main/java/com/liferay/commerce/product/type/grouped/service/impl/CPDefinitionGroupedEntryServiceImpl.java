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

package com.liferay.commerce.product.type.grouped.service.impl;

import com.liferay.commerce.product.service.permission.CPDefinitionPermission;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.base.CPDefinitionGroupedEntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
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

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		for (long entryCPDefinitionId : entryCPDefinitionIds) {
			CPDefinitionPermission.check(
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

		CPDefinitionPermission.check(
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

		CPDefinitionPermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.VIEW);

		return
			cpDefinitionGroupedEntryLocalService.getCPDefinitionGroupedEntries(
				cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionGroupedEntriesCount(long cpDefinitionId)
		throws PortalException {

		CPDefinitionPermission.check(
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
			CPDefinitionPermission.check(
				getPermissionChecker(),
				cpDefinitionGroupedEntry.getCPDefinitionId(), ActionKeys.VIEW);
			CPDefinitionPermission.check(
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

		CPDefinitionPermission.check(
			getPermissionChecker(),
			cpDefinitionGroupedEntry.getCPDefinitionId(), ActionKeys.UPDATE);
		CPDefinitionPermission.check(
			getPermissionChecker(),
			cpDefinitionGroupedEntry.getEntryCPDefinitionId(), ActionKeys.VIEW);

		return
			cpDefinitionGroupedEntryLocalService.updateCPDefinitionGroupedEntry(
				cpDefinitionGroupedEntryId, priority, quantity);
	}

}
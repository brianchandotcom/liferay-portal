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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.type.grouped.constants.GroupedCPTypeConstants;
import com.liferay.commerce.product.type.grouped.exception.CPDefinitionGroupedEntryQuantityException;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.base.CPDefinitionGroupedEntryLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.util.List;

/**
 * @author Andrea Di Giorgi
 */
public class CPDefinitionGroupedEntryLocalServiceImpl
	extends CPDefinitionGroupedEntryLocalServiceBaseImpl {

	@Override
	public void addCPDefinitionGroupedEntries(
			long cpDefinitionId, long[] entryCPDefinitionIds,
			ServiceContext serviceContext)
		throws PortalException {

		for (long entryCPDefinitionId : entryCPDefinitionIds) {
			addCPDefinitionGroupedEntry(
				cpDefinitionId, entryCPDefinitionId, 0, 1, serviceContext);
		}
	}

	@Override
	public CPDefinitionGroupedEntry addCPDefinitionGroupedEntry(
			long cpDefinitionId, long entryCPDefinitionId, double priority,
			int quantity, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			cpDefinitionId);
		User user = userLocalService.getUser(serviceContext.getUserId());

		validate(cpDefinitionId, entryCPDefinitionId, quantity);

		long cpDefinitionGroupedEntryId = counterLocalService.increment();

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
			cpDefinitionGroupedEntryPersistence.create(
				cpDefinitionGroupedEntryId);

		cpDefinitionGroupedEntry.setUuid(serviceContext.getUuid());
		cpDefinitionGroupedEntry.setGroupId(cpDefinition.getGroupId());
		cpDefinitionGroupedEntry.setCompanyId(user.getCompanyId());
		cpDefinitionGroupedEntry.setUserId(user.getUserId());
		cpDefinitionGroupedEntry.setUserName(user.getFullName());
		cpDefinitionGroupedEntry.setCPDefinitionId(
			cpDefinition.getCPDefinitionId());
		cpDefinitionGroupedEntry.setEntryCPDefinitionId(entryCPDefinitionId);
		cpDefinitionGroupedEntry.setPriority(priority);
		cpDefinitionGroupedEntry.setQuantity(quantity);

		cpDefinitionGroupedEntryPersistence.update(cpDefinitionGroupedEntry);

		return cpDefinitionGroupedEntry;
	}

	@Override
	public void deleteCPDefinitionGroupedEntries(long cpDefinitionId) {
		cpDefinitionGroupedEntryPersistence.removeByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CPDefinitionGroupedEntry fetchCPDefinitionGroupedEntryByC_E(
		long cpDefinitionId, long entryCPDefinitionId) {

		return cpDefinitionGroupedEntryPersistence.fetchByC_E(
			cpDefinitionId, entryCPDefinitionId);
	}

	@Override
	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
		long cpDefinitionId) {

		return cpDefinitionGroupedEntryPersistence.findByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
		long cpDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionGroupedEntry> orderByComparator) {

		return cpDefinitionGroupedEntryPersistence.findByCPDefinitionId(
			cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public List<CPDefinitionGroupedEntry>
		getCPDefinitionGroupedEntriesByCPDefinitionId(long cpDefinitionId) {

		return cpDefinitionGroupedEntryPersistence.findByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public int getCPDefinitionGroupedEntriesCount(long cpDefinitionId) {
		return cpDefinitionGroupedEntryPersistence.countByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CPDefinitionGroupedEntry updateCPDefinitionGroupedEntry(
			long cpDefinitionGroupedEntryId, double priority, int quantity)
		throws PortalException {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
			cpDefinitionGroupedEntryPersistence.findByPrimaryKey(
				cpDefinitionGroupedEntryId);

		validate(
			cpDefinitionGroupedEntry.getCPDefinitionId(),
			cpDefinitionGroupedEntry.getEntryCPDefinitionId(), quantity);

		cpDefinitionGroupedEntry.setPriority(priority);
		cpDefinitionGroupedEntry.setQuantity(quantity);

		cpDefinitionGroupedEntryPersistence.update(cpDefinitionGroupedEntry);

		return cpDefinitionGroupedEntry;
	}

	protected void validate(
			long cpDefinitionId, long entryCPDefinitionId, int quantity)
		throws PortalException {

		CPDefinition entryCPDefinition =
			_cpDefinitionLocalService.getCPDefinition(entryCPDefinitionId);

		if ((cpDefinitionId == entryCPDefinition.getCPDefinitionId()) ||
			GroupedCPTypeConstants.NAME.equals(
				entryCPDefinition.getProductTypeName())) {

			throw new NoSuchCPDefinitionException();
		}

		if (quantity <= 0) {
			throw new CPDefinitionGroupedEntryQuantityException();
		}
	}

	@ServiceReference(type = CPDefinitionLocalService.class)
	private CPDefinitionLocalService _cpDefinitionLocalService;

}
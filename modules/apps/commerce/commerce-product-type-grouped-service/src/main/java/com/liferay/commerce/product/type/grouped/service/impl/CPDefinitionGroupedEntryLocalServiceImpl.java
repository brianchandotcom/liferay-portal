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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.commerce.product.type.grouped.constants.GroupedCPTypeConstants;
import com.liferay.commerce.product.type.grouped.exception.CPDefinitionGroupedEntryQuantityException;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.base.CPDefinitionGroupedEntryLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	enabled = false,
	property = "model.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry",
	service = AopService.class
)
public class CPDefinitionGroupedEntryLocalServiceImpl
	extends CPDefinitionGroupedEntryLocalServiceBaseImpl {

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public void addCPDefinitionGroupedEntries(
			long cpDefinitionId, long[] entryCPDefinitionIds,
			ServiceContext serviceContext)
		throws PortalException {

		for (long entryCPDefinitionId : entryCPDefinitionIds) {
			CPDefinition cpDefinition =
				_cpDefinitionLocalService.getCPDefinition(entryCPDefinitionId);

			cpDefinitionGroupedEntryLocalService.
				addCPDefinitionGroupedEntryByEntryCProductId(
					cpDefinitionId, cpDefinition.getCProductId(), 0, 1,
					serviceContext);
		}
	}

	@Override
	public void addCPDefinitionGroupedEntriesByEntryCProductIds(
			long cpDefinitionId, long[] entryCProductIds,
			ServiceContext serviceContext)
		throws PortalException {

		for (long entryCProductId : entryCProductIds) {
			cpDefinitionGroupedEntryLocalService.
				addCPDefinitionGroupedEntryByEntryCProductId(
					cpDefinitionId, entryCProductId, 0, 1, serviceContext);
		}
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CPDefinitionGroupedEntry addCPDefinitionGroupedEntry(
			long cpDefinitionId, long entryCPDefinitionId, double priority,
			int quantity, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			entryCPDefinitionId);

		return cpDefinitionGroupedEntryLocalService.
			addCPDefinitionGroupedEntryByEntryCProductId(
				cpDefinitionId, cpDefinition.getCProductId(), priority,
				quantity, serviceContext);
	}

	@Override
	public CPDefinitionGroupedEntry
			addCPDefinitionGroupedEntryByEntryCProductId(
				long cpDefinitionId, long entryCProductId, double priority,
				int quantity, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition cpDefinition = null;
		User user = userLocalService.getUser(serviceContext.getUserId());

		validate(cpDefinitionId, entryCProductId, quantity);

		long cpDefinitionGroupedEntryId = counterLocalService.increment();

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
			cpDefinitionGroupedEntryPersistence.create(
				cpDefinitionGroupedEntryId);

		if (_cpDefinitionLocalService.isVersionable(cpDefinitionId)) {
			cpDefinition = _cpDefinitionLocalService.copyCPDefinition(
				cpDefinitionId);
		}
		else {
			cpDefinition = _cpDefinitionLocalService.getCPDefinition(
				cpDefinitionId);
		}

		cpDefinitionGroupedEntry.setGroupId(cpDefinition.getGroupId());
		cpDefinitionGroupedEntry.setCompanyId(user.getCompanyId());
		cpDefinitionGroupedEntry.setUserId(user.getUserId());
		cpDefinitionGroupedEntry.setUserName(user.getFullName());
		cpDefinitionGroupedEntry.setCPDefinitionId(
			cpDefinition.getCPDefinitionId());
		cpDefinitionGroupedEntry.setEntryCProductId(entryCProductId);
		cpDefinitionGroupedEntry.setPriority(priority);
		cpDefinitionGroupedEntry.setQuantity(quantity);

		return cpDefinitionGroupedEntryPersistence.update(
			cpDefinitionGroupedEntry);
	}

	@Override
	public void cloneCPDefinitionGroupedEntries(
		long cpDefinitionId, long newCPDefinitionId) {

		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries =
			cpDefinitionGroupedEntryLocalService.
				getCPDefinitionGroupedEntriesByCPDefinitionId(cpDefinitionId);

		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry :
				cpDefinitionGroupedEntries) {

			CPDefinitionGroupedEntry newCPDefinitionGroupedEntry =
				(CPDefinitionGroupedEntry)cpDefinitionGroupedEntry.clone();

			newCPDefinitionGroupedEntry.setUuid(PortalUUIDUtil.generate());
			newCPDefinitionGroupedEntry.setCPDefinitionGroupedEntryId(
				counterLocalService.increment());
			newCPDefinitionGroupedEntry.setCPDefinitionId(newCPDefinitionId);

			cpDefinitionGroupedEntryLocalService.addCPDefinitionGroupedEntry(
				newCPDefinitionGroupedEntry);
		}
	}

	@Override
	public void deleteCPDefinitionGroupedEntries(long cpDefinitionId) {
		if (_cpDefinitionLocalService.isVersionable(cpDefinitionId)) {
			try {
				CPDefinition newCPDefinition =
					_cpDefinitionLocalService.copyCPDefinition(cpDefinitionId);

				cpDefinitionId = newCPDefinition.getCPDefinitionId();
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}
		}

		cpDefinitionGroupedEntryPersistence.removeByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CPDefinitionGroupedEntry fetchCPDefinitionGroupedEntry(
		long cpDefinitionId, long entryCProductId) {

		return cpDefinitionGroupedEntryPersistence.fetchByC_E(
			cpDefinitionId, entryCProductId);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CPDefinitionGroupedEntry fetchCPDefinitionGroupedEntryByC_E(
		long cpDefinitionId, long entryCPDefinitionId) {

		CPDefinition entryCPDefinition =
			_cpDefinitionLocalService.fetchCPDefinition(entryCPDefinitionId);

		if (entryCPDefinition == null) {
			return null;
		}

		return cpDefinitionGroupedEntryLocalService.
			fetchCPDefinitionGroupedEntry(
				cpDefinitionId, entryCPDefinition.getCProductId());
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

		if (_cpDefinitionLocalService.isVersionable(
				cpDefinitionGroupedEntry.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				_cpDefinitionLocalService.copyCPDefinition(
					cpDefinitionGroupedEntry.getCPDefinitionId());

			cpDefinitionGroupedEntry =
				cpDefinitionGroupedEntryPersistence.findByC_E(
					newCPDefinition.getCPDefinitionId(),
					cpDefinitionGroupedEntry.getEntryCProductId());
		}

		validate(
			cpDefinitionGroupedEntry.getCPDefinitionId(),
			cpDefinitionGroupedEntry.getEntryCProductId(), quantity);

		cpDefinitionGroupedEntry.setPriority(priority);
		cpDefinitionGroupedEntry.setQuantity(quantity);

		return cpDefinitionGroupedEntryPersistence.update(
			cpDefinitionGroupedEntry);
	}

	protected void validate(
			long cpDefinitionId, long entryCProductId, int quantity)
		throws PortalException {

		CProduct entryCProduct = _cProductLocalService.getCProduct(
			entryCProductId);

		CPDefinition entryCPDefinition =
			_cpDefinitionLocalService.getCPDefinition(
				entryCProduct.getPublishedCPDefinitionId());

		if ((cpDefinitionId == entryCProduct.getPublishedCPDefinitionId()) ||
			GroupedCPTypeConstants.NAME.equals(
				entryCPDefinition.getProductTypeName())) {

			throw new NoSuchCPDefinitionException();
		}

		if (quantity <= 0) {
			throw new CPDefinitionGroupedEntryQuantityException();
		}
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CProductLocalService _cProductLocalService;

}
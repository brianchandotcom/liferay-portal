/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDefinitionGroupedEntryService}.
 *
 * @author Andrea Di Giorgi
 * @see CPDefinitionGroupedEntryService
 * @generated
 */
public class CPDefinitionGroupedEntryServiceWrapper
	implements CPDefinitionGroupedEntryService,
			   ServiceWrapper<CPDefinitionGroupedEntryService> {

	public CPDefinitionGroupedEntryServiceWrapper() {
		this(null);
	}

	public CPDefinitionGroupedEntryServiceWrapper(
		CPDefinitionGroupedEntryService cpDefinitionGroupedEntryService) {

		_cpDefinitionGroupedEntryService = cpDefinitionGroupedEntryService;
	}

	@Override
	public void addCPDefinitionGroupedEntries(
			long cpDefinitionId, long[] entryCPDefinitionIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_cpDefinitionGroupedEntryService.addCPDefinitionGroupedEntries(
			cpDefinitionId, entryCPDefinitionIds, serviceContext);
	}

	@Override
	public
		com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry
				addCPDefinitionGroupedEntry(
					long cpDefinitionId, long entryCProductId, double priority,
					int quantity,
					com.liferay.portal.kernel.service.ServiceContext
						serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionGroupedEntryService.addCPDefinitionGroupedEntry(
			cpDefinitionId, entryCProductId, priority, quantity,
			serviceContext);
	}

	@Override
	public
		com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry
				deleteCPDefinitionGroupedEntry(long cpDefinitionGroupedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionGroupedEntryService.deleteCPDefinitionGroupedEntry(
			cpDefinitionGroupedEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.product.type.grouped.model.
			CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
					long cpDefinitionId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.product.type.grouped.model.
							CPDefinitionGroupedEntry> orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntries(
			cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.product.type.grouped.model.
			CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
					long companyId, long cpDefinitionId, String keywords,
					int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntries(
			companyId, cpDefinitionId, keywords, start, end, sort);
	}

	@Override
	public int getCPDefinitionGroupedEntriesCount(long cpDefinitionId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionGroupedEntryService.
			getCPDefinitionGroupedEntriesCount(cpDefinitionId);
	}

	@Override
	public int getCPDefinitionGroupedEntriesCount(
			long companyId, long cpDefinitionId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionGroupedEntryService.
			getCPDefinitionGroupedEntriesCount(
				companyId, cpDefinitionId, keywords);
	}

	@Override
	public
		com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry
				getCPDefinitionGroupedEntry(long cpDefinitionGroupedEntryId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntry(
			cpDefinitionGroupedEntryId);
	}

	@Override
	public java.util.List
		<com.liferay.commerce.product.type.grouped.model.
			CPDefinitionGroupedEntry>
					getEntryCProductCPDefinitionGroupedEntries(
						long entryCProductId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.product.type.grouped.model.
								CPDefinitionGroupedEntry> orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionGroupedEntryService.
			getEntryCProductCPDefinitionGroupedEntries(
				entryCProductId, start, end, orderByComparator);
	}

	@Override
	public int getEntryCProductCPDefinitionGroupedEntriesCount(
			long entryCProductId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionGroupedEntryService.
			getEntryCProductCPDefinitionGroupedEntriesCount(entryCProductId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDefinitionGroupedEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public
		com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry
				updateCPDefinitionGroupedEntry(
					long cpDefinitionGroupedEntryId, double priority,
					int quantity)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _cpDefinitionGroupedEntryService.updateCPDefinitionGroupedEntry(
			cpDefinitionGroupedEntryId, priority, quantity);
	}

	@Override
	public CPDefinitionGroupedEntryService getWrappedService() {
		return _cpDefinitionGroupedEntryService;
	}

	@Override
	public void setWrappedService(
		CPDefinitionGroupedEntryService cpDefinitionGroupedEntryService) {

		_cpDefinitionGroupedEntryService = cpDefinitionGroupedEntryService;
	}

	private CPDefinitionGroupedEntryService _cpDefinitionGroupedEntryService;

}
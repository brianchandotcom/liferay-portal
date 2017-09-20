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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTirePriceEntryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTirePriceEntryService
 * @generated
 */
@ProviderType
public class CommerceTirePriceEntryServiceWrapper
	implements CommerceTirePriceEntryService,
		ServiceWrapper<CommerceTirePriceEntryService> {
	public CommerceTirePriceEntryServiceWrapper(
		CommerceTirePriceEntryService commerceTirePriceEntryService) {
		_commerceTirePriceEntryService = commerceTirePriceEntryService;
	}

	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry addCommerceTirePriceEntry(
		long commercePriceEntryId, double price, int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryService.addCommerceTirePriceEntry(commercePriceEntryId,
			price, minQuantity, serviceContext);
	}

	@Override
	public void deleteCommerceTirePriceEntry(long commerceTirePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceTirePriceEntryService.deleteCommerceTirePriceEntry(commerceTirePriceEntryId);
	}

	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry fetchCommerceTirePriceEntry(
		long commerceTirePriceEntryId) {
		return _commerceTirePriceEntryService.fetchCommerceTirePriceEntry(commerceTirePriceEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end) {
		return _commerceTirePriceEntryService.getCommerceTirePriceEntries(commercePriceEntryId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceTirePriceEntry> getCommerceTirePriceEntries(
		long commercePriceEntryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceTirePriceEntry> orderByComparator) {
		return _commerceTirePriceEntryService.getCommerceTirePriceEntries(commercePriceEntryId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceTirePriceEntriesCount(long commercePriceEntryId) {
		return _commerceTirePriceEntryService.getCommerceTirePriceEntriesCount(commercePriceEntryId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceTirePriceEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _commerceTirePriceEntryService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommerceTirePriceEntry> searchCommerceTirePriceEntries(
		long companyId, long groupId, long commercePriceEntryId,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryService.searchCommerceTirePriceEntries(companyId,
			groupId, commercePriceEntryId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.model.CommerceTirePriceEntry updateCommerceTirePriceEntry(
		long commerceTirePriceEntryId, double price, int minQuantity,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceTirePriceEntryService.updateCommerceTirePriceEntry(commerceTirePriceEntryId,
			price, minQuantity, serviceContext);
	}

	@Override
	public CommerceTirePriceEntryService getWrappedService() {
		return _commerceTirePriceEntryService;
	}

	@Override
	public void setWrappedService(
		CommerceTirePriceEntryService commerceTirePriceEntryService) {
		_commerceTirePriceEntryService = commerceTirePriceEntryService;
	}

	private CommerceTirePriceEntryService _commerceTirePriceEntryService;
}
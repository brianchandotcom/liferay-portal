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
 * Provides a wrapper for {@link CommercePriceEntryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryService
 * @generated
 */
@ProviderType
public class CommercePriceEntryServiceWrapper
	implements CommercePriceEntryService,
		ServiceWrapper<CommercePriceEntryService> {
	public CommercePriceEntryServiceWrapper(
		CommercePriceEntryService commercePriceEntryService) {
		_commercePriceEntryService = commercePriceEntryService;
	}

	@Override
	public com.liferay.commerce.model.CommercePriceEntry addCommercePriceEntry(
		long cpInstanceId, long commercePriceListId, double price,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryService.addCommercePriceEntry(cpInstanceId,
			commercePriceListId, price, serviceContext);
	}

	@Override
	public void deleteCommercePriceEntry(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceEntryService.deleteCommercePriceEntry(commercePriceEntryId);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceEntry fetchCommercePriceEntry(
		long commercePriceEntryId) {
		return _commercePriceEntryService.fetchCommercePriceEntry(commercePriceEntryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end) {
		return _commercePriceEntryService.getCommercePriceEntries(commercePriceListId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommercePriceEntry> getCommercePriceEntries(
		long commercePriceListId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommercePriceEntry> orderByComparator) {
		return _commercePriceEntryService.getCommercePriceEntries(commercePriceListId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceEntriesCount(long commercePriceListId) {
		return _commercePriceEntryService.getCommercePriceEntriesCount(commercePriceListId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commercePriceEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _commercePriceEntryService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.model.CommercePriceEntry> searchCommercePriceEntries(
		long companyId, long groupId, long commercePriceListId,
		java.lang.String keywords, int start, int end,
		com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryService.searchCommercePriceEntries(companyId,
			groupId, commercePriceListId, keywords, start, end, sort);
	}

	@Override
	public com.liferay.commerce.model.CommercePriceEntry updateCommercePriceEntry(
		long commercePriceEntryId, double price,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceEntryService.updateCommercePriceEntry(commercePriceEntryId,
			price, serviceContext);
	}

	@Override
	public CommercePriceEntryService getWrappedService() {
		return _commercePriceEntryService;
	}

	@Override
	public void setWrappedService(
		CommercePriceEntryService commercePriceEntryService) {
		_commercePriceEntryService = commercePriceEntryService;
	}

	private CommercePriceEntryService _commercePriceEntryService;
}
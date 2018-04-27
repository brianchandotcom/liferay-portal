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

package com.liferay.commerce.price.list.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListService
 * @generated
 */
@ProviderType
public class CommercePriceListServiceWrapper implements CommercePriceListService,
	ServiceWrapper<CommercePriceListService> {
	public CommercePriceListServiceWrapper(
		CommercePriceListService commercePriceListService) {
		_commercePriceListService = commercePriceListService;
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceList addCommercePriceList(
		long commerceCurrencyId, String name, double priority,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.addCommercePriceList(commerceCurrencyId,
			name, priority, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public void deleteCommercePriceList(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commercePriceListService.deleteCommercePriceList(commercePriceListId);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceList fetchCommercePriceList(
		long commercePriceListId) {
		return _commercePriceListService.fetchCommercePriceList(commercePriceListId);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceList> getCommercePriceLists(
		long groupId, int start, int end) {
		return _commercePriceListService.getCommercePriceLists(groupId, start,
			end);
	}

	@Override
	public java.util.List<com.liferay.commerce.price.list.model.CommercePriceList> getCommercePriceLists(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.price.list.model.CommercePriceList> orderByComparator) {
		return _commercePriceListService.getCommercePriceLists(groupId, status,
			start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListsCount(long groupId, int status) {
		return _commercePriceListService.getCommercePriceListsCount(groupId,
			status);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceListService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.Hits search(
		com.liferay.portal.kernel.search.SearchContext searchContext) {
		return _commercePriceListService.search(searchContext);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<com.liferay.commerce.price.list.model.CommercePriceList> searchCommercePriceLists(
		long companyId, long groupId, String keywords, int status, int start,
		int end, com.liferay.portal.kernel.search.Sort sort)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.searchCommercePriceLists(companyId,
			groupId, keywords, status, start, end, sort);
	}

	@Override
	public com.liferay.commerce.price.list.model.CommercePriceList updateCommercePriceList(
		long commercePriceListId, long commerceCurrencyId, String name,
		double priority, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, boolean neverExpire,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commercePriceListService.updateCommercePriceList(commercePriceListId,
			commerceCurrencyId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CommercePriceListService getWrappedService() {
		return _commercePriceListService;
	}

	@Override
	public void setWrappedService(
		CommercePriceListService commercePriceListService) {
		_commercePriceListService = commercePriceListService;
	}

	private CommercePriceListService _commercePriceListService;
}
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

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceListService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListService
 * @generated
 */
public class CommercePriceListServiceWrapper
	implements CommercePriceListService,
			   ServiceWrapper<CommercePriceListService> {

	public CommercePriceListServiceWrapper() {
		this(null);
	}

	public CommercePriceListServiceWrapper(
		CommercePriceListService commercePriceListService) {

		_commercePriceListService = commercePriceListService;
	}

	@Override
	public CommercePriceList addCommercePriceList(
			String externalReferenceCode, long groupId, long commerceCurrencyId,
			boolean netPrice, String type, long parentCommercePriceListId,
			boolean catalogBasePriceList, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.addCommercePriceList(
			externalReferenceCode, groupId, commerceCurrencyId, netPrice, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CommercePriceList addOrUpdateCommercePriceList(
			String externalReferenceCode, long groupId,
			long commercePriceListId, long commerceCurrencyId, boolean netPrice,
			String type, long parentCommercePriceListId,
			boolean catalogBasePriceList, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.addOrUpdateCommercePriceList(
			externalReferenceCode, groupId, commercePriceListId,
			commerceCurrencyId, netPrice, type, parentCommercePriceListId,
			catalogBasePriceList, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public void deleteCommercePriceList(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceListService.deleteCommercePriceList(commercePriceListId);
	}

	@Override
	public CommercePriceList fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public CommercePriceList fetchCatalogBaseCommercePriceListByType(
			long groupId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.
			fetchCatalogBaseCommercePriceListByType(groupId, type);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommercePriceList fetchCommerceCatalogBasePriceListByType(
			long groupId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.
			fetchCommerceCatalogBasePriceListByType(groupId, type);
	}

	@Override
	public CommercePriceList fetchCommercePriceList(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.fetchCommercePriceList(
			commercePriceListId);
	}

	@Override
	public CommercePriceList getCommercePriceList(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.getCommercePriceList(
			commercePriceListId);
	}

	@Override
	public java.util.List<CommercePriceList> getCommercePriceLists(
			long companyId, int status, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceList>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.getCommercePriceLists(
			companyId, status, start, end, orderByComparator);
	}

	@Override
	public int getCommercePriceListsCount(long companyId, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.getCommercePriceListsCount(
			companyId, status);
	}

	@Override
	public int getCommercePriceListsCount(
			long commercePricingClassId, String title)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commercePriceListService.getCommercePriceListsCount(
			commercePricingClassId, title);
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
	public java.util.List<CommercePriceList> searchByCommercePricingClassId(
			long commercePricingClassId, String name, int start, int end)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commercePriceListService.searchByCommercePricingClassId(
			commercePricingClassId, name, start, end);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommercePriceList> searchCommercePriceLists(
				long companyId, String keywords, int status, int start, int end,
				com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.searchCommercePriceLists(
			companyId, keywords, status, start, end, sort);
	}

	@Override
	public int searchCommercePriceListsCount(
			long companyId, String keywords, int status)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.searchCommercePriceListsCount(
			companyId, keywords, status);
	}

	@Override
	public void setCatalogBasePriceList(
			long groupId, long commercePriceListId, String type)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceListService.setCatalogBasePriceList(
			groupId, commercePriceListId, type);
	}

	@Override
	public CommercePriceList updateCommercePriceList(
			long commercePriceListId, long commerceCurrencyId, boolean netPrice,
			long parentCommercePriceListId, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, netPrice,
			parentCommercePriceListId, name, priority, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommercePriceList updateCommercePriceList(
			long commercePriceListId, long commerceCurrencyId, boolean netPrice,
			String type, long parentCommercePriceListId,
			boolean catalogBasePriceList, String name, double priority,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.updateCommercePriceList(
			commercePriceListId, commerceCurrencyId, netPrice, type,
			parentCommercePriceListId, catalogBasePriceList, name, priority,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CommercePriceList updateExternalReferenceCode(
			CommercePriceList commercePriceList, String externalReferenceCode,
			long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceListService.updateExternalReferenceCode(
			commercePriceList, externalReferenceCode, companyId);
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
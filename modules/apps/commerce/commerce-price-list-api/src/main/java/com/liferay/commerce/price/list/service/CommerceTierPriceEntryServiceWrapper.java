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

import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceTierPriceEntryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceTierPriceEntryService
 * @generated
 */
public class CommerceTierPriceEntryServiceWrapper
	implements CommerceTierPriceEntryService,
			   ServiceWrapper<CommerceTierPriceEntryService> {

	public CommerceTierPriceEntryServiceWrapper() {
		this(null);
	}

	public CommerceTierPriceEntryServiceWrapper(
		CommerceTierPriceEntryService commerceTierPriceEntryService) {

		_commerceTierPriceEntryService = commerceTierPriceEntryService;
	}

	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			long commercePriceEntryId, java.math.BigDecimal price,
			java.math.BigDecimal promoPrice, int minQuantity,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.addCommerceTierPriceEntry(
			commercePriceEntryId, price, promoPrice, minQuantity,
			serviceContext);
	}

	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			java.math.BigDecimal price, java.math.BigDecimal promoPrice,
			int minQuantity,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.addCommerceTierPriceEntry(
			externalReferenceCode, commercePriceEntryId, price, promoPrice,
			minQuantity, serviceContext);
	}

	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			java.math.BigDecimal price, int minQuantity, boolean bulkPricing,
			boolean discountDiscovery, java.math.BigDecimal discountLevel1,
			java.math.BigDecimal discountLevel2,
			java.math.BigDecimal discountLevel3,
			java.math.BigDecimal discountLevel4, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.addCommerceTierPriceEntry(
			externalReferenceCode, commercePriceEntryId, price, minQuantity,
			bulkPricing, discountDiscovery, discountLevel1, discountLevel2,
			discountLevel3, discountLevel4, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommerceTierPriceEntry addOrUpdateCommerceTierPriceEntry(
			String externalReferenceCode, long commerceTierPriceEntryId,
			long commercePriceEntryId, java.math.BigDecimal price,
			java.math.BigDecimal promoPrice, int minQuantity,
			String priceEntryExternalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.addOrUpdateCommerceTierPriceEntry(
			externalReferenceCode, commerceTierPriceEntryId,
			commercePriceEntryId, price, promoPrice, minQuantity,
			priceEntryExternalReferenceCode, serviceContext);
	}

	@Override
	public CommerceTierPriceEntry addOrUpdateCommerceTierPriceEntry(
			String externalReferenceCode, long commerceTierPriceEntryId,
			long commercePriceEntryId, java.math.BigDecimal price,
			int minQuantity, boolean bulkPricing, boolean discountDiscovery,
			java.math.BigDecimal discountLevel1,
			java.math.BigDecimal discountLevel2,
			java.math.BigDecimal discountLevel3,
			java.math.BigDecimal discountLevel4, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String priceEntryExternalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.addOrUpdateCommerceTierPriceEntry(
			externalReferenceCode, commerceTierPriceEntryId,
			commercePriceEntryId, price, minQuantity, bulkPricing,
			discountDiscovery, discountLevel1, discountLevel2, discountLevel3,
			discountLevel4, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, priceEntryExternalReferenceCode,
			serviceContext);
	}

	@Override
	public void deleteCommerceTierPriceEntry(long commerceTierPriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceTierPriceEntryService.deleteCommerceTierPriceEntry(
			commerceTierPriceEntryId);
	}

	@Override
	public CommerceTierPriceEntry fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public java.util.List<CommerceTierPriceEntry> fetchCommerceTierPriceEntries(
			long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.fetchCommerceTierPriceEntries(
			companyId, start, end);
	}

	@Override
	public CommerceTierPriceEntry fetchCommerceTierPriceEntry(
			long commerceTierPriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.fetchCommerceTierPriceEntry(
			commerceTierPriceEntryId);
	}

	@Override
	public java.util.List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
			long commercePriceEntryId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.getCommerceTierPriceEntries(
			commercePriceEntryId, start, end);
	}

	@Override
	public java.util.List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
			long commercePriceEntryId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<CommerceTierPriceEntry> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.getCommerceTierPriceEntries(
			commercePriceEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTierPriceEntriesCount(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.getCommerceTierPriceEntriesCount(
			commercePriceEntryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommerceTierPriceEntriesCountByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.
			getCommerceTierPriceEntriesCountByCompanyId(companyId);
	}

	@Override
	public CommerceTierPriceEntry getCommerceTierPriceEntry(
			long commerceTierPriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.getCommerceTierPriceEntry(
			commerceTierPriceEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceTierPriceEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommerceTierPriceEntry> searchCommerceTierPriceEntries(
				long companyId, long commercePriceEntryId, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.searchCommerceTierPriceEntries(
			companyId, commercePriceEntryId, keywords, start, end, sort);
	}

	@Override
	public int searchCommerceTierPriceEntriesCount(
			long companyId, long commercePriceEntryId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.
			searchCommerceTierPriceEntriesCount(
				companyId, commercePriceEntryId, keywords);
	}

	@Override
	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
			long commerceTierPriceEntryId, java.math.BigDecimal price,
			java.math.BigDecimal promoPrice, int minQuantity,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.updateCommerceTierPriceEntry(
			commerceTierPriceEntryId, price, promoPrice, minQuantity,
			serviceContext);
	}

	@Override
	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
			long commerceTierPriceEntryId, java.math.BigDecimal price,
			int minQuantity, boolean bulkPricing, boolean discountDiscovery,
			java.math.BigDecimal discountLevel1,
			java.math.BigDecimal discountLevel2,
			java.math.BigDecimal discountLevel3,
			java.math.BigDecimal discountLevel4, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.updateCommerceTierPriceEntry(
			commerceTierPriceEntryId, price, minQuantity, bulkPricing,
			discountDiscovery, discountLevel1, discountLevel2, discountLevel3,
			discountLevel4, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
	public CommerceTierPriceEntry updateExternalReferenceCode(
			CommerceTierPriceEntry commerceTierPriceEntry,
			String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceTierPriceEntryService.updateExternalReferenceCode(
			commerceTierPriceEntry, externalReferenceCode);
	}

	@Override
	public CommerceTierPriceEntryService getWrappedService() {
		return _commerceTierPriceEntryService;
	}

	@Override
	public void setWrappedService(
		CommerceTierPriceEntryService commerceTierPriceEntryService) {

		_commerceTierPriceEntryService = commerceTierPriceEntryService;
	}

	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

}
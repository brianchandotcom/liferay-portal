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

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommercePriceEntryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryService
 * @generated
 */
public class CommercePriceEntryServiceWrapper
	implements CommercePriceEntryService,
			   ServiceWrapper<CommercePriceEntryService> {

	public CommercePriceEntryServiceWrapper() {
		this(null);
	}

	public CommercePriceEntryServiceWrapper(
		CommercePriceEntryService commercePriceEntryService) {

		_commercePriceEntryService = commercePriceEntryService;
	}

	@Override
	public CommercePriceEntry addCommercePriceEntry(
			long cpInstanceId, long commercePriceListId,
			java.math.BigDecimal price, java.math.BigDecimal promoPrice,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.addCommercePriceEntry(
			cpInstanceId, commercePriceListId, price, promoPrice,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommercePriceEntry(String, long, String, long,
	 BigDecimal, boolean, BigDecimal, BigDecimal, BigDecimal,
	 int, int, int, int, int, int, int, int, int, int, boolean,
	 ServiceContext)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry addCommercePriceEntry(
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			String externalReferenceCode, java.math.BigDecimal price,
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

		return _commercePriceEntryService.addCommercePriceEntry(
			cProductId, cpInstanceUuid, commercePriceListId,
			externalReferenceCode, price, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cpInstanceId,
			long commercePriceListId, java.math.BigDecimal price,
			java.math.BigDecimal promoPrice,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.addCommercePriceEntry(
			externalReferenceCode, cpInstanceId, commercePriceListId, price,
			promoPrice, serviceContext);
	}

	@Override
	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cProductId,
			String cpInstanceUuid, long commercePriceListId,
			java.math.BigDecimal price, boolean discountDiscovery,
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

		return _commercePriceEntryService.addCommercePriceEntry(
			externalReferenceCode, cProductId, cpInstanceUuid,
			commercePriceListId, price, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			java.math.BigDecimal price, java.math.BigDecimal promoPrice,
			String skuExternalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.addOrUpdateCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, promoPrice,
			skuExternalReferenceCode, serviceContext);
	}

	@Override
	public CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			java.math.BigDecimal price, boolean discountDiscovery,
			java.math.BigDecimal discountLevel1,
			java.math.BigDecimal discountLevel2,
			java.math.BigDecimal discountLevel3,
			java.math.BigDecimal discountLevel4, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.addOrUpdateCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, discountDiscovery,
			discountLevel1, discountLevel2, discountLevel3, discountLevel4,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, skuExternalReferenceCode, serviceContext);
	}

	@Override
	public void deleteCommercePriceEntry(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commercePriceEntryService.deleteCommercePriceEntry(
			commercePriceEntryId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	@Override
	public CommercePriceEntry fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	@Override
	public CommercePriceEntry fetchCommercePriceEntry(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.fetchCommercePriceEntry(
			commercePriceEntryId);
	}

	@Override
	public java.util.List<CommercePriceEntry> getCommercePriceEntries(
			long commercePriceListId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.getCommercePriceEntries(
			commercePriceListId, start, end);
	}

	@Override
	public java.util.List<CommercePriceEntry> getCommercePriceEntries(
			long commercePriceListId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.getCommercePriceEntries(
			commercePriceListId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public java.util.List<CommercePriceEntry>
			getCommercePriceEntriesByCompanyId(
				long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.getCommercePriceEntriesByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getCommercePriceEntriesCount(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.getCommercePriceEntriesCount(
			commercePriceListId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommercePriceEntriesCountByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.
			getCommercePriceEntriesCountByCompanyId(companyId);
	}

	@Override
	public CommercePriceEntry getCommercePriceEntry(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.getCommercePriceEntry(
			commercePriceEntryId);
	}

	@Override
	public CommercePriceEntry getInstanceBaseCommercePriceEntry(
		String cpInstanceUuid, String priceListType) {

		return _commercePriceEntryService.getInstanceBaseCommercePriceEntry(
			cpInstanceUuid, priceListType);
	}

	@Override
	public java.util.List<CommercePriceEntry> getInstanceCommercePriceEntries(
			long cpInstanceId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.getInstanceCommercePriceEntries(
			cpInstanceId, start, end);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public java.util.List<CommercePriceEntry> getInstanceCommercePriceEntries(
			long cpInstanceId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator<CommercePriceEntry>
				orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.getInstanceCommercePriceEntries(
			cpInstanceId, start, end, orderByComparator);
	}

	@Override
	public int getInstanceCommercePriceEntriesCount(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.getInstanceCommercePriceEntriesCount(
			cpInstanceId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commercePriceEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<CommercePriceEntry> searchCommercePriceEntries(
				long companyId, long commercePriceListId, String keywords,
				int start, int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.searchCommercePriceEntries(
			companyId, commercePriceListId, keywords, start, end, sort);
	}

	@Override
	public int searchCommercePriceEntriesCount(
			long companyId, long commercePriceListId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.searchCommercePriceEntriesCount(
			companyId, commercePriceListId, keywords);
	}

	@Override
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, java.math.BigDecimal price,
			java.math.BigDecimal promoPrice,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.updateCommercePriceEntry(
			commercePriceEntryId, price, promoPrice, serviceContext);
	}

	@Override
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, java.math.BigDecimal price,
			boolean discountDiscovery, java.math.BigDecimal discountLevel1,
			java.math.BigDecimal discountLevel2,
			java.math.BigDecimal discountLevel3,
			java.math.BigDecimal discountLevel4, boolean bulkPricing,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.updateCommercePriceEntry(
			commercePriceEntryId, price, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, bulkPricing,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, java.math.BigDecimal price,
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

		return _commercePriceEntryService.updateCommercePriceEntry(
			commercePriceEntryId, price, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #updateExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry updateExternalReferenceCode(
			CommercePriceEntry commercePriceEntry, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.updateExternalReferenceCode(
			commercePriceEntry, externalReferenceCode);
	}

	@Override
	public CommercePriceEntry updateExternalReferenceCode(
			String externalReferenceCode, CommercePriceEntry commercePriceEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.updateExternalReferenceCode(
			externalReferenceCode, commercePriceEntry);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cpInstanceId,
			long commercePriceListId, String externalReferenceCode,
			java.math.BigDecimal price, java.math.BigDecimal promoPrice,
			String skuExternalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.upsertCommercePriceEntry(
			commercePriceEntryId, cpInstanceId, commercePriceListId,
			externalReferenceCode, price, promoPrice, skuExternalReferenceCode,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addOrUpdateCommercePriceEntry(String, long, long, String, long,
	 BigDecimal, BigDecimal, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cProductId, String cpInstanceUuid,
			long commercePriceListId, String externalReferenceCode,
			java.math.BigDecimal price, java.math.BigDecimal promoPrice,
			String skuExternalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.upsertCommercePriceEntry(
			commercePriceEntryId, cProductId, cpInstanceUuid,
			commercePriceListId, externalReferenceCode, price, promoPrice,
			skuExternalReferenceCode, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addOrUpdateCommercePriceEntry(String, long, long, String, long,
	 BigDecimal, boolean, BigDecimal, BigDecimal, BigDecimal,
	 BigDecimal, int, int, int, int, int, int, int, int, int,
	 int, boolean, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cProductId, String cpInstanceUuid,
			long commercePriceListId, String externalReferenceCode,
			java.math.BigDecimal price, boolean discountDiscovery,
			java.math.BigDecimal discountLevel1,
			java.math.BigDecimal discountLevel2,
			java.math.BigDecimal discountLevel3,
			java.math.BigDecimal discountLevel4, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commercePriceEntryService.upsertCommercePriceEntry(
			commercePriceEntryId, cProductId, cpInstanceUuid,
			commercePriceListId, externalReferenceCode, price,
			discountDiscovery, discountLevel1, discountLevel2, discountLevel3,
			discountLevel4, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, skuExternalReferenceCode,
			serviceContext);
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
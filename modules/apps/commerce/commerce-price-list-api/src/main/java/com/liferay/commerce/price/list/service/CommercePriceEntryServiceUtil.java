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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommercePriceEntry. This utility wraps
 * <code>com.liferay.commerce.price.list.service.impl.CommercePriceEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryService
 * @generated
 */
public class CommercePriceEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			addCommercePriceEntry(
				long cpInstanceId, long commercePriceListId,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceEntry(
			cpInstanceId, commercePriceListId, price, promoPrice,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			addCommercePriceEntry(
				long cpInstanceId, long commercePriceListId,
				String externalReferenceCode, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceEntry(
			cpInstanceId, commercePriceListId, externalReferenceCode, price,
			promoPrice, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommercePriceEntry(String, long, String, long,
	 BigDecimal, boolean, BigDecimal, BigDecimal, BigDecimal,
	 int, int, int, int, int, int, int, int, int, int, boolean,
	 ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			addCommercePriceEntry(
				long cProductId, String cpInstanceUuid,
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
				boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommercePriceEntry(
			cProductId, cpInstanceUuid, commercePriceListId,
			externalReferenceCode, price, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			addCommercePriceEntry(
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

		return getService().addCommercePriceEntry(
			externalReferenceCode, cProductId, cpInstanceUuid,
			commercePriceListId, price, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	public static void deleteCommercePriceEntry(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommercePriceEntry(commercePriceEntryId);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			fetchByExternalReferenceCode(
				long companyId, String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			companyId, externalReferenceCode);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			fetchByExternalReferenceCode(
				String externalReferenceCode, long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			fetchCommercePriceEntry(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommercePriceEntry(commercePriceEntryId);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				getCommercePriceEntries(
					long commercePriceListId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceEntries(
			commercePriceListId, start, end);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				getCommercePriceEntries(
					long commercePriceListId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceEntry> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceEntries(
			commercePriceListId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				getCommercePriceEntriesByCompanyId(
					long companyId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceEntriesByCompanyId(
			companyId, start, end);
	}

	public static int getCommercePriceEntriesCount(long commercePriceListId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceEntriesCount(commercePriceListId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static int getCommercePriceEntriesCountByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceEntriesCountByCompanyId(companyId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			getCommercePriceEntry(long commercePriceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommercePriceEntry(commercePriceEntryId);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
		getInstanceBaseCommercePriceEntry(
			String cpInstanceUuid, String priceListType) {

		return getService().getInstanceBaseCommercePriceEntry(
			cpInstanceUuid, priceListType);
	}

	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				getInstanceCommercePriceEntries(
					long cpInstanceId, int start, int end)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getInstanceCommercePriceEntries(
			cpInstanceId, start, end);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static java.util.List
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				getInstanceCommercePriceEntries(
					long cpInstanceId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.price.list.model.
							CommercePriceEntry> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getInstanceCommercePriceEntries(
			cpInstanceId, start, end, orderByComparator);
	}

	public static int getInstanceCommercePriceEntriesCount(long cpInstanceId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getInstanceCommercePriceEntriesCount(cpInstanceId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.price.list.model.CommercePriceEntry>
				searchCommercePriceEntries(
					long companyId, long commercePriceListId, String keywords,
					int start, int end,
					com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommercePriceEntries(
			companyId, commercePriceListId, keywords, start, end, sort);
	}

	public static int searchCommercePriceEntriesCount(
			long companyId, long commercePriceListId, String keywords)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().searchCommercePriceEntriesCount(
			companyId, commercePriceListId, keywords);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			updateCommercePriceEntry(
				long commercePriceEntryId, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceEntry(
			commercePriceEntryId, price, promoPrice, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			updateCommercePriceEntry(
				long commercePriceEntryId, java.math.BigDecimal price,
				boolean discountDiscovery, java.math.BigDecimal discountLevel1,
				java.math.BigDecimal discountLevel2,
				java.math.BigDecimal discountLevel3,
				java.math.BigDecimal discountLevel4, boolean bulkPricing,
				int displayDateMonth, int displayDateDay, int displayDateYear,
				int displayDateHour, int displayDateMinute,
				int expirationDateMonth, int expirationDateDay,
				int expirationDateYear, int expirationDateHour,
				int expirationDateMinute, boolean neverExpire,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommercePriceEntry(
			commercePriceEntryId, price, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, bulkPricing,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			updateCommercePriceEntry(
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

		return getService().updateCommercePriceEntry(
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
	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			updateExternalReferenceCode(
				com.liferay.commerce.price.list.model.CommercePriceEntry
					commercePriceEntry,
				String externalReferenceCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateExternalReferenceCode(
			commercePriceEntry, externalReferenceCode);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			updateExternalReferenceCode(
				String externalReferenceCode,
				com.liferay.commerce.price.list.model.CommercePriceEntry
					commercePriceEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateExternalReferenceCode(
			externalReferenceCode, commercePriceEntry);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			upsertCommercePriceEntry(
				long commercePriceEntryId, long cpInstanceId,
				long commercePriceListId, String externalReferenceCode,
				java.math.BigDecimal price, java.math.BigDecimal promoPrice,
				String skuExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceEntry(
			commercePriceEntryId, cpInstanceId, commercePriceListId,
			externalReferenceCode, price, promoPrice, skuExternalReferenceCode,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #upsertCommercePriceEntry(String, long, long, String, long,
	 BigDecimal, BigDecimal, String, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			upsertCommercePriceEntry(
				long commercePriceEntryId, long cProductId,
				String cpInstanceUuid, long commercePriceListId,
				String externalReferenceCode, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice,
				String skuExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceEntry(
			commercePriceEntryId, cProductId, cpInstanceUuid,
			commercePriceListId, externalReferenceCode, price, promoPrice,
			skuExternalReferenceCode, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #upsertCommercePriceEntry(String, long, long, String, long,
	 BigDecimal, boolean, BigDecimal, BigDecimal, BigDecimal,
	 BigDecimal, int, int, int, int, int, int, int, int, int,
	 int, boolean, String, ServiceContext)}
	 */
	@Deprecated
	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			upsertCommercePriceEntry(
				long commercePriceEntryId, long cProductId,
				String cpInstanceUuid, long commercePriceListId,
				String externalReferenceCode, java.math.BigDecimal price,
				boolean discountDiscovery, java.math.BigDecimal discountLevel1,
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

		return getService().upsertCommercePriceEntry(
			commercePriceEntryId, cProductId, cpInstanceUuid,
			commercePriceListId, externalReferenceCode, price,
			discountDiscovery, discountLevel1, discountLevel2, discountLevel3,
			discountLevel4, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, skuExternalReferenceCode,
			serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			upsertCommercePriceEntry(
				String externalReferenceCode, long commercePriceEntryId,
				long cProductId, String cpInstanceUuid,
				long commercePriceListId, java.math.BigDecimal price,
				java.math.BigDecimal promoPrice,
				String skuExternalReferenceCode,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().upsertCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, promoPrice,
			skuExternalReferenceCode, serviceContext);
	}

	public static com.liferay.commerce.price.list.model.CommercePriceEntry
			upsertCommercePriceEntry(
				String externalReferenceCode, long commercePriceEntryId,
				long cProductId, String cpInstanceUuid,
				long commercePriceListId, java.math.BigDecimal price,
				boolean discountDiscovery, java.math.BigDecimal discountLevel1,
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

		return getService().upsertCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, discountDiscovery,
			discountLevel1, discountLevel2, discountLevel3, discountLevel4,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, skuExternalReferenceCode, serviceContext);
	}

	public static CommercePriceEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommercePriceEntryService, CommercePriceEntryService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommercePriceEntryService.class);

		ServiceTracker<CommercePriceEntryService, CommercePriceEntryService>
			serviceTracker =
				new ServiceTracker
					<CommercePriceEntryService, CommercePriceEntryService>(
						bundle.getBundleContext(),
						CommercePriceEntryService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}
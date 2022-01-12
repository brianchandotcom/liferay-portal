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
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for CommercePriceEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceEntryServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@OSGiBeanProperties(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CommercePriceEntry"
	},
	service = CommercePriceEntryService.class
)
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface CommercePriceEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.commerce.price.list.service.impl.CommercePriceEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the commerce price entry remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link CommercePriceEntryServiceUtil} if injection and service tracking are not available.
	 */
	public CommercePriceEntry addCommercePriceEntry(
			long cpInstanceId, long commercePriceListId, BigDecimal price,
			BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addCommercePriceEntry(String, long, String, long,
	 BigDecimal, boolean, BigDecimal, BigDecimal, BigDecimal,
	 int, int, int, int, int, int, int, int, int, int, boolean,
	 ServiceContext)}
	 */
	@Deprecated
	public CommercePriceEntry addCommercePriceEntry(
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			String externalReferenceCode, BigDecimal price,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cpInstanceId,
			long commercePriceListId, BigDecimal price, BigDecimal promoPrice,
			ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry addCommercePriceEntry(
			String externalReferenceCode, long cProductId,
			String cpInstanceUuid, long commercePriceListId, BigDecimal price,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry addOrUpdateCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			BigDecimal price, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteCommercePriceEntry(long commercePriceEntryId)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry fetchCommercePriceEntry(long commercePriceEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntries(
			long commercePriceListId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntries(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getCommercePriceEntriesByCompanyId(
			long companyId, int start, int end)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceEntriesCount(long commercePriceListId)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCommercePriceEntriesCountByCompanyId(long companyId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry getCommercePriceEntry(long commercePriceEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CommercePriceEntry getInstanceBaseCommercePriceEntry(
		String cpInstanceUuid, String priceListType);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
			long cpInstanceId, int start, int end)
		throws PortalException;

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
			long cpInstanceId, int start, int end,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getInstanceCommercePriceEntriesCount(long cpInstanceId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public BaseModelSearchResult<CommercePriceEntry> searchCommercePriceEntries(
			long companyId, long commercePriceListId, String keywords,
			int start, int end, Sort sort)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCommercePriceEntriesCount(
			long companyId, long commercePriceListId, String keywords)
		throws PortalException;

	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, boolean bulkPricing,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException;

	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price,
			boolean discountDiscovery, BigDecimal discountLevel1,
			BigDecimal discountLevel2, BigDecimal discountLevel3,
			BigDecimal discountLevel4, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #updateExternalReferenceCode(String, long)}
	 */
	@Deprecated
	public CommercePriceEntry updateExternalReferenceCode(
			CommercePriceEntry commercePriceEntry, String externalReferenceCode)
		throws PortalException;

	public CommercePriceEntry updateExternalReferenceCode(
			String externalReferenceCode, CommercePriceEntry commercePriceEntry)
		throws PortalException;

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cpInstanceId,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addOrUpdateCommercePriceEntry(String, long, long, String, long,
	 BigDecimal, BigDecimal, String, ServiceContext)}
	 */
	@Deprecated
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cProductId, String cpInstanceUuid,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException;

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 #addOrUpdateCommercePriceEntry(String, long, long, String, long,
	 BigDecimal, boolean, BigDecimal, BigDecimal, BigDecimal,
	 BigDecimal, int, int, int, int, int, int, int, int, int,
	 int, boolean, String, ServiceContext)}
	 */
	@Deprecated
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cProductId, String cpInstanceUuid,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String skuExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException;

}
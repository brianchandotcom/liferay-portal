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

package com.liferay.commerce.price.list.service.impl;

import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.base.CommercePriceEntryServiceBaseImpl;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.math.BigDecimal;

import java.util.Collections;
import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
public class CommercePriceEntryServiceImpl
	extends CommercePriceEntryServiceBaseImpl {

	@Override
	public CommercePriceEntry addCommercePriceEntry(
			long cpInstanceId, long commercePriceListId, BigDecimal price,
			BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException {

		return addCommercePriceEntry(
			cpInstanceId, commercePriceListId, null, price, promoPrice,
			serviceContext);
	}

	@Override
	public CommercePriceEntry addCommercePriceEntry(
			long cpInstanceId, long commercePriceListId,
			String externalReferenceCode, BigDecimal price,
			BigDecimal promoPrice, ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceEntryLocalService.addCommercePriceEntry(
			cpInstanceId, commercePriceListId, externalReferenceCode, price,
			promoPrice, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addCommercePriceEntry(String, long, String, long,
	 *             BigDecimal, boolean, BigDecimal, BigDecimal, BigDecimal,
	 *             int, int, int, int, int, int, int, int, int, int, boolean,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
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
		throws PortalException {

		return addCommercePriceEntry(
			externalReferenceCode, cProductId, cpInstanceUuid,
			commercePriceListId, price, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
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
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceEntryLocalService.addCommercePriceEntry(
			externalReferenceCode, cProductId, cpInstanceUuid,
			commercePriceListId, price, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public void deleteCommercePriceEntry(long commercePriceEntryId)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.UPDATE);

		commercePriceEntryLocalService.deleteCommercePriceEntry(
			commercePriceEntry);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #fetchByExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		return fetchByExternalReferenceCode(externalReferenceCode, companyId);
	}

	@Override
	public CommercePriceEntry fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, companyId);

		if (commercePriceEntry != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(), ActionKeys.VIEW);
		}

		return commercePriceEntry;
	}

	@Override
	public CommercePriceEntry fetchCommercePriceEntry(long commercePriceEntryId)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceEntryId);

		if (commercePriceEntry != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(), ActionKeys.VIEW);
		}

		return commercePriceEntry;
	}

	@Override
	public List<CommercePriceEntry> getCommercePriceEntries(
			long commercePriceListId, int start, int end)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceEntryLocalService.getCommercePriceEntries(
			commercePriceListId, start, end);
	}

	@Override
	public List<CommercePriceEntry> getCommercePriceEntries(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceEntryLocalService.getCommercePriceEntries(
			commercePriceListId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommercePriceEntry> getCommercePriceEntriesByCompanyId(
			long companyId, int start, int end)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getCommercePriceEntriesCount(long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceEntryLocalService.getCommercePriceEntriesCount(
			commercePriceListId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommercePriceEntriesCountByCompanyId(long companyId)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CommercePriceEntry getCommercePriceEntry(long commercePriceEntryId)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.VIEW);

		return commercePriceEntry;
	}

	@Override
	public CommercePriceEntry getInstanceBaseCommercePriceEntry(
		String cpInstanceUuid, String priceListType) {

		return commercePriceListFinder.findBasePriceEntry(
			cpInstanceUuid, priceListType, true);
	}

	@Override
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
			long cpInstanceId, int start, int end)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return Collections.emptyList();
		}

		return commercePriceListFinder.findByCPInstanceUuid(
			cpInstance.getCPInstanceUuid(), start, end, true);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommercePriceEntry> getInstanceCommercePriceEntries(
			long cpInstanceId, int start, int end,
			OrderByComparator<CommercePriceEntry> orderByComparator)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public int getInstanceCommercePriceEntriesCount(long cpInstanceId)
		throws PortalException {

		CPInstance cpInstance = _cpInstanceService.fetchCPInstance(
			cpInstanceId);

		if (cpInstance == null) {
			return 0;
		}

		return commercePriceListFinder.countByCPInstanceUuid(
			cpInstance.getCPInstanceUuid(), true);
	}

	@Override
	public BaseModelSearchResult<CommercePriceEntry> searchCommercePriceEntries(
			long companyId, long commercePriceListId, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceEntryLocalService.searchCommercePriceEntries(
			companyId, commercePriceListId, keywords, start, end, sort);
	}

	@Override
	public int searchCommercePriceEntriesCount(
			long companyId, long commercePriceListId, String keywords)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceEntryLocalService.searchCommercePriceEntriesCount(
			companyId, commercePriceListId, keywords);
	}

	@Override
	public CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.UPDATE);

		return commercePriceEntryLocalService.updateCommercePriceEntry(
			commercePriceEntryId, price, promoPrice, serviceContext);
	}

	@Override
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
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.UPDATE);

		return commercePriceEntryLocalService.updateCommercePriceEntry(
			commercePriceEntryId, price, null, discountDiscovery,
			discountLevel1, discountLevel2, discountLevel3, discountLevel4,
			bulkPricing, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Override
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
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.UPDATE);

		return commercePriceEntryLocalService.updateCommercePriceEntry(
			commercePriceEntryId, price, discountDiscovery, discountLevel1,
			discountLevel2, discountLevel3, discountLevel4, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #updateExternalReferenceCode(String, long)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry updateExternalReferenceCode(
			CommercePriceEntry commercePriceEntry, String externalReferenceCode)
		throws PortalException {

		return updateExternalReferenceCode(
			externalReferenceCode, commercePriceEntry);
	}

	@Override
	public CommercePriceEntry updateExternalReferenceCode(
			String externalReferenceCode, CommercePriceEntry commercePriceEntry)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.UPDATE);

		return commercePriceEntryLocalService.updateExternalReferenceCode(
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
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceEntryLocalService.upsertCommercePriceEntry(
			commercePriceEntryId, cpInstanceId, commercePriceListId,
			externalReferenceCode, price, promoPrice, skuExternalReferenceCode,
			serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #upsertCommercePriceEntry(String, long, long, String, long,
	 *             BigDecimal, BigDecimal, String, ServiceContext)}
	 */
	@Deprecated
	@Override
	public CommercePriceEntry upsertCommercePriceEntry(
			long commercePriceEntryId, long cProductId, String cpInstanceUuid,
			long commercePriceListId, String externalReferenceCode,
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		return upsertCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, promoPrice,
			skuExternalReferenceCode, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #upsertCommercePriceEntry(String, long, long, String, long,
	 *             BigDecimal, boolean, BigDecimal, BigDecimal, BigDecimal,
	 *             BigDecimal, int, int, int, int, int, int, int, int, int,
	 *             int, boolean, String, ServiceContext)}
	 */
	@Deprecated
	@Override
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
		throws PortalException {

		return upsertCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, discountDiscovery,
			discountLevel1, discountLevel2, discountLevel3, discountLevel4,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, skuExternalReferenceCode, serviceContext);
	}

	@Override
	public CommercePriceEntry upsertCommercePriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			long cProductId, String cpInstanceUuid, long commercePriceListId,
			BigDecimal price, BigDecimal promoPrice,
			String skuExternalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceEntryLocalService.upsertCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, promoPrice,
			skuExternalReferenceCode, serviceContext);
	}

	@Override
	public CommercePriceEntry upsertCommercePriceEntry(
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
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceEntryLocalService.upsertCommercePriceEntry(
			externalReferenceCode, commercePriceEntryId, cProductId,
			cpInstanceUuid, commercePriceListId, price, discountDiscovery,
			discountLevel1, discountLevel2, discountLevel3, discountLevel4,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, skuExternalReferenceCode, serviceContext);
	}

	private static volatile ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommercePriceEntryServiceImpl.class,
				"_commercePriceListModelResourcePermission",
				CommercePriceList.class);

	@ServiceReference(type = CPInstanceService.class)
	private CPInstanceService _cpInstanceService;

}
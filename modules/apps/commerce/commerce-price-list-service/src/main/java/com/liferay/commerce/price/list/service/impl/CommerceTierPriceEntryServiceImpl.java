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
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.base.CommerceTierPriceEntryServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 * @author Zoltán Takács
 */
public class CommerceTierPriceEntryServiceImpl
	extends CommerceTierPriceEntryServiceBaseImpl {

	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			int minQuantity, ServiceContext serviceContext)
		throws PortalException {

		return addCommerceTierPriceEntry(
			null, commercePriceEntryId, price, promoPrice, minQuantity,
			serviceContext);
	}

	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			BigDecimal price, BigDecimal promoPrice, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		if (commercePriceEntry != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(), ActionKeys.UPDATE);
		}

		return commerceTierPriceEntryLocalService.addCommerceTierPriceEntry(
			externalReferenceCode, commercePriceEntryId, price, promoPrice,
			minQuantity, serviceContext);
	}

	@Override
	public CommerceTierPriceEntry addCommerceTierPriceEntry(
			String externalReferenceCode, long commercePriceEntryId,
			BigDecimal price, int minQuantity, boolean bulkPricing,
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

		return commerceTierPriceEntryLocalService.addCommerceTierPriceEntry(
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
			long commercePriceEntryId, BigDecimal price, BigDecimal promoPrice,
			int minQuantity, String priceEntryExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceEntryId);

		if ((commercePriceEntry == null) &&
			Validator.isNotNull(priceEntryExternalReferenceCode)) {

			commercePriceEntry =
				commercePriceEntryLocalService.
					fetchCommercePriceEntryByReferenceCode(
						serviceContext.getCompanyId(),
						priceEntryExternalReferenceCode);
		}

		if (commercePriceEntry != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(), ActionKeys.UPDATE);
		}

		return commerceTierPriceEntryLocalService.
			addOrUpdateCommerceTierPriceEntry(
				externalReferenceCode, commerceTierPriceEntryId,
				commercePriceEntryId, price, promoPrice, minQuantity,
				priceEntryExternalReferenceCode, serviceContext);
	}

	@Override
	public CommerceTierPriceEntry addOrUpdateCommerceTierPriceEntry(
			String externalReferenceCode, long commerceTierPriceEntryId,
			long commercePriceEntryId, BigDecimal price, int minQuantity,
			boolean bulkPricing, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, String priceEntryExternalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceEntryId);

		if ((commercePriceEntry == null) &&
			Validator.isNotNull(priceEntryExternalReferenceCode)) {

			commercePriceEntry =
				commercePriceEntryLocalService.
					fetchCommercePriceEntryByReferenceCode(
						serviceContext.getCompanyId(),
						priceEntryExternalReferenceCode);
		}

		if (commercePriceEntry != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(), ActionKeys.UPDATE);
		}

		return commerceTierPriceEntryLocalService.
			addOrUpdateCommerceTierPriceEntry(
				externalReferenceCode, commerceTierPriceEntryId,
				commercePriceEntryId, price, minQuantity, bulkPricing,
				discountDiscovery, discountLevel1, discountLevel2,
				discountLevel3, discountLevel4, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, priceEntryExternalReferenceCode, serviceContext);
	}

	@Override
	public void deleteCommerceTierPriceEntry(long commerceTierPriceEntryId)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryLocalService.getCommerceTierPriceEntry(
				commerceTierPriceEntryId);

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.UPDATE);

		commerceTierPriceEntryLocalService.deleteCommerceTierPriceEntry(
			commerceTierPriceEntryId);
	}

	@Override
	public CommerceTierPriceEntry fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, companyId);

		if (commerceTierPriceEntry != null) {
			CommercePriceEntry commercePriceEntry =
				commerceTierPriceEntry.getCommercePriceEntry();

			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(), ActionKeys.VIEW);
		}

		return commerceTierPriceEntry;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommerceTierPriceEntry> fetchCommerceTierPriceEntries(
			long companyId, int start, int end)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CommerceTierPriceEntry fetchCommerceTierPriceEntry(
			long commerceTierPriceEntryId)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryLocalService.fetchCommerceTierPriceEntry(
				commerceTierPriceEntryId);

		if (commerceTierPriceEntry != null) {
			CommercePriceEntry commercePriceEntry =
				commerceTierPriceEntry.getCommercePriceEntry();

			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(), ActionKeys.VIEW);
		}

		return commerceTierPriceEntry;
	}

	@Override
	public List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
			long commercePriceEntryId, int start, int end)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.VIEW);

		return commerceTierPriceEntryLocalService.getCommerceTierPriceEntries(
			commercePriceEntryId, start, end);
	}

	@Override
	public List<CommerceTierPriceEntry> getCommerceTierPriceEntries(
			long commercePriceEntryId, int start, int end,
			OrderByComparator<CommerceTierPriceEntry> orderByComparator)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.VIEW);

		return commerceTierPriceEntryLocalService.getCommerceTierPriceEntries(
			commercePriceEntryId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceTierPriceEntriesCount(long commercePriceEntryId)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.VIEW);

		return commerceTierPriceEntryLocalService.
			getCommerceTierPriceEntriesCount(commercePriceEntryId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommerceTierPriceEntriesCountByCompanyId(long companyId)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CommerceTierPriceEntry getCommerceTierPriceEntry(
			long commerceTierPriceEntryId)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryLocalService.getCommerceTierPriceEntry(
				commerceTierPriceEntryId);

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.VIEW);

		return commerceTierPriceEntry;
	}

	@Override
	public BaseModelSearchResult<CommerceTierPriceEntry>
			searchCommerceTierPriceEntries(
				long companyId, long commercePriceEntryId, String keywords,
				int start, int end, Sort sort)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.fetchCommercePriceEntry(
				commercePriceEntryId);

		if (commercePriceEntry != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(), ActionKeys.VIEW);
		}

		return commerceTierPriceEntryLocalService.
			searchCommerceTierPriceEntries(
				companyId, commercePriceEntryId, keywords, start, end, sort);
	}

	@Override
	public int searchCommerceTierPriceEntriesCount(
			long companyId, long commercePriceEntryId, String keywords)
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commercePriceEntryLocalService.getCommercePriceEntry(
				commercePriceEntryId);

		if (commercePriceEntry != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(),
				commercePriceEntry.getCommercePriceListId(), ActionKeys.UPDATE);
		}

		return commercePriceEntryLocalService.searchCommercePriceEntriesCount(
			companyId, commercePriceEntryId, keywords);
	}

	@Override
	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
			long commerceTierPriceEntryId, BigDecimal price,
			BigDecimal promoPrice, int minQuantity,
			ServiceContext serviceContext)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryLocalService.getCommerceTierPriceEntry(
				commerceTierPriceEntryId);

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.UPDATE);

		return commerceTierPriceEntryLocalService.updateCommerceTierPriceEntry(
			commerceTierPriceEntryId, price, promoPrice, minQuantity,
			serviceContext);
	}

	@Override
	public CommerceTierPriceEntry updateCommerceTierPriceEntry(
			long commerceTierPriceEntryId, BigDecimal price, int minQuantity,
			boolean bulkPricing, boolean discountDiscovery,
			BigDecimal discountLevel1, BigDecimal discountLevel2,
			BigDecimal discountLevel3, BigDecimal discountLevel4,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		CommerceTierPriceEntry commerceTierPriceEntry =
			commerceTierPriceEntryLocalService.getCommerceTierPriceEntry(
				commerceTierPriceEntryId);

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.UPDATE);

		return commerceTierPriceEntryLocalService.updateCommerceTierPriceEntry(
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
		throws PortalException {

		CommercePriceEntry commercePriceEntry =
			commerceTierPriceEntry.getCommercePriceEntry();

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceEntry.getCommercePriceListId(),
			ActionKeys.UPDATE);

		return commerceTierPriceEntryLocalService.updateExternalReferenceCode(
			commerceTierPriceEntry, externalReferenceCode);
	}

	private static volatile ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommerceTierPriceEntryServiceImpl.class,
				"_commercePriceListModelResourcePermission",
				CommercePriceList.class);

}
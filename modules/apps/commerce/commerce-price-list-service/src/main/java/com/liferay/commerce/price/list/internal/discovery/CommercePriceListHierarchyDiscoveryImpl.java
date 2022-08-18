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

package com.liferay.commerce.price.list.internal.discovery;

import com.liferay.commerce.account.util.CommerceAccountHelper;
import com.liferay.commerce.price.list.discovery.CommercePriceListDiscovery;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "commerce.price.list.discovery.key=" + CommercePricingConstants.ORDER_BY_HIERARCHY,
	service = CommercePriceListDiscovery.class
)
public class CommercePriceListHierarchyDiscoveryImpl
	implements CommercePriceListDiscovery {

	@Override
	public CommercePriceList getCommercePriceList(
			long groupId, long commerceAccountId, long commerceChannelId,
			long commerceOrderTypeId, String cpInstanceUuid, String type)
		throws PortalException {

		CommercePriceList firstEligibleCommercePriceList = null;

		CommerceChannelAccountEntryRel commerceChannelAccountEntryRel =
			_commerceChannelAccountEntryRelLocalService.
				fetchCommerceChannelAccountEntryRel(
					commerceAccountId, commerceChannelId,
					CommerceChannelAccountEntryRelConstants.TYPE_PRICE_LIST);

		List<CommercePriceList> commercePriceLists =
			_commercePriceListLocalService.
				getCommercePriceListsByAccountAndChannelAndOrderTypeId(
					groupId, commerceAccountId, commerceChannelId,
					commerceOrderTypeId, type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			firstEligibleCommercePriceList = commercePriceLists.get(0);
		}

		commercePriceLists =
			_commercePriceListLocalService.
				getCommercePriceListsByAccountAndOrderTypeId(
					groupId, commerceAccountId, commerceOrderTypeId, type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			if (firstEligibleCommercePriceList == null) {
				firstEligibleCommercePriceList = commercePriceLists.get(0);
			}
		}

		commercePriceLists =
			_commercePriceListLocalService.
				getCommercePriceListsByAccountAndChannelId(
					groupId, commerceAccountId, commerceChannelId, type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			if (firstEligibleCommercePriceList == null) {
				firstEligibleCommercePriceList = commercePriceLists.get(0);
			}
		}

		commercePriceLists =
			_commercePriceListLocalService.getCommercePriceListsByAccountId(
				groupId, commerceAccountId, type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			if (firstEligibleCommercePriceList == null) {
				firstEligibleCommercePriceList = commercePriceLists.get(0);
			}
		}

		long[] commerceAccountGroupIds =
			_commerceAccountHelper.getCommerceAccountGroupIds(
				commerceAccountId);

		commercePriceLists =
			_commercePriceListLocalService.
				getCommercePriceListsByAccountGroupsAndChannelAndOrderTypeId(
					groupId, commerceAccountGroupIds, commerceChannelId,
					commerceOrderTypeId, type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			if (firstEligibleCommercePriceList == null) {
				firstEligibleCommercePriceList = commercePriceLists.get(0);
			}
		}

		commercePriceLists =
			_commercePriceListLocalService.
				getCommercePriceListsByAccountGroupsAndOrderTypeId(
					groupId, commerceAccountGroupIds, commerceOrderTypeId,
					type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			if (firstEligibleCommercePriceList == null) {
				firstEligibleCommercePriceList = commercePriceLists.get(0);
			}
		}

		commercePriceLists =
			_commercePriceListLocalService.
				getCommercePriceListsByAccountGroupsAndChannelId(
					groupId, commerceAccountGroupIds, commerceChannelId, type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			if (firstEligibleCommercePriceList == null) {
				firstEligibleCommercePriceList = commercePriceLists.get(0);
			}
		}

		commercePriceLists =
			_commercePriceListLocalService.
				getCommercePriceListsByAccountGroupIds(
					groupId, commerceAccountGroupIds, type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			if (firstEligibleCommercePriceList == null) {
				firstEligibleCommercePriceList = commercePriceLists.get(0);
			}
		}

		commercePriceLists =
			_commercePriceListLocalService.
				getCommercePriceListsByChannelAndOrderTypeId(
					groupId, commerceChannelId, commerceOrderTypeId, type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			if (firstEligibleCommercePriceList == null) {
				firstEligibleCommercePriceList = commercePriceLists.get(0);
			}
		}

		commercePriceLists =
			_commercePriceListLocalService.getCommercePriceListsByOrderTypeId(
				groupId, commerceOrderTypeId, type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			if (firstEligibleCommercePriceList == null) {
				firstEligibleCommercePriceList = commercePriceLists.get(0);
			}
		}

		commercePriceLists =
			_commercePriceListLocalService.getCommercePriceListsByChannelId(
				groupId, commerceChannelId, type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			if (firstEligibleCommercePriceList == null) {
				firstEligibleCommercePriceList = commercePriceLists.get(0);
			}
		}

		commercePriceLists =
			_commercePriceListLocalService.getCommercePriceListsByUnqualified(
				groupId, type);

		if ((commercePriceLists != null) && !commercePriceLists.isEmpty()) {
			CommercePriceList defaultCommercePriceList =
				_getDefaultCommercePriceList(
					commerceChannelAccountEntryRel, commercePriceLists);

			if (defaultCommercePriceList != null) {
				return defaultCommercePriceList;
			}

			if (firstEligibleCommercePriceList == null) {
				firstEligibleCommercePriceList = commercePriceLists.get(0);
			}
		}

		return firstEligibleCommercePriceList;
	}

	private CommercePriceList _getDefaultCommercePriceList(
			CommerceChannelAccountEntryRel commerceChannelAccountEntryRel,
			List<CommercePriceList> commercePriceLists)
		throws PortalException {

		if (commerceChannelAccountEntryRel == null) {
			return null;
		}

		Stream<CommercePriceList> commercePriceListsStream =
			commercePriceLists.stream();

		if (commercePriceListsStream.mapToLong(
				CommercePriceList::getCommercePriceListId
			).anyMatch(
				commercePriceListId ->
					commercePriceListId ==
						commerceChannelAccountEntryRel.getClassPK()
			)) {

			return _commercePriceListLocalService.getCommercePriceList(
				commerceChannelAccountEntryRel.getClassPK());
		}

		return null;
	}

	@Reference
	private CommerceAccountHelper _commerceAccountHelper;

	@Reference
	private CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.discovery;

import com.liferay.account.service.AccountGroupLocalService;
import com.liferay.commerce.price.list.discovery.CommercePriceListDiscovery;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListLocalService;
import com.liferay.commerce.pricing.constants.CommercePricingConstants;
import com.liferay.commerce.product.constants.CommerceChannelAccountEntryRelConstants;
import com.liferay.commerce.product.model.CommerceChannelAccountEntryRel;
import com.liferay.commerce.product.service.CommerceChannelAccountEntryRelLocalService;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 * @author Alessio Antonio Rendina
 */
@Component(service = CommercePriceListDiscovery.class)
public class CommercePriceListHierarchyDiscoveryImpl
	implements CommercePriceListDiscovery {

	@Override
	public CommercePriceList getCommercePriceList(
			long groupId, long commerceAccountId, long commerceChannelId,
			long commerceOrderTypeId, String cpInstanceUuid,
			String currencyCode, String type, String unitOfMeasureKey)
		throws PortalException {

		return _getCommercePriceList(
			groupId, commerceAccountId, commerceChannelId, commerceOrderTypeId,
			currencyCode, type);
	}

	@Override
	public String getCommercePriceListDiscoveryKey() {
		return CommercePricingConstants.ORDER_BY_HIERARCHY;
	}

	private CommercePriceList _getCommercePriceList(
			long groupId, long commerceAccountId, long commerceChannelId,
			long commerceOrderTypeId, String currencyCode, String type)
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
					commerceOrderTypeId, currencyCode, type);

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
					groupId, commerceAccountId, commerceOrderTypeId,
					currencyCode, type);

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
					groupId, commerceAccountId, commerceChannelId, currencyCode,
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
			_commercePriceListLocalService.getCommercePriceListsByAccountId(
				groupId, commerceAccountId, currencyCode, type);

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
			_accountGroupLocalService.getAccountGroupIds(commerceAccountId);

		commercePriceLists =
			_commercePriceListLocalService.
				getCommercePriceListsByAccountGroupsAndChannelAndOrderTypeId(
					groupId, commerceAccountGroupIds, commerceChannelId,
					commerceOrderTypeId, currencyCode, type);

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
					currencyCode, type);

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
					groupId, commerceAccountGroupIds, commerceChannelId,
					currencyCode, type);

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
					groupId, commerceAccountGroupIds, currencyCode, type);

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
					groupId, commerceChannelId, commerceOrderTypeId,
					currencyCode, type);

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
				groupId, commerceOrderTypeId, currencyCode, type);

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
				groupId, commerceChannelId, currencyCode, type);

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
				groupId, currencyCode, type);

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

		for (CommercePriceList commercePriceList : commercePriceLists) {
			if (commerceChannelAccountEntryRel.getClassPK() !=
					commercePriceList.getCommercePriceListId()) {

				continue;
			}

			return _commercePriceListLocalService.getCommercePriceList(
				commerceChannelAccountEntryRel.getClassPK());
		}

		return null;
	}

	@Reference
	private AccountGroupLocalService _accountGroupLocalService;

	@Reference
	private CommerceChannelAccountEntryRelLocalService
		_commerceChannelAccountEntryRelLocalService;

	@Reference
	private CommercePriceListLocalService _commercePriceListLocalService;

}
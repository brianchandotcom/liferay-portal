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

package com.liferay.commerce.pricing.service.impl;

import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.base.CommercePriceModifierServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.List;

/**
 * @author Riccardo Alberti
 */
public class CommercePriceModifierServiceImpl
	extends CommercePriceModifierServiceBaseImpl {

	@Override
	public CommercePriceModifier addCommercePriceModifier(
			long groupId, String title, String target, long commercePriceListId,
			String modifierType, BigDecimal modifierAmount, double priority,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceModifierLocalService.addCommercePriceModifier(
			groupId, title, target, commercePriceListId, modifierType,
			modifierAmount, priority, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommercePriceModifier addOrUpdateCommercePriceModifier(
			String externalReferenceCode, long commercePriceModifierId,
			long groupId, String title, String target, long commercePriceListId,
			String modifierType, BigDecimal modifierAmount, double priority,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire,
			ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceModifierLocalService.
			addOrUpdateCommercePriceModifier(
				externalReferenceCode, getUserId(), commercePriceModifierId,
				groupId, title, target, commercePriceListId, modifierType,
				modifierAmount, priority, active, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
	}

	@Override
	public CommercePriceModifier deleteCommercePriceModifier(
			long commercePriceModifierId)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierLocalService.getCommercePriceModifier(
				commercePriceModifierId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(),
			commercePriceModifier.getCommercePriceListId(), ActionKeys.UPDATE);

		return commercePriceModifierLocalService.deleteCommercePriceModifier(
			commercePriceModifier);
	}

	@Override
	public CommercePriceModifier fetchByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierLocalService.fetchByExternalReferenceCode(
				externalReferenceCode, companyId);

		if (commercePriceModifier != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(),
				commercePriceModifier.getCommercePriceListId(),
				ActionKeys.VIEW);
		}

		return commercePriceModifier;
	}

	@Override
	public CommercePriceModifier fetchCommercePriceModifier(
			long commercePriceModifierId)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierLocalService.fetchCommercePriceModifier(
				commercePriceModifierId);

		if (commercePriceModifier != null) {
			_commercePriceListModelResourcePermission.check(
				getPermissionChecker(),
				commercePriceModifier.getCommercePriceListId(),
				ActionKeys.VIEW);
		}

		return commercePriceModifier;
	}

	@Override
	public CommercePriceModifier getCommercePriceModifier(
			long commercePriceModifierId)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierLocalService.getCommercePriceModifier(
				commercePriceModifierId);

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(),
			commercePriceModifier.getCommercePriceListId(), ActionKeys.VIEW);

		return commercePriceModifier;
	}

	@Override
	public List<CommercePriceModifier> getCommercePriceModifiers(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceModifier> orderByComparator)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceModifierLocalService.getCommercePriceModifiers(
			commercePriceListId, start, end, orderByComparator);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public List<CommercePriceModifier> getCommercePriceModifiers(
			long companyId, String target)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public int getCommercePriceModifiersCount() throws PortalException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getCommercePriceModifiersCount(long commercePriceListId)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.VIEW);

		return commercePriceModifierLocalService.getCommercePriceModifiersCount(
			commercePriceListId);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public BaseModelSearchResult<CommercePriceModifier>
			searchCommercePriceModifiers(
				long companyId, String keywords, int status, int start, int end,
				Sort sort)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public CommercePriceModifier updateCommercePriceModifier(
			long commercePriceModifierId, long groupId, String title,
			String target, long commercePriceListId, String modifierType,
			BigDecimal modifierAmount, double priority, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		_commercePriceListModelResourcePermission.check(
			getPermissionChecker(), commercePriceListId, ActionKeys.UPDATE);

		return commercePriceModifierLocalService.updateCommercePriceModifier(
			commercePriceModifierId, groupId, title, target,
			commercePriceListId, modifierType, modifierAmount, priority, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	private static volatile ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CommercePriceModifierServiceImpl.class,
				"_commercePriceListModelResourcePermission",
				CommercePriceList.class);

}
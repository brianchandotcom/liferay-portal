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

import com.liferay.commerce.pricing.constants.CommercePriceModifierActionKeys;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.base.CommercePriceModifierServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;

import java.math.BigDecimal;

import java.util.List;

/**
 * @author Riccardo Alberti
 * @see CommercePriceModifierServiceBaseImpl
 */
public class CommercePriceModifierServiceImpl
	extends CommercePriceModifierServiceBaseImpl {

	@Override
	public CommercePriceModifier addCommercePriceModifier(
			long userId, long groupId, String title, String target,
			long commercePriceListId, String modifierType,
			BigDecimal modifierAmount, double priority, boolean active,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceModifierActionKeys.MANAGE_COMMERCE_PRICE_MODIFIERS);

		return commercePriceModifierLocalService.addCommercePriceModifier(
			groupId, title, target, commercePriceListId, modifierType,
			modifierAmount, priority, active, displayDateMonth, displayDateDay,
			displayDateYear, displayDateHour, displayDateMinute,
			expirationDateMonth, expirationDateDay, expirationDateYear,
			expirationDateHour, expirationDateMinute, neverExpire,
			serviceContext);
	}

	@Override
	public CommercePriceModifier deleteCommercePriceModifier(
			long commercePriceModifierId)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceModifierActionKeys.MANAGE_COMMERCE_PRICE_MODIFIERS);

		return commercePriceModifierLocalService.deleteCommercePriceModifier(
			commercePriceModifierId);
	}

	@Override
	public CommercePriceModifier fetchByExternalReferenceCode(
			long companyId, String externalReferenceCode)
		throws PortalException {

		CommercePriceModifier commercePriceModifier =
			commercePriceModifierLocalService.fetchByExternalReferenceCode(
				companyId, externalReferenceCode);

		if (commercePriceModifier != null) {
			PortalPermissionUtil.check(
				getPermissionChecker(),
				CommercePriceModifierActionKeys.VIEW_COMMERCE_PRICE_MODIFIERS);
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
			PortalPermissionUtil.check(
				getPermissionChecker(),
				CommercePriceModifierActionKeys.VIEW_COMMERCE_PRICE_MODIFIERS);
		}

		return commercePriceModifier;
	}

	@Override
	public CommercePriceModifier getCommercePriceModifier(
			long commercePriceModifierId)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceModifierActionKeys.VIEW_COMMERCE_PRICE_MODIFIERS);

		return commercePriceModifierLocalService.getCommercePriceModifier(
			commercePriceModifierId);
	}

	@Override
	public List<CommercePriceModifier> getCommercePriceModifiers(
			long companyId, String target)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceModifierActionKeys.VIEW_COMMERCE_PRICE_MODIFIERS);

		return commercePriceModifierLocalService.getCommercePriceModifiers(
			companyId, target);
	}

	@Override
	public int getCommercePriceModifiersCount() throws PortalException {
		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceModifierActionKeys.VIEW_COMMERCE_PRICE_MODIFIERS);

		return commercePriceModifierLocalService.
			getCommercePriceModifiersCount();
	}

	@Override
	public BaseModelSearchResult<CommercePriceModifier>
			searchCommercePriceModifiers(
				long companyId, String keywords, int status, int start, int end,
				Sort sort)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceModifierActionKeys.VIEW_COMMERCE_PRICE_MODIFIERS);

		return null;
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

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceModifierActionKeys.MANAGE_COMMERCE_PRICE_MODIFIERS);

		return commercePriceModifierLocalService.updateCommercePriceModifier(
			commercePriceModifierId, groupId, title, target,
			commercePriceListId, modifierType, modifierAmount, priority, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, serviceContext);
	}

	@Override
	public CommercePriceModifier upsertCommercePriceModifier(
			long userId, long commercePriceModifierId, long groupId,
			String title, String target, long commercePriceListId,
			String modifierType, BigDecimal modifierAmount, double priority,
			boolean active, int displayDateMonth, int displayDateDay,
			int displayDateYear, int displayDateHour, int displayDateMinute,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, String externalReferenceCode,
			boolean neverExpire, ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(),
			CommercePriceModifierActionKeys.MANAGE_COMMERCE_PRICE_MODIFIERS);

		return commercePriceModifierLocalService.upsertCommercePriceModifier(
			userId, commercePriceModifierId, groupId, title, target,
			commercePriceListId, modifierType, modifierAmount, priority, active,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			externalReferenceCode, neverExpire, serviceContext);
	}

}
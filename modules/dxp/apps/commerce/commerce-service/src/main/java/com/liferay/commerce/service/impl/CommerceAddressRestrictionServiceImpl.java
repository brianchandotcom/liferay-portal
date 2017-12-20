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

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.base.CommerceAddressRestrictionServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAddressRestrictionServiceImpl
	extends CommerceAddressRestrictionServiceBaseImpl {

	@Override
	public CommerceAddressRestriction addCommerceAddressRestriction(
			String className, long classPK, long commerceCountryId,
			ServiceContext serviceContext)
		throws PortalException {

		checkModel(className, classPK);

		return commerceAddressRestrictionLocalService.
			addCommerceAddressRestriction(
				className, classPK, commerceCountryId, serviceContext);
	}

	@Override
	public void deleteCommerceAddressRestriction(
			long commerceAddressRestrictionId)
		throws PortalException {

		CommerceAddressRestriction commerceAddressRestriction =
			commerceAddressRestrictionLocalService.
				getCommerceAddressRestriction(commerceAddressRestrictionId);

		checkModel(
			commerceAddressRestriction.getClassName(),
			commerceAddressRestriction.getClassPK());

		commerceAddressRestrictionLocalService.deleteCommerceAddressRestriction(
			commerceAddressRestrictionId);
	}

	@Override
	public CommerceAddressRestriction fetchCommerceAddressRestriction(
		String className, long classPK, long commerceCountryId) {

		return commerceAddressRestrictionLocalService.
			fetchCommerceAddressRestriction(
				className, classPK, commerceCountryId);
	}

	@Override
	public List<CommerceAddressRestriction> getCommerceAddressRestrictions(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {

		return commerceAddressRestrictionLocalService.
			getCommerceAddressRestrictions(
				className, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAddressRestrictionsCount(
		String className, long classPK) {

		return commerceAddressRestrictionLocalService.
			getCommerceAddressRestrictionsCount(className, classPK);
	}

	@Override
	public boolean isCommerceAddressRestricted(
		String className, long classPK, long commerceCountryId) {

		return
			commerceAddressRestrictionLocalService.isCommerceAddressRestricted(
				className, classPK, commerceCountryId);
	}

	@Override
	public boolean isCommercePaymentMethodRestricted(
		long commercePaymentMethodId, long commerceCountryId) {

		return commerceAddressRestrictionLocalService.
			isCommercePaymentMethodRestricted(
				commercePaymentMethodId, commerceCountryId);
	}

	@Override
	public boolean isCommerceShippingMethodRestricted(
		long commerceShippingMethodId, long commerceCountryId) {

		return commerceAddressRestrictionLocalService.
			isCommerceShippingMethodRestricted(
				commerceShippingMethodId, commerceCountryId);
	}

	protected void checkModel(String className, long classPK)
		throws PortalException {

		if (className.equals(CommercePaymentMethod.class.getName())) {
			CommercePaymentMethod commercePaymentMethod =
				commercePaymentMethodLocalService.getCommercePaymentMethod(
					classPK);

			CommercePermission.check(
				getPermissionChecker(), commercePaymentMethod.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);
		}
		else if (className.equals(CommerceShippingMethod.class.getName())) {
			CommerceShippingMethod commerceShippingMethod =
				commerceShippingMethodLocalService.getCommerceShippingMethod(
					classPK);

			CommercePermission.check(
				getPermissionChecker(), commerceShippingMethod.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_PAYMENT_METHODS);
		}
	}

}
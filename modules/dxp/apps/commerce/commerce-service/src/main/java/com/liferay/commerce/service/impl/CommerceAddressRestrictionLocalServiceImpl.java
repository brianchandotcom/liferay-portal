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

import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.base.CommerceAddressRestrictionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceAddressRestrictionLocalServiceImpl
	extends CommerceAddressRestrictionLocalServiceBaseImpl {

	@Override
	public CommerceAddressRestriction addCommerceAddressRestriction(
			String className, long classPK, long commerceCountryId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long commerceAddressRestrictionId = counterLocalService.increment();

		CommerceAddressRestriction commerceAddressRestriction =
			commerceAddressRestrictionPersistence.create(
				commerceAddressRestrictionId);

		commerceAddressRestriction.setGroupId(groupId);
		commerceAddressRestriction.setCompanyId(user.getCompanyId());
		commerceAddressRestriction.setUserId(user.getUserId());
		commerceAddressRestriction.setUserName(user.getFullName());
		commerceAddressRestriction.setClassName(className);
		commerceAddressRestriction.setClassPK(classPK);
		commerceAddressRestriction.setCommerceCountryId(commerceCountryId);

		commerceAddressRestrictionPersistence.update(
			commerceAddressRestriction);

		return commerceAddressRestriction;
	}

	@Override
	public void deleteCommerceAddressRestrictions(long commerceCountryId) {
		commerceAddressRestrictionPersistence.removeByCommerceCountryId(
			commerceCountryId);
	}

	@Override
	public void deleteCommerceAddressRestrictions(
		String className, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(className);

		commerceAddressRestrictionPersistence.removeByC_C(classNameId, classPK);
	}

	@Override
	public CommerceAddressRestriction fetchCommerceAddressRestriction(
		String className, long classPK, long commerceCountryId) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceAddressRestrictionPersistence.fetchByC_C_C(
			classNameId, classPK, commerceCountryId);
	}

	@Override
	public List<CommerceAddressRestriction> getCommerceAddressRestrictions(
		String className, long classPK, int start, int end,
		OrderByComparator<CommerceAddressRestriction> orderByComparator) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceAddressRestrictionPersistence.findByC_C(
			classNameId, classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAddressRestrictionsCount(
		String className, long classPK) {

		long classNameId = classNameLocalService.getClassNameId(className);

		return commerceAddressRestrictionPersistence.countByC_C(
			classNameId, classPK);
	}

	@Override
	public boolean isCommerceAddressRestricted(
		String className, long classPK, long commerceCountryId) {

		CommerceAddressRestriction commerceAddressRestriction =
			fetchCommerceAddressRestriction(
				className, classPK, commerceCountryId);

		if (commerceAddressRestriction != null) {
			return true;
		}

		return false;
	}

	@Override
	public boolean isCommercePaymentMethodRestricted(
		long commercePaymentMethodId, long commerceCountryId) {

		return isCommerceAddressRestricted(
			CommercePaymentMethod.class.getName(), commercePaymentMethodId,
			commerceCountryId);
	}

	@Override
	public boolean isCommerceShippingMethodRestricted(
		long commerceShippingMethodId, long commerceCountryId) {

		return isCommerceAddressRestricted(
			CommerceShippingMethod.class.getName(), commerceShippingMethodId,
			commerceCountryId);
	}

}
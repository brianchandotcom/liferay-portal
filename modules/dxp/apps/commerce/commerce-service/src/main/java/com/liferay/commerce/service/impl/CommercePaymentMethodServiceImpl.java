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
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.service.base.CommercePaymentMethodServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 */
public class CommercePaymentMethodServiceImpl
	extends CommercePaymentMethodServiceBaseImpl {

	@Override
	public CommercePaymentMethod addCommercePaymentMethod(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String engineKey, Map<String, String> engineParameterMap,
			double priority, boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PAYMENT_METHODS);

		return commercePaymentMethodLocalService.addCommercePaymentMethod(
			nameMap, descriptionMap, engineKey, engineParameterMap, priority,
			active, serviceContext);
	}

	@Override
	public void deleteCommercePaymentMethod(long commercePaymentMethodId)
		throws PortalException {

		CommercePaymentMethod commercePaymentMethod =
			commercePaymentMethodLocalService.getCommercePaymentMethod(
				commercePaymentMethodId);

		CommercePermission.check(
			getPermissionChecker(), commercePaymentMethod.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PAYMENT_METHODS);

		commercePaymentMethodLocalService.deleteCommercePaymentMethod(
			commercePaymentMethod);
	}

	@Override
	public List<CommercePaymentMethod> getCommercePaymentMethods(
		long groupId, boolean active) {

		return commercePaymentMethodLocalService.getCommercePaymentMethods(
			groupId, active);
	}

	@Override
	public CommercePaymentMethod updateCommercePaymentMethod(
			long commercePaymentMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap,
			Map<String, String> engineParameterMap, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommercePaymentMethod commercePaymentMethod =
			commercePaymentMethodLocalService.getCommercePaymentMethod(
				commercePaymentMethodId);

		CommercePermission.check(
			getPermissionChecker(), commercePaymentMethod.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PAYMENT_METHODS);

		return commercePaymentMethodLocalService.updateCommercePaymentMethod(
			commercePaymentMethod.getCommercePaymentMethodId(), nameMap,
			descriptionMap, engineParameterMap, priority, active,
			serviceContext);
	}

}
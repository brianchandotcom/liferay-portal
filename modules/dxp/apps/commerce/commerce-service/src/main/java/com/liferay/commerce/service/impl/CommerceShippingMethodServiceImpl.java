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
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.base.CommerceShippingMethodServiceBaseImpl;
import com.liferay.commerce.service.permission.CommercePermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.File;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingMethodServiceImpl
	extends CommerceShippingMethodServiceBaseImpl {

	@Override
	public CommerceShippingMethod addCommerceShippingMethod(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			File imageFile, String engineKey, double priority, boolean active,
			ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		return commerceShippingMethodLocalService.addCommerceShippingMethod(
			nameMap, descriptionMap, imageFile, engineKey, priority, active,
			serviceContext);
	}

	@Override
	public CommerceShippingMethod createCommerceShippingMethod(
			long commerceShippingMethodId)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			commerceShippingMethodLocalService.fetchCommerceShippingMethod(
				commerceShippingMethodId);

		if (commerceShippingMethod != null) {
			CommercePermission.check(
				getPermissionChecker(), commerceShippingMethod.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);
		}

		return commerceShippingMethodLocalService.createCommerceShippingMethod(
			commerceShippingMethodId);
	}

	@Override
	public void deleteCommerceShippingMethod(long commerceShippingMethodId)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			commerceShippingMethodLocalService.getCommerceShippingMethod(
				commerceShippingMethodId);

		CommercePermission.check(
			getPermissionChecker(), commerceShippingMethod.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		commerceShippingMethodLocalService.deleteCommerceShippingMethod(
			commerceShippingMethod);
	}

	@Override
	public CommerceShippingMethod fetchCommerceShippingMethod(
		long groupId, String engineKey) {

		return commerceShippingMethodLocalService.fetchCommerceShippingMethod(
			groupId, engineKey);
	}

	@Override
	public CommerceShippingMethod getCommerceShippingMethod(
			long commerceShippingMethodId)
		throws PortalException {

		return commerceShippingMethodLocalService.getCommerceShippingMethod(
			commerceShippingMethodId);
	}

	@Override
	public List<CommerceShippingMethod> getCommerceShippingMethods(long groupId)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), groupId,
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		return commerceShippingMethodLocalService.getCommerceShippingMethods(
			groupId);
	}

	@Override
	public List<CommerceShippingMethod> getCommerceShippingMethods(
		long groupId, boolean active) {

		return commerceShippingMethodLocalService.getCommerceShippingMethods(
			groupId, active);
	}

	@Override
	public int getCommerceShippingMethodsCount(long groupId, boolean active) {
		return
			commerceShippingMethodLocalService.getCommerceShippingMethodsCount(
				groupId, active);
	}

	@Override
	public CommerceShippingMethod setActive(
			long commerceShippingMethodId, boolean active)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			commerceShippingMethodLocalService.fetchCommerceShippingMethod(
				commerceShippingMethodId);

		if (commerceShippingMethod != null) {
			CommercePermission.check(
				getPermissionChecker(), commerceShippingMethod.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);
		}

		return commerceShippingMethodLocalService.setActive(
			commerceShippingMethodId, active);
	}

	@Override
	public CommerceShippingMethod updateCommerceShippingMethod(
			long commerceShippingMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, File imageFile, double priority,
			boolean active)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			commerceShippingMethodLocalService.getCommerceShippingMethod(
				commerceShippingMethodId);

		CommercePermission.check(
			getPermissionChecker(), commerceShippingMethod.getGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_SHIPPING_METHODS);

		return commerceShippingMethodLocalService.updateCommerceShippingMethod(
			commerceShippingMethod.getCommerceShippingMethodId(), nameMap,
			descriptionMap, imageFile, priority, active);
	}

}
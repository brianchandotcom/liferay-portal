/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service.impl;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.service.base.CommercePaymentMethodServiceBaseImpl;
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
public class CommercePaymentMethodServiceImpl
	extends CommercePaymentMethodServiceBaseImpl {

	@Override
	public CommercePaymentMethod addCommercePaymentMethod(
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			File imageFile, String engineKey,
			Map<String, String> engineParameterMap, double priority,
			boolean active, ServiceContext serviceContext)
		throws PortalException {

		CommercePermission.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			CommerceActionKeys.MANAGE_COMMERCE_PAYMENT_METHODS);

		return commercePaymentMethodLocalService.addCommercePaymentMethod(
			nameMap, descriptionMap, imageFile, engineKey, engineParameterMap,
			priority, active, serviceContext);
	}

	@Override
	public CommercePaymentMethod createCommercePaymentMethod(
			long commercePaymentMethodId)
		throws PortalException {

		CommercePaymentMethod commercePaymentMethod =
			commercePaymentMethodLocalService.fetchCommercePaymentMethod(
				commercePaymentMethodId);

		if (commercePaymentMethod != null) {
			CommercePermission.check(
				getPermissionChecker(), commercePaymentMethod.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_PAYMENT_METHODS);
		}

		return commercePaymentMethodLocalService.createCommercePaymentMethod(
			commercePaymentMethodId);
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
	public CommercePaymentMethod getCommercePaymentMethod(
			long commercePaymentMethodId)
		throws PortalException {

		return commercePaymentMethodLocalService.getCommercePaymentMethod(
			commercePaymentMethodId);
	}

	@Override
	public List<CommercePaymentMethod> getCommercePaymentMethods(long groupId) {
		return commercePaymentMethodLocalService.getCommercePaymentMethods(
			groupId);
	}

	@Override
	public List<CommercePaymentMethod> getCommercePaymentMethods(
		long groupId, boolean active) {

		return commercePaymentMethodLocalService.getCommercePaymentMethods(
			groupId, active);
	}

	@Override
	public List<CommercePaymentMethod> getCommercePaymentMethods(
		long groupId, long commerceCountryId, boolean active) {

		return commercePaymentMethodLocalService.getCommercePaymentMethods(
			groupId, commerceCountryId, active);
	}

	@Override
	public int getCommercePaymentMethodsCount(long groupId, boolean active) {
		return commercePaymentMethodLocalService.getCommercePaymentMethodsCount(
			groupId, active);
	}

	@Override
	public CommercePaymentMethod setActive(
			long commercePaymentMethodId, boolean active)
		throws PortalException {

		CommercePaymentMethod commercePaymentMethod =
			commercePaymentMethodLocalService.fetchCommercePaymentMethod(
				commercePaymentMethodId);

		if (commercePaymentMethod != null) {
			CommercePermission.check(
				getPermissionChecker(), commercePaymentMethod.getGroupId(),
				CommerceActionKeys.MANAGE_COMMERCE_PAYMENT_METHODS);
		}

		return commercePaymentMethodLocalService.setActive(
			commercePaymentMethodId, active);
	}

	@Override
	public CommercePaymentMethod updateCommercePaymentMethod(
			long commercePaymentMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, File imageFile,
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
			descriptionMap, imageFile, engineParameterMap, priority, active,
			serviceContext);
	}

}
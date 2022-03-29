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

package com.liferay.commerce.payment.internal.util;

import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.payment.util.CommercePaymentHttpHelper;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.petra.encryptor.Encryptor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.security.Key;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	enabled = false, immediate = true, service = CommercePaymentHttpHelper.class
)
public class CommercePaymentHttpHelperImpl
	implements CommercePaymentHttpHelper {

	@Override
	public CommerceOrder getCommerceOrder(HttpServletRequest httpServletRequest)
		throws Exception {

		CommerceOrder commerceOrder = null;

		long groupId = ParamUtil.getLong(httpServletRequest, "groupId");
		String uuid = ParamUtil.getString(httpServletRequest, "uuid");

		String guestToken = ParamUtil.getString(
			httpServletRequest, "guestToken");

		if (Validator.isNotNull(guestToken)) {
			guestToken = guestToken.replaceAll(
				StringPool.SPACE, StringPool.PLUS);

			commerceOrder =
				_commerceOrderLocalService.getCommerceOrderByUuidAndGroupId(
					uuid, groupId);

			Company company = _portal.getCompany(httpServletRequest);

			User defaultUser = company.getDefaultUser();

			String orderGuestToken = _getGuestToken(
				company, commerceOrder.getCommerceOrderId());

			if (!guestToken.equals(orderGuestToken)) {
				throw new PrincipalException.MustHavePermission(
					defaultUser.getUserId(), CommerceOrder.class.getName(),
					commerceOrder.getCommerceOrderId(), ActionKeys.VIEW);
			}

			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(defaultUser));
		}
		else {
			PermissionThreadLocal.setPermissionChecker(
				PermissionCheckerFactoryUtil.create(
					_portal.getUser(httpServletRequest)));

			commerceOrder =
				_commerceOrderService.getCommerceOrderByUuidAndGroupId(
					uuid, groupId);
		}

		return commerceOrder;
	}

	private String _getGuestToken(Company company, long commerceOrderId)
		throws Exception {

		Key key = company.getKeyObj();

		return Encryptor.encrypt(key, String.valueOf(commerceOrderId));
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private Portal _portal;

}
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

package com.liferay.commerce.discount.internal.discount;

import com.liferay.commerce.discount.CommerceDiscountCouponCodeHelper;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = CommerceDiscountCouponCodeHelper.class)
public class CommerceDiscountCouponCodeHelperImpl
	implements CommerceDiscountCouponCodeHelper {

	@Override
	public void addCommerceDiscountCouponCode(
			HttpServletRequest httpServletRequest, String couponCode)
		throws PortalException {

		if (Validator.isNotNull(
				getCommerceDiscountCouponCode(httpServletRequest))) {

			return;
		}

		HttpServletRequest originalServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalServletRequest.getSession();

		httpSession.setAttribute(
			_getSessionAttributeKey(httpServletRequest), couponCode);
	}

	@Override
	public String getCommerceDiscountCouponCode(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		HttpServletRequest originalServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalServletRequest.getSession();

		return (String)httpSession.getAttribute(
			_getSessionAttributeKey(httpServletRequest));
	}

	@Override
	public void removeCommerceDiscountCouponCode(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		HttpServletRequest originalServletRequest =
			_portal.getOriginalServletRequest(httpServletRequest);

		HttpSession httpSession = originalServletRequest.getSession();

		httpSession.setAttribute(
			_getSessionAttributeKey(httpServletRequest), null);
	}

	private String _getSessionAttributeKey(
			HttpServletRequest httpServletRequest)
		throws PortalException {

		long groupId = _portal.getScopeGroupId(httpServletRequest);

		Organization organization =
			_commerceOrganizationHelper.getCurrentOrganization(
				httpServletRequest);

		if (organization != null) {
			groupId = organization.getGroupId();
		}

		return _SESSION_COMMERCE_DISCOUNT_COUPON_CODE + groupId;
	}

	private static final String _SESSION_COMMERCE_DISCOUNT_COUPON_CODE =
		"LIFERAY_SHARED_COMMERCE_DISCOUNT_COUPON_CODE_";

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private Portal _portal;

}
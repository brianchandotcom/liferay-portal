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

package com.liferay.commerce.discount.content.web.internal.portlet.action;

import com.liferay.commerce.discount.CommerceDiscountCouponCodeHelper;
import com.liferay.commerce.discount.constants.CommerceDiscountPortletKeys;
import com.liferay.commerce.discount.exception.CommerceDiscountCouponCodeException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceDiscountPortletKeys.COMMERCE_DISCOUNT_CONTENT_WEB,
		"mvc.command.name=applyCommerceDiscountCouponCode"
	},
	service = MVCActionCommand.class
)
public class ApplyCommerceDiscountCouponCodeMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD)) {
			String couponCode = ParamUtil.getString(
				actionRequest, "couponCode");

			boolean valid = isValidCouponCode(actionRequest, couponCode);

			if (!valid) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(
					actionRequest, CommerceDiscountCouponCodeException.class);

				return;
			}

			_commerceDiscountCouponCodeHelper.addCommerceDiscountCouponCode(
				httpServletRequest, couponCode);
		}
		else if (cmd.equals(Constants.REMOVE)) {
			_commerceDiscountCouponCodeHelper.removeCommerceDiscountCouponCode(
				httpServletRequest);
		}

		hideDefaultSuccessMessage(actionRequest);
	}

	protected boolean isValidCouponCode(
			ActionRequest actionRequest, String couponCode)
		throws PortalException {

		if (Validator.isNull(couponCode)) {
			return false;
		}

		List<CommerceDiscount> commerceDiscounts =
			_commerceDiscountService.getCommerceDiscounts(
				_portal.getScopeGroupId(actionRequest), couponCode);

		for (CommerceDiscount commerceDiscount : commerceDiscounts) {
			if (couponCode.equals(commerceDiscount.getCouponCode())) {
				return true;
			}
		}

		return false;
	}

	@Reference
	private CommerceDiscountCouponCodeHelper _commerceDiscountCouponCodeHelper;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private Portal _portal;

}
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

package com.liferay.commerce.pricing.web.internal.portlet.action;

import com.liferay.commerce.discount.constants.CommerceDiscountConstants;
import com.liferay.commerce.discount.exception.CommerceDiscountCouponCodeException;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Objects;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePricingPortletKeys.COMMERCE_DISCOUNT,
		"mvc.command.name=/commerce_discount/edit_commerce_discount"
	},
	service = MVCActionCommand.class
)
public class EditCommerceDiscountMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateCommerceDiscount(actionRequest);
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof CommerceDiscountCouponCodeException ||
				throwable instanceof NoSuchDiscountException) {

				SessionErrors.add(
					actionRequest, throwable.getClass(), throwable);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
		}
	}

	private BigDecimal[] _getDiscountLevels(String level, BigDecimal amount) {
		BigDecimal[] discountLevels = {
			BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO
		};

		if (Objects.equals(level, CommerceDiscountConstants.LEVEL_L1)) {
			discountLevels[0] = amount;
		}

		if (Objects.equals(level, CommerceDiscountConstants.LEVEL_L2)) {
			discountLevels[1] = amount;
		}

		if (Objects.equals(level, CommerceDiscountConstants.LEVEL_L3)) {
			discountLevels[2] = amount;
		}

		if (Objects.equals(level, CommerceDiscountConstants.LEVEL_L4)) {
			discountLevels[3] = amount;
		}

		return discountLevels;
	}

	private String _getLimitationType(
		int limitationTimes, int limitationTimesPerAccount) {

		if ((limitationTimes > 0) && (limitationTimesPerAccount > 0)) {
			return CommerceDiscountConstants.
				LIMITATION_TYPE_LIMITED_FOR_ACCOUNTS_AND_TOTAL;
		}

		if (limitationTimes > 0) {
			return CommerceDiscountConstants.LIMITATION_TYPE_LIMITED;
		}

		if (limitationTimesPerAccount > 0) {
			return CommerceDiscountConstants.
				LIMITATION_TYPE_LIMITED_FOR_ACCOUNTS;
		}

		return CommerceDiscountConstants.LIMITATION_TYPE_UNLIMITED;
	}

	private CommerceDiscount _updateCommerceDiscount(
			ActionRequest actionRequest)
		throws Exception {

		long commerceDiscountId = ParamUtil.getLong(
			actionRequest, "commerceDiscountId");

		String title = ParamUtil.getString(actionRequest, "title");

		String target = ParamUtil.getString(actionRequest, "target");

		boolean useCouponCode = ParamUtil.getBoolean(
			actionRequest, "useCouponCode");

		String couponCode = ParamUtil.getString(actionRequest, "couponCode");

		if (!useCouponCode) {
			couponCode = null;
		}

		boolean usePercentage = ParamUtil.getBoolean(
			actionRequest, "usePercentage");

		BigDecimal maximumDiscountAmount = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "maximumDiscountAmount", BigDecimal.ZERO);

		String level = ParamUtil.getString(actionRequest, "level");

		BigDecimal amount = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "amount", BigDecimal.ZERO);

		BigDecimal[] discountLevels = _getDiscountLevels(level, amount);

		int limitationTimes = ParamUtil.getInteger(
			actionRequest, "limitationTimes");
		int limitationTimesPerAccount = ParamUtil.getInteger(
			actionRequest, "limitationTimesPerAccount");
		boolean rulesConjunction = ParamUtil.getBoolean(
			actionRequest, "rulesConjunction");

		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");

		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");

		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");

		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");

		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");

		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");

		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");

		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");

		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");

		String externalReferenceCode = ParamUtil.getString(
			actionRequest, "externalReferenceCode");

		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceDiscount.class.getName(), actionRequest);

		return _commerceDiscountService.addOrUpdateCommerceDiscount(
			externalReferenceCode, commerceDiscountId, title, target,
			useCouponCode, couponCode, usePercentage, maximumDiscountAmount,
			level, discountLevels[0], discountLevels[1], discountLevels[2],
			discountLevels[3],
			_getLimitationType(limitationTimes, limitationTimesPerAccount),
			limitationTimes, limitationTimesPerAccount, rulesConjunction,
			active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Reference
	private CommerceDiscountService _commerceDiscountService;

}
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

package com.liferay.commerce.discount.web.internal.portlet.action;

import com.liferay.commerce.discount.constants.CommerceDiscountPortletKeys;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountUserSegmentRel;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.discount.service.CommerceDiscountUserSegmentRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceDiscountPortletKeys.COMMERCE_DISCOUNT,
		"mvc.command.name=editCommerceDiscount"
	},
	service = MVCActionCommand.class
)
public class EditCommerceDiscountMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCommerceDiscounts(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceDiscountIds = null;

		long commerceDiscountId = ParamUtil.getLong(
			actionRequest, "commerceDiscountId");

		if (commerceDiscountId > 0) {
			deleteCommerceDiscountIds = new long[] {commerceDiscountId};
		}
		else {
			deleteCommerceDiscountIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceDiscountIds"),
				0L);
		}

		for (long deleteCommerceDiscountId : deleteCommerceDiscountIds) {
			_commerceDiscountService.deleteCommerceDiscount(
				deleteCommerceDiscountId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CommerceDiscount commerceDiscount = updateCommerceDiscount(
					actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, commerceDiscount);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceDiscounts(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchDiscountException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected String getSaveAndContinueRedirect(
		ActionRequest actionRequest, CommerceDiscount commerceDiscount) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CommerceDiscountPortletKeys.COMMERCE_DISCOUNT,
			PortletRequest.RENDER_PHASE);

		if (commerceDiscount != null) {
			portletURL.setParameter(
				"mvcRenderCommandName", "editCommerceDiscount");
			portletURL.setParameter(
				"commerceDiscountId",
				String.valueOf(commerceDiscount.getCommerceDiscountId()));

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			portletURL.setParameter("redirect", redirect);
		}

		return portletURL.toString();
	}

	protected CommerceDiscount updateCommerceDiscount(
			ActionRequest actionRequest)
		throws Exception {

		long commerceDiscountId = ParamUtil.getLong(
			actionRequest, "commerceDiscountId");

		String title = ParamUtil.getString(actionRequest, "title");
		String target = ParamUtil.getString(actionRequest, "target");
		boolean useCouponCode = ParamUtil.getBoolean(
			actionRequest, "useCouponCode");
		String couponCode = ParamUtil.getString(actionRequest, "couponCode");
		boolean usePercentage = ParamUtil.getBoolean(
			actionRequest, "usePercentage");
		double maximumDiscountAmount = ParamUtil.getDouble(
			actionRequest, "maximumDiscountAmount");
		double level1 = ParamUtil.getDouble(actionRequest, "level1");
		double level2 = ParamUtil.getDouble(actionRequest, "level2");
		double level3 = ParamUtil.getDouble(actionRequest, "level3");
		String limitationType = ParamUtil.getString(
			actionRequest, "limitationType");
		int limitationTimes = ParamUtil.getInteger(
			actionRequest, "limitationTimes");
		boolean cumulative = ParamUtil.getBoolean(actionRequest, "cumulative");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceDiscount.class.getName(), actionRequest);

		CommerceDiscount commerceDiscount = null;

		if (commerceDiscountId <= 0) {
			commerceDiscount = _commerceDiscountService.addCommerceDiscount(
				title, target, useCouponCode, couponCode, usePercentage,
				BigDecimal.valueOf(maximumDiscountAmount),
				BigDecimal.valueOf(level1), BigDecimal.valueOf(level2),
				BigDecimal.valueOf(level3), limitationType, limitationTimes,
				cumulative, active, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				serviceContext);
		}
		else {
			commerceDiscount = _commerceDiscountService.updateCommerceDiscount(
				commerceDiscountId, title, target, useCouponCode, couponCode,
				usePercentage, BigDecimal.valueOf(maximumDiscountAmount),
				BigDecimal.valueOf(level1), BigDecimal.valueOf(level2),
				BigDecimal.valueOf(level3), limitationType, limitationTimes,
				cumulative, active, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				serviceContext);
		}

		if (commerceDiscount != null) {
			updateCommerceDiscountUserSegmentRels(
				actionRequest, commerceDiscount);
		}

		return commerceDiscount;
	}

	protected void updateCommerceDiscountUserSegmentRels(
			ActionRequest actionRequest, CommerceDiscount commerceDiscount)
		throws PortalException {

		long[] addCommerceUserSegmentEntryIds = ParamUtil.getLongValues(
			actionRequest, "addCommerceUserSegmentEntryIds");
		long[] deleteCommerceDiscountUserSegmentRelIds =
			ParamUtil.getLongValues(
				actionRequest, "deleteCommerceDiscountUserSegmentRelIds");

		if (deleteCommerceDiscountUserSegmentRelIds.length > 0) {
			for (long deleteCommerceDiscountUserSegmentRelId :
					deleteCommerceDiscountUserSegmentRelIds) {

				_commerceDiscountUserSegmentRelService.
					deleteCommerceDiscountUserSegmentRel(
						deleteCommerceDiscountUserSegmentRelId);
			}
		}

		if (addCommerceUserSegmentEntryIds.length > 0) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceDiscountUserSegmentRel.class.getName(), actionRequest);

			for (long addCommerceUserSegmentEntryId :
					addCommerceUserSegmentEntryIds) {

				_commerceDiscountUserSegmentRelService.
					addCommerceDiscountUserSegmentRel(
						commerceDiscount.getCommerceDiscountId(),
						addCommerceUserSegmentEntryId, serviceContext);
			}
		}
	}

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private CommerceDiscountUserSegmentRelService
		_commerceDiscountUserSegmentRelService;

	@Reference
	private Portal _portal;

}
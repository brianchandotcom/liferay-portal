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

package com.liferay.shopping.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.shopping.constants.ShoppingPortletKeys;
import com.liferay.shopping.exception.CouponCodeException;
import com.liferay.shopping.exception.CouponDateException;
import com.liferay.shopping.exception.CouponDescriptionException;
import com.liferay.shopping.exception.CouponDiscountException;
import com.liferay.shopping.exception.CouponEndDateException;
import com.liferay.shopping.exception.CouponLimitCategoriesException;
import com.liferay.shopping.exception.CouponLimitSKUsException;
import com.liferay.shopping.exception.CouponMinimumOrderException;
import com.liferay.shopping.exception.CouponNameException;
import com.liferay.shopping.exception.CouponStartDateException;
import com.liferay.shopping.exception.DuplicateCouponCodeException;
import com.liferay.shopping.exception.NoSuchCouponException;
import com.liferay.shopping.model.ShoppingCoupon;
import com.liferay.shopping.service.ShoppingCouponService;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Huang Jie
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING,
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING_ADMIN,
		"mvc.command.name=/shopping/edit_coupon"
	},
	service = MVCActionCommand.class
)
public class EditCouponMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCoupons(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] deleteCouponIds = ParamUtil.getLongValues(
			actionRequest, "rowIds");

		for (long deleteCouponId : deleteCouponIds) {
			_shoppingCouponService.deleteCoupon(
				themeDisplay.getScopeGroupId(), deleteCouponId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCoupon(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCoupons(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchCouponException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CouponCodeException ||
					 e instanceof CouponDateException ||
					 e instanceof CouponDescriptionException ||
					 e instanceof CouponDiscountException ||
					 e instanceof CouponEndDateException ||
					 e instanceof CouponLimitCategoriesException ||
					 e instanceof CouponLimitSKUsException ||
					 e instanceof CouponMinimumOrderException ||
					 e instanceof CouponNameException ||
					 e instanceof CouponStartDateException ||
					 e instanceof DuplicateCouponCodeException) {

				if (e instanceof CouponLimitCategoriesException) {
					CouponLimitCategoriesException clce =
						(CouponLimitCategoriesException)e;

					SessionErrors.add(
						actionRequest, e.getClass(), clce.getCategoryIds());
				}
				else if (e instanceof CouponLimitSKUsException) {
					CouponLimitSKUsException clskue =
						(CouponLimitSKUsException)e;

					SessionErrors.add(
						actionRequest, e.getClass(), clskue.getSkus());
				}
				else {
					SessionErrors.add(actionRequest, e.getClass());
				}
			}
			else {
				throw e;
			}
		}
	}

	@Reference(unbind = "-")
	protected void setShoppingCouponService(
		ShoppingCouponService shoppingCouponService) {

		_shoppingCouponService = shoppingCouponService;
	}

	protected void updateCoupon(ActionRequest actionRequest) throws Exception {
		long couponId = ParamUtil.getLong(actionRequest, "couponId");

		String code = ParamUtil.getString(actionRequest, "code");
		boolean autoCode = ParamUtil.getBoolean(actionRequest, "autoCode");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		int startDateMonth = ParamUtil.getInteger(
			actionRequest, "startDateMonth");
		int startDateDay = ParamUtil.getInteger(actionRequest, "startDateDay");
		int startDateYear = ParamUtil.getInteger(
			actionRequest, "startDateYear");
		int startDateHour = ParamUtil.getInteger(
			actionRequest, "startDateHour");
		int startDateMinute = ParamUtil.getInteger(
			actionRequest, "startDateMinute");
		int startDateAmPm = ParamUtil.getInteger(
			actionRequest, "startDateAmPm");

		if (startDateAmPm == Calendar.PM) {
			startDateHour += 12;
		}

		int endDateMonth = ParamUtil.getInteger(actionRequest, "endDateMonth");
		int endDateDay = ParamUtil.getInteger(actionRequest, "endDateDay");
		int endDateYear = ParamUtil.getInteger(actionRequest, "endDateYear");
		int endDateHour = ParamUtil.getInteger(actionRequest, "endDateHour");
		int endDateMinute = ParamUtil.getInteger(
			actionRequest, "endDateMinute");
		int endDateAmPm = ParamUtil.getInteger(actionRequest, "endDateAmPm");
		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		if (endDateAmPm == Calendar.PM) {
			endDateHour += 12;
		}

		boolean active = ParamUtil.getBoolean(actionRequest, "active");
		String limitCategories = ParamUtil.getString(
			actionRequest, "limitCategories");
		String limitSkus = ParamUtil.getString(actionRequest, "limitSkus");
		double minOrder = ParamUtil.getDouble(actionRequest, "minOrder", -1.0);
		double discount = ParamUtil.getDouble(actionRequest, "discount", -1.0);
		String discountType = ParamUtil.getString(
			actionRequest, "discountType");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			ShoppingCoupon.class.getName(), actionRequest);

		if (couponId <= 0) {

			// Add coupon

			_shoppingCouponService.addCoupon(
				code, autoCode, name, description, startDateMonth, startDateDay,
				startDateYear, startDateHour, startDateMinute, endDateMonth,
				endDateDay, endDateYear, endDateHour, endDateMinute,
				neverExpire, active, limitCategories, limitSkus, minOrder,
				discount, discountType, serviceContext);
		}
		else {

			// Update coupon

			_shoppingCouponService.updateCoupon(
				couponId, name, description, startDateMonth, startDateDay,
				startDateYear, startDateHour, startDateMinute, endDateMonth,
				endDateDay, endDateYear, endDateHour, endDateMinute,
				neverExpire, active, limitCategories, limitSkus, minOrder,
				discount, discountType, serviceContext);
		}
	}

	private ShoppingCouponService _shoppingCouponService;

}
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

import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.pricing.exception.NoSuchPriceModifierException;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.CommercePriceModifierService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePricingPortletKeys.COMMERCE_PRICE_LIST,
		"javax.portlet.name=" + CommercePricingPortletKeys.COMMERCE_PROMOTION,
		"mvc.command.name=/commerce_price_list/edit_commerce_price_modifier"
	},
	service = MVCActionCommand.class
)
public class EditCommercePriceModifierMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long commercePriceModifierId = ParamUtil.getLong(
			actionRequest, "commercePriceModifierId");

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateCommercePriceModifier(
					commercePriceModifierId, actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCommercePriceModifiers(
					commercePriceModifierId, actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchPriceListException ||
				exception instanceof NoSuchPriceModifierException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw exception;
			}
		}
	}

	private void _deleteCommercePriceModifiers(
			long commercePriceModifierId, ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommercePriceModifierIds = null;

		if (commercePriceModifierId > 0) {
			deleteCommercePriceModifierIds = new long[] {
				commercePriceModifierId
			};
		}
		else {
			deleteCommercePriceModifierIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommercePriceModifierIds"),
				0L);
		}

		for (long deleteCommercePriceModifierId :
				deleteCommercePriceModifierIds) {

			_commercePriceModifierService.deleteCommercePriceModifier(
				deleteCommercePriceModifierId);
		}
	}

	private CommercePriceModifier _updateCommercePriceModifier(
			long commercePriceModifierId, ActionRequest actionRequest)
		throws Exception {

		String title = ParamUtil.getString(actionRequest, "title");
		String target = ParamUtil.getString(actionRequest, "target");
		long commercePriceListId = ParamUtil.getLong(
			actionRequest, "commercePriceListId");
		String modifierType = ParamUtil.getString(
			actionRequest, "modifierType");
		BigDecimal modifierAmount = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "modifierAmount", BigDecimal.ZERO);
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		Date date = new Date();

		Calendar calendar = CalendarFactoryUtil.getCalendar(date.getTime());

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth", calendar.get(Calendar.MONTH));
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay",
			calendar.get(Calendar.DAY_OF_MONTH));
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear", calendar.get(Calendar.YEAR));
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour", calendar.get(Calendar.HOUR));
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute", calendar.get(Calendar.MINUTE));
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm", calendar.get(Calendar.AM_PM));

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
			actionRequest, "neverExpire", true);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePriceModifier.class.getName(), actionRequest);

		if (commercePriceModifierId > 0) {
			long commercePriceListGroupId = ParamUtil.getLong(
				actionRequest, "commercePriceListGroupId");

			return _commercePriceModifierService.updateCommercePriceModifier(
				commercePriceModifierId, commercePriceListGroupId, title,
				target, commercePriceListId, modifierType, modifierAmount,
				priority, active, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				serviceContext);
		}

		return _commercePriceModifierService.addCommercePriceModifier(
			0, title, target, commercePriceListId, modifierType, modifierAmount,
			priority, active, displayDateMonth, displayDateDay, displayDateYear,
			displayDateHour, displayDateMinute, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, neverExpire, serviceContext);
	}

	@Reference
	private CommercePriceModifierService _commercePriceModifierService;

	@Reference
	private Portal _portal;

}
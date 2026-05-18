/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.portlet.action;

import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.pricing.constants.CommercePriceModifierConstants;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.pricing.exception.CommercePriceModifierAmountException;
import com.liferay.commerce.pricing.exception.NoSuchPriceModifierException;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.CommercePriceModifierService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.Calendar;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"jakarta.portlet.name=" + CommercePricingPortletKeys.COMMERCE_PRICE_LIST,
		"jakarta.portlet.name=" + CommercePricingPortletKeys.COMMERCE_PROMOTION,
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
				_addOrUpdateCommercePriceModifier(
					commercePriceModifierId, actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCommercePriceModifiers(
					commercePriceModifierId, actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof CommercePriceModifierAmountException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (exception instanceof NoSuchPriceListException ||
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

	private CommercePriceModifier _addOrUpdateCommercePriceModifier(
			long commercePriceModifierId, ActionRequest actionRequest)
		throws Exception {

		String modifierType = ParamUtil.getString(
			actionRequest, "modifierType");

		Calendar calendar = CalendarFactoryUtil.getCalendar();

		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour", calendar.get(Calendar.HOUR));
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm", calendar.get(Calendar.AM_PM));

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");

		if (ParamUtil.getInteger(actionRequest, "expirationDateAmPm") ==
				Calendar.PM) {

			expirationDateHour += 12;
		}

		return _commercePriceModifierService.addOrUpdateCommercePriceModifier(
			null, commercePriceModifierId,
			ParamUtil.getLong(actionRequest, "commercePriceListGroupId"),
			ParamUtil.getLong(actionRequest, "commercePriceListId"),
			ParamUtil.getString(actionRequest, "title"),
			ParamUtil.getString(actionRequest, "target"),
			_commercePriceFormatter.parse(
				actionRequest,
				!modifierType.equals(
					CommercePriceModifierConstants.MODIFIER_TYPE_REPLACE),
				CommercePriceModifier.class.getName(), "modifierAmount"),
			modifierType, ParamUtil.getDouble(actionRequest, "priority"),
			ParamUtil.getBoolean(actionRequest, "active"),
			ParamUtil.getInteger(
				actionRequest, "displayDateMonth",
				calendar.get(Calendar.MONTH)),
			ParamUtil.getInteger(
				actionRequest, "displayDateDay",
				calendar.get(Calendar.DAY_OF_MONTH)),
			ParamUtil.getInteger(
				actionRequest, "displayDateYear", calendar.get(Calendar.YEAR)),
			displayDateHour,
			ParamUtil.getInteger(
				actionRequest, "displayDateMinute",
				calendar.get(Calendar.MINUTE)),
			ParamUtil.getInteger(actionRequest, "expirationDateMonth"),
			ParamUtil.getInteger(actionRequest, "expirationDateDay"),
			ParamUtil.getInteger(actionRequest, "expirationDateYear"),
			expirationDateHour,
			ParamUtil.getInteger(actionRequest, "expirationDateMinute"),
			ParamUtil.getBoolean(actionRequest, "neverExpire", true),
			ServiceContextFactory.getInstance(
				CommercePriceModifier.class.getName(), actionRequest));
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

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommercePriceModifierService _commercePriceModifierService;

}
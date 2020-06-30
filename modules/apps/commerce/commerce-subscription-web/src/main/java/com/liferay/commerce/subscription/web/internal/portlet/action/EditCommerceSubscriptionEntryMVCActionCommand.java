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

package com.liferay.commerce.subscription.web.internal.portlet.action;

import com.liferay.commerce.exception.CommerceSubscriptionEntryNextIterationDateException;
import com.liferay.commerce.exception.CommerceSubscriptionEntrySubscriptionStatusException;
import com.liferay.commerce.exception.CommerceSubscriptionTypeException;
import com.liferay.commerce.exception.NoSuchSubscriptionEntryException;
import com.liferay.commerce.model.CommerceSubscriptionEntry;
import com.liferay.commerce.payment.engine.CommerceSubscriptionEngine;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.service.CommerceSubscriptionEntryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_SUBSCRIPTION_ENTRY,
		"mvc.command.name=editCommerceSubscriptionEntry"
	},
	service = MVCActionCommand.class
)
public class EditCommerceSubscriptionEntryMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceSubscriptionEntries(
			long commerceSubscriptionEntryId, ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceSubscriptionEntryIds = null;

		if (commerceSubscriptionEntryId > 0) {
			deleteCommerceSubscriptionEntryIds = new long[] {
				commerceSubscriptionEntryId
			};
		}
		else {
			deleteCommerceSubscriptionEntryIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceSubscriptionEntryIds"),
				0L);
		}

		for (long deleteCommerceSubscriptionEntryId :
				deleteCommerceSubscriptionEntryIds) {

			_commerceSubscriptionEntryService.deleteCommerceSubscriptionEntry(
				deleteCommerceSubscriptionEntryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long commerceSubscriptionEntryId = ParamUtil.getLong(
			actionRequest, "commerceSubscriptionEntryId");

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceSubscriptionEntries(
					commerceSubscriptionEntryId, actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommerceSubscriptionEntry(
					commerceSubscriptionEntryId, actionRequest);
			}
			else if (cmd.equals("activate")) {
				_commerceSubscriptionEngine.activateRecurringPayment(
					commerceSubscriptionEntryId);
			}
			else if (cmd.equals("cancel")) {
				_commerceSubscriptionEngine.cancelRecurringPayment(
					commerceSubscriptionEntryId);
			}
			else if (cmd.equals("suspend")) {
				_commerceSubscriptionEngine.suspendRecurringPayment(
					commerceSubscriptionEntryId);
			}
		}
		catch (Exception e) {
			if (e instanceof
					CommerceSubscriptionEntryNextIterationDateException ||
				e instanceof
					CommerceSubscriptionEntrySubscriptionStatusException ||
				e instanceof CommerceSubscriptionTypeException) {

				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCommerceSubscriptionEntry");
			}
			else if (e instanceof NoSuchSubscriptionEntryException ||
					 e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected CommerceSubscriptionEntry updateCommerceSubscriptionEntry(
			long commerceSubscriptionEntryId, ActionRequest actionRequest)
		throws Exception {

		int subscriptionLength = ParamUtil.getInteger(
			actionRequest, "subscriptionLength");
		String subscriptionType = ParamUtil.getString(
			actionRequest, "subscriptionType");
		UnicodeProperties subscriptionTypeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "subscriptionTypeSettings--");
		long maxSubscriptionCycles = ParamUtil.getLong(
			actionRequest, "maxSubscriptionCycles");
		int subscriptionStatus = ParamUtil.getInteger(
			actionRequest, "subscriptionStatus");

		int nextIterationDateMonth = ParamUtil.getInteger(
			actionRequest, "nextIterationDateMonth");
		int nextIterationDateDay = ParamUtil.getInteger(
			actionRequest, "nextIterationDateDay");
		int nextIterationDateYear = ParamUtil.getInteger(
			actionRequest, "nextIterationDateYear");
		int nextIterationDateHour = ParamUtil.getInteger(
			actionRequest, "nextIterationDateHour");
		int nextIterationDateMinute = ParamUtil.getInteger(
			actionRequest, "nextIterationDateMinute");
		int nextIterationDateAmPm = ParamUtil.getInteger(
			actionRequest, "nextIterationDateAmPm");

		if (nextIterationDateAmPm == Calendar.PM) {
			nextIterationDateHour += 12;
		}

		int deliverySubscriptionLength = ParamUtil.getInteger(
			actionRequest, "deliverySubscriptionLength");
		String deliverySubscriptionType = ParamUtil.getString(
			actionRequest, "deliverySubscriptionType");
		UnicodeProperties deliverySubscriptionTypeSettingsProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "deliverySubscriptionTypeSettings--");
		long deliveryMaxSubscriptionCycles = ParamUtil.getLong(
			actionRequest, "deliveryMaxSubscriptionCycles");
		int deliverySubscriptionStatus = ParamUtil.getInteger(
			actionRequest, "deliverySubscriptionStatus");

		int deliveryNextIterationDateMonth = ParamUtil.getInteger(
			actionRequest, "deliveryNextIterationDateMonth");
		int deliveryNextIterationDateDay = ParamUtil.getInteger(
			actionRequest, "deliveryNextIterationDateDay");
		int deliveryNextIterationDateYear = ParamUtil.getInteger(
			actionRequest, "deliveryNextIterationDateYear");
		int deliveryNextIterationDateHour = ParamUtil.getInteger(
			actionRequest, "deliveryNextIterationDateHour");
		int deliveryNextIterationDateMinute = ParamUtil.getInteger(
			actionRequest, "deliveryNextIterationDateMinute");
		int deliveryNextIterationDateAmPm = ParamUtil.getInteger(
			actionRequest, "deliveryNextIterationDateAmPm");

		if (deliveryNextIterationDateAmPm == Calendar.PM) {
			deliveryNextIterationDateHour += 12;
		}

		return _commerceSubscriptionEntryService.
			updateCommerceSubscriptionEntry(
				commerceSubscriptionEntryId, subscriptionLength,
				subscriptionType, subscriptionTypeSettingsProperties,
				maxSubscriptionCycles, subscriptionStatus,
				nextIterationDateMonth, nextIterationDateDay,
				nextIterationDateYear, nextIterationDateHour,
				nextIterationDateMinute, deliverySubscriptionLength,
				deliverySubscriptionType,
				deliverySubscriptionTypeSettingsProperties,
				deliveryMaxSubscriptionCycles, deliverySubscriptionStatus,
				deliveryNextIterationDateMonth, deliveryNextIterationDateDay,
				deliveryNextIterationDateYear, deliveryNextIterationDateHour,
				deliveryNextIterationDateMinute);
	}

	@Reference
	private CommerceSubscriptionEngine _commerceSubscriptionEngine;

	@Reference
	private CommerceSubscriptionEntryService _commerceSubscriptionEntryService;

}
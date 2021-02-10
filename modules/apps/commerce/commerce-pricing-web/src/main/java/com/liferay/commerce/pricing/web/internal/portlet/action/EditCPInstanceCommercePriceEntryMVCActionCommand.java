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

import com.liferay.commerce.price.list.exception.DuplicateCommercePriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

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
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/edit_cp_instance_commerce_price_entry"
	},
	service = MVCActionCommand.class
)
public class EditCPInstanceCommercePriceEntryMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommercePriceEntries(ActionRequest actionRequest)
		throws Exception {

		long[] addCommercePriceListIds = null;

		long commercePriceListId = ParamUtil.getLong(
			actionRequest, "commercePriceListId");

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		if (commercePriceListId > 0) {
			addCommercePriceListIds = new long[] {commercePriceListId};
		}
		else {
			addCommercePriceListIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "commercePriceListIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePriceEntry.class.getName(), actionRequest);

		for (long addCommercePriceListId : addCommercePriceListIds) {
			_commercePriceEntryService.addCommercePriceEntry(
				cpInstanceId, addCommercePriceListId, cpInstance.getPrice(),
				cpInstance.getPromoPrice(), serviceContext);
		}
	}

	protected void deleteCommercePriceEntries(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommercePriceEntryIds = null;

		long commercePriceEntryId = ParamUtil.getLong(
			actionRequest, "commercePriceEntryId");

		if (commercePriceEntryId > 0) {
			deleteCommercePriceEntryIds = new long[] {commercePriceEntryId};
		}
		else {
			deleteCommercePriceEntryIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommercePriceEntryIds"),
				0L);
		}

		for (long deleteCommercePriceEntryId : deleteCommercePriceEntryIds) {
			_commercePriceEntryService.deleteCommercePriceEntry(
				deleteCommercePriceEntryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_MULTIPLE)) {

				addCommercePriceEntries(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommercePriceEntries(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommercePriceEntry(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCPInstanceException ||
				exception instanceof NoSuchPriceEntryException ||
				exception instanceof NoSuchPriceListException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof
						DuplicateCommercePriceEntryException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw exception;
			}
		}
	}

	protected CommercePriceEntry updateCommercePriceEntry(
			ActionRequest actionRequest)
		throws Exception {

		long commercePriceEntryId = ParamUtil.getLong(
			actionRequest, "commercePriceEntryId");

		BigDecimal price = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "price", BigDecimal.ZERO);
		BigDecimal promoPrice = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "promoPrice", BigDecimal.ZERO);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePriceEntry.class.getName(), actionRequest);

		return _commercePriceEntryService.updateCommercePriceEntry(
			commercePriceEntryId, price, promoPrice, serviceContext);
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CPInstanceService _cpInstanceService;

}
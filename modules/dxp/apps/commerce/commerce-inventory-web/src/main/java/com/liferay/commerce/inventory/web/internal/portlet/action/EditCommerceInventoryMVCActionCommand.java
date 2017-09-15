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

package com.liferay.commerce.inventory.web.internal.portlet.action;

import com.liferay.commerce.exception.NoSuchInventoryException;
import com.liferay.commerce.model.CommerceInventory;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.service.CAvailabilityRangeEntryService;
import com.liferay.commerce.service.CommerceInventoryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editProductDefinitionInventory"
	},
	service = MVCActionCommand.class
)
public class EditCommerceInventoryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceInventory(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchInventoryException ||
				e instanceof PrincipalException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceInventory(ActionRequest actionRequest)
		throws Exception {

		long commerceInventoryId = ParamUtil.getLong(
			actionRequest, "commerceInventoryId");

		long cAvailabilityRangeEntryId = ParamUtil.getLong(
			actionRequest, "cAvailabilityRangeEntryId");

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		String commerceInventoryEngine = ParamUtil.getString(
			actionRequest, "commerceInventoryEngine");
		String lowStockActivity = ParamUtil.getString(
			actionRequest, "lowStockActivity");
		long commerceAvailabilityRangeId = ParamUtil.getLong(
			actionRequest, "commerceAvailabilityRangeId");
		boolean displayAvailability = ParamUtil.getBoolean(
			actionRequest, "displayAvailability");
		boolean displayStockQuantity = ParamUtil.getBoolean(
			actionRequest, "displayStockQuantity");
		int minStockQuantity = ParamUtil.getInteger(
			actionRequest, "minStockQuantity");
		boolean backOrders = ParamUtil.getBoolean(actionRequest, "backOrders");
		int minCartQuantity = ParamUtil.getInteger(
			actionRequest, "minCartQuantity");
		int maxCartQuantity = ParamUtil.getInteger(
			actionRequest, "maxCartQuantity");
		String allowedCartQuantities = ParamUtil.getString(
			actionRequest, "allowedCartQuantities");
		int multipleCartQuantity = ParamUtil.getInteger(
			actionRequest, "multipleCartQuantity");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceInventory.class.getName(), actionRequest);

		if (commerceInventoryId <= 0) {
			_commerceInventoryService.addCommerceInventory(
				cpDefinitionId, commerceInventoryEngine, lowStockActivity,
				displayAvailability, displayStockQuantity, minStockQuantity,
				backOrders, minCartQuantity, maxCartQuantity,
				allowedCartQuantities, multipleCartQuantity, serviceContext);
		}
		else {
			_commerceInventoryService.updateCommerceInventory(
				commerceInventoryId, commerceInventoryEngine, lowStockActivity,
				displayAvailability, displayStockQuantity, minStockQuantity,
				backOrders, minCartQuantity, maxCartQuantity,
				allowedCartQuantities, multipleCartQuantity, serviceContext);
		}

		_cAvailabilityRangeEntryService.updateCAvailabilityRangeEntry(
			cAvailabilityRangeEntryId, cpDefinitionId,
			commerceAvailabilityRangeId, serviceContext);
	}

	@Reference
	private CAvailabilityRangeEntryService _cAvailabilityRangeEntryService;

	@Reference
	private CommerceInventoryService _commerceInventoryService;

}
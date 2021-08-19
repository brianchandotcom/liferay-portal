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

import com.liferay.commerce.inventory.exception.MVCCException;
import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

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
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_INVENTORY,
		"mvc.command.name=/commerce_inventory/edit_commerce_inventory_replenishment_item"
	},
	service = MVCActionCommand.class
)
public class EditCommerceInventoryReplenishmentItemMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommerceInventoryReplenishmentItem(
			ActionRequest actionRequest)
		throws PortalException {

		String sku = ParamUtil.getString(actionRequest, "sku");

		long commerceInventoryWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseId");

		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

		int day = ParamUtil.getInteger(actionRequest, "dateDay");
		int month = ParamUtil.getInteger(actionRequest, "dateMonth");
		int year = ParamUtil.getInteger(actionRequest, "dateYear");

		Calendar calendar = Calendar.getInstance();

		calendar.set(year, month, day);

		_commerceInventoryReplenishmentItemService.
			addCommerceInventoryReplenishmentItem(
				commerceInventoryWarehouseId, sku, calendar.getTime(),
				quantity);
	}

	protected void deleteCommerceInventoryReplenishmentItem(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceInventoryReplenishmentItemId = ParamUtil.getLong(
			actionRequest, "commerceInventoryReplenishmentItemId");

		_commerceInventoryReplenishmentItemService.
			deleteCommerceInventoryReplenishmentItem(
				commerceInventoryReplenishmentItemId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addCommerceInventoryReplenishmentItem(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceInventoryReplenishmentItem(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommerceInventoryReplenishmentItem(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof MVCCException) {
				SessionErrors.add(actionRequest, exception.getClass());

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				sendRedirect(actionRequest, actionResponse);
			}
			else {
				_log.error(exception, exception);
			}
		}
	}

	protected void updateCommerceInventoryReplenishmentItem(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceInventoryReplenishmentItemId = ParamUtil.getLong(
			actionRequest, "commerceInventoryReplenishmentItemId");

		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

		int day = ParamUtil.getInteger(actionRequest, "dateDay");
		int month = ParamUtil.getInteger(actionRequest, "dateMonth");
		int year = ParamUtil.getInteger(actionRequest, "dateYear");

		long mvccVersion = ParamUtil.getLong(actionRequest, "mvccVersion");

		Calendar calendar = Calendar.getInstance();

		calendar.set(year, month, day);

		_commerceInventoryReplenishmentItemService.
			updateCommerceInventoryReplenishmentItem(
				commerceInventoryReplenishmentItemId, calendar.getTime(),
				quantity, mvccVersion);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceInventoryReplenishmentItemMVCActionCommand.class);

	@Reference
	private CommerceInventoryReplenishmentItemService
		_commerceInventoryReplenishmentItemService;

}
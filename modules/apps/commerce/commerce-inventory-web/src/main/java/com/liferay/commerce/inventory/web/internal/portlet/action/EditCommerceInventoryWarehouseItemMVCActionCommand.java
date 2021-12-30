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
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
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
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_INVENTORY,
		"mvc.command.name=/commerce_inventory/edit_commerce_inventory_warehouse_item"
	},
	service = MVCActionCommand.class
)
public class EditCommerceInventoryWarehouseItemMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				_deleteCommerceInventoryWarehouseItem(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				_updateCommerceInventoryWarehouseItem(actionRequest);
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

	private void _deleteCommerceInventoryWarehouseItem(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceInventoryWarehouseItemId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseItemId");

		_commerceInventoryWarehouseItemService.
			deleteCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItemId);
	}

	private void _updateCommerceInventoryWarehouseItem(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceInventoryWarehouseItemId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseItemId");

		int quantity = ParamUtil.getInteger(actionRequest, "quantity");
		int reservedQuantity = ParamUtil.getInteger(
			actionRequest, "reservedQuantity");
		long mvccVersion = ParamUtil.getLong(actionRequest, "mvccVersion");

		_commerceInventoryWarehouseItemService.
			updateCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseItemId, quantity, reservedQuantity,
				mvccVersion);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceInventoryWarehouseItemMVCActionCommand.class);

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

}
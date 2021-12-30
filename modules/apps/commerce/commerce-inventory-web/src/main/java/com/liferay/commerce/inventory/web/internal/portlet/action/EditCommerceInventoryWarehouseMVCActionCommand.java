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

import com.liferay.commerce.inventory.exception.DuplicateCommerceInventoryWarehouseItemException;
import com.liferay.commerce.inventory.exception.MVCCException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
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
import com.liferay.portal.kernel.util.Portal;

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
		"mvc.command.name=/commerce_inventory/edit_commerce_inventory_warehouse"
	},
	service = MVCActionCommand.class
)
public class EditCommerceInventoryWarehouseMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				_addCommerceInventoryWarehouse(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCommerceInventoryWarehouse(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				_updateCommerceInventoryWarehouse(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof
					DuplicateCommerceInventoryWarehouseItemException ||
				exception instanceof MVCCException) {

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

	private void _addCommerceInventoryWarehouse(ActionRequest actionRequest)
		throws PortalException {

		long commerceInventoryWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseId");

		String sku = ParamUtil.getString(actionRequest, "sku");

		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

		_commerceInventoryWarehouseItemService.
			addCommerceInventoryWarehouseItem(
				commerceInventoryWarehouseId, sku, quantity);
	}

	private void _deleteCommerceInventoryWarehouse(ActionRequest actionRequest)
		throws PortalException {

		String sku = ParamUtil.getString(actionRequest, "sku");

		_commerceInventoryWarehouseItemService.
			deleteCommerceInventoryWarehouseItems(
				_portal.getCompanyId(actionRequest), sku);
	}

	private void _updateCommerceInventoryWarehouse(ActionRequest actionRequest)
		throws PortalException {

		long commerceInventoryWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseId");

		String sku = ParamUtil.getString(actionRequest, "sku");

		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseId, sku);

		if (commerceInventoryWarehouseItem == null) {
			_commerceInventoryWarehouseItemService.
				addCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseId, sku, quantity);
		}
		else {
			_commerceInventoryWarehouseItemService.
				increaseCommerceInventoryWarehouseItemQuantity(
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseItemId(),
					quantity);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceInventoryWarehouseMVCActionCommand.class);

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

	@Reference
	private Portal _portal;

}
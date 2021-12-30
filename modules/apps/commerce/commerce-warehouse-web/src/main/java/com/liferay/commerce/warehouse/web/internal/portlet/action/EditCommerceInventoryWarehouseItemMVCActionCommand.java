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

package com.liferay.commerce.warehouse.web.internal.portlet.action;

import com.liferay.commerce.exception.NoSuchWarehouseItemException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/edit_commerce_inventory_warehouse_item"
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
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateCommerceInventoryWarehouseItem(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchWarehouseItemException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw exception;
			}
		}
	}

	private CommerceInventoryWarehouseItem
			_updateCommerceInventoryWarehouseItem(ActionRequest actionRequest)
		throws PortalException {

		long commerceInventoryWarehouseItemId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseItemId");
		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem = null;

		if (commerceInventoryWarehouseItemId > 0) {
			long mvccVersion = ParamUtil.getLong(actionRequest, "mvccVersion");

			commerceInventoryWarehouseItem =
				_commerceInventoryWarehouseItemService.
					updateCommerceInventoryWarehouseItem(
						commerceInventoryWarehouseItemId, quantity,
						mvccVersion);
		}
		else {
			long commerceInventoryWarehouseId = ParamUtil.getLong(
				actionRequest, "commerceInventoryWarehouseId");
			String sku = ParamUtil.getString(actionRequest, "sku");

			commerceInventoryWarehouseItem =
				_commerceInventoryWarehouseItemService.
					addCommerceInventoryWarehouseItem(
						commerceInventoryWarehouseId, sku, quantity);
		}

		return commerceInventoryWarehouseItem;
	}

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

	@Reference
	private Portal _portal;

}
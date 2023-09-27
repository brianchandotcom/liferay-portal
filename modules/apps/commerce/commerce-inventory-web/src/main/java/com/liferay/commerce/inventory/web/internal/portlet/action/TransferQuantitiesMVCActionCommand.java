/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.web.internal.portlet.action;

import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.math.BigDecimal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_INVENTORY,
		"mvc.command.name=/commerce_inventory/transfer_quantities"
	},
	service = MVCActionCommand.class
)
public class TransferQuantitiesMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.MOVE)) {
			_moveQuantities(actionRequest);
		}
	}

	private void _moveQuantities(ActionRequest actionRequest) throws Exception {
		long fromCommerceInventoryWarehouseId = ParamUtil.getLong(
			actionRequest, "fromCommerceInventoryWarehouseId");
		long toCommerceInventoryWarehouseId = ParamUtil.getLong(
			actionRequest, "toCommerceInventoryWarehouseId");
		BigDecimal quantity = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "quantity", BigDecimal.ZERO);
		String sku = ParamUtil.getString(actionRequest, "sku");
		String unitOfMeasureKey = ParamUtil.getString(
			actionRequest, "unitOfMeasureKey");

		_commerceInventoryWarehouseItemService.moveQuantitiesBetweenWarehouses(
			fromCommerceInventoryWarehouseId, toCommerceInventoryWarehouseId,
			quantity, sku, unitOfMeasureKey);
	}

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

}
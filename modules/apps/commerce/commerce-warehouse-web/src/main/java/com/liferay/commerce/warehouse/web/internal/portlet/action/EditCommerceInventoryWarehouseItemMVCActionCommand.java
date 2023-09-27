/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.warehouse.web.internal.portlet.action;

import com.liferay.commerce.exception.NoSuchWarehouseItemException;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.math.BigDecimal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
@Component(
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

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem = null;

		long commerceInventoryWarehouseItemId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseItemId");

		BigDecimal quantity = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "quantity", BigDecimal.ZERO);
		String unitOfMeasureKey = ParamUtil.getString(
			actionRequest, "unitOfMeasureKey");

		if (commerceInventoryWarehouseItemId > 0) {
			long mvccVersion = ParamUtil.getLong(actionRequest, "mvccVersion");

			commerceInventoryWarehouseItem =
				_commerceInventoryWarehouseItemService.
					updateCommerceInventoryWarehouseItem(
						commerceInventoryWarehouseItemId, mvccVersion, quantity,
						unitOfMeasureKey);
		}
		else {
			long commerceInventoryWarehouseId = ParamUtil.getLong(
				actionRequest, "commerceInventoryWarehouseId");
			String sku = ParamUtil.getString(actionRequest, "sku");

			commerceInventoryWarehouseItem =
				_commerceInventoryWarehouseItemService.
					addCommerceInventoryWarehouseItem(
						StringPool.BLANK, commerceInventoryWarehouseId,
						quantity, sku, unitOfMeasureKey);
		}

		return commerceInventoryWarehouseItem;
	}

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

}
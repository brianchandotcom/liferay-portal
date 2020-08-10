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

package com.liferay.commerce.shipment.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Alec Sloan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_SHIPMENT,
		"mvc.command.name=editCommerceShipmentItem"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShipmentItemMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceShipmentItems(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceShipmentItemIds = null;

		long commerceShipmentItemId = ParamUtil.getLong(
			actionRequest, "commerceShipmentItemId");

		if (commerceShipmentItemId > 0) {
			deleteCommerceShipmentItemIds = new long[] {commerceShipmentItemId};
		}
		else {
			deleteCommerceShipmentItemIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceShipmentItemIds"),
				0L);
		}

		boolean restoreStockQuantity = ParamUtil.getBoolean(
			actionRequest, "restoreStockQuantity");

		for (long deleteCommerceShipmentItemId :
				deleteCommerceShipmentItemIds) {

			_commerceShipmentItemService.deleteCommerceShipmentItem(
				deleteCommerceShipmentItemId, restoreStockQuantity);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceShipmentItems(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommerceShipmentItem(actionRequest);
			}
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass());

			String redirect = getSaveAndContinueRedirect(actionRequest);

			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	protected String getSaveAndContinueRedirect(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, themeDisplay.getScopeGroup(),
			CommerceShipment.class.getName(), PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceShipmentItem");

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		if (commerceShipmentId > 0) {
			portletURL.setParameter(
				"commerceShipmentId", String.valueOf(commerceShipmentId));
		}

		long commerceShipmentItemId = ParamUtil.getLong(
			actionRequest, "commerceShipmentItemId");

		if (commerceShipmentItemId > 0) {
			portletURL.setParameter(
				"commerceShipmentItemId",
				String.valueOf(commerceShipmentItemId));
		}

		long commerceOrderItemId = ParamUtil.getLong(
			actionRequest, "commerceOrderItemId");

		if (commerceOrderItemId > 0) {
			portletURL.setParameter(
				"commerceOrderItemId", String.valueOf(commerceOrderItemId));
		}

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException wse) {
			_log.error(wse, wse);
		}

		return portletURL.toString();
	}

	protected CommerceShipmentItem updateCommerceShipmentItem(
			ActionRequest actionRequest)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceShipmentItem.class.getName(), actionRequest);

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		long commerceOrderItemId = ParamUtil.getLong(
			actionRequest, "commerceOrderItemId");

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(commerceOrderItemId);

		CommerceShipmentItem initialCommerceShipmentItem =
			_commerceShipmentItemService.fetchCommerceShipmentItem(
				commerceShipmentId, commerceOrderItemId, 0);

		CommerceShipmentItem commerceShipmentItem = null;

		List<CommerceInventoryWarehouse> commerceInventoryWarehouses =
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				commerceOrderItem.getCompanyId(),
				commerceOrderItem.getGroupId(), true);

		for (CommerceInventoryWarehouse commerceInventoryWarehouse :
				commerceInventoryWarehouses) {

			long commerceInventoryWarehouseId =
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId();

			commerceShipmentItem =
				_commerceShipmentItemService.fetchCommerceShipmentItem(
					commerceShipmentId, commerceOrderItemId,
					commerceInventoryWarehouseId);

			int quantity = ParamUtil.getInteger(
				actionRequest, commerceInventoryWarehouseId + "_quantity");

			if ((initialCommerceShipmentItem != null) && (quantity > 0)) {
				commerceShipmentItem =
					_commerceShipmentItemService.updateCommerceShipmentItem(
						initialCommerceShipmentItem.getCommerceShipmentItemId(),
						commerceInventoryWarehouseId, quantity);

				initialCommerceShipmentItem = null;
			}
			else if ((commerceShipmentItem == null) && (quantity > 0)) {
				commerceShipmentItem =
					_commerceShipmentItemService.addCommerceShipmentItem(
						commerceShipmentId, commerceOrderItemId,
						commerceInventoryWarehouseId, quantity, serviceContext);
			}
			else if ((commerceShipmentItem != null) &&
					 (quantity != commerceShipmentItem.getQuantity())) {

				commerceShipmentItem =
					_commerceShipmentItemService.updateCommerceShipmentItem(
						commerceShipmentItem.getCommerceShipmentItemId(),
						commerceInventoryWarehouseId, quantity);
			}
		}

		return commerceShipmentItem;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceShipmentItemMVCActionCommand.class);

	@Reference
	private CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

}
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

package com.liferay.commerce.order.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
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
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORDER,
		"mvc.command.name=editCommerceOrderItem"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderItemMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommerceOrderItems(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		long[] cpInstanceIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "cpInstanceIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrderItem.class.getName(), actionRequest);

		for (long cpInstanceId : cpInstanceIds) {
			CPInstance cpInstance = _cpInstanceService.getCPInstance(
				cpInstanceId);

			_commerceOrderItemService.addCommerceOrderItem(
				commerceOrderId, cpInstanceId, 1, 0, cpInstance.getDDMContent(),
				cpInstance.getPrice(), serviceContext);
		}
	}

	protected void deleteCommerceOrderItems(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceOrderItemIds = null;

		long commerceOrderItemId = ParamUtil.getLong(
			actionRequest, "commerceOrderItemId");

		if (commerceOrderItemId > 0) {
			deleteCommerceOrderItemIds = new long[] {commerceOrderItemId};
		}
		else {
			deleteCommerceOrderItemIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceOrderItemIds"),
				0L);
		}

		for (long deleteCommerceOrderItemId : deleteCommerceOrderItemIds) {
			_commerceOrderItemService.deleteCommerceOrderItem(
				deleteCommerceOrderItemId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD)) {
			addCommerceOrderItems(actionRequest);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			updateCommerceOrderItem(actionRequest);
		}
		else if (cmd.equals(Constants.DELETE)) {
			deleteCommerceOrderItems(actionRequest);
		}
	}

	protected void updateCommerceOrderItem(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderItemId = ParamUtil.getLong(
			actionRequest, "commerceOrderItemId");

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(commerceOrderItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		int quantity = ParamUtil.getInteger(actionRequest, "quantity");
		double price = ParamUtil.getDouble(actionRequest, "price");

		if (commerceOrder.isOpen()) {
			_commerceOrderItemService.updateCommerceOrderItem(
				commerceOrderItemId, quantity);
		}
		else {
			_commerceOrderItemService.updateCommerceOrderItem(
				commerceOrderItemId, quantity, commerceOrderItem.getJson(),
				BigDecimal.valueOf(price));
		}
	}

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CPInstanceService _cpInstanceService;

}
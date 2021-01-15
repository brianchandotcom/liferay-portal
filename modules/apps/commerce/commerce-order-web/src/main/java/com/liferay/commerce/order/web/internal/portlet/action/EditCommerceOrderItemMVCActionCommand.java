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
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.exception.CommerceOrderItemRequestedDeliveryDateException;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.math.BigDecimal;

import java.util.concurrent.Callable;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORDER,
		"mvc.command.name=/commerce_order/edit_commerce_order_item"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderItemMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommerceOrderItems(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrderItem.class.getName(), actionRequest);

		long[] cpInstanceIds = ParamUtil.getLongValues(
			actionRequest, "cpInstanceIds");

		for (long cpInstanceId : cpInstanceIds) {
			_commerceOrderItemService.addCommerceOrderItem(
				commerceOrderId, cpInstanceId, null, 1, 0, commerceContext,
				serviceContext);
		}
	}

	protected void deleteCommerceOrderItems(ActionRequest actionRequest)
		throws Exception {

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long[] deleteCommerceOrderItemIds = null;

		long commerceOrderItemId = ParamUtil.getLong(
			actionRequest, "commerceOrderItemId");

		if (commerceOrderItemId > 0) {
			deleteCommerceOrderItemIds = new long[] {commerceOrderItemId};
		}
		else {
			deleteCommerceOrderItemIds = ParamUtil.getLongValues(
				actionRequest, "deleteCommerceOrderItemIds");
		}

		for (long deleteCommerceOrderItemId : deleteCommerceOrderItemIds) {
			_commerceOrderItemService.deleteCommerceOrderItem(
				deleteCommerceOrderItemId, commerceContext);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addCommerceOrderItems(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				Callable<Object> commerceOrderItemCallable =
					new CommerceOrderItemCallable(actionRequest);

				TransactionInvokerUtil.invoke(
					_transactionConfig, commerceOrderItemCallable);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceOrderItems(actionRequest);
			}
			else if (cmd.equals("customFields")) {
				updateCustomFields(actionRequest);
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof CommerceOrderValidatorException) {
				SessionErrors.add(
					actionRequest, throwable.getClass(), throwable);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (throwable instanceof
						CommerceOrderItemRequestedDeliveryDateException) {

				SessionErrors.add(
					actionRequest, throwable.getClass(), throwable);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				_log.error(throwable, throwable);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
	}

	protected void updateCommerceOrderItem(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderItemId = ParamUtil.getLong(
			actionRequest, "commerceOrderItemId");
		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

		CommerceOrderItem commerceOrderItem =
			_commerceOrderItemService.getCommerceOrderItem(commerceOrderItemId);

		CommerceOrder commerceOrder = commerceOrderItem.getCommerceOrder();

		if (commerceOrder.isOpen()) {
			CommerceContext commerceContext =
				(CommerceContext)actionRequest.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceOrderItem.class.getName(), actionRequest);

			commerceOrderItem =
				_commerceOrderItemService.updateCommerceOrderItem(
					commerceOrderItemId, quantity, commerceContext,
					serviceContext);
		}
		else {
			BigDecimal price = (BigDecimal)ParamUtil.getNumber(
				actionRequest, "price");

			commerceOrderItem =
				_commerceOrderItemService.updateCommerceOrderItemUnitPrice(
					commerceOrderItemId, quantity, price);
		}

		int requestedDeliveryDateMonth = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateMonth");
		int requestedDeliveryDateDay = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateDay");
		int requestedDeliveryDateYear = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateYear");

		String deliveryGroup = ParamUtil.getString(
			actionRequest, "deliveryGroup");

		_commerceOrderItemService.updateCommerceOrderItemInfo(
			commerceOrderItem.getCommerceOrderItemId(),
			commerceOrderItem.getShippingAddressId(), deliveryGroup,
			commerceOrderItem.getPrintedNote(), requestedDeliveryDateMonth,
			requestedDeliveryDateDay, requestedDeliveryDateYear);
	}

	protected void updateCustomFields(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderItemId = ParamUtil.getLong(
			actionRequest, "commerceOrderItemId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrderItem.class.getName(), actionRequest);

		_commerceOrderItemService.updateCustomFields(
			commerceOrderItemId, serviceContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceOrderItemMVCActionCommand.class);

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CPInstanceService _cpInstanceService;

	private class CommerceOrderItemCallable implements Callable<Object> {

		@Override
		public Object call() throws Exception {
			updateCommerceOrderItem(_actionRequest);

			return null;
		}

		private CommerceOrderItemCallable(ActionRequest actionRequest) {
			_actionRequest = actionRequest;
		}

		private final ActionRequest _actionRequest;

	}

}
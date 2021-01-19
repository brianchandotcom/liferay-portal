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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.constants.CommerceAddressConstants;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantity;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.order.engine.CommerceOrderEngine;
import com.liferay.commerce.payment.engine.CommercePaymentEngine;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShipmentService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.math.BigDecimal;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

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
		"mvc.command.name=/commerce_order/edit_commerce_order"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderMVCActionCommand extends BaseMVCActionCommand {

	protected void addBillingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAddress.class.getName(), actionRequest);

		CommerceAddress commerceAddress =
			_commerceAddressService.addCommerceAddress(
				CommerceAccount.class.getName(),
				commerceOrder.getCommerceAccountId(), name, description,
				street1, street2, street3, city, zip, commerceRegionId,
				commerceCountryId, phoneNumber,
				CommerceAddressConstants.ADDRESS_TYPE_BILLING, serviceContext);

		_commerceOrderService.updateBillingAddress(
			commerceOrder.getCommerceOrderId(),
			commerceAddress.getCommerceAddressId());
	}

	protected CommerceShipment addShipment(
			ActionRequest actionRequest, long commerceOrderId)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceShipment.class.getName(), actionRequest);

		return _commerceShipmentService.addCommerceShipment(
			commerceOrderId, serviceContext);
	}

	protected void addShippingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAddress.class.getName(), actionRequest);

		CommerceAddress commerceAddress =
			_commerceAddressService.addCommerceAddress(
				CommerceAccount.class.getName(),
				commerceOrder.getCommerceAccountId(), name, description,
				street1, street2, street3, city, zip, commerceRegionId,
				commerceCountryId, phoneNumber,
				CommerceAddressConstants.ADDRESS_TYPE_SHIPPING, serviceContext);

		_commerceOrderService.updateShippingAddress(
			commerceOrder.getCommerceOrderId(),
			commerceAddress.getCommerceAddressId());
	}

	protected void deleteCommerceOrders(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceOrderIds = null;

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		if (commerceOrderId > 0) {
			deleteCommerceOrderIds = new long[] {commerceOrderId};
		}
		else {
			deleteCommerceOrderIds = ParamUtil.getLongValues(
				actionRequest, "deleteCommerceOrderIds");
		}

		for (long deleteCommerceOrderId : deleteCommerceOrderIds) {
			_commerceOrderService.deleteCommerceOrder(deleteCommerceOrderId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceOrders(actionRequest);
			}
			else if (cmd.equals("addBillingAddress")) {
				addBillingAddress(actionRequest);
			}
			else if (cmd.equals("addShippingAddress")) {
				addShippingAddress(actionRequest);
			}
			else if (cmd.equals("customFields")) {
				updateCustomFields(actionRequest);
			}
			else if (cmd.equals("orderSummary")) {
				updateOrderSummary(actionRequest);
			}
			else if (cmd.equals("paymentMethod")) {
				updatePaymentMethod(actionRequest);
			}
			else if (cmd.equals("paymentStatus")) {
				updatePaymentStatus(actionRequest);
			}
			else if (cmd.equals("printedNote")) {
				updatePrintedNote(actionRequest);
			}
			else if (cmd.equals("purchaseOrderNumber")) {
				updatePurchaseOrderNumber(actionRequest);
			}
			else if (cmd.equals("requestedDeliveryDate")) {
				updateRequestedDeliveryDate(actionRequest);
			}
			else if (cmd.equals("selectBillingAddress")) {
				selectBillingAddress(actionRequest);
			}
			else if (cmd.equals("selectShippingAddress")) {
				selectShippingAddress(actionRequest);
			}
			else if (cmd.equals("totals")) {
				updateTotals(actionRequest);
			}
			else if (cmd.equals("transition")) {
				executeTransition(actionRequest, actionResponse);
			}
			else if (cmd.equals("updateBillingAddress")) {
				updateBillingAddress(actionRequest);
			}
			else if (cmd.equals("updateShippingAddress")) {
				updateShippingAddress(actionRequest);
			}
		}
		catch (Exception exception) {
			hideDefaultErrorMessage(actionRequest);
			hideDefaultSuccessMessage(actionRequest);

			SessionErrors.add(actionRequest, exception.getClass());

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			sendRedirect(actionRequest, actionResponse, redirect);
		}
	}

	protected void executeTransition(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		long workflowTaskId = ParamUtil.getLong(
			actionRequest, "workflowTaskId");
		String transitionName = ParamUtil.getString(
			actionRequest, "transitionName");

		if (workflowTaskId > 0) {
			executeWorkflowTransition(
				actionRequest, commerceOrderId, transitionName, workflowTaskId);
		}
		else {
			CommerceOrder commerceOrder =
				_commerceOrderService.getCommerceOrder(commerceOrderId);

			int orderStatus = GetterUtil.getInteger(
				transitionName, commerceOrder.getOrderStatus());

			if (orderStatus == CommerceOrderConstants.ORDER_STATUS_CANCELLED) {
				for (CommerceOrderItem commerceOrderItem :
						commerceOrder.getCommerceOrderItems()) {

					if (commerceOrderItem.getBookedQuantityId() > 0) {
						CommerceInventoryBookedQuantity
							commerceInventoryBookedQuantity =
								_commerceInventoryBookedQuantityLocalService.
									fetchCommerceInventoryBookedQuantity(
										commerceOrderItem.
											getBookedQuantityId());

						if (commerceInventoryBookedQuantity != null) {
							_commerceInventoryBookedQuantityLocalService.
								deleteCommerceInventoryBookedQuantity(
									commerceInventoryBookedQuantity);
						}
					}
				}
			}

			if ((orderStatus ==
					CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED) &&
				(commerceOrder.getOrderStatus() !=
					CommerceOrderConstants.ORDER_STATUS_ON_HOLD)) {

				CommerceShipment commerceShipment = addShipment(
					actionRequest, commerceOrderId);

				redirectToShipments(
					commerceShipment.getCommerceShipmentId(), actionRequest,
					actionResponse);
			}
			else {
				_commerceOrderEngine.transitionCommerceOrder(
					commerceOrder, orderStatus,
					_portal.getUserId(actionRequest));
			}
		}
	}

	protected void executeWorkflowTransition(
			ActionRequest actionRequest, long commerceOrderId,
			String transitionName, long workflowTaskId)
		throws PortalException {

		String comment = ParamUtil.getString(actionRequest, "comment");

		_commerceOrderService.executeWorkflowTransition(
			commerceOrderId, workflowTaskId, transitionName, comment);
	}

	protected void redirectToShipments(
			long commerceShipmentId, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		PortletURL shipmentPortletURL = _portal.getControlPanelPortletURL(
			actionRequest, CommercePortletKeys.COMMERCE_SHIPMENT,
			PortletRequest.RENDER_PHASE);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		shipmentPortletURL.setParameter("redirect", redirect);

		shipmentPortletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_shipment/edit_commerce_shipment");
		shipmentPortletURL.setParameter(
			"commerceShipmentId", String.valueOf(commerceShipmentId));

		sendRedirect(
			actionRequest, actionResponse, shipmentPortletURL.toString());
	}

	protected void selectBillingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		long addressId = ParamUtil.getLong(actionRequest, "addressId");

		_commerceOrderService.updateBillingAddress(commerceOrderId, addressId);
	}

	protected void selectShippingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		long addressId = ParamUtil.getLong(actionRequest, "addressId");

		_commerceOrderService.updateShippingAddress(commerceOrderId, addressId);
	}

	protected void updateBillingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		_commerceOrderService.updateBillingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			serviceContext);
	}

	protected void updateCustomFields(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		_commerceOrderService.updateCustomFields(
			commerceOrderId, serviceContext);
	}

	protected void updateOrderSummary(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		String subtotal = ParamUtil.getString(actionRequest, "subtotal");
		String subtotalDiscountAmount = ParamUtil.getString(
			actionRequest, "subtotalDiscountAmount");
		String shippingAmount = ParamUtil.getString(
			actionRequest, "shippingAmount");
		String shippingDiscountAmount = ParamUtil.getString(
			actionRequest, "shippingDiscountAmount");
		String taxAmount = ParamUtil.getString(actionRequest, "taxAmount");
		String total = ParamUtil.getString(actionRequest, "total");
		String totalDiscountAmount = ParamUtil.getString(
			actionRequest, "totalDiscountAmount");

		_commerceOrderService.updateCommerceOrderPrices(
			commerceOrder.getCommerceOrderId(), new BigDecimal(subtotal),
			new BigDecimal(subtotalDiscountAmount),
			commerceOrder.getSubtotalDiscountPercentageLevel1(),
			commerceOrder.getSubtotalDiscountPercentageLevel2(),
			commerceOrder.getSubtotalDiscountPercentageLevel3(),
			commerceOrder.getSubtotalDiscountPercentageLevel4(),
			new BigDecimal(shippingAmount),
			new BigDecimal(shippingDiscountAmount),
			commerceOrder.getShippingDiscountPercentageLevel1(),
			commerceOrder.getShippingDiscountPercentageLevel2(),
			commerceOrder.getShippingDiscountPercentageLevel3(),
			commerceOrder.getShippingDiscountPercentageLevel4(),
			new BigDecimal(taxAmount), new BigDecimal(total),
			new BigDecimal(totalDiscountAmount),
			commerceOrder.getTotalDiscountPercentageLevel1(),
			commerceOrder.getTotalDiscountPercentageLevel2(),
			commerceOrder.getTotalDiscountPercentageLevel3(),
			commerceOrder.getTotalDiscountPercentageLevel4());
	}

	protected void updatePaymentMethod(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		String paymentMethodKey = ParamUtil.getString(
			actionRequest, "paymentMethodKey");

		_commerceOrderService.updateCommercePaymentMethodKey(
			commerceOrderId, paymentMethodKey);
	}

	protected void updatePaymentStatus(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		int paymentStatus = ParamUtil.getInteger(
			actionRequest, "paymentStatus");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		_commercePaymentEngine.updateOrderPaymentStatus(
			commerceOrderId, paymentStatus, commerceOrder.getTransactionId());
	}

	protected void updatePrintedNote(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");
		String printedNote = ParamUtil.getString(actionRequest, "printedNote");

		_commerceOrderService.updatePrintedNote(commerceOrderId, printedNote);
	}

	protected void updatePurchaseOrderNumber(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");
		String purchaseOrderNumber = ParamUtil.getString(
			actionRequest, "purchaseOrderNumber");

		_commerceOrderService.updatePurchaseOrderNumber(
			commerceOrderId, purchaseOrderNumber);
	}

	protected void updateRequestedDeliveryDate(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		int requestedDeliveryDateMonth = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateMonth");
		int requestedDeliveryDateDay = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateDay");
		int requestedDeliveryDateYear = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateYear");
		int requestedDeliveryDateHour = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateHour");
		int requestedDeliveryDateMinute = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateMinute");
		int requestedDeliveryDateAmPm = ParamUtil.getInteger(
			actionRequest, "requestedDeliveryDateAmPm");

		if (requestedDeliveryDateAmPm == Calendar.PM) {
			requestedDeliveryDateHour += 12;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		_commerceOrderService.updateInfo(
			commerceOrder.getCommerceOrderId(), commerceOrder.getPrintedNote(),
			requestedDeliveryDateMonth, requestedDeliveryDateDay,
			requestedDeliveryDateYear, requestedDeliveryDateHour,
			requestedDeliveryDateMinute, serviceContext);
	}

	protected void updateShippingAddress(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		_commerceOrderService.updateShippingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			serviceContext);
	}

	protected void updateTotals(ActionRequest actionRequest)
		throws PortalException {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		String subtotal = ParamUtil.getString(actionRequest, "subtotal");
		String shippingPrice = ParamUtil.getString(
			actionRequest, "shippingPrice");
		String total = ParamUtil.getString(actionRequest, "total");

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		_commerceOrderService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			commerceOrder.getBillingAddressId(),
			commerceOrder.getShippingAddressId(),
			commerceOrder.getCommercePaymentMethodKey(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingOptionName(),
			commerceOrder.getPurchaseOrderNumber(), new BigDecimal(subtotal),
			new BigDecimal(shippingPrice), new BigDecimal(total),
			commerceOrder.getAdvanceStatus(), commerceContext);
	}

	@Reference
	private CommerceAddressService _commerceAddressService;

	@Reference
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@Reference
	private CommerceOrderEngine _commerceOrderEngine;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePaymentEngine _commercePaymentEngine;

	@Reference
	private CommerceShipmentService _commerceShipmentService;

	@Reference
	private Portal _portal;

}
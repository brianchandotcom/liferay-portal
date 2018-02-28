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
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.commerce.order.web.internal.permission.CommerceOrderWorkflowPermissionChecker;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

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
		"mvc.command.name=editCommerceOrder"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderMVCActionCommand extends BaseMVCActionCommand {

	protected void approveCommerceOrder(long commerceOrderId) throws Exception {
		_commerceOrderService.approveCommerceOrder(commerceOrderId);
	}

	protected void checkoutCommerceOrder(
			ActionRequest actionRequest, long commerceOrderId)
		throws Exception {

		PortletURL portletURL =
			_commerceOrderHelper.getCommerceCheckoutPortletURL(
				_portal.getHttpServletRequest(actionRequest));

		portletURL.setParameter(
			"commerceOrderId", String.valueOf(commerceOrderId));

		actionRequest.setAttribute(WebKeys.REDIRECT, portletURL.toString());
	}

	protected void deleteCommerceOrders(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceOrderIds = null;

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		if (commerceOrderId > 0) {
			deleteCommerceOrderIds = new long[] {commerceOrderId};
		}
		else {
			deleteCommerceOrderIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceOrderIds"),
				0L);
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

		if (cmd.equals(Constants.DELETE)) {
			deleteCommerceOrders(actionRequest);
		}
		else if (cmd.equals("billingAddress")) {
			updateBillingAddress(actionRequest);
		}
		else if (cmd.equals("orderStatus")) {
			updateOrderStatus(actionRequest);
		}
		else if (cmd.equals("payment")) {
			updatePayment(actionRequest);
		}
		else if (cmd.equals("shippingAddress")) {
			updateShippingAddress(actionRequest);
		}
		else if (cmd.equals("totals")) {
			updateTotals(actionRequest);
		}
		else if (cmd.equals("transition")) {
			executeTransition(actionRequest);
		}
	}

	protected void executeTransition(ActionRequest actionRequest)
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
		else if (transitionName.equals("approve") ||
				 transitionName.equals("force-approve")) {

			approveCommerceOrder(commerceOrderId);
		}
		else if (transitionName.equals("checkout")) {
			checkoutCommerceOrder(actionRequest, commerceOrderId);
		}
		else if (transitionName.equals("submit")) {
			submitCommerceOrder(commerceOrderId);
		}
	}

	protected void executeWorkflowTransition(
			ActionRequest actionRequest, long commerceOrderId,
			String transitionName, long workflowTaskId)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String comment = ParamUtil.getString(actionRequest, "comment");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		long companyId = commerceOrder.getCompanyId();

		WorkflowTask workflowTask = _workflowTaskManager.getWorkflowTask(
			companyId, workflowTaskId);

		if (!_commerceOrderWorkflowPermissionChecker.hasPermission(
				commerceOrder, workflowTask,
				themeDisplay.getPermissionChecker())) {

			throw new PrincipalException();
		}

		long userId = themeDisplay.getUserId();

		if (!workflowTask.isAssignedToSingleUser()) {
			workflowTask = _workflowTaskManager.assignWorkflowTaskToUser(
				companyId, userId, workflowTask.getWorkflowTaskId(), userId,
				null, null, null);
		}

		_workflowTaskManager.completeWorkflowTask(
			companyId, userId, workflowTask.getWorkflowTaskId(), transitionName,
			comment, null);
	}

	protected void submitCommerceOrder(long commerceOrderId) throws Exception {
		_commerceOrderService.submitCommerceOrder(commerceOrderId);
	}

	protected void updateBillingAddress(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		_commerceOrderService.updateBillingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			serviceContext);
	}

	protected void updateOrderStatus(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		int orderStatus = ParamUtil.getInteger(actionRequest, "orderStatus");

		_commerceOrderService.updateCommerceOrder(
			commerceOrderId, commerceOrder.getBillingAddressId(),
			commerceOrder.getShippingAddressId(),
			commerceOrder.getCommercePaymentMethodId(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingOptionName(),
			commerceOrder.getPurchaseOrderNumber(), commerceOrder.getSubtotal(),
			commerceOrder.getShippingPrice(), commerceOrder.getTotal(),
			commerceOrder.getAdvanceStatus(), commerceOrder.getPaymentStatus(),
			orderStatus);
	}

	protected void updatePayment(ActionRequest actionRequest) throws Exception {
		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		long commercePaymentMethodId = ParamUtil.getLong(
			actionRequest, "commercePaymentMethodId");
		String advanceStatus = ParamUtil.getString(
			actionRequest, "advanceStatus");
		int paymentStatus = ParamUtil.getInteger(
			actionRequest, "paymentStatus");
		String purchaseOrderNumber = ParamUtil.getString(
			actionRequest, "purchaseOrderNumber");

		_commerceOrderService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			commerceOrder.getBillingAddressId(),
			commerceOrder.getShippingAddressId(), commercePaymentMethodId,
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingOptionName(), purchaseOrderNumber,
			commerceOrder.getSubtotal(), commerceOrder.getShippingPrice(),
			commerceOrder.getTotal(), advanceStatus, paymentStatus,
			commerceOrder.getOrderStatus());
	}

	protected void updateShippingAddress(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		String street1 = ParamUtil.getString(actionRequest, "street1");
		String street2 = ParamUtil.getString(actionRequest, "street2");
		String street3 = ParamUtil.getString(actionRequest, "street3");
		String city = ParamUtil.getString(actionRequest, "city");
		String zip = ParamUtil.getString(actionRequest, "zip");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");
		String phoneNumber = ParamUtil.getString(actionRequest, "phoneNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrder.class.getName(), actionRequest);

		_commerceOrderService.updateShippingAddress(
			commerceOrderId, name, description, street1, street2, street3, city,
			zip, commerceRegionId, commerceCountryId, phoneNumber,
			serviceContext);
	}

	protected void updateTotals(ActionRequest actionRequest) throws Exception {
		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		double subtotal = ParamUtil.getDouble(actionRequest, "subtotal");
		double shippingPrice = ParamUtil.getDouble(
			actionRequest, "shippingPrice");
		double total = ParamUtil.getDouble(actionRequest, "total");

		_commerceOrderService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			commerceOrder.getBillingAddressId(),
			commerceOrder.getShippingAddressId(),
			commerceOrder.getCommercePaymentMethodId(),
			commerceOrder.getCommerceShippingMethodId(),
			commerceOrder.getShippingOptionName(),
			commerceOrder.getPurchaseOrderNumber(), subtotal, shippingPrice,
			total, commerceOrder.getAdvanceStatus(),
			commerceOrder.getPaymentStatus(), commerceOrder.getOrderStatus());
	}

	@Reference
	private CommerceOrderHelper _commerceOrderHelper;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrderWorkflowPermissionChecker
		_commerceOrderWorkflowPermissionChecker;

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}
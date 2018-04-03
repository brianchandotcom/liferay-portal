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

package com.liferay.commerce.organization.order.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.NoSuchOrderException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHelper;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalService;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManager;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORGANIZATION_ORDER,
		"mvc.command.name=editCommerceOrder"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderMVCActionCommand extends BaseMVCActionCommand {

	protected void addCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		long organizationId = ParamUtil.getLong(
			actionRequest, "organizationId");
		long shippingAddressId = ParamUtil.getLong(
			actionRequest, "shippingAddressId");
		String purchaseOrderNumber = ParamUtil.getString(
			actionRequest, "purchaseOrderNumber");

		Organization organization =
			_commerceOrganizationService.getOrganization(organizationId);

		Organization accountOrganization =
			_commerceOrganizationLocalService.getAccountOrganization(
				organization.getOrganizationId());

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_commerceOrderService.addOrganizationCommerceOrder(
			organization.getGroupId(), themeDisplay.getSiteGroupId(),
			accountOrganization.getOrganizationId(), shippingAddressId,
			purchaseOrderNumber);
	}

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

		try {
			if (cmd.equals(Constants.ADD)) {
				addCommerceOrder(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceOrders(actionRequest);
			}
			else if (cmd.equals("reorder")) {
				reorderCommerceOrder(actionRequest);
			}
			else if (cmd.equals("setCurrent")) {
				setCurrentCommerceOrder(actionRequest);
			}
			else if (cmd.equals("transition")) {
				executeTransition(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
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
		else if (transitionName.equals("reorder")) {
			reorderCommerceOrder(actionRequest);
		}
		else if (transitionName.equals("submit")) {
			submitCommerceOrder(commerceOrderId);
		}
	}

	protected void executeWorkflowTransition(
			ActionRequest actionRequest, long commerceOrderId,
			String transitionName, long workflowTaskId)
		throws Exception {

		String comment = ParamUtil.getString(actionRequest, "comment");

		_commerceOrderService.executeWorkflowTransition(
			commerceOrderId, workflowTaskId, transitionName, comment);
	}

	protected void reorderCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		reorderCommerceOrder(actionRequest, commerceOrderId);
	}

	protected void reorderCommerceOrder(
			ActionRequest actionRequest, long commerceOrderId)
		throws Exception {

		CommerceOrder commerceOrder =
			_commerceOrderService.reorderCommerceOrder(commerceOrderId);

		checkoutCommerceOrder(
			actionRequest, commerceOrder.getCommerceOrderId());
	}

	protected void setCurrentCommerceOrder(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		_commerceOrderHelper.setCurrentCommerceOrder(
			_portal.getHttpServletRequest(actionRequest), commerceOrder);
	}

	protected void submitCommerceOrder(long commerceOrderId) throws Exception {
		_commerceOrderService.submitCommerceOrder(commerceOrderId);
	}

	@Reference
	private CommerceOrderHelper _commerceOrderHelper;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceOrganizationLocalService _commerceOrganizationLocalService;

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private Portal _portal;

	@Reference
	private WorkflowInstanceManager _workflowInstanceManager;

	@Reference
	private WorkflowTaskManager _workflowTaskManager;

}
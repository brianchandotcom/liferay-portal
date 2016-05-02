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

package com.liferay.shopping.web.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.shopping.constants.ShoppingPortletKeys;
import com.liferay.shopping.exception.NoSuchOrderException;
import com.liferay.shopping.service.ShoppingOrderService;
import com.liferay.shopping.util.ShoppingUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING,
		"javax.portlet.name=" + ShoppingPortletKeys.SHOPPING_ADMIN,
		"mvc.command.name=/shopping/edit_order"
	},
	service = MVCActionCommand.class
)
public class EditOrderMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteOrders(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] deleteOrderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteOrderIds"), 0L);

		for (long deleteOrderId : deleteOrderIds) {
			_shoppingOrderService.deleteOrder(
				themeDisplay.getScopeGroupId(), deleteOrderId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateOrder(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteOrders(actionRequest);
			}
			else if (cmd.equals("sendEmail")) {
				sendEmail(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
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

	protected void sendEmail(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long orderId = ParamUtil.getLong(actionRequest, "orderId");

		String emailType = ParamUtil.getString(actionRequest, "emailType");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_shoppingOrderService.sendEmail(
			themeDisplay.getScopeGroupId(), orderId, emailType, serviceContext);
	}

	@Reference(unbind = "-")
	protected void setShoppingOrderService(
		ShoppingOrderService shoppingOrderService) {

		_shoppingOrderService = shoppingOrderService;
	}

	protected void updateOrder(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String number = ParamUtil.getString(actionRequest, "number");
		String ppTxnId = ParamUtil.getString(actionRequest, "ppTxnId");
		String ppPaymentStatus = ShoppingUtil.getPpPaymentStatus(
			ParamUtil.getString(actionRequest, "ppPaymentStatus"));
		double ppPaymentGross = ParamUtil.getDouble(
			actionRequest, "ppPaymentGross");
		String ppReceiverEmail = ParamUtil.getString(
			actionRequest, "ppReceiverEmail");
		String ppPayerEmail = ParamUtil.getString(
			actionRequest, "ppPayerEmail");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		_shoppingOrderService.completeOrder(
			themeDisplay.getScopeGroupId(), number, ppTxnId, ppPaymentStatus,
			ppPaymentGross, ppReceiverEmail, ppPayerEmail, serviceContext);
	}

	private ShoppingOrderService _shoppingOrderService;

}
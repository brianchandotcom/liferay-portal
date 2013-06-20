/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.shopping.NoSuchOrderException;
import com.liferay.portlet.shopping.service.ShoppingOrderServiceUtil;
import com.liferay.portlet.shopping.util.ShoppingUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 */
public class EditOrderAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
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

				setForward(actionRequest, "portlet.shopping.error");
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getOrder(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.shopping.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(renderRequest, "portlet.shopping.edit_order"));
	}

	protected void deleteOrders(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] deleteOrderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteOrderIds"), 0L);

		for (int i = 0; i < deleteOrderIds.length; i++) {
			ShoppingOrderServiceUtil.deleteOrder(
				themeDisplay.getScopeGroupId(), deleteOrderIds[i]);
		}
	}

	protected void sendEmail(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long orderId = ParamUtil.getLong(actionRequest, "orderId");

		String emailType = ParamUtil.getString(actionRequest, "emailType");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		ShoppingOrderServiceUtil.sendEmail(
			themeDisplay.getScopeGroupId(), orderId, emailType, serviceContext);
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

		ShoppingOrderServiceUtil.completeOrder(
			themeDisplay.getScopeGroupId(), number, ppTxnId, ppPaymentStatus,
			ppPaymentGross, ppReceiverEmail, ppPayerEmail, serviceContext);
	}

}
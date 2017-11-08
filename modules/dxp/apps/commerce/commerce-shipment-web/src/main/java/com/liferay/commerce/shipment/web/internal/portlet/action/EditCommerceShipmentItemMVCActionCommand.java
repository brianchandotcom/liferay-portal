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
import com.liferay.commerce.exception.CommerceShipmentItemQuantityException;
import com.liferay.commerce.exception.NoSuchShipmentItemException;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceOrderItemLocalService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
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

	protected void addCommerceShipmentItems(ActionRequest actionRequest)
		throws Exception {

		long[] addCommerceOrderItemIds = null;

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");
		long commerceOrderItemId = ParamUtil.getLong(
			actionRequest, "commerceOrderItemId");

		if (commerceOrderItemId > 0) {
			addCommerceOrderItemIds = new long[] {commerceOrderItemId};
		}
		else {
			addCommerceOrderItemIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "commerceOrderItemIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceShipmentItem.class.getName(), actionRequest);

		for (long addCommerceOrderItemId : addCommerceOrderItemIds) {
			CommerceOrderItem commerceOrderItem =
				_commerceOrderItemLocalService.getCommerceOrderItem(
					addCommerceOrderItemId);

			int availableQuantity =
				commerceOrderItem.getQuantity() -
					commerceOrderItem.getShippedQuantity();

			_commerceShipmentItemService.addCommerceShipmentItem(
				commerceShipmentId, addCommerceOrderItemId, availableQuantity,
				serviceContext);
		}
	}

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

		for (long deleteCommerceShipmentItemId :
				deleteCommerceShipmentItemIds) {

			_commerceShipmentItemService.deleteCommerceShipmentItem(
				deleteCommerceShipmentItemId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				addCommerceShipmentItems(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceShipmentItems(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommerceShipmentItem(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof CommerceShipmentItemQuantityException) {
				SessionErrors.add(actionRequest, e.getClass());

				String redirect = getSaveAndContinueRedirect(actionRequest);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (e instanceof NoSuchShipmentItemException ||
					 e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
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

		return portletURL.toString();
	}

	protected CommerceShipmentItem updateCommerceShipmentItem(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceShipmentItemId = ParamUtil.getLong(
			actionRequest, "commerceShipmentItemId");

		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

		return _commerceShipmentItemService.updateCommerceShipmentItem(
			commerceShipmentItemId, quantity);
	}

	@Reference
	private CommerceOrderItemLocalService _commerceOrderItemLocalService;

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference
	private Portal _portal;

}
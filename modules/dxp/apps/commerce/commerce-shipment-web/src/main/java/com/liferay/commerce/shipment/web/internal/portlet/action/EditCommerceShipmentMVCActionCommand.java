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
import com.liferay.commerce.exception.CommerceShipmentStatusException;
import com.liferay.commerce.exception.NoSuchShipmentException;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.service.CommerceShipmentService;
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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;

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
		"mvc.command.name=editCommerceShipment"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShipmentMVCActionCommand extends BaseMVCActionCommand {

	protected void addCommerceShipmentItems(
			ActionRequest actionRequest, long commerceShipmentId)
		throws PortalException {

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceShipmentItem.class.getName(), actionRequest);

		long[] commerceOrderItemIds = ParamUtil.getLongValues(
			actionRequest, "commerceOrderItemId");

		for (long commerceOrderItemId : commerceOrderItemIds) {
			long[] commerceWarehouseIds = ParamUtil.getLongValues(
				actionRequest, commerceOrderItemId + "_warehouse");

			for (long commerceWarehouseId : commerceWarehouseIds) {
				int quantity = ParamUtil.getInteger(
					actionRequest,
					commerceOrderItemId + "_" + commerceWarehouseId +
						"_quantity");

				if (quantity > 0) {
					_commerceShipmentItemService.addCommerceShipmentItem(
						commerceShipmentId, commerceOrderItemId,
						commerceWarehouseId, quantity, serviceContext);
				}
			}
		}
	}

	protected void deleteCommerceShipments(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceShipmentIds = null;

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		if (commerceShipmentId > 0) {
			deleteCommerceShipmentIds = new long[] {commerceShipmentId};
		}
		else {
			deleteCommerceShipmentIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceShipmentIds"),
				0L);
		}

		for (long deleteCommerceShipmentId : deleteCommerceShipmentIds) {
			_commerceShipmentService.deleteCommerceShipment(
				deleteCommerceShipmentId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				hideDefaultSuccessMessage(actionRequest);

				String redirect = getSaveAndRedirectRedirect(actionRequest);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceShipments(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommerceShipment(actionRequest);
			}
			else if (cmd.equals("selectCommerceShipmentItems")) {
				selectCommerceShipmentItems(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof CommerceShipmentItemQuantityException) {
				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "selectCommerceShipmentItems");
			}
			else if (e instanceof CommerceShipmentStatusException) {
				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "viewCommerceShipmentDetail");
			}
			else if (e instanceof NoSuchShipmentException ||
					 e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected String getSaveAndRedirectRedirect(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, themeDisplay.getScopeGroup(),
			CommerceShipment.class.getName(), PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "selectCommerceShipmentItems");

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		portletURL.setParameter(
			"commerceOrderId", String.valueOf(commerceOrderId));

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		portletURL.setParameter(
			"commerceShipmentId", String.valueOf(commerceShipmentId));

		return portletURL.toString();
	}

	protected void selectCommerceShipmentItems(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		CommerceShipment commerceShipment = null;

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		if (commerceShipmentId > 0) {
			commerceShipment = _commerceShipmentService.getCommerceShipment(
				commerceShipmentId);
		}
		else {
			commerceShipment = updateCommerceShipment(actionRequest);
		}

		if (commerceShipment != null) {
			addCommerceShipmentItems(
				actionRequest, commerceShipment.getCommerceShipmentId());
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, themeDisplay.getScopeGroup(),
			CommerceShipment.class.getName(), PortletProvider.Action.VIEW);

		if (commerceShipment != null) {
			portletURL.setParameter(
				"mvcRenderCommandName", "viewCommerceShipmentDetail");
			portletURL.setParameter(
				"commerceShipmentId",
				String.valueOf(commerceShipment.getCommerceShipmentId()));
		}

		sendRedirect(actionRequest, actionResponse, portletURL.toString());
	}

	protected CommerceShipment updateCommerceShipment(
			ActionRequest actionRequest)
		throws Exception {

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		String carrier = ParamUtil.getString(actionRequest, "carrier");
		String trackingNumber = ParamUtil.getString(
			actionRequest, "trackingNumber");
		int status = ParamUtil.getInteger(actionRequest, "status");

		int shippingDateMonth = ParamUtil.getInteger(
			actionRequest, "shippingDateMonth");
		int shippingDateDay = ParamUtil.getInteger(
			actionRequest, "shippingDateDay");
		int shippingDateYear = ParamUtil.getInteger(
			actionRequest, "shippingDateYear");
		int shippingDateHour = ParamUtil.getInteger(
			actionRequest, "shippingDateHour");
		int shippingDateMinute = ParamUtil.getInteger(
			actionRequest, "shippingDateMinute");
		int shippingDateAmPm = ParamUtil.getInteger(
			actionRequest, "shippingDateAmPm");

		if (shippingDateAmPm == Calendar.PM) {
			shippingDateHour += 12;
		}

		int expectedDateMonth = ParamUtil.getInteger(
			actionRequest, "expectedDateMonth");
		int expectedDateDay = ParamUtil.getInteger(
			actionRequest, "expectedDateDay");
		int expectedDateYear = ParamUtil.getInteger(
			actionRequest, "expectedDateYear");
		int expectedDateHour = ParamUtil.getInteger(
			actionRequest, "expectedDateHour");
		int expectedDateMinute = ParamUtil.getInteger(
			actionRequest, "expectedDateMinute");
		int expectedDateAmPm = ParamUtil.getInteger(
			actionRequest, "expectedDateAmPm");

		if (expectedDateAmPm == Calendar.PM) {
			expectedDateHour += 12;
		}

		CommerceShipment commerceShipment = null;

		if (commerceShipmentId > 0) {
			commerceShipment = _commerceShipmentService.updateCommerceShipment(
				commerceShipmentId, carrier, trackingNumber, status,
				shippingDateMonth, shippingDateDay, shippingDateYear,
				shippingDateHour, shippingDateMinute, expectedDateMonth,
				expectedDateDay, expectedDateYear, expectedDateHour,
				expectedDateMinute);
		}
		else {
			long commerceOrderId = ParamUtil.getLong(
				actionRequest, "commerceOrderId");

			if (commerceOrderId > 0) {
				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(
						CommerceShipment.class.getName(), actionRequest);

				commerceShipment = _commerceShipmentService.addCommerceShipment(
					commerceOrderId, serviceContext);
			}
		}

		return commerceShipment;
	}

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference
	private CommerceShipmentService _commerceShipmentService;

}
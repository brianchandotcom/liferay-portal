/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shipment.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.NoSuchShipmentException;
import com.liferay.commerce.model.CommerceShipment;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
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
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CommerceShipment commerceShipment = updateCommerceShipment(
					actionRequest);

				String redirect = getSaveAndRedirectRedirect(
					actionRequest, commerceShipment);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceShipments(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchShipmentException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected String getSaveAndRedirectRedirect(
			ActionRequest actionRequest, CommerceShipment commerceShipment)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, themeDisplay.getScopeGroup(),
			CommerceShipment.class.getName(), PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceShipmentDetail");
		portletURL.setParameter(
			"commerceShipmentId",
			String.valueOf(commerceShipment.getCommerceShipmentId()));

		return portletURL.toString();
	}

	protected CommerceShipment updateCommerceShipment(
			ActionRequest actionRequest)
		throws Exception {

		long commerceShipmentId = ParamUtil.getLong(
			actionRequest, "commerceShipmentId");

		long shipmentUserId = ParamUtil.getLong(
			actionRequest, "shipmentUserId");
		long commerceAddressId = ParamUtil.getLong(
			actionRequest, "commerceAddressId");
		long commerceShippingMethodId = ParamUtil.getLong(
			actionRequest, "commerceShippingMethodId");
		long commerceWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceWarehouseId");

		String carrier = ParamUtil.getString(actionRequest, "carrier");
		String trackingNumber = ParamUtil.getString(
			actionRequest, "trackingNumber");
		int expectedDuration = ParamUtil.getInteger(
			actionRequest, "expectedDuration");

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

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceShipment.class.getName(), actionRequest);

		CommerceShipment commerceShipment = null;

		if (commerceShipmentId <= 0) {
			commerceShipment = _commerceShipmentService.addCommerceShipment(
				shipmentUserId, commerceAddressId, commerceShippingMethodId,
				commerceWarehouseId, carrier, trackingNumber, expectedDuration,
				0, shippingDateMonth, shippingDateDay, shippingDateYear,
				shippingDateHour, shippingDateMinute, expectedDateMonth,
				expectedDateDay, expectedDateYear, expectedDateHour,
				expectedDateMinute, serviceContext);
		}
		else {
			commerceShipment = _commerceShipmentService.updateCommerceShipment(
				commerceShipmentId, shipmentUserId, commerceAddressId,
				commerceShippingMethodId, carrier, trackingNumber,
				expectedDuration, 0, shippingDateMonth, shippingDateDay,
				shippingDateYear, shippingDateHour, shippingDateMinute,
				expectedDateMonth, expectedDateDay, expectedDateYear,
				expectedDateHour, expectedDateMinute);
		}

		return commerceShipment;
	}

	@Reference
	private CommerceShipmentService _commerceShipmentService;

	@Reference
	private Portal _portal;

}
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

package com.liferay.commerce.checkout.web.internal.util;

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.checkout.web.internal.display.context.BaseAddressCheckoutStepDisplayContext;
import com.liferay.commerce.checkout.web.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.exception.CommerceAddressCityException;
import com.liferay.commerce.exception.CommerceAddressCountryException;
import com.liferay.commerce.exception.CommerceAddressNameException;
import com.liferay.commerce.exception.CommerceAddressStreetException;
import com.liferay.commerce.exception.CommerceOrderBillingAddressException;
import com.liferay.commerce.exception.CommerceOrderShippingAddressException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.service.CommerceAddressService;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseAddressCommerceCheckoutStep
	extends BaseCommerceCheckoutStep {

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updateCommerceOrderAddress(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof CommerceAddressCityException ||
				e instanceof CommerceAddressCountryException ||
				e instanceof CommerceAddressNameException ||
				e instanceof CommerceAddressStreetException ||
				e instanceof CommerceOrderBillingAddressException ||
				e instanceof CommerceOrderShippingAddressException) {

				SessionErrors.add(actionRequest, e.getClass());

				return;
			}

			throw e;
		}
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
			getBaseAddressCheckoutStepDisplayContext(
				httpServletRequest, httpServletResponse));

		jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/checkout_step/address.jsp");
	}

	protected CommerceAddress addCommerceAddress(
			CommerceOrder commerceOrder, ActionRequest actionRequest)
		throws PortalException {

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

		serviceContext.setScopeGroupId(commerceOrder.getGroupId());

		return commerceAddressService.addCommerceAddress(
			commerceOrder.getClassName(), commerceOrder.getClassPK(), name,
			description, street1, street2, street3, city, zip, commerceRegionId,
			commerceCountryId, phoneNumber, false, false, serviceContext);
	}

	protected abstract BaseAddressCheckoutStepDisplayContext
			getBaseAddressCheckoutStepDisplayContext(
				HttpServletRequest httpServletRequest,
				HttpServletResponse httpServletResponse)
		throws PortalException;

	protected abstract String getParamName();

	protected void updateCommerceOrderAddress(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = commerceOrderService.getCommerceOrder(
			commerceOrderId);

		boolean newAddress = ParamUtil.getBoolean(actionRequest, "newAddress");

		long commerceAddressId = ParamUtil.getLong(
			actionRequest, getParamName());

		if (newAddress) {
			CommerceAddress commerceAddress = addCommerceAddress(
				commerceOrder, actionRequest);

			commerceAddressId = commerceAddress.getCommerceAddressId();
		}

		commerceOrderModelResourcePermission.check(
			themeDisplay.getPermissionChecker(), commerceOrder,
			CommerceOrderActionKeys.CHECKOUT_COMMERCE_ORDER);

		updateCommerceOrderAddress(commerceOrder, commerceAddressId);
	}

	protected abstract void updateCommerceOrderAddress(
			CommerceOrder commerceOrder, long commerceAddressId)
		throws Exception;

	@Reference
	protected CommerceAddressService commerceAddressService;

	@Reference
	protected CommerceOrderLocalService commerceOrderLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.model.CommerceOrder)"
	)
	protected ModelResourcePermission<CommerceOrder>
		commerceOrderModelResourcePermission;

	@Reference
	protected CommerceOrderService commerceOrderService;

	@Reference
	protected JSPRenderer jspRenderer;

}
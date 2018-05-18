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

package com.liferay.commerce.payment.engine.purchase.order.internal.util;

import com.liferay.commerce.checkout.web.constants.CommerceCheckoutWebKeys;
import com.liferay.commerce.checkout.web.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.exception.CommerceOrderPurchaseOrderNumberException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommercePaymentMethod;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.commerce.payment.engine.purchase.order.internal.PurchaseOrderCommercePaymentEngine;
import com.liferay.commerce.payment.engine.purchase.order.internal.display.context.PurchaseOrderCheckoutStepDisplayContext;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommercePaymentMethodService;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"commerce.checkout.step.name=" + PurchaseOrderCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=" + (Integer.MAX_VALUE - 1)
	},
	service = CommerceCheckoutStep.class
)
public class PurchaseOrderCommerceCheckoutStep
	extends BaseCommerceCheckoutStep {

	public static final String NAME = "purchase-order";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isActive(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		long commercePaymentMethodId = 0;

		long commerceOrderId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderId");

		if (commerceOrderId > 0) {
			CommerceOrder commerceOrder =
				_commerceOrderService.getCommerceOrder(commerceOrderId);

			commercePaymentMethodId =
				commerceOrder.getCommercePaymentMethodId();
		}
		else {
			CommerceOrder commerceOrder =
				_commerceOrderHttpHelper.getCurrentCommerceOrder(
					httpServletRequest);

			commercePaymentMethodId =
				commerceOrder.getCommercePaymentMethodId();
		}

		if (commercePaymentMethodId <= 0) {
			return false;
		}

		CommercePaymentMethod commercePaymentMethod =
			_commercePaymentMethodService.getCommercePaymentMethod(
				commercePaymentMethodId);

		if (PurchaseOrderCommercePaymentEngine.KEY.equals(
				commercePaymentMethod.getEngineKey())) {

			return true;
		}

		return false;
	}

	@Override
	public boolean isOrder() {
		return true;
	}

	@Override
	public boolean isVisible(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		return isActive(httpServletRequest, httpServletResponse);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updatePurchaseOrderNumber(actionRequest);
		}
		catch (CommerceOrderPurchaseOrderNumberException copone) {
			SessionErrors.add(actionRequest, copone.getClass());

			return;
		}
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		PurchaseOrderCheckoutStepDisplayContext
			purchaseOrderCheckoutStepDisplayContext =
				new PurchaseOrderCheckoutStepDisplayContext(
					_commerceOrderService, _configurationProvider,
					httpServletRequest);

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
			purchaseOrderCheckoutStepDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/checkout_step/purchase_order.jsp");
	}

	protected void updatePurchaseOrderNumber(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		String purchaseOrderNumber = ParamUtil.getString(
			actionRequest, "purchaseOrderNumber");

		_commerceOrderService.updatePurchaseOrderNumber(
			commerceOrderId, purchaseOrderNumber);
	}

	@Reference
	private CommerceOrderHttpHelper _commerceOrderHttpHelper;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePaymentMethodService _commercePaymentMethodService;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.payment.engine.purchase.order)"
	)
	private ServletContext _servletContext;

}
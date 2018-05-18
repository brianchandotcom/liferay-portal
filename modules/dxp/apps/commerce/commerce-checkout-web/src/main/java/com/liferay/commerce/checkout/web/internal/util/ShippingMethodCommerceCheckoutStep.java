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
import com.liferay.commerce.checkout.web.internal.display.context.ShippingMethodCheckoutStepDisplayContext;
import com.liferay.commerce.checkout.web.util.BaseCommerceCheckoutStep;
import com.liferay.commerce.checkout.web.util.CommerceCheckoutStep;
import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.exception.CommerceOrderShippingMethodException;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.model.CommerceShippingEngine;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.model.CommerceShippingOption;
import com.liferay.commerce.order.web.security.permission.resource.CommerceOrderPermission;
import com.liferay.commerce.service.CommerceOrderLocalService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.util.CommerceShippingEngineRegistry;
import com.liferay.commerce.util.CommerceShippingHelper;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.math.BigDecimal;

import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

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
		"commerce.checkout.step.name=" + ShippingMethodCommerceCheckoutStep.NAME,
		"commerce.checkout.step.order:Integer=30"
	},
	service = CommerceCheckoutStep.class
)
public class ShippingMethodCommerceCheckoutStep
	extends BaseCommerceCheckoutStep {

	public static final char COMMERCE_SHIPPING_OPTION_KEY_SEPARATOR =
		CharPool.POUND;

	public static final String NAME = "shipping-method";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public boolean isActive(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		CommerceOrder commerceOrder =
			(CommerceOrder)httpServletRequest.getAttribute(
				CommerceCheckoutWebKeys.COMMERCE_ORDER);

		if (!_commerceShippingHelper.isShippable(commerceOrder)) {
			return false;
		}

		if (_commerceShippingMethodService.getCommerceShippingMethodsCount(
				commerceOrder.getSiteGroupId(), true) > 0) {

			return true;
		}

		return false;
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			updateCommerceOrderShippingMethod(actionRequest);
		}
		catch (Exception e) {
			if (e instanceof CommerceOrderShippingMethodException) {
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

		ShippingMethodCheckoutStepDisplayContext
			shippingMethodCheckoutStepDisplayContext =
				new ShippingMethodCheckoutStepDisplayContext(
					_commercePriceFormatter, _commerceShippingEngineRegistry,
					_commerceShippingMethodService, httpServletRequest,
					httpServletResponse);

		httpServletRequest.setAttribute(
			CommerceCheckoutWebKeys.COMMERCE_CHECKOUT_STEP_DISPLAY_CONTEXT,
			shippingMethodCheckoutStepDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse,
			"/checkout_step/shipping_method.jsp");
	}

	protected BigDecimal getShippingPrice(
			CommerceContext commerceContext, CommerceOrder commerceOrder,
			long commerceShippingMethodId, String shippingOptionName,
			Locale locale)
		throws PortalException {

		CommerceShippingMethod commerceShippingMethod =
			_commerceShippingMethodService.getCommerceShippingMethod(
				commerceShippingMethodId);

		if (!commerceShippingMethod.isActive()) {
			throw new CommerceOrderShippingMethodException(
				"Shipping method " +
					commerceShippingMethod.getCommerceShippingMethodId() +
						" is not active");
		}

		CommerceShippingEngine commerceShippingEngine =
			_commerceShippingEngineRegistry.getCommerceShippingEngine(
				commerceShippingMethod.getEngineKey());

		List<CommerceShippingOption> commerceShippingOptions =
			commerceShippingEngine.getCommerceShippingOptions(
				commerceContext, commerceOrder, locale);

		for (CommerceShippingOption commerceShippingOption :
				commerceShippingOptions) {

			if (shippingOptionName.equals(commerceShippingOption.getName())) {
				return commerceShippingOption.getAmount();
			}
		}

		throw new CommerceOrderShippingMethodException(
			"Unable to get amount of option \"" + shippingOptionName +
				"\" for shipping method " + commerceShippingMethodId);
	}

	protected void updateCommerceOrderShippingMethod(
			ActionRequest actionRequest)
		throws Exception {

		String commerceShippingOptionKey = ParamUtil.getString(
			actionRequest, "commerceShippingOptionKey");

		if (Validator.isNull(commerceShippingOptionKey)) {
			throw new CommerceOrderShippingMethodException();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CommerceContext commerceContext =
			(CommerceContext)actionRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");

		CommerceOrder commerceOrder = _commerceOrderService.getCommerceOrder(
			commerceOrderId);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(themeDisplay.getUser());

		if (!CommerceOrderPermission.contains(
				permissionChecker, commerceOrder,
				CommerceOrderActionKeys.CHECKOUT_OPEN_COMMERCE_ORDERS)) {

			return;
		}

		int pos = commerceShippingOptionKey.indexOf(
			COMMERCE_SHIPPING_OPTION_KEY_SEPARATOR);

		long commerceShippingMethodId = GetterUtil.getLong(
			commerceShippingOptionKey.substring(0, pos));
		String shippingOptionName = commerceShippingOptionKey.substring(
			pos + 1);

		BigDecimal shippingPrice = getShippingPrice(
			commerceContext, commerceOrder, commerceShippingMethodId,
			shippingOptionName, themeDisplay.getLocale());

		_commerceOrderLocalService.updateCommerceOrder(
			commerceOrder.getCommerceOrderId(),
			commerceOrder.getBillingAddressId(),
			commerceOrder.getShippingAddressId(),
			commerceOrder.getCommercePaymentMethodId(),
			commerceShippingMethodId, shippingOptionName,
			commerceOrder.getPurchaseOrderNumber(), commerceOrder.getSubtotal(),
			shippingPrice, commerceOrder.getTotal(),
			commerceOrder.getAdvanceStatus());
	}

	@Reference
	private CommerceOrderLocalService _commerceOrderLocalService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CommerceShippingEngineRegistry _commerceShippingEngineRegistry;

	@Reference
	private CommerceShippingHelper _commerceShippingHelper;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private JSPRenderer _jspRenderer;

}
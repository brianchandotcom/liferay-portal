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

package com.liferay.commerce.cart.content.web.internal.display.context;

import com.liferay.commerce.cart.content.web.internal.portlet.configuration.CommerceCartContentTotalPortletInstanceConfiguration;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.order.CommerceOrderValidatorRegistry;
import com.liferay.commerce.price.CommercePriceCalculation;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCartContentTotalDisplayContext
	extends CommerceCartContentDisplayContext {

	public CommerceCartContentTotalDisplayContext(
			HttpServletRequest httpServletRequest,
			CommerceOrderItemService commerceOrderItemService,
			CommerceOrderValidatorRegistry commerceOrderValidatorRegistry,
			CommercePriceCalculation commercePriceCalculation,
			CPDefinitionHelper cpDefinitionHelper,
			CPInstanceHelper cpInstanceHelper, Portal portal)
		throws PortalException {

		super(
			httpServletRequest, commerceOrderItemService,
			commerceOrderValidatorRegistry, commercePriceCalculation,
			cpDefinitionHelper, cpInstanceHelper);

		_portal = portal;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_commerceCartContentTotalPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CommerceCartContentTotalPortletInstanceConfiguration.class);
	}

	public PortletURL getCheckoutPortletURL() throws PortalException {
		long plid = _portal.getPlidFromPortletId(
			commerceCartContentRequestHelper.getScopeGroupId(),
			CommercePortletKeys.COMMERCE_CHECKOUT);

		return PortletURLFactoryUtil.create(
			commerceCartContentRequestHelper.getRequest(),
			CommercePortletKeys.COMMERCE_CHECKOUT, plid,
			PortletRequest.RENDER_PHASE);
	}

	public String getDisplayStyle() {
		return _commerceCartContentTotalPortletInstanceConfiguration.
			displayStyle();
	}

	public long getDisplayStyleGroupId() {
		if (_displayStyleGroupId > 0) {
			return _displayStyleGroupId;
		}

		_displayStyleGroupId =
			_commerceCartContentTotalPortletInstanceConfiguration.
				displayStyleGroupId();

		if (_displayStyleGroupId <= 0) {
			_displayStyleGroupId =
				commerceCartContentRequestHelper.getScopeGroupId();
		}

		return _displayStyleGroupId;
	}

	private final CommerceCartContentTotalPortletInstanceConfiguration
		_commerceCartContentTotalPortletInstanceConfiguration;
	private long _displayStyleGroupId;
	private final Portal _portal;

}
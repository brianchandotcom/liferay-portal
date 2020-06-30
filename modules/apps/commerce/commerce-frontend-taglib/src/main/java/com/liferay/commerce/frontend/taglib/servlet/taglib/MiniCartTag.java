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

package com.liferay.commerce.frontend.taglib.servlet.taglib;

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.configuration.CommercePriceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.commerce.order.CommerceOrderHttpHelper;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;

import javax.portlet.PortletURL;

import javax.servlet.jsp.PageContext;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class MiniCartTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		CommerceContext commerceContext = (CommerceContext)request.getAttribute(
			CommerceWebKeys.COMMERCE_CONTEXT);
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			putValue(
				"cartAPI",
				PortalUtil.getPortalURL(request) + "/o/commerce-ui/cart");

			CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

			if (commerceOrder != null) {
				putValue("orderId", commerceOrder.getCommerceOrderId());
				putValue(
					"commerceAccountId", commerceOrder.getCommerceAccountId());
				putValue("workflowStatus", commerceOrder.getStatus());
			}
			else {
				CommerceAccount commerceAccount =
					commerceContext.getCommerceAccount();

				if (commerceAccount != null) {
					putValue(
						"commerceAccountId",
						commerceAccount.getCommerceAccountId());
				}
			}

			String checkoutURL = StringPool.BLANK;

			PortletURL commerceCheckoutPortletURL =
				_commerceOrderHttpHelper.getCommerceCheckoutPortletURL(request);

			if (commerceCheckoutPortletURL != null) {
				checkoutURL = String.valueOf(commerceCheckoutPortletURL);
			}

			putValue("checkoutUrl", checkoutURL);

			String detailsURL = StringPool.BLANK;

			PortletURL commerceCartPortletURL =
				_commerceOrderHttpHelper.getCommerceCartPortletURL(
					request, commerceOrder);

			if (commerceCartPortletURL != null) {
				detailsURL = String.valueOf(commerceCartPortletURL);
			}

			CommercePriceConfiguration commercePriceConfiguration =
				_configurationProvider.getConfiguration(
					CommercePriceConfiguration.class,
					new SystemSettingsLocator(
						CommerceConstants.PRICE_SERVICE_NAME));

			putValue(
				"displayDiscountLevels",
				commercePriceConfiguration.displayDiscountLevels());

			putValue("detailsUrl", detailsURL);

			putValue("isDisabled", false);
			putValue("isOpen", false);
			putValue("products", Collections.emptyList());
			putValue("productsCount", 0);
			putValue(
				"spritemap", themeDisplay.getPathThemeImages() + "/icons.svg");

			setTemplateNamespace("MiniCart.render");
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return SKIP_BODY;
		}

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		NPMResolver npmResolver = ServletContextUtil.getNPMResolver();

		if (npmResolver == null) {
			return StringPool.BLANK;
		}

		return npmResolver.resolveModuleName(
			"commerce-frontend-taglib/mini_cart/MiniCart.es");
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		_commerceOrderHttpHelper =
			ServletContextUtil.getCommerceOrderHttpHelper();

		_configurationProvider = ServletContextUtil.getConfigurationProvider();

		super.setPageContext(pageContext);
	}

	private static final Log _log = LogFactoryUtil.getLog(MiniCartTag.class);

	private CommerceOrderHttpHelper _commerceOrderHttpHelper;
	private ConfigurationProvider _configurationProvider;

}
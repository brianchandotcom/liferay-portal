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
import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.frontend.taglib.internal.servlet.ServletContextUtil;
import com.liferay.commerce.frontend.util.ProductHelper;
import com.liferay.commerce.model.CommerceOrder;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.soy.servlet.taglib.ComponentRendererTag;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.servlet.jsp.PageContext;

/**
 * @author Fabio Diego Mastrorilli
 * @author Marco Leo
 */
public class AddToCartTag extends ComponentRendererTag {

	@Override
	public int doStartTag() {
		try {
			Map<String, Object> context = getContext();

			long cpInstanceId = GetterUtil.getLong(context.get("productId"));

			String componentId = (String)context.getOrDefault(
				"id", cpInstanceId + "AddToCartButtonId");

			setComponentId(componentId);

			CommerceContext commerceContext =
				(CommerceContext)request.getAttribute(
					CommerceWebKeys.COMMERCE_CONTEXT);

			CommerceAccount commerceAccount =
				commerceContext.getCommerceAccount();

			if (commerceAccount != null) {
				putValue("accountId", commerceAccount.getCommerceAccountId());
			}

			putValue(
				"cartAPI",
				PortalUtil.getPortalURL(request) + "/o/commerce-ui/cart-item");

			putValue("editMode", false);

			int productOrderQuantity = 0;

			CommerceOrder commerceOrder = commerceContext.getCommerceOrder();

			if (commerceOrder != null) {
				putValue("orderId", commerceOrder.getCommerceOrderId());

				productOrderQuantity = commerceOrder.getCommerceOrderItemsCount(
					cpInstanceId);
			}

			putValue("quantity", productOrderQuantity);

			putValue(
				"settings",
				_productHelper.getProductSettingsModel(cpInstanceId));

			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			putValue(
				"spritemap", themeDisplay.getPathThemeImages() + "/icons.svg");

			setTemplateNamespace("AddToCartButton.render");
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
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
			"commerce-frontend-taglib/add_to_cart/AddToCartButton.es");
	}

	public void setCPInstanceId(long cpInstanceId) {
		putValue("productId", cpInstanceId);
	}

	public void setId(String id) {
		putValue("id", id);
	}

	@Override
	public void setPageContext(PageContext pageContext) {
		super.setPageContext(pageContext);

		_productHelper = ServletContextUtil.getProductHelper();
	}

	public void setTextContent(String textContent) {
		putValue("textContent", textContent);
	}

	private static final Log _log = LogFactoryUtil.getLog(AddToCartTag.class);

	private ProductHelper _productHelper;

}
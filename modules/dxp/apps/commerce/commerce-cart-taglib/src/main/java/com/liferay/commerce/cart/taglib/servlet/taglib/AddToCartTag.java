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

package com.liferay.commerce.cart.taglib.servlet.taglib;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.model.CPDefinitionInventoryConstants;
import com.liferay.commerce.service.CPDefinitionInventoryServiceUtil;
import com.liferay.frontend.taglib.soy.servlet.taglib.TemplateRendererTag;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

/**
 * @author Marco Leo
 */
public class AddToCartTag extends TemplateRendererTag {

	@Override
	public int doStartTag() {
		String randomNamespace = StringUtil.randomId() + StringPool.UNDERLINE;

		int minOrderQuantity =
			CPDefinitionInventoryConstants.DEFAULT_MIN_ORDER_QUANTITY;

		try {
			Map<String, Object> context = getContext();

			putValue("id", randomNamespace + "id");

			long cpDefinitionId = GetterUtil.getLong(
				context.get("cpDefinitionId"));

			CPDefinitionInventory cpDefinitionInventory =
				CPDefinitionInventoryServiceUtil.
					fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);

			if (cpDefinitionInventory != null) {
				minOrderQuantity = cpDefinitionInventory.getMinOrderQuantity();
			}

			String quantity = GetterUtil.getString(
				context.get("quantity"), String.valueOf(minOrderQuantity));

			putValue("quantity", quantity);

			String label = GetterUtil.getString(
				context.get("label"), LanguageUtil.get(request, "add-to-cart"));

			putValue("label", label);

			putValue(
				"portletNamespace",
				PortalUtil.getPortletNamespace(
					CommercePortletKeys.COMMERCE_CART_CONTENT));

			putValue("uri", _getURI());
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}

		setTemplateNamespace("AddToCart.render");

		return super.doStartTag();
	}

	@Override
	public String getModule() {
		return "commerce-cart-taglib/add_to_cart/AddToCart.es";
	}

	public void setCPDefinitionId(long cpDefinitionId) {
		putValue("cpDefinitionId", String.valueOf(cpDefinitionId));
	}

	public void setCPInstanceId(long cpInstanceId) {
		putValue("cpInstanceId", String.valueOf(cpInstanceId));
	}

	public void setElementClasses(String elementClasses) {
		putValue("elementClasses", elementClasses);
	}

	public void setLabel(String label) {
		putValue("label", LanguageUtil.get(request, label));
	}

	public void setProductContentId(String productContentId) {
		putValue("productContentId", productContentId);
	}

	public void setQuantity(String quantity) {
		putValue("quantity", quantity);
	}

	public void setQuantityInputId(String quantityInputId) {
		putValue("quantityInputId", quantityInputId);
	}

	private String _getURI() throws WindowStateException {
		PortletURL portletURL = PortletURLFactoryUtil.create(
			request, CommercePortletKeys.COMMERCE_CART_CONTENT,
			PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "addCommerceCartItem");

		return portletURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(AddToCartTag.class);

}
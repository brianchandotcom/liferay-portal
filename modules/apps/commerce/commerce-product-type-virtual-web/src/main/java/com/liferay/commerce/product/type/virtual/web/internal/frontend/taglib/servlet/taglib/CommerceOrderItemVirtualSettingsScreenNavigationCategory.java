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

package com.liferay.commerce.product.type.virtual.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.type.virtual.order.model.CommerceVirtualOrderItem;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemLocalService;
import com.liferay.commerce.product.type.virtual.web.internal.display.context.CommerceVirtualOrderItemEditDisplayContext;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceOrderService;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"screen.navigation.category.order:Integer=20",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceOrderItemVirtualSettingsScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommerceOrderItem> {

	public static final String
		CATEGORY_KEY_COMMERCE_ORDER_ITEM_VIRTUAL_SETTINGS = "virtual-settings";

	@Override
	public String getCategoryKey() {
		return CATEGORY_KEY_COMMERCE_ORDER_ITEM_VIRTUAL_SETTINGS;
	}

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, getCategoryKey());
	}

	@Override
	public String getScreenNavigationKey() {
		return "commerce.order.item.general";
	}

	@Override
	public boolean isVisible(User user, CommerceOrderItem commerceOrderItem) {
		if (commerceOrderItem == null) {
			return false;
		}

		CommerceVirtualOrderItem commerceVirtualOrderItem =
			_commerceVirtualOrderItemLocalService.
				fetchCommerceVirtualOrderItemByCommerceOrderItemId(
					commerceOrderItem.getCommerceOrderItemId());

		if (commerceVirtualOrderItem == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			RenderRequest renderRequest =
				(RenderRequest)httpServletRequest.getAttribute(
					JavaConstants.JAVAX_PORTLET_REQUEST);

			CommerceVirtualOrderItemEditDisplayContext
				commerceVirtualOrderItemEditDisplayContext =
					new CommerceVirtualOrderItemEditDisplayContext(
						_commerceOrderService, _commerceOrderItemService,
						_getCommerceVirtualOrderItem(httpServletRequest),
						_dlAppService, _itemSelector, renderRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commerceVirtualOrderItemEditDisplayContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/commerce_order_item/virtual_settings.jsp");
	}

	private CommerceVirtualOrderItem _getCommerceVirtualOrderItem(
		HttpServletRequest httpServletRequest) {

		CommerceVirtualOrderItem commerceVirtualOrderItem = null;

		long commerceVirtualOrderItemId = ParamUtil.getLong(
			httpServletRequest, "commerceVirtualOrderItemId");

		if (commerceVirtualOrderItemId > 0) {
			commerceVirtualOrderItem =
				_commerceVirtualOrderItemLocalService.
					fetchCommerceVirtualOrderItem(commerceVirtualOrderItemId);
		}

		if (commerceVirtualOrderItem != null) {
			return commerceVirtualOrderItem;
		}

		long commerceOrderItemId = ParamUtil.getLong(
			httpServletRequest, "commerceOrderItemId");

		if (commerceOrderItemId > 0) {
			commerceVirtualOrderItem =
				_commerceVirtualOrderItemLocalService.
					fetchCommerceVirtualOrderItemByCommerceOrderItemId(
						commerceOrderItemId);
		}

		return commerceVirtualOrderItem;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemVirtualSettingsScreenNavigationCategory.class);

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceOrderService _commerceOrderService;

	@Reference
	private CommerceVirtualOrderItemLocalService
		_commerceVirtualOrderItemLocalService;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.product.type.virtual.web)"
	)
	private ServletContext _servletContext;

}
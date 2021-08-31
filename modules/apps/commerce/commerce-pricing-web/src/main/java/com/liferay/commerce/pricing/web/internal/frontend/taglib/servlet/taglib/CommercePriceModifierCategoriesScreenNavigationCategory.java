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

package com.liferay.commerce.pricing.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.pricing.constants.CommercePriceModifierConstants;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.service.CommercePriceModifierService;
import com.liferay.commerce.pricing.type.CommercePriceModifierTypeRegistry;
import com.liferay.commerce.pricing.web.internal.display.context.CommercePriceListDisplayContext;
import com.liferay.commerce.pricing.web.internal.servlet.taglib.ui.constants.CommercePriceListScreenNavigationConstants;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

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
public class CommercePriceModifierCategoriesScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommercePriceModifier> {

	@Override
	public String getCategoryKey() {
		return CommercePriceListScreenNavigationConstants.
			CATEGORY_KEY_CATEGORIES;
	}

	@Override
	public String getEntryKey() {
		return CommercePriceListScreenNavigationConstants.
			CATEGORY_KEY_CATEGORIES;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(
			locale,
			CommercePriceListScreenNavigationConstants.CATEGORY_KEY_CATEGORIES);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommercePriceListScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_PRICE_MODIFIER_GENERAL;
	}

	@Override
	public boolean isVisible(
		User user, CommercePriceModifier commercePriceModifier) {

		if ((commercePriceModifier == null) ||
			!CommercePriceModifierConstants.TARGET_CATEGORIES.equals(
				commercePriceModifier.getTarget())) {

			return false;
		}

		boolean hasPermission = false;

		try {
			hasPermission = _commercePriceListModelResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(),
				commercePriceModifier.getCommercePriceListId(),
				ActionKeys.UPDATE);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return hasPermission;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			CommercePriceListDisplayContext commercePriceListDisplayContext =
				new CommercePriceListDisplayContext(
					_commerceCatalogService, _commerceCurrencyService,
					_commercePriceListModelResourcePermission,
					_commercePriceListService, _commercePriceModifierService,
					_commercePriceModifierTypeRegistry, httpServletRequest);

			httpServletRequest.setAttribute(
				WebKeys.PORTLET_DISPLAY_CONTEXT,
				commercePriceListDisplayContext);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		_jspRenderer.renderJSP(
			_setServletContext, httpServletRequest, httpServletResponse,
			"/commerce_price_lists/commerce_price_modifier/categories.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceModifierCategoriesScreenNavigationCategory.class);

	@Reference
	private CommerceCatalogService _commerceCatalogService;

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceList)"
	)
	private ModelResourcePermission<CommercePriceList>
		_commercePriceListModelResourcePermission;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private CommercePriceModifierService _commercePriceModifierService;

	@Reference
	private CommercePriceModifierTypeRegistry
		_commercePriceModifierTypeRegistry;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.pricing.web)"
	)
	private ServletContext _setServletContext;

}
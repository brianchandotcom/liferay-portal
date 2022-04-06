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

package com.liferay.commerce.account.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.account.constants.AccountConstants;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryService;
import com.liferay.commerce.account.constants.CommerceAccountWebKeys;
import com.liferay.commerce.account.web.internal.constants.AccountEntryScreenNavigationEntryConstants;
import com.liferay.commerce.account.web.internal.display.context.CommerceAccountDisplayContext;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.service.CommerceShippingOptionAccountEntryRelService;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"screen.navigation.category.order:Integer=70",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class AccountEntryCommerceOrderDefaultsScreenNavigationCategory
	implements ScreenNavigationCategory, ScreenNavigationEntry<AccountEntry> {

	@Override
	public String getCategoryKey() {
		return AccountEntryScreenNavigationEntryConstants.
			CATEGORY_KEY_COMMERCE_ORDER_DEFAULTS;
	}

	@Override
	public String getEntryKey() {
		return AccountEntryScreenNavigationEntryConstants.
			CATEGORY_KEY_COMMERCE_ORDER_DEFAULTS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "order-defaults");
	}

	@Override
	public String getScreenNavigationKey() {
		return AccountEntryScreenNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_ACCOUNT_ENTRY;
	}

	@Override
	public boolean isVisible(User user, AccountEntry accountEntry) {
		if ((accountEntry == null) ||
			!Objects.equals(
				accountEntry.getType(),
				AccountConstants.ACCOUNT_ENTRY_TYPE_BUSINESS)) {

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
			CommerceAccountDisplayContext commerceAccountDisplayContext =
				new CommerceAccountDisplayContext(
					_accountEntryService, _commerceChannelService,
					_commerceShippingFixedOptionService,
					_commerceShippingMethodService,
					_commerceShippingOptionAccountEntryRelService,
					httpServletRequest, _portal);

			httpServletRequest.setAttribute(
				CommerceAccountWebKeys.COMMERCE_ACCOUNT_DISPLAY_CONTEXT,
				commerceAccountDisplayContext);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/account_entry/commerce_order_defaults.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AccountEntryCommerceOrderDefaultsScreenNavigationCategory.class);

	@Reference
	private AccountEntryService _accountEntryService;

	@Reference
	private CommerceChannelService _commerceChannelService;

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private CommerceShippingOptionAccountEntryRelService
		_commerceShippingOptionAccountEntryRelService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.account.web)"
	)
	private ServletContext _servletContext;

}
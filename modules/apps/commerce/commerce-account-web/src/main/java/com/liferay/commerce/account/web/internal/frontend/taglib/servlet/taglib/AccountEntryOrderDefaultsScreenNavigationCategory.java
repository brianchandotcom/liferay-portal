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

import com.liferay.commerce.account.web.internal.constants.AccountEntryScreenNavigationEntryConstants;
import com.liferay.commerce.account.web.internal.display.AccountEntryDisplay;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	property = {
		"screen.navigation.category.order:Integer=70",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class AccountEntryOrderDefaultsScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<AccountEntryDisplay> {

	@Override
	public String getCategoryKey() {
		return AccountEntryScreenNavigationEntryConstants.
			CATEGORY_KEY_ORDER_DEFAULTS;
	}

	@Override
	public String getEntryKey() {
		return AccountEntryScreenNavigationEntryConstants.
			CATEGORY_KEY_ORDER_DEFAULTS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			AccountEntryScreenNavigationEntryConstants.
				CATEGORY_KEY_ORDER_DEFAULTS);
	}

	@Override
	public String getScreenNavigationKey() {
		return AccountEntryScreenNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_ACCOUNT_ENTRY;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/account_entry/order_defaults.jsp");
	}

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.account.web)"
	)
	private ServletContext _servletContext;

}
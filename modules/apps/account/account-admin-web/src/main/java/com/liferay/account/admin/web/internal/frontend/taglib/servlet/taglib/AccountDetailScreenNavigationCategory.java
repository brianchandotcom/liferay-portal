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

package com.liferay.account.admin.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.account.admin.web.internal.constants.AccountScreenNavigationEntryConstants;
import com.liferay.account.admin.web.internal.constants.AccountWebKeys;
import com.liferay.account.admin.web.internal.display.AccountDisplay;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Albert Lee
 */
@Component(
	property = {
		"screen.navigation.category.order:Integer=10",
		"screen.navigation.entry.order:Integer=10"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class AccountDetailScreenNavigationCategory
	implements ScreenNavigationCategory, ScreenNavigationEntry {

	public String getCategoryKey() {
		return AccountScreenNavigationEntryConstants.CATEGORY_KEY_DETAIL;
	}

	public String getEntryKey() {
		return AccountScreenNavigationEntryConstants.ENTRY_KEY_DETAIL;
	}

	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "detail");
	}

	public String getScreenNavigationKey() {
		return AccountScreenNavigationEntryConstants.
			SCREEN_NAVIGATION_KEY_ACCOUNT;
	}

	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		long accountEntryId = ParamUtil.getLong(
			httpServletRequest, "accountEntryId");

		AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(
			accountEntryId);

		AccountDisplay accountDisplay = null;

		if (accountEntry != null) {
			accountDisplay = AccountDisplay.of(accountEntry);
		}

		httpServletRequest.setAttribute(
			AccountWebKeys.ACCOUNT_DISPLAY, accountDisplay);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse, "/account/detail.jsp");
	}

	@Reference
	private AccountEntryLocalService _accountEntryLocalService;

	@Reference
	private JSPRenderer _jspRenderer;

}
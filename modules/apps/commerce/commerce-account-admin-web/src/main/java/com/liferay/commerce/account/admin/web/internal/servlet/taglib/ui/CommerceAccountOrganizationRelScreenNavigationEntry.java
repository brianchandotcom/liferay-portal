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

package com.liferay.commerce.account.admin.web.internal.servlet.taglib.ui;

import com.liferay.commerce.account.admin.web.internal.display.context.CommerceAccountOrganizationRelAdminDisplayContext;
import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.account.service.CommerceAccountOrganizationRelService;
import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.item.selector.ItemSelector;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.JavaConstants;
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
	property = "screen.navigation.entry.order:Integer=30",
	service = ScreenNavigationEntry.class
)
public class CommerceAccountOrganizationRelScreenNavigationEntry
	implements ScreenNavigationEntry<CommerceAccount> {

	@Override
	public String getCategoryKey() {
		return CommerceAccountScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_ACCOUNT_DETAIL;
	}

	@Override
	public String getEntryKey() {
		return CommerceAccountScreenNavigationConstants.
			ENTRY_KEY_COMMERCE_ACCOUNT_ORGANIZATIONS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(
			resourceBundle,
			CommerceAccountScreenNavigationConstants.
				ENTRY_KEY_COMMERCE_ACCOUNT_ORGANIZATIONS);
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceAccountScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_ACCOUNT_GENERAL;
	}

	@Override
	public boolean isVisible(User user, CommerceAccount commerceAccount) {
		if (commerceAccount == null) {
			return false;
		}

		return true;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		CommerceAccountOrganizationRelAdminDisplayContext
			commerceAccountOrganizationRelAdminDisplayContext =
				new CommerceAccountOrganizationRelAdminDisplayContext(
					_commerceAccountModelResourcePermission,
					_commerceAccountService,
					_commerceAccountOrganizationRelService, _itemSelector,
					renderRequest);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceAccountOrganizationRelAdminDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/account/organizations.jsp");
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.account.model.CommerceAccount)"
	)
	private ModelResourcePermission<CommerceAccount>
		_commerceAccountModelResourcePermission;

	@Reference
	private CommerceAccountOrganizationRelService
		_commerceAccountOrganizationRelService;

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private ItemSelector _itemSelector;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.account.admin.web)"
	)
	private ServletContext _servletContext;

}
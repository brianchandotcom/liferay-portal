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

package com.liferay.commerce.organization.web.internal.servlet.taglib.ui;

import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.web.internal.display.context.CommerceOrganizationBranchesDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {"screen.navigation.entry.order:Integer=30"},
	service = {ScreenNavigationEntry.class}
)
public class CommerceOrganizationBranchesScreenNavigationEntry
	implements ScreenNavigationEntry<Organization> {

	@Override
	public String getCategoryKey() {
		return CommerceOrganizationScreenNavigationConstants.CATEGORY_DETAILS;
	}

	@Override
	public String getEntryKey() {
		return CommerceOrganizationScreenNavigationConstants.
			ENTRY_KEY_ORGANIZATION_BRANCHES;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "branches");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceOrganizationScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY;
	}

	@Override
	public boolean isVisible(User user, Organization organization) {
		String type = organization.getType();

		return type.equals("account");
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceOrganizationBranchesDisplayContext
			commerceOrganizationBranchesDisplayContext =
				new CommerceOrganizationBranchesDisplayContext(
					httpServletRequest, _commerceOrganizationService,
					_organizationService);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceOrganizationBranchesDisplayContext);

		_jspRenderer.renderJSP(
			httpServletRequest, httpServletResponse, "/view_branches.jsp");
	}

	@Reference
	private CommerceOrganizationService _commerceOrganizationService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private OrganizationService _organizationService;

}
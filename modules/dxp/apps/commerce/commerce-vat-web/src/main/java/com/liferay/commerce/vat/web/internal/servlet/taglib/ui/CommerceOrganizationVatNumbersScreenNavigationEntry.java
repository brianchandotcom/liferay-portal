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

package com.liferay.commerce.vat.web.internal.servlet.taglib.ui;

import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.vat.constants.CommerceVatWebKeys;
import com.liferay.commerce.vat.model.CommerceVatNumber;
import com.liferay.commerce.vat.service.CommerceVatNumberService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "screen.navigation.entry.order:Integer=50",
	service = ScreenNavigationEntry.class
)
public class CommerceOrganizationVatNumbersScreenNavigationEntry
	implements ScreenNavigationEntry<Organization> {

	public static final String ENTRY_KEY = "vat-number";

	@Override
	public String getCategoryKey() {
		return "details";
	}

	@Override
	public String getEntryKey() {
		return ENTRY_KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, ENTRY_KEY);
	}

	@Override
	public String getScreenNavigationKey() {
		return "organization-screen-navigation";
	}

	@Override
	public boolean isVisible(User user, Organization organization) {
		if (organization == null) {
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
			Organization organization =
				_commerceOrganizationHelper.getCurrentOrganization(
					httpServletRequest);

			httpServletRequest.setAttribute(WebKeys.ORGANIZATION, organization);

			long groupId = _portal.getScopeGroupId(httpServletRequest);

			CommerceVatNumber commerceVatNumber =
				_commerceVatNumberService.fetchCommerceVatNumber(
					groupId, organization.getModelClassName(),
					organization.getUserId());

			if (commerceVatNumber != null) {
				httpServletRequest.setAttribute(
					CommerceVatWebKeys.COMMERCE_VAT_NUMBER, commerceVatNumber);
			}
		}
		catch (PortalException pe) {
			_log.error(pe, pe);
		}

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/organization/vat_number.jsp");
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrganizationVatNumbersScreenNavigationEntry.class);

	@Reference
	private CommerceOrganizationHelper _commerceOrganizationHelper;

	@Reference
	private CommerceVatNumberService _commerceVatNumberService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference(target = "(osgi.web.symbolicname=com.liferay.commerce.vat.web)")
	private ServletContext _servletContext;

}
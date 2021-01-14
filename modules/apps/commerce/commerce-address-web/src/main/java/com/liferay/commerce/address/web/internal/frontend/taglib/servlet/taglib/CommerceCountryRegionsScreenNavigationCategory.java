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

package com.liferay.commerce.address.web.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.address.web.internal.display.context.CommerceRegionsDisplayContext;
import com.liferay.commerce.address.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.address.web.internal.servlet.taglib.ui.constants.CommerceCountryScreenNavigationConstants;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationCategory;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

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
		"screen.navigation.entry.order:Integer=20"
	},
	service = {ScreenNavigationCategory.class, ScreenNavigationEntry.class}
)
public class CommerceCountryRegionsScreenNavigationCategory
	implements ScreenNavigationCategory,
			   ScreenNavigationEntry<CommerceCountry> {

	@Override
	public String getCategoryKey() {
		return CommerceCountryScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_COUNTRY_REGIONS;
	}

	@Override
	public String getEntryKey() {
		return CommerceCountryScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_COUNTRY_REGIONS;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, "regions");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceCountryScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_COUNTRY_GENERAL;
	}

	@Override
	public boolean isVisible(User user, CommerceCountry commerceCountry) {
		if (commerceCountry == null) {
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
		RenderResponse renderResponse =
			(RenderResponse)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_RESPONSE);

		CommerceRegionsDisplayContext commerceRegionsDisplayContext =
			new CommerceRegionsDisplayContext(
				_actionHelper, _commerceRegionService,
				_portletResourcePermission, renderRequest, renderResponse);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT, commerceRegionsDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/country/regions.jsp");
	}

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CommerceRegionService _commerceRegionService;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(resource.name=" + CommerceConstants.RESOURCE_NAME_ADDRESS + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.address.web)"
	)
	private ServletContext _servletContext;

}
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

package com.liferay.commerce.cloud.client.web.internal.servlet.taglib.ui.entry;

import com.liferay.commerce.cloud.client.web.internal.constants.CommerceCloudClientScreenNavigationConstants;
import com.liferay.commerce.cloud.client.web.internal.display.context.EditConfigurationDisplayContext;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;

import java.io.IOException;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
public abstract class BaseCommerceCloudClientConfigurationScreenNavigationEntry
	implements ScreenNavigationEntry<EditConfigurationDisplayContext> {

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = _getResourceBundle(locale);

		return LanguageUtil.get(resourceBundle, getEntryKey());
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceCloudClientScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_CLOUD_CLIENT_CONFIGURATION;
	}

	@Override
	public void render(HttpServletRequest request, HttpServletResponse response)
		throws IOException {

		jspRenderer.renderJSP(servletContext, request, response, getJspPath());
	}

	protected abstract String getJspPath();

	@Reference
	protected JSPRenderer jspRenderer;

	@Reference
	protected Portal portal;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.commerce.cloud.client.web)"
	)
	protected ResourceBundleLoader resourceBundleLoader;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.cloud.client.web)"
	)
	protected ServletContext servletContext;

	private ResourceBundle _getResourceBundle(Locale locale) {
		ResourceBundle resourceBundle = resourceBundleLoader.loadResourceBundle(
			locale);

		return new AggregateResourceBundle(
			resourceBundle, portal.getResourceBundle(locale));
	}

}
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
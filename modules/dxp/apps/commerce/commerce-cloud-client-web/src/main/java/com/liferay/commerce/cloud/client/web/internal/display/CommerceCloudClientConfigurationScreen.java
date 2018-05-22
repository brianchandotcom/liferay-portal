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

package com.liferay.commerce.cloud.client.web.internal.display;

import com.liferay.commerce.cloud.client.constants.CommerceCloudClientConstants;
import com.liferay.commerce.cloud.client.util.CommerceCloudClient;
import com.liferay.commerce.cloud.client.web.internal.display.context.CommerceCloudClientConfigurationDisplayContext;
import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.portlet.PortletURLFactory;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
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
 * @author Andrea Di Giorgi
 */
@Component(immediate = true)
public class CommerceCloudClientConfigurationScreen
	implements ConfigurationScreen {

	@Override
	public String getCategoryKey() {
		return "commerce";
	}

	@Override
	public String getKey() {
		return CommerceCloudClientConstants.CONFIGURATION_PID;
	}

	@Override
	public String getName(Locale locale) {
		ResourceBundle resourceBundle =
			_commerceCloudClientResourceBundleLoader.loadResourceBundle(locale);

		return LanguageUtil.get(
			resourceBundle, "commerce-cloud-client-configuration-name");
	}

	@Override
	public String getScope() {
		return ExtendedObjectClassDefinition.Scope.SYSTEM.getValue();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		CommerceCloudClientConfigurationDisplayContext
			commerceCloudClientConfigurationDisplayContext =
				new CommerceCloudClientConfigurationDisplayContext(
					_commerceCloudClient, _commerceOrderResourceBundleLoader,
					_configurationProvider, httpServletRequest, _jsonFactory,
					_portal, _portletURLFactory);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceCloudClientConfigurationDisplayContext);

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/edit_configuration.jsp");
	}

	@Reference
	private CommerceCloudClient _commerceCloudClient;

	@Reference(
		target = "(bundle.symbolic.name=com.liferay.commerce.cloud.client.api)"
	)
	private ResourceBundleLoader _commerceCloudClientResourceBundleLoader;

	@Reference(target = "(bundle.symbolic.name=com.liferay.commerce.order.web)")
	private ResourceBundleLoader _commerceOrderResourceBundleLoader;

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference
	private Portal _portal;

	@Reference
	private PortletURLFactory _portletURLFactory;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.cloud.client.web)"
	)
	private ServletContext _servletContext;

}
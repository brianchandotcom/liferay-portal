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

package com.liferay.commerce.shipping.engine.fedex.internal.servlet.taglib.ui;

import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.shipping.engine.fedex.internal.FedExCommerceShippingEngine;
import com.liferay.commerce.shipping.engine.fedex.internal.configuration.FedExCommerceShippingEngineGroupServiceConfiguration;
import com.liferay.commerce.shipping.engine.fedex.internal.constants.FedExCommerceShippingEngineConstants;
import com.liferay.commerce.shipping.web.servlet.taglib.ui.CommerceShippingScreenNavigationConstants;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "screen.navigation.entry.order:Integer=20",
	service = ScreenNavigationEntry.class
)
public class CommerceShippingMethodFedExConfigurationScreenNavigationEntry
	implements ScreenNavigationEntry<CommerceShippingMethod> {

	@Override
	public String getCategoryKey() {
		return CommerceShippingScreenNavigationConstants.
			CATEGORY_KEY_COMMERCE_SHIPPING_METHOD_DETAILS;
	}

	@Override
	public String getEntryKey() {
		return "fedex-configuration";
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "configuration");
	}

	@Override
	public String getScreenNavigationKey() {
		return CommerceShippingScreenNavigationConstants.
			SCREEN_NAVIGATION_KEY_COMMERCE_SHIPPING_METHOD;
	}

	@Override
	public boolean isVisible(
		User user, CommerceShippingMethod commerceShippingMethod) {

		String engineKey = commerceShippingMethod.getEngineKey();

		if (engineKey.equals(FedExCommerceShippingEngine.KEY)) {
			return true;
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			FedExCommerceShippingEngineGroupServiceConfiguration
				fedExCommerceShippingEngineGroupServiceConfiguration =
					_configurationProvider.getConfiguration(
						FedExCommerceShippingEngineGroupServiceConfiguration.
							class,
						new ParameterMapSettingsLocator(
							httpServletRequest.getParameterMap(),
							new GroupServiceSettingsLocator(
								themeDisplay.getScopeGroupId(),
								FedExCommerceShippingEngineConstants.
									SERVICE_NAME)));

			httpServletRequest.setAttribute(
				FedExCommerceShippingEngineGroupServiceConfiguration.class.
					getName(),
				fedExCommerceShippingEngineGroupServiceConfiguration);

			_jspRenderer.renderJSP(
				_servletContext, httpServletRequest, httpServletResponse,
				"/configuration.jsp");
		}
		catch (ConfigurationException ce) {
			_log.error(ce, ce);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShippingMethodFedExConfigurationScreenNavigationEntry.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.shipping.engine.fedex)"
	)
	private ServletContext _servletContext;

}
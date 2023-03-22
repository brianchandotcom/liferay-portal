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

package com.liferay.commerce.tax.engine.remote.internal.frontend.taglib.servlet.taglib;

import com.liferay.commerce.tax.engine.remote.internal.RemoteCommerceTaxEngine;
import com.liferay.commerce.tax.engine.remote.internal.configuration.RemoteCommerceTaxConfiguration;
import com.liferay.commerce.tax.engine.remote.internal.constants.RemoteCommerceTaxEngineConstants;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.frontend.taglib.servlet.taglib.ScreenNavigationEntry;
import com.liferay.frontend.taglib.servlet.taglib.util.JSPRenderer;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "screen.navigation.entry.order:Integer=10",
	service = ScreenNavigationEntry.class
)
public class RemoteCommerceTaxMethodConfigurationScreenNavigationEntry
	extends RemoteCommerceTaxMethodConfigurationScreenNavigationCategory
	implements ScreenNavigationEntry<CommerceTaxMethod> {

	@Override
	public String getEntryKey() {
		return getCategoryKey();
	}

	@Override
	public boolean isVisible(User user, CommerceTaxMethod commerceTaxMethod) {
		if (commerceTaxMethod == null) {
			return false;
		}

		String engineKey = commerceTaxMethod.getEngineKey();

		if (engineKey.equals(RemoteCommerceTaxEngine.KEY)) {
			return true;
		}

		return false;
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		httpServletRequest.setAttribute(
			RemoteCommerceTaxConfiguration.class.getName(),
			_getRemoteCommerceTaxConfiguration(httpServletRequest));

		_jspRenderer.renderJSP(
			_servletContext, httpServletRequest, httpServletResponse,
			"/configuration.jsp");
	}

	private RemoteCommerceTaxConfiguration _getRemoteCommerceTaxConfiguration(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		try {
			return _configurationProvider.getConfiguration(
				RemoteCommerceTaxConfiguration.class,
				new ParameterMapSettingsLocator(
					httpServletRequest.getParameterMap(),
					new GroupServiceSettingsLocator(
						themeDisplay.getScopeGroupId(),
						RemoteCommerceTaxEngineConstants.SERVICE_NAME)));
		}
		catch (ConfigurationException configurationException) {
			throw new SystemException(configurationException);
		}
	}

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private JSPRenderer _jspRenderer;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.commerce.tax.engine.remote)"
	)
	private ServletContext _servletContext;

}
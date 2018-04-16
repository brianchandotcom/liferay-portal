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

package com.liferay.commerce.shipping.web.internal.display.context;

import com.liferay.commerce.configuration.CommerceShippingGroupServiceConfiguration;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.model.CommerceShippingOriginLocator;
import com.liferay.commerce.util.CommerceShippingOriginLocatorRegistry;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceShippingSettingsDisplayContext {

	public CommerceShippingSettingsDisplayContext(
		CommerceShippingOriginLocatorRegistry
			commerceShippingOriginLocatorRegistry,
		ConfigurationProvider configurationProvider,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		_commerceShippingOriginLocatorRegistry =
			commerceShippingOriginLocatorRegistry;
		_configurationProvider = configurationProvider;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
	}

	public CommerceShippingGroupServiceConfiguration
			getCommerceShippingGroupServiceConfiguration()
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _configurationProvider.getConfiguration(
			CommerceShippingGroupServiceConfiguration.class,
			new ParameterMapSettingsLocator(
				_renderRequest.getParameterMap(),
				new GroupServiceSettingsLocator(
					themeDisplay.getScopeGroupId(),
					CommerceConstants.SHIPPING_SERVICE_NAME)));
	}

	public String getCommerceShippingOriginLocatorHideBoxIds(String key) {
		Map<String, CommerceShippingOriginLocator>
			commerceShippingOriginLocators =
				getCommerceShippingOriginLocators();

		StringBundler sb = new StringBundler(
			(commerceShippingOriginLocators.size() - 1) * 6 + 1);

		sb.append(CharPool.OPEN_BRACKET);

		boolean first = true;

		for (String curKey : commerceShippingOriginLocators.keySet()) {
			if (curKey.equals(key)) {
				continue;
			}

			if (!first) {
				sb.append(StringPool.COMMA_AND_SPACE);
			}

			first = false;

			sb.append(CharPool.APOSTROPHE);
			sb.append(_renderResponse.getNamespace());
			sb.append(curKey);
			sb.append("OriginOptions");
			sb.append(CharPool.APOSTROPHE);
		}

		sb.append(CharPool.CLOSE_BRACKET);

		return sb.toString();
	}

	public Map<String, CommerceShippingOriginLocator>
		getCommerceShippingOriginLocators() {

		return _commerceShippingOriginLocatorRegistry.
			getCommerceShippingOriginLocators();
	}

	private final CommerceShippingOriginLocatorRegistry
		_commerceShippingOriginLocatorRegistry;
	private final ConfigurationProvider _configurationProvider;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}
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

package com.liferay.commerce.cloud.client.web.internal.display.context;

import com.liferay.commerce.cloud.client.configuration.CommerceCloudClientConfiguration;
import com.liferay.commerce.cloud.client.exception.CommerceCloudClientException;
import com.liferay.commerce.cloud.client.util.CommerceCloudClient;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Optional;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 */
public class EditConfigurationDisplayContext {

	public EditConfigurationDisplayContext(
		CommerceCloudClient commerceCloudClient,
		ResourceBundleLoader commerceCloudClientResourceBundleLoader,
		ResourceBundleLoader commerceOrderResourceBundleLoader,
		ConfigurationProvider configurationProvider,
		HttpServletRequest httpServletRequest, JSONFactory jsonFactory,
		Portal portal, RenderResponse renderResponse) {

		_commerceCloudClient = commerceCloudClient;
		_commerceCloudClientResourceBundleLoader =
			commerceCloudClientResourceBundleLoader;
		_commerceOrderResourceBundleLoader = commerceOrderResourceBundleLoader;
		_configurationProvider = configurationProvider;
		_httpServletRequest = httpServletRequest;
		_jsonFactory = jsonFactory;
		_portal = portal;
		_renderResponse = renderResponse;

		addBreadcrumbEntries();
	}

	public String getCategoryName() {
		return LanguageUtil.get(_httpServletRequest, "category.commerce");
	}

	public CommerceCloudClientConfiguration
			getCommerceCloudClientConfiguration()
		throws Exception {

		if (_commerceCloudClientConfiguration == null) {
			_commerceCloudClientConfiguration =
				_configurationProvider.getSystemConfiguration(
					CommerceCloudClientConfiguration.class);
		}

		return _commerceCloudClientConfiguration;
	}

	public JSONObject getCommerceCloudServerConfiguration() {
		if (_commerceCloudServerConfiguration == null) {
			JSONObject jsonObject = null;

			try {
				jsonObject = _commerceCloudClient.getServerConfiguration();
			}
			catch (CommerceCloudClientException.MustBeConfigured mbc) {
			}
			catch (CommerceCloudClientException ccce) {
				_log.error(ccce, ccce);

				jsonObject = _jsonFactory.createJSONObject();

				jsonObject.putException(ccce);
			}

			_commerceCloudServerConfiguration = Optional.ofNullable(jsonObject);
		}

		return _commerceCloudServerConfiguration.orElse(null);
	}

	public String getCommerceCloudServerConfigurationLabel(String value) {
		value = StringUtil.toLowerCase(value);

		return StringUtil.replace(value, CharPool.UNDERLINE, CharPool.DASH);
	}

	public String getOrderStatusLabel(int orderStatus) {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle =
			_commerceOrderResourceBundleLoader.loadResourceBundle(
				themeDisplay.getLocale());

		return LanguageUtil.get(
			resourceBundle,
			CommerceOrderConstants.getOrderStatusLabel(orderStatus));
	}

	public String getViewCategoryURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "/view_category");
		portletURL.setParameter("configurationCategory", "commerce");

		return portletURL.toString();
	}

	protected void addBreadcrumbEntries() {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)_httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_portal.addPortletBreadcrumbEntry(
			_httpServletRequest, portletDisplay.getPortletDisplayName(),
			String.valueOf(_renderResponse.createRenderURL()));

		_portal.addPortletBreadcrumbEntry(
			_httpServletRequest, getCategoryName(), getViewCategoryURL());

		ResourceBundle resourceBundle =
			_commerceCloudClientResourceBundleLoader.loadResourceBundle(
				themeDisplay.getLocale());

		String configurationName = LanguageUtil.get(
			resourceBundle, "commerce-cloud-client-configuration-name");

		_portal.addPortletBreadcrumbEntry(
			_httpServletRequest, configurationName, null);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditConfigurationDisplayContext.class);

	private final CommerceCloudClient _commerceCloudClient;
	private CommerceCloudClientConfiguration _commerceCloudClientConfiguration;
	private final ResourceBundleLoader _commerceCloudClientResourceBundleLoader;
	private Optional<JSONObject> _commerceCloudServerConfiguration;
	private final ResourceBundleLoader _commerceOrderResourceBundleLoader;
	private final ConfigurationProvider _configurationProvider;
	private final HttpServletRequest _httpServletRequest;
	private final JSONFactory _jsonFactory;
	private final Portal _portal;
	private final RenderResponse _renderResponse;

}
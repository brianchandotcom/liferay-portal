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

package com.liferay.commerce.cloud.client.web.internal.display.context;

import com.liferay.commerce.cloud.client.configuration.CommerceCloudClientConfiguration;
import com.liferay.commerce.cloud.client.exception.CommerceCloudClientException;
import com.liferay.commerce.cloud.client.util.CommerceCloudClient;
import com.liferay.commerce.constants.CommerceOrderConstants;
import com.liferay.petra.function.UnsafeSupplier;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.json.JSONArray;
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
import javax.portlet.RenderRequest;
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
		Portal portal, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		_commerceCloudClient = commerceCloudClient;
		_commerceCloudClientResourceBundleLoader =
			commerceCloudClientResourceBundleLoader;
		_commerceOrderResourceBundleLoader = commerceOrderResourceBundleLoader;
		_configurationProvider = configurationProvider;
		_httpServletRequest = httpServletRequest;
		_jsonFactory = jsonFactory;
		_portal = portal;
		_renderRequest = renderRequest;
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

	public String getDefaultCallbackHost() {
		return _portal.getHost(_renderRequest);
	}

	public JSONObject getForecastingConfiguration() {
		if (_forecastingConfiguration == null) {
			_forecastingConfiguration = getConfiguration(
				_commerceCloudClient::getForecastingConfiguration);
		}

		return _forecastingConfiguration.orElse(null);
	}

	public String getForecastingConfigurationLabel(String value) {
		value = StringUtil.toLowerCase(value);

		return StringUtil.replace(value, CharPool.UNDERLINE, CharPool.DASH);
	}

	public JSONArray getForecastingItemsConfiguration() {
		JSONObject forecastingConfigurationJSONObject =
			getForecastingConfiguration();

		if (forecastingConfigurationJSONObject == null) {
			return null;
		}

		JSONArray jsonArray = forecastingConfigurationJSONObject.getJSONArray(
			"items");

		if ((jsonArray == null) || (jsonArray.length() == 0)) {
			jsonArray = _jsonFactory.createJSONArray();

			JSONObject jsonObject = _jsonFactory.createJSONObject();

			jsonObject.put("ahead", 1);

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	public String getOrderStatusLabel(int orderStatus) {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ResourceBundle resourceBundle =
			_commerceOrderResourceBundleLoader.loadResourceBundle(
				themeDisplay.getLocale());

		return LanguageUtil.get(
			resourceBundle,
			CommerceOrderConstants.getOrderStatusLabel(orderStatus));
	}

	public JSONObject getProjectConfiguration() {
		if (_projectConfiguration == null) {
			_projectConfiguration = getConfiguration(
				_commerceCloudClient::getProjectConfiguration);
		}

		return _projectConfiguration.orElse(null);
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

	protected Optional<JSONObject> getConfiguration(
		UnsafeSupplier<JSONObject, CommerceCloudClientException> supplier) {

		JSONObject jsonObject = null;

		try {
			jsonObject = supplier.get();
		}
		catch (CommerceCloudClientException.MustBeConfigured mbc) {
		}
		catch (CommerceCloudClientException ccce) {
			_log.error(ccce, ccce);

			jsonObject = _jsonFactory.createJSONObject();

			jsonObject.putException(ccce);
		}

		return Optional.ofNullable(jsonObject);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditConfigurationDisplayContext.class);

	private final CommerceCloudClient _commerceCloudClient;
	private CommerceCloudClientConfiguration _commerceCloudClientConfiguration;
	private final ResourceBundleLoader _commerceCloudClientResourceBundleLoader;
	private final ResourceBundleLoader _commerceOrderResourceBundleLoader;
	private final ConfigurationProvider _configurationProvider;
	private Optional<JSONObject> _forecastingConfiguration;
	private final HttpServletRequest _httpServletRequest;
	private final JSONFactory _jsonFactory;
	private final Portal _portal;
	private Optional<JSONObject> _projectConfiguration;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}
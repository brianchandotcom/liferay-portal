/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.product.analytics.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.display.ConfigurationFormRenderer;
import com.liferay.layout.utility.page.kernel.provider.LayoutUtilityPageEntryLayoutProvider;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.product.analytics.web.internal.configuration.ProductAnalyticsConfiguration;
import com.liferay.product.analytics.web.internal.constants.ProductAnalyticsWebKeys;
import com.liferay.product.analytics.web.internal.display.context.ProductAnalyticsConfigurationDisplayContext;

import jakarta.portlet.PortletRequest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Date;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lucas Miranda
 */
@Component(service = ConfigurationFormRenderer.class)
public class ProductAnalyticsConfigurationFormRenderer
	implements ConfigurationFormRenderer {

	@Override
	public String getPid() {
		return ProductAnalyticsConfiguration.class.getName();
	}

	@Override
	public Map<String, Object> getRequestParameters(
		HttpServletRequest httpServletRequest) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String portletId = PortalUtil.getPortletId(
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAKARTA_PORTLET_REQUEST));

		ProductAnalyticsConfiguration productAnalyticsConfiguration = null;

		try {
			if (portletId.equals(
					ConfigurationAdminPortletKeys.SYSTEM_SETTINGS)) {

				productAnalyticsConfiguration =
					_configurationProvider.getSystemConfiguration(
						ProductAnalyticsConfiguration.class);
			}
			else if (portletId.equals(
						ConfigurationAdminPortletKeys.SITE_SETTINGS)) {

				productAnalyticsConfiguration =
					_configurationProvider.getGroupConfiguration(
						ProductAnalyticsConfiguration.class,
						themeDisplay.getCompanyId(),
						themeDisplay.getScopeGroupId());
			}
			else {
				productAnalyticsConfiguration =
					_configurationProvider.getCompanyConfiguration(
						ProductAnalyticsConfiguration.class,
						themeDisplay.getCompanyId());
			}
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);
		}

		int consentRenewalPeriod = ParamUtil.getInteger(
			httpServletRequest, "consentRenewalPeriod", 12);

		int cookiesConfigurationProviderConsentRenewalPeriod =
			consentRenewalPeriod;

		if (productAnalyticsConfiguration != null) {
			cookiesConfigurationProviderConsentRenewalPeriod =
				productAnalyticsConfiguration.consentRenewalPeriod();
		}

		boolean enabled = ParamUtil.getBoolean(httpServletRequest, "enabled");

		if (!enabled &&
			(consentRenewalPeriod !=
				cookiesConfigurationProviderConsentRenewalPeriod)) {

			consentRenewalPeriod =
				cookiesConfigurationProviderConsentRenewalPeriod;
		}

		if ((consentRenewalPeriod < 1) || (consentRenewalPeriod > 12)) {
			consentRenewalPeriod = 12;
		}

		return HashMapBuilder.<String, Object>put(
			"consentRenewalPeriod", consentRenewalPeriod
		).put(
			"enabled", enabled
		).put(
			"lastModified",
			() -> {
				Date now = new Date();

				return ParamUtil.getLong(
					httpServletRequest, "lastModified", now.getTime());
			}
		).build();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			httpServletRequest.setAttribute(
				ProductAnalyticsWebKeys.
					PRODUCT_ANALYTICS_CONFIGURATION_DISPLAY_CONTEXT,
				new ProductAnalyticsConfigurationDisplayContext(
					httpServletRequest, _layoutUtilityPageEntryLayoutProvider));

			RequestDispatcher requestDispatcher =
				_servletContext.getRequestDispatcher(
					"/product_analytics_configuration/view.jsp");

			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render /product_analytics_configuration/view.jsp",
				exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductAnalyticsConfigurationFormRenderer.class);

	@Reference
	private ConfigurationProvider _configurationProvider;

	@Reference
	private LayoutUtilityPageEntryLayoutProvider
		_layoutUtilityPageEntryLayoutProvider;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.product.analytics.web)"
	)
	private ServletContext _servletContext;

}
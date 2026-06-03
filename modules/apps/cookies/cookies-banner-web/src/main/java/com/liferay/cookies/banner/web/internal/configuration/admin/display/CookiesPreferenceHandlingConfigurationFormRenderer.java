/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.banner.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.configuration.admin.display.ConfigurationFormRenderer;
import com.liferay.cookies.banner.web.internal.constants.CookiesBannerWebKeys;
import com.liferay.cookies.banner.web.internal.display.context.CookiesPreferenceHandlingConfigurationDisplayContext;
import com.liferay.cookies.configuration.CookiesConfigurationProvider;
import com.liferay.cookies.configuration.CookiesPreferenceHandlingConfiguration;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.PortletRequest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Thiago Buarque
 */
@Component(service = ConfigurationFormRenderer.class)
public class CookiesPreferenceHandlingConfigurationFormRenderer
	implements ConfigurationFormRenderer {

	@Override
	public String getPid() {
		return CookiesPreferenceHandlingConfiguration.class.getName();
	}

	@Override
	public Map<String, Object> getRequestParameters(
		HttpServletRequest httpServletRequest) {

		if (!ParamUtil.getBoolean(httpServletRequest, "enabled")) {
			return Map.of("active", false, "enabled", false);
		}

		ConfigurationScope configurationScope = _getConfigurationScope(
			httpServletRequest);

		long customFloatingIconImageId =
			_cookiesConfigurationProvider.
				getCookiesPreferenceHandlingCustomFloatingIconImageId(
					configurationScope.getScope(),
					configurationScope.getScopePK());

		long fileEntryId = ParamUtil.getLong(httpServletRequest, "fileEntryId");

		try {
			if (ParamUtil.getBoolean(httpServletRequest, "deleteLogo")) {
				_imageLocalService.deleteImage(customFloatingIconImageId);

				customFloatingIconImageId = 0;
			}
			else if (fileEntryId > 0) {
				FileEntry fileEntry = _dlAppLocalService.getFileEntry(
					fileEntryId);

				byte[] bytes = FileUtil.getBytes(fileEntry.getContentStream());

				Image image = null;

				if (customFloatingIconImageId > 0) {
					image = _imageLocalService.moveImage(
						customFloatingIconImageId, bytes);
				}
				else {
					ThemeDisplay themeDisplay =
						(ThemeDisplay)httpServletRequest.getAttribute(
							WebKeys.THEME_DISPLAY);

					image = _imageLocalService.updateImage(
						themeDisplay.getCompanyId(),
						_counterLocalService.increment(), bytes);
				}

				customFloatingIconImageId = image.getImageId();
			}
		}
		catch (IOException | PortalException exception) {
			throw new RuntimeException(exception);
		}

		return HashMapBuilder.<String, Object>put(
			"active", ParamUtil.getBoolean(httpServletRequest, "active")
		).put(
			"consentRenewalPeriod",
			ParamUtil.getInteger(httpServletRequest, "consentRenewalPeriod", 12)
		).put(
			"consentRenewalPeriodTimeUnit",
			ParamUtil.getString(
				httpServletRequest, "consentRenewalPeriodTimeUnit", "months")
		).put(
			"customFloatingIconImageId", customFloatingIconImageId
		).put(
			"dissentRenewalPeriod",
			ParamUtil.getInteger(httpServletRequest, "dissentRenewalPeriod", 12)
		).put(
			"dissentRenewalPeriodTimeUnit",
			ParamUtil.getString(
				httpServletRequest, "dissentRenewalPeriodTimeUnit", "months")
		).put(
			"enabled", true
		).put(
			"explicitConsentMode",
			ParamUtil.getBoolean(httpServletRequest, "explicitConsentMode")
		).put(
			"floatingIcon",
			ParamUtil.getString(httpServletRequest, "floatingIcon", "cookie")
		).put(
			"floatingIconEnabled",
			ParamUtil.getBoolean(httpServletRequest, "floatingIconEnabled")
		).put(
			"globalPrivacyControlEnabled",
			ParamUtil.getBoolean(
				httpServletRequest, "globalPrivacyControlEnabled")
		).put(
			"modifiedDate",
			() -> {
				long modifiedDate = ParamUtil.getLong(
					httpServletRequest, "modifiedDate");

				if (modifiedDate <= 0) {
					return null;
				}

				return modifiedDate;
			}
		).put(
			"storeConsent",
			ParamUtil.getBoolean(httpServletRequest, "storeConsent")
		).build();
	}

	@Override
	public void render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws IOException {

		try {
			_render(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new IOException(
				"Unable to render /cookies_preference_handling_configuration" +
					"/view.jsp",
				exception);
		}
	}

	private ConfigurationScope _getConfigurationScope(
		HttpServletRequest httpServletRequest) {

		String portletId = PortalUtil.getPortletId(
			(PortletRequest)httpServletRequest.getAttribute(
				JavaConstants.JAKARTA_PORTLET_REQUEST));
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (ConfigurationAdminPortletKeys.INSTANCE_SETTINGS.equals(portletId)) {
			return new ConfigurationScope(
				ExtendedObjectClassDefinition.Scope.COMPANY,
				themeDisplay.getCompanyId());
		}

		if (ConfigurationAdminPortletKeys.SITE_SETTINGS.equals(portletId)) {
			return new ConfigurationScope(
				ExtendedObjectClassDefinition.Scope.GROUP,
				themeDisplay.getScopeGroupId());
		}

		return new ConfigurationScope(
			ExtendedObjectClassDefinition.Scope.SYSTEM, 0);
	}

	private void _render(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		RequestDispatcher requestDispatcher =
			_servletContext.getRequestDispatcher(
				"/cookies_preference_handling_configuration/view.jsp");

		ConfigurationScope configurationScope = _getConfigurationScope(
			httpServletRequest);

		httpServletRequest.setAttribute(
			CookiesBannerWebKeys.
				COOKIES_PREFERENCE_HANDLING_CONFIGURATION_DISPLAY_CONTEXT,
			new CookiesPreferenceHandlingConfigurationDisplayContext(
				_cookiesConfigurationProvider, configurationScope.getScope(),
				configurationScope.getScopePK()));

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	@Reference
	private CookiesConfigurationProvider _cookiesConfigurationProvider;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private DLAppLocalService _dlAppLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.cookies.banner.web)"
	)
	private ServletContext _servletContext;

	private static class ConfigurationScope {

		public ExtendedObjectClassDefinition.Scope getScope() {
			return _scope;
		}

		public long getScopePK() {
			return _scopePK;
		}

		private ConfigurationScope(
			ExtendedObjectClassDefinition.Scope scope, long scopePK) {

			_scope = scope;
			_scopePK = scopePK;
		}

		private final ExtendedObjectClassDefinition.Scope _scope;
		private final long _scopePK;

	}

}
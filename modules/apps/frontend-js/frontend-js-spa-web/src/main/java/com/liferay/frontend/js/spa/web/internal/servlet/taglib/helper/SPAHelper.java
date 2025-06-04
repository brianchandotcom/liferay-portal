/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.spa.web.internal.servlet.taglib.helper;

import com.liferay.frontend.js.spa.web.internal.configuration.SPAConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoader;
import com.liferay.portal.kernel.resource.bundle.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsValues;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.lang.reflect.Field;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Bryce Osterhaus
 */
public class SPAHelper {

	public SPAHelper(
		ConfigurationProvider configurationProvider, JSONFactory jsonFactory,
		Portal portal, PortletLocalService portletLocalService,
		long siteGroupId) {

		_jsonFactory = jsonFactory;
		_portal = portal;
		_portletLocalService = portletLocalService;

		SPAConfiguration spaConfiguration;

		try {
			spaConfiguration = configurationProvider.getGroupConfiguration(
				SPAConfiguration.class, siteGroupId);
		}
		catch (ConfigurationException configurationException) {
			_log.error(configurationException);

			spaConfiguration = _SPA_CONFIGURATION;
		}

		_spaConfiguration = spaConfiguration;
	}

	public long getCacheExpirationTime() {
		return _spaConfiguration.cacheExpirationTime();
	}

	public String[] getCustomExcludedPaths() {
		return _spaConfiguration.customExcludedPaths();
	}

	public JSONArray getExcludedPathsJSONArray() {
		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (String excludedPath : _SPA_DEFAULT_EXCLUDED_PATHS) {
			jsonArray.put(_portal.getPathContext() + excludedPath);
		}

		String[] customExcludedPaths = _spaConfiguration.customExcludedPaths();

		if (ArrayUtil.isEmpty(customExcludedPaths)) {
			return jsonArray;
		}

		for (String customExcludedPath : customExcludedPaths) {
			jsonArray.put(_portal.getPathContext() + customExcludedPath);
		}

		return jsonArray;
	}

	public JSONArray getExcludedTargetPortletsJSONArray() {
		return _jsonFactory.createJSONArray(
			new String[] {PortletKeys.USERS_ADMIN, PortletKeys.SERVER_ADMIN});
	}

	public ResourceBundle getLanguageResourceBundle(
		String servletContextName, Locale locale) {

		ResourceBundleLoader resourceBundleLoader =
			ResourceBundleLoaderUtil.
				getResourceBundleLoaderByServletContextName(servletContextName);

		if (resourceBundleLoader == null) {
			resourceBundleLoader =
				ResourceBundleLoaderUtil.getPortalResourceBundleLoader();
		}

		return resourceBundleLoader.loadResourceBundle(locale);
	}

	public String getLoginRedirect(HttpServletRequest httpServletRequest) {
		return ParamUtil.getString(httpServletRequest, _REDIRECT_PARAM_NAME);
	}

	public String[] getNavigationExceptionSelectors() {
		return _spaConfiguration.navigationExceptionSelectors();
	}

	public JSONArray getPortletsBlacklistJSONArray(ThemeDisplay themeDisplay) {
		return _portletsBlacklistJSONArrays.computeIfAbsent(
			themeDisplay.getCompanyId(),
			companyId -> {
				JSONArray portletsBlacklistJSONArray =
					_jsonFactory.createJSONArray();

				_portletLocalService.visitPortlets(
					companyId,
					portlet -> {
						if (!portlet.isSinglePageApplication() &&
							!portlet.isUndeployedPortlet() &&
							portlet.isActive() && portlet.isReady()) {

							portletsBlacklistJSONArray.put(
								portlet.getPortletId());
						}
					});

				return portletsBlacklistJSONArray;
			});
	}

	public int getRequestTimeout() {
		return _spaConfiguration.requestTimeout();
	}

	public int getUserNotificationTimeout() {
		return _spaConfiguration.userNotificationTimeout();
	}

	public JSONArray getValidStatusCodesJSONArray() {
		return _VALID_STATUS_CODES_JSON_ARRAY;
	}

	public boolean isClearScreensCache(
		HttpServletRequest httpServletRequest, HttpSession httpSession) {

		boolean singlePageApplicationClearCache = GetterUtil.getBoolean(
			httpServletRequest.getAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE));

		if (singlePageApplicationClearCache) {
			return true;
		}

		String portletId = httpServletRequest.getParameter("p_p_id");

		if (Validator.isNull(portletId)) {
			return false;
		}

		String singlePageApplicationLastPortletId =
			(String)httpSession.getAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_LAST_PORTLET_ID);

		if (Validator.isNotNull(singlePageApplicationLastPortletId) &&
			!Objects.equals(portletId, singlePageApplicationLastPortletId)) {

			return true;
		}

		return false;
	}

	public boolean isDebugEnabled() {
		return _log.isDebugEnabled();
	}

	public boolean isEnabled() {
		return _spaConfiguration.enabled();
	}

	public boolean isPreloadCSS() {
		return _spaConfiguration.preloadCSS();
	}

	private static final String _REDIRECT_PARAM_NAME;

	private static final SPAConfiguration _SPA_CONFIGURATION =
		new SPAConfiguration() {

			@Override
			public long cacheExpirationTime() {
				return -1;
			}

			@Override
			public String[] customExcludedPaths() {
				return new String[0];
			}

			@Override
			public boolean enabled() {
				return PropsValues.JAVASCRIPT_SINGLE_PAGE_APPLICATION_ENABLED;
			}

			@Override
			public String[] navigationExceptionSelectors() {
				return new String[] {
					":not([target=\"_blank\"])", ":not([data-senna-off])",
					":not([data-resource-href])"
				};
			}

			@Override
			public boolean preloadCSS() {
				return false;
			}

			@Override
			public int requestTimeout() {
				return 0;
			}

			@Override
			public int userNotificationTimeout() {
				return 30000;
			}

		};

	private static final String[] _SPA_DEFAULT_EXCLUDED_PATHS = {
		"/c/document_library", "/documents", "/image", "/o/cms/download-folder"
	};

	private static final JSONArray _VALID_STATUS_CODES_JSON_ARRAY;

	private static final Log _log = LogFactoryUtil.getLog(SPAHelper.class);

	static {
		Class<?> clazz = ServletResponseConstants.class;

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Field field : clazz.getDeclaredFields()) {
			try {
				jsonArray.put(field.getInt(null));
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}
			}
		}

		_VALID_STATUS_CODES_JSON_ARRAY = jsonArray;

		String portletNamespace = PortalUtil.getPortletNamespace(
			PropsUtil.get(PropsKeys.AUTH_LOGIN_PORTLET_NAME));

		_REDIRECT_PARAM_NAME = portletNamespace.concat("redirect");
	}

	private final JSONFactory _jsonFactory;
	private final Portal _portal;
	private final PortletLocalService _portletLocalService;
	private final Map<Long, JSONArray> _portletsBlacklistJSONArrays =
		new ConcurrentHashMap<>();
	private final SPAConfiguration _spaConfiguration;

}
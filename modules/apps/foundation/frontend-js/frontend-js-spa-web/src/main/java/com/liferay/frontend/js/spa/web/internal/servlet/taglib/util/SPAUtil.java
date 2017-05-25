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

package com.liferay.frontend.js.spa.web.internal.servlet.taglib.util;

import com.liferay.frontend.js.spa.web.configuration.SPAConfiguration;
import com.liferay.frontend.js.spa.web.configuration.SPAConfigurationUtil;
import com.liferay.osgi.util.StringPlus;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Bruno Basto
 */
@Component(
	configurationPid = "com.liferay.frontend.js.spa.web.configuration.SPAConfiguration",
	service = SPAUtil.class
)
@Designate(ocd = SPAConfiguration.class)
public class SPAUtil {

	public long getCacheExpirationTime(long companyId) {
		return _cacheExpirationTime;
	}

	public String getExcludedPaths() {
		return _spaExcludedPaths;
	}

	public ResourceBundle getLanguageResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	public String getLoginRedirect(HttpServletRequest request) {
		return ParamUtil.getString(request, _redirectParamName);
	}

	public String getNavigationExceptionSelectors() {
		return _navigationExceptionSelectorsString;
	}

	public String getPortletsBlacklist(ThemeDisplay themeDisplay) {
		StringBundler sb = new StringBundler();

		sb.append(StringPool.OPEN_CURLY_BRACE);

		List<Portlet> companyPortlets = _portletLocalService.getPortlets(
			themeDisplay.getCompanyId());

		for (Portlet portlet : companyPortlets) {
			if (!portlet.isSinglePageApplication() &&
				!portlet.isUndeployedPortlet() && portlet.isActive() &&
				portlet.isReady()) {

				sb.append(StringPool.QUOTE);
				sb.append(portlet.getPortletId());
				sb.append("\":true,");
			}
		}

		if (sb.index() == 1) {
			sb.append(StringPool.CLOSE_CURLY_BRACE);
		}
		else {
			sb.setIndex(sb.index() - 1);

			sb.append("\":true}");
		}

		return sb.toString();
	}

	public int getRequestTimeout() {
		return _spaConfiguration.requestTimeout();
	}

	public int getUserNotificationTimeout() {
		return _spaConfiguration.userNotificationTimeout();
	}

	public String getValidStatusCodes() {
		return _VALID_STATUS_CODES;
	}

	public boolean isClearScreensCache(
		HttpServletRequest request, HttpSession session) {

		boolean singlePageApplicationClearCache = GetterUtil.getBoolean(
			request.getAttribute(WebKeys.SINGLE_PAGE_APPLICATION_CLEAR_CACHE));

		if (singlePageApplicationClearCache) {
			return true;
		}

		String portletId = request.getParameter("p_p_id");

		if (Validator.isNull(portletId)) {
			return false;
		}

		String singlePageApplicationLastPortletId =
			(String)session.getAttribute(
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

	@Activate
	protected void activate(
			BundleContext bundleContext, SPAConfiguration spaConfiguration)
		throws InvalidSyntaxException {

		_cacheExpirationTime = _getCacheExpirationTime(spaConfiguration);

		_spaConfiguration = spaConfiguration;

		Collections.addAll(
			_navigationExceptionSelectors,
			_spaConfiguration.navigationExceptionSelectors());

		_navigationExceptionSelectorsString = ListUtil.toString(
			_navigationExceptionSelectors, (String)null, StringPool.BLANK);

		Filter filter = bundleContext.createFilter(
			"(&(objectClass=java.lang.Object)(" +
				_SPA_NAVIGATION_EXCEPTION_SELECTOR_KEY + "=*))");

		_navigationExceptionSelectorTracker = new ServiceTracker<>(
			bundleContext, filter,
			new NavigationExceptionSelectorTrackerCustomizer(bundleContext));

		_navigationExceptionSelectorTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_navigationExceptionSelectorTracker.close();
	}

	@Modified
	protected void modified(SPAConfiguration spaConfiguration) {
		_cacheExpirationTime = _getCacheExpirationTime(spaConfiguration);

		_navigationExceptionSelectors.removeAll(
			Arrays.asList(_spaConfiguration.navigationExceptionSelectors()));

		_spaConfiguration = spaConfiguration;

		Collections.addAll(
			_navigationExceptionSelectors,
			_spaConfiguration.navigationExceptionSelectors());

		_navigationExceptionSelectorsString = ListUtil.toString(
			_navigationExceptionSelectors, (String)null, StringPool.BLANK);
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	private long _getCacheExpirationTime(SPAConfiguration spaConfiguration) {
		long cacheExpirationTime = spaConfiguration.cacheExpirationTime();

		if (cacheExpirationTime > 0) {
			cacheExpirationTime *= Time.MINUTE;
		}

		return cacheExpirationTime;
	}

	private static final String _SPA_NAVIGATION_EXCEPTION_SELECTOR_KEY =
		"javascript.single.page.application.navigation.exception.selector";

	private static final String _VALID_STATUS_CODES;

	private static final Log _log = LogFactoryUtil.getLog(SPAUtil.class);

	private static final List<String> _navigationExceptionSelectors =
		new CopyOnWriteArrayList<>();
	private static volatile String _navigationExceptionSelectorsString;
	private static final String _redirectParamName;
	private static final String _spaExcludedPaths;

	static {
		Class<?> clazz = ServletResponseConstants.class;

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Field field : clazz.getDeclaredFields()) {
			try {
				jsonArray.put(field.getInt(null));
			}
			catch (Exception e) {
			}
		}

		_VALID_STATUS_CODES = jsonArray.toJSONString();

		String portletNamespace = PortalUtil.getPortletNamespace(
			PropsUtil.get(PropsKeys.AUTH_LOGIN_PORTLET_NAME));

		_redirectParamName = portletNamespace.concat("redirect");

		jsonArray = JSONFactoryUtil.createJSONArray();

		String[] excludedPaths = StringUtil.split(
			SPAConfigurationUtil.get("spa.excluded.paths"));

		String pathContext = PortalUtil.getPathContext();

		for (String excludedPath : excludedPaths) {
			jsonArray.put(pathContext + excludedPath);
		}

		_spaExcludedPaths = jsonArray.toString();
	}

	private long _cacheExpirationTime;
	private ServiceTracker<Object, Object> _navigationExceptionSelectorTracker;
	private PortletLocalService _portletLocalService;
	private SPAConfiguration _spaConfiguration;

	private static final class NavigationExceptionSelectorTrackerCustomizer
		implements ServiceTrackerCustomizer<Object, Object> {

		public NavigationExceptionSelectorTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public Object addingService(ServiceReference<Object> reference) {
			List<String> selectors = StringPlus.asList(
				reference.getProperty(_SPA_NAVIGATION_EXCEPTION_SELECTOR_KEY));

			_navigationExceptionSelectors.addAll(selectors);

			_navigationExceptionSelectorsString = ListUtil.toString(
				_navigationExceptionSelectors, (String)null, StringPool.BLANK);

			Object service = _bundleContext.getService(reference);

			_serviceReferences.add(reference);

			return service;
		}

		@Override
		public void modifiedService(
			ServiceReference<Object> reference, Object service) {

			removedService(reference, service);

			addingService(reference);
		}

		@Override
		public void removedService(
			ServiceReference<Object> reference, Object service) {

			List<String> selectors = StringPlus.asList(
				reference.getProperty(_SPA_NAVIGATION_EXCEPTION_SELECTOR_KEY));

			_navigationExceptionSelectors.removeAll(selectors);

			_navigationExceptionSelectorsString = ListUtil.toString(
				_navigationExceptionSelectors, (String)null, StringPool.BLANK);

			_serviceReferences.remove(reference);

			_bundleContext.ungetService(reference);
		}

		private final BundleContext _bundleContext;
		private final List<ServiceReference<Object>> _serviceReferences =
			new CopyOnWriteArrayList<>();

	}

}
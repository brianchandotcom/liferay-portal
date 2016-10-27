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
import com.liferay.portal.kernel.json.JSONObject;
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
		long cacheExpirationTime = _spaConfiguration.cacheExpirationTime();

		if (cacheExpirationTime > 0) {
			cacheExpirationTime *= Time.MINUTE;
		}

		return cacheExpirationTime;
	}

	public String getExcludedPaths() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String[] excludedPaths = StringUtil.split(
			SPAConfigurationUtil.get("spa.excluded.paths"));

		for (String excludedPath : excludedPaths) {
			jsonArray.put(excludedPath);
		}

		return jsonArray.toString();
	}

	public ResourceBundle getLanguageResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());
	}

	public String getLoginRedirect(HttpServletRequest request) {
		String portletNamespace = PortalUtil.getPortletNamespace(
			PropsUtil.get(PropsKeys.AUTH_LOGIN_PORTLET_NAME));

		return ParamUtil.getString(request, portletNamespace + "redirect");
	}

	public String getNavigationExceptionSelectors() {
		return ListUtil.toString(
			_navigationExceptionSelectors, (String)null, StringPool.BLANK);
	}

	public String getPortletsBlacklist(ThemeDisplay themeDisplay) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		List<Portlet> companyPortlets = _portletLocalService.getPortlets(
			themeDisplay.getCompanyId());

		for (Portlet portlet : companyPortlets) {
			if (portlet.isActive() && portlet.isReady() &&
				!portlet.isUndeployedPortlet() &&
				!portlet.isSinglePageApplication()) {

				jsonObject.put(portlet.getPortletId(), true);
			}
		}

		return jsonObject.toString();
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

		String singlePageApplicationLastPortletId =
			(String)session.getAttribute(
				WebKeys.SINGLE_PAGE_APPLICATION_LAST_PORTLET_ID);

		if (Validator.isNotNull(portletId) &&
			Validator.isNotNull(singlePageApplicationLastPortletId) &&
			!Objects.equals(portletId, singlePageApplicationLastPortletId)) {

			return true;
		}

		return false;
	}

	@Activate
	protected void activate(
			BundleContext bundleContext, SPAConfiguration spaConfiguration)
		throws InvalidSyntaxException {

		_spaConfiguration = spaConfiguration;

		_navigationExceptionSelectors.addAll(
			Arrays.asList(_spaConfiguration.navigationExceptionSelectors()));

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
		_navigationExceptionSelectors.removeAll(
			Arrays.asList(_spaConfiguration.navigationExceptionSelectors()));

		_spaConfiguration = spaConfiguration;

		_navigationExceptionSelectors.addAll(
			Arrays.asList(_spaConfiguration.navigationExceptionSelectors()));
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	private static final String _SPA_NAVIGATION_EXCEPTION_SELECTOR_KEY =
		"javascript.single.page.application.navigation.exception.selector";

	private static final String _VALID_STATUS_CODES;

	private static final List<String> _navigationExceptionSelectors =
		new CopyOnWriteArrayList<>();

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
	}

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

			Collections.addAll(
				_navigationExceptionSelectors,
				selectors.toArray(new String[selectors.size()]));

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

			_navigationExceptionSelectors.remove(selectors);

			_serviceReferences.remove(reference);

			_bundleContext.ungetService(reference);
		}

		private final BundleContext _bundleContext;
		private final List<ServiceReference<Object>> _serviceReferences =
			new CopyOnWriteArrayList<>();

	}

}
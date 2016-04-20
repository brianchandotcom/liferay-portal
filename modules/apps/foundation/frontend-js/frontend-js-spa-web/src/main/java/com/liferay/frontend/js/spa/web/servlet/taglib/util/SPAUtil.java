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

package com.liferay.frontend.js.spa.web.servlet.taglib.util;

import com.liferay.frontend.js.spa.web.configuration.SPAConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.servlet.ServletResponseConstants;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	configurationPid = "com.liferay.frontend.js.spa.web.configuration.SPAConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true
)
public class SPAUtil {

	public static long getCacheExpirationTime() {
		return _instance._getCacheExpirationTime();
	}

	public static String getExcludedPaths() {
		return _instance._getExcludedPaths();
	}

	public static String getPortletsBlacklist(ThemeDisplay themeDisplay) {
		return _instance._getPortletsBlacklist(themeDisplay);
	}

	public static String getValidStatusCodes() {
		return _instance._getValidStatusCodes();
	}

	public static boolean isClearScreensCache(
		HttpServletRequest request, HttpSession session) {

		return _instance._isClearScreensCache(request, session);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_instance = this;

		_spaConfiguration = ConfigurableUtil.createConfigurable(
			SPAConfiguration.class, properties);
	}

	@Deactivate
	protected void deactivate() {
		_instance = null;

		_spaConfiguration = null;
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	private long _getCacheExpirationTime() {
		long cacheExpirationTime = -1;

		if (_spaConfiguration != null) {
			cacheExpirationTime = GetterUtil.getLong(
				_spaConfiguration.cacheExpirationTime());
		}

		if (cacheExpirationTime > -1) {
			cacheExpirationTime *= Time.MINUTE;
		}

		return cacheExpirationTime;
	}

	private String _getExcludedPaths() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		String[] excludedPaths = {"/documents", "/image"};

		if (_spaConfiguration != null) {
			excludedPaths = _spaConfiguration.excludedPaths();
		}

		for (String excludedPath : excludedPaths) {
			jsonArray.put(excludedPath);
		}

		return jsonArray.toString();
	}

	private String _getPortletsBlacklist(ThemeDisplay themeDisplay) {
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

	private String _getValidStatusCodes() {
		Class<?> clazz = ServletResponseConstants.class;

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Field field : clazz.getDeclaredFields()) {
			try {
				jsonArray.put(field.getInt(null));
			}
			catch (Exception e) {
			}
		}

		return jsonArray.toJSONString();
	}

	private boolean _isClearScreensCache(
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

	private static SPAUtil _instance;

	private PortletLocalService _portletLocalService;
	private volatile SPAConfiguration _spaConfiguration;

}
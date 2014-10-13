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

package com.liferay.portlet;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.util.RequestUtils;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletResourceBundles {

	public static String getString(HttpServletRequest request, String key) {
		return _instance._getString(request, key);
	}

	public static String getString(Locale locale, String key) {
		return _instance._getString(locale, key);
	}

	public static String getString(String languageId, String key) {
		return _instance._getString(languageId, key);
	}

	public static String getString(
		String servletContextName, String languageId, String key) {

		return _instance._getString(servletContextName, languageId, key);
	}

	public static void put(
		String servletContextName, String languageId,
		ResourceBundle resourceBundle) {

		_instance._put(servletContextName, languageId, resourceBundle);
	}

	public static void remove(String servletContextName) {
		_instance._remove(servletContextName);
	}

	private PortletResourceBundles() {
		_resourceBundles =
			new ConcurrentHashMap<String, Map<String, ResourceBundle>>(
				new LinkedHashMap<String, Map<String, ResourceBundle>>());
	}

	private ResourceBundle _getResourceBundle(
		Map<String, ResourceBundle> resourceBundles, String languageId) {

		ResourceBundle resourceBundle = resourceBundles.get(languageId);

		if (resourceBundle == null) {
			try {
				resourceBundle = new PropertyResourceBundle(
					new UnsyncByteArrayInputStream(new byte[0]));

				resourceBundles.put(languageId, resourceBundle);
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}

		return resourceBundle;
	}

	private ResourceBundle _getResourceBundle(
		String servletContextName, String languageId) {

		Map<String, ResourceBundle> resourceBundles = _getResourceBundles(
			servletContextName);

		return _getResourceBundle(resourceBundles, languageId);
	}

	private Map<String, ResourceBundle> _getResourceBundles(
		String servletContextName) {

		Map<String, ResourceBundle> resourceBundles = _resourceBundles.get(
			servletContextName);

		if (resourceBundles == null) {
			resourceBundles = new HashMap<String, ResourceBundle>();

			_resourceBundles.put(servletContextName, resourceBundles);
		}

		return resourceBundles;
	}

	private String _getString(HttpServletRequest request, String key) {
		Locale locale = RequestUtils.getUserLocale(request, null);

		return _getString(locale, key);
	}

	private String _getString(Locale locale, String key) {
		return _getString(LocaleUtil.toLanguageId(locale), key);
	}

	private String _getString(String languageId, String key) {
		return _getString(null, languageId, key);
	}

	private String _getString(
		String servletContextName, String languageId, String key) {

		if (servletContextName != null) {
			ResourceBundle resourceBundle = _getResourceBundle(
				servletContextName, languageId);

			return resourceBundle.getString(key);
		}

		for (Map.Entry<String, Map<String, ResourceBundle>> entry :
				_resourceBundles.entrySet()) {

			Map<String, ResourceBundle> resourceBundles = entry.getValue();

			ResourceBundle resourceBundle = _getResourceBundle(
				resourceBundles, languageId);

			return ResourceBundleUtil.getString(resourceBundle, key);
		}

		return null;
	}

	private void _put(
		String servletContextName, String languageId,
		ResourceBundle resourceBundle) {

		Map<String, ResourceBundle> resourceBundles = _getResourceBundles(
			servletContextName);

		resourceBundles.put(languageId, resourceBundle);
	}

	private void _remove(String servletContextName) {
		_resourceBundles.remove(servletContextName);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletResourceBundles.class);

	private static final PortletResourceBundles _instance =
		new PortletResourceBundles();

	private final Map<String, Map<String, ResourceBundle>> _resourceBundles;

}
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

package com.liferay.portal.util;

import com.liferay.petra.content.ContentUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.PortalPreferencesWrapper;
import com.liferay.portlet.PortalPreferencesWrapperCacheUtil;

import java.util.Enumeration;
import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class PrefsPropsUtil {

	public static boolean getBoolean(long companyId, String name) {
		PortletPreferences preferences = getPreferences(companyId, true);

		return getBoolean(preferences, name);
	}

	public static boolean getBoolean(
		long companyId, String name, boolean defaultValue) {

		PortletPreferences preferences = getPreferences(companyId, true);

		return getBoolean(preferences, name, defaultValue);
	}

	public static boolean getBoolean(
		PortletPreferences preferences, String name) {

		return GetterUtil.getBoolean(getString(preferences, name));
	}

	public static boolean getBoolean(
		PortletPreferences preferences, String name, boolean defaultValue) {

		return GetterUtil.getBoolean(
			getString(preferences, name, defaultValue));
	}

	public static boolean getBoolean(String name) {
		PortletPreferences preferences = getPreferences(true);

		return getBoolean(preferences, name);
	}

	public static boolean getBoolean(String name, boolean defaultValue) {
		PortletPreferences preferences = getPreferences(true);

		return getBoolean(preferences, name, defaultValue);
	}

	public static String getContent(long companyId, String name) {
		PortletPreferences preferences = getPreferences(companyId, true);

		return getContent(preferences, name);
	}

	public static String getContent(
		PortletPreferences preferences, String name) {

		String value = preferences.getValue(name, StringPool.BLANK);

		if (Validator.isNotNull(value)) {
			return value;
		}

		return ContentUtil.get(
			PrefsPropsUtil.class.getClassLoader(), PropsUtil.get(name));
	}

	public static String getContent(String name) {
		PortletPreferences preferences = getPreferences(true);

		return getContent(preferences, name);
	}

	public static double getDouble(long companyId, String name) {
		PortletPreferences preferences = getPreferences(companyId, true);

		return getDouble(preferences, name);
	}

	public static double getDouble(
		long companyId, String name, double defaultValue) {

		PortletPreferences preferences = getPreferences(companyId, true);

		return getDouble(preferences, name, defaultValue);
	}

	public static double getDouble(
		PortletPreferences preferences, String name) {

		return GetterUtil.getDouble(getString(preferences, name));
	}

	public static double getDouble(
		PortletPreferences preferences, String name, double defaultValue) {

		return GetterUtil.getDouble(getString(preferences, name, defaultValue));
	}

	public static double getDouble(String name) {
		PortletPreferences preferences = getPreferences(true);

		return getDouble(preferences, name);
	}

	public static double getDouble(String name, double defaultValue) {
		PortletPreferences preferences = getPreferences(true);

		return getDouble(preferences, name, defaultValue);
	}

	public static int getInteger(long companyId, String name) {
		PortletPreferences preferences = getPreferences(companyId, true);

		return getInteger(preferences, name);
	}

	public static int getInteger(
		long companyId, String name, int defaultValue) {

		PortletPreferences preferences = getPreferences(companyId, true);

		return getInteger(preferences, name, defaultValue);
	}

	public static int getInteger(PortletPreferences preferences, String name) {
		return GetterUtil.getInteger(getString(preferences, name));
	}

	public static int getInteger(
		PortletPreferences preferences, String name, int defaultValue) {

		return GetterUtil.getInteger(
			getString(preferences, name, defaultValue));
	}

	public static int getInteger(String name) {
		PortletPreferences preferences = getPreferences(true);

		return getInteger(preferences, name);
	}

	public static int getInteger(String name, int defaultValue) {
		PortletPreferences preferences = getPreferences(true);

		return getInteger(preferences, name, defaultValue);
	}

	public static long getLong(long companyId, String name) {
		PortletPreferences preferences = getPreferences(companyId, true);

		return getLong(preferences, name);
	}

	public static long getLong(long companyId, String name, long defaultValue) {
		PortletPreferences preferences = getPreferences(companyId, true);

		return getLong(preferences, name, defaultValue);
	}

	public static long getLong(PortletPreferences preferences, String name) {
		return GetterUtil.getLong(getString(preferences, name));
	}

	public static long getLong(
		PortletPreferences preferences, String name, long defaultValue) {

		return GetterUtil.getLong(getString(preferences, name, defaultValue));
	}

	public static long getLong(String name) {
		PortletPreferences preferences = getPreferences(true);

		return getLong(preferences, name);
	}

	public static long getLong(String name, long defaultValue) {
		PortletPreferences preferences = getPreferences(true);

		return getLong(preferences, name, defaultValue);
	}

	public static PortletPreferences getPreferences() {
		return getPreferences(false);
	}

	public static PortletPreferences getPreferences(boolean readOnly) {
		PortalPreferencesWrapper portalPreferencesWrapper =
			PortalPreferencesWrapperCacheUtil.get(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		if (portalPreferencesWrapper != null) {
			if (!readOnly) {
				portalPreferencesWrapper = portalPreferencesWrapper.clone();
			}

			return portalPreferencesWrapper;
		}

		return _portalPreferencesLocalService.getPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_COMPANY);
	}

	public static PortletPreferences getPreferences(long companyId) {
		return getPreferences(companyId, false);
	}

	public static PortletPreferences getPreferences(
		long companyId, boolean readOnly) {

		long ownerId = companyId;
		int ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;

		PortalPreferencesWrapper portalPreferencesWrapper =
			PortalPreferencesWrapperCacheUtil.get(ownerId, ownerType);

		if (portalPreferencesWrapper != null) {
			if (!readOnly) {
				portalPreferencesWrapper = portalPreferencesWrapper.clone();
			}

			return portalPreferencesWrapper;
		}

		return _portalPreferencesLocalService.getPreferences(
			ownerId, ownerType);
	}

	public static Properties getProperties(
		PortletPreferences preferences, String prefix, boolean removePrefix) {

		Properties newProperties = new Properties();

		Enumeration<String> enu = preferences.getNames();

		while (enu.hasMoreElements()) {
			String key = enu.nextElement();

			if (key.startsWith(prefix)) {
				String value = preferences.getValue(key, StringPool.BLANK);

				if (removePrefix) {
					key = key.substring(prefix.length());
				}

				newProperties.setProperty(key, value);
			}
		}

		return newProperties;
	}

	public static Properties getProperties(
		String prefix, boolean removePrefix) {

		PortletPreferences preferences = getPreferences(true);

		return getProperties(preferences, prefix, removePrefix);
	}

	public static short getShort(long companyId, String name) {
		PortletPreferences preferences = getPreferences(companyId, true);

		return getShort(preferences, name);
	}

	public static short getShort(
		long companyId, String name, short defaultValue) {

		PortletPreferences preferences = getPreferences(companyId, true);

		return getShort(preferences, name, defaultValue);
	}

	public static short getShort(PortletPreferences preferences, String name) {
		return GetterUtil.getShort(getString(preferences, name));
	}

	public static short getShort(
		PortletPreferences preferences, String name, short defaultValue) {

		return GetterUtil.getShort(getString(preferences, name, defaultValue));
	}

	public static short getShort(String name) {
		PortletPreferences preferences = getPreferences(true);

		return getShort(preferences, name);
	}

	public static short getShort(String name, short defaultValue) {
		PortletPreferences preferences = getPreferences(true);

		return getShort(preferences, name, defaultValue);
	}

	public static String getString(long companyId, String name) {
		PortletPreferences preferences = getPreferences(companyId, true);

		return getString(preferences, name);
	}

	public static String getString(
		long companyId, String name, String defaultValue) {

		PortletPreferences preferences = getPreferences(companyId, true);

		return getString(preferences, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name) {

		String value = PropsUtil.get(name);

		return preferences.getValue(name, value);
	}

	public static String getString(
		PortletPreferences preferences, String name, boolean defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return String.valueOf(defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name, double defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return String.valueOf(defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name, int defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return String.valueOf(defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name, long defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return String.valueOf(defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name, short defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return String.valueOf(defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name, String defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return defaultValue;
	}

	public static String getString(String name) {
		PortletPreferences preferences = getPreferences(true);

		return getString(preferences, name);
	}

	public static String getString(String name, String defaultValue) {
		PortletPreferences preferences = getPreferences(true);

		return getString(preferences, name, defaultValue);
	}

	public static String[] getStringArray(
		long companyId, String name, String delimiter) {

		PortletPreferences preferences = getPreferences(companyId, true);

		return getStringArray(preferences, name, delimiter);
	}

	public static String[] getStringArray(
		long companyId, String name, String delimiter, String[] defaultValue) {

		PortletPreferences preferences = getPreferences(companyId, true);

		return getStringArray(preferences, name, delimiter, defaultValue);
	}

	public static String[] getStringArray(
		PortletPreferences preferences, String name, String delimiter) {

		String value = PropsUtil.get(name);

		value = preferences.getValue(name, value);

		return StringUtil.split(value, delimiter);
	}

	public static String[] getStringArray(
		PortletPreferences preferences, String name, String delimiter,
		String[] defaultValue) {

		String value = preferences.getValue(name, null);

		if (value == null) {
			return defaultValue;
		}

		return StringUtil.split(value, delimiter);
	}

	public static String[] getStringArray(String name, String delimiter) {
		PortletPreferences preferences = getPreferences(true);

		return getStringArray(preferences, name, delimiter);
	}

	public static String[] getStringArray(
		String name, String delimiter, String[] defaultValue) {

		PortletPreferences preferences = getPreferences(true);

		return getStringArray(preferences, name, delimiter, defaultValue);
	}

	public static String getStringFromNames(long companyId, String... names) {
		for (String name : names) {
			String value = getString(companyId, name);

			if (Validator.isNotNull(value)) {
				return value;
			}
		}

		return null;
	}

	@BeanReference(type = PortalPreferencesLocalService.class)
	private static PortalPreferencesLocalService _portalPreferencesLocalService;

}
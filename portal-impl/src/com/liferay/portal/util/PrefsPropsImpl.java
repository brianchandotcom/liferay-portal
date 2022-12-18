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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;

import java.util.Enumeration;
import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class PrefsPropsImpl implements PrefsProps {

	@Override
	public boolean getBoolean(long companyId, String name) {
		return getBoolean(getPreferences(companyId, true), name);
	}

	@Override
	public boolean getBoolean(
		long companyId, String name, boolean defaultValue) {

		return getBoolean(getPreferences(companyId, true), name, defaultValue);
	}

	@Override
	public boolean getBoolean(PortletPreferences preferences, String name) {
		return GetterUtil.getBoolean(getString(preferences, name));
	}

	@Override
	public boolean getBoolean(
		PortletPreferences preferences, String name, boolean defaultValue) {

		return GetterUtil.getBoolean(
			getString(preferences, name, defaultValue));
	}

	@Override
	public boolean getBoolean(String name) {
		return getBoolean(getPreferences(true), name);
	}

	@Override
	public boolean getBoolean(String name, boolean defaultValue) {
		return getBoolean(getPreferences(true), name, defaultValue);
	}

	@Override
	public String getContent(long companyId, String name) {
		return getContent(getPreferences(companyId, true), name);
	}

	@Override
	public String getContent(PortletPreferences preferences, String name) {
		String value = preferences.getValue(name, StringPool.BLANK);

		if (Validator.isNotNull(value)) {
			return value;
		}

		try {
			return StringUtil.read(
				PrefsPropsImpl.class.getClassLoader(), PropsUtil.get(name));
		}
		catch (IOException ioException) {
			_log.error(
				"Unable to read the content for " + PropsUtil.get(name),
				ioException);

			return null;
		}
	}

	@Override
	public String getContent(String name) {
		return getContent(getPreferences(true), name);
	}

	@Override
	public double getDouble(long companyId, String name) {
		return getDouble(getPreferences(companyId, true), name);
	}

	@Override
	public double getDouble(long companyId, String name, double defaultValue) {
		return getDouble(getPreferences(companyId, true), name, defaultValue);
	}

	@Override
	public double getDouble(PortletPreferences preferences, String name) {
		return GetterUtil.getDouble(getString(preferences, name));
	}

	@Override
	public double getDouble(
		PortletPreferences preferences, String name, double defaultValue) {

		return GetterUtil.getDouble(getString(preferences, name, defaultValue));
	}

	@Override
	public double getDouble(String name) {
		return getDouble(getPreferences(true), name);
	}

	@Override
	public double getDouble(String name, double defaultValue) {
		return getDouble(getPreferences(true), name, defaultValue);
	}

	@Override
	public int getInteger(long companyId, String name) {
		return getInteger(getPreferences(companyId, true), name);
	}

	@Override
	public int getInteger(long companyId, String name, int defaultValue) {
		return getInteger(getPreferences(companyId, true), name, defaultValue);
	}

	@Override
	public int getInteger(PortletPreferences preferences, String name) {
		return GetterUtil.getInteger(getString(preferences, name));
	}

	@Override
	public int getInteger(
		PortletPreferences preferences, String name, int defaultValue) {

		return GetterUtil.getInteger(
			getString(preferences, name, defaultValue));
	}

	@Override
	public int getInteger(String name) {
		return getInteger(getPreferences(true), name);
	}

	@Override
	public int getInteger(String name, int defaultValue) {
		return getInteger(getPreferences(true), name, defaultValue);
	}

	@Override
	public long getLong(long companyId, String name) {
		return getLong(getPreferences(companyId, true), name);
	}

	@Override
	public long getLong(long companyId, String name, long defaultValue) {
		return getLong(getPreferences(companyId, true), name, defaultValue);
	}

	@Override
	public long getLong(PortletPreferences preferences, String name) {
		return GetterUtil.getLong(getString(preferences, name));
	}

	@Override
	public long getLong(
		PortletPreferences preferences, String name, long defaultValue) {

		return GetterUtil.getLong(getString(preferences, name, defaultValue));
	}

	@Override
	public long getLong(String name) {
		return getLong(getPreferences(true), name);
	}

	@Override
	public long getLong(String name, long defaultValue) {
		return getLong(getPreferences(true), name, defaultValue);
	}

	@Override
	public PortletPreferences getPreferences() {
		return getPreferences(false);
	}

	@Override
	public PortletPreferences getPreferences(boolean readOnly) {
		return _portalPreferencesLocalService.getPreferences(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_COMPANY);
	}

	@Override
	public PortletPreferences getPreferences(long companyId) {
		return getPreferences(companyId, false);
	}

	@Override
	public PortletPreferences getPreferences(long companyId, boolean readOnly) {
		return _portalPreferencesLocalService.getPreferences(
			companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY);
	}

	@Override
	public Properties getProperties(
		PortletPreferences preferences, String prefix, boolean removePrefix) {

		Properties newProperties = new Properties();

		Enumeration<String> enumeration = preferences.getNames();

		while (enumeration.hasMoreElements()) {
			String key = enumeration.nextElement();

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

	@Override
	public Properties getProperties(String prefix, boolean removePrefix) {
		return getProperties(getPreferences(true), prefix, removePrefix);
	}

	@Override
	public short getShort(long companyId, String name) {
		return getShort(getPreferences(companyId, true), name);
	}

	@Override
	public short getShort(long companyId, String name, short defaultValue) {
		return getShort(getPreferences(companyId, true), name, defaultValue);
	}

	@Override
	public short getShort(PortletPreferences preferences, String name) {
		return GetterUtil.getShort(getString(preferences, name));
	}

	@Override
	public short getShort(
		PortletPreferences preferences, String name, short defaultValue) {

		return GetterUtil.getShort(getString(preferences, name, defaultValue));
	}

	@Override
	public short getShort(String name) {
		return getShort(getPreferences(true), name);
	}

	@Override
	public short getShort(String name, short defaultValue) {
		return getShort(getPreferences(true), name, defaultValue);
	}

	@Override
	public String getString(long companyId, String name) {
		return getString(getPreferences(companyId, true), name);
	}

	@Override
	public String getString(long companyId, String name, String defaultValue) {
		return getString(getPreferences(companyId, true), name, defaultValue);
	}

	@Override
	public String getString(PortletPreferences preferences, String name) {
		String value = PropsUtil.get(name);

		return preferences.getValue(name, value);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, boolean defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return String.valueOf(defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, double defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return String.valueOf(defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, int defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return String.valueOf(defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, long defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return String.valueOf(defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, short defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return String.valueOf(defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, String defaultValue) {

		String value = getString(preferences, name);

		if (value != null) {
			return value;
		}

		return defaultValue;
	}

	@Override
	public String getString(String name) {
		return getString(getPreferences(true), name);
	}

	@Override
	public String getString(String name, String defaultValue) {
		return getString(getPreferences(true), name, defaultValue);
	}

	@Override
	public String[] getStringArray(
		long companyId, String name, String delimiter) {

		return getStringArray(getPreferences(companyId, true), name, delimiter);
	}

	@Override
	public String[] getStringArray(
		long companyId, String name, String delimiter, String[] defaultValue) {

		return getStringArray(
			getPreferences(companyId, true), name, delimiter, defaultValue);
	}

	@Override
	public String[] getStringArray(
		PortletPreferences preferences, String name, String delimiter) {

		String value = PropsUtil.get(name);

		return StringUtil.split(preferences.getValue(name, value), delimiter);
	}

	@Override
	public String[] getStringArray(
		PortletPreferences preferences, String name, String delimiter,
		String[] defaultValue) {

		String value = preferences.getValue(name, null);

		if (value == null) {
			return defaultValue;
		}

		return StringUtil.split(value, delimiter);
	}

	@Override
	public String[] getStringArray(String name, String delimiter) {
		return getStringArray(getPreferences(true), name, delimiter);
	}

	@Override
	public String[] getStringArray(
		String name, String delimiter, String[] defaultValue) {

		return getStringArray(
			getPreferences(true), name, delimiter, defaultValue);
	}

	@Override
	public String getStringFromNames(long companyId, String... names) {
		for (String name : names) {
			String value = getString(companyId, name);

			if (Validator.isNotNull(value)) {
				return value;
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(PrefsPropsImpl.class);

	@BeanReference(type = PortalPreferencesLocalService.class)
	private PortalPreferencesLocalService _portalPreferencesLocalService;

}
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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class PrefsPropsUtil {

	public static boolean getBoolean(long companyId, String name) {
		return getPrefsProps().getBoolean(companyId, name);
	}

	public static boolean getBoolean(
		long companyId, String name, boolean defaultValue) {

		return getPrefsProps().getBoolean(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getBoolean(PortletPreferences, String)}
	 */
	@Deprecated
	public static boolean getBoolean(
		PortletPreferences preferences, long companyId, String name) {

		return getBoolean(preferences, name);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getBoolean(PortletPreferences, String, boolean)}
	 */
	@Deprecated
	public static boolean getBoolean(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		return getBoolean(preferences, name, defaultValue);
	}

	public static boolean getBoolean(
		PortletPreferences preferences, String name) {

		return getPrefsProps().getBoolean(preferences, name);
	}

	public static boolean getBoolean(
		PortletPreferences preferences, String name, boolean defaultValue) {

		return getPrefsProps().getBoolean(preferences, name, defaultValue);
	}

	public static boolean getBoolean(String name) {
		return getPrefsProps().getBoolean(name);
	}

	public static boolean getBoolean(String name, boolean defaultValue) {
		return getPrefsProps().getBoolean(name, defaultValue);
	}

	public static String getContent(long companyId, String name) {
		return getPrefsProps().getContent(companyId, name);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getContent(PortletPreferences, String)}
	 */
	@Deprecated
	public static String getContent(
		PortletPreferences preferences, long companyId, String name) {

		return getContent(preferences, name);
	}

	public static String getContent(
		PortletPreferences preferences, String name) {

		return getPrefsProps().getContent(preferences, name);
	}

	public static String getContent(String name) {
		return getPrefsProps().getContent(name);
	}

	public static double getDouble(long companyId, String name) {
		return getPrefsProps().getDouble(companyId, name);
	}

	public static double getDouble(
		long companyId, String name, double defaultValue) {

		return getPrefsProps().getDouble(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getDouble(PortletPreferences, String)}
	 */
	@Deprecated
	public static double getDouble(
		PortletPreferences preferences, long companyId, String name) {

		return getDouble(preferences, name);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getDouble(PortletPreferences, String, double)}
	 */
	@Deprecated
	public static double getDouble(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return getDouble(preferences, name, defaultValue);
	}

	public static double getDouble(
		PortletPreferences preferences, String name) {

		return getPrefsProps().getDouble(preferences, name);
	}

	public static double getDouble(
		PortletPreferences preferences, String name, double defaultValue) {

		return getPrefsProps().getDouble(preferences, name, defaultValue);
	}

	public static double getDouble(String name) {
		return getPrefsProps().getDouble(name);
	}

	public static double getDouble(String name, double defaultValue) {
		return getPrefsProps().getDouble(name, defaultValue);
	}

	public static int getInteger(long companyId, String name) {
		return getPrefsProps().getInteger(companyId, name);
	}

	public static int getInteger(
		long companyId, String name, int defaultValue) {

		return getPrefsProps().getInteger(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getInteger(PortletPreferences, String)}
	 */
	@Deprecated
	public static int getInteger(
		PortletPreferences preferences, long companyId, String name) {

		return getInteger(preferences, name);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getInteger(PortletPreferences, String, int)}
	 */
	@Deprecated
	public static int getInteger(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return getInteger(preferences, name, defaultValue);
	}

	public static int getInteger(PortletPreferences preferences, String name) {
		return getPrefsProps().getInteger(preferences, name);
	}

	public static int getInteger(
		PortletPreferences preferences, String name, int defaultValue) {

		return getPrefsProps().getInteger(preferences, name, defaultValue);
	}

	public static int getInteger(String name) {
		return getPrefsProps().getInteger(name);
	}

	public static int getInteger(String name, int defaultValue) {
		return getPrefsProps().getInteger(name, defaultValue);
	}

	public static long getLong(long companyId, String name) {
		return getPrefsProps().getLong(companyId, name);
	}

	public static long getLong(long companyId, String name, long defaultValue) {
		return getPrefsProps().getLong(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getLong(PortletPreferences,
	 *             String)}
	 */
	@Deprecated
	public static long getLong(
		PortletPreferences preferences, long companyId, String name) {

		return getLong(preferences, name);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getLong(PortletPreferences,
	 *             String, long)}
	 */
	@Deprecated
	public static long getLong(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return getLong(preferences, name, defaultValue);
	}

	public static long getLong(PortletPreferences preferences, String name) {
		return getPrefsProps().getLong(preferences, name);
	}

	public static long getLong(
		PortletPreferences preferences, String name, long defaultValue) {

		return getPrefsProps().getLong(preferences, name, defaultValue);
	}

	public static long getLong(String name) {
		return getPrefsProps().getLong(name);
	}

	public static long getLong(String name, long defaultValue) {
		return getPrefsProps().getLong(name, defaultValue);
	}

	public static PortletPreferences getPreferences() {
		return getPrefsProps().getPreferences();
	}

	public static PortletPreferences getPreferences(boolean readOnly) {
		return getPrefsProps().getPreferences(readOnly);
	}

	public static PortletPreferences getPreferences(long companyId) {
		return getPrefsProps().getPreferences(companyId);
	}

	public static PortletPreferences getPreferences(
		long companyId, boolean readOnly) {

		return getPrefsProps().getPreferences(companyId, readOnly);
	}

	public static PrefsProps getPrefsProps() {
		PortalRuntimePermission.checkGetBeanProperty(PrefsPropsUtil.class);

		return _prefsProps;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getProperties(PortletPreferences, String, boolean)}
	 */
	@Deprecated
	public static Properties getProperties(
		PortletPreferences preferences, long companyId, String prefix,
		boolean removePrefix) {

		return getProperties(preferences, prefix, removePrefix);
	}

	public static Properties getProperties(
		PortletPreferences preferences, String prefix, boolean removePrefix) {

		return getPrefsProps().getProperties(preferences, prefix, removePrefix);
	}

	public static Properties getProperties(
		String prefix, boolean removePrefix) {

		return getPrefsProps().getProperties(prefix, removePrefix);
	}

	public static short getShort(long companyId, String name) {
		return getPrefsProps().getShort(companyId, name);
	}

	public static short getShort(
		long companyId, String name, short defaultValue) {

		return getPrefsProps().getShort(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getShort(PortletPreferences,
	 *             String)}
	 */
	@Deprecated
	public static short getShort(
		PortletPreferences preferences, long companyId, String name) {

		return getShort(preferences, name);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getShort(PortletPreferences,
	 *             String, short)}
	 */
	@Deprecated
	public static short getShort(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return getShort(preferences, name, defaultValue);
	}

	public static short getShort(PortletPreferences preferences, String name) {
		return getPrefsProps().getShort(preferences, name);
	}

	public static short getShort(
		PortletPreferences preferences, String name, short defaultValue) {

		return getPrefsProps().getShort(preferences, name, defaultValue);
	}

	public static short getShort(String name) {
		return getPrefsProps().getShort(name);
	}

	public static short getShort(String name, short defaultValue) {
		return getPrefsProps().getShort(name, defaultValue);
	}

	public static String getString(long companyId, String name) {
		return getPrefsProps().getString(companyId, name);
	}

	public static String getString(
		long companyId, String name, String defaultValue) {

		return getPrefsProps().getString(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String)}
	 */
	@Deprecated
	public static String getString(
		PortletPreferences preferences, long companyId, String name) {

		return getString(preferences, name);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, boolean)}
	 */
	@Deprecated
	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, double)}
	 */
	@Deprecated
	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, int)}
	 */
	@Deprecated
	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, long)}
	 */
	@Deprecated
	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, short)}
	 */
	@Deprecated
	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, String)}
	 */
	@Deprecated
	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		String defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name) {

		return getPrefsProps().getString(preferences, name);
	}

	public static String getString(
		PortletPreferences preferences, String name, boolean defaultValue) {

		return getPrefsProps().getString(preferences, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name, double defaultValue) {

		return getPrefsProps().getString(preferences, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name, int defaultValue) {

		return getPrefsProps().getString(preferences, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name, long defaultValue) {

		return getPrefsProps().getString(preferences, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name, short defaultValue) {

		return getPrefsProps().getString(preferences, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, String name, String defaultValue) {

		return getPrefsProps().getString(preferences, name, defaultValue);
	}

	public static String getString(String name) {
		return getPrefsProps().getString(name);
	}

	public static String getString(String name, String defaultValue) {
		return getPrefsProps().getString(name, defaultValue);
	}

	public static String[] getStringArray(
		long companyId, String name, String delimiter) {

		return getPrefsProps().getStringArray(companyId, name, delimiter);
	}

	public static String[] getStringArray(
		long companyId, String name, String delimiter, String[] defaultValue) {

		return getPrefsProps().getStringArray(
			companyId, name, delimiter, defaultValue);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getStringArray(PortletPreferences, String, String)}
	 */
	@Deprecated
	public static String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter) {

		return getStringArray(preferences, name, delimiter);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getStringArray(PortletPreferences, String, String,
	 *             String[])}
	 */
	@Deprecated
	public static String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter, String[] defaultValue) {

		return getStringArray(preferences, name, delimiter, defaultValue);
	}

	public static String[] getStringArray(
		PortletPreferences preferences, String name, String delimiter) {

		return getPrefsProps().getStringArray(preferences, name, delimiter);
	}

	public static String[] getStringArray(
		PortletPreferences preferences, String name, String delimiter,
		String[] defaultValue) {

		return getPrefsProps().getStringArray(
			preferences, name, delimiter, defaultValue);
	}

	public static String[] getStringArray(String name, String delimiter) {
		return getPrefsProps().getStringArray(name, delimiter);
	}

	public static String[] getStringArray(
		String name, String delimiter, String[] defaultValue) {

		return getPrefsProps().getStringArray(name, delimiter, defaultValue);
	}

	public static String getStringFromNames(long companyId, String... names) {
		return getPrefsProps().getStringFromNames(companyId, names);
	}

	public void setPrefsProps(PrefsProps prefsProps) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_prefsProps = prefsProps;
	}

	private static PrefsProps _prefsProps;

}
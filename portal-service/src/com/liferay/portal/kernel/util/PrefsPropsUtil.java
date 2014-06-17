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

	public static boolean getBoolean(
		PortletPreferences preferences, long companyId, String name) {

		return getPrefsProps().getBoolean(preferences, companyId, name);
	}

	public static boolean getBoolean(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		return getPrefsProps().getBoolean(
			preferences, companyId, name, defaultValue);
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

	public static String getContent(
		PortletPreferences preferences, long companyId, String name) {

		return getPrefsProps().getContent(preferences, companyId, name);
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

	public static double getDouble(
		PortletPreferences preferences, long companyId, String name) {

		return getPrefsProps().getDouble(preferences, companyId, name);
	}

	public static double getDouble(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return getPrefsProps().getDouble(
			preferences, companyId, name, defaultValue);
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

	public static int getInteger(
		PortletPreferences preferences, long companyId, String name) {

		return getPrefsProps().getInteger(preferences, companyId, name);
	}

	public static int getInteger(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return getPrefsProps().getInteger(
			preferences, companyId, name, defaultValue);
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

	public static long getLong(
		PortletPreferences preferences, long companyId, String name) {

		return getPrefsProps().getLong(preferences, companyId, name);
	}

	public static long getLong(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return getPrefsProps().getLong(
			preferences, companyId, name, defaultValue);
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

	public static Properties getProperties(
		PortletPreferences preferences, long companyId, String prefix,
		boolean removePrefix) {

		return getPrefsProps().getProperties(
			preferences, companyId, prefix, removePrefix);
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

	public static short getShort(
		PortletPreferences preferences, long companyId, String name) {

		return getPrefsProps().getShort(preferences, companyId, name);
	}

	public static short getShort(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return getPrefsProps().getShort(
			preferences, companyId, name, defaultValue);
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

	public static String getString(
		PortletPreferences preferences, long companyId, String name) {

		return getPrefsProps().getString(preferences, companyId, name);
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		return getPrefsProps().getString(
			preferences, companyId, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return getPrefsProps().getString(
			preferences, companyId, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return getPrefsProps().getString(
			preferences, companyId, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return getPrefsProps().getString(
			preferences, companyId, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return getPrefsProps().getString(
			preferences, companyId, name, defaultValue);
	}

	public static String getString(
		PortletPreferences preferences, long companyId, String name,
		String defaultValue) {

		return getPrefsProps().getString(
			preferences, companyId, name, defaultValue);
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

	public static String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter) {

		return getPrefsProps().getStringArray(
			preferences, companyId, name, delimiter);
	}

	public static String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter, String[] defaultValue) {

		return getPrefsProps().getStringArray(
			preferences, companyId, name, delimiter, defaultValue);
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
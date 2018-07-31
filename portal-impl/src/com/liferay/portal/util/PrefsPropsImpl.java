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

import com.liferay.portal.kernel.util.PrefsProps;

import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public class PrefsPropsImpl implements PrefsProps {

	@Override
	public boolean getBoolean(long companyId, String name) {
		return PrefsPropsUtil.getBoolean(companyId, name);
	}

	@Override
	public boolean getBoolean(
		long companyId, String name, boolean defaultValue) {

		return PrefsPropsUtil.getBoolean(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getBoolean(PortletPreferences, String)}
	 */
	@Deprecated
	@Override
	public boolean getBoolean(
		PortletPreferences preferences, long companyId, String name) {

		return getBoolean(preferences, name);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getBoolean(PortletPreferences, String, boolean)}
	 */
	@Deprecated
	@Override
	public boolean getBoolean(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		return getBoolean(preferences, name, defaultValue);
	}

	@Override
	public boolean getBoolean(PortletPreferences preferences, String name) {
		return PrefsPropsUtil.getBoolean(preferences, name);
	}

	@Override
	public boolean getBoolean(
		PortletPreferences preferences, String name, boolean defaultValue) {

		return PrefsPropsUtil.getBoolean(preferences, name, defaultValue);
	}

	@Override
	public boolean getBoolean(String name) {
		return PrefsPropsUtil.getBoolean(name);
	}

	@Override
	public boolean getBoolean(String name, boolean defaultValue) {
		return PrefsPropsUtil.getBoolean(name, defaultValue);
	}

	@Override
	public String getContent(long companyId, String name) {
		return PrefsPropsUtil.getContent(companyId, name);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getContent(PortletPreferences, String)}
	 */
	@Deprecated
	@Override
	public String getContent(
		PortletPreferences preferences, long companyId, String name) {

		return getContent(preferences, name);
	}

	@Override
	public String getContent(PortletPreferences preferences, String name) {
		return PrefsPropsUtil.getContent(preferences, name);
	}

	@Override
	public String getContent(String name) {
		return PrefsPropsUtil.getContent(name);
	}

	@Override
	public double getDouble(long companyId, String name) {
		return PrefsPropsUtil.getDouble(companyId, name);
	}

	@Override
	public double getDouble(long companyId, String name, double defaultValue) {
		return PrefsPropsUtil.getDouble(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getDouble(PortletPreferences, String)}
	 */
	@Deprecated
	@Override
	public double getDouble(
		PortletPreferences preferences, long companyId, String name) {

		return getDouble(preferences, name);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getDouble(PortletPreferences, String, double)}
	 */
	@Deprecated
	@Override
	public double getDouble(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return getDouble(preferences, name, defaultValue);
	}

	@Override
	public double getDouble(PortletPreferences preferences, String name) {
		return PrefsPropsUtil.getDouble(preferences, name);
	}

	@Override
	public double getDouble(
		PortletPreferences preferences, String name, double defaultValue) {

		return PrefsPropsUtil.getDouble(preferences, name, defaultValue);
	}

	@Override
	public double getDouble(String name) {
		return PrefsPropsUtil.getDouble(name);
	}

	@Override
	public double getDouble(String name, double defaultValue) {
		return PrefsPropsUtil.getDouble(name, defaultValue);
	}

	@Override
	public int getInteger(long companyId, String name) {
		return PrefsPropsUtil.getInteger(companyId, name);
	}

	@Override
	public int getInteger(long companyId, String name, int defaultValue) {
		return PrefsPropsUtil.getInteger(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getInteger(PortletPreferences, String)}
	 */
	@Deprecated
	@Override
	public int getInteger(
		PortletPreferences preferences, long companyId, String name) {

		return getInteger(preferences, name);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getInteger(PortletPreferences, String, int)}
	 */
	@Deprecated
	@Override
	public int getInteger(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return getInteger(preferences, name, defaultValue);
	}

	@Override
	public int getInteger(PortletPreferences preferences, String name) {
		return PrefsPropsUtil.getInteger(preferences, name);
	}

	@Override
	public int getInteger(
		PortletPreferences preferences, String name, int defaultValue) {

		return PrefsPropsUtil.getInteger(preferences, name, defaultValue);
	}

	@Override
	public int getInteger(String name) {
		return PrefsPropsUtil.getInteger(name);
	}

	@Override
	public int getInteger(String name, int defaultValue) {
		return PrefsPropsUtil.getInteger(name, defaultValue);
	}

	@Override
	public long getLong(long companyId, String name) {
		return PrefsPropsUtil.getLong(companyId, name);
	}

	@Override
	public long getLong(long companyId, String name, long defaultValue) {
		return PrefsPropsUtil.getLong(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getLong(PortletPreferences, String)}
	 */
	@Deprecated
	@Override
	public long getLong(
		PortletPreferences preferences, long companyId, String name) {

		return getLong(preferences, name);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getLong(PortletPreferences, String, long)}
	 */
	@Deprecated
	@Override
	public long getLong(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return getLong(preferences, name, defaultValue);
	}

	@Override
	public long getLong(PortletPreferences preferences, String name) {
		return PrefsPropsUtil.getLong(preferences, name);
	}

	@Override
	public long getLong(
		PortletPreferences preferences, String name, long defaultValue) {

		return PrefsPropsUtil.getLong(preferences, name, defaultValue);
	}

	@Override
	public long getLong(String name) {
		return PrefsPropsUtil.getLong(name);
	}

	@Override
	public long getLong(String name, long defaultValue) {
		return PrefsPropsUtil.getLong(name, defaultValue);
	}

	@Override
	public PortletPreferences getPreferences() {
		return PrefsPropsUtil.getPreferences();
	}

	@Override
	public PortletPreferences getPreferences(boolean readOnly) {
		return PrefsPropsUtil.getPreferences(readOnly);
	}

	@Override
	public PortletPreferences getPreferences(long companyId) {
		return PrefsPropsUtil.getPreferences(companyId);
	}

	@Override
	public PortletPreferences getPreferences(long companyId, boolean readOnly) {
		return PrefsPropsUtil.getPreferences(companyId, readOnly);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getProperties(PortletPreferences, String, boolean)}
	 */
	@Deprecated
	@Override
	public Properties getProperties(
		PortletPreferences preferences, long companyId, String prefix,
		boolean removePrefix) {

		return getProperties(preferences, prefix, removePrefix);
	}

	@Override
	public Properties getProperties(
		PortletPreferences preferences, String prefix, boolean removePrefix) {

		return PrefsPropsUtil.getProperties(preferences, prefix, removePrefix);
	}

	@Override
	public Properties getProperties(String prefix, boolean removePrefix) {
		return PrefsPropsUtil.getProperties(prefix, removePrefix);
	}

	@Override
	public short getShort(long companyId, String name) {
		return PrefsPropsUtil.getShort(companyId, name);
	}

	@Override
	public short getShort(long companyId, String name, short defaultValue) {
		return PrefsPropsUtil.getShort(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getShort(PortletPreferences, String)}
	 */
	@Deprecated
	@Override
	public short getShort(
		PortletPreferences preferences, long companyId, String name) {

		return getShort(preferences, name);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getShort(PortletPreferences, String, short)}
	 */
	@Deprecated
	@Override
	public short getShort(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return getShort(preferences, name, defaultValue);
	}

	@Override
	public short getShort(PortletPreferences preferences, String name) {
		return PrefsPropsUtil.getShort(preferences, name);
	}

	@Override
	public short getShort(
		PortletPreferences preferences, String name, short defaultValue) {

		return PrefsPropsUtil.getShort(preferences, name, defaultValue);
	}

	@Override
	public short getShort(String name) {
		return PrefsPropsUtil.getShort(name);
	}

	@Override
	public short getShort(String name, short defaultValue) {
		return PrefsPropsUtil.getShort(name, defaultValue);
	}

	@Override
	public String getString(long companyId, String name) {
		return PrefsPropsUtil.getString(companyId, name);
	}

	@Override
	public String getString(long companyId, String name, String defaultValue) {
		return PrefsPropsUtil.getString(companyId, name, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getString(PortletPreferences, String)}
	 */
	@Deprecated
	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name) {

		return getString(preferences, name);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getString(PortletPreferences, String, boolean)}
	 */
	@Deprecated
	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getString(PortletPreferences, String, double)}
	 */
	@Deprecated
	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getString(PortletPreferences, String, int)}
	 */
	@Deprecated
	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getString(PortletPreferences, String, long)}
	 */
	@Deprecated
	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getString(PortletPreferences, String, short)}
	 */
	@Deprecated
	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getString(PortletPreferences, String, String)}
	 */
	@Deprecated
	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		String defaultValue) {

		return getString(preferences, name, defaultValue);
	}

	@Override
	public String getString(PortletPreferences preferences, String name) {
		return PrefsPropsUtil.getString(preferences, name);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, boolean defaultValue) {

		return PrefsPropsUtil.getString(preferences, name, defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, double defaultValue) {

		return PrefsPropsUtil.getString(preferences, name, defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, int defaultValue) {

		return PrefsPropsUtil.getString(preferences, name, defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, long defaultValue) {

		return PrefsPropsUtil.getString(preferences, name, defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, short defaultValue) {

		return PrefsPropsUtil.getString(preferences, name, defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, String name, String defaultValue) {

		return PrefsPropsUtil.getString(preferences, name, defaultValue);
	}

	@Override
	public String getString(String name) {
		return PrefsPropsUtil.getString(name);
	}

	@Override
	public String getString(String name, String defaultValue) {
		return PrefsPropsUtil.getString(name, defaultValue);
	}

	@Override
	public String[] getStringArray(
		long companyId, String name, String delimiter) {

		return PrefsPropsUtil.getStringArray(companyId, name, delimiter);
	}

	@Override
	public String[] getStringArray(
		long companyId, String name, String delimiter, String[] defaultValue) {

		return PrefsPropsUtil.getStringArray(
			companyId, name, delimiter, defaultValue);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getStringArray(PortletPreferences, String, String)}
	 */
	@Deprecated
	@Override
	public String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter) {

		return getStringArray(preferences, name, delimiter);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getStringArray(PortletPreferences, String, String,
	 *             String[])}
	 */
	@Deprecated
	@Override
	public String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter, String[] defaultValue) {

		return getStringArray(preferences, name, delimiter, defaultValue);
	}

	@Override
	public String[] getStringArray(
		PortletPreferences preferences, String name, String delimiter) {

		return PrefsPropsUtil.getStringArray(preferences, name, delimiter);
	}

	@Override
	public String[] getStringArray(
		PortletPreferences preferences, String name, String delimiter,
		String[] defaultValue) {

		return PrefsPropsUtil.getStringArray(
			preferences, name, delimiter, defaultValue);
	}

	@Override
	public String[] getStringArray(String name, String delimiter) {
		return PrefsPropsUtil.getStringArray(name, delimiter);
	}

	@Override
	public String[] getStringArray(
		String name, String delimiter, String[] defaultValue) {

		return PrefsPropsUtil.getStringArray(name, delimiter, defaultValue);
	}

	@Override
	public String getStringFromNames(long companyId, String... names) {
		return PrefsPropsUtil.getStringFromNames(companyId, names);
	}

}
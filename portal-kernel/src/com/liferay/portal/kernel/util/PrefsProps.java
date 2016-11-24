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

import aQute.bnd.annotation.ProviderType;

import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface PrefsProps {

	public boolean getBoolean(long companyId, String name);

	public boolean getBoolean(
		long companyId, String name, boolean defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getBoolean(PortletPreferences, String)}
	 */
	@Deprecated
	public boolean getBoolean(
		PortletPreferences preferences, long companyId, String name);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getBoolean(PortletPreferences, String, boolean)}
	 */
	@Deprecated
	public boolean getBoolean(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue);

	public boolean getBoolean(PortletPreferences preferences, String name);

	public boolean getBoolean(
		PortletPreferences preferences, String name, boolean defaultValue);

	public boolean getBoolean(String name);

	public boolean getBoolean(String name, boolean defaultValue);

	public String getContent(long companyId, String name);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getContent(PortletPreferences, String)}
	 */
	@Deprecated
	public String getContent(
		PortletPreferences preferences, long companyId, String name);

	public String getContent(PortletPreferences preferences, String name);

	public String getContent(String name);

	public double getDouble(long companyId, String name);

	public double getDouble(long companyId, String name, double defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getDouble(PortletPreferences, String)}
	 */
	@Deprecated
	public double getDouble(
		PortletPreferences preferences, long companyId, String name);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getDouble(PortletPreferences, String, double)}
	 */
	@Deprecated
	public double getDouble(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue);

	public double getDouble(PortletPreferences preferences, String name);

	public double getDouble(
		PortletPreferences preferences, String name, double defaultValue);

	public double getDouble(String name);

	public double getDouble(String name, double defaultValue);

	public int getInteger(long companyId, String name);

	public int getInteger(long companyId, String name, int defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getInteger(PortletPreferences, String)}
	 */
	@Deprecated
	public int getInteger(
		PortletPreferences preferences, long companyId, String name);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getInteger(PortletPreferences, String, int)}
	 */
	@Deprecated
	public int getInteger(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue);

	public int getInteger(PortletPreferences preferences, String name);

	public int getInteger(
		PortletPreferences preferences, String name, int defaultValue);

	public int getInteger(String name);

	public int getInteger(String name, int defaultValue);

	public long getLong(long companyId, String name);

	public long getLong(long companyId, String name, long defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getLong(PortletPreferences,
	 *             String)}
	 */
	@Deprecated
	public long getLong(
		PortletPreferences preferences, long companyId, String name);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getLong(PortletPreferences,
	 *             String, long)}
	 */
	@Deprecated
	public long getLong(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue);

	public long getLong(PortletPreferences preferences, String name);

	public long getLong(
		PortletPreferences preferences, String name, long defaultValue);

	public long getLong(String name);

	public long getLong(String name, long defaultValue);

	public PortletPreferences getPreferences();

	public PortletPreferences getPreferences(boolean readOnly);

	public PortletPreferences getPreferences(long companyId);

	public PortletPreferences getPreferences(long companyId, boolean readOnly);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getProperties(PortletPreferences, String, boolean)}
	 */
	@Deprecated
	public Properties getProperties(
		PortletPreferences preferences, long companyId, String prefix,
		boolean removePrefix);

	public Properties getProperties(
		PortletPreferences preferences, String prefix, boolean removePrefix);

	public Properties getProperties(String prefix, boolean removePrefix);

	public short getShort(long companyId, String name);

	public short getShort(long companyId, String name, short defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getShort(PortletPreferences,
	 *             String)}
	 */
	@Deprecated
	public short getShort(
		PortletPreferences preferences, long companyId, String name);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getShort(PortletPreferences,
	 *             String, short)}
	 */
	@Deprecated
	public short getShort(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue);

	public short getShort(PortletPreferences preferences, String name);

	public short getShort(
		PortletPreferences preferences, String name, short defaultValue);

	public short getShort(String name);

	public short getShort(String name, short defaultValue);

	public String getString(long companyId, String name);

	public String getString(long companyId, String name, String defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String)}
	 */
	@Deprecated
	public String getString(
		PortletPreferences preferences, long companyId, String name);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, boolean)}
	 */
	@Deprecated
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, double)}
	 */
	@Deprecated
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, int)}
	 */
	@Deprecated
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, long)}
	 */
	@Deprecated
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, short)}
	 */
	@Deprecated
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getString(PortletPreferences, String, String)}
	 */
	@Deprecated
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		String defaultValue);

	public String getString(PortletPreferences preferences, String name);

	public String getString(
		PortletPreferences preferences, String name, boolean defaultValue);

	public String getString(
		PortletPreferences preferences, String name, double defaultValue);

	public String getString(
		PortletPreferences preferences, String name, int defaultValue);

	public String getString(
		PortletPreferences preferences, String name, long defaultValue);

	public String getString(
		PortletPreferences preferences, String name, short defaultValue);

	public String getString(
		PortletPreferences preferences, String name, String defaultValue);

	public String getString(String name);

	public String getString(String name, String defaultValue);

	public String[] getStringArray(
		long companyId, String name, String delimiter);

	public String[] getStringArray(
		long companyId, String name, String delimiter, String[] defaultValue);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getStringArray(PortletPreferences, String, String)}
	 */
	@Deprecated
	public String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link
	 *             #getStringArray(PortletPreferences, String, String,
	 *             String[])}
	 */
	@Deprecated
	public String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter, String[] defaultValue);

	public String[] getStringArray(
		PortletPreferences preferences, String name, String delimiter);

	public String[] getStringArray(
		PortletPreferences preferences, String name, String delimiter,
		String[] defaultValue);

	public String[] getStringArray(String name, String delimiter);

	public String[] getStringArray(
		String name, String delimiter, String[] defaultValue);

	public String getStringFromNames(long companyId, String... names);

}
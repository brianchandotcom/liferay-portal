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

import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
public interface PrefsProps {

	public boolean getBoolean(long companyId, String name);

	public boolean getBoolean(
		long companyId, String name, boolean defaultValue);

	public boolean getBoolean(
		PortletPreferences preferences, long companyId, String name);

	public boolean getBoolean(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue);

	public boolean getBoolean(String name);

	public boolean getBoolean(String name, boolean defaultValue);

	public String getContent(long companyId, String name);

	public String getContent(
		PortletPreferences preferences, long companyId, String name);

	public String getContent(String name);

	public double getDouble(long companyId, String name);

	public double getDouble(long companyId, String name, double defaultValue);

	public double getDouble(
		PortletPreferences preferences, long companyId, String name);

	public double getDouble(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue);

	public double getDouble(String name);

	public double getDouble(String name, double defaultValue);

	public int getInteger(long companyId, String name);

	public int getInteger(long companyId, String name, int defaultValue);

	public int getInteger(
		PortletPreferences preferences, long companyId, String name);

	public int getInteger(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue);

	public int getInteger(String name);

	public int getInteger(String name, int defaultValue);

	public long getLong(long companyId, String name);

	public long getLong(long companyId, String name, long defaultValue);

	public long getLong(
		PortletPreferences preferences, long companyId, String name);

	public long getLong(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue);

	public long getLong(String name);

	public long getLong(String name, long defaultValue);

	public PortletPreferences getPreferences();

	public PortletPreferences getPreferences(boolean readOnly);

	public PortletPreferences getPreferences(long companyId);

	public PortletPreferences getPreferences(long companyId, boolean readOnly);

	public Properties getProperties(
		PortletPreferences preferences, long companyId, String prefix,
		boolean removePrefix);

	public Properties getProperties(String prefix, boolean removePrefix);

	public short getShort(long companyId, String name);

	public short getShort(long companyId, String name, short defaultValue);

	public short getShort(
		PortletPreferences preferences, long companyId, String name);

	public short getShort(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue);

	public short getShort(String name);

	public short getShort(String name, short defaultValue);

	public String getString(long companyId, String name);

	public String getString(long companyId, String name, String defaultValue);

	public String getString(
		PortletPreferences preferences, long companyId, String name);

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue);

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue);

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue);

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue);

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue);

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		String defaultValue);

	public String getString(String name);

	public String getString(String name, String defaultValue);

	public String[] getStringArray(
		long companyId, String name, String delimiter);

	public String[] getStringArray(
		long companyId, String name, String delimiter, String[] defaultValue);

	public String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter);

	public String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter, String[] defaultValue);

	public String[] getStringArray(String name, String delimiter);

	public String[] getStringArray(
		String name, String delimiter, String[] defaultValue);

	public String getStringFromNames(long companyId, String... names);

}
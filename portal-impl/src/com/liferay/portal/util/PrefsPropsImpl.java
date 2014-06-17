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

import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.PrefsProps;

import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
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

	@Override
	public boolean getBoolean(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getBoolean(preferences, companyId, name);
	}

	@Override
	public boolean getBoolean(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		return PrefsPropsUtil.getBoolean(
			preferences, companyId, name, defaultValue);
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

	@Override
	public String getContent(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getContent(preferences, companyId, name);
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

	@Override
	public double getDouble(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getDouble(preferences, companyId, name);
	}

	@Override
	public double getDouble(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return PrefsPropsUtil.getDouble(
			preferences, companyId, name, defaultValue);
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

	@Override
	public int getInteger(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getInteger(preferences, companyId, name);
	}

	@Override
	public int getInteger(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return PrefsPropsUtil.getInteger(
			preferences, companyId, name, defaultValue);
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

	@Override
	public long getLong(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getLong(preferences, companyId, name);
	}

	@Override
	public long getLong(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return PrefsPropsUtil.getLong(
			preferences, companyId, name, defaultValue);
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

	@Override
	public Properties getProperties(
		PortletPreferences preferences, long companyId, String prefix,
		boolean removePrefix) {

		return PrefsPropsUtil.getProperties(
			preferences, companyId, prefix, removePrefix);
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

	@Override
	public short getShort(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getShort(preferences, companyId, name);
	}

	@Override
	public short getShort(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return PrefsPropsUtil.getShort(
			preferences, companyId, name, defaultValue);
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

	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getString(preferences, companyId, name);
	}

	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
	}

	@Override
	public String getString(
		PortletPreferences preferences, long companyId, String name,
		String defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
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

	@Override
	public String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter) {

		return PrefsPropsUtil.getStringArray(
			preferences, companyId, name, delimiter);
	}

	@Override
	public String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter, String[] defaultValue) {

		return PrefsPropsUtil.getStringArray(
			preferences, companyId, name, delimiter, defaultValue);
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
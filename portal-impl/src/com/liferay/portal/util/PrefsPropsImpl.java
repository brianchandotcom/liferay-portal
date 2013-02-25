/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.security.pacl.DoPrivileged;
import com.liferay.portal.kernel.util.PrefsProps;

import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Brian Wing Shun Chan
 */
@DoPrivileged
public class PrefsPropsImpl implements PrefsProps {

	public boolean getBoolean(long companyId, String name)
		throws SystemException {

		return PrefsPropsUtil.getBoolean(companyId, name);
	}

	public boolean getBoolean(long companyId, String name, boolean defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getBoolean(companyId, name, defaultValue);
	}

	public boolean getBoolean(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getBoolean(preferences, companyId, name);
	}

	public boolean getBoolean(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		return PrefsPropsUtil.getBoolean(
			preferences, companyId, name, defaultValue);
	}

	public boolean getBoolean(String name) throws SystemException {
		return PrefsPropsUtil.getBoolean(name);
	}

	public boolean getBoolean(String name, boolean defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getBoolean(name, defaultValue);
	}

	public String getContent(long companyId, String name)
		throws SystemException {

		return PrefsPropsUtil.getContent(companyId, name);
	}

	public String getContent(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getContent(preferences, companyId, name);
	}

	public String getContent(String name) throws SystemException {
		return PrefsPropsUtil.getContent(name);
	}

	public double getDouble(long companyId, String name)
		throws SystemException {

		return PrefsPropsUtil.getDouble(companyId, name);
	}

	public double getDouble(long companyId, String name, double defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getDouble(companyId, name, defaultValue);
	}

	public double getDouble(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getDouble(preferences, companyId, name);
	}

	public double getDouble(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return PrefsPropsUtil.getDouble(
			preferences, companyId, name, defaultValue);
	}

	public double getDouble(String name) throws SystemException {
		return PrefsPropsUtil.getDouble(name);
	}

	public double getDouble(String name, double defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getDouble(name, defaultValue);
	}

	public int getInteger(long companyId, String name) throws SystemException {
		return PrefsPropsUtil.getInteger(companyId, name);
	}

	public int getInteger(long companyId, String name, int defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getInteger(companyId, name, defaultValue);
	}

	public int getInteger(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getInteger(preferences, companyId, name);
	}

	public int getInteger(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return PrefsPropsUtil.getInteger(
			preferences, companyId, name, defaultValue);
	}

	public int getInteger(String name) throws SystemException {
		return PrefsPropsUtil.getInteger(name);
	}

	public int getInteger(String name, int defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getInteger(name, defaultValue);
	}

	public long getLong(long companyId, String name) throws SystemException {
		return PrefsPropsUtil.getLong(companyId, name);
	}

	public long getLong(long companyId, String name, long defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getLong(companyId, name, defaultValue);
	}

	public long getLong(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getLong(preferences, companyId, name);
	}

	public long getLong(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return PrefsPropsUtil.getLong(
			preferences, companyId, name, defaultValue);
	}

	public long getLong(String name) throws SystemException {
		return PrefsPropsUtil.getLong(name);
	}

	public long getLong(String name, long defaultValue) throws SystemException {
		return PrefsPropsUtil.getLong(name, defaultValue);
	}

	public PortletPreferences getPreferences() throws SystemException {
		return PrefsPropsUtil.getPreferences();
	}

	public PortletPreferences getPreferences(long companyId)
		throws SystemException {

		return PrefsPropsUtil.getPreferences(companyId);
	}

	public Properties getProperties(
		PortletPreferences preferences, long companyId, String prefix,
		boolean removePrefix) {

		return PrefsPropsUtil.getProperties(
			preferences, companyId, prefix, removePrefix);
	}

	public Properties getProperties(String prefix, boolean removePrefix)
		throws SystemException {

		return PrefsPropsUtil.getProperties(prefix, removePrefix);
	}

	public short getShort(long companyId, String name) throws SystemException {
		return PrefsPropsUtil.getShort(companyId, name);
	}

	public short getShort(long companyId, String name, short defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getShort(companyId, name, defaultValue);
	}

	public short getShort(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getShort(preferences, companyId, name);
	}

	public short getShort(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return PrefsPropsUtil.getShort(
			preferences, companyId, name, defaultValue);
	}

	public short getShort(String name) throws SystemException {
		return PrefsPropsUtil.getShort(name);
	}

	public short getShort(String name, short defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getShort(name, defaultValue);
	}

	public String getString(long companyId, String name)
		throws SystemException {

		return PrefsPropsUtil.getString(companyId, name);
	}

	public String getString(long companyId, String name, String defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getString(companyId, name, defaultValue);
	}

	public String getString(
		PortletPreferences preferences, long companyId, String name) {

		return PrefsPropsUtil.getString(preferences, companyId, name);
	}

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		boolean defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
	}

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		double defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
	}

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		int defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
	}

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		long defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
	}

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		short defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
	}

	public String getString(
		PortletPreferences preferences, long companyId, String name,
		String defaultValue) {

		return PrefsPropsUtil.getString(
			preferences, companyId, name, defaultValue);
	}

	public String getString(String name) throws SystemException {
		return PrefsPropsUtil.getString(name);
	}

	public String getString(String name, String defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getString(name, defaultValue);
	}

	public String[] getStringArray(
			long companyId, String name, String delimiter)
		throws SystemException {

		return PrefsPropsUtil.getStringArray(companyId, name, delimiter);
	}

	public String[] getStringArray(
			long companyId, String name, String delimiter,
			String[] defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getStringArray(
			companyId, name, delimiter, defaultValue);
	}

	public String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter) {

		return PrefsPropsUtil.getStringArray(
			preferences, companyId, name, delimiter);
	}

	public String[] getStringArray(
		PortletPreferences preferences, long companyId, String name,
		String delimiter, String[] defaultValue) {

		return PrefsPropsUtil.getStringArray(
			preferences, companyId, name, delimiter, defaultValue);
	}

	public String[] getStringArray(String name, String delimiter)
		throws SystemException {

		return PrefsPropsUtil.getStringArray(name, delimiter);
	}

	public String[] getStringArray(
			String name, String delimiter, String[] defaultValue)
		throws SystemException {

		return PrefsPropsUtil.getStringArray(name, delimiter, defaultValue);
	}

	public String getStringFromNames(long companyId, String... names)
		throws SystemException {

		return PrefsPropsUtil.getStringFromNames(companyId, names);
	}

}
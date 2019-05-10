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

package com.liferay.portal.kernel.bean;

import javax.servlet.http.HttpServletRequest;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public interface BeanProperties {

	public void copyProperties(Object source, Object target);

	public void copyProperties(Object source, Object target, Class<?> editable);

	public void copyProperties(
		Object source, Object target, String[] ignoreProperties);

	public <T> T deepCopyProperties(Object source) throws Exception;

	public boolean getBoolean(Object bean, String param);

	public boolean getBoolean(Object bean, String param, boolean defaultValue);

	public boolean getBooleanSilent(Object bean, String param);

	public boolean getBooleanSilent(
		Object bean, String param, boolean defaultValue);

	public byte getByte(Object bean, String param);

	public byte getByte(Object bean, String param, byte defaultValue);

	public byte getByteSilent(Object bean, String param);

	public byte getByteSilent(Object bean, String param, byte defaultValue);

	public double getDouble(Object bean, String param);

	public double getDouble(Object bean, String param, double defaultValue);

	public double getDoubleSilent(Object bean, String param);

	public double getDoubleSilent(
		Object bean, String param, double defaultValue);

	public float getFloat(Object bean, String param);

	public float getFloat(Object bean, String param, float defaultValue);

	public float getFloatSilent(Object bean, String param);

	public float getFloatSilent(Object bean, String param, float defaultValue);

	public int getInteger(Object bean, String param);

	public int getInteger(Object bean, String param, int defaultValue);

	public int getIntegerSilent(Object bean, String param);

	public int getIntegerSilent(Object bean, String param, int defaultValue);

	public long getLong(Object bean, String param);

	public long getLong(Object bean, String param, long defaultValue);

	public long getLongSilent(Object bean, String param);

	public long getLongSilent(Object bean, String param, long defaultValue);

	public Object getObject(Object bean, String param);

	public Object getObject(Object bean, String param, Object defaultValue);

	public Object getObjectSilent(Object bean, String param);

	public Object getObjectSilent(
		Object bean, String param, Object defaultValue);

	public Class<?> getObjectType(Object bean, String param);

	public Class<?> getObjectType(
		Object bean, String param, Class<?> defaultValue);

	public Class<?> getObjectTypeSilent(Object bean, String param);

	public Class<?> getObjectTypeSilent(
		Object bean, String param, Class<?> defaultValue);

	public short getShort(Object bean, String param);

	public short getShort(Object bean, String param, short defaultValue);

	public short getShortSilent(Object bean, String param);

	public short getShortSilent(Object bean, String param, short defaultValue);

	public String getString(Object bean, String param);

	public String getString(Object bean, String param, String defaultValue);

	public String getStringSilent(Object bean, String param);

	public String getStringSilent(
		Object bean, String param, String defaultValue);

	public void setProperties(
		Object bean, HttpServletRequest httpServletRequest);

	public void setProperties(
		Object bean, HttpServletRequest httpServletRequest,
		String[] ignoreProperties);

	public void setProperty(Object bean, String param, Object value);

	public void setPropertySilent(Object bean, String param, Object value);

}
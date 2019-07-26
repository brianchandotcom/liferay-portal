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

package com.liferay.portal.search.document;

import com.liferay.portal.search.geolocation.GeoLocationPoint;

import java.util.Collection;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Wade Cao
 * @author André de Oliveira
 */
@ProviderType
public interface DocumentBuilder {

	public Document build();

	public DocumentBuilder setBoolean(String name, Boolean value);

	public DocumentBuilder setBooleans(String name, Boolean... value);

	public DocumentBuilder setDate(String name, String value);

	public DocumentBuilder setDates(String name, String... values);

	public DocumentBuilder setDouble(String name, Double value);

	public DocumentBuilder setDoubles(String name, Double... values);

	public DocumentBuilder setFloat(String name, Float value);

	public DocumentBuilder setFloats(String name, Float... values);

	public DocumentBuilder setGeoLocationPoint(
		String name, GeoLocationPoint values);

	public DocumentBuilder setGeoLocationPoints(
		String name, GeoLocationPoint... values);

	public DocumentBuilder setInteger(String name, Integer value);

	public DocumentBuilder setIntegers(String name, Integer... values);

	public DocumentBuilder setLong(String name, Long value);

	public DocumentBuilder setLongs(String name, Long... values);

	public DocumentBuilder setString(String name, String value);

	public DocumentBuilder setStrings(String name, String... value);

	public DocumentBuilder setValue(String name, Object value);

	public DocumentBuilder setValues(String name, Collection<Object> values);

	public DocumentBuilder unsetValue(String name);

}
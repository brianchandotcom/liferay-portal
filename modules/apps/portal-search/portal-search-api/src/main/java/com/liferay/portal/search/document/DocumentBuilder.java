/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
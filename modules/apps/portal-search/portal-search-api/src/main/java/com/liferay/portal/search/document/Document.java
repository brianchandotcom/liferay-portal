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

import java.util.List;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author Wade Cao
 */
@ProviderType
public interface Document {

	public Boolean getBoolean(String string);

	public List<Boolean> getBooleans(String string);

	public String getDate(String name);

	public List<String> getDates(String name);

	public Double getDouble(String name);

	public List<Double> getDoubles(String name);

	public Map<String, Field> getFields();

	public Float getFloat(String name);

	public List<Float> getFloats(String name);

	public GeoLocationPoint getGeoLocationPoint(String name);

	public List<GeoLocationPoint> getGeoLocationPoints(String name);

	public Integer getInteger(String name);

	public List<Integer> getIntegers(String name);

	public Long getLong(String name);

	public List<Long> getLongs(String name);

	public String getString(String name);

	public List<String> getStrings(String name);

	public Object getValue(String name);

	public List<Object> getValues(String name);

}
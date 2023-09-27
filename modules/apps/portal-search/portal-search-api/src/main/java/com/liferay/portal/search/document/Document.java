/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
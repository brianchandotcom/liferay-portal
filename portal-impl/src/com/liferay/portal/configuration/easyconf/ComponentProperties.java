
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

package com.liferay.portal.configuration.easyconf;

import com.germinus.easyconf.AggregatedProperties;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;

/**
 * @author Shuyang Zhou
 */
public class ComponentProperties {

	public ComponentProperties(AggregatedProperties aggregatedProperties) {
		_aggregatedProperties = aggregatedProperties;
	}

	public List getLoadedSources() {
		return _aggregatedProperties.loadedSources();
	}

	public Properties getProperties() {
		Properties properties = new Properties();

		Iterator<String> keyIterator = _aggregatedProperties.getKeys();

		while (keyIterator.hasNext()) {
			String key = keyIterator.next();

			List<Object> list = _aggregatedProperties.getList(key);

			properties.setProperty(key, StringUtil.merge(list));
		}

		return properties;
	}

	public Object getProperty(String key) {
		return _aggregatedProperties.getProperty(key);
	}

	public String getString(String key) {
		Object value = _aggregatedProperties.getProperty(key);

		if (value instanceof List) {
			return StringUtil.merge((List)value);
		}

		return _aggregatedProperties.getString(key);
	}

	public String getString(String key, Filter filter) {
		Iterator<String> iterator = filter.filterKeyIterator(key);

		while (iterator.hasNext()) {
			String value = _aggregatedProperties.getString(
				iterator.next(), null);

			if (value != null) {
				return value;
			}
		}

		return null;
	}

	public String[] getStringArray(String key) {
		return _aggregatedProperties.getStringArray(key);
	}

	public String[] getStringArray(String key, Filter filter) {
		Iterator<String> iterator = filter.filterKeyIterator(key);

		while (iterator.hasNext()) {
			List<?> value = _aggregatedProperties.getList(
				iterator.next(), null);

			if (value != null) {
				return value.toArray(new String[0]);
			}
		}

		return StringPool.EMPTY_ARRAY;
	}

	public void setProperty(String key, Object value) {
		_aggregatedProperties.setProperty(key, value);
	}

	public Configuration toConfiguration() {
		return _aggregatedProperties;
	}

	private final AggregatedProperties _aggregatedProperties;

}
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

package com.liferay.portal.search.elasticsearch7.internal.index;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.spi.index.IndexDefinition;

import java.util.Map;
import java.util.Objects;

/**
 * @author André de Oliveira
 */
public class IndexDefinitionData {

	public IndexDefinitionData(
		IndexDefinition indexDefinition, Map<String, Object> properties) {

		_index = _getIndexName(
			properties.get(IndexDefinition.PROPERTY_KEY_INDEX_NAME));
		_source = _getSource(
			indexDefinition,
			properties.get(
				IndexDefinition.PROPERTY_KEY_INDEX_SETTINGS_RESOURCE_NAME));
	}

	public String getIndex() {
		return _index;
	}

	public String getSource() {
		return _source;
	}

	private static String _getIndexName(Object property) {
		return String.valueOf(Objects.requireNonNull(property));
	}

	private static String _getSource(
		IndexDefinition indexDefinition, Object property) {

		String resourceName = String.valueOf(Objects.requireNonNull(property));

		return StringUtil.read(indexDefinition.getClass(), resourceName);
	}

	private final String _index;
	private final String _source;

}
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

package com.liferay.gradle.plugins.lang.merger.tasks;

import java.util.LinkedHashMap;
import java.util.Map;

import org.gradle.util.GUtil;

/**
 * @author Andrea Di Giorgi
 */
public class MergePropertiesSetting {

	public MergePropertiesSetting(String name) {
		_name = name;
	}

	public String getName() {
		return _name;
	}

	public Map<String, String> getTransformKeys() {
		return _transformKeys;
	}

	public void setTransformKeys(Map<?, ?> transformKeys) {
		_transformKeys.clear();

		transformKeys(transformKeys);
	}

	public MergePropertiesSetting transformKey(
		String sourceKey, String destinationKey) {

		_transformKeys.put(sourceKey, destinationKey);

		return this;
	}

	public MergePropertiesSetting transformKeys(Map<?, ?> transformKeys) {
		GUtil.addToMap(_transformKeys, transformKeys);

		return this;
	}

	private final String _name;
	private final Map<String, String> _transformKeys = new LinkedHashMap<>();

}
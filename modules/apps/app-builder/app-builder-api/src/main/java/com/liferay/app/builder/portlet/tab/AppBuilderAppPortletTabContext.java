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

package com.liferay.app.builder.portlet.tab;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Rafael Praxedes
 */
public class AppBuilderAppPortletTabContext {

	public AppBuilderAppPortletTabContext addDataLayoutProperties(
		long dataLayoutId, Map<String, Object> properties) {

		_propertiesMap.put(dataLayoutId, properties);

		return this;
	}

	public List<Long> getDataLayoutIds() {
		return new ArrayList<>(_propertiesMap.keySet());
	}

	public String getName(long dataLayoutId, Locale locale) {
		Map<String, Object> properties = _propertiesMap.get(dataLayoutId);

		Map<Locale, String> nameMap =
			(Map<Locale, String>)properties.getOrDefault(
				"nameMap", Collections.emptyMap());

		return nameMap.getOrDefault(locale, StringPool.BLANK);
	}

	public boolean isReadOnly(long dataLayoutId) {
		Map<String, Object> properties = _propertiesMap.get(dataLayoutId);

		return MapUtil.getBoolean(properties, "readOnly");
	}

	private final Map<Long, Map<String, Object>> _propertiesMap =
		new LinkedHashMap<>();

}
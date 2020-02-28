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

package com.liferay.data.engine.configuration;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public interface DataLayoutBuilderConfiguration {

	public default String[] getDisabledProperties() {
		return new String[0];
	}

	public default String[] getDisabledTabs() {
		return new String[0];
	}

	public default Map<String, Object> getSuccessPageSettings() {
		return HashMapBuilder.<String, Object>put(
			"enabled", true
		).build();
	}

	public default String[] getUnimplementedProperties() {
		return new String[] {
			"fieldNamespace", "indexType", "localizable", "readOnly", "type",
			"validation", "visibilityExpression"
		};
	}

	public default boolean isMultiPage() {
		return true;
	}

}
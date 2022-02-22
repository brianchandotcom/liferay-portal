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

package com.liferay.object.field.business.type;

import com.liferay.object.field.render.ObjectFieldRenderingContext;
import com.liferay.object.model.ObjectField;
import com.liferay.petra.string.StringPool;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marcela Cunha
 */
public interface ObjectFieldBusinessType {

	public String getDBType();

	public String getDDMFormFieldTypeName();

	public default String getDescription(Locale locale) {
		return StringPool.BLANK;
	}

	public String getLabel(Locale locale);

	public String getName();

	public default Map<String, Object> getProperties(
		ObjectField objectField,
		ObjectFieldRenderingContext objectFieldRenderingContext) {

		return Collections.emptyMap();
	}

	public default boolean isVisible() {
		return true;
	}

}
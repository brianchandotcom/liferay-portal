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

package com.liferay.portal.odata.entity;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Models a <code>EntityModel</code>.
 *
 * @author Cristina González
 * @review
 */
public interface EntityModel {

	public static Map<String, EntityField> toEntityFieldsMap(
		EntityField... entityFields) {

		Map<String, EntityField> entityFieldsMap = new HashMap<>();

		for (EntityField entityField : entityFields) {
			entityFieldsMap.put(entityField.getName(), entityField);
		}

		return entityFieldsMap;
	}

	/**
	 * Returns a Map with all the entity fields used to create the EDM.
	 *
	 * @return the entity field map
	 * @review
	 */
	public Map<String, EntityField> getEntityFieldsMap();

	/**
	 * Returns the name of the single entity type used to create the EDM.
	 *
	 * @return the entity type name
	 * @review
	 */
	public default String getName() {
		Class<?> clazz = getClass();

		return StringUtil.replace(
			clazz.getName(), CharPool.PERIOD, CharPool.UNDERLINE);
	}

}
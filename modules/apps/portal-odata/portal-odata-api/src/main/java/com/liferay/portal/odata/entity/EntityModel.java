/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.odata.entity;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Collections;
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

	public default Map<String, EntityRelationship> getEntityRelationshipsMap() {
		return Collections.emptyMap();
	}

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

	public static class EntityRelationship {

		public EntityRelationship(
			EntityModel entityModel, String name, Type type) {

			_entityModel = entityModel;
			_name = name;
			_type = type;
		}

		public EntityModel getEntityModel() {
			return _entityModel;
		}

		public String getName() {
			return _name;
		}

		public Type getType() {
			return _type;
		}

		public enum Type {

			COLLECTION, SINGLETON

		}

		private final EntityModel _entityModel;
		private final String _name;
		private final Type _type;

	}

}
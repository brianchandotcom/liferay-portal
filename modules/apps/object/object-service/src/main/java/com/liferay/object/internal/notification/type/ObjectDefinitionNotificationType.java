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

package com.liferay.object.internal.notification.type;

import com.liferay.notification.type.LegacyNotificationType;
import com.liferay.object.model.ObjectDefinition;

import java.util.Locale;
import java.util.Map;

/**
 * @author Gustavo Lima
 */
public class ObjectDefinitionNotificationType
	implements LegacyNotificationType {

	public ObjectDefinitionNotificationType(ObjectDefinition objectDefinition) {
		_objectDefinition = objectDefinition;
	}

	@Override
	public String getClassName(Object object) {
		return _objectDefinition.getClassName();
	}

	@Override
	public long getClassPK(Object object) {
		if (!(object instanceof Map)) {
			throw new IllegalArgumentException(
				"Object " + object + " is not a map");
		}

		Map<String, Object> values = (Map<String, Object>)object;

		return (Long)values.get("id");
	}

	@Override
	public String getKey() {
		return _objectDefinition.getClassName();
	}

	@Override
	public String getLabel(Locale locale) {
		return _objectDefinition.getShortName();
	}

	private final ObjectDefinition _objectDefinition;

}
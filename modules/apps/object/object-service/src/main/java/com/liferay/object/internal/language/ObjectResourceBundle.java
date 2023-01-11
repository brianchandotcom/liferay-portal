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

package com.liferay.object.internal.language;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.language.LanguageResources;
import com.liferay.portal.language.ResourceBundleEnumeration;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Brian Wing Shun Chan
 * @author Marco Leo
 */
public class ObjectResourceBundle extends ResourceBundle {

	public ObjectResourceBundle(
		Locale locale, ObjectDefinition objectDefinition) {

		_map = HashMapBuilder.put(
			"model.resource." + objectDefinition.getResourceName(),
			objectDefinition.getPluralLabel(locale)
		).put(
			"model.resource.com.liferay.object.model.ObjectDefinition#" +
				objectDefinition.getObjectDefinitionId(),
			objectDefinition.getLabel(locale)
		).build();

		setParent(LanguageResources.getResourceBundle(locale));
	}

	@Override
	public Enumeration<String> getKeys() {
		return new ResourceBundleEnumeration(_map.keySet(), parent.getKeys());
	}

	@Override
	protected Object handleGetObject(String key) {
		return _map.get(key);
	}

	private final Map<String, String> _map;

}
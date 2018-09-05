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

package com.liferay.structured.content.apio.internal.architect.filter;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides the entity data model from the Indexed Entity (JournalArticle).
 *
 * @author Julio Camarero
 * @review
 */
public class StructuredContentSingleEntitySchemaBasedEdmProvider
	extends BaseSingleEntitySchemaBasedEdmProvider {

	@Override
	public Map<String, EntityType> getEntityTypesMap() {
		return _entityTypesMap;
	}

	@Override
	public String getName() {
		return "StructuredContent";
	}

	private static final Map<String, EntityType> _entityTypesMap;

	static {
		_entityTypesMap = new HashMap<>();

		_entityTypesMap.put("datePublished", EntityType.DATE);
		_entityTypesMap.put("title", EntityType.STRING);
	}

}
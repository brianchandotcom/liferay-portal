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

package com.liferay.layout.page.template.admin.web.internal.headless.delivery.dto.v1_0.structure.importer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

/**
 * @author Pavel Savinov
 */
public abstract class BaseLayoutStructureItemHelper {

	protected Map<String, Object> getDefinitionMap(Object definition)
		throws Exception {

		Map<String, Object> definitionMap = null;

		if (definition instanceof Map) {
			definitionMap = (Map<String, Object>)definition;
		}
		else {
			definitionMap = _objectMapper.readValue(
				definition.toString(), Map.class);
		}

		return definitionMap;
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper();

}
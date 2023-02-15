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

package com.liferay.headless.builder.operation;

import com.liferay.info.field.InfoField;

import java.util.Map;

/**
 * @author Carlos Correa
 */
public class Response {

	public Response(
		String entityName, Map<String, InfoField> infoFields,
		String schemaName) {

		_entityName = entityName;
		_infoFields = infoFields;
		_schemaName = schemaName;
	}

	public String getEntityName() {
		return _entityName;
	}

	public Map<String, InfoField> getInfoFields() {
		return _infoFields;
	}

	public String getSchemaName() {
		return _schemaName;
	}

	private final String _entityName;
	private final Map<String, InfoField> _infoFields;
	private final String _schemaName;

}
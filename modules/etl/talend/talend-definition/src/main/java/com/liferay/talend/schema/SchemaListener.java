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

package com.liferay.talend.schema;

import com.liferay.talend.common.log.DebugUtils;
import com.liferay.talend.resource.BaseLiferayResourceProperties;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.ISchemaListener;
import org.talend.components.common.SchemaProperties;

/**
 * @author Igor Beslic
 */
public class SchemaListener implements ISchemaListener {

	public SchemaListener() {
		if (_logger.isWarnEnabled()) {
			_logger.warn("Using this constructor indicates something is wrong");
		}

		_baseLiferayResourceProperties = null;
	}

	public SchemaListener(
		BaseLiferayResourceProperties baseLiferayResourceProperties) {

		_baseLiferayResourceProperties = baseLiferayResourceProperties;
	}

	@Override
	public void afterSchema() {
		DebugUtils.debugCurrentStackTrace(_logger);

		if (_logger.isTraceEnabled()) {
			_logger.trace(
				"Schema details: " +
					_baseLiferayResourceProperties.getSchema());
		}

		Schema currentSchema = _baseLiferayResourceProperties.getSchema();

		if (_isEmptySchema(currentSchema)) {
			if (_logger.isWarnEnabled()) {
				_logger.warn("Schema is empty for " + this);
			}
		}
	}

	private boolean _isEmptySchema(Schema schema) {
		if (schema.equals(SchemaProperties.EMPTY_SCHEMA)) {
			return true;
		}

		return false;
	}

	private static Logger _logger = LoggerFactory.getLogger(
		SchemaListener.class);

	private final BaseLiferayResourceProperties _baseLiferayResourceProperties;

}
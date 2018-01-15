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

package com.liferay.consumer.talend.runtime;

import com.liferay.consumer.talend.connection.LiferayProvideConnectionProperties;

import java.io.IOException;

import org.apache.avro.Schema;

import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.daikon.properties.ValidationResult;

/**
 * @author Zoltán Takács
 */
public interface LiferaySourceOrSinkRuntime extends SourceOrSink {

	public Schema guessSchema(String resourceURL) throws IOException;

	public ValidationResult validateConnection(
		LiferayProvideConnectionProperties properties);

}
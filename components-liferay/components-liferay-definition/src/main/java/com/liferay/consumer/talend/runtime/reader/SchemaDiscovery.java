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

package com.liferay.consumer.talend.runtime.reader;

import java.io.IOException;

import org.apache.avro.Schema;

import org.talend.components.api.component.runtime.SourceOrSink;

/**
 * @author Zoltán Takács
 *
 * Represents runtime interface for schema discovery functionality.
 * guessSchema() is the main functionality of this method.
 * However, initialize() and validate() should be called before to
 * store Properties and validate them
 */
public interface SchemaDiscovery extends SourceOrSink {

	public Schema guessSchema() throws IOException;

}
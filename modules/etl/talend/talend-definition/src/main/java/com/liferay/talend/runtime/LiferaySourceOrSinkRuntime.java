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

package com.liferay.talend.runtime;

import com.liferay.talend.common.oas.OASParameter;
import com.liferay.talend.connection.LiferayConnectionPropertiesProvider;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.avro.Schema;

import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.daikon.properties.ValidationResult;

/**
 * @author Zoltán Takács
 */
public interface LiferaySourceOrSinkRuntime extends SourceOrSink {

	public Set<String> getEndpointList(String operation) throws IOException;

	public Map<String, String> getEndpointMap(String operation)
		throws IOException;

	public Schema getEndpointSchema(String endpoint, String operation)
		throws IOException;

	public List<OASParameter> getParameters(String endpoint, String operation);

	public Set<String> getSupportedOperations(String endpoint);

	public ValidationResult validateConnection(
		LiferayConnectionPropertiesProvider liferayConnectionPropertiesProvider,
		RuntimeContainer runtimeContainer);

}
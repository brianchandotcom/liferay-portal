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

import com.liferay.talend.connection.LiferayConnectionPropertiesProvider;
import com.liferay.talend.runtime.apio.operation.Operation;

import java.io.IOException;

import java.util.List;

import org.apache.avro.Schema;

import org.talend.components.api.component.runtime.SourceOrSink;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.ValidationResult;

/**
 * @author Zoltán Takács
 */
public interface LiferaySourceOrSinkRuntime extends SourceOrSink {

	public List<NamedThing> getAvailableWebSites() throws IOException;

	public Schema getExpectedFormSchema(Operation operation) throws IOException;

	public Schema getInputResourceCollectionSchema(String resourceURL)
		throws IOException;

	public List<NamedThing> getResourceList(String webSiteURL)
		throws IOException;

	public List<Operation> getResourceSupportedOperations(String resourceURL)
		throws IOException;

	public boolean hasWebSiteResource();

	public ValidationResult validateConnection(
		LiferayConnectionPropertiesProvider
			liferayConnectionPropertiesProvider);

}
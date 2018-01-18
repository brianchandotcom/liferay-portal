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

package com.liferay.talend.runtime.reader;

import com.liferay.talend.connection.LiferayConnectionResourceBaseProperties;
import com.liferay.talend.runtime.LiferaySource;
import com.liferay.talend.tliferayinput.TLiferayInputProperties;

import java.io.IOException;

import java.util.Map;

import org.apache.avro.Schema;

import org.talend.components.api.component.runtime.AbstractBoundedReader;
import org.talend.components.api.component.runtime.BoundedSource;
import org.talend.components.api.component.runtime.Result;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.daikon.avro.AvroUtils;

/**
 * @author Zoltán Takács
 */
public abstract class LiferayBaseReader<T> extends AbstractBoundedReader<T> {

	@Override
	public void close() throws IOException {
	}

	@Override
	public Map<String, Object> getReturnValues() {
		Result result = new Result();

		result.totalCount = dataCount;

		return result.toMap();
	}

	protected LiferayBaseReader(
		RuntimeContainer runtimeContainer, LiferaySource liferaySource) {

		super(liferaySource);

		this.runtimeContainer = runtimeContainer;
	}

	protected Schema getSchema() throws IOException {
		if (schema == null) {
			schema = liferayConnectionResourceBaseProperties.getSchema();

			if (AvroUtils.isIncludeAllFields(schema)) {
				String resourceURL = null;

				if (liferayConnectionResourceBaseProperties instanceof
						TLiferayInputProperties) {

					resourceURL =
						liferayConnectionResourceBaseProperties.resource.
							resourceURL.getValue();
				}

				BoundedSource boundedSource = getCurrentSource();

				schema = boundedSource.getEndpointSchema(
					runtimeContainer, resourceURL);
			}
		}

		return schema;
	}

	protected int dataCount;
	protected LiferayConnectionResourceBaseProperties
		liferayConnectionResourceBaseProperties;
	protected RuntimeContainer runtimeContainer;
	protected transient Schema schema;

}
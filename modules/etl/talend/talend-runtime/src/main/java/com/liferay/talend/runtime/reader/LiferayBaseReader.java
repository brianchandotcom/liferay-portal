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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.runtime.AbstractBoundedReader;
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
		RuntimeContainer container, LiferaySource source) {

		super(source);
		this.container = container;
	}

	protected Schema getSchema() throws IOException {
		if (runtimeSchema == null) {
			runtimeSchema = properties.getSchema();

			if (AvroUtils.isIncludeAllFields(runtimeSchema)) {
				String resourceURL = null;

				if (properties instanceof TLiferayInputProperties) {
					resourceURL = properties.resource.resourceURL.getValue();
				}

				runtimeSchema = getCurrentSource().getEndpointSchema(
					container, resourceURL);
			}
		}

		return runtimeSchema;
	}

	protected RuntimeContainer container;
	protected int dataCount;
	protected LiferayConnectionResourceBaseProperties properties;
	protected transient Schema runtimeSchema;

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayBaseReader.class);

}
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

import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.properties.batch.LiferayBatchFileProperties;
import com.liferay.talend.properties.batch.LiferayBatchOutputProperties;
import com.liferay.talend.runtime.client.RESTClient;
import com.liferay.talend.runtime.reader.LiferayBatchFileReader;
import com.liferay.talend.tliferaybatchfile.TLiferayBatchFileDefinition;

import java.io.File;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.apache.avro.Schema;

import org.talend.components.api.component.runtime.Reader;
import org.talend.components.api.component.runtime.Source;
import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.api.properties.ComponentReferenceProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;

/**
 * @author Igor Beslic
 */
public class LiferayBatchOutputSink implements Source {

	@Override
	public Reader createReader(RuntimeContainer container) {
		return new LiferayBatchFileReader(this);
	}

	public String getBatchFilePath() {
		return _batchFilePath;
	}

	@Override
	public Schema getEndpointSchema(
		RuntimeContainer runtimeContainer, String schemaName) {

		return null;
	}

	@Override
	public List<NamedThing> getSchemaNames(RuntimeContainer runtimeContainer) {
		return Collections.emptyList();
	}

	@Override
	public ValidationResult initialize(
		RuntimeContainer runtimeContainer,
		ComponentProperties componentProperties) {

		Objects.requireNonNull(componentProperties);

		ValidationResult validationResult = _initializeBatchFile(
			componentProperties);

		if (validationResult.getStatus() == ValidationResult.Result.ERROR) {
			return validationResult;
		}

		LiferayConnectionProperties liferayConnectionProperties = null;

		if (componentProperties instanceof LiferayConnectionProperties) {
			liferayConnectionProperties =
				(LiferayConnectionProperties)componentProperties;
		}
		else {
			Properties properties = componentProperties.getProperties(
				"connection");

			if (properties instanceof LiferayConnectionProperties) {
				liferayConnectionProperties =
					(LiferayConnectionProperties)properties;
			}
		}

		if (liferayConnectionProperties == null) {
			return new ValidationResult(
				ValidationResult.Result.ERROR,
				"Unable to locate connection properties");
		}

		liferayConnectionProperties =
			liferayConnectionProperties.
				getEffectiveLiferayConnectionProperties();

		_restClient = new RESTClient(
			liferayConnectionProperties,
			_getBatchOutputURL(
				liferayConnectionProperties.getApplicationBaseHref()));

		return ValidationResult.OK;
	}

	public void submit(File batchFile) {
		_restClient.executePostRequest(batchFile);
	}

	@Override
	public ValidationResult validate(RuntimeContainer runtimeContainer) {
		return ValidationResult.OK;
	}

	private String _getBatchOutputURL(String base) {
		StringBuilder sb = new StringBuilder();

		sb.append(base);
		sb.append("/");
		sb.append(_entityClass);
		sb.append("/");
		sb.append(_entityVersion);
		sb.append("/");

		return sb.toString();
	}

	private ValidationResult _initializeBatchFile(
		ComponentProperties componentProperties) {

		if (!(componentProperties instanceof LiferayBatchOutputProperties)) {
			return new ValidationResult(
				ValidationResult.Result.ERROR,
				String.format(
					"Unable to initialize %s with %s", getClass(),
					String.valueOf(componentProperties)));
		}

		LiferayBatchOutputProperties liferayBatchOutputProperties =
			(LiferayBatchOutputProperties)componentProperties;

		ComponentReferenceProperties<LiferayBatchFileProperties>
			batchFilePropertiesComponentReferenceProperties =
				liferayBatchOutputProperties.
					batchFilePropertiesComponentReferenceProperties;

		LiferayBatchFileProperties liferayBatchFileProperties =
			batchFilePropertiesComponentReferenceProperties.getReference();

		if (liferayBatchFileProperties == null) {
			return new ValidationResult(
				ValidationResult.Result.ERROR,
				String.format(
					"Unable to initialize %s without properly selected %s",
					getClass(), TLiferayBatchFileDefinition.COMPONENT_NAME));
		}

		_batchFilePath = liferayBatchFileProperties.getBatchFilePath();
		_entityClass = liferayBatchFileProperties.getEntityClassName();
		_entityVersion = liferayBatchFileProperties.getEntityVersion();

		return ValidationResult.OK;
	}

	private transient String _batchFilePath;
	private transient String _entityClass;
	private transient String _entityVersion;
	private RESTClient _restClient;

}
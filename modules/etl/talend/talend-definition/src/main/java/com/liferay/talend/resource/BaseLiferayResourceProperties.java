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

package com.liferay.talend.resource;

import com.liferay.talend.LiferayBaseComponentDefinition;
import com.liferay.talend.common.oas.OASParameter;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferayConnectionPropertiesProvider;
import com.liferay.talend.runtime.LiferaySourceOrSinkRuntime;
import com.liferay.talend.runtime.ValidatedSoSSandboxRuntime;
import com.liferay.talend.schema.SchemaListener;
import com.liferay.talend.tliferayoutput.Action;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.UriBuilder;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.ISchemaListener;
import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.properties.property.StringProperty;

/**
 * @author Ivica Cardic
 */
public abstract class BaseLiferayResourceProperties
	extends ComponentPropertiesImpl
	implements LiferayConnectionPropertiesProvider {

	public BaseLiferayResourceProperties(String name) {
		super(name);
	}

	public ValidationResult afterEndpoint() throws Exception {
		if (_logger.isDebugEnabled()) {
			_logger.debug("Endpoint: " + endpoint.getValue());
		}

		ValidatedSoSSandboxRuntime validatedSoSSandboxRuntime =
			LiferayBaseComponentDefinition.initializeSandboxedRuntime(
				getEffectiveLiferayConnectionProperties());

		ValidationResultMutable validationResultMutable =
			validatedSoSSandboxRuntime.getValidationResultMutable();

		if (validationResultMutable.getStatus() ==
				ValidationResult.Result.ERROR) {

			return validationResultMutable;
		}

		return doAfterEndpoint(
			validatedSoSSandboxRuntime.getLiferaySourceOrSinkRuntime(),
			validationResultMutable);
	}

	public String getEndpoint() {
		return endpoint.getValue();
	}

	public URI getEndpointURI() {
		String applicationBaseHref = connection.getApplicationBaseHref();

		String endpointRelativePath = endpoint.getValue();

		if (endpointRelativePath.startsWith("/")) {
			endpointRelativePath = endpointRelativePath.substring(1);
		}

		String endpointHref = applicationBaseHref.concat(endpointRelativePath);

		UriBuilder uriBuilder = UriBuilder.fromPath(endpointHref);

		return buildEndpointURI(uriBuilder);
	}

	@Override
	public LiferayConnectionProperties getLiferayConnectionProperties() {
		return connection;
	}

	public Schema getSchema() {
		Property<Schema> schemaProperty = main.schema;

		return schemaProperty.getValue();
	}

	@Override
	public Properties init() {
		if (connection == null) {
			throw new IllegalStateException(
				"Unable to initialize class if `connection` field is null");
		}

		Properties properties = super.init();

		if (_logger.isTraceEnabled()) {
			_logger.trace("Initialized " + System.identityHashCode(this));
		}

		return properties;
	}

	public void setLiferayConnectionProperties(
		LiferayConnectionProperties liferayConnectionProperties) {

		connection = liferayConnectionProperties;
	}

	public void setSchema(Schema schema) {
		Property<Schema> schemaProperty = main.schema;

		schemaProperty.setValue(schema);
	}

	public void setSchemaListener(ISchemaListener schemaListener) {
		this.schemaListener = schemaListener;
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		// Special property settings

		endpoint.setRequired();

		// Forms

		_setupReferenceForm();
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		endpoint.setValue(null);

		setSchemaListener(new SchemaListener(this));

		if (_logger.isTraceEnabled()) {
			_logger.trace("Properties set " + System.identityHashCode(this));
		}
	}

	public LiferayConnectionProperties connection = null;
	public StringProperty endpoint = new StringProperty("endpoint");

	public SchemaProperties main = new SchemaProperties("main") {

		@SuppressWarnings("unused")
		public void afterSchema() {
			if (schemaListener != null) {
				schemaListener.afterSchema();
			}
		}

	};

	public Property<Action> operations = PropertyFactory.newEnum(
		"operations", Action.class);
	public ParametersTable parametersTable = new ParametersTable(
		"parametersTable");
	public ISchemaListener schemaListener;

	protected URI buildEndpointURI(UriBuilder uriBuilder) {
		List<String> parameterNames = parametersTable.columnName.getValue();
		List<String> parameterTypes = parametersTable.typeColumnName.getValue();
		List<String> parameterValues =
			parametersTable.valueColumnName.getValue();

		Stream<String> parameterNamesStream = parameterNames.stream();

		parameterNames = parameterNamesStream.map(
			name -> name.replace("*", "")
		).collect(
			Collectors.toList()
		);

		for (int i = 0; i < parameterNames.size(); i++) {
			uriBuilder.resolveTemplate(
				parameterNames.get(i), parameterValues.get(i));
		}

		for (int i = 0; i < parameterNames.size(); i++) {
			String typeString = parameterTypes.get(i);

			if (OASParameter.Type.PATH == OASParameter.Type.valueOf(
					typeString.toUpperCase())) {

				continue;
			}

			String parameterValue = parameterValues.get(i);

			if ((parameterValue != null) &&
				!Objects.equals(parameterValue, "")) {

				uriBuilder.queryParam(parameterNames.get(i), parameterValue);
			}
		}

		return uriBuilder.build();
	}

	protected abstract ValidationResult doAfterEndpoint(
		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime,
		ValidationResultMutable validationResultMutable);

	protected LiferayConnectionProperties
		getEffectiveLiferayConnectionProperties() {

		LiferayConnectionProperties liferayConnectionProperties =
			getLiferayConnectionProperties();

		if (liferayConnectionProperties == null) {
			_logger.error("LiferayConnectionProperties is null");
		}

		LiferayConnectionProperties referencedLiferayConnectionProperties =
			liferayConnectionProperties.getReferencedConnectionProperties();

		if (referencedLiferayConnectionProperties != null) {
			if (_logger.isDebugEnabled()) {
				_logger.debug("Using a reference connection properties");
				_logger.debug(
					"API spec URL: " +
						referencedLiferayConnectionProperties.getApiSpecURL());
				_logger.debug(
					"User ID: " +
						referencedLiferayConnectionProperties.getUserId());
			}

			return referencedLiferayConnectionProperties;
		}

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"API spec URL: " +
					liferayConnectionProperties.apiSpecURL.getValue());
			_logger.debug(
				"User ID: " + liferayConnectionProperties.getUserId());
		}

		return liferayConnectionProperties;
	}

	protected void populateParametersTable(
		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime,
		String operation) {

		List<String> parameterNames = new ArrayList<>();
		List<String> parameterValues = new ArrayList<>();
		List<String> parameterTypes = new ArrayList<>();

		List<OASParameter> oasParameters =
			liferaySourceOrSinkRuntime.getParameters(
				endpoint.getValue(), operation);

		if (oasParameters.isEmpty()) {
			parametersTable.columnName.setValue(Collections.emptyList());
			parametersTable.valueColumnName.setValue(Collections.emptyList());
			parametersTable.typeColumnName.setValue(Collections.emptyList());
		}
		else {
			for (OASParameter oasParameter : oasParameters) {
				String name = oasParameter.getName();

				if (Objects.equals(name, "page") ||
					Objects.equals(name, "pageSize")) {

					continue;
				}

				if (oasParameter.isRequired() ||
					(OASParameter.Type.PATH == oasParameter.getType())) {

					name = name + "*";
				}

				parameterNames.add(name);

				OASParameter.Type type = oasParameter.getType();

				String typeString = type.toString();

				typeString = typeString.toLowerCase();

				parameterTypes.add(typeString);

				parameterValues.add("");
			}

			parametersTable.columnName.setValue(parameterNames);
			parametersTable.typeColumnName.setValue(parameterTypes);
			parametersTable.valueColumnName.setValue(parameterValues);
		}
	}

	private void _setupReferenceForm() {
		Form referenceForm = Form.create(this, Form.REFERENCE);

		Widget endpointReferenceWidget = Widget.widget(endpoint);

		endpointReferenceWidget.setCallAfter(true);
		endpointReferenceWidget.setLongRunning(true);
		endpointReferenceWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		referenceForm.addRow(endpointReferenceWidget);

		referenceForm.addRow(main.getForm(Form.REFERENCE));

		Widget parametersTableWidget = Widget.widget(parametersTable);

		referenceForm.addRow(
			parametersTableWidget.setWidgetType(Widget.TABLE_WIDGET_TYPE));
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		BaseLiferayResourceProperties.class);

}
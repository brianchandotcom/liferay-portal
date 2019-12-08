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
import com.liferay.talend.common.daikon.DaikonUtil;
import com.liferay.talend.common.oas.OASParameter;
import com.liferay.talend.common.oas.OASSource;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.schema.SchemaListener;
import com.liferay.talend.source.LiferayOASSource;
import com.liferay.talend.tliferayoutput.Action;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.UriBuilder;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.ISchemaListener;
import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.properties.property.StringProperty;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public abstract class BaseLiferayResourceProperties
	extends ComponentPropertiesImpl {

	public BaseLiferayResourceProperties(String name) {
		super(name);
	}

	public ValidationResult afterEndpoint() {
		if (_logger.isDebugEnabled()) {
			_logger.debug("Endpoint: " + endpoint.getValue());
		}

		LiferayOASSource liferayOASSource =
			LiferayBaseComponentDefinition.getLiferayOASSource(
				connection.getEffectiveLiferayConnectionProperties());

		if (!liferayOASSource.isValid()) {
			return liferayOASSource.getValidationResult();
		}

		return doAfterEndpoint(liferayOASSource.getOASSource());
	}

	public ValidationResult beforeEndpoint() {
		LiferayOASSource liferayOASSource =
			LiferayBaseComponentDefinition.getLiferayOASSource(
				connection.getEffectiveLiferayConnectionProperties());

		if (!liferayOASSource.isValid()) {
			return liferayOASSource.getValidationResult();
		}

		String message = getI18nMessage("error.validation.resources");

		try {
			Set<String> endpoints = getEndpoints(
				liferayOASSource.getOASSource());

			if (!endpoints.isEmpty()) {
				endpoint.setPossibleNamedThingValues(
					DaikonUtil.toNamedThings(endpoints));

				return liferayOASSource.getValidationResult();
			}
		}
		catch (Exception e) {
			message = e.getMessage();
		}

		return new ValidationResult(ValidationResult.Result.ERROR, message);
	}

	public String getEndpoint() {
		return endpoint.getValue();
	}

	public URI getEndpointURI() {
		LiferayConnectionProperties liferayConnectionProperties =
			connection.getEffectiveLiferayConnectionProperties();

		String applicationBaseHref =
			liferayConnectionProperties.getApplicationBaseHref();

		String endpointRelativePath = endpoint.getValue();

		if (endpointRelativePath.startsWith("/")) {
			endpointRelativePath = endpointRelativePath.substring(1);
		}

		String endpointHref = applicationBaseHref.concat(endpointRelativePath);

		UriBuilder uriBuilder = UriBuilder.fromPath(endpointHref);

		return buildEndpointURI(uriBuilder);
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

	protected abstract ValidationResult doAfterEndpoint(OASSource oasSource);

	protected abstract Set<String> getEndpoints(OASSource oasSource);

	protected String getI18nMessage(String key) {
		return _i18nMessages.getMessage(key);
	}

	protected void populateParametersTable(List<OASParameter> oasParameters) {
		if (oasParameters.isEmpty()) {
			parametersTable.columnName.setValue(Collections.emptyList());
			parametersTable.valueColumnName.setValue(Collections.emptyList());
			parametersTable.typeColumnName.setValue(Collections.emptyList());

			return;
		}

		List<String> parameterNames = new ArrayList<>();
		List<String> parameterTypes = new ArrayList<>();
		List<String> parameterValues = new ArrayList<>();

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

	private static final I18nMessages _i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		_i18nMessages = i18nMessageProvider.getI18nMessages(
			LiferayInputResourceProperties.class);
	}

}
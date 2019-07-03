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
import com.liferay.talend.tliferayoutput.Action;

import java.net.URI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.UriBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.ISchemaListener;
import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.common.SchemaProperties;
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
		if (_log.isDebugEnabled()) {
			_log.debug("Endpoint: " + endpoint.getValue());
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

		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
			validatedSoSSandboxRuntime.getLiferaySourceOrSinkRuntime();

		return doAfterEndpoint(
			liferaySourceOrSinkRuntime, validationResultMutable);
	}

	public URI getEndpointURI() {
		String applicationBaseHref = connection.getApplicationBaseHref();

		String endpointRelativePath = endpoint.getValue();

		if (endpointRelativePath.startsWith("/")) {
			endpointRelativePath = endpointRelativePath.substring(1);
		}

		String endpointHref = applicationBaseHref.concat(endpointRelativePath);

		UriBuilder uriBuilder = UriBuilder.fromPath(endpointHref);

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

	@Override
	public LiferayConnectionProperties getLiferayConnectionProperties() {
		return connection;
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
	}

	public LiferayConnectionProperties connection =
		new LiferayConnectionProperties("connection");
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

	protected abstract ValidationResult doAfterEndpoint(
		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime,
		ValidationResultMutable validationResultMutable);

	protected LiferayConnectionProperties
		getEffectiveLiferayConnectionProperties() {

		LiferayConnectionProperties liferayConnectionProperties =
			getLiferayConnectionProperties();

		if (liferayConnectionProperties == null) {
			_log.error("LiferayConnectionProperties is null");
		}

		LiferayConnectionProperties referencedLiferayConnectionProperties =
			liferayConnectionProperties.getReferencedConnectionProperties();

		if (referencedLiferayConnectionProperties != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Using a reference connection properties");
				_log.debug(
					"API spec URL: " +
						referencedLiferayConnectionProperties.getApiSpecURL());
				_log.debug(
					"User ID: " +
						referencedLiferayConnectionProperties.getUserId());
			}

			return referencedLiferayConnectionProperties;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"API spec URL: " +
					liferayConnectionProperties.apiSpecURL.getValue());
			_log.debug("User ID: " + liferayConnectionProperties.getUserId());
		}

		return liferayConnectionProperties;
	}

	protected void populateParametersTable(
		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime,
		String httpMethod) {

		List<String> parameterNames = new ArrayList<>();
		List<String> parameterValues = new ArrayList<>();
		List<String> parameterTypes = new ArrayList<>();

		List<OASParameter> oasParameters =
			liferaySourceOrSinkRuntime.getParameters(
				endpoint.getValue(), httpMethod);

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

	private static final Logger _log = LoggerFactory.getLogger(
		BaseLiferayResourceProperties.class);

}
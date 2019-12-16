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

package com.liferay.talend.properties.resource;

import com.liferay.talend.LiferayBaseComponentDefinition;
import com.liferay.talend.common.daikon.DaikonUtil;
import com.liferay.talend.common.oas.OASExplorer;
import com.liferay.talend.common.oas.OASParameter;
import com.liferay.talend.common.oas.OASSource;
import com.liferay.talend.common.oas.constants.OASConstants;
import com.liferay.talend.common.schema.SchemaBuilder;
import com.liferay.talend.common.util.StringUtil;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.properties.parameters.RequestParameter;
import com.liferay.talend.properties.parameters.RequestParameterProperties;
import com.liferay.talend.source.LiferayOASSource;

import java.net.URI;

import java.util.List;
import java.util.Set;

import javax.json.JsonObject;

import javax.ws.rs.core.UriBuilder;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.StringProperty;

/**
 * @author Igor Beslic
 */
public class LiferayResourceProperties extends ComponentPropertiesImpl {

	public LiferayResourceProperties(String name) {
		super(name);
	}

	public ValidationResult afterEndpoint() {
		LiferayOASSource liferayOASSource =
			LiferayBaseComponentDefinition.getLiferayOASSource(this);

		if (!liferayOASSource.isValid()) {
			return liferayOASSource.getValidationResult();
		}

		return _afterEndpoint(liferayOASSource.getOASSource());
	}

	public ValidationResult beforeEndpoint() {
		ValidationResult validationResult = _validateOpenAPIModule();

		if (validationResult.getStatus() == ValidationResult.Result.ERROR) {
			return validationResult;
		}

		LiferayOASSource liferayOASSource =
			LiferayBaseComponentDefinition.getLiferayOASSource(this);

		if (!liferayOASSource.isValid()) {
			return liferayOASSource.getValidationResult();
		}

		String message = getI18nMessage("error.validation.resources");

		try {
			Set<String> endpoints = StringUtil.stripPrefix(
				_getOpenAPIModuleVersionPath(),
				_getEndpoints(liferayOASSource.getOASSource()));

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

	public String getEndpointUrl() {
		URI endpointURI = _getEndpointURI();

		return endpointURI.toASCIIString();
	}

	public LiferayConnectionProperties getLiferayConnectionProperties() {
		return connection.getEffectiveLiferayConnectionProperties();
	}

	public String getOpenAPIUrl() {
		URI openAPIURI = _getEndpointOASURI();

		return openAPIURI.toASCIIString();
	}

	public Schema getSchema() {
		Property<Schema> schemaProperty = flowSchema.schema;

		return schemaProperty.getValue();
	}

	public void setIncludeLiferayOASParameters(boolean include) {
		_includeLiferayOASParameters = include;
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form mainForm = new Form(this, Form.MAIN);

		Widget openAPIModuleWidget = Widget.widget(openAPIModule);

		openAPIModuleWidget.setWidgetType(Widget.DEFAULT_WIDGET_TYPE);

		mainForm.addRow(openAPIModuleWidget);

		Widget endpointReferenceWidget = Widget.widget(endpoint);

		endpointReferenceWidget.setLongRunning(true);
		endpointReferenceWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		mainForm.addRow(endpointReferenceWidget);

		Widget requestParametersWidget = Widget.widget(parameters);

		requestParametersWidget.setWidgetType(Widget.TABLE_WIDGET_TYPE);

		mainForm.addRow(requestParametersWidget);

		mainForm.addRow(flowSchema.getForm(Form.REFERENCE));
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		endpoint.setValue(null);
		endpoint.setRequired(true);

		openAPIModule.setValue(null);
		openAPIModule.setRequired(true);

		_setupParametersProperties();
	}

	public LiferayConnectionProperties connection =
		new LiferayConnectionProperties("connection");
	public StringProperty endpoint = new StringProperty("endpoint");
	public SchemaProperties flowSchema = new SchemaProperties("flowSchema");
	public StringProperty openAPIModule = new StringProperty("openAPIModule");
	public RequestParameterProperties parameters =
		new RequestParameterProperties("parameters");

	private ValidationResult _afterEndpoint(OASSource oasSource) {
		String openAPIEntityOperationPath = _getOpenAPIEntityOperationPath();
		JsonObject oasJsonObject = oasSource.getOASJsonObject(getOpenAPIUrl());

		try {
			SchemaBuilder schemaBuilder = new SchemaBuilder();

			Schema endpointSchema = schemaBuilder.inferSchema(
				openAPIEntityOperationPath, OASConstants.OPERATION_GET,
				oasJsonObject);

			flowSchema.schema.setValue(endpointSchema);
		}
		catch (TalendRuntimeException tre) {
			_logger.error("Unable to generate schema", tre);

			return new ValidationResult(
				ValidationResult.Result.ERROR,
				getI18nMessage("error.validation.schema"));
		}

		_setupParametersProperties();

		OASExplorer oasExplorer = new OASExplorer();

		parameters.addParameters(
			oasExplorer.getParameters(
				openAPIEntityOperationPath, OASConstants.OPERATION_GET,
				oasJsonObject));

		return ValidationResult.OK;
	}

	private String _getEndpointBase() {
		StringBuilder sb = new StringBuilder();

		LiferayConnectionProperties liferayConnectionProperties =
			getLiferayConnectionProperties();

		sb.append(liferayConnectionProperties.getHostUrl());

		sb.append(openAPIModule.getValue());

		return sb.toString();
	}

	private URI _getEndpointOASURI() {
		UriBuilder uriBuilder = UriBuilder.fromPath(_getEndpointBase());

		uriBuilder.path("/openapi.json");

		return uriBuilder.build();
	}

	private Set<String> _getEndpoints(OASSource oasSource) {
		OASExplorer oasExplorer = new OASExplorer();

		return oasExplorer.getEndpointList(
			oasSource.getOASJsonObject(getOpenAPIUrl()),
			OASConstants.OPERATION_GET);
	}

	private URI _getEndpointURI() {
		UriBuilder uriBuilder = UriBuilder.fromPath(_getEndpointBase());

		uriBuilder.path(endpoint.getValue());

		if (!parameters.isEmpty()) {
			List<RequestParameter> requestParameters =
				parameters.getRequestParameters();

			for (RequestParameter requestParameter : requestParameters) {
				if (requestParameter.isPathLocation()) {
					uriBuilder.resolveTemplate(
						requestParameter.getName(),
						requestParameter.getValue());

					continue;
				}

				uriBuilder.queryParam(
					requestParameter.getName(), requestParameter.getValue());
			}
		}

		return uriBuilder.build();
	}

	private String _getOpenAPIEntityOperationPath() {
		String openAPIModuleVersionPath = _getOpenAPIModuleVersionPath();

		return openAPIModuleVersionPath.concat(endpoint.getValue());
	}

	private String _getOpenAPIModuleVersionPath() {
		String openAPIModuleValue = openAPIModule.getValue();

		return openAPIModuleValue.substring(
			openAPIModuleValue.lastIndexOf("/"));
	}

	private void _setupParametersProperties() {
		parameters.removeAll();

		if (_includeLiferayOASParameters) {
			parameters.addParameters(OASParameter.liferayOASParameters);
		}
	}

	private ValidationResult _validateOpenAPIModule() {
		if (StringUtil.isEmpty(openAPIModule.getValue())) {
			return new ValidationResult(
				ValidationResult.Result.ERROR,
				"OpenAPIModule property is required");
		}

		return ValidationResult.OK;
	}

	private static Logger _logger = LoggerFactory.getLogger(
		LiferayResourceProperties.class);

	private transient boolean _includeLiferayOASParameters;

}
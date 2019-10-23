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
import com.liferay.talend.common.oas.OASExplorer;
import com.liferay.talend.common.oas.OASSource;
import com.liferay.talend.common.oas.constants.OASConstants;
import com.liferay.talend.common.schema.SchemaBuilder;
import com.liferay.talend.common.schema.SchemaUtils;
import com.liferay.talend.properties.ExceptionUtils;
import com.liferay.talend.source.LiferayOASSource;
import com.liferay.talend.tliferayoutput.Action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.common.SchemaProperties;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 */
public class LiferayOutputResourceProperties
	extends BaseLiferayResourceProperties {

	public LiferayOutputResourceProperties(
		String name, SchemaProperties schemaFlow,
		SchemaProperties schemaReject) {

		super(name);

		_schemaFlow = schemaFlow;
		_schemaReject = schemaReject;

		if (_logger.isTraceEnabled()) {
			_logger.trace("Instantiated " + System.identityHashCode(this));
		}
	}

	public ValidationResult afterOperations() {
		Action action = operations.getValue();

		if (_logger.isDebugEnabled()) {
			_logger.debug("Selected method: " + action.getMethodName());
		}

		LiferayOASSource liferayOASSource =
			LiferayBaseComponentDefinition.getLiferayOASSource(
				getEffectiveLiferayConnectionProperties());

		if (!liferayOASSource.isValid()) {
			return liferayOASSource.getValidationResult();
		}

		if (action == Action.Unavailable) {
			_resetComponents();

			return liferayOASSource.getValidationResult();
		}

		OASSource oasSource = liferayOASSource.getOASSource();

		OASExplorer oasExplorer = new OASExplorer();

		populateParametersTable(
			oasExplorer.getParameters(
				endpoint.getValue(), action.getMethodName(),
				oasSource.getOASJsonObject()));

		return _createEndpointSchema(oasSource);
	}

	public ValidationResult beforeEndpoint() {
		LiferayOASSource liferayOASSource =
			LiferayBaseComponentDefinition.getLiferayOASSource(
				getEffectiveLiferayConnectionProperties());

		if (!liferayOASSource.isValid()) {
			return liferayOASSource.getValidationResult();
		}

		OASSource oasSource = liferayOASSource.getOASSource();

		OASExplorer oasExplorer = new OASExplorer();

		try {
			Set<String> endpoints = oasExplorer.getEndpointList(
				oasSource.getOASJsonObject(), OASConstants.OPERATION_DELETE,
				OASConstants.OPERATION_PATCH, OASConstants.OPERATION_POST);

			if (endpoints.isEmpty()) {
				return new ValidationResult(
					ValidationResult.Result.ERROR,
					_i18nMessages.getMessage("error.validation.resources"));
			}

			endpoint.setPossibleNamedThingValues(
				DaikonUtil.toNamedThings(endpoints));
		}
		catch (Exception e) {
			return ExceptionUtils.exceptionToValidationResult(e);
		}

		return null;
	}

	@Override
	public Properties init() {
		Properties properties = super.init();

		if (_logger.isTraceEnabled()) {
			_logger.trace("Initialized " + System.identityHashCode(this));
		}

		return properties;
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form form = getForm(Form.REFERENCE);

		Widget mainWidget = form.getWidget("main");
		Widget parametersTableWidget = form.getWidget("parametersTable");

		operations.setRequired();

		Widget operationsWidget = Widget.widget(operations);

		operationsWidget.setLongRunning(true);
		operationsWidget.setWidgetType(Widget.ENUMERATION_WIDGET_TYPE);

		form.replaceRow("main", operationsWidget);

		form.replaceRow("parametersTable", mainWidget);

		form.addRow(parametersTableWidget);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		operations.setValue(null);

		operations.setPossibleValues((List<?>)null);
		operations.setTaggedValue(_ADD_QUOTES, true);

		if (_logger.isTraceEnabled()) {
			_logger.trace("Properties set " + System.identityHashCode(this));
		}
	}

	@Override
	protected ValidationResult doAfterEndpoint(OASSource oasSource) {
		Set<String> supportedOperations = Collections.emptySet();

		try {
			endpoint.setValue(endpoint.getValue());

			OASExplorer oasExplorer = new OASExplorer();

			supportedOperations = oasExplorer.getSupportedOperations(
				endpoint.getValue(), oasSource.getOASJsonObject());
		}
		catch (TalendRuntimeException tre) {
			endpoint.setValue(null);
			operations.setValue(null);

			operations.setPossibleValues((List<?>)null);

			return new ValidationResult(
				ValidationResult.Result.ERROR,
				"Unable to resolve http operations");
		}

		List<Action> actions = new ArrayList<>();

		actions.add(Action.Unavailable);

		for (String supportedOperation : supportedOperations) {
			actions.add(Action.toAction(supportedOperation));
		}

		operations.setPossibleValues(actions);

		_resetComponents();

		return ValidationResult.OK;
	}

	private ValidationResult _createEndpointSchema(OASSource oasSource) {
		try {
			Action action = operations.getValue();

			SchemaBuilder schemaBuilder = new SchemaBuilder();

			main.schema.setValue(
				schemaBuilder.inferSchema(
					endpoint.getValue(), action.getMethodName(),
					oasSource.getOASJsonObject()));

			_updateSchemas();
		}
		catch (TalendRuntimeException tre) {
			_logger.error("Unable to generate schema", tre);

			return new ValidationResult(
				ValidationResult.Result.ERROR,
				_i18nMessages.getMessage("error.validation.schema"));
		}

		return new ValidationResult(
			ValidationResult.Result.OK,
			_i18nMessages.getMessage("success.validation.schema"));
	}

	private void _resetComponents() {
		main.schema.setValue(SchemaProperties.EMPTY_SCHEMA);

		operations.setValue(null);

		parametersTable.columnName.setValue(Collections.emptyList());
		parametersTable.valueColumnName.setValue(Collections.emptyList());
		parametersTable.typeColumnName.setValue(Collections.emptyList());
	}

	private void _updateSchemas() {
		if (_logger.isDebugEnabled()) {
			_logger.debug("Update output schemas");
		}

		Schema schema = main.schema.getValue();

		_schemaFlow.schema.setValue(schema);

		Schema rejectSchema = SchemaUtils.createRejectSchema(schema);

		_schemaReject.schema.setValue(rejectSchema);
	}

	private static final String _ADD_QUOTES = "ADD_QUOTES";

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferayOutputResourceProperties.class);

	private static final I18nMessages _i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		_i18nMessages = i18nMessageProvider.getI18nMessages(
			LiferayOutputResourceProperties.class);
	}

	private final SchemaProperties _schemaFlow;
	private final SchemaProperties _schemaReject;

}
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
import com.liferay.talend.common.oas.constants.OASConstants;
import com.liferay.talend.common.schema.SchemaUtils;
import com.liferay.talend.properties.ExceptionUtils;
import com.liferay.talend.runtime.LiferaySourceOrSinkRuntime;
import com.liferay.talend.runtime.ValidatedSoSSandboxRuntime;
import com.liferay.talend.tliferayoutput.Action;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.common.SchemaProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.SimpleNamedThing;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;

/**
 * @author Ivica Cardic
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

		ValidatedSoSSandboxRuntime validatedSoSSandboxRuntime =
			LiferayBaseComponentDefinition.initializeSandboxedRuntime(
				getEffectiveLiferayConnectionProperties());

		ValidationResultMutable validationResultMutable =
			validatedSoSSandboxRuntime.getValidationResultMutable();

		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
			validatedSoSSandboxRuntime.getLiferaySourceOrSinkRuntime();

		if (action == Action.Unavailable) {
			_resetComponents();
		}
		else {
			populateParametersTable(
				liferaySourceOrSinkRuntime, action.getMethodName());

			_createEndpointSchema(
				liferaySourceOrSinkRuntime, validationResultMutable);
		}

		return validationResultMutable;
	}

	public ValidationResult beforeEndpoint() {
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

		try {
			Set<String> endpoints = liferaySourceOrSinkRuntime.getEndpointList(
				OASConstants.OPERATION_POST);

			endpoints.addAll(
				liferaySourceOrSinkRuntime.getEndpointList(
					OASConstants.OPERATION_PATCH));
			endpoints.addAll(
				liferaySourceOrSinkRuntime.getEndpointList(
					OASConstants.OPERATION_DELETE));

			List<NamedThing> endpointsNamedThing = new ArrayList<>();

			endpoints.forEach(
				endpoint -> endpointsNamedThing.add(
					new SimpleNamedThing(endpoint, endpoint)));

			if (endpointsNamedThing.isEmpty()) {
				validationResultMutable.setMessage(
					_i18nMessages.getMessage("error.validation.resources"));
				validationResultMutable.setStatus(
					ValidationResult.Result.ERROR);

				return validationResultMutable;
			}

			endpoint.setPossibleNamedThingValues(endpointsNamedThing);
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
	protected ValidationResult doAfterEndpoint(
		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime,
		ValidationResultMutable validationResultMutable) {

		Set<String> supportedOperations = Collections.emptySet();

		try {
			endpoint.setValue(endpoint.getValue());

			supportedOperations =
				liferaySourceOrSinkRuntime.getSupportedOperations(
					endpoint.getValue());
		}
		catch (TalendRuntimeException tre) {
			endpoint.setValue(null);
			operations.setValue(null);

			operations.setPossibleValues((List<?>)null);
		}

		if (supportedOperations.isEmpty()) {
			operations.setPossibleValues(Action.Unavailable);
		}
		else {
			Stream<String> operationsStream = supportedOperations.stream();

			List<Action> actions = operationsStream.filter(
				operation -> {
					Set<String> availableMethodNames =
						Action.getAvailableMethodNames();

					return availableMethodNames.contains(operation);
				}
			).map(
				this::_toAction
			).collect(
				Collectors.toList()
			);

			actions.add(0, Action.Unavailable);

			operations.setPossibleValues(actions);
		}

		_resetComponents();

		return validationResultMutable;
	}

	private void _createEndpointSchema(
		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime,
		ValidationResultMutable validationResultMutable) {

		try {
			Action action = operations.getValue();

			Schema endpointSchema =
				liferaySourceOrSinkRuntime.getEndpointSchema(
					endpoint.getValue(), action.getMethodName());

			main.schema.setValue(endpointSchema);

			_updateSchemas();

			validationResultMutable.setMessage(
				_i18nMessages.getMessage("success.validation.schema"));
		}
		catch (IOException | TalendRuntimeException e) {
			validationResultMutable.setMessage(
				_i18nMessages.getMessage("error.validation.schema"));
			validationResultMutable.setStatus(ValidationResult.Result.ERROR);

			_logger.error("Unable to generate schema", e);
		}
	}

	private void _resetComponents() {
		main.schema.setValue(SchemaProperties.EMPTY_SCHEMA);

		operations.setValue(null);

		parametersTable.columnName.setValue(Collections.emptyList());
		parametersTable.valueColumnName.setValue(Collections.emptyList());
		parametersTable.typeColumnName.setValue(Collections.emptyList());
	}

	private Action _toAction(String method) {
		Stream<Action> actionsStream = Action.getActionsStream();

		return actionsStream.filter(
			action -> method.equals(action.getMethodName())
		).findFirst(
		).orElseThrow(
			() -> new UnsupportedOperationException(
				String.format("Unsupported operation: %s.", method))
		);
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
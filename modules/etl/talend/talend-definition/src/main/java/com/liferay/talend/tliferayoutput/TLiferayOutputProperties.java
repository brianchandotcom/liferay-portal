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

package com.liferay.talend.tliferayoutput;

import com.liferay.talend.LiferayBaseComponentDefinition;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.connection.LiferayConnectionResourceBaseProperties;
import com.liferay.talend.exception.ExceptionUtils;
import com.liferay.talend.runtime.LiferaySourceOrSinkRuntime;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.ISchemaListener;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.SchemaConstants;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResult.Result;
import org.talend.daikon.properties.ValidationResultMutable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.sandbox.SandboxedInstance;

/**
 * @author Zoltán Takács
 */
public class TLiferayOutputProperties
	extends LiferayConnectionResourceBaseProperties {

	public TLiferayOutputProperties(String name) {
		super(name);
	}

	public void afterOperations() {
		if (_log.isDebugEnabled()) {
			Action action = operations.getValue();

			_log.debug("Selected method: " + action.getMethod());
		}

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form mainForm = getForm(Form.MAIN);

		Widget operationsWidget = Widget.widget(operations);

		operationsWidget.setWidgetType(Widget.ENUMERATION_WIDGET_TYPE);
		operationsWidget.setLongRunning(true);

		mainForm.addRow(operationsWidget);

		Widget calculateSchemaWidget = Widget.widget(calculateSchema);

		calculateSchemaWidget.setLongRunning(true);
		calculateSchemaWidget.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		mainForm.addRow(calculateSchemaWidget);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		resource.setSchemaListener(
			new ISchemaListener() {

				@Override
				public void afterSchema() {
				}

			});
	}

	public void setupSchemas() {
		Schema.Field docIdField = new Schema.Field(
			"docId", AvroUtils._string(), null, (Object)null,
			Schema.Field.Order.ASCENDING);

		docIdField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

		List<Schema.Field> fields = new ArrayList<>();

		fields.add(docIdField);

		Schema initialSchema = Schema.createRecord(
			"liferay", null, null, false, fields);

		resource.main.schema.setValue(initialSchema);

		_updateOutputSchemas();
	}

	public void setValidationResult(
		ValidationResult validationResult,
		ValidationResultMutable validationResultMutable) {

		validationResultMutable.setStatus(validationResult.getStatus());
		validationResultMutable.setMessage(validationResult.getMessage());
	}

	public ValidationResult validateCalculateSchema() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Resource URL: " + resource.resourceURL.getValue());
		}

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable(Result.OK);

		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(
				null, _getEffectiveConnectionProperties());

			setValidationResult(
				liferaySourceOrSinkRuntime.validate(null),
				validationResultMutable);

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.OK) {

				setValidationResult(
					validateOperations(), validationResultMutable);
			}

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.OK) {

				try {
					NamedThing supportedOperation = _getSupportedOperation(
						liferaySourceOrSinkRuntime);

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Form for schema fields: " +
								supportedOperation.getTitle());
					}

					Schema schema = _getOperationSchema(
						liferaySourceOrSinkRuntime, supportedOperation);

					resource.main.schema.setValue(schema);

					validationResultMutable.setMessage(
						i18nMessages.getMessage("success.validation.schema"));
				}
				catch (IOException | UnsupportedOperationException e) {
					setValidationResult(
						ExceptionUtils.exceptionToValidationResult(e),
						validationResultMutable);
				}
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to determine supported operations");
				}
			}
		}

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));

		return validationResultMutable;
	}

	public ValidationResult validateOperations() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Resource URL: " + resource.resourceURL.getValue());
		}

		ValidationResultMutable validationResultMutable =
			new ValidationResultMutable(Result.OK);

		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(
				null, _getEffectiveConnectionProperties());

			setValidationResult(
				liferaySourceOrSinkRuntime.validate(null),
				validationResultMutable);

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.OK) {

				try {
					_getSupportedOperation(liferaySourceOrSinkRuntime);

					validationResultMutable.setMessage(
						i18nMessages.getMessage(
							"success.validation.operation"));
				}
				catch (IOException | UnsupportedOperationException e) {
					setValidationResult(
						ExceptionUtils.exceptionToValidationResult(e),
						validationResultMutable);
				}
			}
			else {
				if (_log.isDebugEnabled()) {
					_log.debug("Unable to determine supported operations");
				}
			}
		}

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));

		return validationResultMutable;
	}

	public transient PresentationItem calculateSchema = new PresentationItem(
		"calculateSchema", "Calculate schema");
	public Property<Action> operations = PropertyFactory.newEnum(
		"operations", Action.class);
	public SchemaProperties schemaReject = new SchemaProperties("schemaReject");

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnectors) {

		Set<PropertyPathConnector> connectors = new HashSet<>();

		if (!outputConnectors) {
			connectors.add(mainConnector);
			connectors.add(rejectConnector);

			return connectors;
		}

		return Collections.<PropertyPathConnector>emptySet();
	}

	protected static final I18nMessages i18nMessages =
		GlobalI18N.getI18nMessageProvider().getI18nMessages(
			TLiferayOutputProperties.class);

	protected transient PropertyPathConnector rejectConnector =
		new PropertyPathConnector(Connector.REJECT_NAME, "schemaReject");

	private LiferayConnectionProperties _getEffectiveConnectionProperties() {
		LiferayConnectionProperties liferayConnectionProperties =
			getConnectionProperties();

		if (liferayConnectionProperties == null) {
			_log.error("LiferayConnectionProperties is null");
		}

		LiferayConnectionProperties referencedLiferayConnectionProperties =
			liferayConnectionProperties.getReferencedConnectionProperties();

		if (referencedLiferayConnectionProperties != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Using a reference connection properties.");
				_log.debug(
					"UserID: " +
						referencedLiferayConnectionProperties.userId.
							getValue());
				_log.debug(
					"Endpoint: " +
						referencedLiferayConnectionProperties.endpoint.
							getValue());
			}

			return referencedLiferayConnectionProperties;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"UserID: " + liferayConnectionProperties.userId.getValue());
			_log.debug(
				"Endpoint: " + liferayConnectionProperties.endpoint.getValue());
		}

		return liferayConnectionProperties;
	}

	private Schema _getOperationSchema(
			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime,
			NamedThing supportedOperation)
		throws IOException {

		return liferaySourceOrSinkRuntime.getExpectedFormSchema(
			supportedOperation);
	}

	@SuppressWarnings("unused")
	private List<String> _getSchemaFieldNames(Property<Schema> schemaProperty) {
		Schema schema = schemaProperty.getValue();
		List<String> fieldNames = new ArrayList<>();

		for (Schema.Field field : schema.getFields()) {
			fieldNames.add(field.name());
		}

		return fieldNames;
	}

	private NamedThing _getSupportedOperation(
			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime)
		throws IOException, UnsupportedOperationException {

		List<NamedThing> supportedOperations = new ArrayList<>();

		supportedOperations.addAll(
			liferaySourceOrSinkRuntime.getResourceSupportedOperations(
				resource.resourceURL.getStringValue()));

		Stream<NamedThing> stream = supportedOperations.stream();

		String availableMethodNames = stream.map(
			NamedThing::getName
		).collect(
			Collectors.joining(", ")
		);

		Action action = operations.getValue();

		String method = action.getMethod();

		stream = supportedOperations.stream();

		NamedThing supportedOperation = stream.filter(
			operation -> method.equals(operation.getDisplayName())
		).findFirst(
		).orElseThrow(
			() -> new UnsupportedOperationException(
				i18nMessages.getMessage(
					"error.validation.operation", action.name(),
					availableMethodNames))
		);

		return supportedOperation;
	}

	private void _updateOutputSchemas() {
		if (_log.isDebugEnabled()) {
			_log.debug("Update output schemas");
		}

		final List<Schema.Field> rejectFields = new ArrayList<>();

		Schema.Field field = new Schema.Field(
			_FIELD_ERROR_CODE, AvroUtils._string(), null, (Object)null);

		field.addProp(SchemaConstants.TALEND_IS_LOCKED, "false");
		field.addProp(SchemaConstants.TALEND_FIELD_GENERATED, "true");
		field.addProp(SchemaConstants.TALEND_COLUMN_DB_LENGTH, "255");

		rejectFields.add(field);

		field = new Schema.Field(
			_FIELD_ERROR_FIELDS, AvroUtils._string(), null, (Object)null);

		field.addProp(SchemaConstants.TALEND_IS_LOCKED, "false");
		field.addProp(SchemaConstants.TALEND_FIELD_GENERATED, "true");
		field.addProp(SchemaConstants.TALEND_COLUMN_DB_LENGTH, "255");

		rejectFields.add(field);

		field = new Schema.Field(
			_FIELD_ERROR_MESSAGE, AvroUtils._string(), null, (Object)null);

		field.addProp(SchemaConstants.TALEND_IS_LOCKED, "false");
		field.addProp(SchemaConstants.TALEND_FIELD_GENERATED, "true");
		field.addProp(SchemaConstants.TALEND_COLUMN_DB_LENGTH, "255");

		rejectFields.add(field);

		Schema rejectSchema = Schema.createRecord(
			"rejectOutput", null, null, false, rejectFields);

		schemaReject.schema.setValue(rejectSchema);
	}

	private static final String _FIELD_ERROR_CODE = "errorCode";

	private static final String _FIELD_ERROR_FIELDS = "errorFields";

	private static final String _FIELD_ERROR_MESSAGE = "errorMessage";

	private static final Logger _log = LoggerFactory.getLogger(
		TLiferayOutputProperties.class);

}
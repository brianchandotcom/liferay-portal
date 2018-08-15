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
import com.liferay.talend.connection.LiferayConnectionResourceBaseProperties;
import com.liferay.talend.exception.ExceptionUtils;
import com.liferay.talend.resource.LiferayResourceProperties;
import com.liferay.talend.runtime.LiferaySourceOrSinkRuntime;
import com.liferay.talend.runtime.apio.operation.Operation;
import com.liferay.talend.utils.DebugUtils;
import com.liferay.talend.utils.SchemaUtils;
import com.liferay.talend.utils.URIUtils;

import java.io.IOException;

import java.net.URI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.ISchemaListener;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.SchemaConstants;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
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

	public static final String ADD_QUOTES = "ADD_QUOTES";

	public static final String FIELD_ERROR_MESSAGE = "_errorMessage";

	public static final List<String> rejectSchemaFieldNames;

	static {
		rejectSchemaFieldNames = new ArrayList<>(
			Arrays.asList(FIELD_ERROR_MESSAGE));
	}

	public static Schema createRejectSchema(Schema inputSchema) {
		final List<Schema.Field> rejectFields = new ArrayList<>();

		Schema.Field field = new Schema.Field(
			FIELD_ERROR_MESSAGE, AvroUtils.wrapAsNullable(AvroUtils._string()),
			null, (Object)null);

		field.addProp(SchemaConstants.TALEND_COLUMN_DB_LENGTH, "255");
		field.addProp(SchemaConstants.TALEND_FIELD_GENERATED, "true");
		field.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

		rejectFields.add(field);

		Schema rejectSchema = SchemaUtils.newSchema(
			inputSchema, "rejectOutput", rejectFields);

		return rejectSchema;
	}

	public TLiferayOutputProperties(String name) {
		super(name);
	}

	public ValidationResult afterCalculateSchema() throws Exception {
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
				null, getEffectiveLiferayConnectionProperties());

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
					Operation supportedOperation = _getSupportedOperation(
						liferaySourceOrSinkRuntime);

					if (_log.isDebugEnabled()) {
						_log.debug(
							"Form for schema fields: " +
								supportedOperation.getExpects());
					}

					Schema schema = _getOperationSchema(
						liferaySourceOrSinkRuntime, supportedOperation);

					resource.main.schema.setValue(schema);
					temporaryMainSchema = schema;

					_updateOutputSchemas();

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

	public void afterOperations() {
		if (_log.isDebugEnabled()) {
			Action action = operations.getValue();

			_log.debug("Selected method: " + action.getMethodName());
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

		operations.setRequired();

		Widget operationsWidget = Widget.widget(operations);

		operationsWidget.setLongRunning(true);
		operationsWidget.setWidgetType(Widget.ENUMERATION_WIDGET_TYPE);

		mainForm.addRow(operationsWidget);

		Widget calculateSchemaWidget = Widget.widget(calculateSchema);

		calculateSchemaWidget.setLongRunning(true);
		calculateSchemaWidget.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		mainForm.addRow(calculateSchemaWidget);

		Form advancedForm = new Form(this, Form.ADVANCED);

		advancedForm.addRow(dieOnError);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		dieOnError.setValue(true);
		operations.setValue(null);
		operations.setPossibleValues((List<?>)null);
		operations.setTaggedValue(ADD_QUOTES, true);

		resource = new ResourcePropertiesHelper("resource");

		resource.connection = connection;

		resource.setupProperties();

		resource.setSchemaListener(
			new ISchemaListener() {

				/**
				 * We have to reset the schema because of a Talend's internal
				 * mechanism. @see https://github.com/Talend/tdi-studio-se/blob/737243fcdf1591970536d46edad98d2992b16593/main/plugins/org.talend.designer.core.generic/src/main/java/org/talend/designer/core/generic/model/GenericElementParameter.java#L319
				 */
				@Override
				public void afterSchema() {
					Schema schema = resource.main.schema.getValue();

					if (_log.isTraceEnabled()) {
						_log.trace("Schema details:\n" + schema.toString());

						DebugUtils.logCurrentStackTrace(_log);
					}

					if (schema.equals(SchemaProperties.EMPTY_SCHEMA)) {
						resource.main.schema.setValue(temporaryMainSchema);
					}
				}

			});

		_setupSchemas();
	}

	public void setValidationResult(
		ValidationResult validationResult,
		ValidationResultMutable validationResultMutable) {

		validationResultMutable.setMessage(validationResult.getMessage());
		validationResultMutable.setStatus(validationResult.getStatus());
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
				null, getEffectiveLiferayConnectionProperties());

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
		"calculateSchema", "Calculate Schema");
	public Property<Boolean> dieOnError = PropertyFactory.newBoolean(
		"dieOnError");
	public Property<Action> operations = PropertyFactory.newEnum(
		"operations", Action.class);
	public SchemaProperties schemaFlow = new SchemaProperties("schemaFlow");
	public SchemaProperties schemaReject = new SchemaProperties("schemaReject");

	/**
	 * Have to use an explicit class to get the override of afterResource(), an
	 * anonymous class cannot be public and thus cannot be called via Talend's
	 * reflection mechanism.
	 */
	public class ResourcePropertiesHelper extends LiferayResourceProperties {

		public ResourcePropertiesHelper(String name) {
			super(name);
		}

		@Override
		public ValidationResult afterResourceURL() throws Exception {
			if (_log.isDebugEnabled()) {
				_log.debug("Resource URL: " + resourceURL.getValue());
			}

			List<Operation> supportedOperations = new ArrayList<>();

			ValidationResultMutable validationResultMutable =
				new ValidationResultMutable();

			validationResultMutable.setStatus(Result.OK);

			try (SandboxedInstance sandboxedInstance =
					LiferayBaseComponentDefinition.getSandboxedInstance(
						LiferayBaseComponentDefinition.
							RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

				LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
					(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

				liferaySourceOrSinkRuntime.initialize(
					null, getEffectiveLiferayConnectionProperties());

				ValidationResult validationResult =
					liferaySourceOrSinkRuntime.validate(null);

				validationResultMutable.setMessage(
					validationResult.getMessage());
				validationResultMutable.setStatus(validationResult.getStatus());

				if (validationResultMutable.getStatus() ==
						ValidationResult.Result.OK) {

					try {
						URI resourceURI = URIUtils.setPaginationLimitOnURL(
							resourceURL.getValue(), 1);

						String resourceCollectionType =
							liferaySourceOrSinkRuntime.
								getResourceCollectionType(
									resourceURI.toString());

						resource.setValue(resourceCollectionType);

						supportedOperations.addAll(
							liferaySourceOrSinkRuntime.
								getResourceSupportedOperations(
									resourceURI.toString()));
					}
					catch (IOException ioe) {
						validationResult =
							ExceptionUtils.exceptionToValidationResult(ioe);

						validationResultMutable.setMessage(
							validationResult.getMessage());
						validationResultMutable.setStatus(
							validationResult.getStatus());
					}
					catch (NoSuchElementException nsee) {
						validationResultMutable.setMessage(
							i18nMessages.getMessage(
								"error.validation.resourceType"));
						validationResultMutable.setStatus(Result.ERROR);
					}
				}
			}

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.ERROR) {

				resource.setValue("");
				resourceURL.setValue("");
				operations.setValue(null);
				operations.setPossibleValues((List<?>)null);
			}

			Stream<Operation> operationStream = supportedOperations.stream();

			List<Action> actionList = operationStream.map(
				Operation::getMethod
			).map(
				this::_methodToAction
			).collect(
				Collectors.toList()
			);

			if (!actionList.isEmpty()) {
				operations.setPossibleValues(actionList);
			}
			else {
				operations.setPossibleValues(Action.Unavailable);
			}

			operations.setValue(null);

			refreshLayout(getForm(Form.MAIN));
			refreshLayout(getForm(Form.REFERENCE));

			return validationResultMutable;
		}

		private Action _methodToAction(String method) {
			Stream<Action> actionStream = Action.getActionStream();

			return actionStream.filter(
				action -> method.equals(action.getMethodName())
			).findFirst(
			).orElseThrow(
				() -> new UnsupportedOperationException(
					String.format("Unsupported operation: %s.", method))
			);
		}

	}

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnectors) {

		Set<PropertyPathConnector> connectors = new HashSet<>();

		if (outputConnectors) {
			connectors.add(flowConnector);
			connectors.add(rejectConnector);
		}
		else {
			connectors.add(mainConnector);
		}

		return connectors;
	}

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(
			TLiferayOutputProperties.class);
	}

	protected transient PropertyPathConnector flowConnector =
		new PropertyPathConnector(Connector.MAIN_NAME, "schemaFlow");
	protected transient PropertyPathConnector rejectConnector =
		new PropertyPathConnector(Connector.REJECT_NAME, "schemaReject");

	private String _getAvailableOperations(Stream<Operation> operationStream) {
		String availableOperations = operationStream.map(
			Operation::getId
		).collect(
			Collectors.joining(", ")
		);

		if (availableOperations.isEmpty()) {
			return "No operation available!";
		}

		return availableOperations;
	}

	private Schema _getOperationSchema(
			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime,
			Operation supportedOperation)
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

	private Operation _getSupportedOperation(
			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime)
		throws IOException, UnsupportedOperationException {

		List<Operation> supportedOperations = new ArrayList<>();

		supportedOperations.addAll(
			liferaySourceOrSinkRuntime.getResourceSupportedOperations(
				resource.resourceURL.getStringValue()));

		Supplier<Stream<Operation>> operationStreamSupplier =
			() -> supportedOperations.stream();

		final String availableOperations = _getAvailableOperations(
			operationStreamSupplier.get());

		Action action = operations.getValue();

		String method = action.getMethodName();

		Stream<Operation> stream = operationStreamSupplier.get();

		Operation supportedOperation = stream.filter(
			operation -> method.equals(operation.getMethod())
		).findFirst(
		).orElseThrow(
			() -> new UnsupportedOperationException(
				i18nMessages.getMessage(
					"error.validation.operation", action.name(),
					availableOperations))
		);

		return supportedOperation;
	}

	private void _setupSchemas() {
		Schema.Field docIdField = new Schema.Field(
			"resourceId", AvroUtils._string(), null, (Object)null,
			Schema.Field.Order.ASCENDING);

		docIdField.addProp(SchemaConstants.TALEND_FIELD_GENERATED, "true");
		docIdField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

		List<Schema.Field> fields = new ArrayList<>();

		fields.add(docIdField);

		Schema initialSchema = Schema.createRecord(
			"liferay", null, null, false, fields);

		resource.main.schema.setValue(initialSchema);

		_updateOutputSchemas();
	}

	private void _updateOutputSchemas() {
		if (_log.isDebugEnabled()) {
			_log.debug("Update output schemas");
		}

		Schema inputSchema = resource.main.schema.getValue();

		schemaFlow.schema.setValue(inputSchema);

		Schema rejectSchema = createRejectSchema(inputSchema);

		schemaReject.schema.setValue(rejectSchema);
	}

	private static final Logger _log = LoggerFactory.getLogger(
		TLiferayOutputProperties.class);

}
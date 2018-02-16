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
import com.liferay.talend.resource.LiferayResourceProperties;
import com.liferay.talend.runtime.LiferaySourceOrSinkRuntime;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
		_updateOutputSchemas();

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

		operations.setRequired();
		Widget operationsWidget = Widget.widget(operations);

		operationsWidget.setWidgetType(Widget.ENUMERATION_WIDGET_TYPE);
		operationsWidget.setLongRunning(true);

		mainForm.addRow(operationsWidget);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		resource = new ResourcePropertiesHelper("resource");

		resource.connection = connection;
		resource.setupProperties();
		resource.setSchemaListener(
			new ISchemaListener() {

				@Override
				public void afterSchema() {
				}

			});
	}

	public ValidationResult validateOperations() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Resource URL: " + resource.resourceURL.getValue());
		}

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
				null, _getEffectiveConnectionProperties());

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			validationResultMutable.setStatus(validationResult.getStatus());
			validationResultMutable.setMessage(validationResult.getMessage());

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.OK) {

				try {
					List<NamedThing> supportedOperations = new ArrayList<>();

					supportedOperations.addAll(
						liferaySourceOrSinkRuntime.
							getResourceSupportedOperations(
								resource.resourceURL.getStringValue()));

					Stream<NamedThing> stream = supportedOperations.stream();

					Action action = operations.getValue();

					String method = action.getMethod();

					stream.filter(
						operation -> method.equals(operation.getDisplayName())
					).findFirst(
					).orElseThrow(
						() -> new UnsupportedOperationException(
							i18nMessages.getMessage(
								"error.validation.operation", action.name()))
					);

					validationResultMutable.setMessage(
						i18nMessages.getMessage("success.validation.schema"));
				}
				catch (IOException | UnsupportedOperationException e) {
					validationResult =
						ExceptionUtils.exceptionToValidationResult(e);

					validationResultMutable.setStatus(
						validationResult.getStatus());
					validationResultMutable.setMessage(
						validationResult.getMessage());
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

	public Property<Action> operations = PropertyFactory.newEnum(
		"operations", Action.class);
	public SchemaProperties schemaReject = new SchemaProperties("schemaReject");

	/**
	 * Have to use an explicit class to get the override of afterResourceURL(),
	 * an anonymous class cannot be public and thus cannot be called via
	 * Talend's reflection mechanism.
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
					null, _getEffectiveConnectionProperties());

				ValidationResult validationResult =
					liferaySourceOrSinkRuntime.validate(null);

				validationResultMutable.setStatus(validationResult.getStatus());
				validationResultMutable.setMessage(
					validationResult.getMessage());

				if (validationResultMutable.getStatus() ==
						ValidationResult.Result.OK) {

					try {
						List<NamedThing> operations = new ArrayList<>();

						operations.addAll(
							liferaySourceOrSinkRuntime.
								getResourceSupportedOperations(
									resourceURL.getStringValue()));
					}
					catch (IOException ioe) {
						validationResult =
							ExceptionUtils.exceptionToValidationResult(ioe);

						validationResultMutable.setStatus(
							validationResult.getStatus());
						validationResultMutable.setMessage(
							validationResult.getMessage());
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

		public void setupSchemas() {
			Schema.Field docIdField = new Schema.Field(
				"docId", AvroUtils._string(), null, (Object)null,
				Schema.Field.Order.ASCENDING);

			docIdField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

			List<Schema.Field> fields = new ArrayList<>();

			fields.add(docIdField);

			Schema initialSchema = Schema.createRecord(
				"liferay", null, null, false, fields);

			main.schema.setValue(initialSchema);

			_updateOutputSchemas();
		}

	}

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

	@SuppressWarnings("unused")
	private List<String> _getSchemaFieldNames(Property<Schema> schemaProperty) {
		Schema schema = schemaProperty.getValue();
		List<String> fieldNames = new ArrayList<>();

		for (Schema.Field field : schema.getFields()) {
			fieldNames.add(field.name());
		}

		return fieldNames;
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
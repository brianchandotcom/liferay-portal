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

import com.liferay.talend.common.schema.SchemaUtils;
import com.liferay.talend.connection.LiferayConnectionResourceBaseProperties;
import com.liferay.talend.resource.LiferayOutputResourceProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.SchemaConstants;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * @author Zoltán Takács
 * @author Ivica Cardic
 */
public class TLiferayOutputProperties
	extends LiferayConnectionResourceBaseProperties {

	public TLiferayOutputProperties(String name) {
		super(name);

		resource = new LiferayOutputResourceProperties(
			"resource", schemaFlow, schemaReject);

		resource.setLiferayConnectionProperties(connection);

		if (_logger.isTraceEnabled()) {
			_logger.trace("Instantiated " + System.identityHashCode(this));
		}
	}

	public Action getConfiguredAction() {
		return resource.operations.getValue();
	}

	public Boolean getDieOnError() {
		return dieOnError.getValue();
	}

	public Schema getSchema() {
		return resource.getSchema();
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
	public void refreshLayout(Form form) {
		super.refreshLayout(form);
	}

	public void setConnectionApiSpecURLValue(String value) {
		connection.apiSpecURL.setValue(value);
	}

	public void setResourceEndpointValue(String value) {
		resource.endpoint.setValue(value);
	}

	public void setResourceOperationsValue(Action action) {
		resource.operations.setValue(action);
	}

	public void setResourceParametersTableColumnNameValue(String value) {
		resource.parametersTable.columnName.setValue(Arrays.asList(value));
	}

	public void setResourceParametersTableTypeColumnNameValue(String value) {
		resource.parametersTable.typeColumnName.setValue(Arrays.asList(value));
	}

	public void setResourceParametersTableValueColumnNameValue(String value) {
		resource.parametersTable.valueColumnName.setValue(Arrays.asList(value));
	}

	public void setSchema(Schema schema) {
		if (resource == null) {
			return;
		}

		resource.setSchema(schema);
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form advancedForm = getForm(Form.ADVANCED);

		advancedForm.addRow(dieOnError);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		dieOnError.setValue(true);

		_setupSchemas();

		if (_logger.isTraceEnabled()) {
			_logger.trace("Properties set " + System.identityHashCode(this));
		}
	}

	public void setValidationResult(
		ValidationResult validationResult,
		ValidationResultMutable validationResultMutable) {

		validationResultMutable.setMessage(validationResult.getMessage());
		validationResultMutable.setStatus(validationResult.getStatus());
	}

	public Property<Boolean> dieOnError = PropertyFactory.newBoolean(
		"dieOnError");
	public SchemaProperties schemaFlow = new SchemaProperties("schemaFlow");
	public SchemaProperties schemaReject = new SchemaProperties("schemaReject");

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

		resource.setSchema(initialSchema);

		_updateOutputSchemas();
	}

	private void _updateOutputSchemas() {
		if (_logger.isDebugEnabled()) {
			_logger.debug("Update output schemas");
		}

		Schema inputSchema = resource.getSchema();

		schemaFlow.schema.setValue(inputSchema);

		Schema rejectSchema = SchemaUtils.createRejectSchema(inputSchema);

		schemaReject.schema.setValue(rejectSchema);
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		TLiferayOutputProperties.class);

}
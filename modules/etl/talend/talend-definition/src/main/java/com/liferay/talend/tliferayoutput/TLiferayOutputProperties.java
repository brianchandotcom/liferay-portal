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

import com.liferay.talend.connection.LiferayConnectionResourceBaseProperties;
import com.liferay.talend.resource.LiferayResourceProperties;
import com.liferay.talend.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.avro.Schema;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.ISchemaListener;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.avro.AvroUtils;
import org.talend.daikon.avro.SchemaConstants;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * @author Zoltán Takács
 */
public class TLiferayOutputProperties
	extends LiferayConnectionResourceBaseProperties {

	public TLiferayOutputProperties(String name) {
		super(name);
	}

	public void afterSupportedOperations() {
		_updateOutputSchemas();

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

		Widget supportedOperationsWidget = Widget.widget(supportedOperations);

		supportedOperationsWidget.setWidgetType(Widget.ENUMERATION_WIDGET_TYPE);

		mainForm.addRow(supportedOperationsWidget);
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
					_updateOutputSchemas();
				}

			});

		supportedOperations.setValue(null);
	}

	public SchemaProperties schemaReject = new SchemaProperties("schemaReject");
	public Property<List<String>> supportedOperations =
		PropertyFactory.newStringList("supportedOperations");

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
			setupSchema();

			List<String> list = new ArrayList<>();

			list.add("CREATE");
			list.add("UPDATE");
			list.add("DELETE");
			list.add("UPSERT");

			supportedOperations.setPossibleValues(list);

			refreshLayout(getForm(Form.MAIN));
			refreshLayout(getForm(Form.REFERENCE));

			return ValidationResult.OK;
		}

		public void setupSchema() {
			main.schema.setValue(SchemaProperties.EMPTY_SCHEMA);

			Schema.Field docIdField = new Schema.Field(
				"docId", AvroUtils._string(), null, (Object)null,
				Schema.Field.Order.ASCENDING);

			docIdField.addProp(SchemaConstants.TALEND_IS_LOCKED, "true");

			List<Schema.Field> fields = new ArrayList<>();

			fields.add(docIdField);

			Schema initialSchema = Schema.createRecord(
				"liferay", null, null, false, fields);

			main.schema.setValue(initialSchema);
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

	protected transient PropertyPathConnector rejectConnector =
		new PropertyPathConnector(Connector.REJECT_NAME, "schemaReject");

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
		Schema inputSchema = resource.main.schema.getValue();

		if (_log.isDebugEnabled()) {
			_log.debug("Update output schemas");
		}

		Schema mainOutputSchema = CommonUtils.newSchema(
			inputSchema, "output", Collections.<Schema.Field>emptyList());

		resource.main.schema.setValue(mainOutputSchema);

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
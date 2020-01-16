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

package com.liferay.talend.properties.batch;

import static org.talend.daikon.properties.presentation.Widget.widget;

import com.liferay.talend.LiferayDefinition;
import com.liferay.talend.common.daikon.DaikonUtil;
import com.liferay.talend.common.oas.OASException;
import com.liferay.talend.common.oas.OASExplorer;
import com.liferay.talend.common.schema.SchemaBuilder;
import com.liferay.talend.common.schema.constants.BatchSchemaConstants;
import com.liferay.talend.internal.oas.LiferayOASSource;
import com.liferay.talend.properties.connection.LiferayConnectionProperties;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.json.JsonObject;

import org.apache.avro.Schema;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.properties.property.StringProperty;

/**
 * @author Igor Beslic
 */
public class LiferayBatchFileProperties
	extends FixedConnectorsComponentProperties {

	public LiferayBatchFileProperties(String name) {
		super(name);

		batchFilePath.setRequired();
	}

	public LiferayBatchFileProperties(
		String batchFilePath, Schema entitySchema, String name,
		JsonObject oasJsonObject) {

		this(name);

		_setBatchFilePathValue(batchFilePath);

		_setEntitySchemaValue(entitySchema);

		_oasJsonObject = oasJsonObject;
	}

	public ValidationResult afterEntity() {
		SchemaBuilder schemaBuilder = new SchemaBuilder();

		Property<Schema> entitySchemaProperty = entitySchemaProperties.schema;

		entitySchemaProperty.setValue(
			schemaBuilder.getEntitySchema(
				_getEntityName(), _getOASJsonObject()));

		OASExplorer oasExplorer = new OASExplorer();

		entityVersion.setValue(oasExplorer.getVersion(_getOASJsonObject()));

		return null;
	}

	public ValidationResult beforeEntity() {
		OASExplorer oasExplorer = new OASExplorer();

		try {
			Set<String> entitySchemaNames = oasExplorer.getEntitySchemaNames(
				_getOASJsonObject());

			if (entitySchemaNames.isEmpty()) {
				return new ValidationResult(
					ValidationResult.Result.ERROR,
					"Unable to find any exposed resources");
			}

			entity.setPossibleNamedThingValues(
				DaikonUtil.toNamedThings(entitySchemaNames));
		}
		catch (Exception exception) {
			return new ValidationResult(
				ValidationResult.Result.ERROR, exception.getMessage());
		}

		return null;
	}

	public String getBatchFilePath() {
		return batchFilePath.getStringValue();
	}

	public String getEntityClassName() {
		return entity.getValue();
	}

	public Schema getEntitySchema() {
		Property<Schema> schemaProperty = entitySchemaProperties.schema;

		return schemaProperty.getValue();
	}

	public String getEntityVersion() {
		return entityVersion.getValue();
	}

	@Override
	public void setupLayout() {
		Form mainForm = new Form(this, Form.MAIN);

		mainForm.addRow(connection.getForm(Form.REFERENCE));

		Widget entitySelectWidget = Widget.widget(entity);

		entitySelectWidget.setCallAfter(true);
		entitySelectWidget.setLongRunning(true);
		entitySelectWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		mainForm.addRow(entitySelectWidget);

		mainForm.addColumn(entityVersion);

		mainForm.addRow(entitySchemaProperties.getForm(Form.REFERENCE));

		Widget bulkFilePathWidget = widget(batchFilePath);

		bulkFilePathWidget.setWidgetType(Widget.FILE_WIDGET_TYPE);

		mainForm.addRow(bulkFilePathWidget);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		Property<Schema> flowSchemaProperty = flowSchemaProperties.schema;

		flowSchemaProperty.setValue(BatchSchemaConstants.SCHEMA);
	}

	public Property<String> batchFilePath = PropertyFactory.newProperty(
		"batchFilePath");
	public LiferayConnectionProperties connection =
		new LiferayConnectionProperties("connection");
	public StringProperty entity = new StringProperty("entity");
	public SchemaProperties entitySchemaProperties = new SchemaProperties(
		"entitySchemaProperties");
	public StringProperty entityVersion = new StringProperty("entityVersion");
	public SchemaProperties flowSchemaProperties = new SchemaProperties(
		"flowSchemaProperties");
	public SchemaProperties rejectSchemaProperties = new SchemaProperties(
		"rejectSchemaProperties");

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnection) {

		if (!outputConnection) {
			return Collections.singleton(
				new PropertyPathConnector(
					Connector.MAIN_NAME + "_INPUT", "entitySchemaProperties"));
		}

		Set<PropertyPathConnector> schemaPropertiesConnectors = new HashSet<>();

		schemaPropertiesConnectors.add(
			new PropertyPathConnector(
				Connector.MAIN_NAME, "flowSchemaProperties"));
		schemaPropertiesConnectors.add(
			new PropertyPathConnector(
				Connector.REJECT_NAME, "rejectSchemaProperties"));

		return Collections.unmodifiableSet(schemaPropertiesConnectors);
	}

	private String _getEntityName() {
		return entity.getValue();
	}

	private JsonObject _getOASJsonObject() {
		if (_oasJsonObject != null) {
			return _oasJsonObject;
		}

		LiferayOASSource liferayOASSource =
			LiferayDefinition.getLiferayOASSource(
				connection.getEffectiveLiferayConnectionProperties());

		if (!liferayOASSource.isValid()) {
			throw new OASException("Unable to obtain OpenAPI specification");
		}

		_oasJsonObject = liferayOASSource.getOASJsonObject();

		return _oasJsonObject;
	}

	private void _setBatchFilePathValue(String value) {
		batchFilePath.setValue(value);
	}

	private void _setEntitySchemaValue(Schema value) {
		Property<Schema> schemaProperty = entitySchemaProperties.schema;

		schemaProperty.setValue(value);
	}

	private transient JsonObject _oasJsonObject;

}
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

import com.liferay.talend.common.schema.constants.BatchSchemaConstants;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.tliferaybatchfile.TLiferayBatchFileDefinition;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.avro.Schema;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.api.properties.ComponentReferenceProperties;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;

/**
 * @author Igor Beslic
 */
public class LiferayBatchOutputProperties
	extends FixedConnectorsComponentProperties {

	public LiferayBatchOutputProperties(String name) {
		super(name);
	}

	@Override
	public void setupLayout() {
		Form mainForm = new Form(this, Form.MAIN);

		mainForm.addRow(connection.getForm(Form.REFERENCE));
		mainForm.addRow(batchDefinitionSchema.getForm(Form.REFERENCE));

		Widget batchFilePropertiesWidget = Widget.widget(
			batchFilePropertiesComponentReferenceProperties);

		batchFilePropertiesWidget.setWidgetType(
			Widget.COMPONENT_REFERENCE_WIDGET_TYPE);

		mainForm.addRow(batchFilePropertiesWidget);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		Property<Schema> schemaProperty = batchDefinitionSchema.schema;

		schemaProperty.setValue(BatchSchemaConstants.SCHEMA);

		Property<Schema> flowSchemaProperty = flowSchema.schema;

		flowSchemaProperty.setValue(BatchSchemaConstants.SCHEMA);
	}

	public SchemaProperties batchDefinitionSchema = new SchemaProperties(
		"batchDefinitionSchema");
	public ComponentReferenceProperties<LiferayBatchFileProperties>
		batchFilePropertiesComponentReferenceProperties =
			new ComponentReferenceProperties<>(
				"batchFilePropertiesComponentReferenceProperties",
				TLiferayBatchFileDefinition.COMPONENT_NAME);
	public LiferayConnectionProperties connection =
		new LiferayConnectionProperties("connection");
	public SchemaProperties flowSchema = new SchemaProperties("flowSchema");
	public SchemaProperties rejectSchema = new SchemaProperties("rejectSchema");

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnection) {

		if (!outputConnection) {
			return Collections.singleton(
				new PropertyPathConnector(
					Connector.MAIN_NAME, "batchDefinitionSchema"));
		}

		Set<PropertyPathConnector> schemaPropertiesConnectors = new HashSet<>();

		schemaPropertiesConnectors.add(
			new PropertyPathConnector(Connector.MAIN_NAME, "flowSchema"));
		schemaPropertiesConnectors.add(
			new PropertyPathConnector(Connector.REJECT_NAME, "rejectSchema"));

		return Collections.unmodifiableSet(schemaPropertiesConnectors);
	}

}
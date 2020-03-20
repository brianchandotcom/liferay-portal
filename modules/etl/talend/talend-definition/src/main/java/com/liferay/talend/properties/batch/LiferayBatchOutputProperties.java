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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.avro.Schema;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;

/**
 * @author Igor Beslic
 */
public class LiferayBatchOutputProperties
	extends FixedConnectorsComponentProperties {

	public LiferayBatchOutputProperties(String name) {
		super(name);
	}

	public LiferayBatchFileProperties getEffectiveLiferayBatchFileProperties() {
		return liferayBatchFileProperties.
			getEffectiveLiferayBatchFileProperties();
	}

	@Override
	public void setupLayout() {
		Form mainForm = new Form(this, Form.MAIN);

		mainForm.addRow(
			batchDefinitionSchemaProperties.getForm(Form.REFERENCE));
		mainForm.addRow(liferayBatchFileProperties.getForm(Form.REFERENCE));
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		Property<Schema> schemaProperty =
			batchDefinitionSchemaProperties.schema;

		schemaProperty.setValue(BatchSchemaConstants.SCHEMA);

		Property<Schema> flowSchemaProperty = flowSchemaProperties.schema;

		flowSchemaProperty.setValue(BatchSchemaConstants.SCHEMA);
	}

	public SchemaProperties batchDefinitionSchemaProperties =
		new SchemaProperties("batchDefinitionSchemaProperties");
	public SchemaProperties flowSchemaProperties = new SchemaProperties(
		"flowSchemaProperties");
	public LiferayBatchFileProperties liferayBatchFileProperties =
		new LiferayBatchFileProperties("liferayBatchFileProperties");
	public SchemaProperties rejectSchemaProperties = new SchemaProperties(
		"rejectSchemaProperties");

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnection) {

		if (!outputConnection) {
			return Collections.singleton(
				new PropertyPathConnector(
					Connector.MAIN_NAME + "_INPUT",
					"batchDefinitionSchemaProperties"));
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

}
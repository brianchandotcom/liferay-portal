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

package com.liferay.talend.properties.input;

import com.liferay.talend.properties.connection.LiferayConnectionProperties;
import com.liferay.talend.properties.resource.LiferayResourceProperties;
import com.liferay.talend.properties.resource.Operation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.avro.Schema;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.presentation.Form;

/**
 * @author Igor Beslic
 */
public class LiferayInputProperties extends FixedConnectorsComponentProperties {

	public LiferayInputProperties(String name) {
		super(name);
	}

	public LiferayInputProperties(
		String name, Operation operation, String openAPIModule, String hostURL,
		String endpoint, List<String> parameterNamesColumn,
		List<String> parameterLocationsColumn,
		List<String> parameterValuesColumn) {

		super(name);

		resource.connection.hostURL.setValue(hostURL);

		resource.endpoint.setValue(endpoint);

		resource.openAPIModule.setValue(openAPIModule);

		resource.parameters.parameterLocationColumn.setValue(
			parameterLocationsColumn);
		resource.parameters.parameterNameColumn.setValue(parameterNamesColumn);
		resource.parameters.parameterValueColumn.setValue(
			parameterValuesColumn);
	}

	public String getEndpointUrl() {
		return resource.getEndpointUrl();
	}

	public int getItemsPerPage() {
		LiferayConnectionProperties liferayConnectionProperties =
			resource.getLiferayConnectionProperties();

		return liferayConnectionProperties.getItemsPerPage();
	}

	public Schema getOutboundSchema() {
		return resource.getOutboundSchema();
	}

	@Override
	public Properties init() {
		resource.setIncludeLiferayOASParameters(true);

		return super.init();
	}

	@Override
	public void setupLayout() {
		Form mainForm = new Form(this, Form.MAIN);

		mainForm.addRow(resource.connection.getForm(Form.REFERENCE));
		mainForm.addRow(resource.getForm(Form.MAIN));

		Form advancedForm = new Form(this, Form.ADVANCED);

		advancedForm.addRow(resource.connection.getForm(Form.ADVANCED));
	}

	public LiferayResourceProperties resource = new LiferayResourceProperties(
		"resource");

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnection) {

		if (!outputConnection) {
			return Collections.emptySet();
		}

		Set<PropertyPathConnector> schemaPropertiesConnectors = new HashSet<>();

		schemaPropertiesConnectors.add(
			new PropertyPathConnector(
				Connector.MAIN_NAME, "resource.outboundSchemaProperties"));

		return Collections.unmodifiableSet(schemaPropertiesConnectors);
	}

}
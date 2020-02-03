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

package com.liferay.talend.properties.output;

import com.liferay.talend.common.oas.constants.OASConstants;
import com.liferay.talend.properties.resource.LiferayResourceProperties;
import com.liferay.talend.properties.resource.Operation;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.common.FixedConnectorsComponentProperties;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * @author Igor Beslic
 */
public class LiferayOutputProperties
	extends FixedConnectorsComponentProperties {

	public LiferayOutputProperties(String name) {
		super(name);
	}

	public LiferayOutputProperties(
		String name, Operation operation, String openAPIModule, String hostURL,
		String endpoint, List<String> parameterNamesColumn,
		List<String> parameterLocationsColumn,
		List<String> parameterValuesColumn) {

		super(name);

		resource.connection.hostURL.setValue(hostURL);

		resource.endpoint.setValue(endpoint);

		resource.openAPIModule.setValue(openAPIModule);

		resource.operations.setValue(operation);

		resource.parameters.parameterLocationColumn.setValue(
			parameterLocationsColumn);
		resource.parameters.parameterNameColumn.setValue(parameterNamesColumn);
		resource.parameters.parameterValueColumn.setValue(
			parameterValuesColumn);
	}

	public ValidationResult afterOperations() {
		return ValidationResult.OK;
	}

	public boolean getDieOnError() {
		return dieOnError.getValue();
	}

	public String getEndpointUrl() {
		return resource.getEndpointUrl();
	}

	public Operation getOperation() {
		return resource.operations.getValue();
	}

	@Override
	public Properties init() {
		resource.setDisplayOperations(true);

		resource.setAllowedOperations(
			OASConstants.OPERATION_DELETE, OASConstants.OPERATION_PATCH,
			OASConstants.OPERATION_POST);

		return super.init();
	}

	@Override
	public void setupLayout() {
		Form mainForm = new Form(this, Form.MAIN);

		mainForm.addRow(resource.connection.getForm(Form.REFERENCE));
		mainForm.addRow(resource.getForm(Form.MAIN));

		Form advancedForm = new Form(this, Form.ADVANCED);

		advancedForm.addRow(resource.connection.getForm(Form.ADVANCED));
		advancedForm.addRow(dieOnError);
	}

	@Override
	public void setupProperties() {
		dieOnError.setValue(true);
	}

	public Property<Boolean> dieOnError = PropertyFactory.newBoolean(
		"dieOnError");
	public LiferayResourceProperties resource = new LiferayResourceProperties(
		"resource");

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnection) {

		Set<PropertyPathConnector> schemaPropertiesConnectors = new HashSet<>();

		if (!outputConnection) {
			schemaPropertiesConnectors.add(
				new PropertyPathConnector(
					Connector.MAIN_NAME, "resource.inboundSchemaProperties"));

			return Collections.unmodifiableSet(schemaPropertiesConnectors);
		}

		schemaPropertiesConnectors.add(
			new PropertyPathConnector(
				Connector.MAIN_NAME, "resource.outboundSchemaProperties"));
		schemaPropertiesConnectors.add(
			new PropertyPathConnector(
				Connector.REJECT_NAME, "resource.rejectSchemaProperties"));

		return Collections.unmodifiableSet(schemaPropertiesConnectors);
	}

}
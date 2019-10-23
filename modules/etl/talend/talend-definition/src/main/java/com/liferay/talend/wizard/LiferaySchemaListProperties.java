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

package com.liferay.talend.wizard;

import static org.talend.daikon.properties.property.PropertyFactory.newProperty;

import com.liferay.talend.LiferayBaseComponentDefinition;
import com.liferay.talend.common.oas.OASExplorer;
import com.liferay.talend.common.oas.OASSource;
import com.liferay.talend.common.oas.constants.OASConstants;
import com.liferay.talend.common.schema.SchemaBuilder;
import com.liferay.talend.connection.LiferayConnectionProperties;
import com.liferay.talend.resource.LiferayInputResourceProperties;
import com.liferay.talend.source.LiferayOASSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.json.JsonObject;

import org.apache.commons.lang3.reflect.TypeLiteral;

import org.talend.components.api.exception.ComponentException;
import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.daikon.NamedThing;
import org.talend.daikon.SimpleNamedThing;
import org.talend.daikon.properties.Properties;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.service.Repository;

/**
 * @author Ivica Cardic
 */
public class LiferaySchemaListProperties extends ComponentPropertiesImpl {

	public LiferaySchemaListProperties(String name) {
		super(name);
	}

	public ValidationResult afterFormFinishMain(
			Repository<Properties> repository)
		throws Exception {

		LiferayOASSource liferayOASSource =
			LiferayBaseComponentDefinition.getLiferayOASSource(
				connection.getReferencedConnectionProperties());

		if (!liferayOASSource.isValid()) {
			return liferayOASSource.getValidationResult();
		}

		String connectionRepositoryLocation = repository.storeProperties(
			connection, connection.name.getValue(), repositoryLocation, null);

		for (NamedThing namedThing : selectedSchemaNames.getValue()) {
			String endpoint = namedThing.getName();

			LiferayInputResourceProperties liferayInputResourceProperties =
				new LiferayInputResourceProperties(endpoint);

			liferayInputResourceProperties.connection = connection;

			liferayInputResourceProperties.init();

			liferayInputResourceProperties.endpoint.setValue(endpoint);

			liferayInputResourceProperties.afterEndpoint();

			repository.storeProperties(
				liferayInputResourceProperties, namedThing.getDisplayName(),
				connectionRepositoryLocation, "main.schema");
		}

		return ValidationResult.OK;
	}

	public void beforeFormPresentMain() throws Exception {
		LiferayOASSource liferayOASSource =
			LiferayBaseComponentDefinition.getLiferayOASSource(
				connection.getReferencedConnectionProperties());

		if (!liferayOASSource.isValid()) {
			throw new IllegalStateException(
				"Liferay open API specificatio0n source unavailable");
		}

		OASSource oasSource = liferayOASSource.getOASSource();

		try {
			Map<String, String> endpointMap = _getEndpointMap(
				oasSource.getOASJsonObject(), OASConstants.OPERATION_GET);

			for (Map.Entry<String, String> entry : endpointMap.entrySet()) {
				String endpoint = entry.getKey();

				if (endpoint.endsWith("{id}")) {
					schemaNames.add(
						new SimpleNamedThing(entry.getKey(), entry.getValue()));
				}
			}
		}
		catch (Exception e) {
			throw new ComponentException(e);
		}

		selectedSchemaNames.setPossibleValues(schemaNames);

		Form mainForm = getForm(Form.MAIN);

		mainForm.setAllowBack(true);
		mainForm.setAllowFinish(true);
	}

	public LiferaySchemaListProperties setConnection(
		LiferayConnectionProperties liferayConnectionProperties) {

		connection = liferayConnectionProperties;

		return this;
	}

	public LiferaySchemaListProperties setRepositoryLocation(
		String repositoryLocation) {

		this.repositoryLocation = repositoryLocation;

		return this;
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form form = Form.create(this, Form.MAIN);

		Widget selectedSchemaNamesWidget = Widget.widget(selectedSchemaNames);

		selectedSchemaNamesWidget.setWidgetType(
			Widget.NAME_SELECTION_AREA_WIDGET_TYPE);

		form.addRow(selectedSchemaNamesWidget);
	}

	public LiferayConnectionProperties connection;
	public String repositoryLocation;
	public List<NamedThing> schemaNames = new ArrayList<>();
	public Property<List<NamedThing>> selectedSchemaNames = newProperty(
		new TypeLiteral<List<NamedThing>>() {
		},
		"selectedSchemaNames");

	private Map<String, String> _getEndpointMap(
		JsonObject oasJsonObject, String operation) {

		Map<String, String> endpointMap = new TreeMap<>();

		SchemaBuilder schemaBuilder = new SchemaBuilder();

		OASExplorer oasExplorer = new OASExplorer();

		Set<String> endpoints = oasExplorer.getEndpointList(
			oasJsonObject, operation);

		for (String endpoint : endpoints) {
			endpointMap.put(
				endpoint,
				schemaBuilder.extractEndpointSchemaName(
					endpoint, operation, oasJsonObject));
		}

		return endpointMap;
	}

}
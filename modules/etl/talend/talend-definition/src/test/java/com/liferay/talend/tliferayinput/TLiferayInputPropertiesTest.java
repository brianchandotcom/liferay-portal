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

package com.liferay.talend.tliferayinput;

import com.liferay.talend.BasePropertiesTestCase;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class TLiferayInputPropertiesTest extends BasePropertiesTestCase {

	@Test
	public void testComponentConnectors() throws Exception {
		TLiferayInputDefinition tLiferayInputDefinition =
			new TLiferayInputDefinition();

		Class<? extends ComponentProperties> propertyClass =
			tLiferayInputDefinition.getPropertyClass();

		Assert.assertEquals(
			"Component properties implementation class",
			TLiferayInputProperties.class, propertyClass);

		ComponentProperties componentProperties =
			getDefaultInitializedComponentPropertiesInstance(propertyClass);

		Set<? extends Connector> possibleOutputConnectors =
			componentProperties.getPossibleConnectors(true);

		Assert.assertEquals(
			"Output connectors count", 1, possibleOutputConnectors.size());

		for (Connector outputConnector : possibleOutputConnectors) {
			PropertyPathConnector propertyPathConnector =
				(PropertyPathConnector)outputConnector;

			assertEquals(
				componentProperties, propertyPathConnector.getPropertyPath(),
				SchemaProperties.EMPTY_SCHEMA);
		}

		Set<? extends Connector> possibleInputConnectors =
			componentProperties.getPossibleConnectors(false);

		Assert.assertEquals(
			"No input connectors", 0, possibleInputConnectors.size());
	}

	@Test
	public void testInit() throws Exception {
		ComponentProperties componentProperties =
			getDefaultInitializedComponentPropertiesInstance(
				TLiferayInputProperties.class);

		NamedThing connectionNamedThing = getNamedThing(
			"connection", componentProperties.getForm(Form.ADVANCED));

		NamedThing itemsPerPageNamedThing = getNamedThing(
			"itemsPerPage", (Form)connectionNamedThing);

		Assert.assertEquals(
			"ItemsPerPage widget display component implementation",
			Property.class, itemsPerPageNamedThing.getClass());

		NamedThing resourceNamedThing = getNamedThing(
			"resource", componentProperties.getForm(Form.MAIN));

		Assert.assertEquals(
			"Resource widget display component implementation", Form.class,
			resourceNamedThing.getClass());

		Form resourceMainForm = (Form)resourceNamedThing;

		Assert.assertNull(
			"Operations widget is not present in input properties",
			resourceMainForm.getWidget("operations"));
	}

}
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

import com.liferay.talend.BasePropertiesTestCase;
import com.liferay.talend.properties.resource.Operation;

import org.junit.Assert;
import org.junit.Test;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.EnumProperty;
import org.talend.daikon.properties.property.Property;

/**
 * @author Igor Beslic
 */
public class TLiferayOutputPropertiesTest extends BasePropertiesTestCase {

	@Test
	public void testComponentConnectors() throws Exception {
		TLiferayOutputDefinition tLiferayOutputDefinition =
			new TLiferayOutputDefinition();

		Class<? extends ComponentProperties> propertyClass =
			tLiferayOutputDefinition.getPropertyClass();

		Assert.assertEquals(
			"Component properties implementation class",
			TLiferayOutputProperties.class, propertyClass);

		ComponentProperties componentProperties =
			getDefaultInitializedComponentPropertiesInstance(propertyClass);

		for (Connector outputConnector :
				componentProperties.getPossibleConnectors(true)) {

			PropertyPathConnector propertyPathConnector =
				(PropertyPathConnector)outputConnector;

			assertEquals(
				componentProperties, propertyPathConnector.getPropertyPath(),
				SchemaProperties.EMPTY_SCHEMA);
		}

		for (Connector inputConnector :
				componentProperties.getPossibleConnectors(false)) {

			PropertyPathConnector propertyPathConnector =
				(PropertyPathConnector)inputConnector;

			assertEquals(
				componentProperties, propertyPathConnector.getPropertyPath(),
				SchemaProperties.EMPTY_SCHEMA);
		}
	}

	@Test
	public void testInit() {
		TLiferayOutputProperties tLiferayOutputProperties =
			new TLiferayOutputProperties("root");

		tLiferayOutputProperties.init();

		NamedThing dieOnErrorNamedThing = _getNamedThing(
			"dieOnError", tLiferayOutputProperties.getForm(Form.ADVANCED));

		Assert.assertEquals(
			"DieOnError widget display component implementation",
			Property.class, dieOnErrorNamedThing.getClass());

		NamedThing resourceNamedThing = _getNamedThing(
			"resource", tLiferayOutputProperties.getForm(Form.MAIN));

		Assert.assertEquals(
			"Resource widget display component implementation", Form.class,
			resourceNamedThing.getClass());

		NamedThing operationsNamedThing = _getNamedThing(
			"operations", (Form)resourceNamedThing);

		Assert.assertEquals(
			"Resource widget display component implementation",
			EnumProperty.class, operationsNamedThing.getClass());

		Property<Operation> operationProperty =
			(EnumProperty)operationsNamedThing;

		Assert.assertTrue(
			"Field operation is required", operationProperty.isRequired());
		Assert.assertFalse(
			"Field operation is nullable", operationProperty.isNullable());
	}

	private NamedThing _getNamedThing(String name, Form form) {
		Widget formWidget = form.getWidget(name);

		return formWidget.getContent();
	}

}
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import com.liferay.talend.BasePropertiesTestCase;

import java.util.Set;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import org.talend.components.api.component.Connector;
import org.talend.components.api.component.PropertyPathConnector;
import org.talend.components.api.properties.ComponentProperties;
import org.talend.components.common.SchemaProperties;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class TLiferayInputPropertiesTest extends BasePropertiesTestCase {

	@Test
	public void testComponentConnectors() throws Exception {
		TLiferayInputDefinition tLiferayOutputDefinition =
			new TLiferayInputDefinition();

		Class<? extends ComponentProperties> propertyClass =
			tLiferayOutputDefinition.getPropertyClass();

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

	/**
	 * Checks initial layout
	 *
	 * @review
	 */
	@Ignore
	@Test
	public void testRefreshLayout() {
		TLiferayInputProperties tLiferayInputProperties =
			new TLiferayInputProperties("root");

		tLiferayInputProperties.init();

		tLiferayInputProperties.refreshLayout(
			tLiferayInputProperties.getForm(Form.MAIN));

		Form form = tLiferayInputProperties.getForm(Form.MAIN);

		Assert.assertFalse(_isHidden(form, "endpoint"));
		Assert.assertFalse(_isHidden(form, "guessSchema"));
		Assert.assertFalse(_isHidden(form, "schema"));
	}

	/**
	 * Checks forms are filled with required widgets
	 *
	 * @review
	 */
	@Ignore
	@Test
	public void testSetupLayout() {
		TLiferayInputProperties tLiferayInputProperties =
			new TLiferayInputProperties("root");

		tLiferayInputProperties.setupLayout();

		Form form = tLiferayInputProperties.getForm(Form.MAIN);

		assertThat(form, notNullValue());

		Widget schemaWidget = form.getWidget("schema");

		assertThat(schemaWidget, notNullValue());

		Widget guessSchemaWidget = form.getWidget("guessSchema");

		assertThat(guessSchemaWidget, notNullValue());
	}

	private boolean _isHidden(Form form, String name) {
		Widget widget = form.getWidget(name);

		if (widget.isHidden()) {
			return true;
		}

		return false;
	}

}
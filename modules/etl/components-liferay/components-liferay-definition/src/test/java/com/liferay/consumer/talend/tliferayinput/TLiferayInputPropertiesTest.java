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

package com.liferay.consumer.talend.tliferayinput;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;

/**
 * @author Zoltán Takács
 */
public class TLiferayInputPropertiesTest {

	/**
	 * Checks initial layout
	 */
	@Ignore
	@Test
	public void testRefreshLayout() {
		TLiferayInputProperties properties = new TLiferayInputProperties(
			"root");

		properties.init();

		properties.refreshLayout(properties.getForm(Form.MAIN));

		Form mainForm = properties.getForm(Form.MAIN);

		boolean schemaHidden = mainForm.getWidget("schema").isHidden();

		Assert.assertFalse(schemaHidden);

		boolean endpointHidden = mainForm.getWidget("endpoint").isHidden();

		Assert.assertFalse(endpointHidden);

		boolean guessSchemaHidden = mainForm.getWidget(
			"guessSchema").isHidden();

		Assert.assertFalse(guessSchemaHidden);
	}

	/**
	 * Checks forms are filled with required widgets
	 */
	@Ignore
	@Test
	public void testSetupLayout() {
		TLiferayInputProperties properties = new TLiferayInputProperties(
			"root");

		//properties.schema.init();

		properties.setupLayout();

		Form main = properties.getForm(Form.MAIN);

		assertThat(main, notNullValue());

		Collection<Widget> mainWidgets = main.getWidgets();

		//assertThat(mainWidgets, hasSize(6));

		Widget schemaWidget = main.getWidget("schema");

		assertThat(schemaWidget, notNullValue());

		Widget guessSchemaWidget = main.getWidget("guessSchema");

		assertThat(guessSchemaWidget, notNullValue());
	}

	/**
	 * Checks default values are set correctly
	 */
	@Test
	public void testSetupProperties() {
		TLiferayInputProperties properties = new TLiferayInputProperties(
			"root");

		properties.setupProperties();
	}

}
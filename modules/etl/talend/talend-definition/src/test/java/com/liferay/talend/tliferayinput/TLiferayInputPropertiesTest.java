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

	/**
	 * Checks default values are set correctly
	 */
	@Test
	public void testSetupProperties() {
		TLiferayInputProperties tLiferayInputProperties =
			new TLiferayInputProperties("root");

		tLiferayInputProperties.setupProperties();
	}

	private boolean _isHidden(Form form, String name) {
		Widget widget = form.getWidget(name);

		if (widget.isHidden()) {
			return true;
		}

		return false;
	}

}
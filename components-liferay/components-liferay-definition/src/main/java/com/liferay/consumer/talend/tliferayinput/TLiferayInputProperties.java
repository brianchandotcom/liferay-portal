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

import com.liferay.consumer.talend.connection.LiferayConnectionResourceBaseProperties;

import java.util.Collections;
import java.util.Set;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.component.PropertyPathConnector;
import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;

/**
 * @author Zoltán Takács
 */
public class TLiferayInputProperties
	extends LiferayConnectionResourceBaseProperties {

	/**
	 * Out of band (a.k.a flow variables) data schema It has one field: int
	 * currentLine
	 */
	public static final Schema outOfBandSchema;

	/**
	 * Sets Out of band schema. This schema is not supposed to be changed by
	 * user
	 */
	static {
		Field currentLineField = new Field(
			"CURRENT_LINE", Schema.create(Schema.Type.INT), null, (Object)null);

		outOfBandSchema = Schema.createRecord("OutOfBand", null, null, false);

		outOfBandSchema.setFields(Collections.singletonList(currentLineField));
	}

	public TLiferayInputProperties(String name) {
		super(name);
	}

	/**
	 * Refreshes form after "Guess Schema" button was processed
	 */
	public void afterGuessSchema() {
		refreshLayout(getForm(Form.MAIN));
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form mainForm = getForm(Form.MAIN);

		mainForm.addRow(queryString);

		Widget guessButton = Widget.widget(guessSchema);

		guessButton.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		mainForm.addRow(guessButton);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();
	}

	public Property<String> queryString =
		PropertyFactory.newProperty("queryString"); //$NON-NLS-1$

	public transient PresentationItem guessSchema = new PresentationItem(
		"guessSchema", "Guess schema");

	@Override
	protected Set<PropertyPathConnector> getAllSchemaPropertiesConnectors(
		boolean outputConnectors) {

		if (outputConnectors) {
			return Collections.singleton(mainConnector);
		}

		return Collections.<PropertyPathConnector>emptySet();
	}

	private static final Logger _log = LoggerFactory.getLogger(
		TLiferayInputProperties.class);

	private static final long serialVersionUID = 8010931662185868407L;

}
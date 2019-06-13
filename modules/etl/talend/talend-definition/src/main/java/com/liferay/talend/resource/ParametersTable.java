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

package com.liferay.talend.resource;

import static org.talend.daikon.properties.property.PropertyFactory.newProperty;

import java.util.List;

import org.apache.commons.lang3.reflect.TypeLiteral;

import org.talend.components.common.BasedOnSchemaTable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.property.Property;

/**
 * @author Ivica Cardic
 */
public class ParametersTable extends BasedOnSchemaTable {

	public ParametersTable(String name) {
		super(name);
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		Form mainForm = getForm(Form.MAIN);

		mainForm.addColumn(valueColumnName);
		mainForm.addColumn(typeColumnName);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		typeColumnName.setTaggedValue(ADD_QUOTES, true);
	}

	public Property<List<String>> typeColumnName = newProperty(
		_LIST_STRING_TYPE, "typeColumnName");
	public Property<List<String>> valueColumnName = newProperty(
		_LIST_STRING_TYPE, "valueColumnName");

	private static final TypeLiteral<List<String>> _LIST_STRING_TYPE =
		new TypeLiteral<List<String>>() {
		};

	private static final long serialVersionUID = 3473102423403696522L;

}
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

package com.liferay.frontend.taglib.data.set.display.sample.web.internal.view;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @author Javier de Arcos
 */
@Component(
	property = "clay.data.set.display.name=dataSetDisplay",
	service = ClayDataSetDisplayView.class
)
public class TableClayDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		clayTableSchemaBuilder.addClayTableSchemaField("id", "id");

		clayTableSchemaBuilder.addClayTableSchemaField("able", "Able");

		ClayTableSchemaField bakerClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField("baker", "Baker");

		bakerClayTableSchemaField.setContentRenderer("boolean");

		clayTableSchemaBuilder.addClayTableSchemaField("charlie", "Charlie");

		clayTableSchemaBuilder.addClayTableSchemaField("dog", "Dog");

		clayTableSchemaBuilder.addClayTableSchemaField("easy", "Easy");

		clayTableSchemaBuilder.addClayTableSchemaField("fox", "Fox");

		clayTableSchemaBuilder.addClayTableSchemaField("george", "George");

		clayTableSchemaBuilder.addClayTableSchemaField("how", "How");

		clayTableSchemaBuilder.addClayTableSchemaField("item", "Item");

		clayTableSchemaBuilder.addClayTableSchemaField("jig", "Jig");

		ClayTableSchemaField statusClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField("status", "status");

		statusClayTableSchemaField.setContentRenderer("status");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"creator.name", "author");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}
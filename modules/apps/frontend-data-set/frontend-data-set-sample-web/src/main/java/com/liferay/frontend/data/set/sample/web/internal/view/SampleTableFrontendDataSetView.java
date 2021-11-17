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

package com.liferay.frontend.data.set.sample.web.internal.view;

import com.liferay.frontend.data.set.sample.web.internal.constants.FrontendDataSetSampleFrontendDataSetNames;
import com.liferay.frontend.data.set.view.FrontendDataSetView;
import com.liferay.frontend.data.set.view.table.BaseTableFrontendDataSetView;
import com.liferay.frontend.data.set.view.table.FrontendDataSetTableSchema;
import com.liferay.frontend.data.set.view.table.FrontendDataSetTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FrontendDataSetTableSchemaBuilderFactory;
import com.liferay.frontend.data.set.view.table.FrontendDataSetTableSchemaField;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @author Javier de Arcos
 */
@Component(
	enabled = true,
	property = "frontend.data.set.name=" + FrontendDataSetSampleFrontendDataSetNames.HEADLESS_SAMPLE,
	service = FrontendDataSetView.class
)
public class SampleTableFrontendDataSetView
	extends BaseTableFrontendDataSetView {

	@Override
	public FrontendDataSetTableSchema getFrontendDataSetTableSchema(
		Locale locale) {

		FrontendDataSetTableSchemaBuilder frontendDataSetTableSchemaBuilder =
			_frontendDataSetTableSchemaBuilderFactory.create();

		frontendDataSetTableSchemaBuilder.addFrontendDataSetTableSchemaField(
			"id", "id");
		frontendDataSetTableSchemaBuilder.addFrontendDataSetTableSchemaField(
			"title", "Title");
		frontendDataSetTableSchemaBuilder.addFrontendDataSetTableSchemaField(
			"description", "Description");
		frontendDataSetTableSchemaBuilder.addFrontendDataSetTableSchemaField(
			"date", "Date");

		FrontendDataSetTableSchemaField statusFrontendDataSetTableSchemaField =
			frontendDataSetTableSchemaBuilder.
				addFrontendDataSetTableSchemaField("status", "status");

		statusFrontendDataSetTableSchemaField.setContentRenderer("status");

		frontendDataSetTableSchemaBuilder.addFrontendDataSetTableSchemaField(
			"creator.name", "author");

		return frontendDataSetTableSchemaBuilder.build();
	}

	@Reference
	private FrontendDataSetTableSchemaBuilderFactory
		_frontendDataSetTableSchemaBuilderFactory;

}
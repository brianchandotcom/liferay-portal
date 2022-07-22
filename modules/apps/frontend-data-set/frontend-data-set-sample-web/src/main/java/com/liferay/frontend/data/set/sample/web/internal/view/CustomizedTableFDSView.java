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

import com.liferay.frontend.data.set.sample.web.internal.constants.FDSSampleFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 * @author Javier de Arcos
 */
@Component(
	enabled = true,
	property = "frontend.data.set.name=" + FDSSampleFDSNames.CUSTOMIZED,
	service = FDSView.class
)
public class CustomizedTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"id", "id",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).add(
			"title", "title",
			fdsTableSchemaField -> fdsTableSchemaField.setSortable(true)
		).add(
			"description", "description"
		).add(
			"date", "date"
		).add(
			"color", "color"
		).add(
			"status", "status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"status")
		).add(
			"creator.name", "author",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"sampleCustomDataRenderer")
		).build();
	}

	@Override
	public boolean isDefault() {
		return true;
	}

	@Override
	public boolean isQuickActionsEnabled() {
		return true;
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}
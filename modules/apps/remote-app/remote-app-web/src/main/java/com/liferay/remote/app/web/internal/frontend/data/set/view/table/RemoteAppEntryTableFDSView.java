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

package com.liferay.remote.app.web.internal.frontend.data.set.view.table;

import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaField;
import com.liferay.remote.app.web.internal.constants.RemoteAppAdminFDSNames;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bruno Basto
 */
@Component(
	immediate = true,
	property = "frontend.data.set.name=" + RemoteAppAdminFDSNames.REMOTE_APP_ENTRIES,
	service = FDSView.class
)
public class RemoteAppEntryTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		_addFDSTableSchemaField(
			fdsTableSchemaBuilder, "name", "name", "actionLink");
		_addFDSTableSchemaField(fdsTableSchemaBuilder, "type", "type");
		_addFDSTableSchemaField(
			fdsTableSchemaBuilder, "status", "status", "status");

		return fdsTableSchemaBuilder.build();
	}

	private void _addFDSTableSchemaField(
		FDSTableSchemaBuilder fdsTableSchemaBuilder, String fieldName,
		String label) {

		_addFDSTableSchemaField(fdsTableSchemaBuilder, fieldName, label, null);
	}

	private void _addFDSTableSchemaField(
		FDSTableSchemaBuilder fdsTableSchemaBuilder, String fieldName,
		String label, String contentRenderer) {

		FDSTableSchemaField fdsTableSchemaField =
			fdsTableSchemaBuilder.addFDSTableSchemaField(fieldName, label);

		if (contentRenderer != null) {
			fdsTableSchemaField.setContentRenderer(contentRenderer);
		}
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}
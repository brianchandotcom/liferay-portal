/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.blueprint.admin.frontend.data.set.view.table;

import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaField;
import com.liferay.search.experiences.web.internal.blueprint.admin.constants.SXPBlueprintAdminFDSNames;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kevin Tan
 */
@Component(
	enabled = false,
	property = "frontend.data.set.name=" + SXPBlueprintAdminFDSNames.SXP_BLUEPRINTS,
	service = FDSView.class
)
public class SXPBlueprintsTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		FDSTableSchemaField titleFDSTableSchemaField =
			fdsTableSchemaBuilder.addFDSTableSchemaField("title", "title");

		titleFDSTableSchemaField.setContentRenderer("actionLink");

		titleFDSTableSchemaField.setSortable(true);

		fdsTableSchemaBuilder.addFDSTableSchemaField(
			"description", "description");

		fdsTableSchemaBuilder.addFDSTableSchemaField("id", "id");

		fdsTableSchemaBuilder.addFDSTableSchemaField("userName", "author");

		FDSTableSchemaField createDateFDSTableSchemaField =
			fdsTableSchemaBuilder.addFDSTableSchemaField(
				"createDate", "created");

		createDateFDSTableSchemaField.setContentRenderer("dateTime");

		createDateFDSTableSchemaField.setSortable(true);

		FDSTableSchemaField modifiedDateFDSTableSchemaField =
			fdsTableSchemaBuilder.addFDSTableSchemaField(
				"modifiedDate", "modified");

		modifiedDateFDSTableSchemaField.setContentRenderer("dateTime");

		modifiedDateFDSTableSchemaField.setSortable(true);

		return fdsTableSchemaBuilder.build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}
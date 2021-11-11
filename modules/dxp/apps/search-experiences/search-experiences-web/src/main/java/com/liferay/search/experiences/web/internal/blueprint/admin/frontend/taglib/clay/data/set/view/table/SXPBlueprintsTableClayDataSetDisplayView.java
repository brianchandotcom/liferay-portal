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

package com.liferay.search.experiences.web.internal.blueprint.admin.frontend.taglib.clay.data.set.view.table;

import com.liferay.frontend.taglib.clay.data.set.ClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.BaseTableClayDataSetDisplayView;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilder;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaBuilderFactory;
import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchemaField;
import com.liferay.search.experiences.web.internal.blueprint.admin.constants.SXPBlueprintAdminClayDataSetDisplayNames;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Locale;

/**
 * @author Kevin Tan
 */
@Component(
	enabled = true,
	property = "clay.data.set.display.name=" + SXPBlueprintAdminClayDataSetDisplayNames.SXP_BLUEPRINTS,
	service = ClayDataSetDisplayView.class
)
public class SXPBlueprintsTableClayDataSetDisplayView
	extends BaseTableClayDataSetDisplayView {

	@Override
	public ClayTableSchema getClayTableSchema(Locale locale) {
		ClayTableSchemaBuilder clayTableSchemaBuilder =
			_clayTableSchemaBuilderFactory.create();

		ClayTableSchemaField titleClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField("title", "title");

		titleClayTableSchemaField.setContentRenderer("actionLink");

		clayTableSchemaBuilder.addClayTableSchemaField(
			"description", "description");

		clayTableSchemaBuilder.addClayTableSchemaField("id", "id");

		clayTableSchemaBuilder.addClayTableSchemaField(
				"userName", "author");

		ClayTableSchemaField createDateClayTableSchemaField =
				clayTableSchemaBuilder.addClayTableSchemaField(
						"createDate", "created");

		createDateClayTableSchemaField.setContentRenderer("date");

		ClayTableSchemaField modifiedDateClayTableSchemaField =
			clayTableSchemaBuilder.addClayTableSchemaField(
				"modifiedDate", "modified");

		modifiedDateClayTableSchemaField.setContentRenderer("date");

		return clayTableSchemaBuilder.build();
	}

	@Reference
	private ClayTableSchemaBuilderFactory _clayTableSchemaBuilderFactory;

}
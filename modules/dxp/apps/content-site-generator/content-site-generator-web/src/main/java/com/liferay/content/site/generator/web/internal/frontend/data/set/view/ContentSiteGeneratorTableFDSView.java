/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.content.site.generator.web.internal.frontend.data.set.view;

import com.liferay.content.site.generator.web.internal.constants.ContentSiteGeneratorFDSNames;
import com.liferay.frontend.data.set.constants.FDSTimeZoneBehaviorConstants;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.DateTimeFDSTableSchemaField;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mylena Monte
 */
@Component(
	property = "frontend.data.set.name=" + ContentSiteGeneratorFDSNames.CONTENT_SITE_GENERATOR,
	service = FDSView.class
)
public class ContentSiteGeneratorTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"name", "name",
			fdsTableSchemaField -> fdsTableSchemaField.setActionId(
				"view"
			).setContentRenderer(
				"actionLink"
			)
		).add(
			"runStatus.name", "status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"label")
		).add(
			_dateTimeField("dateCreated", "created")
		).add(
			_dateTimeField("committedAt", "committed")
		).build();
	}

	@Override
	public boolean isDefault(String fdsName) {
		return true;
	}

	private DateTimeFDSTableSchemaField _dateTimeField(
		String fieldName, String label) {

		DateTimeFDSTableSchemaField dateTimeFDSTableSchemaField =
			new DateTimeFDSTableSchemaField();

		dateTimeFDSTableSchemaField.setContentRenderer(
			"dateTime"
		).setFieldName(
			fieldName
		).setLabel(
			label
		);

		dateTimeFDSTableSchemaField.setTimeZoneBehavior(
			FDSTimeZoneBehaviorConstants.APPLY_THEME_DISPLAY_TIME_ZONE);

		return dateTimeFDSTableSchemaField;
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}
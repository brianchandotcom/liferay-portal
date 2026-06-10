/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.web.internal.frontend.data.set.view.table;

import com.liferay.audience.web.internal.constants.AudienceFDSNames;
import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	property = "frontend.data.set.name=" + AudienceFDSNames.AUDIENCE_ENTRIES,
	service = FDSView.class
)
public class AudienceTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"name", "name",
			fdsTableSchemaField -> {
				fdsTableSchemaField.setActionId("edit");
				fdsTableSchemaField.setContentRenderer("actionLink");
				fdsTableSchemaField.setSortable(true);
			}
		).add(
			"modifiedDate", "modified-date",
			fdsTableSchemaField -> {
				fdsTableSchemaField.setContentRenderer("date");
				fdsTableSchemaField.setSortable(true);
			}
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}
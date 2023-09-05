/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.list.type.frontend.data.set.view.table;

import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.object.web.internal.list.type.constants.ListTypeFDSNames;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "frontend.data.set.name=" + ListTypeFDSNames.LIST_TYPE_DEFINITIONS,
	service = FDSView.class
)
public class ListTypeDefinitionsTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		fdsTableSchemaBuilder = fdsTableSchemaBuilder.add(
			"name", "name",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink"));

		if (FeatureFlagManagerUtil.isEnabled("LPS-193355")) {
			fdsTableSchemaBuilder = fdsTableSchemaBuilder.add(
				"system", "source",
				fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
					"sourceDataRenderer"));
		}

		return fdsTableSchemaBuilder.build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}
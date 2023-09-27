/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.object.definitions.frontend.data.set.view.table;

import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.object.web.internal.object.definitions.constants.ObjectDefinitionsFDSNames;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Gabriel Albuquerque
 */
@Component(
	property = "frontend.data.set.name=" + ObjectDefinitionsFDSNames.OBJECT_FIELDS,
	service = FDSView.class
)
public class ObjectFieldsTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		fdsTableSchemaBuilder.add(
			"label.LANG", "label",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"actionLink")
		).add(
			"businessType", "type"
		).add(
			"required", "mandatory",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"boolean")
		).add(
			"system", "source",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"objectFieldSourceDataRenderer")
		);

		if (FeatureFlagManagerUtil.isEnabled("LPS-172017")) {
			fdsTableSchemaBuilder.add(
				"localized", "translatable",
				fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
					"boolean"));
		}

		return fdsTableSchemaBuilder.build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}
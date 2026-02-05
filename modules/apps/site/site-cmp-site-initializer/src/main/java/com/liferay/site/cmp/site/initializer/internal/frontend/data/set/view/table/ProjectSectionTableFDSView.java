/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.frontend.data.set.view.table;

import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilder;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.site.cmp.site.initializer.internal.constants.CMPSiteInitializerFDSNames;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "frontend.data.set.name=" + CMPSiteInitializerFDSNames.CMP_PROJECT,
	service = FDSView.class
)
public class ProjectSectionTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		FDSTableSchemaBuilder fdsTableSchemaBuilder =
			_fdsTableSchemaBuilderFactory.create();

		return fdsTableSchemaBuilder.add(
			"embedded.title", "title",
			fdsTableSchemaField -> fdsTableSchemaField.setActionId(
				"actionLink"
			).setContentRenderer(
				"simpleActionLinkTableCellRenderer"
			)
		).add(
			"embedded.dueDate", "due-date",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"dueDateTableCellRenderer")
		).add(
			"embedded.completionRate", "completion-rate",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"progressBarTableCellRenderer")
		).add(
			"embedded.r_userToCMPProjectManager_userERC", "manager",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"userRelationshipTableCellRenderer")
		).add(
			"embedded.r_userToCMPProjectSponsor_userERC", "sponsor",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"userRelationshipTableCellRenderer")
		).add(
			"embedded.state", "state",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"stateTableCellRenderer")
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}
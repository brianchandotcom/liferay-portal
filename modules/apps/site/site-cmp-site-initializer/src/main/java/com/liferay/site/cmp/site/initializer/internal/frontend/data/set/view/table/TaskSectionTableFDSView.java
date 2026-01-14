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
import com.liferay.site.cmp.site.initializer.internal.util.FDSViewUtil;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(
	property = "frontend.data.set.name=" + CMPSiteInitializerFDSNames.CMP_TASK,
	service = FDSView.class
)
public class TaskSectionTableFDSView extends BaseTableFDSView {

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
			"embedded.assignTo.name", "assign-to"
		).add(
			"embedded.r_cmpProjectToCMPTasks_c_cmpProject.title", "project"
		).add(
			FDSViewUtil.getDateFDSTableSchemaField(
				"embedded.dueDate", "due-date")
		).add(
			"embedded.state", "state",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"stateTableCellRenderer")
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}
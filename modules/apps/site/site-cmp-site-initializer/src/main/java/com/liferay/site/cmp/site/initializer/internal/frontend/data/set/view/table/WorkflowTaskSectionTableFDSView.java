/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.frontend.data.set.view.table;

import com.liferay.frontend.data.set.view.FDSView;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;
import com.liferay.frontend.data.set.view.table.FDSTableSchema;
import com.liferay.frontend.data.set.view.table.FDSTableSchemaBuilderFactory;
import com.liferay.site.cmp.site.initializer.internal.constants.CMPSiteInitializerFDSNames;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fábio Alves
 */
@Component(
	property = "frontend.data.set.name=" + CMPSiteInitializerFDSNames.CMP_WORKFLOW_TASKS,
	service = FDSView.class
)
public class WorkflowTaskSectionTableFDSView extends BaseTableFDSView {

	@Override
	public FDSTableSchema getFDSTableSchema(Locale locale) {
		return _fdsTableSchemaBuilderFactory.create(
		).add(
			"assetTitle", "asset-title",
			fdsTableSchemaField -> fdsTableSchemaField.setActionId(
				"actionLinkWorkflowTask"
			).setContentRenderer(
				"assetTitleTableCellRenderer"
			)
		).add(
			"assetType", "asset-type",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"assetTypeTableCellRenderer")
		).add(
			"embedded.creator.name", "author",
			fdsTableSchemaField -> fdsTableSchemaField.setLocalizeLabel(true)
		).add(
			"task", "task",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"taskTableCellRenderer")
		).add(
			"dueDate", "due-date",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"dueDateTableCellRenderer")
		).add(
			"state", "status",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"workflowStateTableCellRenderer")
		).add(
			"dateModified", "last-activity-date",
			fdsTableSchemaField -> fdsTableSchemaField.setContentRenderer(
				"dateTime"
			).setSortable(
				true
			)
		).build();
	}

	@Reference
	private FDSTableSchemaBuilderFactory _fdsTableSchemaBuilderFactory;

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.frontend.data.set.view.table;

import com.liferay.frontend.data.set.constants.FDSTimeZoneBehaviorConstants;
import com.liferay.frontend.data.set.view.table.BaseDateFDSTableSchemaField;
import com.liferay.frontend.data.set.view.table.BaseTableFDSView;

/**
 * @author Daniel Raposo
 */
public abstract class BaseProcessesTableFDSView extends BaseTableFDSView {

	public BaseDateFDSTableSchemaField getDateFDSTableSchemaField(
		String contentRenderer, String fieldName, String label) {

		BaseDateFDSTableSchemaField baseDateFDSTableSchemaField =
			new BaseDateFDSTableSchemaField();

		baseDateFDSTableSchemaField.setContentRenderer(
			contentRenderer
		).setFieldName(
			fieldName
		).setLabel(
			label
		).setLocalizeLabel(
			true
		).setSortable(
			true
		);

		baseDateFDSTableSchemaField.setTimeZoneBehavior(
			FDSTimeZoneBehaviorConstants.APPLY_THEME_DISPLAY_TIME_ZONE);

		return baseDateFDSTableSchemaField;
	}

}
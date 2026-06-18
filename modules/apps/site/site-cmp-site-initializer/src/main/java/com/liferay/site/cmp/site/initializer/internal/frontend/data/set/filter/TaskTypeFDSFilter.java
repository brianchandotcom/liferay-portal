/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.constants.FDSEntityFieldTypes;
import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;

import java.util.List;
import java.util.Locale;

/**
 * @author Victor Kammerer
 */
public class TaskTypeFDSFilter extends BaseSelectionFDSFilter {

	public TaskTypeFDSFilter(
		ClassNameLocalService classNameLocalService,
		ObjectDefinition taskObjectDefinition) {

		_classNameLocalService = classNameLocalService;
		_taskObjectDefinition = taskObjectDefinition;
	}

	@Override
	public String getEntityFieldType() {
		return FDSEntityFieldTypes.INTEGER;
	}

	@Override
	public String getId() {
		return "classNameId";
	}

	@Override
	public String getLabel() {
		return "task-type";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return ListUtil.fromArray(
			new SelectionFDSFilterItem(
				"project-tasks",
				_classNameLocalService.getClassNameId(
					_taskObjectDefinition.getClassName())),
			new SelectionFDSFilterItem(
				"workflow-tasks",
				_classNameLocalService.getClassNameId(
					KaleoTaskInstanceToken.class.getName())));
	}

	private final ClassNameLocalService _classNameLocalService;
	private final ObjectDefinition _taskObjectDefinition;

}
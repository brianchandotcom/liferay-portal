/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.dynamic.data.lists.form.web.util;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordIdComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordModifiedDateComparator;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.theme.ThemeDisplay;

import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Leonardo Barros
 */
public class DDLFormAdminPortletUtil {

	public static DDMForm createSettingsDDMForm(ThemeDisplay themeDisplay)
		throws PortalException {

		DDMForm ddmForm = DDMFormFactory.create(DDLRecordSetSettings.class);

		ddmForm.addAvailableLocale(themeDisplay.getLocale());
		ddmForm.setDefaultLocale(themeDisplay.getLocale());

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(false);

		DDMFormField ddmFormField = ddmFormFieldsMap.get("workflowDefinition");

		DDMFormFieldOptions ddmFormFieldOptions =
			createWorkflowDefinitionDDMFormFieldOptions(themeDisplay);

		ddmFormField.setDDMFormFieldOptions(ddmFormFieldOptions);

		return ddmForm;
	}

	public static OrderByComparator<DDLRecord> getRecordOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDLRecord> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new DDLRecordModifiedDateComparator(orderByAsc);
		}
		else {
			orderByComparator = new DDLRecordIdComparator(orderByAsc);
		}

		return orderByComparator;
	}

	protected static DDMFormFieldOptions
			createWorkflowDefinitionDDMFormFieldOptions(
				ThemeDisplay themeDisplay)
		throws PortalException {

		Locale locale = themeDisplay.getLocale();

		DDMFormFieldOptions ddmFormFieldOptions = new DDMFormFieldOptions();

		ddmFormFieldOptions.setDefaultLocale(locale);

		ddmFormFieldOptions.addOptionLabel(
			StringPool.BLANK, locale, LanguageUtil.get(locale, "no-workflow"));

		List<WorkflowDefinition> workflowDefinitions =
			WorkflowDefinitionManagerUtil.getActiveWorkflowDefinitions(
				themeDisplay.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		for (WorkflowDefinition workflowDefinition : workflowDefinitions) {
			String value =
				workflowDefinition.getName() + StringPool.AT +
				workflowDefinition.getVersion();

			String version = LanguageUtil.format(
				locale, "version-x", workflowDefinition.getVersion(), false);

			String label = workflowDefinition.getName() + " (" + version + ")";

			ddmFormFieldOptions.addOptionLabel(value, locale, label);
		}

		return ddmFormFieldOptions;
	}

}
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

import com.liferay.dynamic.data.lists.form.web.configuration.DDLFormWebConfiguration;
import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordIdComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordModifiedDateComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetCreateDateComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetModifiedDateComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetNameComparator;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowDefinition;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Leonardo Barros
 * @author Rafael Praxedes
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

	public static OrderByComparator<DDLRecordSet>
		getDDLRecordSetOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDLRecordSet> orderByComparator = null;

		if (orderByCol.equals("create-date")) {
			orderByComparator = new DDLRecordSetCreateDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("modified-date")) {
			orderByComparator = new DDLRecordSetModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new DDLRecordSetNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public static String getDisplayStyle(
		PortletRequest portletRequest, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			DDLFormWebConfiguration ddlFormWebConfiguration =
				(DDLFormWebConfiguration)portletRequest.getAttribute(
					DDLFormWebConfiguration.class.getName());

			displayStyle = portalPreferences.getValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"display-style", ddlFormWebConfiguration.defaultDisplayView());
		}
		else if (ArrayUtil.contains(displayViews, displayStyle)) {
			portalPreferences.setValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"display-style", displayStyle);
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
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
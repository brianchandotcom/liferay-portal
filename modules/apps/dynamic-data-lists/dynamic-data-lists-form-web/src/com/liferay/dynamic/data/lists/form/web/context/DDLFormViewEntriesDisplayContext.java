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

package com.liferay.dynamic.data.lists.form.web.context;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.form.web.search.RecordSearch;
import com.liferay.dynamic.data.lists.form.web.util.DDLFormAdminPortletUtil;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.render.DDMFormFieldValueRendererRegistryUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngineUtil;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.PortletURLUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;

/**
 * @author Leonardo Barros
 */
public class DDLFormViewEntriesDisplayContext {

	public DDLFormViewEntriesDisplayContext(
		LiferayPortletRequest liferayPortletRequest,
		LiferayPortletResponse liferayPortletResponse,
		DDMStructure ddmStructure, DDLRecordSet recordSet) {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_recordSet = recordSet;
		_ddmFormFields = new ArrayList<>();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)_liferayPortletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(
			ParamUtil.getString(_liferayPortletRequest, "redirect"));

		createRecordSearchContainer(ddmStructure);
	}

	public String getColumnName(int index, DDMFormValues ddmFormValues) {
		DDMFormField ddmFormField = _ddmFormFields.get(index);
		return ddmFormField.getName();
	}

	public String getColumnValue(int index, DDMFormValues ddmFormValues) {
		DDMFormField ddmFormField = _ddmFormFields.get(index);

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap();

		DDMFormFieldValueRenderer ddmFormFieldValueRenderer =
			DDMFormFieldValueRendererRegistryUtil.getDDMFormFieldValueRenderer(
				ddmFormField.getType());

		return ddmFormFieldValueRenderer.render(
			ddmFormFieldValuesMap.get(ddmFormField.getName()),
			_liferayPortletRequest.getLocale());
	}

	public DDMFormValues getDDMFormValues(DDLRecord ddlRecord)
		throws PortalException {

		DDLRecordVersion recordVersion = ddlRecord.getRecordVersion();

		return StorageEngineUtil.getDDMFormValues(
			recordVersion.getDDMStorageId());
	}

	public String getDisplayStyle() {
		return "list";
	}

	public RecordSearch getRecordSearchContainer() {
		return _recordSearchContainer;
	}

	public int getTotalColumns() {
		return _ddmFormFields.size();
	}

	protected void createRecordSearchContainer(DDMStructure ddmStructure) {
		List<String> headerNames = new ArrayList<>();

		List<DDMFormField> ddmFormfields = ddmStructure.getDDMFormFields(false);

		int totalColumns = _COLUMS_TO_SHOW_ON_LIST;

		if (ddmFormfields.size() < totalColumns) {
			totalColumns = ddmFormfields.size();
		}

		for (int i = 0; i < totalColumns; i++) {
			DDMFormField ddmFormField = ddmFormfields.get(i);

			_ddmFormFields.add(ddmFormField);

			LocalizedValue label = ddmFormField.getLabel();

			headerNames.add(
				label.getString(_liferayPortletRequest.getLocale()));
		}

		PortletURL currentURLObj = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		_recordSearchContainer = new RecordSearch(
			_liferayPortletRequest, currentURLObj, headerNames);

		RowChecker rowChecker = new RowChecker(_liferayPortletResponse);

		rowChecker.setCssClass("entry-selector");

		_recordSearchContainer.setRowChecker(rowChecker);

		String orderByCol = ParamUtil.getString(
			_liferayPortletRequest, "orderByCol");
		String orderByType = ParamUtil.getString(
			_liferayPortletRequest, "orderByType");

		PortalPreferences preferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				_liferayPortletRequest);

		if (Validator.isNull(orderByCol)) {
			orderByCol = preferences.getValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"view-entries-order-by-col", "modified-date");
			orderByType = preferences.getValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"view-entries-order-by-type", "asc");
		}
		else {
			preferences.setValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"view-entries-order-by-col", orderByCol);
			preferences.setValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"view-entries-order-by-type", orderByType);
		}

		OrderByComparator<DDLRecord> orderByComparator =
			DDLFormAdminPortletUtil.getRecordOrderByComparator(
				orderByCol, orderByType);

		_recordSearchContainer.setOrderByCol(orderByCol);
		_recordSearchContainer.setOrderByComparator(orderByComparator);
		_recordSearchContainer.setOrderByType(orderByType);

		loadRecordSearchContainer();
	}

	protected void loadRecordSearchContainer() {
		List<DDLRecord> results = null;

		int total = 0;

		DisplayTerms displayTerms = _recordSearchContainer.getDisplayTerms();

		int status = WorkflowConstants.STATUS_ANY;

		if (Validator.isNull(displayTerms.getKeywords())) {
			total = DDLRecordLocalServiceUtil.getRecordsCount(
				_recordSet.getRecordSetId(), status);

			_recordSearchContainer.setTotal(total);

			results = DDLRecordLocalServiceUtil.getRecords(
				_recordSet.getRecordSetId(), status,
				_recordSearchContainer.getStart(),
				_recordSearchContainer.getEnd(),
				_recordSearchContainer.getOrderByComparator());
		}
		else {
			SearchContext searchContext = SearchContextFactory.getInstance(
				_liferayPortletRequest.getHttpServletRequest());

			searchContext.setAttribute(
				"recordSetId", _recordSet.getRecordSetId());
			searchContext.setAttribute(Field.STATUS, status);
			searchContext.setEnd(_recordSearchContainer.getEnd());
			searchContext.setKeywords(displayTerms.getKeywords());
			searchContext.setStart(_recordSearchContainer.getStart());

			BaseModelSearchResult<DDLRecord> baseModelSearchResult =
				DDLRecordLocalServiceUtil.searchDDLRecords(searchContext);

			results = baseModelSearchResult.getBaseModels();

			total = baseModelSearchResult.getLength();

			_recordSearchContainer.setTotal(total);
		}

		_recordSearchContainer.setResults(results);
	}

	private static final int _COLUMS_TO_SHOW_ON_LIST = 5;

	private final List<DDMFormField> _ddmFormFields;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private RecordSearch _recordSearchContainer;
	private final DDLRecordSet _recordSet;

}
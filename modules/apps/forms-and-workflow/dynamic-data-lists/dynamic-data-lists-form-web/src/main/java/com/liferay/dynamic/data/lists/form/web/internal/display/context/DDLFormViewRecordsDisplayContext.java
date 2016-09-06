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

package com.liferay.dynamic.data.lists.form.web.internal.display.context;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.form.web.internal.search.RecordSearch;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Leonardo Barros
 */
public class DDLFormViewRecordsDisplayContext {

	public DDLFormViewRecordsDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			DDLRecordSet ddlRecordSet,
			DDLRecordLocalService ddlRecordLocalService,
			DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
			StorageEngine storageEngine)
		throws PortalException {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddlRecordSet = ddlRecordSet;
		_ddlRecordLocalService = ddlRecordLocalService;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_storageEngine = storageEngine;

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(
			ParamUtil.getString(_renderRequest, "redirect"));

		createRecordSearchContainer(ddlRecordSet.getDDMStructure());
	}

	public String getColumnName(DDMFormField ddmFormField) {
		LocalizedValue label = ddmFormField.getLabel();

		return label.getString(_renderRequest.getLocale());
	}

	public String getColumnValue(
		DDMFormField ddmFormField, List<DDMFormFieldValue> ddmFormFieldValues) {

		if (ddmFormFieldValues == null) {
			return StringPool.BLANK;
		}

		final DDMFormFieldValueRenderer ddmFieldValueRenderer =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueRenderer(
				ddmFormField.getType());

		List<String> renderedDDMFormFielValues = ListUtil.toList(
			ddmFormFieldValues,
			new Function<DDMFormFieldValue, String>() {

				@Override
				public String apply(DDMFormFieldValue ddmFormFieldValue) {
					return ddmFieldValueRenderer.render(
						ddmFormFieldValue, _renderRequest.getLocale());
				}

			});

		return StringUtil.merge(
			renderedDDMFormFielValues, StringPool.COMMA_AND_SPACE);
	}

	public DDLRecordSet getDDLRecordSet() {
		return _ddlRecordSet;
	}

	public List<DDMFormField> getDDMFormFields() {
		return _ddmFormFields;
	}

	public DDMFormValues getDDMFormValues(DDLRecord ddlRecord)
		throws PortalException {

		DDLRecordVersion recordVersion = ddlRecord.getRecordVersion();

		return _storageEngine.getDDMFormValues(recordVersion.getDDMStorageId());
	}

	public String getDisplayStyle() {
		return "list";
	}

	public String getOrderByCol() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_renderRequest);

		String orderByCol = ParamUtil.getString(_renderRequest, "orderByCol");

		if (Validator.isNull(orderByCol)) {
			orderByCol = portalPreferences.getValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"view-entries-order-by-col", "modified-date");
		}
		else {
			portalPreferences.setValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"view-entries-order-by-col", orderByCol);
		}

		return orderByCol;
	}

	public String getOrderByType() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_renderRequest);

		String orderByType = ParamUtil.getString(_renderRequest, "orderByType");

		if (Validator.isNull(orderByType)) {
			orderByType = portalPreferences.getValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"view-entries-order-by-type", "asc");
		}
		else {
			portalPreferences.setValue(
				DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM_ADMIN,
				"view-entries-order-by-type", orderByType);
		}

		return orderByType;
	}

	public RecordSearch getRecordSearchContainer() {
		return _recordSearchContainer;
	}

	public int getStatus(DDLRecord ddlRecord) throws PortalException {
		DDLRecordVersion recordVersion = ddlRecord.getRecordVersion();

		return recordVersion.getStatus();
	}

	protected void createRecordSearchContainer(DDMStructure ddmStructure) {
		List<String> headerNames = new ArrayList<>();

		List<DDMFormField> ddmFormfields = getNontransientDDMFormFields(
			ddmStructure.getDDMForm());

		int totalColumns = _MAX_COLUMNS;

		if (ddmFormfields.size() < totalColumns) {
			totalColumns = ddmFormfields.size();
		}

		for (int i = 0; i < totalColumns; i++) {
			DDMFormField ddmFormField = ddmFormfields.get(i);

			_ddmFormFields.add(ddmFormField);

			LocalizedValue label = ddmFormField.getLabel();

			headerNames.add(label.getString(_renderRequest.getLocale()));
		}

		PortletURL portletURL = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		_recordSearchContainer = new RecordSearch(
			_renderRequest, portletURL, headerNames);

		OrderByComparator<DDLRecord> orderByComparator =
			RecordSearch.getRecordOrderByComparator(
				getOrderByCol(), getOrderByType());

		_recordSearchContainer.setOrderByCol(getOrderByCol());
		_recordSearchContainer.setOrderByComparator(orderByComparator);
		_recordSearchContainer.setOrderByType(getOrderByType());

		updateSearchContainerResults();
	}

	protected List<DDMFormField> getNontransientDDMFormFields(DDMForm ddmForm) {
		List<DDMFormField> ddmFormfields = new ArrayList<>();

		for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
			if (ddmFormField.isTransient()) {
				continue;
			}

			ddmFormfields.add(ddmFormField);
		}

		return ddmFormfields;
	}

	protected void updateSearchContainerResults() {
		List<DDLRecord> results = null;
		int total = 0;

		DisplayTerms displayTerms = _recordSearchContainer.getDisplayTerms();

		int status = WorkflowConstants.STATUS_ANY;

		if (Validator.isNull(displayTerms.getKeywords())) {
			results = _ddlRecordLocalService.getRecords(
				_ddlRecordSet.getRecordSetId(), status,
				_recordSearchContainer.getStart(),
				_recordSearchContainer.getEnd(),
				_recordSearchContainer.getOrderByComparator());
			total = _ddlRecordLocalService.getRecordsCount(
				_ddlRecordSet.getRecordSetId(), status);
		}
		else {
			SearchContext searchContext = SearchContextFactory.getInstance(
				PortalUtil.getHttpServletRequest(_renderRequest));

			searchContext.setAttribute(Field.STATUS, status);
			searchContext.setAttribute(
				"recordSetId", _ddlRecordSet.getRecordSetId());
			searchContext.setAttribute(
				"recordSetScope", _ddlRecordSet.getScope());
			searchContext.setEnd(_recordSearchContainer.getEnd());
			searchContext.setKeywords(displayTerms.getKeywords());
			searchContext.setStart(_recordSearchContainer.getStart());

			BaseModelSearchResult<DDLRecord> baseModelSearchResult =
				_ddlRecordLocalService.searchDDLRecords(searchContext);

			results = baseModelSearchResult.getBaseModels();
			total = baseModelSearchResult.getLength();
		}

		_recordSearchContainer.setResults(results);
		_recordSearchContainer.setTotal(total);
	}

	private static final int _MAX_COLUMNS = 5;

	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDLRecordSet _ddlRecordSet;
	private final List<DDMFormField> _ddmFormFields = new ArrayList<>();
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private RecordSearch _recordSearchContainer;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StorageEngine _storageEngine;

}
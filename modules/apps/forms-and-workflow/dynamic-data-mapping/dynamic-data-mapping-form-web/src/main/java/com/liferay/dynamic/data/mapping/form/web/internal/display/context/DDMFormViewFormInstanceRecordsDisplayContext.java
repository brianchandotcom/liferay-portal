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

package com.liferay.dynamic.data.mapping.form.web.internal.display.context;

import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldValueRenderer;
import com.liferay.dynamic.data.mapping.form.web.internal.search.FormInstanceRecordSearch;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
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
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
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
public class DDMFormViewFormInstanceRecordsDisplayContext {

	public DDMFormViewFormInstanceRecordsDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			DDMFormInstance formInstance,
			DDMFormInstanceRecordLocalService formInstanceRecordLocalService,
			DDMFormFieldTypeServicesTracker formFieldTypeServicesTracker,
			StorageEngine storageEngine)
		throws PortalException {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddmFormInstance = formInstance;
		_ddmFormInstanceRecordLocalService = formInstanceRecordLocalService;
		_ddmFormFieldTypeServicesTracker = formFieldTypeServicesTracker;
		_storageEngine = storageEngine;

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(
			ParamUtil.getString(_renderRequest, "redirect"));

		createFormInstanceRecordSearchContainer(formInstance.getStructure());
	}

	public String getColumnName(DDMFormField formField) {
		LocalizedValue label = formField.getLabel();

		return label.getString(_renderRequest.getLocale());
	}

	public String getColumnValue(
		DDMFormField formField, List<DDMFormFieldValue> formFieldValues) {

		if (formFieldValues == null) {
			return StringPool.BLANK;
		}

		final DDMFormFieldValueRenderer fieldValueRenderer =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldValueRenderer(
				formField.getType());

		List<String> renderedFormFielValues = ListUtil.toList(
			formFieldValues,
			new Function<DDMFormFieldValue, String>() {

				@Override
				public String apply(DDMFormFieldValue formFieldValue) {
					return fieldValueRenderer.render(
						formFieldValue, _renderRequest.getLocale());
				}

			});

		return StringUtil.merge(
			renderedFormFielValues, StringPool.COMMA_AND_SPACE);
	}

	public List<DDMFormField> getDDMFormFields() {
		return _ddmFormFields;
	}

	public DDMFormInstance getDDMFormInstance() {
		return _ddmFormInstance;
	}

	public FormInstanceRecordSearch getDDMFormInstanceRecordSearchContainer() {
		return _ddmFormInstanceRecordSearchContainer;
	}

	public DDMFormValues getDDMFormValues(
			DDMFormInstanceRecord formInstanceRecord)
		throws PortalException {

		DDMFormInstanceRecordVersion formInstanceRecordVersion =
			formInstanceRecord.getFormInstanceRecordVersion();

		return _storageEngine.getDDMFormValues(
			formInstanceRecordVersion.getStorageId());
	}

	public String getDisplayStyle() {
		return "list";
	}

	public List<NavigationItem> getNavigationItems() {
		DDMFormInstance ddmFormInstance = getDDMFormInstance();

		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(
							HtmlUtil.escape(
								ddmFormInstance.getName(
									_renderRequest.getLocale())));
					});
			}
		};
	}

	public String getOrderByCol() {
		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(_renderRequest);

		String orderByCol = ParamUtil.getString(_renderRequest, "orderByCol");

		if (Validator.isNull(orderByCol)) {
			orderByCol = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
				"view-entries-order-by-col", "modified-date");
		}
		else {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
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
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
				"view-entries-order-by-type", "asc");
		}
		else {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
				"view-entries-order-by-type", orderByType);
		}

		return orderByType;
	}

	public int getStatus(DDMFormInstanceRecord formInstanceRecord)
		throws PortalException {

		DDMFormInstanceRecordVersion formInstanceRecordVersion =
			formInstanceRecord.getFormInstanceRecordVersion();

		return formInstanceRecordVersion.getStatus();
	}

	protected void createFormInstanceRecordSearchContainer(
		DDMStructure structure) {

		List<String> headerNames = new ArrayList<>();

		List<DDMFormField> formfields = getNontransientFormFields(
			structure.getDDMForm());

		int totalColumns = _MAX_COLUMNS;

		if (formfields.size() < totalColumns) {
			totalColumns = formfields.size();
		}

		for (int i = 0; i < totalColumns; i++) {
			DDMFormField formField = formfields.get(i);

			_ddmFormFields.add(formField);

			LocalizedValue label = formField.getLabel();

			headerNames.add(label.getString(_renderRequest.getLocale()));
		}

		PortletURL portletURL = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		_ddmFormInstanceRecordSearchContainer = new FormInstanceRecordSearch(
			_renderRequest, portletURL, headerNames);

		OrderByComparator<DDMFormInstanceRecord> orderByComparator =
			FormInstanceRecordSearch.getDDMFormInstanceRecordOrderByComparator(
				getOrderByCol(), getOrderByType());

		_ddmFormInstanceRecordSearchContainer.setOrderByCol(getOrderByCol());
		_ddmFormInstanceRecordSearchContainer.setOrderByComparator(
			orderByComparator);
		_ddmFormInstanceRecordSearchContainer.setOrderByType(getOrderByType());

		updateSearchContainerResults();
	}

	protected List<DDMFormField> getNontransientFormFields(DDMForm form) {
		List<DDMFormField> formfields = new ArrayList<>();

		for (DDMFormField formField : form.getDDMFormFields()) {
			if (formField.isTransient()) {
				continue;
			}

			formfields.add(formField);
		}

		return formfields;
	}

	protected void updateSearchContainerResults() {
		List<DDMFormInstanceRecord> results = null;
		int total = 0;

		DisplayTerms displayTerms =
			_ddmFormInstanceRecordSearchContainer.getDisplayTerms();

		int status = WorkflowConstants.STATUS_ANY;

		if (Validator.isNull(displayTerms.getKeywords())) {
			results = _ddmFormInstanceRecordLocalService.getFormInstanceRecords(
				_ddmFormInstance.getFormInstanceId(), status,
				_ddmFormInstanceRecordSearchContainer.getStart(),
				_ddmFormInstanceRecordSearchContainer.getEnd(),
				_ddmFormInstanceRecordSearchContainer.getOrderByComparator());
			total =
				_ddmFormInstanceRecordLocalService.getFormInstanceRecordsCount(
					_ddmFormInstance.getFormInstanceId(), status);
		}
		else {
			SearchContext searchContext = SearchContextFactory.getInstance(
				PortalUtil.getHttpServletRequest(_renderRequest));

			searchContext.setAttribute(Field.STATUS, status);
			searchContext.setAttribute(
				"formInstanceId", _ddmFormInstance.getFormInstanceId());
			searchContext.setEnd(
				_ddmFormInstanceRecordSearchContainer.getEnd());
			searchContext.setKeywords(displayTerms.getKeywords());
			searchContext.setStart(
				_ddmFormInstanceRecordSearchContainer.getStart());

			BaseModelSearchResult<DDMFormInstanceRecord> baseModelSearchResult =
				_ddmFormInstanceRecordLocalService.searchFormInstanceRecords(
					searchContext);

			results = baseModelSearchResult.getBaseModels();
			total = baseModelSearchResult.getLength();
		}

		_ddmFormInstanceRecordSearchContainer.setResults(results);
		_ddmFormInstanceRecordSearchContainer.setTotal(total);
	}

	private static final int _MAX_COLUMNS = 5;

	private final List<DDMFormField> _ddmFormFields = new ArrayList<>();
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormInstance _ddmFormInstance;
	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private FormInstanceRecordSearch _ddmFormInstanceRecordSearchContainer;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StorageEngine _storageEngine;

}
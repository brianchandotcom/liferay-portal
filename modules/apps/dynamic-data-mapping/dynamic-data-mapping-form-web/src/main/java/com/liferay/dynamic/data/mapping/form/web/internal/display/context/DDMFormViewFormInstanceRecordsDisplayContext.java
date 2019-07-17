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
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
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
import java.util.function.Function;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class DDMFormViewFormInstanceRecordsDisplayContext {

	public DDMFormViewFormInstanceRecordsDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			DDMFormInstance formInstance,
			DDMFormInstanceRecordLocalService formInstanceRecordLocalService,
			DDMFormFieldTypeServicesTracker formFieldTypeServicesTracker)
		throws PortalException {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddmFormInstance = formInstance;
		_ddmFormInstanceRecordLocalService = formInstanceRecordLocalService;
		_ddmFormFieldTypeServicesTracker = formFieldTypeServicesTracker;

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setShowBackIcon(true);
		portletDisplay.setURLBack(
			ParamUtil.getString(_renderRequest, "redirect"));

		setDDMFormFields();
	}

	public List<DropdownItem> getActionItemsDropdownItems() {
		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_renderRequest);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteRecords");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(httpServletRequest, "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getClearResultsURL() throws PortletException {
		PortletURL clearResultsURL = PortletURLUtil.clone(
			getPortletURL(), _renderResponse);

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public String getColumnName(DDMFormField formField) {
		LocalizedValue label = formField.getLabel();

		return label.getString(_renderRequest.getLocale());
	}

	public String getColumnValue(
		DDMFormField formField, List<DDMFormFieldValue> formFieldValues) {

		if ((formField == null) || (formFieldValues == null)) {
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

	public DDMFormValues getDDMFormValues(
			DDMFormInstanceRecord formInstanceRecord)
		throws PortalException {

		DDMFormInstanceRecordVersion formInstanceRecordVersion =
			formInstanceRecord.getFormInstanceRecordVersion();

		return formInstanceRecordVersion.getDDMFormValues();
	}

	public String getDisplayStyle() {
		return "list";
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_renderRequest);

		return new DropdownItemList() {
			{
				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getFilterNavigationDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(
								httpServletRequest, "filter-by-navigation"));
					});

				addGroup(
					dropdownGroupItem -> {
						dropdownGroupItem.setDropdownItems(
							getOrderByDropdownItems());
						dropdownGroupItem.setLabel(
							LanguageUtil.get(httpServletRequest, "order-by"));
					});
			}
		};
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
							HtmlUtil.extractText(
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

	public PortletURL getPortletURL() {
		DDMFormInstance ddmFormInstance = getDDMFormInstance();

		PortletURL portletURL = PortletURLUtil.getCurrent(
			_renderRequest, _renderResponse);

		portletURL.setParameter(
			"mvcPath", "/admin/view_form_instance_records.jsp");
		portletURL.setParameter(
			"redirect", ParamUtil.getString(_renderRequest, "redirect"));
		portletURL.setParameter(
			"formInstanceId",
			String.valueOf(ddmFormInstance.getFormInstanceId()));

		String delta = ParamUtil.getString(_renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String displayStyle = getDisplayStyle();

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", displayStyle);
		}

		String keywords = getKeywords();

		if (Validator.isNotNull(keywords)) {
			portletURL.setParameter("keywords", keywords);
		}

		String orderByCol = getOrderByCol();

		if (Validator.isNotNull(orderByCol)) {
			portletURL.setParameter("orderByCol", orderByCol);
		}

		String orderByType = getOrderByType();

		if (Validator.isNotNull(orderByType)) {
			portletURL.setParameter("orderByType", orderByType);
		}

		return portletURL;
	}

	public SearchContainer<?> getSearch() {
		String displayStyle = getDisplayStyle();

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", displayStyle);

		FormInstanceRecordSearch formInstanceRecordSearch =
			new FormInstanceRecordSearch(
				_renderRequest, portletURL, getHeaderNames());

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMFormInstanceRecord> orderByComparator =
			FormInstanceRecordSearch.getDDMFormInstanceRecordOrderByComparator(
				orderByCol, orderByType);

		formInstanceRecordSearch.setOrderByCol(orderByCol);
		formInstanceRecordSearch.setOrderByComparator(orderByComparator);
		formInstanceRecordSearch.setOrderByType(orderByType);

		if (formInstanceRecordSearch.isSearch()) {
			formInstanceRecordSearch.setEmptyResultsMessage(
				"no-entries-were-found");
		}
		else {
			formInstanceRecordSearch.setEmptyResultsMessage(
				"there-are-no-entries");
		}

		setDDMFormInstanceRecordSearchResults(formInstanceRecordSearch);
		setDDMFormInstanceRecordSearchTotal(formInstanceRecordSearch);

		return formInstanceRecordSearch;
	}

	public String getSearchActionURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		DDMFormInstance ddmFormInstance = getDDMFormInstance();

		portletURL.setParameter(
			"mvcPath", "/admin/view_form_instance_records.jsp");
		portletURL.setParameter(
			"redirect", ParamUtil.getString(_renderRequest, "redirect"));
		portletURL.setParameter(
			"formInstanceId",
			String.valueOf(ddmFormInstance.getFormInstanceId()));

		return portletURL.toString();
	}

	public String getSearchContainerId() {
		return "ddmFormInstanceRecord";
	}

	public String getSortingURL() throws Exception {
		PortletURL sortingURL = PortletURLUtil.clone(
			getPortletURL(), _renderResponse);

		String orderByType = ParamUtil.getString(_renderRequest, "orderByType");

		sortingURL.setParameter(
			"orderByType", orderByType.equals("asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getStatus(DDMFormInstanceRecord formInstanceRecord)
		throws PortalException {

		DDMFormInstanceRecordVersion formInstanceRecordVersion =
			formInstanceRecord.getFormInstanceRecordVersion();

		return formInstanceRecordVersion.getStatus();
	}

	public int getTotalItems() {
		SearchContainer<?> searchContainer = getSearch();

		return searchContainer.getTotal();
	}

	public boolean isDisabledManagementBar() {
		if (hasResults()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_renderRequest);

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);

						dropdownItem.setHref(
							getPortletURL(), "navigation", "all");

						dropdownItem.setLabel(
							LanguageUtil.get(httpServletRequest, "all"));
					});
			}
		};
	}

	protected List<String> getHeaderNames() {
		List<String> headerNames = new ArrayList<>();

		List<DDMFormField> formFields = getDDMFormFields();

		int totalColumns = _MAX_COLUMNS;

		if (formFields.size() < totalColumns) {
			totalColumns = formFields.size();
		}

		for (int i = 0; i < totalColumns; i++) {
			DDMFormField formField = formFields.get(i);

			LocalizedValue label = formField.getLabel();

			headerNames.add(label.getString(_renderRequest.getLocale()));
		}

		return headerNames;
	}

	protected String getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	protected List<DDMFormField> getNontransientFormFields(DDMForm form) {
		List<DDMFormField> formFields = new ArrayList<>();

		for (DDMFormField formField : form.getDDMFormFields()) {
			if (formField.isTransient()) {
				continue;
			}

			formFields.add(formField);
		}

		return formFields;
	}

	protected List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						String orderByCol = "modified-date";

						dropdownItem.setActive(
							orderByCol.equals(getOrderByCol()));
						dropdownItem.setHref(
							getPortletURL(), "orderByCol", orderByCol);
						dropdownItem.setLabel(
							LanguageUtil.get(
								PortalUtil.getHttpServletRequest(
									_renderRequest),
								orderByCol));
					});
			}
		};
	}

	protected SearchContext getSearchContext(int status) {
		SearchContext searchContext = SearchContextFactory.getInstance(
			PortalUtil.getHttpServletRequest(_renderRequest));

		searchContext.setAttribute(Field.STATUS, status);
		searchContext.setAttribute(
			"formInstanceId", _ddmFormInstance.getFormInstanceId());
		searchContext.setEnd(searchContext.getEnd());
		searchContext.setKeywords(getKeywords());
		searchContext.setStart(searchContext.getStart());

		return searchContext;
	}

	protected boolean hasResults() {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected void setDDMFormFields() throws PortalException {
		DDMStructure structure = _ddmFormInstance.getStructure();

		List<DDMFormField> formFields = getNontransientFormFields(
			structure.getDDMForm());

		for (DDMFormField formField : formFields) {
			_ddmFormFields.add(formField);
		}
	}

	protected void setDDMFormInstanceRecordSearchResults(
		FormInstanceRecordSearch formInstanceRecordSearch) {

		List<DDMFormInstanceRecord> results;

		int status = WorkflowConstants.STATUS_ANY;

		if (Validator.isNull(getKeywords())) {
			results = _ddmFormInstanceRecordLocalService.getFormInstanceRecords(
				_ddmFormInstance.getFormInstanceId(), status,
				formInstanceRecordSearch.getStart(),
				formInstanceRecordSearch.getEnd(),
				formInstanceRecordSearch.getOrderByComparator());
		}
		else {
			BaseModelSearchResult<DDMFormInstanceRecord> baseModelSearchResult =
				_ddmFormInstanceRecordLocalService.searchFormInstanceRecords(
					getSearchContext(status));

			results = baseModelSearchResult.getBaseModels();
		}

		formInstanceRecordSearch.setResults(results);
	}

	protected void setDDMFormInstanceRecordSearchTotal(
		FormInstanceRecordSearch ddmFormInstanceRecordSearch) {

		int total;

		int status = WorkflowConstants.STATUS_ANY;

		if (Validator.isNull(getKeywords())) {
			total =
				_ddmFormInstanceRecordLocalService.getFormInstanceRecordsCount(
					_ddmFormInstance.getFormInstanceId(), status);
		}
		else {
			BaseModelSearchResult<DDMFormInstanceRecord> baseModelSearchResult =
				_ddmFormInstanceRecordLocalService.searchFormInstanceRecords(
					getSearchContext(status));

			total = baseModelSearchResult.getLength();
		}

		ddmFormInstanceRecordSearch.setTotal(total);
	}

	private static final int _MAX_COLUMNS = 5;

	private final List<DDMFormField> _ddmFormFields = new ArrayList<>();
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormInstance _ddmFormInstance;
	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;

}
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

package com.liferay.dynamic.data.lists.web.internal.display.context;

import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.constants.DDLPortletKeys;
import com.liferay.dynamic.data.lists.constants.DDLWebKeys;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordCreateDateComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordModifiedDateComparator;
import com.liferay.dynamic.data.lists.web.internal.display.context.util.DDLRequestHelper;
import com.liferay.dynamic.data.lists.web.internal.search.RecordSearch;
import com.liferay.dynamic.data.lists.web.internal.security.permission.resource.DDLRecordSetPermission;
import com.liferay.dynamic.data.mapping.exception.StorageException;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemList;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchContextFactory;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Rafael Praxedes
 */
public class DDLViewRecordsDisplayContext {

	public DDLViewRecordsDisplayContext(
			LiferayPortletRequest liferayPortletRequest,
			LiferayPortletResponse liferayPortletResponse,
			long formDDMTemplateId)
		throws PortalException {

		_liferayPortletRequest = liferayPortletRequest;
		_liferayPortletResponse = liferayPortletResponse;
		_formDDMTemplateId = formDDMTemplateId;

		_ddlRecordSet = (DDLRecordSet)_liferayPortletRequest.getAttribute(
			DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD_SET);

		_ddlRequestHelper = new DDLRequestHelper(
			PortalUtil.getHttpServletRequest(liferayPortletRequest));

		_ddmStructure = _ddlRecordSet.getDDMStructure(formDDMTemplateId);

		User user = PortalUtil.getUser(liferayPortletRequest);

		if (user == null) {
			ThemeDisplay themeDisplay = _ddlRequestHelper.getThemeDisplay();

			user = themeDisplay.getDefaultUser();
		}

		_user = user;
	}

	public List<DropdownItem> getActionItemsDropdownItems()
		throws PortalException {

		if (!hasDeletePermission()) {
			return null;
		}

		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.putData("action", "deleteRecords");
						dropdownItem.setIcon("times-circle");
						dropdownItem.setLabel(
							LanguageUtil.get(
								_ddlRequestHelper.getRequest(), "delete"));
						dropdownItem.setQuickAction(true);
					});
			}
		};
	}

	public String getClearResultsURL() throws PortletException {
		PortletURL clearResultsURL = PortletURLUtil.clone(
			getPortletURL(), _liferayPortletResponse);

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public CreationMenu getCreationMenu() throws PortalException {
		if (!isShowAddRecordButton()) {
			return null;
		}

		return new CreationMenu() {
			{
				addPrimaryDropdownItem(
					dropdownItem -> {
						dropdownItem.setHref(
							_liferayPortletResponse.createRenderURL(),
							"mvcPath", "/edit_record.jsp", "redirect",
							PortalUtil.getCurrentURL(
								_ddlRequestHelper.getRequest()),
							"recordSetId",
							String.valueOf(_ddlRecordSet.getRecordSetId()),
							"formDDMTemplateId",
							String.valueOf(_formDDMTemplateId));

						dropdownItem.setLabel(
							LanguageUtil.format(
								_ddlRequestHelper.getRequest(), "add-x",
								HtmlUtil.escape(
									_ddmStructure.getName(
										_ddlRequestHelper.getLocale())),
								false));
					});
			}
		};
	}

	public OrderByComparator<DDLRecord> getDDLRecordOrderByComparator(
		String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDLRecord> orderByComparator;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new DDLRecordModifiedDateComparator(orderByAsc);
		}
		else {
			orderByComparator = new DDLRecordCreateDateComparator(orderByAsc);
		}

		return orderByComparator;
	}

	public List<DDMFormField> getDDMFormFields() {
		if (_ddmFormFields == null) {
			DDMForm ddmForm = _ddmStructure.getDDMForm();

			List<DDMFormField> ddmFormFields = new ArrayList<>();

			for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
				addDDMFormField(ddmFormFields, ddmFormField);
			}

			int totalColumns = _TOTAL_COLUMNS;

			if (ddmFormFields.size() < totalColumns) {
				totalColumns = ddmFormFields.size();
			}

			_ddmFormFields = ddmFormFields.subList(0, totalColumns);
		}

		return _ddmFormFields;
	}

	public Map<String, List<DDMFormFieldValue>> getDDMFormFieldValuesMap(
			DDLRecordVersion recordVersion)
		throws StorageException {

		DDMFormValues ddmFormValues = recordVersion.getDDMFormValues();

		List<DDMFormFieldValue> ddmFormFieldValues =
			ddmFormValues.getDDMFormFieldValues();

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			new LinkedHashMap<>();

		for (DDMFormFieldValue ddmFormFieldValue : ddmFormFieldValues) {
			putDDMFormFieldValue(ddmFormFieldValuesMap, ddmFormFieldValue);
		}

		return ddmFormFieldValuesMap;
	}

	public DDMStructure getDDMStructure() {
		return _ddmStructure;
	}

	public String getDisplayStyle() {
		return "list";
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		HttpServletRequest httpServletRequest = _ddlRequestHelper.getRequest();

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
		return new NavigationItemList() {
			{
				add(
					navigationItem -> {
						navigationItem.setActive(true);
						navigationItem.setHref(StringPool.BLANK);
						navigationItem.setLabel(
							HtmlUtil.extractText(
								_ddlRecordSet.getName(
									_ddlRequestHelper.getLocale())));
					});
			}
		};
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			_liferayPortletRequest, "orderByCol", "modified-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(
			_liferayPortletRequest, "orderByType", "asc");
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = PortletURLUtil.getCurrent(
			_liferayPortletRequest, _liferayPortletResponse);

		portletURL.setParameter("mvcPath", getMVCPath());
		portletURL.setParameter(
			"redirect",
			ParamUtil.getString(_liferayPortletRequest, "redirect"));
		portletURL.setParameter(
			"recordSetId", String.valueOf(_ddlRecordSet.getRecordSetId()));

		String delta = ParamUtil.getString(_liferayPortletRequest, "delta");

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

	public SearchContainer<?> getSearch() throws PortalException {
		String displayStyle = getDisplayStyle();

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", displayStyle);

		List<String> headerNames = new ArrayList<>();

		List<DDMFormField> ddmFormFields = getDDMFormFields();

		for (DDMFormField ddmFormField : ddmFormFields) {
			LocalizedValue label = ddmFormField.getLabel();

			headerNames.add(label.getString(_ddlRequestHelper.getLocale()));
		}

		if (hasUpdatePermission()) {
			headerNames.add("status");
			headerNames.add("modified-date");
			headerNames.add("author");
		}

		headerNames.add(StringPool.BLANK);

		SearchContainer recordSearch = new RecordSearch(
			_liferayPortletRequest, portletURL, headerNames);

		if (!_user.isDefaultUser()) {
			recordSearch.setRowChecker(
				new EmptyOnClickRowChecker(_liferayPortletResponse));
		}

		OrderByComparator<DDLRecord> orderByComparator =
			getDDLRecordOrderByComparator(getOrderByCol(), getOrderByType());

		recordSearch.setOrderByCol(getOrderByCol());
		recordSearch.setOrderByComparator(orderByComparator);
		recordSearch.setOrderByType(getOrderByType());

		if (recordSearch.isSearch()) {
			recordSearch.setEmptyResultsMessage(
				LanguageUtil.format(
					_ddlRequestHelper.getLocale(), "no-x-records-were-found",
					_ddlRecordSet.getName(), false));
		}
		else {
			recordSearch.setEmptyResultsMessage("there-are-no-records");
		}

		setDDLRecordSearchResults(recordSearch);
		setDDLRecordSearchTotal(recordSearch);

		return recordSearch;
	}

	public String getSearchActionURL() {
		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcPath", getMVCPath());
		portletURL.setParameter(
			"redirect", PortalUtil.getCurrentURL(_liferayPortletRequest));
		portletURL.setParameter(
			"recordSetId", String.valueOf(_ddlRecordSet.getRecordSetId()));

		return portletURL.toString();
	}

	public String getSearchContainerId() {
		return "ddlRecord";
	}

	public String getSortingURL() throws Exception {
		PortletURL sortingURL = PortletURLUtil.clone(
			getPortletURL(), _liferayPortletResponse);

		String orderByType = ParamUtil.getString(
			_liferayPortletRequest, "orderByType");

		sortingURL.setParameter(
			"orderByType", orderByType.equals("asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public int getTotalItems() throws PortalException {
		SearchContainer<?> searchContainer = getSearch();

		return searchContainer.getTotal();
	}

	public boolean hasDeletePermission() throws PortalException {
		boolean hasDeletePermission = false;

		if (isEditable() || isAdminPortlet()) {
			hasDeletePermission = DDLRecordSetPermission.contains(
				getPermissionChecker(), _ddlRecordSet.getRecordSetId(),
				ActionKeys.DELETE);
		}

		return hasDeletePermission;
	}

	public boolean hasUpdatePermission() throws PortalException {
		boolean hasUpdatePermission = false;

		if (isEditable() || isAdminPortlet()) {
			hasUpdatePermission = DDLRecordSetPermission.contains(
				getPermissionChecker(), _ddlRecordSet.getRecordSetId(),
				ActionKeys.UPDATE);
		}

		return hasUpdatePermission;
	}

	public boolean isAdminPortlet() {
		String portletName = getPortletName();

		return portletName.equals(DDLPortletKeys.DYNAMIC_DATA_LISTS);
	}

	public boolean isDisabledManagementBar() throws PortalException {
		if (hasResults()) {
			return false;
		}

		if (isSearch()) {
			return false;
		}

		return true;
	}

	public boolean isEditable() {
		if (isAdminPortlet()) {
			return true;
		}

		return PrefsParamUtil.getBoolean(
			_ddlRequestHelper.getPortletPreferences(),
			_ddlRequestHelper.getRenderRequest(), "editable", true);
	}

	public boolean isSelectable() {
		return !_user.isDefaultUser();
	}

	protected void addDDMFormField(
		List<DDMFormField> ddmFormFields, DDMFormField ddmFormField) {

		if (!isDDMFormFieldTransient(ddmFormField)) {
			ddmFormFields.add(ddmFormField);

			return;
		}

		for (DDMFormField nestedDDMFormField :
				ddmFormField.getNestedDDMFormFields()) {

			addDDMFormField(ddmFormFields, nestedDDMFormField);
		}
	}

	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		return new DropdownItemList() {
			{
				add(
					dropdownItem -> {
						dropdownItem.setActive(true);

						dropdownItem.setHref(
							getPortletURL(), "navigation", "all");

						dropdownItem.setLabel(
							LanguageUtil.get(
								_ddlRequestHelper.getRequest(), "all"));
					});
			}
		};
	}

	protected String getKeywords() {
		return ParamUtil.getString(_liferayPortletRequest, "keywords");
	}

	protected String getMVCPath() {
		if (isAdminPortlet()) {
			return "/view_record_set.jsp";
		}

		return "/view_selected_record_set.jsp";
	}

	protected UnsafeConsumer<DropdownItem, Exception> getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(_ddlRequestHelper.getRequest(), orderByCol));
		};
	}

	protected List<DropdownItem> getOrderByDropdownItems() {
		return new DropdownItemList() {
			{
				add(getOrderByDropdownItem("create-date"));
				add(getOrderByDropdownItem("modified-date"));
			}
		};
	}

	protected PermissionChecker getPermissionChecker() {
		return _ddlRequestHelper.getPermissionChecker();
	}

	protected String getPortletName() {
		return _ddlRequestHelper.getPortletName();
	}

	protected SearchContext getSearchContext(
		SearchContainer recordSearch, int status) {

		SearchContext searchContext = SearchContextFactory.getInstance(
			_ddlRequestHelper.getRequest());

		searchContext.setAttribute(Field.STATUS, status);
		searchContext.setAttribute(
			"recordSetId", _ddlRecordSet.getRecordSetId());
		searchContext.setAttribute("recordSetScope", _ddlRecordSet.getScope());
		searchContext.setEnd(recordSearch.getEnd());
		searchContext.setKeywords(getKeywords());
		searchContext.setStart(recordSearch.getStart());

		return searchContext;
	}

	protected boolean hasResults() throws PortalException {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	protected boolean isDDMFormFieldTransient(DDMFormField ddmFormField) {
		if (Validator.isNull(ddmFormField.getDataType())) {
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

	protected boolean isShowAddRecordButton() throws PortalException {
		boolean showAddRecordButton = false;

		if (isEditable()) {
			showAddRecordButton = DDLRecordSetPermission.contains(
				getPermissionChecker(), _ddlRecordSet.getRecordSetId(),
				DDLActionKeys.ADD_RECORD);
		}

		return showAddRecordButton;
	}

	protected void putDDMFormFieldValue(
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap,
		DDMFormFieldValue ddmFormFieldValue) {

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			ddmFormFieldValue.getName());

		if (ddmFormFieldValues == null) {
			ddmFormFieldValues = new ArrayList<>();

			ddmFormFieldValuesMap.put(
				ddmFormFieldValue.getName(), ddmFormFieldValues);
		}

		ddmFormFieldValues.add(ddmFormFieldValue);

		for (DDMFormFieldValue nestedDDMFormFieldValue :
				ddmFormFieldValue.getNestedDDMFormFieldValues()) {

			putDDMFormFieldValue(
				ddmFormFieldValuesMap, nestedDDMFormFieldValue);
		}
	}

	protected void setDDLRecordSearchResults(SearchContainer recordSearch)
		throws PortalException {

		List<DDLRecord> results = null;

		DisplayTerms displayTerms = recordSearch.getDisplayTerms();

		int status = WorkflowConstants.STATUS_APPROVED;

		if (isShowAddRecordButton()) {
			status = WorkflowConstants.STATUS_ANY;
		}

		if (Validator.isNull(displayTerms.getKeywords())) {
			results = DDLRecordLocalServiceUtil.getRecords(
				_ddlRecordSet.getRecordSetId(), status, recordSearch.getStart(),
				recordSearch.getEnd(), recordSearch.getOrderByComparator());
		}
		else {
			SearchContext searchContext = getSearchContext(
				recordSearch, status);

			BaseModelSearchResult<DDLRecord> baseModelSearchResult =
				DDLRecordLocalServiceUtil.searchDDLRecords(searchContext);

			results = baseModelSearchResult.getBaseModels();
		}

		recordSearch.setResults(results);
	}

	protected void setDDLRecordSearchTotal(SearchContainer recordSearch)
		throws PortalException {

		int total;

		DisplayTerms displayTerms = recordSearch.getDisplayTerms();

		int status = WorkflowConstants.STATUS_APPROVED;

		if (isShowAddRecordButton()) {
			status = WorkflowConstants.STATUS_ANY;
		}

		if (Validator.isNull(displayTerms.getKeywords())) {
			total = DDLRecordLocalServiceUtil.getRecordsCount(
				_ddlRecordSet.getRecordSetId(), status);
		}
		else {
			SearchContext searchContext = getSearchContext(
				recordSearch, status);

			BaseModelSearchResult<DDLRecord> baseModelSearchResult =
				DDLRecordLocalServiceUtil.searchDDLRecords(searchContext);

			total = baseModelSearchResult.getLength();
		}

		recordSearch.setTotal(total);
	}

	private static final int _TOTAL_COLUMNS = 5;

	private final DDLRecordSet _ddlRecordSet;
	private final DDLRequestHelper _ddlRequestHelper;
	private List<DDMFormField> _ddmFormFields;
	private final DDMStructure _ddmStructure;
	private final long _formDDMTemplateId;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final User _user;

}
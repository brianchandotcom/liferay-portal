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

import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.dynamic.data.lists.constants.DDLWebKeys;
import com.liferay.dynamic.data.lists.form.web.configuration.DDLFormWebConfiguration;
import com.liferay.dynamic.data.lists.form.web.internal.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.form.web.internal.display.context.util.DDLFormAdminRequestHelper;
import com.liferay.dynamic.data.lists.form.web.internal.instance.lifecycle.AddDefaultSharedFormLayoutPortalInstanceLifecycleListener;
import com.liferay.dynamic.data.lists.form.web.internal.search.RecordSetSearch;
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.model.DDLRecordVersion;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.lists.service.permission.DDLPermission;
import com.liferay.dynamic.data.lists.service.permission.DDLRecordSetPermission;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetCreateDateComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetModifiedDateComparator;
import com.liferay.dynamic.data.lists.util.comparator.DDLRecordSetNameComparator;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesJSONSerializer;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.StorageEngine;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.kernel.util.ResourceBundleLoaderUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowEngineManager;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Bruno Basto
 */
public class DDLFormAdminDisplayContext {

	public DDLFormAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener,
		DDLFormWebConfiguration ddlFormWebConfiguration,
		DDLRecordLocalService ddlRecordLocalService,
		DDLRecordSetService ddlRecordSetService,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormFieldTypesJSONSerializer ddmFormFieldTypesJSONSerializer,
		DDMFormRenderer ddmFormRenderer,
		DDMFormValuesFactory ddmFormValuesFactory,
		DDMFormValuesMerger ddmFormValuesMerger,
		DDMStructureLocalService ddmStructureLocalService,
		DDMStructureService ddmStructureService, JSONFactory jsonFactory,
		StorageEngine storageEngine,
		WorkflowEngineManager workflowEngineManager) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_addDefaultSharedFormLayoutPortalInstanceLifecycleListener =
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener;
		_ddlFormWebConfiguration = ddlFormWebConfiguration;
		_ddlRecordLocalService = ddlRecordLocalService;
		_ddlRecordSetService = ddlRecordSetService;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_ddmFormFieldTypesJSONSerializer = ddmFormFieldTypesJSONSerializer;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesFactory = ddmFormValuesFactory;
		_ddmFormValuesMerger = ddmFormValuesMerger;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmStructureService = ddmStructureService;
		_jsonFactory = jsonFactory;
		_storageEngine = storageEngine;
		_workflowEngineManager = workflowEngineManager;

		ddlFormAdminRequestHelper = new DDLFormAdminRequestHelper(
			renderRequest);
	}

	public int getAutosaveInterval() {
		return _ddlFormWebConfiguration.autosaveInterval();
	}

	public Locale[] getAvailableLocales() {
		Locale[] availableLocales = getFormBuilderContextAvailableLocales();

		if (availableLocales != null) {
			return availableLocales;
		}

		availableLocales = getDDMFormAvailableLocales();

		if (availableLocales != null) {
			return availableLocales;
		}

		return new Locale[] {getSiteDefaultLocale()};
	}

	public long getCompanyId() {
		return ddlFormAdminRequestHelper.getCompanyId();
	}

	public DDLFormViewRecordDisplayContext
		getDDLFormViewRecordDisplayContext() {

		return new DDLFormViewRecordDisplayContext(
			PortalUtil.getHttpServletRequest(_renderRequest),
			PortalUtil.getHttpServletResponse(_renderResponse),
			_ddlRecordLocalService, _ddmFormRenderer, _ddmFormValuesFactory,
			_ddmFormValuesMerger);
	}

	public DDLFormViewRecordsDisplayContext
			getDDLFormViewRecordsDisplayContext()
		throws PortalException {

		return new DDLFormViewRecordsDisplayContext(
			_renderRequest, _renderResponse, getRecordSet(),
			_ddlRecordLocalService, _ddmFormFieldTypeServicesTracker,
			_storageEngine);
	}

	public JSONArray getDDMFormFieldTypesJSONArray() throws PortalException {
		List<DDMFormFieldType> ddmFormFieldTypes =
			_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes();

		String serializedDDMFormFieldTypes =
			_ddmFormFieldTypesJSONSerializer.serialize(ddmFormFieldTypes);

		return _jsonFactory.createJSONArray(serializedDDMFormFieldTypes);
	}

	public String getDDMFormHTML(RenderRequest renderRequest)
		throws PortalException {

		DDLFormViewRecordDisplayContext ddlFormViewRecordDisplayContext =
			getDDLFormViewRecordDisplayContext();

		return ddlFormViewRecordDisplayContext.getDDMFormHTML(renderRequest);
	}

	public DDMStructure getDDMStructure() throws PortalException {
		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return null;
		}

		_ddmStructure = _ddmStructureLocalService.getStructure(
			recordSet.getDDMStructureId());

		return _ddmStructure;
	}

	public DDMStructureService getDDMStructureService() {
		return _ddmStructureService;
	}

	public long getDDMStrucutureId() throws PortalException {
		DDMStructure ddmStructure = getDDMStructure();

		if (ddmStructure == null) {
			return 0;
		}

		return ddmStructure.getStructureId();
	}

	public String getDefaultLanguageId() {
		String defaultLanguageId = getFormBuilderContextDefaultLanguageId();

		if (defaultLanguageId != null) {
			return defaultLanguageId;
		}

		defaultLanguageId = getDDMFormDefaultLanguageId();

		if (defaultLanguageId != null) {
			return defaultLanguageId;
		}

		return LocaleUtil.toLanguageId(getSiteDefaultLocale());
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(
				_renderRequest, _ddlFormWebConfiguration, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	public String getFormDescription() throws PortalException {
		DDLRecordSet recordSet = getRecordSet();

		if (recordSet != null) {
			ThemeDisplay themeDisplay =
				ddlFormAdminRequestHelper.getThemeDisplay();

			return LocalizationUtil.getLocalization(
				recordSet.getDescription(), themeDisplay.getLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("description");
	}

	public String getFormLocalizedDescription() throws PortalException {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> descriptionMap = recordSet.getDescriptionMap();

			for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	public String getFormLocalizedName() throws PortalException {
		JSONObject jsonObject = _jsonFactory.createJSONObject();

		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> nameMap = recordSet.getNameMap();

			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	public String getFormName() throws PortalException {
		DDLRecordSet recordSet = getRecordSet();

		if (recordSet != null) {
			ThemeDisplay themeDisplay =
				ddlFormAdminRequestHelper.getThemeDisplay();

			return LocalizationUtil.getLocalization(
				recordSet.getName(), themeDisplay.getLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("name");
	}

	public String getFormURL() throws PortalException {
		return getFormURL(getRecordSet());
	}

	public String getFormURL(DDLRecordSet recordSet) throws PortalException {
		String formURL = null;

		DDLRecordSetSettings recordSetSettings = recordSet.getSettingsModel();

		if (recordSetSettings.requireAuthentication()) {
			formURL = getRestrictedFormURL();
		}
		else {
			formURL = getSharedFormURL();
		}

		return formURL;
	}

	public JSONFactory getJSONFactory() {
		return _jsonFactory;
	}

	public String getLexiconIconsPath() {
		ThemeDisplay themeDisplay = ddlFormAdminRequestHelper.getThemeDisplay();

		StringBundler sb = new StringBundler(3);

		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/lexicon/icons.svg");
		sb.append(StringPool.POUND);

		return sb.toString();
	}

	public String getOrderByCol() {
		return ParamUtil.getString(_renderRequest, "orderByCol", "create-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(_renderRequest, "orderByType", "desc");
	}

	public PermissionChecker getPermissionChecker() {
		return ddlFormAdminRequestHelper.getPermissionChecker();
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = _renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter("groupId", String.valueOf(getScopeGroupId()));
		portletURL.setParameter("currentTab", "forms");

		return portletURL;
	}

	public String getPreviewFormURL() throws PortalException {
		String publishedFormURL = getPublishedFormURL();

		if (Validator.isNull(publishedFormURL)) {
			return StringPool.BLANK;
		}

		return publishedFormURL.concat("/preview");
	}

	public String getPublishedFormURL() throws PortalException {
		return getPublishedFormURL(_recordSet);
	}

	public String getPublishedFormURL(DDLRecordSet recordSet)
		throws PortalException {

		if (recordSet == null) {
			return StringPool.BLANK;
		}

		String formURL = getFormURL(recordSet);

		return formURL.concat(String.valueOf(recordSet.getRecordSetId()));
	}

	public DDLRecordSet getRecordSet() throws PortalException {
		if (_recordSet != null) {
			return _recordSet;
		}

		long recordSetId = ParamUtil.getLong(_renderRequest, "recordSetId");

		if (recordSetId > 0) {
			_recordSet = _ddlRecordSetService.fetchRecordSet(recordSetId);
		}
		else {
			DDLRecord ddlRecord = getRecord();

			if (ddlRecord != null) {
				_recordSet = ddlRecord.getRecordSet();
			}
		}

		return _recordSet;
	}

	public DDLRecordVersion getRecordVersion() throws PortalException {
		DDLRecord record = getRecord();

		return record.getLatestRecordVersion();
	}

	public RenderRequest getRenderRequest() {
		return _renderRequest;
	}

	public RenderResponse getRenderResponse() {
		return _renderResponse;
	}

	public ResourceBundle getResourceBundle() {
		ResourceBundleLoader portalResourceBundleLoader =
			ResourceBundleLoaderUtil.getPortalResourceBundleLoader();

		ThemeDisplay themeDisplay = ddlFormAdminRequestHelper.getThemeDisplay();

		ResourceBundle portalResourceBundle =
			portalResourceBundleLoader.loadResourceBundle(
				themeDisplay.getLocale());

		ResourceBundle portletResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());

		return new AggregateResourceBundle(
			portletResourceBundle, portalResourceBundle);
	}

	public String getRestrictedFormURL() {
		return _addDefaultSharedFormLayoutPortalInstanceLifecycleListener.
			getFormLayoutURL(ddlFormAdminRequestHelper.getThemeDisplay(), true);
	}

	public long getScopeGroupId() {
		return ddlFormAdminRequestHelper.getScopeGroupId();
	}

	public SearchContainer<?> getSearch() {
		String displayStyle = getDisplayStyle();

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", displayStyle);

		RecordSetSearch recordSetSearch = new RecordSetSearch(
			_renderRequest, portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDLRecordSet> orderByComparator =
			getDDLRecordSetOrderByComparator(orderByCol, orderByType);

		recordSetSearch.setOrderByCol(orderByCol);
		recordSetSearch.setOrderByComparator(orderByComparator);
		recordSetSearch.setOrderByType(orderByType);

		if (recordSetSearch.isSearch()) {
			recordSetSearch.setEmptyResultsMessage("no-forms-were-found");
		}
		else {
			recordSetSearch.setEmptyResultsMessage("there-are-no-forms");
		}

		setRecordSetSearchResults(recordSetSearch);
		setRecordSetSearchTotal(recordSetSearch);

		return recordSetSearch;
	}

	public String getSearchContainerId() {
		return "ddlRecordSet";
	}

	public String getSharedFormURL() {
		return _addDefaultSharedFormLayoutPortalInstanceLifecycleListener.
			getFormLayoutURL(
				ddlFormAdminRequestHelper.getThemeDisplay(), false);
	}

	public boolean isAuthenticationRequired() throws PortalException {
		DDLRecordSet recordSet = getRecordSet();

		if (recordSet == null) {
			return false;
		}

		DDLRecordSetSettings recordSetSettings = recordSet.getSettingsModel();

		return recordSetSettings.requireAuthentication();
	}

	public boolean isDDLRecordWorkflowHandlerDeployed() {
		if (!_workflowEngineManager.isDeployed()) {
			return false;
		}

		WorkflowHandler<DDLRecord> ddlRecordWorkflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				DDLRecord.class.getName());

		if (ddlRecordWorkflowHandler != null) {
			return true;
		}

		return false;
	}

	public boolean isFormPublished() throws PortalException {
		return isFormPublished(getRecordSet());
	}

	public boolean isFormPublished(DDLRecordSet recordSet)
		throws PortalException {

		if (recordSet == null) {
			return false;
		}

		DDLRecordSetSettings recordSetSettings = recordSet.getSettingsModel();

		return recordSetSettings.published();
	}

	public boolean isShowAddButton() {
		return DDLPermission.contains(
			ddlFormAdminRequestHelper.getPermissionChecker(),
			ddlFormAdminRequestHelper.getScopeGroupId(),
			DDLActionKeys.ADD_RECORD_SET);
	}

	public boolean isShowCopyRecordSetButton() {
		return isShowAddButton();
	}

	public boolean isShowCopyURLRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.VIEW);
	}

	public boolean isShowDeleteRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.DELETE);
	}

	public boolean isShowEditRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.UPDATE);
	}

	public boolean isShowExportRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.VIEW);
	}

	public boolean isShowPermissionsIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.PERMISSIONS);
	}

	public boolean isShowSearch() throws PortalException {
		if (hasResults()) {
			return true;
		}

		if (isSearch()) {
			return true;
		}

		return false;
	}

	public boolean isShowViewEntriesRecordSetIcon(DDLRecordSet recordSet) {
		return DDLRecordSetPermission.contains(
			ddlFormAdminRequestHelper.getPermissionChecker(), recordSet,
			ActionKeys.VIEW);
	}

	protected OrderByComparator<DDLRecordSet> getDDLRecordSetOrderByComparator(
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

	protected DDMForm getDDMForm() throws PortalException {
		DDMStructure ddmStructure = getDDMStructure();

		DDMForm ddmForm = new DDMForm();

		if (ddmStructure != null) {
			ddmForm = ddmStructure.getDDMForm();
		}

		return ddmForm;
	}

	protected Locale[] getDDMFormAvailableLocales() {
		try {
			DDMStructure ddmStructure = getDDMStructure();

			if (ddmStructure == null) {
				return null;
			}

			DDMForm ddmForm = ddmStructure.getDDMForm();

			Set<Locale> availableLocales = ddmForm.getAvailableLocales();

			return availableLocales.toArray(
				new Locale[availableLocales.size()]);
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return null;
		}
	}

	protected String getDDMFormDefaultLanguageId() {
		try {
			DDMStructure ddmStructure = getDDMStructure();

			if (ddmStructure == null) {
				return null;
			}

			DDMForm ddmForm = ddmStructure.getDDMForm();

			return LocaleUtil.toLanguageId(ddmForm.getDefaultLocale());
		}
		catch (PortalException pe) {
			_log.error(pe, pe);

			return null;
		}
	}

	protected String getDisplayStyle(
		PortletRequest portletRequest,
		DDLFormWebConfiguration ddlFormWebConfiguration,
		String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
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

	protected Locale[] getFormBuilderContextAvailableLocales() {
		String serializedFormBuilderContext = ParamUtil.getString(
			_renderRequest, "serializedFormBuilderContext");

		if (Validator.isNull(serializedFormBuilderContext)) {
			return null;
		}

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				serializedFormBuilderContext);

			JSONArray jsonArray = jsonObject.getJSONArray(
				"availableLanguageIds");

			Locale[] locales = new Locale[jsonArray.length()];

			for (int i = 0; i < jsonArray.length(); i++) {
				locales[i] = LocaleUtil.fromLanguageId(jsonArray.getString(i));
			}

			return locales;
		}
		catch (JSONException jsone) {
			_log.error("Unable to deserialize form context", jsone);

			return null;
		}
	}

	protected String getFormBuilderContextDefaultLanguageId() {
		String serializedFormBuilderContext = ParamUtil.getString(
			_renderRequest, "serializedFormBuilderContext");

		if (Validator.isNull(serializedFormBuilderContext)) {
			return null;
		}

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				serializedFormBuilderContext);

			return jsonObject.getString("defaultLanguageId");
		}
		catch (JSONException jsone) {
			_log.error("Unable to deserialize form context", jsone);

			return null;
		}
	}

	protected String getJSONObjectLocalizedPropertyFromRequest(
		String propertyName) {

		String propertyValue = ParamUtil.getString(
			ddlFormAdminRequestHelper.getRequest(), propertyName);

		if (Validator.isNull(propertyValue)) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = ddlFormAdminRequestHelper.getThemeDisplay();

		try {
			JSONObject jsonObject = _jsonFactory.createJSONObject(
				propertyValue);

			String languageId = themeDisplay.getLanguageId();

			if (jsonObject.has(languageId)) {
				return jsonObject.getString(languageId);
			}

			return jsonObject.getString(getDefaultLanguageId());
		}
		catch (JSONException jsone) {
			_log.error(
				String.format(
					"Unable to deserialize JSON localized property \"%s\" " +
						"from request",
					propertyName),
				jsone);
		}

		return StringPool.BLANK;
	}

	protected String getKeywords() {
		return ParamUtil.getString(_renderRequest, "keywords");
	}

	protected DDLRecord getRecord() throws PortalException {
		long recordId = ParamUtil.getLong(_renderRequest, "recordId");

		if (recordId > 0) {
			return _ddlRecordLocalService.fetchRecord(recordId);
		}

		HttpServletRequest httpServletRequest =
			ddlFormAdminRequestHelper.getRequest();

		Object record = httpServletRequest.getAttribute(
			DDLWebKeys.DYNAMIC_DATA_LISTS_RECORD);

		if (record instanceof DDLFormRecord) {
			DDLFormRecord formRecord = (DDLFormRecord)record;

			return formRecord.getDDLRecord();
		}
		else {
			return (DDLRecord)record;
		}
	}

	protected String getServletContextPath(Servlet servlet) {
		String proxyPath = _portal.getPathProxy();

		ServletConfig servletConfig = servlet.getServletConfig();

		ServletContext servletContext = servletConfig.getServletContext();

		return proxyPath.concat(servletContext.getContextPath());
	}

	protected Locale getSiteDefaultLocale() {
		ThemeDisplay themeDisplay = ddlFormAdminRequestHelper.getThemeDisplay();

		return themeDisplay.getSiteDefaultLocale();
	}

	protected int getTotal() throws PortalException {
		SearchContainer<?> searchContainer = getSearch();

		return searchContainer.getTotal();
	}

	protected boolean hasResults() throws PortalException {
		if (getTotal() > 0) {
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

	protected String serialize(
		List<DDMDataProviderInstance> ddmDataProviderInstances, Locale locale) {

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		for (DDMDataProviderInstance ddmDataProviderInstance :
				ddmDataProviderInstances) {

			JSONObject jsonObject = toJSONObject(
				ddmDataProviderInstance, locale);

			jsonArray.put(jsonObject);
		}

		return jsonArray.toString();
	}

	protected void setRecordSetSearchResults(RecordSetSearch recordSetSearch) {
		List<DDLRecordSet> results = _ddlRecordSetService.search(
			ddlFormAdminRequestHelper.getCompanyId(),
			ddlFormAdminRequestHelper.getScopeGroupId(), getKeywords(),
			DDLRecordSetConstants.SCOPE_FORMS, recordSetSearch.getStart(),
			recordSetSearch.getEnd(), recordSetSearch.getOrderByComparator());

		recordSetSearch.setResults(results);
	}

	protected void setRecordSetSearchTotal(RecordSetSearch recordSetSearch) {
		int total = _ddlRecordSetService.searchCount(
			ddlFormAdminRequestHelper.getCompanyId(),
			ddlFormAdminRequestHelper.getScopeGroupId(), getKeywords(),
			DDLRecordSetConstants.SCOPE_FORMS);

		recordSetSearch.setTotal(total);
	}

	protected JSONObject toJSONObject(
		DDMDataProviderInstance ddmDataProviderInstance, Locale locale) {

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"id", ddmDataProviderInstance.getDataProviderInstanceId());
		jsonObject.put("name", ddmDataProviderInstance.getName(locale));

		return jsonObject;
	}

	protected final DDLFormAdminRequestHelper ddlFormAdminRequestHelper;

	private static final String[] _DISPLAY_VIEWS = {"descriptive", "list"};

	private static final Log _log = LogFactoryUtil.getLog(
		DDLFormAdminDisplayContext.class);

	private final AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
		_addDefaultSharedFormLayoutPortalInstanceLifecycleListener;
	private final DDLFormWebConfiguration _ddlFormWebConfiguration;
	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DDLRecordSetService _ddlRecordSetService;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormFieldTypesJSONSerializer
		_ddmFormFieldTypesJSONSerializer;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private DDMStructure _ddmStructure;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMStructureService _ddmStructureService;
	private String _displayStyle;
	private final JSONFactory _jsonFactory;
	private Portal _portal;
	private DDLRecordSet _recordSet;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final StorageEngine _storageEngine;
	private final WorkflowEngineManager _workflowEngineManager;

}
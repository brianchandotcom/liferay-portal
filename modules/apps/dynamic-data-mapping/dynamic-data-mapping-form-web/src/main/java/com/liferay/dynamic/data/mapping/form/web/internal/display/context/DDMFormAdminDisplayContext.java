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
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextRequest;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextResponse;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsRequest;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsResponse;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsRetriever;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldType;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormTemplateContextFactory;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.dynamic.data.mapping.form.web.internal.constants.DDMFormWebKeys;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.DDMFormAdminRequestHelper;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.FormInstancePermissionCheckerHelper;
import com.liferay.dynamic.data.mapping.form.web.internal.instance.lifecycle.AddDefaultSharedFormLayoutPortalInstanceLifecycleListener;
import com.liferay.dynamic.data.mapping.form.web.internal.search.DDMFormInstanceRowChecker;
import com.liferay.dynamic.data.mapping.form.web.internal.search.DDMFormInstanceSearch;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializer;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormFieldTypesSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterTracker;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterTracker;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormLayoutFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.dynamic.data.mapping.util.comparator.DDMFormInstanceModifiedDateComparator;
import com.liferay.dynamic.data.mapping.util.comparator.DDMFormInstanceNameComparator;
import com.liferay.frontend.js.loader.modules.extender.npm.NPMResolver;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenuBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemListBuilder;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.ViewTypeItemList;
import com.liferay.frontend.taglib.servlet.taglib.util.EmptyResultMessageKeys;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletURLUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Bruno Basto
 */
public class DDMFormAdminDisplayContext {

	public DDMFormAdminDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener,
		DDMFormBuilderContextFactory ddmFormBuilderContextFactory,
		DDMFormBuilderSettingsRetriever ddmFormBuilderSettingsRetriever,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormFieldTypesSerializer ddmFormFieldTypesSerializer,
		DDMFormInstanceLocalService ddmFormInstanceLocalService,
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService,
		DDMFormInstanceRecordWriterTracker ddmFormInstanceRecordWriterTracker,
		DDMFormInstanceService ddmFormInstanceService,
		DDMFormInstanceVersionLocalService ddmFormInstanceVersionLocalService,
		DDMFormRenderer ddmFormRenderer,
		DDMFormTemplateContextFactory ddmFormTemplateContextFactory,
		DDMFormValuesFactory ddmFormValuesFactory,
		DDMFormValuesMerger ddmFormValuesMerger,
		DDMFormWebConfiguration ddmFormWebConfiguration,
		DDMStorageAdapterTracker ddmStorageAdapterTracker,
		DDMStructureLocalService ddmStructureLocalService,
		DDMStructureService ddmStructureService, JSONFactory jsonFactory,
		NPMResolver npmResolver, Portal portal) {

		_addDefaultSharedFormLayoutPortalInstanceLifecycleListener =
			addDefaultSharedFormLayoutPortalInstanceLifecycleListener;
		_ddmFormBuilderContextFactory = ddmFormBuilderContextFactory;
		_ddmFormBuilderSettingsRetriever = ddmFormBuilderSettingsRetriever;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_ddmFormFieldTypesSerializer = ddmFormFieldTypesSerializer;
		_ddmFormInstanceLocalService = ddmFormInstanceLocalService;
		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
		_ddmFormInstanceRecordWriterTracker =
			ddmFormInstanceRecordWriterTracker;
		_ddmFormInstanceService = ddmFormInstanceService;
		_ddmFormInstanceVersionLocalService =
			ddmFormInstanceVersionLocalService;
		_ddmFormTemplateContextFactory = ddmFormTemplateContextFactory;
		_ddmFormValuesFactory = ddmFormValuesFactory;
		_ddmFormValuesMerger = ddmFormValuesMerger;
		_ddmFormWebConfiguration = ddmFormWebConfiguration;
		_ddmStorageAdapterTracker = ddmStorageAdapterTracker;
		_ddmStructureLocalService = ddmStructureLocalService;
		_ddmStructureService = ddmStructureService;
		_npmResolver = npmResolver;
		_portal = portal;

		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;
		this.ddmFormRenderer = ddmFormRenderer;
		this.jsonFactory = jsonFactory;

		formAdminRequestHelper = new DDMFormAdminRequestHelper(renderRequest);

		_formInstancePermissionCheckerHelper =
			new FormInstancePermissionCheckerHelper(formAdminRequestHelper);
	}

	public List<DropdownItem> getActionItemsDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.putData("action", "deleteFormInstances");
				dropdownItem.setIcon("times-circle");
				dropdownItem.setLabel(
					LanguageUtil.get(
						formAdminRequestHelper.getRequest(), "delete"));
				dropdownItem.setQuickAction(true);
			}
		).build();
	}

	public String getAutocompleteUserURL() {
		LiferayPortletURL autocompleteUserURL =
			(LiferayPortletURL)renderResponse.createResourceURL();

		autocompleteUserURL.setCopyCurrentRenderParameters(false);
		autocompleteUserURL.setResourceID("/admin/autocomplete_user");

		return autocompleteUserURL.toString();
	}

	public int getAutosaveInterval() {
		return _ddmFormWebConfiguration.autosaveInterval();
	}

	public Map<String, String> getAvailableExportExtensions() {
		return _ddmFormInstanceRecordWriterTracker.
			getDDMFormInstanceRecordWriterExtensions();
	}

	public Locale[] getAvailableLocales() {
		Locale[] availableLocales = getFormBuilderContextAvailableLocales();

		if (availableLocales != null) {
			return availableLocales;
		}

		availableLocales = getFormAvailableLocales();

		if (availableLocales != null) {
			return availableLocales;
		}

		return new Locale[] {getDefaultLocale()};
	}

	public String getClearResultsURL() throws PortletException {
		PortletURL clearResultsURL = PortletURLUtil.clone(
			getPortletURL(), renderResponse);

		clearResultsURL.setParameter("keywords", StringPool.BLANK);

		return clearResultsURL.toString();
	}

	public long getCompanyId() {
		return formAdminRequestHelper.getCompanyId();
	}

	public CreationMenu getCreationMenu() {
		if (!_formInstancePermissionCheckerHelper.isShowAddButton()) {
			return null;
		}

		return CreationMenuBuilder.addPrimaryDropdownItem(
			getAddFormDropdownItem()
		).build();
	}

	public String getCSVExport() {
		return _ddmFormWebConfiguration.csvExport();
	}

	public String getDataProviderInstanceParameterSettingsURL()
		throws PortalException {

		DDMFormBuilderSettingsResponse ddmFormBuilderSettingsResponse =
			getDDMFormBuilderSettingsResponse();

		return ddmFormBuilderSettingsResponse.
			getDataProviderInstanceParameterSettingsURL();
	}

	public String getDataProviderInstancesURL() throws PortalException {
		DDMFormBuilderSettingsResponse ddmFormBuilderSettingsResponse =
			getDDMFormBuilderSettingsResponse();

		return ddmFormBuilderSettingsResponse.getDataProviderInstancesURL();
	}

	public Map<String, Object> getDDMFormContext(RenderRequest renderRequest)
		throws Exception {

		return getDDMFormContext(renderRequest, true);
	}

	public Map<String, Object> getDDMFormContext(
			RenderRequest renderRequest, boolean readOnly)
		throws Exception {

		DDMFormViewFormInstanceRecordDisplayContext
			formViewRecordDisplayContext = getFormViewRecordDisplayContext();

		return formViewRecordDisplayContext.getDDMFormContext(
			renderRequest, readOnly);
	}

	public JSONArray getDDMFormFieldTypesJSONArray() throws PortalException {
		List<DDMFormFieldType> availableDDMFormFieldTypes =
			_removeDDMFormFieldTypesOutOfScope(
				_ddmFormFieldTypeServicesTracker.getDDMFormFieldTypes());

		String serializedFormFieldTypes = serialize(availableDDMFormFieldTypes);

		JSONArray jsonArray = jsonFactory.createJSONArray(
			serializedFormFieldTypes);

		HttpServletRequest httpServletRequest =
			formAdminRequestHelper.getRequest();

		HttpServletResponse httpServletResponse =
			PortalUtil.getHttpServletResponse(renderResponse);

		for (int i = 0; i < jsonArray.length(); i++) {
			DDMFormFieldType ddmFormFieldType = availableDDMFormFieldTypes.get(
				i);

			JSONObject jsonObject = jsonArray.getJSONObject(i);

			Class<?> ddmFormFieldTypeSettings =
				ddmFormFieldType.getDDMFormFieldTypeSettings();

			DDMForm ddmForm = DDMFormFactory.create(ddmFormFieldTypeSettings);

			DDMFormLayout ddmFormLayout = DDMFormLayoutFactory.create(
				ddmFormFieldTypeSettings);

			DDMFormRenderingContext ddmFormRenderingContext =
				new DDMFormRenderingContext();

			ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);
			ddmFormRenderingContext.setHttpServletResponse(httpServletResponse);
			ddmFormRenderingContext.setContainerId("settings");
			ddmFormRenderingContext.setLocale(
				LocaleUtil.fromLanguageId(getDefaultLanguageId()));
			ddmFormRenderingContext.setPortletNamespace(
				renderResponse.getNamespace());
			ddmFormRenderingContext.setViewMode(true);

			try {
				Map<String, Object> ddmFormTemplateContext =
					_ddmFormTemplateContextFactory.create(
						ddmForm, ddmFormLayout, ddmFormRenderingContext);

				jsonObject.put("settingsContext", ddmFormTemplateContext);
			}
			catch (PortalException portalException) {
				_log.error(portalException, portalException);
			}
		}

		return jsonArray;
	}

	public DDMFormInstance getDDMFormInstance() throws PortalException {
		if (_ddmFormInstance != null) {
			return _ddmFormInstance;
		}

		long formInstanceId = ParamUtil.getLong(
			renderRequest, "formInstanceId");

		if (formInstanceId > 0) {
			_ddmFormInstance = _ddmFormInstanceService.fetchFormInstance(
				formInstanceId);
		}
		else {
			DDMFormInstanceRecord formInstanceRecord =
				getDDMFormInstanceRecord();

			if (formInstanceRecord != null) {
				_ddmFormInstance = formInstanceRecord.getFormInstance();
			}
		}

		return _ddmFormInstance;
	}

	public DDMFormInstanceRecordVersion getDDMFormInstanceRecordVersion()
		throws PortalException {

		DDMFormInstanceRecord formInstanceRecord = getDDMFormInstanceRecord();

		return formInstanceRecord.getLatestFormInstanceRecordVersion();
	}

	public Map<String, Object> getDDMFormSettingsContext(
			PageContext pageContext)
		throws Exception {

		long formInstanceId = ParamUtil.getLong(
			renderRequest, "formInstanceId");

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext(pageContext, renderRequest);

		setDDMFormRenderingContextDDMFormValues(
			ddmFormRenderingContext, formInstanceId);

		DDMFormLayout ddmFormLayout = DDMFormLayoutFactory.create(
			DDMFormInstanceSettings.class);

		ddmFormLayout.setPaginationMode(DDMFormLayout.TABBED_MODE);

		DDMForm ddmForm = createSettingsDDMForm(formInstanceId, themeDisplay);

		return ddmFormRenderer.getDDMFormTemplateContext(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	public DDMStructure getDDMStructure() throws PortalException {
		if (_ddmStructure != null) {
			return _ddmStructure;
		}

		DDMFormInstance formInstance = getDDMFormInstance();

		if (formInstance == null) {
			return null;
		}

		_ddmStructure = _ddmStructureLocalService.getStructure(
			formInstance.getStructureId());

		return _ddmStructure;
	}

	public long getDDMStructureId() throws PortalException {
		DDMStructure structure = getDDMStructure();

		if (structure == null) {
			return 0;
		}

		return structure.getStructureId();
	}

	public String getDefaultLanguageId() {
		String defaultLanguageId = getFormBuilderContextDefaultLanguageId();

		if (defaultLanguageId != null) {
			return defaultLanguageId;
		}

		defaultLanguageId = getFormDefaultLanguageId();

		if (defaultLanguageId != null) {
			return defaultLanguageId;
		}

		return LocaleUtil.toLanguageId(getDefaultLocale());
	}

	public String getDisplayStyle() {
		if (_displayStyle == null) {
			_displayStyle = getDisplayStyle(
				renderRequest, _ddmFormWebConfiguration, getDisplayViews());
		}

		return _displayStyle;
	}

	public String[] getDisplayViews() {
		return _DISPLAY_VIEWS;
	}

	public List<NavigationItem> getElementSetBuilderNavigationItems() {
		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(true);

				HttpServletRequest httpServletRequest =
					formAdminRequestHelper.getRequest();

				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "builder"));
			}
		).build();
	}

	public List<DropdownItem> getEmptyResultMessageActionItemsDropdownItems() {
		if (!_formInstancePermissionCheckerHelper.isShowAddButton() ||
			isSearch()) {

			return null;
		}

		return DropdownItemListBuilder.add(
			getAddFormDropdownItem()
		).build();
	}

	public EmptyResultMessageKeys.AnimationType
		getEmptyResultMessageAnimationType() {

		if (isSearch()) {
			return EmptyResultMessageKeys.AnimationType.SUCCESS;
		}

		return EmptyResultMessageKeys.AnimationType.EMPTY;
	}

	public String getEmptyResultMessageDescription() {
		if (isSearch()) {
			return StringPool.BLANK;
		}

		HttpServletRequest httpServletRequest =
			formAdminRequestHelper.getRequest();

		return LanguageUtil.get(
			httpServletRequest, "create-forms-to-start-collecting-data");
	}

	public String getEmptyResultsMessage() {
		SearchContainer<?> search = getSearch();

		HttpServletRequest httpServletRequest =
			formAdminRequestHelper.getRequest();

		return LanguageUtil.get(
			httpServletRequest, search.getEmptyResultsMessage());
	}

	public String getFieldSetDefinitionURL() throws PortalException {
		DDMFormBuilderSettingsResponse ddmFormBuilderSettingsResponse =
			getDDMFormBuilderSettingsResponse();

		return ddmFormBuilderSettingsResponse.getFieldSetDefinitionURL();
	}

	public JSONArray getFieldSetsJSONArray() throws PortalException {
		DDMFormBuilderSettingsResponse ddmFormBuilderSettingsResponse =
			getDDMFormBuilderSettingsResponse();

		return ddmFormBuilderSettingsResponse.getFieldSets();
	}

	public List<DropdownItem> getFilterItemsDropdownItems() {
		HttpServletRequest httpServletRequest =
			formAdminRequestHelper.getRequest();

		return DropdownItemListBuilder.addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(
					getFilterNavigationDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(
						httpServletRequest, "filter-by-navigation"));
			}
		).addGroup(
			dropdownGroupItem -> {
				dropdownGroupItem.setDropdownItems(getOrderByDropdownItems());
				dropdownGroupItem.setLabel(
					LanguageUtil.get(httpServletRequest, "order-by"));
			}
		).build();
	}

	public List<NavigationItem> getFormBuilderNavigationItems() {
		HttpServletRequest httpServletRequest =
			formAdminRequestHelper.getRequest();

		int activeNavItem = ParamUtil.getInteger(
			httpServletRequest, "activeNavItem");

		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.putData("action", "showForm");

				if (activeNavItem == _NAV_ITEM_FORM) {
					navigationItem.setActive(true);
				}

				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "form"));
			}
		).add(
			navigationItem -> {
				navigationItem.putData("action", "showRules");

				if (activeNavItem == _NAV_ITEM_RULES) {
					navigationItem.setActive(true);
				}

				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "rules"));
			}
		).add(
			navigationItem -> {
				navigationItem.putData("action", "showReport");

				if (activeNavItem == _NAV_ITEM_REPORT) {
					navigationItem.setActive(true);
				}

				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "entries"));
			}
		).build();
	}

	public String getFormDescription() throws PortalException {
		DDMFormInstance formInstance = getDDMFormInstance();

		if (formInstance != null) {
			return LocalizationUtil.getLocalization(
				formInstance.getDescription(), getFormDefaultLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("description");
	}

	public String getFormLocalizedDescription() throws PortalException {
		JSONObject jsonObject = jsonFactory.createJSONObject();

		DDMFormInstance formInstance = getDDMFormInstance();

		if (formInstance == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> descriptionMap =
				formInstance.getDescriptionMap();

			for (Map.Entry<Locale, String> entry : descriptionMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	public <T> String getFormLocalizedName(T object) throws PortalException {
		DDMFormInstance formInstance = (DDMFormInstance)object;

		JSONObject jsonObject = jsonFactory.createJSONObject();

		if (formInstance == null) {
			jsonObject.put(getDefaultLanguageId(), "");
		}
		else {
			Map<Locale, String> nameMap = formInstance.getNameMap();

			for (Map.Entry<Locale, String> entry : nameMap.entrySet()) {
				jsonObject.put(
					LocaleUtil.toLanguageId(entry.getKey()), entry.getValue());
			}
		}

		return jsonObject.toString();
	}

	public String getFormName() throws PortalException {
		DDMFormInstance formInstance = getDDMFormInstance();

		if (formInstance != null) {
			return LocalizationUtil.getLocalization(
				formInstance.getName(), getFormDefaultLanguageId());
		}

		return getJSONObjectLocalizedPropertyFromRequest("name");
	}

	public String getFormURL() throws PortalException {
		return getFormURL(getDDMFormInstance());
	}

	public String getFormURL(DDMFormInstance formInstance)
		throws PortalException {

		String formURL = null;

		DDMFormInstanceSettings formInstanceSettings =
			formInstance.getSettingsModel();

		if (formInstanceSettings.requireAuthentication()) {
			formURL = getRestrictedFormURL();
		}
		else {
			formURL = getSharedFormURL();
		}

		return formURL;
	}

	public DDMFormViewFormInstanceRecordDisplayContext
		getFormViewRecordDisplayContext() {

		return new DDMFormViewFormInstanceRecordDisplayContext(
			formAdminRequestHelper.getRequest(),
			PortalUtil.getHttpServletResponse(renderResponse),
			_ddmFormInstanceRecordLocalService,
			_ddmFormInstanceVersionLocalService, ddmFormRenderer,
			_ddmFormValuesFactory, _ddmFormValuesMerger);
	}

	public DDMFormViewFormInstanceRecordsDisplayContext
			getFormViewRecordsDisplayContext()
		throws PortalException {

		return new DDMFormViewFormInstanceRecordsDisplayContext(
			renderRequest, renderResponse, getDDMFormInstance(),
			_ddmFormInstanceRecordLocalService,
			_ddmFormFieldTypeServicesTracker);
	}

	public String getFunctionsMetadata() throws PortalException {
		DDMFormBuilderSettingsResponse ddmFormBuilderSettingsResponse =
			getDDMFormBuilderSettingsResponse();

		return ddmFormBuilderSettingsResponse.getFunctionsMetadata();
	}

	public String getFunctionsURL() throws PortalException {
		DDMFormBuilderSettingsResponse ddmFormBuilderSettingsResponse =
			getDDMFormBuilderSettingsResponse();

		return ddmFormBuilderSettingsResponse.getFunctionsURL();
	}

	public String getInvalidDDMFormFieldType(DDMFormInstance ddmFormInstance) {
		try {
			String invalidDDMFormFieldType = _invalidDDMFormFieldTypes.get(
				ddmFormInstance.getFormInstanceId());

			if (invalidDDMFormFieldType != null) {
				return invalidDDMFormFieldType;
			}

			DDMForm ddmForm = ddmFormInstance.getDDMForm();

			for (DDMFormField ddmFormField : ddmForm.getDDMFormFields()) {
				DDMFormFieldType ddmFormFieldType =
					_ddmFormFieldTypeServicesTracker.getDDMFormFieldType(
						ddmFormField.getType());

				if (ddmFormFieldType == null) {
					_invalidDDMFormFieldTypes.put(
						ddmFormInstance.getFormInstanceId(),
						ddmFormField.getType());

					return ddmFormField.getType();
				}
			}

			_invalidDDMFormFieldTypes.put(
				ddmFormInstance.getFormInstanceId(), StringPool.BLANK);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return null;
	}

	public DDMStructureVersion getLatestDDMStructureVersion()
		throws PortalException {

		DDMStructure structure = getDDMStructure();

		if (structure == null) {
			return null;
		}

		return structure.getLatestStructureVersion();
	}

	public long getLatestDDMStructureVersionId() throws PortalException {
		DDMStructureVersion latestDDMStructureVersion =
			getLatestDDMStructureVersion();

		if (latestDDMStructureVersion == null) {
			return 0;
		}

		return latestDDMStructureVersion.getStructureVersionId();
	}

	public String getLexiconIconsPath() {
		ThemeDisplay themeDisplay = formAdminRequestHelper.getThemeDisplay();

		StringBundler sb = new StringBundler(3);

		sb.append(themeDisplay.getPathThemeImages());
		sb.append("/clay/icons.svg");
		sb.append(StringPool.POUND);

		return sb.toString();
	}

	public String getMainRequire() {
		return _npmResolver.resolveModuleName("dynamic-data-mapping-form-web");
	}

	public List<NavigationItem> getNavigationItems() {
		HttpServletRequest httpServletRequest =
			formAdminRequestHelper.getRequest();

		String currentTab = ParamUtil.getString(
			httpServletRequest, "currentTab", "forms");

		return NavigationItemListBuilder.add(
			navigationItem -> {
				navigationItem.setActive(currentTab.equals("forms"));
				navigationItem.setHref(
					renderResponse.createRenderURL(), "currentTab", "forms");
				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "forms"));
			}
		).add(
			navigationItem -> {
				navigationItem.setActive(currentTab.equals("element-set"));
				navigationItem.setHref(
					renderResponse.createRenderURL(), "currentTab",
					"element-set");
				navigationItem.setLabel(
					LanguageUtil.get(httpServletRequest, "element-sets"));
			}
		).add(
			navigationItem -> _populateDDMDataProviderNavigationItem(
				navigationItem)
		).build();
	}

	public String getOrderByCol() {
		return ParamUtil.getString(
			renderRequest, "orderByCol", "modified-date");
	}

	public String getOrderByType() {
		return ParamUtil.getString(renderRequest, "orderByType", "desc");
	}

	public PermissionChecker getPermissionChecker() {
		return formAdminRequestHelper.getPermissionChecker();
	}

	public <T> T getPermissionCheckerHelper() {
		return (T)_formInstancePermissionCheckerHelper;
	}

	public PortletURL getPortletURL() {
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter("currentTab", "forms");
		portletURL.setParameter("groupId", String.valueOf(getScopeGroupId()));

		String delta = ParamUtil.getString(renderRequest, "delta");

		if (Validator.isNotNull(delta)) {
			portletURL.setParameter("delta", delta);
		}

		String displayStyle = ParamUtil.getString(
			renderRequest, "displayStyle");

		if (Validator.isNotNull(displayStyle)) {
			portletURL.setParameter("displayStyle", getDisplayStyle());
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

	public String getPublishedFormURL() throws PortalException {
		return getPublishedFormURL(_ddmFormInstance);
	}

	public String getPublishedFormURL(DDMFormInstance formInstance)
		throws PortalException {

		if (!isFormPublished(formInstance)) {
			return StringPool.BLANK;
		}

		String formURL = getFormURL(formInstance);

		return formURL.concat(String.valueOf(formInstance.getFormInstanceId()));
	}

	public String getRestrictedFormURL() {
		return _addDefaultSharedFormLayoutPortalInstanceLifecycleListener.
			getFormLayoutURL(formAdminRequestHelper.getThemeDisplay(), true);
	}

	public String getRolesURL() throws PortalException {
		DDMFormBuilderSettingsResponse ddmFormBuilderSettingsResponse =
			getDDMFormBuilderSettingsResponse();

		return ddmFormBuilderSettingsResponse.getRolesURL();
	}

	public long getScopeGroupId() {
		return formAdminRequestHelper.getScopeGroupId();
	}

	public SearchContainer<?> getSearch() {
		String displayStyle = getDisplayStyle();

		PortletURL portletURL = getPortletURL();

		portletURL.setParameter("displayStyle", displayStyle);

		DDMFormInstanceSearch ddmFormInstanceSearch = new DDMFormInstanceSearch(
			renderRequest, portletURL);

		String orderByCol = getOrderByCol();
		String orderByType = getOrderByType();

		OrderByComparator<DDMFormInstance> orderByComparator =
			getDDMFormInstanceOrderByComparator(orderByCol, orderByType);

		ddmFormInstanceSearch.setOrderByCol(orderByCol);
		ddmFormInstanceSearch.setOrderByComparator(orderByComparator);
		ddmFormInstanceSearch.setOrderByType(orderByType);

		if (ddmFormInstanceSearch.isSearch()) {
			ddmFormInstanceSearch.setEmptyResultsMessage("no-forms-were-found");
		}
		else {
			ddmFormInstanceSearch.setEmptyResultsMessage("there-are-no-forms");
		}

		ddmFormInstanceSearch.setRowChecker(
			new DDMFormInstanceRowChecker(renderResponse));

		setDDMFormInstanceSearchResults(ddmFormInstanceSearch);
		setDDMFormInstanceSearchTotal(ddmFormInstanceSearch);

		return ddmFormInstanceSearch;
	}

	public String getSearchActionURL() {
		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter("mvcPath", "/admin/view.jsp");
		portletURL.setParameter("currentTab", "forms");
		portletURL.setParameter("groupId", String.valueOf(getScopeGroupId()));

		return portletURL.toString();
	}

	public String getSearchContainerId() {
		return "formInstance";
	}

	public String getSerializedDDMFormRules() throws PortalException {
		DDMFormBuilderSettingsResponse ddmFormBuilderSettingsResponse =
			getDDMFormBuilderSettingsResponse();

		return ddmFormBuilderSettingsResponse.getSerializedDDMFormRules();
	}

	public String getSerializedFormBuilderContext() throws PortalException {
		String serializedFormBuilderContext = ParamUtil.getString(
			renderRequest, "serializedFormBuilderContext");

		ThemeDisplay themeDisplay = formAdminRequestHelper.getThemeDisplay();

		if (Validator.isNotNull(serializedFormBuilderContext)) {
			JSONObject jsonObject = jsonFactory.createJSONObject(
				serializedFormBuilderContext);

			_escape(themeDisplay.getLanguageId(), "description", jsonObject);
			_escape(themeDisplay.getLanguageId(), "name", jsonObject);

			return jsonObject.toString();
		}

		JSONSerializer jsonSerializer = jsonFactory.createJSONSerializer();

		DDMFormBuilderContextRequest ddmFormBuilderContextRequest =
			DDMFormBuilderContextRequest.with(
				Optional.ofNullable(null), themeDisplay.getRequest(),
				themeDisplay.getResponse(),
				LocaleUtil.fromLanguageId(getDefaultLanguageId()), true);

		ddmFormBuilderContextRequest.addProperty(
			"ddmStructureVersion", getLatestDDMStructureVersion());
		ddmFormBuilderContextRequest.addProperty(
			"portletNamespace", renderResponse.getNamespace());

		DDMFormBuilderContextResponse ddmFormBuilderContextResponse =
			_ddmFormBuilderContextFactory.create(ddmFormBuilderContextRequest);

		Map<String, Object> context =
			ddmFormBuilderContextResponse.getContext();

		context.put(
			"activeNavItem",
			ParamUtil.getInteger(
				renderRequest, "activeNavItem", _NAV_ITEM_FORM));

		return jsonSerializer.serializeDeep(context);
	}

	public String getSharedFormURL() {
		return _addDefaultSharedFormLayoutPortalInstanceLifecycleListener.
			getFormLayoutURL(formAdminRequestHelper.getThemeDisplay(), false);
	}

	public String getShareFormInstanceURL(DDMFormInstance formInstance) {
		if (formInstance == null) {
			return StringPool.BLANK;
		}

		PortletURL shareFormInstanceURL = renderResponse.createActionURL();

		shareFormInstanceURL.setParameter(
			ActionRequest.ACTION_NAME, "/admin/share_form_instance");

		if (formInstance != null) {
			shareFormInstanceURL.setParameter(
				"formInstanceId",
				String.valueOf(formInstance.getFormInstanceId()));
		}

		return shareFormInstanceURL.toString();
	}

	public String getSortingURL() throws Exception {
		PortletURL sortingURL = PortletURLUtil.clone(
			getPortletURL(), renderResponse);

		String orderByType = ParamUtil.getString(renderRequest, "orderByType");

		sortingURL.setParameter(
			"orderByType", orderByType.equals("asc") ? "desc" : "asc");

		return sortingURL.toString();
	}

	public DDMStructureService getStructureService() {
		return _ddmStructureService;
	}

	public int getTotalItems() {
		SearchContainer<?> searchContainer = getSearch();

		return searchContainer.getTotal();
	}

	public List<ViewTypeItem> getViewTypesItems() throws Exception {
		PortletURL portletURL = PortletURLUtil.clone(
			getPortletURL(), renderResponse);

		return new ViewTypeItemList(portletURL, getDisplayStyle()) {
			{
				String[] viewTypes = getDisplayViews();

				for (String viewType : viewTypes) {
					if (viewType.equals("descriptive")) {
						addListViewTypeItem();
					}
					else {
						addTableViewTypeItem();
					}
				}
			}
		};
	}

	public boolean hasResults() {
		if (getTotalItems() > 0) {
			return true;
		}

		return false;
	}

	public boolean hasValidDDMFormFields(DDMFormInstance ddmFormInstance) {
		if (Validator.isNull(getInvalidDDMFormFieldType(ddmFormInstance))) {
			return true;
		}

		return false;
	}

	public boolean hasValidStorageType(DDMFormInstance ddmFormInstance) {
		try {
			DDMStorageAdapter ddmStorageAdapter =
				_ddmStorageAdapterTracker.getDDMStorageAdapter(
					ddmFormInstance.getStorageType());

			if (ddmStorageAdapter != null) {
				return true;
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		return false;
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

	public boolean isFormPublished() throws PortalException {
		return isFormPublished(getDDMFormInstance());
	}

	public boolean isFormPublished(DDMFormInstance formInstance)
		throws PortalException {

		if (formInstance == null) {
			return false;
		}

		DDMFormInstanceSettings formInstanceSettings =
			formInstance.getSettingsModel();

		return formInstanceSettings.published();
	}

	public boolean isShowPublishAlert() {
		return ParamUtil.getBoolean(renderRequest, "showPublishAlert");
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		PageContext pageContext, RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setHttpServletRequest(
			_portal.getHttpServletRequest(renderRequest));
		ddmFormRenderingContext.setHttpServletResponse(
			PipingServletResponse.createPipingServletResponse(pageContext));
		ddmFormRenderingContext.setContainerId("settingsDDMForm");
		ddmFormRenderingContext.setLocale(themeDisplay.getLocale());
		ddmFormRenderingContext.setPortletNamespace(
			renderResponse.getNamespace());

		return ddmFormRenderingContext;
	}

	protected DDMForm createSettingsDDMForm(
			long formInstanceId, ThemeDisplay themeDisplay)
		throws PortalException {

		DDMForm ddmForm = DDMFormFactory.create(DDMFormInstanceSettings.class);

		ddmForm.addAvailableLocale(themeDisplay.getLocale());
		ddmForm.setDefaultLocale(themeDisplay.getLocale());

		if (formInstanceId > 0) {
			Map<String, DDMFormField> ddmFormFieldsMap =
				ddmForm.getDDMFormFieldsMap(false);

			DDMFormField ddmFormField = ddmFormFieldsMap.get("storageType");

			ddmFormField.setReadOnly(true);
		}

		return ddmForm;
	}

	protected UnsafeConsumer<DropdownItem, Exception> getAddFormDropdownItem() {
		return dropdownItem -> {
			HttpServletRequest httpServletRequest =
				formAdminRequestHelper.getRequest();

			ThemeDisplay themeDisplay =
				(ThemeDisplay)httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			dropdownItem.setHref(
				renderResponse.createRenderURL(), "mvcRenderCommandName",
				"/admin/edit_form_instance", "redirect",
				PortalUtil.getCurrentURL(httpServletRequest), "groupId",
				String.valueOf(themeDisplay.getScopeGroupId()));

			dropdownItem.setLabel(
				LanguageUtil.get(httpServletRequest, "new-form"));
		};
	}

	protected DDMForm getDDMForm() throws PortalException {
		DDMStructure structure = getDDMStructure();

		DDMForm form = new DDMForm();

		if (structure != null) {
			form = structure.getDDMForm();
		}

		return form;
	}

	protected DDMFormBuilderSettingsResponse getDDMFormBuilderSettingsResponse()
		throws PortalException {

		if (_ddmFormBuilderSettingsResponse != null) {
			return _ddmFormBuilderSettingsResponse;
		}

		ThemeDisplay themeDisplay = formAdminRequestHelper.getThemeDisplay();

		long fieldSetClassNameId = PortalUtil.getClassNameId(
			DDMFormInstance.class);

		DDMFormBuilderSettingsRequest ddmFormBuilderSettingsRequest =
			DDMFormBuilderSettingsRequest.with(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				fieldSetClassNameId, getDDMForm(), themeDisplay.getLocale());

		_ddmFormBuilderSettingsResponse =
			_ddmFormBuilderSettingsRetriever.getSettings(
				ddmFormBuilderSettingsRequest);

		return _ddmFormBuilderSettingsResponse;
	}

	protected OrderByComparator<DDMFormInstance>
		getDDMFormInstanceOrderByComparator(
			String orderByCol, String orderByType) {

		boolean orderByAsc = false;

		if (orderByType.equals("asc")) {
			orderByAsc = true;
		}

		OrderByComparator<DDMFormInstance> orderByComparator = null;

		if (orderByCol.equals("modified-date")) {
			orderByComparator = new DDMFormInstanceModifiedDateComparator(
				orderByAsc);
		}
		else if (orderByCol.equals("name")) {
			orderByComparator = new DDMFormInstanceNameComparator(orderByAsc);
		}

		return orderByComparator;
	}

	protected DDMFormInstanceRecord getDDMFormInstanceRecord() {
		long formInstanceRecordId = ParamUtil.getLong(
			renderRequest, "formInstanceRecordId");

		if (formInstanceRecordId > 0) {
			return _ddmFormInstanceRecordLocalService.fetchFormInstanceRecord(
				formInstanceRecordId);
		}

		HttpServletRequest httpServletRequest =
			formAdminRequestHelper.getRequest();

		return (DDMFormInstanceRecord)httpServletRequest.getAttribute(
			DDMFormWebKeys.DYNAMIC_DATA_MAPPING_FORM_INSTANCE_RECORD);
	}

	protected Locale getDefaultLocale() {
		String i18nLanguageId = (String)renderRequest.getAttribute(
			WebKeys.I18N_LANGUAGE_ID);

		if (Validator.isNotNull(i18nLanguageId)) {
			return LocaleUtil.fromLanguageId(i18nLanguageId);
		}

		ThemeDisplay themeDisplay = formAdminRequestHelper.getThemeDisplay();

		return Optional.ofNullable(
			themeDisplay.getSiteDefaultLocale()
		).orElse(
			themeDisplay.getLocale()
		);
	}

	protected String getDisplayStyle(
		PortletRequest portletRequest,
		DDMFormWebConfiguration formWebConfiguration, String[] displayViews) {

		PortalPreferences portalPreferences =
			PortletPreferencesFactoryUtil.getPortalPreferences(portletRequest);

		String displayStyle = ParamUtil.getString(
			portletRequest, "displayStyle");

		if (Validator.isNull(displayStyle)) {
			displayStyle = portalPreferences.getValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN, "display-style",
				formWebConfiguration.defaultDisplayView());
		}
		else if (ArrayUtil.contains(displayViews, displayStyle)) {
			portalPreferences.setValue(
				DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN, "display-style",
				displayStyle);
		}

		if (!ArrayUtil.contains(displayViews, displayStyle)) {
			displayStyle = displayViews[0];
		}

		return displayStyle;
	}

	protected List<DropdownItem> getFilterNavigationDropdownItems() {
		return DropdownItemListBuilder.add(
			dropdownItem -> {
				dropdownItem.setActive(true);
				dropdownItem.setHref(getPortletURL(), "navigation", "all");
				dropdownItem.setLabel(
					LanguageUtil.get(
						formAdminRequestHelper.getRequest(), "all"));
			}
		).build();
	}

	protected Locale[] getFormAvailableLocales() {
		try {
			DDMStructure structure = getDDMStructure();

			if (structure == null) {
				return null;
			}

			DDMForm form = structure.getDDMForm();

			Set<Locale> availableLocales = form.getAvailableLocales();

			return availableLocales.toArray(new Locale[0]);
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return null;
		}
	}

	protected Locale[] getFormBuilderContextAvailableLocales() {
		String serializedFormBuilderContext = ParamUtil.getString(
			renderRequest, "serializedFormBuilderContext");

		if (Validator.isNull(serializedFormBuilderContext)) {
			return null;
		}

		try {
			JSONObject jsonObject = jsonFactory.createJSONObject(
				serializedFormBuilderContext);

			JSONArray jsonArray = jsonObject.getJSONArray(
				"availableLanguageIds");

			Locale[] locales = new Locale[jsonArray.length()];

			for (int i = 0; i < jsonArray.length(); i++) {
				locales[i] = LocaleUtil.fromLanguageId(jsonArray.getString(i));
			}

			return locales;
		}
		catch (JSONException jsonException) {
			_log.error("Unable to deserialize form context", jsonException);

			return null;
		}
	}

	protected String getFormBuilderContextDefaultLanguageId() {
		String serializedFormBuilderContext = ParamUtil.getString(
			renderRequest, "serializedFormBuilderContext");

		if (Validator.isNull(serializedFormBuilderContext)) {
			return null;
		}

		try {
			JSONObject jsonObject = jsonFactory.createJSONObject(
				serializedFormBuilderContext);

			return jsonObject.getString("defaultLanguageId");
		}
		catch (JSONException jsonException) {
			_log.error("Unable to deserialize form context", jsonException);

			return null;
		}
	}

	protected String getFormDefaultLanguageId() {
		try {
			DDMStructure structure = getDDMStructure();

			if (structure == null) {
				return null;
			}

			DDMForm form = structure.getDDMForm();

			return LocaleUtil.toLanguageId(form.getDefaultLocale());
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);

			return null;
		}
	}

	protected String getJSONObjectLocalizedPropertyFromRequest(
		String propertyName) {

		String propertyValue = ParamUtil.getString(
			formAdminRequestHelper.getRequest(), propertyName);

		if (Validator.isNull(propertyValue)) {
			return StringPool.BLANK;
		}

		ThemeDisplay themeDisplay = formAdminRequestHelper.getThemeDisplay();

		try {
			JSONObject jsonObject = jsonFactory.createJSONObject(propertyValue);

			String languageId = themeDisplay.getLanguageId();

			if (jsonObject.has(languageId)) {
				return jsonObject.getString(languageId);
			}

			return jsonObject.getString(getDefaultLanguageId());
		}
		catch (JSONException jsonException) {
			_log.error(
				String.format(
					"Unable to deserialize JSON localized property \"%s\" " +
						"from request",
					propertyName),
				jsonException);
		}

		return StringPool.BLANK;
	}

	protected String getKeywords() {
		return ParamUtil.getString(renderRequest, "keywords");
	}

	protected UnsafeConsumer<DropdownItem, Exception> getOrderByDropdownItem(
		String orderByCol) {

		return dropdownItem -> {
			dropdownItem.setActive(orderByCol.equals(getOrderByCol()));
			dropdownItem.setHref(getPortletURL(), "orderByCol", orderByCol);
			dropdownItem.setLabel(
				LanguageUtil.get(
					formAdminRequestHelper.getRequest(), orderByCol));
		};
	}

	protected List<DropdownItem> getOrderByDropdownItems() {
		return DropdownItemListBuilder.add(
			getOrderByDropdownItem("modified-date")
		).add(
			getOrderByDropdownItem("name")
		).build();
	}

	protected boolean isSearch() {
		if (Validator.isNotNull(getKeywords())) {
			return true;
		}

		return false;
	}

	protected String serialize(List<DDMFormFieldType> ddmFormFieldTypes) {
		DDMFormFieldTypesSerializerSerializeRequest.Builder builder =
			DDMFormFieldTypesSerializerSerializeRequest.Builder.newBuilder(
				ddmFormFieldTypes);

		DDMFormFieldTypesSerializerSerializeResponse
			ddmFormFieldTypesSerializerSerializeResponse =
				_ddmFormFieldTypesSerializer.serialize(builder.build());

		return ddmFormFieldTypesSerializerSerializeResponse.getContent();
	}

	protected void setDDMFormInstanceSearchResults(
		DDMFormInstanceSearch ddmFormInstanceSearch) {

		List<DDMFormInstance> results = _ddmFormInstanceService.search(
			formAdminRequestHelper.getCompanyId(),
			formAdminRequestHelper.getScopeGroupId(), getKeywords(),
			ddmFormInstanceSearch.getStart(), ddmFormInstanceSearch.getEnd(),
			ddmFormInstanceSearch.getOrderByComparator());

		ddmFormInstanceSearch.setResults(results);
	}

	protected void setDDMFormInstanceSearchTotal(
		DDMFormInstanceSearch ddmFormInstanceSearch) {

		int total = _ddmFormInstanceService.searchCount(
			formAdminRequestHelper.getCompanyId(),
			formAdminRequestHelper.getScopeGroupId(), getKeywords());

		ddmFormInstanceSearch.setTotal(total);
	}

	protected void setDDMFormRenderingContextDDMFormValues(
			DDMFormRenderingContext ddmFormRenderingContext,
			long formInstanceId)
		throws PortalException {

		DDMFormInstance formInstance =
			_ddmFormInstanceLocalService.fetchFormInstance(formInstanceId);

		if (formInstance == null) {
			return;
		}

		ddmFormRenderingContext.setDDMFormValues(
			formInstance.getSettingsDDMFormValues());
	}

	protected final DDMFormRenderer ddmFormRenderer;
	protected final DDMFormAdminRequestHelper formAdminRequestHelper;
	protected final JSONFactory jsonFactory;
	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;

	private void _escape(
		String languageId, String propertyName,
		JSONObject serializedFormBuilderContextJSONObject) {

		if (!serializedFormBuilderContextJSONObject.has(propertyName)) {
			return;
		}

		JSONObject jsonObject =
			serializedFormBuilderContextJSONObject.getJSONObject(propertyName);

		jsonObject.put(
			languageId, HtmlUtil.escape(jsonObject.getString(languageId)));
	}

	private void _populateDDMDataProviderNavigationItem(
		NavigationItem navigationItem) {

		navigationItem.setActive(false);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			formAdminRequestHelper.getLiferayPortletRequest(),
			PortletProviderUtil.getPortletId(
				DDMDataProviderInstance.class.getName(),
				PortletProvider.Action.EDIT),
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/view.jsp");
		portletURL.setParameter(
			"backURL", formAdminRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"refererPortletName",
			DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN);
		portletURL.setParameter(
			"groupId",
			String.valueOf(formAdminRequestHelper.getScopeGroupId()));
		portletURL.setParameter("showBackIcon", Boolean.FALSE.toString());

		navigationItem.setHref(portletURL.toString());

		ResourceBundle moduleResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", formAdminRequestHelper.getLocale(), getClass());

		navigationItem.setLabel(
			LanguageUtil.get(moduleResourceBundle, "data-providers"));
	}

	private List<DDMFormFieldType> _removeDDMFormFieldTypesOutOfScope(
		List<DDMFormFieldType> ddmFormFieldTypes) {

		List<DDMFormFieldType> availableDDMFormFieldTypes = new ArrayList<>();

		ListUtil.filter(
			ddmFormFieldTypes, availableDDMFormFieldTypes,
			ddmFormFieldType ->
				!ddmFormFieldType.getName(
				).equals(
					"geolocation"
				) &&
				!ddmFormFieldType.getName(
				).equals(
					"journal_article"
				) &&
				!ddmFormFieldType.getName(
				).equals(
					"link_to_layout"
				));

		return Collections.unmodifiableList(availableDDMFormFieldTypes);
	}

	private static final String[] _DISPLAY_VIEWS = {"descriptive", "list"};

	private static final int _NAV_ITEM_FORM = 0;

	private static final int _NAV_ITEM_REPORT = 2;

	private static final int _NAV_ITEM_RULES = 1;

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormAdminDisplayContext.class);

	private final AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
		_addDefaultSharedFormLayoutPortalInstanceLifecycleListener;
	private final DDMFormBuilderContextFactory _ddmFormBuilderContextFactory;
	private DDMFormBuilderSettingsResponse _ddmFormBuilderSettingsResponse;
	private final DDMFormBuilderSettingsRetriever
		_ddmFormBuilderSettingsRetriever;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private final DDMFormFieldTypesSerializer _ddmFormFieldTypesSerializer;
	private DDMFormInstance _ddmFormInstance;
	private final DDMFormInstanceLocalService _ddmFormInstanceLocalService;
	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private final DDMFormInstanceRecordWriterTracker
		_ddmFormInstanceRecordWriterTracker;
	private final DDMFormInstanceService _ddmFormInstanceService;
	private final DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;
	private final DDMFormTemplateContextFactory _ddmFormTemplateContextFactory;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private final DDMFormWebConfiguration _ddmFormWebConfiguration;
	private final DDMStorageAdapterTracker _ddmStorageAdapterTracker;
	private DDMStructure _ddmStructure;
	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DDMStructureService _ddmStructureService;
	private String _displayStyle;
	private final FormInstancePermissionCheckerHelper
		_formInstancePermissionCheckerHelper;
	private final Map<Long, String> _invalidDDMFormFieldTypes = new HashMap<>();
	private final NPMResolver _npmResolver;
	private final Portal _portal;

}
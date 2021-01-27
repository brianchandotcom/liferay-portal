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

import com.liferay.dynamic.data.mapping.constants.DDMActionKeys;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesTracker;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.DDMFormGuestUploadFieldUtil;
import com.liferay.dynamic.data.mapping.form.web.internal.display.context.util.DDMFormInstanceStagingUtil;
import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMFormInstancePermission;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutColumn;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutPage;
import com.liferay.dynamic.data.mapping.model.DDMFormLayoutRow;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapter;
import com.liferay.dynamic.data.mapping.storage.DDMStorageAdapterTracker;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.AggregateResourceBundle;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.SessionParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Stream;

import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcellus Tavares
 */
public class DDMFormDisplayContext {

	public DDMFormDisplayContext(
		RenderRequest renderRequest, RenderResponse renderResponse,
		DDMFormFieldTypeServicesTracker ddmFormFieldTypeServicesTracker,
		DDMFormInstanceLocalService ddmFormInstanceLocalService,
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService,
		DDMFormInstanceRecordVersionLocalService
			ddmFormInstanceRecordVersionLocalService,
		DDMFormInstanceService ddmFormInstanceService,
		DDMFormInstanceVersionLocalService ddmFormInstanceVersionLocalService,
		DDMFormRenderer ddmFormRenderer,
		DDMFormValuesFactory ddmFormValuesFactory,
		DDMFormValuesMerger ddmFormValuesMerger,
		DDMFormWebConfiguration ddmFormWebConfiguration,
		DDMStorageAdapterTracker ddmStorageAdapterTracker,
		GroupLocalService groupLocalService, JSONFactory jsonFactory,
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService,
		Portal portal) {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddmFormFieldTypeServicesTracker = ddmFormFieldTypeServicesTracker;
		_ddmFormInstanceLocalService = ddmFormInstanceLocalService;
		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
		_ddmFormInstanceRecordVersionLocalService =
			ddmFormInstanceRecordVersionLocalService;
		_ddmFormInstanceService = ddmFormInstanceService;
		_ddmFormInstanceVersionLocalService =
			ddmFormInstanceVersionLocalService;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesFactory = ddmFormValuesFactory;
		_ddmFormValuesMerger = ddmFormValuesMerger;
		_ddmFormWebConfiguration = ddmFormWebConfiguration;
		_ddmStorageAdapterTracker = ddmStorageAdapterTracker;
		_groupLocalService = groupLocalService;
		_jsonFactory = jsonFactory;
		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
		_portal = portal;

		_containerId = "ddmForm".concat(StringUtil.randomString());

		if (Validator.isNotNull(getPortletResource())) {
			return;
		}

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceLocalService.fetchDDMFormInstance(
				getFormInstanceId());

		if (ddmFormInstance == null) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	public int getAutosaveInterval() {
		return _ddmFormWebConfiguration.autosaveInterval() * 60000;
	}

	public String[] getAvailableLanguageIds() throws PortalException {
		ThemeDisplay themeDisplay = getThemeDisplay();

		Set<Locale> siteAvailablesLocales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		DDMForm ddmForm = getDDMForm();

		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		Stream<Locale> availableLocalesStream = availableLocales.stream();

		return availableLocalesStream.filter(
			locale -> siteAvailablesLocales.contains(locale)
		).map(
			locale -> LanguageUtil.getLanguageId(locale)
		).toArray(
			String[]::new
		);
	}

	public String getContainerId() {
		return _containerId;
	}

	public String getDDMFormHTML() throws PortalException {
		DDMFormInstance ddmFormInstance = getFormInstance();

		if (ddmFormInstance == null) {
			return StringPool.BLANK;
		}

		boolean maximumSubmissionLimitReached =
			DDMFormGuestUploadFieldUtil.isMaximumSubmissionLimitReached(
				ddmFormInstance,
				PortalUtil.getHttpServletRequest(_renderRequest),
				_ddmFormWebConfiguration.
					maximumSubmissionsForGuestUploadFields());

		boolean requireCaptcha = isCaptchaRequired(ddmFormInstance);

		DDMForm ddmForm = getDDMForm(ddmFormInstance, requireCaptcha);

		Map<String, DDMFormField> ddmFormFieldsMap =
			ddmForm.getDDMFormFieldsMap(true);

		for (DDMFormField ddmFormField : ddmFormFieldsMap.values()) {
			if (Objects.equals(ddmFormField.getType(), "document_library")) {
				ddmFormField.setProperty(
					"maximumSubmissionLimitReached",
					maximumSubmissionLimitReached);

				if (ddmFormField.isRepeatable()) {
					ddmFormField.setProperty(
						"maximumRepetitions",
						_ddmFormWebConfiguration.
							maximumRepetitionsForUploadFields());
				}
			}
		}

		DDMFormLayout ddmFormLayout = getDDMFormLayout(
			ddmFormInstance, requireCaptcha);

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext(ddmForm);

		ddmFormRenderingContext.setGroupId(ddmFormInstance.getGroupId());

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion = null;

		DDMFormInstanceRecord formInstanceRecord = getFormInstanceRecord();

		if (formInstanceRecord != null) {
			ddmFormInstanceRecordVersion =
				formInstanceRecord.getLatestFormInstanceRecordVersion();
		}
		else {
			ddmFormInstanceRecordVersion =
				_ddmFormInstanceRecordVersionLocalService.
					fetchLatestFormInstanceRecordVersion(
						getUserId(), getFormInstanceId(),
						getFormInstanceVersion(),
						WorkflowConstants.STATUS_DRAFT);
		}

		if (ddmFormInstanceRecordVersion != null) {
			DDMFormValues mergedDDMFormValues = _ddmFormValuesMerger.merge(
				ddmFormInstanceRecordVersion.getDDMFormValues(),
				ddmFormRenderingContext.getDDMFormValues());

			ddmFormRenderingContext.setDDMFormValues(mergedDDMFormValues);
		}

		if (!hasAddFormInstanceRecordPermission() ||
			!hasValidStorageType(ddmFormInstance)) {

			ddmFormRenderingContext.setReadOnly(true);
		}

		ddmFormRenderingContext.setShowSubmitButton(isShowSubmitButton());
		ddmFormRenderingContext.setSubmitLabel(getSubmitLabel());

		return _ddmFormRenderer.render(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	public DDMFormSuccessPageSettings getDDMFormSuccessPageSettings()
		throws PortalException {

		DDMForm ddmForm = getDDMForm();

		return ddmForm.getDDMFormSuccessPageSettings();
	}

	public String getDefaultLanguageId() throws PortalException {
		String languageId = ParamUtil.getString(_renderRequest, "languageId");

		Locale locale = LocaleUtil.fromLanguageId(languageId, true, false);

		DDMForm ddmForm = getDDMForm();

		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		if (!availableLocales.contains(locale)) {
			locale = getLocale(
				PortalUtil.getHttpServletRequest(_renderRequest), ddmForm);
		}

		return LanguageUtil.getLanguageId(locale);
	}

	public DDMFormInstance getFormInstance() {
		if (_ddmFormInstance != null) {
			return _ddmFormInstance;
		}

		try {
			_ddmFormInstance = _ddmFormInstanceService.fetchFormInstance(
				getFormInstanceId());
		}
		catch (PortalException portalException) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}

			return null;
		}

		return _ddmFormInstance;
	}

	public long getFormInstanceId() {
		if (_ddmFormInstanceId != 0) {
			return _ddmFormInstanceId;
		}

		_ddmFormInstanceId = PrefsParamUtil.getLong(
			_renderRequest.getPreferences(), _renderRequest, "formInstanceId");

		if (_ddmFormInstanceId == 0) {
			_ddmFormInstanceId = getFormInstanceIdFromSession();
		}

		return _ddmFormInstanceId;
	}

	public DDMFormInstanceRecord getFormInstanceRecord() {
		if (_ddmFormInstanceRecord != null) {
			return _ddmFormInstanceRecord;
		}

		_ddmFormInstanceRecord =
			_ddmFormInstanceRecordLocalService.fetchDDMFormInstanceRecord(
				getFormInstanceRecordId());

		return _ddmFormInstanceRecord;
	}

	public long getFormInstanceRecordId() {
		if (_ddmFormInstanceRecordId != 0) {
			return _ddmFormInstanceRecordId;
		}

		return PrefsParamUtil.getLong(
			_renderRequest.getPreferences(), _renderRequest,
			"formInstanceRecordId");
	}

	public String getRedirectURL() throws PortalException {
		DDMFormInstance ddmFormInstance = getFormInstance();

		if (ddmFormInstance == null) {
			return null;
		}

		DDMFormInstanceSettings ddmFormInstanceSettings =
			ddmFormInstance.getSettingsModel();

		return ddmFormInstanceSettings.redirectURL();
	}

	public String getSubmitLabel() throws JSONException, PortalException {
		DDMFormInstance ddmFormInstance = getFormInstance();

		if (ddmFormInstance == null) {
			return StringPool.BLANK;
		}

		DDMFormInstanceSettings ddmFormInstanceSettings =
			ddmFormInstance.getSettingsModel();

		JSONObject jsonObject = _jsonFactory.createJSONObject(
			ddmFormInstanceSettings.submitLabel());

		String submitLabel = jsonObject.getString(getDefaultLanguageId());

		if (Validator.isNotNull(submitLabel)) {
			return submitLabel;
		}

		ResourceBundle resourceBundle = getResourceBundle(
			getLocale(
				PortalUtil.getHttpServletRequest(_renderRequest),
				getDDMForm()));

		if (hasWorkflowEnabled(getFormInstance(), getThemeDisplay())) {
			DDMFormInstanceRecord ddmFormInstanceRecord =
				getFormInstanceRecord();

			if (ddmFormInstanceRecord != null) {
				DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
					ddmFormInstanceRecord.getLatestFormInstanceRecordVersion();

				if (ddmFormInstanceRecordVersion.getStatus() ==
						WorkflowConstants.STATUS_PENDING) {

					return LanguageUtil.get(resourceBundle, "save");
				}
			}

			return LanguageUtil.get(resourceBundle, "submit-for-publication");
		}

		return LanguageUtil.get(resourceBundle, "submit-form");
	}

	public boolean hasAddFormInstanceRecordPermission() throws PortalException {
		if (_hasAddFormInstanceRecordPermission != null) {
			return _hasAddFormInstanceRecordPermission;
		}

		_hasAddFormInstanceRecordPermission = false;

		DDMFormInstance ddmFormInstance = getFormInstance();

		if (ddmFormInstance != null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_hasAddFormInstanceRecordPermission =
				DDMFormInstancePermission.contains(
					themeDisplay.getPermissionChecker(), ddmFormInstance,
					DDMActionKeys.ADD_FORM_INSTANCE_RECORD);
		}

		return _hasAddFormInstanceRecordPermission;
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

	public boolean hasViewPermission() throws PortalException {
		if (_hasViewPermission != null) {
			return _hasViewPermission;
		}

		_hasViewPermission = false;

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceLocalService.fetchFormInstance(getFormInstanceId());

		if (ddmFormInstance != null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_hasViewPermission = DDMFormInstancePermission.contains(
				themeDisplay.getPermissionChecker(), ddmFormInstance,
				ActionKeys.VIEW);
		}

		return _hasViewPermission;
	}

	public boolean isAutosaveEnabled() throws PortalException {
		if (_autosaveEnabled != null) {
			return _autosaveEnabled;
		}

		if (isDefaultUser()) {
			_autosaveEnabled = Boolean.FALSE;
		}
		else {
			DDMFormInstance formInstance = getFormInstance();

			DDMFormInstanceSettings formInstanceSettings =
				formInstance.getSettingsModel();

			_autosaveEnabled =
				formInstanceSettings.autosaveEnabled() &&
				(getAutosaveInterval() > 0);
		}

		return _autosaveEnabled;
	}

	public boolean isFormAvailable() throws PortalException {
		if (isPreview()) {
			return true;
		}

		DDMFormInstance formInstance = getFormInstance();

		if (formInstance != null) {
			Group group = _groupLocalService.getGroup(
				formInstance.getGroupId());

			Group scopeGroup = _groupLocalService.getGroup(
				_portal.getScopeGroupId(_renderRequest));

			if ((group != null) && (scopeGroup != null) &&
				group.isStagingGroup() && !scopeGroup.isStagingGroup()) {

				return false;
			}

			if ((group != null) && group.isStagedRemotely()) {
				ThemeDisplay themeDisplay = getThemeDisplay();

				if (!DDMFormInstanceStagingUtil.
						isFormInstancePublishedToRemoteLive(
							group, themeDisplay.getUser(),
							formInstance.getUuid())) {

					return false;
				}
			}
		}

		if (isSharedURL()) {
			if (isFormPublished() && isFormShared()) {
				return true;
			}

			return false;
		}

		if (formInstance != null) {
			return true;
		}

		return false;
	}

	public boolean isFormShared() {
		PortletSession portletSession = _renderRequest.getPortletSession(false);

		if (portletSession != null) {
			return SessionParamUtil.getBoolean(_renderRequest, "shared");
		}

		return ParamUtil.getBoolean(_renderRequest, "shared");
	}

	public boolean isPreview() throws PortalException {
		ThemeDisplay themeDisplay = getThemeDisplay();

		if (ParamUtil.getBoolean(_renderRequest, "preview") &&
			DDMFormInstancePermission.contains(
				themeDisplay.getPermissionChecker(), getFormInstanceId(),
				ActionKeys.UPDATE)) {

			return true;
		}

		return false;
	}

	public boolean isRememberMe() {
		String rememberMe = CookieKeys.getCookie(
			PortalUtil.getHttpServletRequest(_renderRequest),
			CookieKeys.REMEMBER_ME);

		if ((rememberMe != null) && rememberMe.equals("true")) {
			return true;
		}

		return false;
	}

	public Boolean isRequireAuthentication() throws PortalException {
		DDMFormInstance ddmFormInstance = getFormInstance();

		if (ddmFormInstance == null) {
			return false;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMFormInstanceSettings ddmFormInstanceSettings =
			ddmFormInstance.getSettingsModel();

		Layout layout = themeDisplay.getLayout();

		if (ddmFormInstanceSettings.requireAuthentication() &&
			!layout.isPrivateLayout() && !themeDisplay.isSignedIn()) {

			return true;
		}

		return false;
	}

	public boolean isShowConfigurationIcon() throws PortalException {
		if (_showConfigurationIcon != null) {
			return _showConfigurationIcon;
		}

		if (isPreview() || (isSharedURL() && isFormShared())) {
			_showConfigurationIcon = false;

			return _showConfigurationIcon;
		}

		ThemeDisplay themeDisplay = getThemeDisplay();

		_showConfigurationIcon = PortletPermissionUtil.contains(
			themeDisplay.getPermissionChecker(), themeDisplay.getLayout(),
			getPortletId(), ActionKeys.CONFIGURATION);

		return _showConfigurationIcon;
	}

	public boolean isShowSubmitButton() {
		return !ParamUtil.getBoolean(_renderRequest, "preview");
	}

	public boolean isShowSuccessPage() throws PortalException {
		if (!SessionErrors.isEmpty(_renderRequest) ||
			SessionMessages.isEmpty(_renderRequest)) {

			return false;
		}

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			getDDMFormSuccessPageSettings();

		return ddmFormSuccessPageSettings.isEnabled();
	}

	protected String createCaptchaResourceURL() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String captchaResourceURL =
			themeDisplay.getPathMain() + "/portal/captcha/get_image";

		String portletId = PortalUtil.getPortletId(_renderRequest);

		if (Validator.isNotNull(portletId)) {
			captchaResourceURL = captchaResourceURL.concat(
				"?portletId=" + portletId);
		}

		return captchaResourceURL;
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		DDMForm ddmForm) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		String redirectURL = ParamUtil.getString(_renderRequest, "redirect");

		if (Validator.isNotNull(redirectURL)) {
			ddmFormRenderingContext.setCancelLabel(
				LanguageUtil.get(ddmForm.getDefaultLocale(), "cancel"));
		}

		ddmFormRenderingContext.setContainerId(_containerId);
		ddmFormRenderingContext.setDDMFormValues(
			_ddmFormValuesFactory.create(_renderRequest, ddmForm));

		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_renderRequest);

		ddmFormRenderingContext.setHttpServletRequest(httpServletRequest);

		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(_renderResponse));
		ddmFormRenderingContext.setLocale(
			getLocale(httpServletRequest, ddmForm));
		ddmFormRenderingContext.setPortletNamespace(
			_renderResponse.getNamespace());

		if (Validator.isNotNull(redirectURL)) {
			ddmFormRenderingContext.setRedirectURL(redirectURL);
		}

		ddmFormRenderingContext.setSharedURL(isSharedURL());

		if (Validator.isNull(redirectURL)) {
			ddmFormRenderingContext.setShowCancelButton(false);
		}

		ddmFormRenderingContext.setViewMode(true);

		return ddmFormRenderingContext;
	}

	protected DDMFormLayoutRow createFullColumnDDMFormLayoutRow(
		String ddmFormFieldName) {

		DDMFormLayoutRow ddmFormLayoutRow = new DDMFormLayoutRow();

		DDMFormLayoutColumn ddmFormLayoutColumn = new DDMFormLayoutColumn(
			DDMFormLayoutColumn.FULL, ddmFormFieldName);

		ddmFormLayoutRow.addDDMFormLayoutColumn(ddmFormLayoutColumn);

		return ddmFormLayoutRow;
	}

	protected DDMForm getDDMForm() throws PortalException {
		DDMFormInstance ddmFormInstance = getFormInstance();

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		return ddmStructure.getDDMForm();
	}

	protected DDMForm getDDMForm(
			DDMFormInstance ddmFormInstance, boolean requireCaptcha)
		throws PortalException {

		DDMForm ddmForm = null;

		if (isPreview()) {
			DDMStructure ddmStructure = ddmFormInstance.getStructure();

			DDMStructureVersion latestStructureVersion =
				ddmStructure.getLatestStructureVersion();

			ddmForm = latestStructureVersion.getDDMForm();
		}
		else {
			DDMFormInstanceVersion latestFormInstanceVersion =
				_ddmFormInstanceVersionLocalService.
					getLatestFormInstanceVersion(
						ddmFormInstance.getFormInstanceId(),
						WorkflowConstants.STATUS_APPROVED);

			DDMStructureVersion structureVersion =
				latestFormInstanceVersion.getStructureVersion();

			ddmForm = structureVersion.getDDMForm();
		}

		if (requireCaptcha) {
			DDMFormField captchaDDMFormField = new DDMFormField(
				_DDM_FORM_FIELD_NAME_CAPTCHA, "captcha");

			captchaDDMFormField.setDataType("string");
			captchaDDMFormField.setProperty("url", createCaptchaResourceURL());

			ddmForm.addDDMFormField(captchaDDMFormField);
		}

		return ddmForm;
	}

	protected DDMFormLayout getDDMFormLayout(
			DDMFormInstance ddmFormInstance, boolean requireCaptcha)
		throws PortalException {

		DDMFormLayout ddmFormLayout = null;

		if (isPreview()) {
			DDMStructure ddmStructure = ddmFormInstance.getStructure();

			DDMStructureVersion latestStructureVersion =
				ddmStructure.getLatestStructureVersion();

			ddmFormLayout = latestStructureVersion.getDDMFormLayout();
		}
		else {
			DDMFormInstanceVersion latestFormInstanceVersion =
				_ddmFormInstanceVersionLocalService.
					getLatestFormInstanceVersion(
						ddmFormInstance.getFormInstanceId(),
						WorkflowConstants.STATUS_APPROVED);

			DDMStructureVersion structureVersion =
				latestFormInstanceVersion.getStructureVersion();

			ddmFormLayout = structureVersion.getDDMFormLayout();
		}

		if (requireCaptcha) {
			DDMFormLayoutPage lastDDMFormLayoutPage = getLastDDMFormLayoutPage(
				ddmFormLayout);

			DDMFormLayoutRow ddmFormLayoutRow =
				createFullColumnDDMFormLayoutRow(_DDM_FORM_FIELD_NAME_CAPTCHA);

			lastDDMFormLayoutPage.addDDMFormLayoutRow(ddmFormLayoutRow);
		}

		return ddmFormLayout;
	}

	protected long getFormInstanceIdFromSession() {
		PortletSession portletSession = _renderRequest.getPortletSession();

		return GetterUtil.getLong(
			portletSession.getAttribute("ddmFormInstanceId"));
	}

	protected String getFormInstanceVersion() {
		DDMFormInstance ddmFormInstance = getFormInstance();

		if (ddmFormInstance == null) {
			return "1.0";
		}

		return ddmFormInstance.getVersion();
	}

	protected DDMFormLayoutPage getLastDDMFormLayoutPage(
		DDMFormLayout ddmFormLayout) {

		List<DDMFormLayoutPage> ddmFormLayoutPages =
			ddmFormLayout.getDDMFormLayoutPages();

		return ddmFormLayoutPages.get(ddmFormLayoutPages.size() - 1);
	}

	protected Locale getLocale(
		HttpServletRequest httpServletRequest, DDMForm ddmForm) {

		String defaultLanguageId = ParamUtil.getString(
			httpServletRequest, "defaultLanguageId");

		if (Validator.isNotNull(defaultLanguageId)) {
			return LocaleUtil.fromLanguageId(defaultLanguageId);
		}

		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		Locale locale = LocaleUtil.fromLanguageId(
			LanguageUtil.getLanguageId(httpServletRequest));

		if (availableLocales.contains(locale)) {
			return locale;
		}

		return ddmForm.getDefaultLocale();
	}

	protected String getPortletId() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getId();
	}

	protected String getPortletResource() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		return portletDisplay.getPortletResource();
	}

	protected ResourceBundle getResourceBundle(Locale locale) {
		ResourceBundle portalResourceBundle = _portal.getResourceBundle(locale);

		ResourceBundle moduleResourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return new AggregateResourceBundle(
			moduleResourceBundle, portalResourceBundle);
	}

	protected ThemeDisplay getThemeDisplay() {
		return (ThemeDisplay)_renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
	}

	protected User getUser() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getUser();
	}

	protected long getUserId() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getUserId();
	}

	protected boolean hasWorkflowEnabled(
		DDMFormInstance ddmFormInstance, ThemeDisplay themeDisplay) {

		return _workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
			themeDisplay.getCompanyId(), ddmFormInstance.getGroupId(),
			DDMFormInstance.class.getName(),
			ddmFormInstance.getFormInstanceId());
	}

	protected boolean isCaptchaRequired(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMFormInstanceSettings ddmFormInstanceSettings =
			ddmFormInstance.getSettingsModel();

		return ddmFormInstanceSettings.requireCaptcha();
	}

	protected boolean isDefaultUser() {
		User user = getUser();

		return user.isDefaultUser();
	}

	protected boolean isFormPublished() throws PortalException {
		DDMFormInstance ddmFormInstance = getFormInstance();

		if (ddmFormInstance == null) {
			return false;
		}

		DDMFormInstanceSettings ddmFormInstanceSettings =
			ddmFormInstance.getSettingsModel();

		return ddmFormInstanceSettings.published();
	}

	protected boolean isSharedURL() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		String urlCurrent = themeDisplay.getURLCurrent();

		return urlCurrent.contains("/shared");
	}

	private static final String _DDM_FORM_FIELD_NAME_CAPTCHA = "_CAPTCHA_";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormDisplayContext.class);

	private Boolean _autosaveEnabled;
	private final String _containerId;
	private final DDMFormFieldTypeServicesTracker
		_ddmFormFieldTypeServicesTracker;
	private DDMFormInstance _ddmFormInstance;
	private long _ddmFormInstanceId;
	private final DDMFormInstanceLocalService _ddmFormInstanceLocalService;
	private DDMFormInstanceRecord _ddmFormInstanceRecord;
	private long _ddmFormInstanceRecordId;
	private final DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private final DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;
	private final DDMFormInstanceService _ddmFormInstanceService;
	private final DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private final DDMFormWebConfiguration _ddmFormWebConfiguration;
	private final DDMStorageAdapterTracker _ddmStorageAdapterTracker;
	private final GroupLocalService _groupLocalService;
	private Boolean _hasAddFormInstanceRecordPermission;
	private Boolean _hasViewPermission;
	private final JSONFactory _jsonFactory;
	private final Portal _portal;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Boolean _showConfigurationIcon;
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}
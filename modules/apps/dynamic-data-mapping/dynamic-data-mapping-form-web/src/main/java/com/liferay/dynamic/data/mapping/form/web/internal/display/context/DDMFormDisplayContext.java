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
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.security.permission.resource.DDMFormInstancePermission;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
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
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermissionUtil;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.kernel.util.SessionParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marcellus Tavares
 */
public class DDMFormDisplayContext {

	public DDMFormDisplayContext(
			RenderRequest renderRequest, RenderResponse renderResponse,
			DDMFormInstanceLocalService ddmFormInstanceLocalService,
			DDMFormInstanceRecordVersionLocalService
				ddmFormInstanceRecordVersionLocalService,
			DDMFormInstanceService ddmFormInstanceService,
			DDMFormInstanceVersionLocalService
				ddmFormInstanceVersionLocalService,
			DDMFormRenderer ddmFormRenderer,
			DDMFormValuesFactory ddmFormValuesFactory,
			DDMFormValuesMerger ddmFormValuesMerger,
			GroupLocalService groupLocalService,
			WorkflowDefinitionLinkLocalService
				workflowDefinitionLinkLocalService)
		throws PortalException {

		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_ddmFormInstanceLocalService = ddmFormInstanceLocalService;
		_ddmFormInstanceRecordVersionLocalService =
			ddmFormInstanceRecordVersionLocalService;
		_ddmFormInstanceService = ddmFormInstanceService;
		_ddmFormInstanceVersionLocalService =
			ddmFormInstanceVersionLocalService;
		_ddmFormRenderer = ddmFormRenderer;
		_ddmFormValuesFactory = ddmFormValuesFactory;
		_ddmFormValuesMerger = ddmFormValuesMerger;
		_groupLocalService = groupLocalService;
		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;

		_containerId = StringUtil.randomString();

		if (Validator.isNotNull(getPortletResource())) {
			return;
		}

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceLocalService.fetchDDMFormInstance(
				getFormInstanceId());

		if ((ddmFormInstance == null) || !hasViewPermission()) {
			renderRequest.setAttribute(
				WebKeys.PORTLET_CONFIGURATOR_VISIBILITY, Boolean.TRUE);
		}
	}

	public String[] getAvailableLanguageIds() throws PortalException {
		ThemeDisplay themeDisplay = getThemeDisplay();

		Set<Locale> siteAvailablesLocales = LanguageUtil.getAvailableLocales(
			themeDisplay.getSiteGroupId());

		DDMForm ddmForm = getDDMForm();

		Set<Locale> availableLocales = ddmForm.getAvailableLocales();

		Stream<Locale> localeStreams = availableLocales.stream();

		return localeStreams.filter(
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

		boolean requireCaptcha = isCaptchaRequired(ddmFormInstance);

		DDMForm ddmForm = getDDMForm(ddmFormInstance, requireCaptcha);

		DDMFormLayout ddmFormLayout = getDDMFormLayout(
			ddmFormInstance, requireCaptcha);

		DDMFormRenderingContext ddmFormRenderingContext =
			createDDMFormRenderingContext(ddmForm);

		ddmFormRenderingContext.setGroupId(ddmFormInstance.getGroupId());

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			_ddmFormInstanceRecordVersionLocalService.
				fetchLatestFormInstanceRecordVersion(
					getUserId(), getFormInstanceId(), getFormInstanceVersion(),
					WorkflowConstants.STATUS_DRAFT);

		if (ddmFormInstanceRecordVersion != null) {
			DDMFormValues mergedDDMFormValues = _ddmFormValuesMerger.merge(
				ddmFormInstanceRecordVersion.getDDMFormValues(),
				ddmFormRenderingContext.getDDMFormValues());

			ddmFormRenderingContext.setDDMFormValues(mergedDDMFormValues);
		}

		boolean showSubmitButton = isShowSubmitButton();

		ddmFormRenderingContext.setShowSubmitButton(showSubmitButton);

		String submitLabel = getSubmitLabel(
			ddmFormInstance, ddmFormRenderingContext.getLocale());

		ddmFormRenderingContext.setSubmitLabel(submitLabel);

		if (!hasAddFormInstanceRecordPermission()) {
			ddmFormRenderingContext.setReadOnly(true);
		}

		return _ddmFormRenderer.render(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	public DDMFormSuccessPageSettings getDDMFormSuccessPageSettings()
		throws PortalException {

		DDMForm ddmForm = getDDMForm();

		return ddmForm.getDDMFormSuccessPageSettings();
	}

	public String getDefaultLanguageId() throws PortalException {
		DDMForm ddmForm = getDDMForm();

		return ParamUtil.getString(
			_renderRequest, "languageId",
			LanguageUtil.getLanguageId(ddmForm.getDefaultLocale()));
	}

	public DDMFormInstance getFormInstance() {
		if (_ddmFormInstance != null) {
			return _ddmFormInstance;
		}

		try {
			_ddmFormInstance = _ddmFormInstanceService.fetchFormInstance(
				getFormInstanceId());
		}
		catch (PortalException pe) {

			// LPS-52675

			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
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

	public String getRedirectURL() throws PortalException {
		DDMFormInstance ddmFormInstance = getFormInstance();

		if (ddmFormInstance == null) {
			return null;
		}

		DDMFormInstanceSettings ddmFormInstanceSettings =
			ddmFormInstance.getSettingsModel();

		return ddmFormInstanceSettings.redirectURL();
	}

	public boolean hasAddFormInstanceRecordPermission() throws PortalException {
		if (_hasAddFormInstanceRecordPermission != null) {
			return _hasAddFormInstanceRecordPermission;
		}

		_hasAddFormInstanceRecordPermission = true;

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

			_autosaveEnabled = formInstanceSettings.autosaveEnabled();
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

			if ((group != null) && group.isStagingGroup()) {
				return false;
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

	public boolean isShowSuccessPage() throws PortalException {
		if (SessionMessages.isEmpty(_renderRequest)) {
			return false;
		}

		DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
			getDDMFormSuccessPageSettings();

		return ddmFormSuccessPageSettings.isEnabled();
	}

	protected String createCaptchaResourceURL() {
		ResourceURL resourceURL = _renderResponse.createResourceURL();

		resourceURL.setResourceID("captcha");

		return resourceURL.toString();
	}

	protected DDMFormRenderingContext createDDMFormRenderingContext(
		DDMForm ddmForm) {

		DDMFormRenderingContext ddmFormRenderingContext =
			new DDMFormRenderingContext();

		ddmFormRenderingContext.setContainerId(_containerId);
		ddmFormRenderingContext.setDDMFormValues(
			_ddmFormValuesFactory.create(_renderRequest, ddmForm));

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			_renderRequest);

		ddmFormRenderingContext.setHttpServletRequest(request);

		ddmFormRenderingContext.setHttpServletResponse(
			PortalUtil.getHttpServletResponse(_renderResponse));
		ddmFormRenderingContext.setLocale(getLocale(request, ddmForm));
		ddmFormRenderingContext.setPortletNamespace(
			_renderResponse.getNamespace());
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

			ddmForm = ddmStructure.getDDMForm();
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

			ddmFormLayout = ddmStructure.getDDMFormLayout();
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

	protected Locale getLocale(HttpServletRequest request, DDMForm ddmForm) {
		Set<Locale> availableLocales = ddmForm.getAvailableLocales();
		String languageId = LanguageUtil.getLanguageId(request);

		Locale locale = LocaleUtil.fromLanguageId(languageId);

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

	protected String getSubmitLabel(
		DDMFormInstance ddmFormInstance, Locale locale) {

		ThemeDisplay themeDisplay = getThemeDisplay();

		boolean workflowEnabled = hasWorkflowEnabled(
			ddmFormInstance, themeDisplay);

		if (workflowEnabled) {
			return LanguageUtil.get(locale, "submit-for-publication");
		}

		return LanguageUtil.get(locale, "submit");
	}

	protected ThemeDisplay getThemeDisplay() {
		ThemeDisplay themeDisplay = (ThemeDisplay)_renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return themeDisplay;
	}

	protected User getUser() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getUser();
	}

	protected long getUserId() {
		ThemeDisplay themeDisplay = getThemeDisplay();

		return themeDisplay.getUserId();
	}

	protected boolean hasViewPermission() throws PortalException {
		if (_hasViewPermission != null) {
			return _hasViewPermission;
		}

		_hasViewPermission = true;

		DDMFormInstance ddmFormInstance = getFormInstance();

		if (ddmFormInstance != null) {
			ThemeDisplay themeDisplay = getThemeDisplay();

			_hasViewPermission = DDMFormInstancePermission.contains(
				themeDisplay.getPermissionChecker(), ddmFormInstance,
				ActionKeys.VIEW);
		}

		return _hasViewPermission;
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

	protected boolean isShowSubmitButton() {
		boolean preview = ParamUtil.getBoolean(_renderRequest, "preview");

		if (preview) {
			return false;
		}

		return true;
	}

	private static final String _DDM_FORM_FIELD_NAME_CAPTCHA = "_CAPTCHA_";

	private static final Log _log = LogFactoryUtil.getLog(
		DDMFormDisplayContext.class);

	private Boolean _autosaveEnabled;
	private final String _containerId;
	private DDMFormInstance _ddmFormInstance;
	private long _ddmFormInstanceId;
	private final DDMFormInstanceLocalService _ddmFormInstanceLocalService;
	private final DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;
	private final DDMFormInstanceService _ddmFormInstanceService;
	private final DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;
	private final DDMFormRenderer _ddmFormRenderer;
	private final DDMFormValuesFactory _ddmFormValuesFactory;
	private final DDMFormValuesMerger _ddmFormValuesMerger;
	private final GroupLocalService _groupLocalService;
	private Boolean _hasAddFormInstanceRecordPermission;
	private Boolean _hasViewPermission;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private Boolean _showConfigurationIcon;
	private final WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}
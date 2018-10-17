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

package com.liferay.dynamic.data.mapping.form.taglib.servlet.taglib.util;

import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextFactory;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextRequest;
import com.liferay.dynamic.data.mapping.form.builder.context.DDMFormBuilderContextResponse;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsRequest;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsResponse;
import com.liferay.dynamic.data.mapping.form.builder.settings.DDMFormBuilderSettingsRetriever;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderer;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingContext;
import com.liferay.dynamic.data.mapping.form.renderer.DDMFormRenderingException;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureVersion;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureVersionLocalService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesMerger;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONSerializer;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Locale;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = {})
public class DDMFormTaglibUtil {

	public static DDMFormValues createDDMFormValues(
		HttpServletRequest request, DDMForm ddmForm) {

		return _ddmFormValuesFactory.create(request, ddmForm);
	}

	public static DDMForm getDDMForm(
		long ddmStructureId, long ddmStructureVersionId) {

		if (ddmStructureVersionId > 0) {
			DDMStructureVersion ddmStructureVersion =
				_ddmStructureVersionLocalService.fetchDDMStructureVersion(
					ddmStructureVersionId);

			if (ddmStructureVersion != null) {
				return ddmStructureVersion.getDDMForm();
			}
		}

		if (ddmStructureId > 0) {
			DDMStructure ddmStructure =
				_ddmStructureLocalService.fetchDDMStructure(ddmStructureId);

			if (ddmStructure != null) {
				return ddmStructure.getDDMForm();
			}
		}

		return new DDMForm();
	}

	public static DDMFormBuilderSettingsResponse getDDMFormBuilderSettings(
		DDMFormBuilderSettingsRequest ddmFormBuilderSettingsRequest) {

		DDMFormBuilderSettingsRetriever ddmFormBuilderSettingsRetriever =
			getDDMFormBuilderSettingsRetriever();

		return ddmFormBuilderSettingsRetriever.getSettings(
			ddmFormBuilderSettingsRequest);
	}

	public static DDMFormInstance getDDMFormInstance(long ddmFormInstanceId) {
		return _ddmFormInstanceLocalService.fetchFormInstance(
			ddmFormInstanceId);
	}

	public static DDMFormInstanceRecord getDDMFormInstanceRecord(
		long ddmFormInstanceRecordId) {

		return _ddmFormInstanceRecordLocalService.fetchDDMFormInstanceRecord(
			ddmFormInstanceRecordId);
	}

	public static DDMFormInstanceRecordVersion getDDMFormInstanceRecordVersion(
		long ddmFormInstanceRecordVersionId) {

		return _ddmFormInstanceRecordVersionLocalService.
			fetchDDMFormInstanceRecordVersion(ddmFormInstanceRecordVersionId);
	}

	public static DDMFormInstanceVersion getDDMFormInstanceVersion(
		long ddmFormInstanceVersionId) {

		return _ddmFormInstanceVersionLocalService.fetchDDMFormInstanceVersion(
			ddmFormInstanceVersionId);
	}

	public static String getFormBuilderContext(
		long ddmStructureId, long ddmStructureVersionId,
		HttpServletRequest request) {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String serializedFormBuilderContext = ParamUtil.getString(
			request, "serializedFormBuilderContext");

		if (Validator.isNotNull(serializedFormBuilderContext)) {
			return serializedFormBuilderContext;
		}

		JSONSerializer jsonSerializer = _jsonFactory.createJSONSerializer();

		Optional<DDMStructure> ddmStructureOptional = Optional.ofNullable(
			_ddmStructureLocalService.fetchDDMStructure(ddmStructureId));

		DDMStructureVersion ddmStructureVersion =
			_ddmStructureVersionLocalService.fetchDDMStructureVersion(
				ddmStructureVersionId);

		Locale locale = themeDisplay.getSiteDefaultLocale();

		if (ddmStructureOptional.isPresent() || (ddmStructureVersion != null)) {
			DDMForm ddmForm = getDDMForm(ddmStructureId, ddmStructureVersionId);

			locale = ddmForm.getDefaultLocale();
		}

		DDMFormBuilderContextRequest ddmFormBuilderContextRequest =
			DDMFormBuilderContextRequest.with(
				ddmStructureOptional, themeDisplay.getRequest(),
				themeDisplay.getResponse(), locale, true);

		ddmFormBuilderContextRequest.addProperty(
			"ddmStructureVersion", ddmStructureVersion);

		DDMFormBuilderContextResponse ddmFormBuilderContextResponse =
			_ddmFormBuilderContextFactory.create(ddmFormBuilderContextRequest);

		return jsonSerializer.serializeDeep(
			ddmFormBuilderContextResponse.getContext());
	}

	public static Group getGroup(long groupId) {
		return _groupLocalService.fetchGroup(groupId);
	}

	public static DDMFormInstanceVersion getLatestDDMFormInstanceVersion(
			long ddmFormInstanceId, int status)
		throws PortalException {

		return _ddmFormInstanceVersionLocalService.getLatestFormInstanceVersion(
			ddmFormInstanceId, status);
	}

	public static boolean hasWorkflowDefinitionLink(
		long companyId, long groupId, String name, long ddmFormInstanceId) {

		return _workflowDefinitionLinkLocalService.hasWorkflowDefinitionLink(
			companyId, groupId, name, ddmFormInstanceId);
	}

	public static DDMFormValues mergeDDMFormValues(
		DDMFormValues newDDMFormValues, DDMFormValues existingDDMFormValues) {

		return _ddmFormValuesMerger.merge(
			newDDMFormValues, existingDDMFormValues);
	}

	public static String renderForm(
			DDMForm ddmForm, DDMFormLayout ddmFormLayout,
			DDMFormRenderingContext ddmFormRenderingContext)
		throws DDMFormRenderingException {

		return _ddmFormRenderer.render(
			ddmForm, ddmFormLayout, ddmFormRenderingContext);
	}

	protected static DDMFormBuilderSettingsRetriever
		getDDMFormBuilderSettingsRetriever() {

		if (_ddmFormBuilderSettingsRetriever == null) {
			throw new IllegalStateException();
		}

		return _ddmFormBuilderSettingsRetriever;
	}

	@Reference(unbind = "-")
	protected void setDDMFormBuilderContextFactory(
		DDMFormBuilderContextFactory ddmFormBuilderContextFactory) {

		_ddmFormBuilderContextFactory = ddmFormBuilderContextFactory;
	}

	@Reference(unbind = "-")
	protected void setDDMFormBuilderSettingsRetriever(
		DDMFormBuilderSettingsRetriever ddmFormBuilderSettingsRetriever) {

		_ddmFormBuilderSettingsRetriever = ddmFormBuilderSettingsRetriever;
	}

	@Reference(unbind = "-")
	protected void setDDMFormInstanceLocalService(
		DDMFormInstanceLocalService ddmFormInstanceLocalService) {

		_ddmFormInstanceLocalService = ddmFormInstanceLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormInstanceRecordLocalService(
		DDMFormInstanceRecordLocalService ddmFormInstanceRecordLocalService) {

		_ddmFormInstanceRecordLocalService = ddmFormInstanceRecordLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormInstanceRecordVersionLocalService(
		DDMFormInstanceRecordVersionLocalService
			ddmFormInstanceRecordVersionLocalService) {

		_ddmFormInstanceRecordVersionLocalService =
			ddmFormInstanceRecordVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormInstanceVersionLocalService(
		DDMFormInstanceVersionLocalService ddmFormInstanceVersionLocalService) {

		_ddmFormInstanceVersionLocalService =
			ddmFormInstanceVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormRenderer(DDMFormRenderer ddmFormRenderer) {
		_ddmFormRenderer = ddmFormRenderer;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesFactory(
		DDMFormValuesFactory ddmFormValuesFactory) {

		_ddmFormValuesFactory = ddmFormValuesFactory;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesMerger(
		DDMFormValuesMerger ddmFormValuesMerger) {

		_ddmFormValuesMerger = ddmFormValuesMerger;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setDDMStructureVersionLocalService(
		DDMStructureVersionLocalService ddmStructureVersionLocalService) {

		_ddmStructureVersionLocalService = ddmStructureVersionLocalService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setJSONFactory(JSONFactory jsonFactory) {
		_jsonFactory = jsonFactory;
	}

	@Reference(unbind = "-")
	protected void setWorkflowDefinitionLinkLocalService(
		WorkflowDefinitionLinkLocalService workflowDefinitionLinkLocalService) {

		_workflowDefinitionLinkLocalService =
			workflowDefinitionLinkLocalService;
	}

	private static DDMFormBuilderContextFactory _ddmFormBuilderContextFactory;
	private static DDMFormBuilderSettingsRetriever
		_ddmFormBuilderSettingsRetriever;
	private static DDMFormInstanceLocalService _ddmFormInstanceLocalService;
	private static DDMFormInstanceRecordLocalService
		_ddmFormInstanceRecordLocalService;
	private static DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;
	private static DDMFormInstanceVersionLocalService
		_ddmFormInstanceVersionLocalService;
	private static DDMFormRenderer _ddmFormRenderer;
	private static DDMFormValuesFactory _ddmFormValuesFactory;
	private static DDMFormValuesMerger _ddmFormValuesMerger;
	private static DDMStructureLocalService _ddmStructureLocalService;
	private static DDMStructureVersionLocalService
		_ddmStructureVersionLocalService;
	private static GroupLocalService _groupLocalService;
	private static JSONFactory _jsonFactory;
	private static WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

}
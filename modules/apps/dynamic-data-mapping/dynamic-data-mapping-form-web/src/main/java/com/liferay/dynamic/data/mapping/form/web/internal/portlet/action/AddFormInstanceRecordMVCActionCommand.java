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

package com.liferay.dynamic.data.mapping.form.web.internal.portlet.action;

import com.liferay.captcha.util.CaptchaUtil;
import com.liferay.dynamic.data.mapping.constants.DDMPortletKeys;
import com.liferay.dynamic.data.mapping.exception.FormInstanceNotPublishedException;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.constants.DDMFormWebKeys;
import com.liferay.dynamic.data.mapping.form.web.internal.instance.lifecycle.AddDefaultSharedFormLayoutPortalInstanceLifecycleListener;
import com.liferay.dynamic.data.mapping.form.web.internal.portlet.action.helper.AddFormInstanceRecordMVCCommandHelper;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletSession;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM,
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM_ADMIN,
		"mvc.command.name=/dynamic_data_mapping_form/add_form_instance_record"
	},
	service = MVCActionCommand.class
)
public class AddFormInstanceRecordMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		PortletSession portletSession = actionRequest.getPortletSession();

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		if (groupId == 0) {
			groupId = GetterUtil.getLong(
				portletSession.getAttribute(DDMFormWebKeys.GROUP_ID));
		}

		long formInstanceId = ParamUtil.getLong(
			actionRequest, "formInstanceId");

		if (formInstanceId == 0) {
			formInstanceId = GetterUtil.getLong(
				portletSession.getAttribute(
					DDMFormWebKeys.DYNAMIC_DATA_MAPPING_FORM_INSTANCE_ID));
		}

		DDMFormInstance ddmFormInstance =
			_ddmFormInstanceService.getFormInstance(formInstanceId);

		_addFormInstanceMVCCommandHelper.validateExpirationStatus(
			ddmFormInstance, actionRequest);
		_addFormInstanceMVCCommandHelper.validateSubmissionLimitStatus(
			ddmFormInstance, _ddmFormInstanceRecordVersionLocalService,
			actionRequest);

		_validatePublishStatus(actionRequest, ddmFormInstance);

		_validateCaptcha(actionRequest, ddmFormInstance);

		DDMForm ddmForm = getDDMForm(ddmFormInstance);

		DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
			actionRequest, ddmForm);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_addFormInstanceMVCCommandHelper.updateNonevaluableDDMFormFields(
			actionRequest, ddmForm, ddmFormValues,
			LocaleUtil.fromLanguageId(_language.getLanguageId(actionRequest)));

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMFormInstanceRecord.class.getName(), actionRequest);

		serviceContext.setRequest(_portal.getHttpServletRequest(actionRequest));

		_updateFormInstanceRecord(
			actionRequest, ddmFormInstance, ddmFormValues, groupId,
			serviceContext, themeDisplay.getUserId());

		if (!SessionErrors.isEmpty(actionRequest)) {
			return;
		}

		if (SessionMessages.contains(
				actionRequest,
				_portal.getPortletId(actionRequest) +
					SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE)) {

			SessionMessages.clear(actionRequest);
		}

		SessionMessages.add(actionRequest, "formInstanceRecordAdded");

		DDMFormInstanceSettings ddmFormInstanceSettings =
			ddmFormInstance.getSettingsModel();

		String ddmFormInstanceSettingsRedirectURL =
			ddmFormInstanceSettings.redirectURL();

		if (Validator.isNotNull(ddmFormInstanceSettingsRedirectURL)) {
			hideDefaultSuccessMessage(actionRequest);
		}

		portletSession.setAttribute(
			DDMFormWebKeys.DYNAMIC_DATA_MAPPING_FORM_INSTANCE_ID,
			formInstanceId);
		portletSession.setAttribute(DDMFormWebKeys.GROUP_ID, groupId);

		sendRedirect(
			actionRequest, actionResponse,
			ParamUtil.getString(
				actionRequest, "redirect", ddmFormInstanceSettingsRedirectURL));
	}

	protected DDMForm getDDMForm(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		return ddmStructure.getDDMForm();
	}

	private void _updateFormInstanceRecord(
			ActionRequest actionRequest, DDMFormInstance ddmFormInstance,
			DDMFormValues ddmFormValues, long groupId,
			ServiceContext serviceContext, long userId)
		throws Exception {

		long ddmFormInstanceRecordId = ParamUtil.getLong(
			actionRequest, "formInstanceRecordId");

		if (ddmFormInstanceRecordId != 0) {
			_ddmFormInstanceRecordService.updateFormInstanceRecord(
				ddmFormInstanceRecordId, false, ddmFormValues, serviceContext);
		}
		else {
			DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
				_ddmFormInstanceRecordVersionLocalService.
					fetchLatestFormInstanceRecordVersion(
						userId, ddmFormInstance.getFormInstanceId(),
						ddmFormInstance.getVersion(),
						WorkflowConstants.STATUS_DRAFT);

			if (ddmFormInstanceRecordVersion == null) {
				_ddmFormInstanceRecordService.addFormInstanceRecord(
					groupId, ddmFormInstance.getFormInstanceId(), ddmFormValues,
					serviceContext);
			}
			else {
				_ddmFormInstanceRecordService.updateFormInstanceRecord(
					ddmFormInstanceRecordVersion.getFormInstanceRecordId(),
					false, ddmFormValues, serviceContext);
			}
		}
	}

	private void _validateCaptcha(
			ActionRequest actionRequest, DDMFormInstance ddmFormInstance)
		throws Exception {

		DDMFormInstanceSettings formInstanceSettings =
			ddmFormInstance.getSettingsModel();

		if (formInstanceSettings.requireCaptcha()) {
			CaptchaUtil.check(actionRequest);
		}
	}

	private void _validatePublishStatus(
			ActionRequest actionRequest, DDMFormInstance ddmFormInstance)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String currentURL = ParamUtil.getString(actionRequest, "currentURL");

		DDMFormInstanceSettings ddmFormInstanceSettings =
			ddmFormInstance.getSettingsModel();

		if (StringUtil.startsWith(
				currentURL,
				_addDefaultSharedFormLayoutPortalInstanceLifecycleListener.
					getFormLayoutURL(themeDisplay)) &&
			!ddmFormInstanceSettings.published()) {

			throw new FormInstanceNotPublishedException(
				"Form instance " + ddmFormInstance.getFormInstanceId() +
					" is not published");
		}
	}

	@Reference
	private AddDefaultSharedFormLayoutPortalInstanceLifecycleListener
		_addDefaultSharedFormLayoutPortalInstanceLifecycleListener;

	@Reference
	private AddFormInstanceRecordMVCCommandHelper
		_addFormInstanceMVCCommandHelper;

	@Reference
	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	@Reference
	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

	@Reference
	private DDMFormInstanceService _ddmFormInstanceService;

	@Reference
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}
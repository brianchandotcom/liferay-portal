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
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.form.web.internal.constants.DDMFormWebKeys;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecordVersion;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceSettings;
import com.liferay.dynamic.data.mapping.model.DDMFormSuccessPageSettings;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceRecordVersionLocalService;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.captcha.CaptchaException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
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
	immediate = true,
	property = {
		"javax.portlet.name=" + DDMPortletKeys.DYNAMIC_DATA_MAPPING_FORM,
		"mvc.command.name=addFormInstanceRecord"
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

		try {
			validateCaptcha(actionRequest, ddmFormInstance);
		}
		catch (CaptchaException ce) {
			SessionErrors.add(actionRequest, ce.getClass());

			return;
		}

		DDMForm ddmForm = getDDMForm(ddmFormInstance);

		DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
			actionRequest, ddmForm);

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_addFormInstanceMVCCommandHelper.
			updateRequiredFieldsAccordingToVisibility(
				actionRequest, ddmForm, ddmFormValues,
				themeDisplay.getLocale());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDMFormInstanceRecord.class.getName(), actionRequest);

		serviceContext.setRequest(_portal.getHttpServletRequest(actionRequest));

		DDMFormInstanceRecordVersion ddmFormInstanceRecordVersion =
			_ddmFormInstanceRecordVersionLocalService.
				fetchLatestFormInstanceRecordVersion(
					themeDisplay.getUserId(), formInstanceId,
					ddmFormInstance.getVersion(),
					WorkflowConstants.STATUS_DRAFT);

		if (ddmFormInstanceRecordVersion == null) {
			_ddmFormInstanceRecordService.addFormInstanceRecord(
				groupId, formInstanceId, ddmFormValues, serviceContext);
		}
		else {
			_ddmFormInstanceRecordService.updateFormInstanceRecord(
				ddmFormInstanceRecordVersion.getFormInstanceRecordId(), false,
				ddmFormValues, serviceContext);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			DDMFormInstanceSettings formInstanceSettings =
				ddmFormInstance.getSettingsModel();

			String redirectURL = formInstanceSettings.redirectURL();

			if (Validator.isNotNull(redirectURL)) {
				portletSession.setAttribute(
					DDMFormWebKeys.DYNAMIC_DATA_MAPPING_FORM_INSTANCE_ID,
					formInstanceId);
				portletSession.setAttribute(DDMFormWebKeys.GROUP_ID, groupId);

				sendRedirect(actionRequest, actionResponse, redirectURL);
			}
			else {
				DDMFormSuccessPageSettings ddmFormSuccessPageSettings =
					ddmForm.getDDMFormSuccessPageSettings();

				if (ddmFormSuccessPageSettings.isEnabled()) {
					String portletId = _portal.getPortletId(actionRequest);

					SessionMessages.add(
						actionRequest,
						portletId.concat(
							SessionMessages.
								KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE));
				}
			}
		}
	}

	protected DDMForm getDDMForm(DDMFormInstance ddmFormInstance)
		throws PortalException {

		DDMStructure ddmStructure = ddmFormInstance.getStructure();

		return ddmStructure.getDDMForm();
	}

	@Reference(unbind = "-")
	protected void setDDMFormInstanceRecordService(
		DDMFormInstanceRecordService ddmFormInstanceRecordService) {

		_ddmFormInstanceRecordService = ddmFormInstanceRecordService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormInstanceService(
		DDMFormInstanceService ddmFormInstanceService) {

		_ddmFormInstanceService = ddmFormInstanceService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesFactory(
		DDMFormValuesFactory ddmFormValuesFactory) {

		_ddmFormValuesFactory = ddmFormValuesFactory;
	}

	protected void validateCaptcha(
			ActionRequest actionRequest, DDMFormInstance ddmFormInstance)
		throws Exception {

		DDMFormInstanceSettings formInstanceSettings =
			ddmFormInstance.getSettingsModel();

		if (formInstanceSettings.requireCaptcha()) {
			CaptchaUtil.check(actionRequest);
		}
	}

	@Reference
	private AddFormInstanceRecordMVCCommandHelper
		_addFormInstanceMVCCommandHelper;

	private DDMFormInstanceRecordService _ddmFormInstanceRecordService;

	@Reference
	private DDMFormInstanceRecordVersionLocalService
		_ddmFormInstanceRecordVersionLocalService;

	private DDMFormInstanceService _ddmFormInstanceService;
	private DDMFormValuesFactory _ddmFormValuesFactory;

	@Reference
	private Portal _portal;

}
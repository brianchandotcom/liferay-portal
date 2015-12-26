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

package com.liferay.dynamic.data.lists.form.web.portlet.action;

import com.liferay.dynamic.data.lists.form.web.constants.DDLFormPortletKeys;
import com.liferay.dynamic.data.lists.form.web.notification.DDLFormEmailNotificationSender;
import com.liferay.dynamic.data.lists.form.web.util.DDLFormEmailNotificationUtil;
import com.liferay.dynamic.data.lists.model.DDLFormRecord;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetSettings;
import com.liferay.dynamic.data.lists.service.DDLRecordService;
import com.liferay.dynamic.data.lists.service.DDLRecordSetService;
import com.liferay.dynamic.data.mapping.form.values.factory.DDMFormValuesFactory;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.portal.kernel.captcha.CaptchaTextException;
import com.liferay.portal.kernel.captcha.CaptchaUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.util.PortalUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + DDLFormPortletKeys.DYNAMIC_DATA_LISTS_FORM,
		"mvc.command.name=addRecord"
	},
	service = MVCActionCommand.class
)
public class AddRecordMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long recordSetId = ParamUtil.getLong(actionRequest, "recordSetId");

		DDLRecordSet recordSet = _ddlRecordSetService.getRecordSet(recordSetId);

		validateCaptcha(actionRequest, recordSet);

		DDMForm ddmForm = getDDMForm(recordSet);

		DDMFormValues ddmFormValues = _ddmFormValuesFactory.create(
			actionRequest, ddmForm);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DDLRecord.class.getName(), actionRequest);

		serviceContext.setAttribute(
			"assetClassName", DDLFormRecord.class.getName());

		DDLRecord ddlRecord = _ddlRecordService.addRecord(
			groupId, recordSetId, DDLRecordConstants.DISPLAY_INDEX_DEFAULT,
			ddmFormValues, serviceContext);

		if (DDLFormEmailNotificationUtil.isEmailNotificationEnabled(
				recordSet)) {

			_ddlFormEmailNotificationSender.sendEmailNotification(
				actionRequest, ddlRecord);
		}

		DDLRecordSetSettings recordSetSettings = recordSet.getSettingsModel();

		String redirectURL = recordSetSettings.redirectURL();

		if (SessionErrors.isEmpty(actionRequest) &&
			Validator.isNotNull(redirectURL)) {

			String portletId = PortalUtil.getPortletId(actionRequest);

			SessionMessages.add(
				actionRequest, portletId,
				SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_SUCCESS_MESSAGE);

			actionResponse.sendRedirect(redirectURL);
		}
	}

	protected DDMForm getDDMForm(DDLRecordSet recordSet)
		throws PortalException {

		DDMStructure ddmStructure = recordSet.getDDMStructure();

		return ddmStructure.getDDMForm();
	}

	@Reference(unbind = "-")
	protected void setDDLFormEmailNotificationSender(
		DDLFormEmailNotificationSender ddlFormEmailNotificationSender) {

		_ddlFormEmailNotificationSender = ddlFormEmailNotificationSender;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordService(DDLRecordService ddlRecordService) {
		_ddlRecordService = ddlRecordService;
	}

	@Reference(unbind = "-")
	protected void setDDLRecordSetService(
		DDLRecordSetService ddlRecordSetService) {

		_ddlRecordSetService = ddlRecordSetService;
	}

	@Reference(unbind = "-")
	protected void setDDMFormValuesFactory(
		DDMFormValuesFactory ddmFormValuesFactory) {

		_ddmFormValuesFactory = ddmFormValuesFactory;
	}

	protected void validateCaptcha(
			ActionRequest actionRequest, DDLRecordSet recordSet)
		throws Exception {

		DDLRecordSetSettings recordSetSettings = recordSet.getSettingsModel();

		if (recordSetSettings.requireCaptcha()) {
			try {
				CaptchaUtil.check(actionRequest);
			}
			catch (CaptchaTextException cte) {
				SessionErrors.add(
					actionRequest, CaptchaTextException.class.getName());

				throw cte;
			}
		}
	}

	private volatile DDLFormEmailNotificationSender
		_ddlFormEmailNotificationSender;
	private volatile DDLRecordService _ddlRecordService;
	private volatile DDLRecordSetService _ddlRecordSetService;
	private volatile DDMFormValuesFactory _ddmFormValuesFactory;

}
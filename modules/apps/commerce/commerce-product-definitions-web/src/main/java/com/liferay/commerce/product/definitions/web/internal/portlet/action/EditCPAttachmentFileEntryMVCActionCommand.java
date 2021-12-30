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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPAttachmentFileEntryExpirationDateException;
import com.liferay.commerce.product.exception.DuplicateCPAttachmentFileEntryException;
import com.liferay.commerce.product.exception.NoSuchCPAttachmentFileEntryException;
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.util.DDMFormValuesHelper;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/edit_cp_attachment_file_entry"
	},
	service = MVCActionCommand.class
)
public class EditCPAttachmentFileEntryMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateCPAttachmentFileEntry(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCPAttachmentFileEntry(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCPAttachmentFileEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof
						CPAttachmentFileEntryExpirationDateException ||
					 exception instanceof
						 DuplicateCPAttachmentFileEntryException ||
					 exception instanceof NoSuchFileEntryException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw exception;
			}
		}
	}

	private void _deleteCPAttachmentFileEntry(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPAttachmentFileEntryIds = null;

		long cpAttachmentFileEntryId = ParamUtil.getLong(
			actionRequest, "cpAttachmentFileEntryId");

		if (cpAttachmentFileEntryId > 0) {
			deleteCPAttachmentFileEntryIds = new long[] {
				cpAttachmentFileEntryId
			};
		}
		else {
			deleteCPAttachmentFileEntryIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCPAttachmentFileEntryIds"),
				0L);
		}

		for (long deleteCPAttachmentFileEntryId :
				deleteCPAttachmentFileEntryIds) {

			_cpAttachmentFileEntryService.deleteCPAttachmentFileEntry(
				deleteCPAttachmentFileEntryId);
		}
	}

	private void _updateCPAttachmentFileEntry(ActionRequest actionRequest)
		throws Exception {

		long cpAttachmentFileEntryId = ParamUtil.getLong(
			actionRequest, "cpAttachmentFileEntryId");

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");
		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

		String cdnURL = ParamUtil.getString(actionRequest, "cdnURL");

		boolean cdnEnabled = Validator.isNotNull(cdnURL);

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");
		String ddmFormValues = ParamUtil.getString(
			actionRequest, "ddmFormValues");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		int type = ParamUtil.getInteger(actionRequest, "type");

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinitionId);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPAttachmentFileEntry.class.getName(), actionRequest);

		serviceContext.setScopeGroupId(cpDefinition.getGroupId());

		if (cpAttachmentFileEntryId > 0) {
			_cpAttachmentFileEntryService.updateCPAttachmentFileEntry(
				cpAttachmentFileEntryId, fileEntryId, cdnEnabled, cdnURL,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, titleMap, ddmFormValues,
				priority, type, serviceContext);
		}
		else {
			_cpAttachmentFileEntryService.addCPAttachmentFileEntry(
				serviceContext.getScopeGroupId(),
				_portal.getClassNameId(CPDefinition.class), cpDefinitionId,
				fileEntryId, cdnEnabled, cdnURL, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, titleMap, ddmFormValues, priority, type,
				serviceContext);
		}
	}

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private DDMFormValuesHelper _ddmFormValuesHelper;

	@Reference
	private Portal _portal;

}
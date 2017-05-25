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
import com.liferay.commerce.product.model.CPAttachmentFileEntry;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPAttachmentFileEntryService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=editCPAttachmentFileEntry"
	},
	service = MVCActionCommand.class
)
public class EditCPAttachmentFileEntryMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCPAttachmentFileEntry(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPAttachmentFileEntryIds = null;

		long cpAttachmentFileEntryId = ParamUtil.getLong(
			actionRequest, "cpAttachmentFileEntryId");

		if (cpAttachmentFileEntryId > 0) {
			deleteCPAttachmentFileEntryIds =
				new long[] {cpAttachmentFileEntryId};
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

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
			updateCPAttachmentFileEntry(actionRequest);
		}
		else if (cmd.equals(Constants.DELETE)) {
			deleteCPAttachmentFileEntry(actionRequest);
		}
	}

	protected void updateCPAttachmentFileEntry(ActionRequest actionRequest)
		throws Exception {

		Locale locale = actionRequest.getLocale();

		long cpAttachmentFileEntryId = ParamUtil.getLong(
			actionRequest, "cpAttachmentFileEntryId");

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");
		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");

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
		String json = _cpInstanceHelper.toJSON(
			cpDefinitionId, locale, ddmFormValues);
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		int type = ParamUtil.getInteger(actionRequest, "type");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPAttachmentFileEntry.class.getName(), actionRequest);

		if (cpAttachmentFileEntryId > 0) {
			_cpAttachmentFileEntryService.updateCPAttachmentFileEntry(
				cpAttachmentFileEntryId, fileEntryId, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, titleMap, json, priority, type, serviceContext);
		}
		else {
			long classNameId = _portal.getClassNameId(CPDefinition.class);

			_cpAttachmentFileEntryService.addCPAttachmentFileEntry(
				classNameId, cpDefinitionId, fileEntryId, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, titleMap, json, priority, type, serviceContext);
		}
	}

	@Reference
	private CPAttachmentFileEntryService _cpAttachmentFileEntryService;

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private Portal _portal;

}
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
import com.liferay.commerce.product.exception.NoSuchSkuContributorCPDefinitionOptionRelException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.util.CPInstanceHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Calendar;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=editProductInstance"
	},
	service = MVCActionCommand.class
)
public class EditCPInstanceMVCActionCommand extends BaseMVCActionCommand {

	protected void buildCPInstances(ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPInstance.class.getName(), actionRequest);

		_cpInstanceService.buildCPInstances(cpDefinitionId, serviceContext);
	}

	protected void deleteCPInstances(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPInstanceIds = null;

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		if (cpInstanceId > 0) {
			deleteCPInstanceIds = new long[] {cpInstanceId};
		}
		else {
			deleteCPInstanceIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPInstanceIds"), 0L);
		}

		for (long deleteCPInstanceId : deleteCPInstanceIds) {
			_cpInstanceService.deleteCPInstance(deleteCPInstanceId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCPInstance(actionRequest);
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
				buildCPInstances(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPInstances(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof
					NoSuchSkuContributorCPDefinitionOptionRelException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "viewProductInstances");

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw new PortletException(e);
			}
		}
	}

	protected void updateCPInstance(ActionRequest actionRequest)
		throws Exception {

		Locale locale = actionRequest.getLocale();

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		String sku = ParamUtil.getString(actionRequest, "sku");

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

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPInstance.class.getName(), actionRequest);

		if (cpInstanceId > 0) {
			_cpInstanceService.updateCPInstance(
				cpInstanceId, sku, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				serviceContext);
		}
		else {
			String ddmFormValues = ParamUtil.getString(
				actionRequest, "ddmFormValues");

			String ddmContent = _cpInstanceHelper.toJSON(
				cpDefinitionId, locale, ddmFormValues);

			_cpInstanceService.addCPInstance(
				cpDefinitionId, sku, ddmContent, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
		}
	}

	@Reference
	private CPInstanceHelper _cpInstanceHelper;

	@Reference
	private CPInstanceService _cpInstanceService;

}
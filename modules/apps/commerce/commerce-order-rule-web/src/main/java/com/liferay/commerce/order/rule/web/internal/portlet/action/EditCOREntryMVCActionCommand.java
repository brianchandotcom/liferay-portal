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

package com.liferay.commerce.order.rule.web.internal.portlet.action;

import com.liferay.commerce.order.rule.constants.COREntryPortletKeys;
import com.liferay.commerce.order.rule.exception.NoSuchCOREntryException;
import com.liferay.commerce.order.rule.model.COREntry;
import com.liferay.commerce.order.rule.service.COREntryService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + COREntryPortletKeys.COR_ENTRY,
		"mvc.command.name=/cor_entry/edit_cor_entry"
	},
	service = MVCActionCommand.class
)
public class EditCOREntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				long corEntryId = ParamUtil.getLong(
					actionRequest, "corEntryId");

				boolean active = ParamUtil.getBoolean(actionRequest, "active");
				String description = ParamUtil.getString(
					actionRequest, "description");
				int displayDateMonth = ParamUtil.getInteger(
					actionRequest, "displayDateMonth");
				int displayDateDay = ParamUtil.getInteger(
					actionRequest, "displayDateDay");
				int displayDateYear = ParamUtil.getInteger(
					actionRequest, "displayDateYear");
				int displayDateHour = ParamUtil.getInteger(
					actionRequest, "displayDateHour");

				int displayDateAmPm = ParamUtil.getInteger(
					actionRequest, "displayDateAmPm");

				if (displayDateAmPm == Calendar.PM) {
					displayDateHour += 12;
				}

				int displayDateMinute = ParamUtil.getInteger(
					actionRequest, "displayDateMinute");
				int expirationDateMonth = ParamUtil.getInteger(
					actionRequest, "expirationDateMonth");
				int expirationDateDay = ParamUtil.getInteger(
					actionRequest, "expirationDateDay");
				int expirationDateYear = ParamUtil.getInteger(
					actionRequest, "expirationDateYear");
				int expirationDateHour = ParamUtil.getInteger(
					actionRequest, "expirationDateHour");

				int expirationDateAmPm = ParamUtil.getInteger(
					actionRequest, "expirationDateAmPm");

				if (expirationDateAmPm == Calendar.PM) {
					expirationDateHour += 12;
				}

				int expirationDateMinute = ParamUtil.getInteger(
					actionRequest, "expirationDateMinute");
				boolean neverExpire = ParamUtil.getBoolean(
					actionRequest, "neverExpire");
				String name = ParamUtil.getString(actionRequest, "name");
				int priority = ParamUtil.getInteger(actionRequest, "priority");

				ServiceContext serviceContext =
					ServiceContextFactory.getInstance(
						COREntry.class.getName(), actionRequest);

				if (corEntryId <= 0) {
					_corEntryService.addCOREntry(
						ParamUtil.getString(
							actionRequest, "externalReferenceCode"),
						active, description, displayDateMonth, displayDateDay,
						displayDateYear, displayDateHour, displayDateMinute,
						expirationDateMonth, expirationDateDay,
						expirationDateYear, expirationDateHour,
						expirationDateMinute, neverExpire, name, priority,
						ParamUtil.getString(actionRequest, "type"),
						_getTypeSettings(actionRequest), serviceContext);
				}
				else {
					_corEntryService.updateCOREntry(
						corEntryId, active, description, displayDateMonth,
						displayDateDay, displayDateYear, displayDateHour,
						displayDateMinute, expirationDateMonth,
						expirationDateDay, expirationDateYear,
						expirationDateHour, expirationDateMinute, neverExpire,
						name, priority, _getTypeSettings(actionRequest),
						serviceContext);
				}
			}
		}
		catch (Throwable throwable) {
			if (throwable instanceof NoSuchCOREntryException) {
				SessionErrors.add(
					actionRequest, throwable.getClass(), throwable);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				SessionErrors.add(actionRequest, throwable.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
		}
	}

	private String _getTypeSettings(ActionRequest actionRequest) {
		UnicodeProperties typeSettingsUnicodeProperties =
			PropertiesParamUtil.getProperties(
				actionRequest, "type--settings--");

		return typeSettingsUnicodeProperties.toString();
	}

	@Reference
	private COREntryService _corEntryService;

}
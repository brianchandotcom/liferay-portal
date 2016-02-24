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

package com.liferay.currency.converter.web.portlet.action;

import com.liferay.currency.converter.web.constants.CurrencyConverterPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CurrencyConverterPortletKeys.CURRENCY_CONVERTER,
		"mvc.command.name=edit"
	},
	service = MVCActionCommand.class
)
public class EditPreferencesMVCActionCommand implements MVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		if (!(actionResponse instanceof ActionResponse)) {
			return false;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return false;
		}

		PortletPreferences portletPreferences = actionRequest.getPreferences();

		String[] symbols = StringUtil.split(
			StringUtil.toUpperCase(
				ParamUtil.getString(actionRequest, "symbols")));

		portletPreferences.setValues("symbols", symbols);

		try {
			portletPreferences.store();
		}
		catch (Exception e) {
			SessionErrors.add(
				actionRequest, ValidatorException.class.getName(), e);

			return false;
		}

		SessionMessages.add(
			actionRequest,
			PortalUtil.getPortletId(actionRequest) +
				SessionMessages.KEY_SUFFIX_UPDATED_PREFERENCES);

		actionResponse.setRenderParameter("mvcPath", "/edit.jsp");

		return true;
	}

}
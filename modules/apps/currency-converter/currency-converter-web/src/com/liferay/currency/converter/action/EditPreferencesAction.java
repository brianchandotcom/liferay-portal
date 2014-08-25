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

package com.liferay.currency.converter.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.ActionCommand;

import org.osgi.service.component.annotations.Component;

import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ValidatorException;

/**
 * @author Brian Wing Shun Chan
 * @author Peter Fellwock
 */
@Component(
immediate = true,
property = {
	"action.command.name=currency_converter/edit",
	"javax.portlet.name=com_liferay_currency_converter_portlet_CurrencyConverterPortlet"
	},
	service = ActionCommand.class
)
public class EditPreferencesAction implements ActionCommand {

	@Override
	public boolean processCommand(
			PortletRequest portletRequest, PortletResponse portletResponse) 
		throws PortletException {

		String cmd = ParamUtil.getString(portletRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return false;
		}

		PortletPreferences portletPreferences =	portletRequest.getPreferences();

		String[] symbols = StringUtil.split(
			StringUtil.toUpperCase(
				ParamUtil.getString(portletRequest, "symbols")));

		portletPreferences.setValues("symbols", symbols);

		try {
			portletPreferences.store();
		}
		catch (Exception e) {
			SessionErrors.add(
				portletRequest, ValidatorException.class.getName(), e);

			return false;
		}

		SessionMessages.add(
			portletRequest,
			PortalUtil.getPortletId(portletRequest) +
				SessionMessages.KEY_SUFFIX_UPDATED_PREFERENCES);

		ActionResponse actionResponse = (ActionResponse) portletResponse;

		actionResponse.setRenderParameter("mvcPath", "/edit.jsp");

		return true;
	}

}
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

package com.liferay.commerce.tax.engine.remote.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.tax.engine.remote.internal.constants.RemoteCommerceTaxEngineConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_TAX_METHODS,
		"mvc.command.name=editRemoteCommerceTaxConfiguration"
	},
	service = MVCActionCommand.class
)
public class EditRemoteCommerceTaxConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				_portal.getScopeGroupId(actionRequest),
				RemoteCommerceTaxEngineConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		String taxValueEndpointAuthorizationToken = ParamUtil.getString(
			actionRequest, "settings--taxValueEndpointAuthorizationToken--");

		if (Validator.isNotNull(taxValueEndpointAuthorizationToken)) {
			modifiableSettings.setValue(
				"taxValueEndpointAuthorizationToken",
				taxValueEndpointAuthorizationToken);
		}

		String taxValueEndpointURL = ParamUtil.getString(
			actionRequest, "settings--taxValueEndpointURL--");

		_validate(taxValueEndpointURL);

		modifiableSettings.setValue(
			"taxValueEndpointURL", String.valueOf(taxValueEndpointURL));

		modifiableSettings.store();
	}

	private void _validate(String taxCalculationEndpointURL) throws Exception {
		if (!Validator.isUrl(taxCalculationEndpointURL)) {
			throw new PortletException(
				"Invalid URL " + taxCalculationEndpointURL);
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private SettingsFactory _settingsFactory;

}
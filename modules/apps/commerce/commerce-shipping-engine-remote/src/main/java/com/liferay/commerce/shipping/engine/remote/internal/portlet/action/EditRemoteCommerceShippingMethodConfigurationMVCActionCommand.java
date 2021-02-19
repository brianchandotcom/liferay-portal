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

package com.liferay.commerce.shipping.engine.remote.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.shipping.engine.remote.internal.constants.RemoteCommerceShippingEngineConstants;
import com.liferay.portal.kernel.exception.PortalException;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_SHIPPING_METHODS,
		"mvc.command.name=/commerce_shipping/edit_remote_commerce_shipping_method_configuration"
	},
	service = MVCActionCommand.class
)
public class EditRemoteCommerceShippingMethodConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Settings settings = _settingsFactory.getSettings(
			new GroupServiceSettingsLocator(
				_portal.getScopeGroupId(actionRequest),
				RemoteCommerceShippingEngineConstants.SERVICE_NAME));

		ModifiableSettings modifiableSettings =
			settings.getModifiableSettings();

		String shippingOptionsEndpointAuthorizationToken = ParamUtil.getString(
			actionRequest,
			"settings--shippingOptionsEndpointAuthorizationToken--");

		if (Validator.isNotNull(shippingOptionsEndpointAuthorizationToken)) {
			modifiableSettings.setValue(
				"shippingOptionsEndpointAuthorizationToken",
				shippingOptionsEndpointAuthorizationToken);
		}

		String shippingOptionsEndpointURL = ParamUtil.getString(
			actionRequest, "settings--shippingOptionsEndpointURL--");

		_validate(shippingOptionsEndpointURL);

		modifiableSettings.setValue(
			"shippingOptionsEndpointURL",
			String.valueOf(shippingOptionsEndpointURL));

		modifiableSettings.store();
	}

	private void _validate(String shippingOptionsEndpointURL) throws Exception {
		if (!Validator.isUrl(shippingOptionsEndpointURL)) {
			throw new PortalException(
				"Invalid URL " + shippingOptionsEndpointURL);
		}
	}

	@Reference
	private Portal _portal;

	@Reference
	private SettingsFactory _settingsFactory;

}
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

package com.liferay.saml.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.web.internal.constants.SamlAdminPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SamlAdminPortletKeys.SAML_ADMIN,
		"mvc.command.name=/admin/updateServiceProvider"
	},
	service = MVCActionCommand.class
)
public class UpdateServiceProviderMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "settings--");

		_samlProviderConfigurationHelper.updateProperties(properties);

		actionResponse.setRenderParameter("mvcRenderCommandName", "/admin");
		actionResponse.setRenderParameter("tabs1", "service-provider");
	}

	@Reference
	private SamlProviderConfigurationHelper _samlProviderConfigurationHelper;

}
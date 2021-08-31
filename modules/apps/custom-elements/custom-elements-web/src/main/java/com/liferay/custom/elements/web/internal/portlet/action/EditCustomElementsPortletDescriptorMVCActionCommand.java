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

package com.liferay.custom.elements.web.internal.portlet.action;

import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;
import com.liferay.custom.elements.service.CustomElementsPortletDescriptorLocalService;
import com.liferay.custom.elements.web.internal.constants.CustomElementsPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CustomElementsPortletKeys.CUSTOM_ELEMENTS_PORTLET_DESCRIPTOR,
		"mvc.command.name=/custom_elements_portlet_descriptor/edit_custom_elements_portlet_descriptor"
	},
	service = MVCActionCommand.class
)
public class EditCustomElementsPortletDescriptorMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String cssURLs = ParamUtil.getString(actionRequest, "cssURLs");
		String htmlElementName = ParamUtil.getString(
			actionRequest, "htmlElementName");
		boolean instanceable = ParamUtil.getBoolean(
			actionRequest, "instanceable");
		String name = ParamUtil.getString(actionRequest, "name");
		String properties = ParamUtil.getString(actionRequest, "properties");

		if (cmd.equals(Constants.ADD)) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CustomElementsPortletDescriptor.class.getName(), actionRequest);

			_customElementsPortletDescriptorLocalService.
				addCustomElementsPortletDescriptor(
					serviceContext.getUserId(), cssURLs, htmlElementName,
					instanceable, name, properties, serviceContext);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			long customElementsPortletDescriptorId = ParamUtil.getLong(
				actionRequest, "customElementsPortletDescriptorId");

			_customElementsPortletDescriptorLocalService.
				updateCustomElementsPortletDescriptor(
					customElementsPortletDescriptorId, cssURLs, htmlElementName,
					instanceable, name, properties);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	@Reference
	private CustomElementsPortletDescriptorLocalService
		_customElementsPortletDescriptorLocalService;

}
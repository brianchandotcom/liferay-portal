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

import com.liferay.custom.elements.model.CustomElementsSource;
import com.liferay.custom.elements.service.CustomElementsSourceLocalService;
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
		"javax.portlet.name=" + CustomElementsPortletKeys.CUSTOM_ELEMENTS_SOURCE,
		"mvc.command.name=/custom_elements_source/edit_custom_elements_source"
	},
	service = MVCActionCommand.class
)
public class EditCustomElementsSourceMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		String htmlElementName = ParamUtil.getString(
			actionRequest, "HTMLElementName");
		String name = ParamUtil.getString(actionRequest, "name");
		String urls = ParamUtil.getString(actionRequest, "URLs");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CustomElementsSource.class.getName(), actionRequest);

		if (cmd.equals(Constants.ADD)) {
			_customElementsSourceLocalService.addCustomElementsSource(
				serviceContext.getUserId(), htmlElementName, name, urls,
				serviceContext);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			_customElementsSourceLocalService.updateCustomElementsSource(
				ParamUtil.getLong(actionRequest, "customElementsSourceId"),
				htmlElementName, name, urls, serviceContext);
		}

		String redirect = ParamUtil.getString(actionRequest, "redirect");

		if (Validator.isNotNull(redirect)) {
			actionResponse.sendRedirect(redirect);
		}
	}

	@Reference
	private CustomElementsSourceLocalService _customElementsSourceLocalService;

}
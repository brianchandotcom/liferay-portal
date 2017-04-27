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

package com.liferay.commerce.product.options.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.NoSuchProductOptionException;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_OPTIONS,
		"mvc.command.name=editProductOption"
	},
	service = MVCActionCommand.class
)
public class EditCPOptionMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCPOption(ActionRequest actionRequest)
		throws Exception {

		long cpOptionId = ParamUtil.getLong(
			actionRequest, "cpOptionId");

		_cpOptionLocalService.deleteCPOption(
			cpOptionId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCPOption(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCPOption(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchProductOptionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected CPOption updateCPOption(
			ActionRequest actionRequest)
		throws Exception {

		long cpOptionId = ParamUtil.getLong(
			actionRequest, "cpOptionId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		String ddmFormFieldTypeName = ParamUtil.getString(
			actionRequest, "ddmFormFieldTypeName");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPOption.class.getName(), actionRequest);

		CPOption cpOption = null;

		if (cpOptionId <= 0) {

			// Add commerce product option

			cpOption =
				_cpOptionLocalService.addCPOption(
					nameMap, descriptionMap, ddmFormFieldTypeName,
					serviceContext);
		}
		else {

			// Update commerce product option

			cpOption =
				_cpOptionLocalService.updateCPOption(
					cpOptionId, nameMap, descriptionMap,
					ddmFormFieldTypeName, serviceContext);
		}

		return cpOption;
	}

	@Reference
	private CPOptionLocalService
		_cpOptionLocalService;

}
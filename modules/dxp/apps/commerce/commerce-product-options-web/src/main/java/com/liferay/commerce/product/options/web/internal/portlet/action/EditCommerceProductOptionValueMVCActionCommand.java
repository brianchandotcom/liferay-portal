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

import com.liferay.commerce.product.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.exception.NoSuchProductOptionValueException;
import com.liferay.commerce.product.model.CommerceProductOptionValue;
import com.liferay.commerce.product.service.CommerceProductOptionValueLocalService;
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
		"javax.portlet.name=" + CommerceProductPortletKeys.COMMERCE_PRODUCT_OPTIONS,
		"mvc.command.name=editProductOptionValue"
	},
	service = MVCActionCommand.class
)
public class EditCommerceProductOptionValueMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceProductOptionValue(ActionRequest actionRequest)
		throws Exception {

		long commerceProductOptionValueId = ParamUtil.getLong(
			actionRequest, "commerceProductOptionValueId");

		_commerceProductOptionValueLocalService.
			deleteCommerceProductOptionValue(commerceProductOptionValueId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceProductOptionValue(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommerceProductOptionValue(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchProductOptionValueException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected CommerceProductOptionValue updateCommerceProductOptionValue(
			ActionRequest actionRequest)
		throws Exception {

		long commerceProductOptionValueId = ParamUtil.getLong(
			actionRequest, "commerceProductOptionValueId");

		long commerceProductOptionId = ParamUtil.getLong(
			actionRequest, "commerceProductOptionId");
		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");
		int priority = ParamUtil.getInteger(actionRequest, "priority");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceProductOptionValue.class.getName(), actionRequest);

		CommerceProductOptionValue commerceProductOptionValue = null;

		if (commerceProductOptionValueId <= 0) {

			// Add commerce product option value

			commerceProductOptionValue =
				_commerceProductOptionValueLocalService.
					addCommerceProductOptionValue(
						commerceProductOptionId, titleMap, priority,
						serviceContext);
		}
		else {

			// Update commerce product option value

			commerceProductOptionValue =
				_commerceProductOptionValueLocalService.
					updateCommerceProductOptionValue(
						commerceProductOptionValueId, titleMap, priority,
						serviceContext);
		}

		return commerceProductOptionValue;
	}

	@Reference
	private CommerceProductOptionValueLocalService
		_commerceProductOptionValueLocalService;

}
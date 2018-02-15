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

package com.liferay.commerce.shipping.engine.fixed.web.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceShippingFixedOption"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShippingFixedOptionMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceShippingFixedOptions(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceShippingFixedOptionIds = null;

		long commerceShippingFixedOptionId = ParamUtil.getLong(
			actionRequest, "commerceShippingFixedOptionId");

		if (commerceShippingFixedOptionId > 0) {
			deleteCommerceShippingFixedOptionIds =
				new long[] {commerceShippingFixedOptionId};
		}
		else {
			deleteCommerceShippingFixedOptionIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceShippingFixedOptionIds"),
				0L);
		}

		for (long deleteCommerceShippingFixedOptionId :
				deleteCommerceShippingFixedOptionIds) {

			_commerceShippingFixedOptionService.
				deleteCommerceShippingFixedOption(
					deleteCommerceShippingFixedOptionId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceShippingFixedOption(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceShippingFixedOptions(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchShippingFixedOptionException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceShippingFixedOption(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceShippingFixedOptionId = ParamUtil.getLong(
			actionRequest, "commerceShippingFixedOptionId");

		long commerceShippingMethodId = ParamUtil.getLong(
			actionRequest, "commerceShippingMethodId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		double amount = ParamUtil.getDouble(actionRequest, "amount");
		double priority = ParamUtil.getDouble(actionRequest, "priority");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceShippingFixedOption.class.getName(), actionRequest);

		if (commerceShippingFixedOptionId > 0) {
			_commerceShippingFixedOptionService.
				updateCommerceShippingFixedOption(
					commerceShippingFixedOptionId, nameMap, descriptionMap,
					amount, priority);
		}
		else {
			_commerceShippingFixedOptionService.addCommerceShippingFixedOption(
				commerceShippingMethodId, nameMap, descriptionMap, amount,
				priority, serviceContext);
		}
	}

	@Reference
	private CommerceShippingFixedOptionService
		_commerceShippingFixedOptionService;

}
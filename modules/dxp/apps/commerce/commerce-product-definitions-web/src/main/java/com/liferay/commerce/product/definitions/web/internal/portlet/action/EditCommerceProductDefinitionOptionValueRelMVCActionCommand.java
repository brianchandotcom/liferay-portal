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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.model.CommerceProductDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CommerceProductDefinitionOptionValueRelService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
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
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceProductPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=editProductDefinitionOptionValueRel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceProductDefinitionOptionValueRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceProductDefinitionOptionValueRel(
			ActionRequest actionRequest)
		throws Exception {

		long commerceProductDefinitionOptionValueRelId = ParamUtil.getLong(
			actionRequest, "commerceProductDefinitionOptionValueRelId");

		// Delete commerce product definition option value rel

		_commerceProductDefinitionOptionValueRelService.
			deleteCommerceProductDefinitionOptionValueRel(
				commerceProductDefinitionOptionValueRelId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceProductDefinitionOptionValueRel(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceProductDefinitionOptionValueRel(actionRequest);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	protected CommerceProductDefinitionOptionValueRel
			updateCommerceProductDefinitionOptionValueRel(
				ActionRequest actionRequest)
		throws Exception {

		long commerceProductDefinitionOptionValueRelId = ParamUtil.getLong(
			actionRequest, "commerceProductDefinitionOptionValueRelId");

		long commerceProductDefinitionOptionRelId = ParamUtil.getLong(
			actionRequest, "commerceProductDefinitionOptionRelId");

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");

		int priority = ParamUtil.getInteger(actionRequest, "priority");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceProductDefinitionOptionValueRel.class.getName(),
			actionRequest);

		CommerceProductDefinitionOptionValueRel
			commerceProductDefinitionOptionValueRel = null;

		if (commerceProductDefinitionOptionValueRelId <= 0) {

			// Add commerce product definition option value rel

			commerceProductDefinitionOptionValueRel =
				_commerceProductDefinitionOptionValueRelService.
					addCommerceProductDefinitionOptionValueRel(
						commerceProductDefinitionOptionRelId, titleMap,
						priority, serviceContext);
		}
		else {

			// Update commerce product definition option value rel

			commerceProductDefinitionOptionValueRel =
				_commerceProductDefinitionOptionValueRelService.
					updateCommerceProductDefinitionOptionValueRel(
						commerceProductDefinitionOptionValueRelId, titleMap,
						priority, serviceContext);
		}

		return commerceProductDefinitionOptionValueRel;
	}

	@Reference
	private CommerceProductDefinitionOptionValueRelService
		_commerceProductDefinitionOptionValueRelService;

}
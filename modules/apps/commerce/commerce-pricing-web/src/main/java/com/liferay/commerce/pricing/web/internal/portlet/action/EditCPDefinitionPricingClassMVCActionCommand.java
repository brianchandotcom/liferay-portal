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

package com.liferay.commerce.pricing.web.internal.portlet.action;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
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
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/edit_cp_definition_pricing_class"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionPricingClassMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.UPDATE)) {
				updateCommercePricingClass(actionRequest);
			}
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	protected CommercePricingClass updateCommercePricingClass(
			ActionRequest actionRequest)
		throws Exception {

		CommercePricingClass commercePricingClass = null;

		Map<Locale, String> titleMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "title");

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePricingClass.class.getName(), actionRequest);

		long commercePricingClassId = ParamUtil.getLong(
			actionRequest, "commercePricingClassId");

		if (commercePricingClassId <= 0) {
			commercePricingClass =
				_commercePricingClassService.addCommercePricingClass(
					null, titleMap, descriptionMap, serviceContext);
		}
		else {
			commercePricingClass =
				_commercePricingClassService.updateCommercePricingClass(
					commercePricingClassId, titleMap, descriptionMap,
					serviceContext);
		}

		return commercePricingClass;
	}

	@Reference
	private CommercePricingClassService _commercePricingClassService;

}
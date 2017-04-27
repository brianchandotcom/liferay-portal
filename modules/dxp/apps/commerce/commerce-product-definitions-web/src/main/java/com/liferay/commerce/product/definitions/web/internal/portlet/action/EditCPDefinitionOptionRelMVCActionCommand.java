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

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
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
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=editProductDefinitionOptionRel"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionOptionRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCPDefinitionOptionRel(ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionOptionRelId = ParamUtil.getLong(
			actionRequest, "cpDefinitionOptionRelId");

		_cpDefinitionOptionRelService.deleteCPDefinitionOptionRel(
			cpDefinitionOptionRelId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCPDefinitionOptionRel(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCPDefinitionOptionRel(actionRequest);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	protected CPDefinitionOptionRel
			updateCPDefinitionOptionRel(
				ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionOptionRelId = ParamUtil.getLong(
			actionRequest, "cpDefinitionOptionRelId");

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		long cpOptionId = ParamUtil.getLong(
			actionRequest, "cpOptionId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap = LocalizationUtil.
			getLocalizationMap(actionRequest, "description");

		String ddmFormFieldTypeName = ParamUtil.
			getString(actionRequest, "ddmFormFieldTypeName");

		int priority = ParamUtil.getInteger(actionRequest, "priority");

		boolean facetable = ParamUtil.getBoolean(actionRequest, "facetable");
		boolean skuContributor = ParamUtil.getBoolean(
			actionRequest, "skuContributor");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinitionOptionRel.class.getName(), actionRequest);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			null;

		if (cpDefinitionOptionRelId <= 0) {

			// Add commerce product definition option rel

			cpDefinitionOptionRel =
				_cpDefinitionOptionRelService.
					addCPDefinitionOptionRel(
						cpDefinitionId, cpOptionId,
						serviceContext);
		}
		else {

			// Update commerce product definition option rel

			cpDefinitionOptionRel =
				_cpDefinitionOptionRelService.
					updateCPDefinitionOptionRel(
						cpDefinitionOptionRelId,
						cpOptionId, nameMap, descriptionMap,
						ddmFormFieldTypeName, priority, facetable,
						skuContributor, serviceContext);
		}

		return cpDefinitionOptionRel;
	}

	@Reference
	private CPDefinitionOptionRelService
		_cpDefinitionOptionRelService;

}
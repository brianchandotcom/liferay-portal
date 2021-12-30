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

package com.liferay.commerce.product.tax.category.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.exception.CPTaxCategoryNameException;
import com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
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
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_TAX_CATEGORY,
		"mvc.command.name=/cp_tax_category/edit_cp_tax_category"
	},
	service = MVCActionCommand.class
)
public class EditCPTaxCategoryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				_deleteCPTaxCategories(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				_updateCPTaxCategory(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchCPTaxCategoryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof CPTaxCategoryNameException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName",
					"/cp_tax_category/edit_cp_tax_category");
			}
			else {
				throw exception;
			}
		}
	}

	private void _deleteCPTaxCategories(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCPTaxCategoryIds = null;

		long cpTaxCategoryId = ParamUtil.getLong(
			actionRequest, "cpTaxCategoryId");

		if (cpTaxCategoryId > 0) {
			deleteCPTaxCategoryIds = new long[] {cpTaxCategoryId};
		}
		else {
			deleteCPTaxCategoryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCPTaxCategoryIds"),
				0L);
		}

		for (long deleteCPTaxCategoryId : deleteCPTaxCategoryIds) {
			_cpTaxCategoryService.deleteCPTaxCategory(deleteCPTaxCategoryId);
		}
	}

	private void _updateCPTaxCategory(ActionRequest actionRequest)
		throws PortalException {

		long cpTaxCategoryId = ParamUtil.getLong(
			actionRequest, "cpTaxCategoryId");

		String externalReferenceCode = ParamUtil.getString(
			actionRequest, "externalReferenceCode");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		if (cpTaxCategoryId <= 0) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CPTaxCategory.class.getName(), actionRequest);

			_cpTaxCategoryService.addCPTaxCategory(
				externalReferenceCode, nameMap, descriptionMap, serviceContext);
		}
		else {
			_cpTaxCategoryService.updateCPTaxCategory(
				externalReferenceCode, cpTaxCategoryId, nameMap,
				descriptionMap);
		}
	}

	@Reference
	private CPTaxCategoryService _cpTaxCategoryService;

}
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

package com.liferay.commerce.tax.category.web.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.exception.CommerceTaxCategoryNameException;
import com.liferay.commerce.exception.NoSuchTaxCategoryException;
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.service.CommerceTaxCategoryService;
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
		"mvc.command.name=editCommerceTaxCategory"
	},
	service = MVCActionCommand.class
)
public class EditCommerceTaxCategoryMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceTaxCategories(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceTaxCategoryIds = null;

		long commerceTaxCategoryId = ParamUtil.getLong(
			actionRequest, "commerceTaxCategoryId");

		if (commerceTaxCategoryId > 0) {
			deleteCommerceTaxCategoryIds = new long[] {commerceTaxCategoryId};
		}
		else {
			deleteCommerceTaxCategoryIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceTaxCategoryIds"),
				0L);
		}

		for (long deleteCommerceTaxCategoryId : deleteCommerceTaxCategoryIds) {
			_commerceTaxCategoryService.deleteCommerceTaxCategory(
				deleteCommerceTaxCategoryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceTaxCategories(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommerceTaxCategory(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchTaxCategoryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommerceTaxCategoryNameException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCommerceTaxCategory");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceTaxCategory(ActionRequest actionRequest)
		throws PortalException {

		long commerceTaxCategoryId = ParamUtil.getLong(
			actionRequest, "commerceTaxCategoryId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceTaxCategory.class.getName(), actionRequest);

		if (commerceTaxCategoryId <= 0) {
			_commerceTaxCategoryService.addCommerceTaxCategory(
				nameMap, descriptionMap, serviceContext);
		}
		else {
			_commerceTaxCategoryService.updateCommerceTaxCategory(
				commerceTaxCategoryId, nameMap, descriptionMap);
		}
	}

	@Reference
	private CommerceTaxCategoryService _commerceTaxCategoryService;

}
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

import com.liferay.commerce.exception.NoSuchTaxCategoryRelException;
import com.liferay.commerce.model.CommerceTaxCategoryRel;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.service.CommerceTaxCategoryRelService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editCommerceTaxCategoryRel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceTaxCategoryRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommerceTaxCategoryRels(ActionRequest actionRequest)
		throws Exception {

		long[] addCommerceTaxCategoryIds = null;

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");
		long commerceTaxCategoryId = ParamUtil.getLong(
			actionRequest, "commerceTaxCategoryId");

		if (commerceTaxCategoryId > 0) {
			addCommerceTaxCategoryIds = new long[] {commerceTaxCategoryId};
		}
		else {
			addCommerceTaxCategoryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "commerceTaxCategoryIds"),
				0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceTaxCategoryRel.class.getName(), actionRequest);

		for (long addCommerceTaxCategoryId : addCommerceTaxCategoryIds) {
			_commerceTaxCategoryRelService.addCommerceTaxCategoryRel(
				addCommerceTaxCategoryId, CPDefinition.class.getName(),
				cpDefinitionId, serviceContext);
		}
	}

	protected void deleteCommerceTaxCategoryRels(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceTaxCategoryRelIds = null;

		long commerceTaxCategoryRelId = ParamUtil.getLong(
			actionRequest, "commerceTaxCategoryRelId");

		if (commerceTaxCategoryRelId > 0) {
			deleteCommerceTaxCategoryRelIds =
				new long[] {commerceTaxCategoryRelId};
		}
		else {
			deleteCommerceTaxCategoryRelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceTaxCategoryRelIds"),
				0L);
		}

		for (long deleteCommerceTaxCategoryRelId :
				deleteCommerceTaxCategoryRelIds) {

			_commerceTaxCategoryRelService.deleteCommerceTaxCategoryRel(
				deleteCommerceTaxCategoryRelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_MULTIPLE)) {

				addCommerceTaxCategoryRels(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceTaxCategoryRels(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchTaxCategoryRelException ||
				e instanceof PrincipalException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw e;
			}
		}
	}

	@Reference
	private CommerceTaxCategoryRelService _commerceTaxCategoryRelService;

}
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

package com.liferay.commerce.tax.engine.fixed.web.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.exception.NoSuchTaxCategoryException;
import com.liferay.commerce.tax.engine.fixed.exception.CommerceTaxFixedRateCommerceTaxCategoryIdException;
import com.liferay.commerce.tax.engine.fixed.exception.NoSuchTaxFixedRateException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateService;
import com.liferay.portal.kernel.exception.PortalException;
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
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceTaxFixedRate"
	},
	service = MVCActionCommand.class
)
public class EditCommerceTaxFixedRateMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceTaxFixedRates(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceTaxFixedRateIds = null;

		long commerceTaxFixedRateId = ParamUtil.getLong(
			actionRequest, "commerceTaxFixedRateId");

		if (commerceTaxFixedRateId > 0) {
			deleteCommerceTaxFixedRateIds = new long[] {commerceTaxFixedRateId};
		}
		else {
			deleteCommerceTaxFixedRateIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceTaxFixedRateIds"),
				0L);
		}

		for (long deleteCommerceTaxFixedRateId :
				deleteCommerceTaxFixedRateIds) {

			_commerceTaxFixedRateService.deleteCommerceTaxFixedRate(
				deleteCommerceTaxFixedRateId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceTaxFixedRate(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceTaxFixedRates(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof
					CommerceTaxFixedRateCommerceTaxCategoryIdException ||
				e instanceof NoSuchTaxCategoryException ||
				e instanceof NoSuchTaxFixedRateException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceTaxFixedRate(ActionRequest actionRequest)
		throws PortalException {

		long commerceTaxFixedRateId = ParamUtil.getLong(
			actionRequest, "commerceTaxFixedRateId");

		long commerceTaxMethodId = ParamUtil.getLong(
			actionRequest, "commerceTaxMethodId");
		long commerceTaxCategoryId = ParamUtil.getLong(
			actionRequest, "commerceTaxCategoryId");

		double rate = ParamUtil.getDouble(actionRequest, "rate");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceTaxFixedRate.class.getName(), actionRequest);

		if (commerceTaxFixedRateId > 0) {
			_commerceTaxFixedRateService.updateCommerceTaxFixedRate(
				commerceTaxFixedRateId, rate);
		}
		else {
			_commerceTaxFixedRateService.addCommerceTaxFixedRate(
				commerceTaxMethodId, commerceTaxCategoryId, rate,
				serviceContext);
		}
	}

	@Reference
	private CommerceTaxFixedRateService _commerceTaxFixedRateService;

}
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
import com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException;
import com.liferay.commerce.tax.engine.fixed.exception.DuplicateCommerceTaxFixedRateException;
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
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceTaxFixedRate"
	},
	service = MVCActionCommand.class
)
public class EditCommerceTaxFixedRateMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceTaxFixedRate(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof
					DuplicateCommerceTaxFixedRateException ||
				e instanceof NoSuchCPTaxCategoryException ||
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

		long commerceTaxMethodId = ParamUtil.getLong(
			actionRequest, "commerceTaxMethodId");
		long cpTaxCategoryId = ParamUtil.getLong(
			actionRequest, "cpTaxCategoryId");

		double rate = ParamUtil.getDouble(actionRequest, "rate");

		CommerceTaxFixedRate commerceTaxFixedRate =
			_commerceTaxFixedRateService.fetchCommerceTaxFixedRateByCPTC_CTM(
				cpTaxCategoryId, commerceTaxMethodId);

		if (commerceTaxFixedRate != null) {
			_commerceTaxFixedRateService.updateCommerceTaxFixedRate(
				commerceTaxFixedRate.getCommerceTaxFixedRateId(), rate);
		}
		else {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceTaxFixedRate.class.getName(), actionRequest);

			_commerceTaxFixedRateService.addCommerceTaxFixedRate(
				commerceTaxMethodId, cpTaxCategoryId, rate, serviceContext);
		}
	}

	@Reference
	private CommerceTaxFixedRateService _commerceTaxFixedRateService;

}
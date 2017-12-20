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

import com.liferay.commerce.admin.web.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchCShippingFixedOptionRelException;
import com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.CShippingFixedOptionRelService;
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
		"mvc.command.name=editCShippingFixedOptionRel"
	},
	service = MVCActionCommand.class
)
public class EditCShippingFixedOptionRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCShippingFixedOptionRels(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCShippingFixedOptionRelIds = null;

		long cShippingFixedOptionRelId = ParamUtil.getLong(
			actionRequest, "cShippingFixedOptionRelId");

		if (cShippingFixedOptionRelId > 0) {
			deleteCShippingFixedOptionRelIds =
				new long[] {cShippingFixedOptionRelId};
		}
		else {
			deleteCShippingFixedOptionRelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCShippingFixedOptionRelIds"),
				0L);
		}

		for (long deleteCShippingFixedOptionRelId :
				deleteCShippingFixedOptionRelIds) {

			_cShippingFixedOptionRelService.deleteCShippingFixedOptionRel(
				deleteCShippingFixedOptionRelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCShippingFixedOptionRel(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCShippingFixedOptionRels(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCShippingFixedOptionRelException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCShippingFixedOptionRel(ActionRequest actionRequest)
		throws PortalException {

		long cShippingFixedOptionRelId = ParamUtil.getLong(
			actionRequest, "cShippingFixedOptionRelId");

		long commerceShippingMethodId = ParamUtil.getLong(
			actionRequest, "commerceShippingMethodId");

		long commerceShippingFixedOptionId = ParamUtil.getLong(
			actionRequest, "commerceShippingFixedOptionId");

		long commerceWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceWarehouseId");

		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");

		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");

		String zip = ParamUtil.getString(actionRequest, "zip");
		double weightFrom = ParamUtil.getDouble(actionRequest, "weightFrom");
		double weightTo = ParamUtil.getDouble(actionRequest, "weightTo");
		double fixedPrice = ParamUtil.getDouble(actionRequest, "fixedPrice");
		double rateUnitWeightPrice = ParamUtil.getDouble(
			actionRequest, "rateUnitWeightPrice");
		double ratePercentage = ParamUtil.getDouble(
			actionRequest, "ratePercentage");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CShippingFixedOptionRel.class.getName(), actionRequest);

		if (cShippingFixedOptionRelId > 0) {
			_cShippingFixedOptionRelService.updateCShippingFixedOptionRel(
				cShippingFixedOptionRelId, commerceWarehouseId,
				commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
				fixedPrice, rateUnitWeightPrice, ratePercentage);
		}
		else {
			_cShippingFixedOptionRelService.addCShippingFixedOptionRel(
				commerceShippingMethodId, commerceShippingFixedOptionId,
				commerceWarehouseId, commerceCountryId, commerceRegionId, zip,
				weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
				ratePercentage, serviceContext);
		}
	}

	@Reference
	private CShippingFixedOptionRelService _cShippingFixedOptionRelService;

}
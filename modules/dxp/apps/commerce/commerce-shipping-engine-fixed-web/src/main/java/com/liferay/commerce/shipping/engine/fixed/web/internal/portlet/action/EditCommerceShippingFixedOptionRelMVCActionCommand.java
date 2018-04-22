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
import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionRelService;
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

import java.math.BigDecimal;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceShippingFixedOptionRel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShippingFixedOptionRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceShippingFixedOptionRels(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceShippingFixedOptionRelIds = null;

		long commerceShippingFixedOptionRelId = ParamUtil.getLong(
			actionRequest, "commerceShippingFixedOptionRelId");

		if (commerceShippingFixedOptionRelId > 0) {
			deleteCommerceShippingFixedOptionRelIds =
				new long[] {commerceShippingFixedOptionRelId};
		}
		else {
			deleteCommerceShippingFixedOptionRelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceShippingFixedOptionRelIds"),
				0L);
		}

		for (long deleteCommerceShippingFixedOptionRelId :
				deleteCommerceShippingFixedOptionRelIds) {

			_commerceShippingFixedOptionRelService.
				deleteCommerceShippingFixedOptionRel(
					deleteCommerceShippingFixedOptionRelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceShippingFixedOptionRel(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceShippingFixedOptionRels(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchShippingFixedOptionRelException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceShippingFixedOptionRel(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceShippingFixedOptionRelId = ParamUtil.getLong(
			actionRequest, "commerceShippingFixedOptionRelId");

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
		String fixedPrice = ParamUtil.getString(actionRequest, "fixedPrice");
		String rateUnitWeightPrice = ParamUtil.getString(
			actionRequest, "rateUnitWeightPrice");
		double ratePercentage = ParamUtil.getDouble(
			actionRequest, "ratePercentage");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceShippingFixedOptionRel.class.getName(), actionRequest);

		if (commerceShippingFixedOptionRelId > 0) {
			_commerceShippingFixedOptionRelService.
				updateCommerceShippingFixedOptionRel(
					commerceShippingFixedOptionRelId, commerceWarehouseId,
					commerceCountryId, commerceRegionId, zip, weightFrom,
					weightTo, new BigDecimal(fixedPrice),
					new BigDecimal(rateUnitWeightPrice), ratePercentage);
		}
		else {
			_commerceShippingFixedOptionRelService.
				addCommerceShippingFixedOptionRel(
					commerceShippingMethodId, commerceShippingFixedOptionId,
					commerceWarehouseId, commerceCountryId, commerceRegionId,
					zip, weightFrom, weightTo, new BigDecimal(fixedPrice),
					new BigDecimal(rateUnitWeightPrice), ratePercentage,
					serviceContext);
		}
	}

	@Reference
	private CommerceShippingFixedOptionRelService
		_commerceShippingFixedOptionRelService;

}
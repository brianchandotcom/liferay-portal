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

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.service.CommerceShippingMethodService;
import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionRelException;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.math.BigDecimal;

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
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_SHIPPING_METHODS,
		"mvc.command.name=/commerce_shipping_methods/edit_commerce_shipping_fixed_option_rel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShippingFixedOptionRelMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateCommerceShippingFixedOptionRel(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCommerceShippingFixedOptionRels(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchShippingFixedOptionRelException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw exception;
			}
		}
	}

	private void _deleteCommerceShippingFixedOptionRels(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceShippingFixedOptionRelIds = null;

		long commerceShippingFixedOptionRelId = ParamUtil.getLong(
			actionRequest, "commerceShippingFixedOptionRelId");

		if (commerceShippingFixedOptionRelId > 0) {
			deleteCommerceShippingFixedOptionRelIds = new long[] {
				commerceShippingFixedOptionRelId
			};
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

	private void _updateCommerceShippingFixedOptionRel(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceShippingFixedOptionRelId = ParamUtil.getLong(
			actionRequest, "commerceShippingFixedOptionRelId");

		long commerceInventoryWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceInventoryWarehouseId");
		long countryId = ParamUtil.getLong(actionRequest, "countryId");
		long regionId = ParamUtil.getLong(actionRequest, "regionId");
		String zip = ParamUtil.getString(actionRequest, "zip");
		double weightFrom = ParamUtil.getDouble(actionRequest, "weightFrom");
		double weightTo = ParamUtil.getDouble(actionRequest, "weightTo");
		BigDecimal fixedPrice = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "fixedPrice", BigDecimal.ZERO);
		BigDecimal rateUnitWeightPrice = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "rateUnitWeightPrice", BigDecimal.ZERO);
		double ratePercentage = ParamUtil.getDouble(
			actionRequest, "ratePercentage");

		if (commerceShippingFixedOptionRelId > 0) {
			_commerceShippingFixedOptionRelService.
				updateCommerceShippingFixedOptionRel(
					commerceShippingFixedOptionRelId,
					commerceInventoryWarehouseId, countryId, regionId, zip,
					weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
					ratePercentage);
		}
		else {
			long commerceShippingMethodId = ParamUtil.getLong(
				actionRequest, "commerceShippingMethodId");
			long commerceShippingFixedOptionId = ParamUtil.getLong(
				actionRequest, "commerceShippingFixedOptionId");

			CommerceShippingMethod commerceShippingMethod =
				_commerceShippingMethodService.getCommerceShippingMethod(
					commerceShippingMethodId);

			_commerceShippingFixedOptionRelService.
				addCommerceShippingFixedOptionRel(
					commerceShippingMethod.getGroupId(),
					commerceShippingMethod.getCommerceShippingMethodId(),
					commerceShippingFixedOptionId, commerceInventoryWarehouseId,
					countryId, regionId, zip, weightFrom, weightTo, fixedPrice,
					rateUnitWeightPrice, ratePercentage);
		}
	}

	@Reference
	private CommerceShippingFixedOptionRelService
		_commerceShippingFixedOptionRelService;

	@Reference
	private CommerceShippingMethodService _commerceShippingMethodService;

	@Reference
	private Portal _portal;

}
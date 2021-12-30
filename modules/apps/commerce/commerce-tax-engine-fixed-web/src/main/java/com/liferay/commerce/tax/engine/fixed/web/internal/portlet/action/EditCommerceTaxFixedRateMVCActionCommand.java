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

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.product.exception.NoSuchCPTaxCategoryException;
import com.liferay.commerce.tax.engine.fixed.exception.DuplicateCommerceTaxFixedRateException;
import com.liferay.commerce.tax.engine.fixed.exception.NoSuchTaxFixedRateException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateService;
import com.liferay.commerce.tax.model.CommerceTaxMethod;
import com.liferay.commerce.tax.service.CommerceTaxMethodService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import java.text.NumberFormat;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_TAX_METHODS,
		"mvc.command.name=/commerce_tax_methods/edit_commerce_tax_fixed_rate"
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
				_updateCommerceTaxFixedRate(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCommerceTaxFixedRates(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof DuplicateCommerceTaxFixedRateException ||
				exception instanceof NoSuchCPTaxCategoryException ||
				exception instanceof NoSuchTaxFixedRateException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw exception;
			}
		}
	}

	private void _deleteCommerceTaxFixedRates(ActionRequest actionRequest)
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

	private void _updateCommerceTaxFixedRate(ActionRequest actionRequest)
		throws Exception {

		long commerceTaxFixedRateId = ParamUtil.getLong(
			actionRequest, "commerceTaxFixedRateId");
		long commerceTaxMethodId = ParamUtil.getLong(
			actionRequest, "commerceTaxMethodId");
		long cpTaxCategoryId = ParamUtil.getLong(
			actionRequest, "CPTaxCategoryId");

		String localizedRate = ParamUtil.getString(actionRequest, "rate");

		NumberFormat numberFormat = NumberFormat.getNumberInstance(
			_portal.getLocale(actionRequest));

		Number rate = numberFormat.parse(localizedRate);

		CommerceTaxFixedRate commerceTaxFixedRate =
			_commerceTaxFixedRateService.fetchCommerceTaxFixedRate(
				cpTaxCategoryId, commerceTaxMethodId);

		if (commerceTaxFixedRate != null) {
			commerceTaxFixedRateId =
				commerceTaxFixedRate.getCommerceTaxFixedRateId();
		}

		if (commerceTaxFixedRateId > 0) {
			_commerceTaxFixedRateService.updateCommerceTaxFixedRate(
				commerceTaxFixedRateId, rate.doubleValue());
		}
		else {
			CommerceTaxMethod commerceTaxMethod =
				_commerceTaxMethodService.getCommerceTaxMethod(
					commerceTaxMethodId);

			_commerceTaxFixedRateService.addCommerceTaxFixedRate(
				commerceTaxMethod.getGroupId(),
				commerceTaxMethod.getCommerceTaxMethodId(), cpTaxCategoryId,
				rate.doubleValue());
		}
	}

	@Reference
	private CommerceTaxFixedRateService _commerceTaxFixedRateService;

	@Reference
	private CommerceTaxMethodService _commerceTaxMethodService;

	@Reference
	private Portal _portal;

}
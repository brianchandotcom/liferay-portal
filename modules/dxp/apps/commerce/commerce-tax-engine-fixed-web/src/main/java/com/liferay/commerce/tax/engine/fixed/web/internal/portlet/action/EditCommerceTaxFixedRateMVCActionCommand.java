/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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
			_commerceTaxFixedRateService.fetchCommerceTaxFixedRate(
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
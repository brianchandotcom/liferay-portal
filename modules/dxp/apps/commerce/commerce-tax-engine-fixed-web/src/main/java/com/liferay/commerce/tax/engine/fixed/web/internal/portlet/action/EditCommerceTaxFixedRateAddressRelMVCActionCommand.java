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
import com.liferay.commerce.tax.engine.fixed.exception.NoSuchTaxFixedRateAddressRelException;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelService;
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
		"mvc.command.name=editCommerceTaxFixedRateAddressRel"
	},
	service = MVCActionCommand.class
)
public class EditCommerceTaxFixedRateAddressRelMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceTaxFixedRateAddressRels(
			ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceTaxFixedRateAddressRelIds = null;

		long commerceTaxFixedRateAddressRelId = ParamUtil.getLong(
			actionRequest, "commerceTaxFixedRateAddressRelId");

		if (commerceTaxFixedRateAddressRelId > 0) {
			deleteCommerceTaxFixedRateAddressRelIds =
				new long[] {commerceTaxFixedRateAddressRelId};
		}
		else {
			deleteCommerceTaxFixedRateAddressRelIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceTaxFixedRateAddressRelIds"),
				0L);
		}

		for (long deleteCommerceTaxFixedRateAddressRelId :
				deleteCommerceTaxFixedRateAddressRelIds) {

			_commerceTaxFixedRateAddressRelService.
				deleteCommerceTaxFixedRateAddressRel(
					deleteCommerceTaxFixedRateAddressRelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceTaxFixedRateAddressRel(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceTaxFixedRateAddressRels(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchTaxFixedRateAddressRelException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceTaxFixedRateAddressRel(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceTaxFixedRateAddressRelId = ParamUtil.getLong(
			actionRequest, "commerceTaxFixedRateAddressRelId");

		long commerceTaxMethodId = ParamUtil.getLong(
			actionRequest, "commerceTaxMethodId");
		long cpTaxCategoryId = ParamUtil.getLong(
			actionRequest, "cpTaxCategoryId");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");
		long commerceRegionId = ParamUtil.getLong(
			actionRequest, "commerceRegionId");
		String zip = ParamUtil.getString(actionRequest, "zip");
		double rate = ParamUtil.getDouble(actionRequest, "rate");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceTaxFixedRateAddressRel.class.getName(), actionRequest);

		if (commerceTaxFixedRateAddressRelId > 0) {
			_commerceTaxFixedRateAddressRelService.
				updateCommerceTaxFixedRateAddressRel(
					commerceTaxFixedRateAddressRelId, commerceCountryId,
					commerceRegionId, zip, rate);
		}
		else {
			_commerceTaxFixedRateAddressRelService.
				addCommerceTaxFixedRateAddressRel(
					commerceTaxMethodId, cpTaxCategoryId, commerceCountryId,
					commerceRegionId, zip, rate, serviceContext);
		}
	}

	@Reference
	private CommerceTaxFixedRateAddressRelService
		_commerceTaxFixedRateAddressRelService;

}
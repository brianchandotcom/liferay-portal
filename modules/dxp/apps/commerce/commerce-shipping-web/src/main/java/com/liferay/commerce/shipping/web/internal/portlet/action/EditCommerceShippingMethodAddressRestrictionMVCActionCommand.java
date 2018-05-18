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

package com.liferay.commerce.shipping.web.internal.portlet.action;

import com.liferay.commerce.admin.constants.CommerceAdminPortletKeys;
import com.liferay.commerce.exception.NoSuchAddressRestrictionException;
import com.liferay.commerce.model.CommerceAddressRestriction;
import com.liferay.commerce.service.CommerceAddressRestrictionService;
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
		"mvc.command.name=editCommerceShippingMethodAddressRestriction"
	},
	service = MVCActionCommand.class
)
public class EditCommerceShippingMethodAddressRestrictionMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommerceAddressRestrictions(ActionRequest actionRequest)
		throws PortalException {

		long[] addCommerceCountryIds = null;

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");

		if (commerceCountryId > 0) {
			addCommerceCountryIds = new long[] {commerceCountryId};
		}
		else {
			addCommerceCountryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "commerceCountryIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceAddressRestriction.class.getName(), actionRequest);

		for (long addCommerceCountryId : addCommerceCountryIds) {
			_commerceAddressRestrictionService.addCommerceAddressRestriction(
				className, classPK, addCommerceCountryId, serviceContext);
		}
	}

	protected void deleteCommerceAddressRestrictions(
			ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceAddressRestrictionIds = null;

		long commerceAddressRestrictionId = ParamUtil.getLong(
			actionRequest, "commerceAddressRestrictionId");

		if (commerceAddressRestrictionId > 0) {
			deleteCommerceAddressRestrictionIds =
				new long[] {commerceAddressRestrictionId};
		}
		else {
			deleteCommerceAddressRestrictionIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceAddressRestrictionIds"),
				0L);
		}

		for (long deleteCommerceAddressRestrictionId :
				deleteCommerceAddressRestrictionIds) {

			_commerceAddressRestrictionService.deleteCommerceAddressRestriction(
				deleteCommerceAddressRestrictionId);
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

				addCommerceAddressRestrictions(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceAddressRestrictions(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchAddressRestrictionException ||
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
	private CommerceAddressRestrictionService
		_commerceAddressRestrictionService;

}
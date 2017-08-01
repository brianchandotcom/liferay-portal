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

package com.liferay.commerce.address.web.internal.portlet.action;

import com.liferay.commerce.address.exception.NoSuchCountryException;
import com.liferay.commerce.address.model.CommerceCountry;
import com.liferay.commerce.address.service.CommerceCountryService;
import com.liferay.commerce.admin.web.constants.CommerceAdminPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceAdminPortletKeys.COMMERCE_ADMIN,
		"mvc.command.name=editCommerceCountry"
	},
	service = MVCActionCommand.class
)
public class EditCommerceCountryMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteCommerceCountries(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceCountryIds = null;

		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");

		if (commerceCountryId > 0) {
			deleteCommerceCountryIds = new long[] {commerceCountryId};
		}
		else {
			deleteCommerceCountryIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceCountryIds"),
				0L);
		}

		for (long deleteCommerceCountryId : deleteCommerceCountryIds) {
			_commerceCountryService.deleteCommerceCountry(
				deleteCommerceCountryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CommerceCountry commerceCountry = updateCommerceCountry(
					actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, commerceCountry);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceCountries(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCountryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, CommerceCountry commerceCountry)
		throws Exception {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CommerceAdminPortletKeys.COMMERCE_ADMIN,
			PortletRequest.RENDER_PHASE);

		if (commerceCountry != null) {
			portletURL.setParameter(
				"mvcRenderCommandName", "editCommerceCountry");
			portletURL.setParameter(
				"commerceCountryId",
				String.valueOf(commerceCountry.getCommerceCountryId()));
		}

		return portletURL.toString();
	}

	protected CommerceCountry updateCommerceCountry(ActionRequest actionRequest)
		throws Exception {

		long commerceCountryId = ParamUtil.getLong(
			actionRequest, "commerceCountryId");

		String name = ParamUtil.getString(actionRequest, "name");
		boolean allowsBilling = ParamUtil.getBoolean(
			actionRequest, "allowsBilling");
		boolean allowsShipping = ParamUtil.getBoolean(
			actionRequest, "allowsShipping");
		String twoLettersISOCode = ParamUtil.getString(
			actionRequest, "twoLettersISOCode");
		String threeLettersISOCode = ParamUtil.getString(
			actionRequest, "threeLettersISOCode");
		int numericISOCode = ParamUtil.getInteger(
			actionRequest, "numericISOCode");
		boolean subjectToVAT = ParamUtil.getBoolean(
			actionRequest, "subjectToVAT");
		double priority = ParamUtil.getDouble(actionRequest, "priority");
		boolean published = ParamUtil.getBoolean(actionRequest, "published");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceCountry.class.getName(), actionRequest);

		CommerceCountry commerceCountry = null;

		if (commerceCountryId <= 0) {

			// Add commerce country

			commerceCountry = _commerceCountryService.addCommerceCountry(
				name, allowsBilling, allowsShipping, twoLettersISOCode,
				threeLettersISOCode, numericISOCode, subjectToVAT, priority,
				published, serviceContext);
		}
		else {

			// Update commerce country

			commerceCountry = _commerceCountryService.updateCommerceCountry(
				commerceCountryId, name, allowsBilling, allowsShipping,
				twoLettersISOCode, threeLettersISOCode, numericISOCode,
				subjectToVAT, priority, published);
		}

		return commerceCountry;
	}

	@Reference
	private CommerceCountryService _commerceCountryService;

	@Reference
	private Portal _portal;

}
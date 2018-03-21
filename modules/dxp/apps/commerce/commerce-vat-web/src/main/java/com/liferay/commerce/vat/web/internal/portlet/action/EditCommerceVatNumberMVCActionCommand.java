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

package com.liferay.commerce.vat.web.internal.portlet.action;

import com.liferay.commerce.vat.constants.CommerceVatPortletKeys;
import com.liferay.commerce.vat.exception.NoSuchVatNumberException;
import com.liferay.commerce.vat.model.CommerceVatNumber;
import com.liferay.commerce.vat.service.CommerceVatNumberService;
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
		"javax.portlet.name=" + CommerceVatPortletKeys.COMMERCE_VAT_NUMBER,
		"mvc.command.name=editCommerceVatNumber"
	},
	service = MVCActionCommand.class
)
public class EditCommerceVatNumberMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceVatNumbers(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceVatNumberIds = null;

		long commerceVatNumberId = ParamUtil.getLong(
			actionRequest, "commerceVatNumberId");

		if (commerceVatNumberId > 0) {
			deleteCommerceVatNumberIds = new long[] {commerceVatNumberId};
		}
		else {
			deleteCommerceVatNumberIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceVatNumberIds"),
				0L);
		}

		for (long deleteCommerceVatNumberId : deleteCommerceVatNumberIds) {
			_commerceVatNumberService.deleteCommerceVatNumber(
				deleteCommerceVatNumberId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceVatNumber(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceVatNumbers(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchVatNumberException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceVatNumber(ActionRequest actionRequest)
		throws PortalException {

		long commerceVatNumberId = ParamUtil.getLong(
			actionRequest, "commerceVatNumberId");

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		String vatNumber = ParamUtil.getString(actionRequest, "vatNumber");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceVatNumber.class.getName(), actionRequest);

		if (commerceVatNumberId <= 0) {
			_commerceVatNumberService.addCommerceVatNumber(
				className, classPK, vatNumber, serviceContext);
		}
		else {
			_commerceVatNumberService.updateCommerceVatNumber(
				commerceVatNumberId, vatNumber);
		}
	}

	@Reference
	private CommerceVatNumberService _commerceVatNumberService;

}
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

package com.liferay.commerce.price.list.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.NoSuchTirePriceEntryException;
import com.liferay.commerce.model.CommerceTirePriceEntry;
import com.liferay.commerce.service.CommerceTirePriceEntryService;
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
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_PRICE_LIST,
		"mvc.command.name=editCommerceTirePriceEntry"
	},
	service = MVCActionCommand.class
)
public class EditCommerceTirePriceEntryMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceTirePriceEntries(
			long commerceTirePriceEntryId, ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceTirePriceEntryIds = null;

		if (commerceTirePriceEntryId > 0) {
			deleteCommerceTirePriceEntryIds =
				new long[] {commerceTirePriceEntryId};
		}
		else {
			deleteCommerceTirePriceEntryIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceTirePriceEntryIds"),
				0L);
		}

		for (long deleteCommerceTirePriceEntryId :
				deleteCommerceTirePriceEntryIds) {

			_commerceTirePriceEntryService.deleteCommerceTirePriceEntry(
				deleteCommerceTirePriceEntryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long commerceTirePriceEntryId = ParamUtil.getLong(
			actionRequest, "commerceTirePriceEntryId");

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceTirePriceEntry(
					commerceTirePriceEntryId, actionRequest);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceTirePriceEntries(
					commerceTirePriceEntryId, actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchTirePriceEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected CommerceTirePriceEntry updateCommerceTirePriceEntry(
			long commerceTirePriceEntryId, ActionRequest actionRequest)
		throws Exception {

		long commercePriceEntryId = ParamUtil.getLong(
			actionRequest, "commercePriceEntryId");

		double price = ParamUtil.getDouble(actionRequest, "price");
		int minQuantity = ParamUtil.getInteger(actionRequest, "minQuantity");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceTirePriceEntry.class.getName(), actionRequest);

		CommerceTirePriceEntry commerceTirePriceEntry = null;

		if (commerceTirePriceEntryId <= 0) {
			commerceTirePriceEntry =
				_commerceTirePriceEntryService.addCommerceTirePriceEntry(
					commercePriceEntryId, price, minQuantity, serviceContext);
		}
		else {
			commerceTirePriceEntry =
				_commerceTirePriceEntryService.updateCommerceTirePriceEntry(
					commerceTirePriceEntryId, price, minQuantity,
					serviceContext);
		}

		return commerceTirePriceEntry;
	}

	@Reference
	private CommerceTirePriceEntryService _commerceTirePriceEntryService;

}
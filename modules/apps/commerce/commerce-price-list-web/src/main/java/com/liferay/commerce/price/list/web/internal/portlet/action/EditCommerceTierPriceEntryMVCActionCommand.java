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

import com.liferay.commerce.price.list.constants.CommercePriceListPortletKeys;
import com.liferay.commerce.price.list.exception.DuplicateCommerceTierPriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchTierPriceEntryException;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
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

import java.math.BigDecimal;

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
		"javax.portlet.name=" + CommercePriceListPortletKeys.COMMERCE_PRICE_LIST,
		"mvc.command.name=editCommerceTierPriceEntry"
	},
	service = MVCActionCommand.class
)
public class EditCommerceTierPriceEntryMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceTierPriceEntries(
			long commerceTierPriceEntryId, ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommerceTierPriceEntryIds = null;

		if (commerceTierPriceEntryId > 0) {
			deleteCommerceTierPriceEntryIds = new long[] {
				commerceTierPriceEntryId
			};
		}
		else {
			deleteCommerceTierPriceEntryIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceTierPriceEntryIds"),
				0L);
		}

		for (long deleteCommerceTierPriceEntryId :
				deleteCommerceTierPriceEntryIds) {

			_commerceTierPriceEntryService.deleteCommerceTierPriceEntry(
				deleteCommerceTierPriceEntryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long commerceTierPriceEntryId = ParamUtil.getLong(
			actionRequest, "commerceTierPriceEntryId");

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceTierPriceEntry(
					commerceTierPriceEntryId, actionRequest);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceTierPriceEntries(
					commerceTierPriceEntryId, actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchTierPriceEntryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof DuplicateCommerceTierPriceEntryException) {
				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, e.getClass());

				String redirect = getSaveAndContinueRedirect(
					actionRequest, commerceTierPriceEntryId);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw e;
			}
		}
	}

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, long commerceTierPriceEntryId)
		throws Exception {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			actionRequest, CommercePriceListPortletKeys.COMMERCE_PRICE_LIST,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceTierPriceEntry");

		long commercePriceEntryId = ParamUtil.getLong(
			actionRequest, "commercePriceEntryId");

		if (commercePriceEntryId > 0) {
			portletURL.setParameter(
				"commercePriceEntryId", String.valueOf(commercePriceEntryId));
		}

		long commercePriceListId = ParamUtil.getLong(
			actionRequest, "commercePriceListId");

		if (commercePriceListId > 0) {
			portletURL.setParameter(
				"commercePriceListId", String.valueOf(commercePriceListId));
		}

		if (commerceTierPriceEntryId > 0) {
			portletURL.setParameter(
				"commerceTierPriceEntryId",
				String.valueOf(commerceTierPriceEntryId));
		}

		return portletURL.toString();
	}

	protected CommerceTierPriceEntry updateCommerceTierPriceEntry(
			long commerceTierPriceEntryId, ActionRequest actionRequest)
		throws Exception {

		long commercePriceEntryId = ParamUtil.getLong(
			actionRequest, "commercePriceEntryId");

		BigDecimal price = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "price", BigDecimal.ZERO);
		BigDecimal promoPrice = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "promoPrice", BigDecimal.ZERO);
		int minQuantity = ParamUtil.getInteger(actionRequest, "minQuantity");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceTierPriceEntry.class.getName(), actionRequest);

		CommerceTierPriceEntry commerceTierPriceEntry = null;

		if (commerceTierPriceEntryId <= 0) {
			commerceTierPriceEntry =
				_commerceTierPriceEntryService.addCommerceTierPriceEntry(
					commercePriceEntryId, price, promoPrice, minQuantity,
					serviceContext);
		}
		else {
			commerceTierPriceEntry =
				_commerceTierPriceEntryService.updateCommerceTierPriceEntry(
					commerceTierPriceEntryId, price, promoPrice, minQuantity,
					serviceContext);
		}

		return commerceTierPriceEntry;
	}

	@Reference
	private CommerceTierPriceEntryService _commerceTierPriceEntryService;

	@Reference
	private Portal _portal;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.portlet.action;

import com.liferay.commerce.price.list.exception.DuplicateCommerceTierPriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchTierPriceEntryException;
import com.liferay.commerce.price.list.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.list.service.CommerceTierPriceEntryService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
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
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/edit_cp_instance_commerce_tier_price_entry"
	},
	service = MVCActionCommand.class
)
public class EditCPInstanceCommerceTierPriceEntryMVCActionCommand
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
		catch (Exception exception) {
			if (exception instanceof NoSuchTierPriceEntryException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof
						DuplicateCommerceTierPriceEntryException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				throw exception;
			}
		}
	}

	protected CommerceTierPriceEntry updateCommerceTierPriceEntry(
			long commerceTierPriceEntryId, ActionRequest actionRequest)
		throws Exception {

		BigDecimal price = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "price", BigDecimal.ZERO);
		BigDecimal promoPrice = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "promoPrice", BigDecimal.ZERO);
		BigDecimal minQuantity = (BigDecimal)ParamUtil.getNumber(
			actionRequest, "minQuantity", BigDecimal.ZERO);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceTierPriceEntry.class.getName(), actionRequest);

		CommerceTierPriceEntry commerceTierPriceEntry = null;

		if (commerceTierPriceEntryId <= 0) {
			long commercePriceEntryId = ParamUtil.getLong(
				actionRequest, "commercePriceEntryId");

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

}
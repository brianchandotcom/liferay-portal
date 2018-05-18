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

package com.liferay.commerce.price.list.web.internal.portlet.action;

import com.liferay.commerce.price.list.constants.CommercePriceListPortletKeys;
import com.liferay.commerce.price.list.exception.DuplicateCommercePriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
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
	immediate = true,
	property = {
		"javax.portlet.name=" + CommercePriceListPortletKeys.COMMERCE_PRICE_LIST,
		"mvc.command.name=editCommercePriceEntry"
	},
	service = MVCActionCommand.class
)
public class EditCommercePriceEntryMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommercePriceEntries(ActionRequest actionRequest)
		throws Exception {

		long[] addCPInstanceIds = null;

		long commercePriceListId = ParamUtil.getLong(
			actionRequest, "commercePriceListId");
		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		if (cpInstanceId > 0) {
			addCPInstanceIds = new long[] {cpInstanceId};
		}
		else {
			addCPInstanceIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "cpInstanceIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePriceEntry.class.getName(), actionRequest);

		for (long addCPInstanceId : addCPInstanceIds) {
			CPInstance cpInstance = _cpInstanceService.getCPInstance(
				addCPInstanceId);

			_commercePriceEntryService.addCommercePriceEntry(
				addCPInstanceId, commercePriceListId, cpInstance.getPrice(),
				cpInstance.getPromoPrice(), serviceContext);
		}
	}

	protected void deleteCommercePriceEntries(
			long commercePriceEntryId, ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommercePriceEntryIds = null;

		if (commercePriceEntryId > 0) {
			deleteCommercePriceEntryIds = new long[] {commercePriceEntryId};
		}
		else {
			deleteCommercePriceEntryIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommercePriceEntryIds"),
				0L);
		}

		for (long deleteCommercePriceEntryId : deleteCommercePriceEntryIds) {
			_commercePriceEntryService.deleteCommercePriceEntry(
				deleteCommercePriceEntryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long commercePriceEntryId = ParamUtil.getLong(
			actionRequest, "commercePriceEntryId");

		try {
			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_MULTIPLE)) {

				addCommercePriceEntries(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommercePriceEntries(commercePriceEntryId, actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommercePriceEntry(commercePriceEntryId, actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCPInstanceException ||
				e instanceof NoSuchPriceEntryException ||
				e instanceof NoSuchPriceListException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof DuplicateCommercePriceEntryException) {
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

	protected CommercePriceEntry updateCommercePriceEntry(
			long commercePriceEntryId, ActionRequest actionRequest)
		throws Exception {

		double price = ParamUtil.getDouble(actionRequest, "price");
		double promoPrice = ParamUtil.getDouble(actionRequest, "promoPrice");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePriceEntry.class.getName(), actionRequest);

		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryService.updateCommercePriceEntry(
				commercePriceEntryId, BigDecimal.valueOf(price),
				BigDecimal.valueOf(promoPrice), serviceContext);

		return commercePriceEntry;
	}

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference
	private CPInstanceService _cpInstanceService;

}
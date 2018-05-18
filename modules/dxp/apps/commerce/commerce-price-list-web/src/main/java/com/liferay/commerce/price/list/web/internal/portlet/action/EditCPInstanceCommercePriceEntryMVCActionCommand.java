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

import com.liferay.commerce.price.list.exception.DuplicateCommercePriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceEntryException;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.constants.CPPortletKeys;
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
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editCPInstanceCommercePriceEntry"
	},
	service = MVCActionCommand.class
)
public class EditCPInstanceCommercePriceEntryMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommercePriceEntries(ActionRequest actionRequest)
		throws Exception {

		long[] addCommercePriceListIds = null;

		long commercePriceListId = ParamUtil.getLong(
			actionRequest, "commercePriceListId");
		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		CPInstance cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);

		if (commercePriceListId > 0) {
			addCommercePriceListIds = new long[] {commercePriceListId};
		}
		else {
			addCommercePriceListIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "commercePriceListIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePriceEntry.class.getName(), actionRequest);

		for (long addCommercePriceListId : addCommercePriceListIds) {
			_commercePriceEntryService.addCommercePriceEntry(
				cpInstanceId, addCommercePriceListId, cpInstance.getPrice(),
				cpInstance.getPromoPrice(), serviceContext);
		}
	}

	protected void deleteCommercePriceEntries(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommercePriceEntryIds = null;

		long commercePriceEntryId = ParamUtil.getLong(
			actionRequest, "commercePriceEntryId");

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

		try {
			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_MULTIPLE)) {

				addCommercePriceEntries(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommercePriceEntries(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommercePriceEntry(actionRequest);
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
			ActionRequest actionRequest)
		throws Exception {

		long commercePriceEntryId = ParamUtil.getLong(
			actionRequest, "commercePriceEntryId");

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
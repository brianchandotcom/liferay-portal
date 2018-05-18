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
import com.liferay.commerce.price.list.exception.CommercePriceListCurrencyException;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListUserSegmentEntryRel;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.price.list.service.CommercePriceListUserSegmentEntryRelService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
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
		"mvc.command.name=editCommercePriceList"
	},
	service = MVCActionCommand.class
)
public class EditCommercePriceListMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommercePriceLists(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCommercePriceListIds = null;

		long commercePriceListId = ParamUtil.getLong(
			actionRequest, "commercePriceListId");

		if (commercePriceListId > 0) {
			deleteCommercePriceListIds = new long[] {commercePriceListId};
		}
		else {
			deleteCommercePriceListIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommercePriceListIds"),
				0L);
		}

		for (long deleteCommercePriceListId : deleteCommercePriceListIds) {
			_commercePriceListService.deleteCommercePriceList(
				deleteCommercePriceListId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CommercePriceList commercePriceList = updateCommercePriceList(
					actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, commercePriceList);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommercePriceLists(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchPriceListException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommercePriceListCurrencyException) {
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

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, CommercePriceList commercePriceList)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletURL portletURL = PortletProviderUtil.getPortletURL(
			actionRequest, themeDisplay.getScopeGroup(),
			CommercePriceList.class.getName(), PortletProvider.Action.EDIT);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommercePriceList");
		portletURL.setParameter(
			"commercePriceListId",
			String.valueOf(commercePriceList.getCommercePriceListId()));

		return portletURL.toString();
	}

	protected CommercePriceList updateCommercePriceList(
			ActionRequest actionRequest)
		throws Exception {

		long commercePriceListId = ParamUtil.getLong(
			actionRequest, "commercePriceListId");

		long commerceCurrencyId = ParamUtil.getLong(
			actionRequest, "commerceCurrencyId");

		String name = ParamUtil.getString(actionRequest, "name");
		double priority = ParamUtil.getDouble(actionRequest, "priority");

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth");
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay");
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear");
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour");
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute");
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm");

		if (displayDateAmPm == Calendar.PM) {
			displayDateHour += 12;
		}

		int expirationDateMonth = ParamUtil.getInteger(
			actionRequest, "expirationDateMonth");
		int expirationDateDay = ParamUtil.getInteger(
			actionRequest, "expirationDateDay");
		int expirationDateYear = ParamUtil.getInteger(
			actionRequest, "expirationDateYear");
		int expirationDateHour = ParamUtil.getInteger(
			actionRequest, "expirationDateHour");
		int expirationDateMinute = ParamUtil.getInteger(
			actionRequest, "expirationDateMinute");
		int expirationDateAmPm = ParamUtil.getInteger(
			actionRequest, "expirationDateAmPm");

		if (expirationDateAmPm == Calendar.PM) {
			expirationDateHour += 12;
		}

		boolean neverExpire = ParamUtil.getBoolean(
			actionRequest, "neverExpire");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePriceList.class.getName(), actionRequest);

		CommercePriceList commercePriceList = null;

		if (commercePriceListId <= 0) {
			commercePriceList = _commercePriceListService.addCommercePriceList(
				commerceCurrencyId, name, priority, displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, serviceContext);
		}
		else {
			commercePriceList =
				_commercePriceListService.updateCommercePriceList(
					commercePriceListId, commerceCurrencyId, name, priority,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);
		}

		if (commercePriceList != null) {
			updateCommercePriceListUserSegmentEntryRels(
				actionRequest, commercePriceList);
		}

		return commercePriceList;
	}

	protected void updateCommercePriceListUserSegmentEntryRels(
			ActionRequest actionRequest, CommercePriceList commercePriceList)
		throws PortalException {

		long[] addCommerceUserSegmentEntryIds = ParamUtil.getLongValues(
			actionRequest, "addCommerceUserSegmentEntryIds");
		long[] deleteCommercePriceListUserSegmentEntryRelIds =
			ParamUtil.getLongValues(
				actionRequest, "deleteCommercePriceListUserSegmentEntryRelIds");

		if (deleteCommercePriceListUserSegmentEntryRelIds.length > 0) {
			for (long deleteCommercePriceListUserSegmentEntryRelId :
					deleteCommercePriceListUserSegmentEntryRelIds) {

				_commercePriceListUserSegmentEntryRelService.
					deleteCommercePriceListUserSegmentEntryRel(
						deleteCommercePriceListUserSegmentEntryRelId);
			}
		}

		if (addCommerceUserSegmentEntryIds.length > 0) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommercePriceListUserSegmentEntryRel.class.getName(),
				actionRequest);

			for (long addCommerceUserSegmentEntryId :
					addCommerceUserSegmentEntryIds) {

				CommercePriceListUserSegmentEntryRel
					commercePriceListUserSegmentEntryRel =
						_commercePriceListUserSegmentEntryRelService.
							fetchCommercePriceListUserSegmentEntryRel(
								commercePriceList.getCommercePriceListId(),
								addCommerceUserSegmentEntryId);

				if (commercePriceListUserSegmentEntryRel == null) {
					_commercePriceListUserSegmentEntryRelService.
						addCommercePriceListUserSegmentEntryRel(
							commercePriceList.getCommercePriceListId(),
							addCommerceUserSegmentEntryId, 0, serviceContext);
				}
			}
		}
	}

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private CommercePriceListUserSegmentEntryRelService
		_commercePriceListUserSegmentEntryRelService;

	@Reference
	private Portal _portal;

}
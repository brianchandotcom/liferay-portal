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

package com.liferay.commerce.pricing.web.internal.portlet.action;

import com.liferay.commerce.price.list.exception.CommercePriceListCurrencyException;
import com.liferay.commerce.price.list.exception.CommercePriceListParentPriceListGroupIdException;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelService;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.product.exception.NoSuchCatalogException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Calendar;
import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePricingPortletKeys.COMMERCE_PRICE_LIST,
		"javax.portlet.name=" + CommercePricingPortletKeys.COMMERCE_PROMOTION,
		"mvc.command.name=/commerce_price_list/edit_commerce_price_list"
	},
	service = MVCActionCommand.class
)
public class EditCommercePriceListMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				CommercePriceList commercePriceList = _updateCommercePriceList(
					actionRequest);

				String redirect = getSaveAndContinueRedirect(
					actionRequest, commercePriceList);

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteCommercePriceLists(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchPriceListException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof CommercePriceListCurrencyException ||
					 exception instanceof
						 CommercePriceListParentPriceListGroupIdException ||
					 exception instanceof NoSuchCatalogException) {

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

	protected String getSaveAndContinueRedirect(
			ActionRequest actionRequest, CommercePriceList commercePriceList)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		String portletName = portletDisplay.getPortletName();

		PortletURL portletURL = null;

		if (portletName.equals(
				CommercePricingPortletKeys.COMMERCE_PRICE_LIST)) {

			portletURL = PortletProviderUtil.getPortletURL(
				actionRequest, themeDisplay.getScopeGroup(),
				CommercePriceList.class.getName(), PortletProvider.Action.EDIT);
		}
		else if (portletName.equals(
					CommercePricingPortletKeys.COMMERCE_PROMOTION)) {

			portletURL = PortletProviderUtil.getPortletURL(
				actionRequest, themeDisplay.getScopeGroup(),
				CommercePriceList.class.getName(), PortletProvider.Action.VIEW);
		}
		else {
			return ParamUtil.getString(actionRequest, "redirect");
		}

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_price_list/edit_commerce_price_list");
		portletURL.setParameter(
			"commercePriceListId",
			String.valueOf(commercePriceList.getCommercePriceListId()));

		return portletURL.toString();
	}

	private void _deleteCommercePriceLists(ActionRequest actionRequest)
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

	private CommercePriceList _updateCommercePriceList(
			ActionRequest actionRequest)
		throws Exception {

		long commercePriceListId = ParamUtil.getLong(
			actionRequest, "commercePriceListId");

		long commerceCurrencyId = ParamUtil.getLong(
			actionRequest, "commerceCurrencyId");
		boolean netPrice = ParamUtil.getBoolean(
			actionRequest, "netPrice", true);
		long parentCommercePriceListId = ParamUtil.getLong(
			actionRequest, "parentCommercePriceListId");

		String name = ParamUtil.getString(actionRequest, "name");
		double priority = ParamUtil.getDouble(actionRequest, "priority");

		Date date = new Date();

		Calendar calendar = CalendarFactoryUtil.getCalendar(date.getTime());

		int displayDateMonth = ParamUtil.getInteger(
			actionRequest, "displayDateMonth", calendar.get(Calendar.MONTH));
		int displayDateDay = ParamUtil.getInteger(
			actionRequest, "displayDateDay",
			calendar.get(Calendar.DAY_OF_MONTH));
		int displayDateYear = ParamUtil.getInteger(
			actionRequest, "displayDateYear", calendar.get(Calendar.YEAR));
		int displayDateHour = ParamUtil.getInteger(
			actionRequest, "displayDateHour", calendar.get(Calendar.HOUR));
		int displayDateMinute = ParamUtil.getInteger(
			actionRequest, "displayDateMinute", calendar.get(Calendar.MINUTE));
		int displayDateAmPm = ParamUtil.getInteger(
			actionRequest, "displayDateAmPm", calendar.get(Calendar.AM_PM));

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
			actionRequest, "neverExpire", true);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommercePriceList.class.getName(), actionRequest);

		CommercePriceList commercePriceList;

		if (commercePriceListId <= 0) {
			long commerceCatalogGroupId = ParamUtil.getLong(
				actionRequest, "commerceCatalogGroupId");
			String type = ParamUtil.getString(actionRequest, "type");

			commercePriceList = _commercePriceListService.addCommercePriceList(
				null, commerceCatalogGroupId, commerceCurrencyId, netPrice,
				type, parentCommercePriceListId, false, name, priority,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, serviceContext);
		}
		else {
			commercePriceList =
				_commercePriceListService.updateCommercePriceList(
					commercePriceListId, commerceCurrencyId, netPrice,
					parentCommercePriceListId, name, priority, displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, serviceContext);
		}

		return commercePriceList;
	}

	private void _updateCommercePriceListAccountRels(
			ActionRequest actionRequest, CommercePriceList commercePriceList)
		throws PortalException {

		long[] deleteCommercePriceListAccountRelIds = ParamUtil.getLongValues(
			actionRequest, "deleteCommercePriceListAccountRelIds");

		if (deleteCommercePriceListAccountRelIds.length > 0) {
			for (long deleteCommercePriceListAccountRelId :
					deleteCommercePriceListAccountRelIds) {

				_commercePriceListAccountRelService.
					deleteCommercePriceListAccountRel(
						deleteCommercePriceListAccountRelId);
			}
		}

		long[] addCommerceAccountIds = ParamUtil.getLongValues(
			actionRequest, "addCommerceAccountIds");

		if (addCommerceAccountIds.length > 0) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommercePriceListAccountRel.class.getName(), actionRequest);

			for (long addCommerceAccountId : addCommerceAccountIds) {
				CommercePriceListAccountRel commercePriceListAccountRel =
					_commercePriceListAccountRelService.
						fetchCommercePriceListAccountRel(
							commercePriceList.getCommercePriceListId(),
							addCommerceAccountId);

				if (commercePriceListAccountRel == null) {
					_commercePriceListAccountRelService.
						addCommercePriceListAccountRel(
							commercePriceList.getCommercePriceListId(),
							addCommerceAccountId, 0, serviceContext);
				}
			}
		}
	}

	private void _updateCommercePriceListCommerceAccountGroupRels(
			ActionRequest actionRequest, CommercePriceList commercePriceList)
		throws PortalException {

		long[] addCommerceAccountGroupIds = ParamUtil.getLongValues(
			actionRequest, "addCommerceAccountGroupIds");

		long[] deleteCommercePriceListCommerceAccountGroupRelIds =
			ParamUtil.getLongValues(
				actionRequest,
				"deleteCommercePriceListCommerceAccountGroupRelIds");

		if (deleteCommercePriceListCommerceAccountGroupRelIds.length > 0) {
			for (long deleteCommercePriceListCommerceAccountGroupRelId :
					deleteCommercePriceListCommerceAccountGroupRelIds) {

				_commercePriceListCommerceAccountGroupRelService.
					deleteCommercePriceListCommerceAccountGroupRel(
						deleteCommercePriceListCommerceAccountGroupRelId);
			}
		}

		if (addCommerceAccountGroupIds.length > 0) {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommercePriceListCommerceAccountGroupRel.class.getName(),
				actionRequest);

			for (long addCommerceAccountGroupId : addCommerceAccountGroupIds) {
				CommercePriceListCommerceAccountGroupRel
					commercePriceListAccountGroupEntryRel =
						_commercePriceListCommerceAccountGroupRelService.
							fetchCommercePriceListCommerceAccountGroupRel(
								commercePriceList.getCommercePriceListId(),
								addCommerceAccountGroupId);

				if (commercePriceListAccountGroupEntryRel == null) {
					_commercePriceListCommerceAccountGroupRelService.
						addCommercePriceListCommerceAccountGroupRel(
							commercePriceList.getCommercePriceListId(),
							addCommerceAccountGroupId, 0, serviceContext);
				}
			}
		}
	}

	private void _updateQualifiers(ActionRequest actionRequest)
		throws PortalException {

		long commercePriceListId = ParamUtil.getLong(
			actionRequest, "commercePriceListId");

		CommercePriceList commercePriceList =
			_commercePriceListService.getCommercePriceList(commercePriceListId);

		_updateCommercePriceListAccountRels(actionRequest, commercePriceList);
		_updateCommercePriceListCommerceAccountGroupRels(
			actionRequest, commercePriceList);
	}

	@Reference
	private CommercePriceListAccountRelService
		_commercePriceListAccountRelService;

	@Reference
	private CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

}
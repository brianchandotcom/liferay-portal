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

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommercePriceEntryDisplayContext
	extends BaseCommercePriceListDisplayContext {

	public CommercePriceEntryDisplayContext(
		CommerceCatalogService commerceCatalogService,
		CommercePriceEntryService commercePriceEntryService,
		ModelResourcePermission<CommercePriceList>
			commercePriceListModelResourcePermission,
		CommercePriceListService commercePriceListService,
		HttpServletRequest httpServletRequest) {

		super(
			commerceCatalogService, commercePriceListModelResourcePermission,
			commercePriceListService, httpServletRequest);

		_commercePriceEntryService = commercePriceEntryService;
	}

	public String getAddCommerceTierPriceEntryRenderURL() throws Exception {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "addCommerceTierPriceEntry");
		portletURL.setParameter(
			"commercePriceEntryId", String.valueOf(getCommercePriceEntryId()));
		portletURL.setParameter(
			"commercePriceListId", String.valueOf(getCommercePriceListId()));
		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public String getBasePrice() throws PortalException {
		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		CommercePriceList commercePriceList = getCommercePriceList();

		CommercePriceEntry instanceBaseCommercePriceEntry =
			_commercePriceEntryService.getInstanceBaseCommercePriceEntry(
				commercePriceEntry.getCPInstanceUuid(),
				commercePriceList.getType());

		if (instanceBaseCommercePriceEntry == null) {
			return StringPool.DASH;
		}

		CommerceMoney priceMoney = instanceBaseCommercePriceEntry.getPriceMoney(
			commercePriceList.getCommerceCurrencyId());

		return priceMoney.format(commercePricingRequestHelper.getLocale());
	}

	public CommercePriceEntry getCommercePriceEntry() throws PortalException {
		if (_commercePriceEntry != null) {
			return _commercePriceEntry;
		}

		long commercePriceEntryId = ParamUtil.getLong(
			commercePricingRequestHelper.getRequest(), "commercePriceEntryId");

		if (commercePriceEntryId > 0) {
			_commercePriceEntry =
				_commercePriceEntryService.getCommercePriceEntry(
					commercePriceEntryId);
		}

		return _commercePriceEntry;
	}

	public long getCommercePriceEntryId() throws PortalException {
		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry == null) {
			return 0;
		}

		return commercePriceEntry.getCommercePriceEntryId();
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (hasPermission(getCommercePriceListId(), ActionKeys.UPDATE)) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(
						getAddCommerceTierPriceEntryRenderURL());
					dropdownItem.setLabel(
						LanguageUtil.get(
							httpServletRequest, "add-new-price-tier"));
					dropdownItem.setTarget("modal-lg");
				});
		}

		return creationMenu;
	}

	public List<ClayDataSetActionDropdownItem>
			getPriceEntriesClayDataSetActionDropdownItems()
		throws PortalException {

		List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems =
			new ArrayList<>();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommercePriceEntry");
		portletURL.setParameter(
			"redirect", commercePricingRequestHelper.getCurrentURL());
		portletURL.setParameter(
			"commercePriceListId", String.valueOf(getCommercePriceListId()));
		portletURL.setParameter("commercePriceEntryId", "{id}");

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException, windowStateException);
		}

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				portletURL.toString(), "pencil", "edit",
				LanguageUtil.get(httpServletRequest, "edit"), "get", null,
				"sidePanel"));

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				null, "trash", "remove",
				LanguageUtil.get(httpServletRequest, "remove"), "delete",
				"delete", "headless"));

		return clayDataSetActionDropdownItems;
	}

	public String getPriceEntryApiURL() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/price-lists/" +
			getCommercePriceListId() +
				"/price-entries?nestedFields=product,sku";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceEntryDisplayContext.class);

	private CommercePriceEntry _commercePriceEntry;
	private final CommercePriceEntryService _commercePriceEntryService;

}
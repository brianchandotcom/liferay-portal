/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.currency.model.CommerceMoney;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceEntryLocalService;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
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
		CommercePriceEntryLocalService commercePriceEntryLocalService,
		CommercePriceEntryService commercePriceEntryService,
		ModelResourcePermission<CommercePriceList>
			commercePriceListModelResourcePermission,
		CommercePriceListService commercePriceListService,
		HttpServletRequest httpServletRequest) {

		super(
			commerceCatalogService, commercePriceListModelResourcePermission,
			commercePriceListService, httpServletRequest);

		_commercePriceEntryLocalService = commercePriceEntryLocalService;
		_commercePriceEntryService = commercePriceEntryService;
	}

	public String getAddCommerceTierPriceEntryRenderURL() throws Exception {
		return PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			"/commerce_price_list/add_commerce_tier_price_entry"
		).setParameter(
			"commercePriceEntryId", getCommercePriceEntryId()
		).setParameter(
			"commercePriceListId", getCommercePriceListId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public String getBasePrice() throws PortalException {
		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		CommercePriceList commercePriceList = getCommercePriceList();

		CommercePriceEntry instanceBaseCommercePriceEntry =
			_commercePriceEntryLocalService.getInstanceBaseCommercePriceEntry(
				commercePriceEntry.getCPInstanceUuid(),
				commercePriceList.getType(),
				commercePriceEntry.getUnitOfMeasureKey());

		if (instanceBaseCommercePriceEntry == null) {
			return StringPool.DASH;
		}

		CommerceMoney priceCommerceMoney =
			instanceBaseCommercePriceEntry.getPriceCommerceMoney(
				commercePriceList.getCommerceCurrencyId());

		return priceCommerceMoney.format(
			commercePricingRequestHelper.getLocale());
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

	public List<FDSActionDropdownItem> getPriceEntriesFDSActionDropdownItems()
		throws PortalException {

		List<FDSActionDropdownItem> fdsActionDropdownItems = new ArrayList<>();

		PortletURL portletURL = PortletURLBuilder.createRenderURL(
			liferayPortletResponse
		).setMVCRenderCommandName(
			"/commerce_price_list/edit_commerce_price_entry"
		).setRedirect(
			commercePricingRequestHelper.getCurrentURL()
		).setParameter(
			"commercePriceEntryId", "{priceEntryId}"
		).setParameter(
			"commercePriceListId", getCommercePriceListId()
		).buildPortletURL();

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException);
		}

		fdsActionDropdownItems.add(
			new FDSActionDropdownItem(
				portletURL.toString(), "pencil", "edit",
				LanguageUtil.get(httpServletRequest, "edit"), "get", null,
				"sidePanel"));

		fdsActionDropdownItems.add(
			new FDSActionDropdownItem(
				null, "trash", "remove",
				LanguageUtil.get(httpServletRequest, "remove"), "delete",
				"delete", "headless"));

		return fdsActionDropdownItems;
	}

	public String getPriceEntryApiURL() throws PortalException {
		return "/o/headless-commerce-admin-pricing/v2.0/price-lists/" +
			getCommercePriceListId() +
				"/price-entries?nestedFields=product,sku";
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceEntryDisplayContext.class);

	private CommercePriceEntry _commercePriceEntry;
	private final CommercePriceEntryLocalService
		_commercePriceEntryLocalService;
	private final CommercePriceEntryService _commercePriceEntryService;

}
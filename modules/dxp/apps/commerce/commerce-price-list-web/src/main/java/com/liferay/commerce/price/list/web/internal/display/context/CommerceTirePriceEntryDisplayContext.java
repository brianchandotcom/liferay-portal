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

package com.liferay.commerce.price.list.web.internal.display.context;

import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommerceTirePriceEntry;
import com.liferay.commerce.price.list.web.internal.portlet.action.ActionHelper;
import com.liferay.commerce.price.list.web.internal.util.CommercePriceListPortletUtil;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CommerceTirePriceEntryService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTirePriceEntryDisplayContext
	extends BaseCommercePriceListDisplayContext<CommerceTirePriceEntry> {

	public CommerceTirePriceEntryDisplayContext(
		ActionHelper actionHelper,
		CommerceTirePriceEntryService commerceTirePriceEntryService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(actionHelper, renderRequest, renderResponse);

		_commerceTirePriceEntryService = commerceTirePriceEntryService;
	}

	public CommercePriceEntry getCommercePriceEntry() throws PortalException {
		return actionHelper.getCommercePriceEntry(renderRequest);
	}

	public long getCommercePriceEntryId() throws PortalException {
		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry == null) {
			return 0;
		}

		return commercePriceEntry.getCommercePriceEntryId();
	}

	public CommerceTirePriceEntry getCommerceTirePriceEntry()
		throws PortalException {

		if (_commerceTirePriceEntry != null) {
			return _commerceTirePriceEntry;
		}

		_commerceTirePriceEntry = actionHelper.getCommerceTirePriceEntry(
			renderRequest);

		return _commerceTirePriceEntry;
	}

	public long getCommerceTirePriceEntryId() throws PortalException {
		CommerceTirePriceEntry commerceTirePriceEntry =
			getCommerceTirePriceEntry();

		if (commerceTirePriceEntry == null) {
			return 0;
		}

		return commerceTirePriceEntry.getCommerceTirePriceEntryId();
	}

	public String getContextTitle() throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		StringBuilder sb = new StringBuilder();

		CommercePriceList commercePriceList = getCommercePriceList();

		if (commercePriceList != null) {
			sb.append(commercePriceList.getName());
		}

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry != null) {
			CPInstance cpInstance = commercePriceEntry.getCPInstance();

			if (cpInstance != null) {
				CPDefinition cpDefinition = cpInstance.getCPDefinition();

				if (cpDefinition != null) {
					sb.append(" - ");
					sb.append(
						cpDefinition.getTitle(themeDisplay.getLanguageId()));
					sb.append(" - ");
					sb.append(cpInstance.getSku());
				}
			}
		}

		CommerceTirePriceEntry commerceTirePriceEntry =
			getCommerceTirePriceEntry();

		String contextTitle = sb.toString();

		if (commerceTirePriceEntry == null) {
			contextTitle = LanguageUtil.format(
				themeDisplay.getRequest(), "add-tire-price-entry-to-x",
				sb.toString());
		}

		return contextTitle;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceTirePriceEntries");

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry != null) {
			portletURL.setParameter(
				"commercePriceEntryId",
				String.valueOf(getCommercePriceEntryId()));
		}

		return portletURL;
	}

	@Override
	public SearchContainer<CommerceTirePriceEntry> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, "there-are-no-price-entries");

		OrderByComparator<CommerceTirePriceEntry> orderByComparator =
			CommercePriceListPortletUtil.getCommerceTypePriceOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CommercePriceListPortletUtil.getCommercePriceEntrySort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CommerceTirePriceEntry>
				commercePriceListBaseModelSearchResult =
					_commerceTirePriceEntryService.
						searchCommerceTirePriceEntries(
							themeDisplay.getCompanyId(),
							themeDisplay.getScopeGroupId(),
							getCommercePriceEntryId(), getKeywords(),
							searchContainer.getStart(),
							searchContainer.getEnd(), sort);

			searchContainer.setTotal(
				commercePriceListBaseModelSearchResult.getLength());
			searchContainer.setResults(
				commercePriceListBaseModelSearchResult.getBaseModels());
		}
		else {
			int total =
				_commerceTirePriceEntryService.getCommerceTirePriceEntriesCount(
					getCommercePriceEntryId());

			searchContainer.setTotal(total);

			List<CommerceTirePriceEntry> results =
				_commerceTirePriceEntryService.getCommerceTirePriceEntries(
					getCommercePriceEntryId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	public boolean hasCustomAttributesAvailable() throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return CustomAttributesUtil.hasCustomAttributes(
			themeDisplay.getCompanyId(), CommerceTirePriceEntry.class.getName(),
			getCommerceTirePriceEntryId(), null);
	}

	private CommerceTirePriceEntry _commerceTirePriceEntry;
	private final CommerceTirePriceEntryService _commerceTirePriceEntryService;

}
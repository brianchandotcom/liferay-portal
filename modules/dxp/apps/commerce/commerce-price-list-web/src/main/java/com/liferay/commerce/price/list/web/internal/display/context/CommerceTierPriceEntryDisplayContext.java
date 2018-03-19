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
import com.liferay.commerce.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.price.list.web.display.context.BaseCommercePriceListDisplayContext;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CommerceTierPriceEntryService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTierPriceEntryDisplayContext
	extends BaseCommercePriceListDisplayContext<CommerceTierPriceEntry> {

	public CommerceTierPriceEntryDisplayContext(
		CommercePriceFormatter commercePriceFormatter,
		CommercePriceListActionHelper commercePriceListActionHelper,
		CommerceTierPriceEntryService commerceTierPriceEntryService,
		HttpServletRequest httpServletRequest) {

		super(commercePriceListActionHelper, httpServletRequest);

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);

		_commercePriceFormatter = commercePriceFormatter;
		_commerceTierPriceEntryService = commerceTierPriceEntryService;
	}

	public CommercePriceEntry getCommercePriceEntry() throws PortalException {
		return commercePriceListActionHelper.getCommercePriceEntry(
			_cpRequestHelper.getRenderRequest());
	}

	public long getCommercePriceEntryId() throws PortalException {
		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry == null) {
			return 0;
		}

		return commercePriceEntry.getCommercePriceEntryId();
	}

	public CommerceTierPriceEntry getCommerceTierPriceEntry()
		throws PortalException {

		if (_commerceTierPriceEntry != null) {
			return _commerceTierPriceEntry;
		}

		_commerceTierPriceEntry =
			commercePriceListActionHelper.getCommerceTierPriceEntry(
				_cpRequestHelper.getRenderRequest());

		return _commerceTierPriceEntry;
	}

	public long getCommerceTierPriceEntryId() throws PortalException {
		CommerceTierPriceEntry commerceTierPriceEntry =
			getCommerceTierPriceEntry();

		if (commerceTierPriceEntry == null) {
			return 0;
		}

		return commerceTierPriceEntry.getCommerceTierPriceEntryId();
	}

	public String getCommerceTierPriceEntryPrice(
			CommerceTierPriceEntry commerceTierPriceEntry)
		throws PortalException {

		CommercePriceList commercePriceList = getCommercePriceList();

		return _commercePriceFormatter.format(
			commercePriceList.getCommerceCurrency(),
			commerceTierPriceEntry.getPrice());
	}

	public String getContextTitle() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(5);

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

		CommerceTierPriceEntry commerceTierPriceEntry =
			getCommerceTierPriceEntry();

		String contextTitle = sb.toString();

		if (commerceTierPriceEntry == null) {
			contextTitle = LanguageUtil.format(
				themeDisplay.getRequest(), "add-tier-price-entry-to-x",
				contextTitle);
		}

		return contextTitle;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCommerceTierPriceEntries");

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry != null) {
			portletURL.setParameter(
				"commercePriceEntryId",
				String.valueOf(getCommercePriceEntryId()));
		}

		return portletURL;
	}

	@Override
	public SearchContainer<CommerceTierPriceEntry> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null,
			"there-are-no-tier-price-entries");

		OrderByComparator<CommerceTierPriceEntry> orderByComparator =
			CommerceUtil.getCommerceTierPriceEntryOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort = CommerceUtil.getCommercePriceEntrySort(
				getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CommerceTierPriceEntry>
				commercePriceListBaseModelSearchResult =
					_commerceTierPriceEntryService.
						searchCommerceTierPriceEntries(
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
				_commerceTierPriceEntryService.getCommerceTierPriceEntriesCount(
					getCommercePriceEntryId());

			searchContainer.setTotal(total);

			List<CommerceTierPriceEntry> results =
				_commerceTierPriceEntryService.getCommerceTierPriceEntries(
					getCommercePriceEntryId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	public boolean hasCustomAttributes() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return CustomAttributesUtil.hasCustomAttributes(
			themeDisplay.getCompanyId(), CommerceTierPriceEntry.class.getName(),
			getCommerceTierPriceEntryId(), null);
	}

	private final CommercePriceFormatter _commercePriceFormatter;
	private CommerceTierPriceEntry _commerceTierPriceEntry;
	private final CommerceTierPriceEntryService _commerceTierPriceEntryService;
	private final CPRequestHelper _cpRequestHelper;

}
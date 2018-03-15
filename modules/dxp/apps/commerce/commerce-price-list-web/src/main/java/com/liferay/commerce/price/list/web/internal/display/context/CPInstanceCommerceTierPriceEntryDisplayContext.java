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

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.model.CommercePriceEntry;
import com.liferay.commerce.model.CommercePriceList;
import com.liferay.commerce.model.CommerceTierPriceEntry;
import com.liferay.commerce.price.CommercePriceFormatter;
import com.liferay.commerce.price.list.web.portlet.action.CommercePriceListActionHelper;
import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPInstanceScreenNavigationConstants;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.service.CommerceTierPriceEntryService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public class CPInstanceCommerceTierPriceEntryDisplayContext
	extends
		BaseCPDefinitionsSearchContainerDisplayContext<CommerceTierPriceEntry> {

	public CPInstanceCommerceTierPriceEntryDisplayContext(
		ActionHelper actionHelper,
		CommercePriceFormatter commercePriceFormatter,
		CommercePriceListActionHelper commercePriceListActionHelper,
		CommerceTierPriceEntryService commercePriceEntryService,
		HttpServletRequest httpServletRequest) {

		super(
			actionHelper, httpServletRequest,
			CommerceTierPriceEntry.class.getSimpleName());

		_commercePriceFormatter = commercePriceFormatter;
		_commercePriceListActionHelper = commercePriceListActionHelper;
		_commerceTierPriceEntryService = commercePriceEntryService;

		setDefaultOrderByCol("create-date");
		setDefaultOrderByType("desc");
	}

	public CommercePriceEntry getCommercePriceEntry() {
		return _commercePriceListActionHelper.getCommercePriceEntry(
			cpRequestHelper.getRenderRequest());
	}

	public long getCommercePriceEntryId() {
		long commercePriceEntryId = 0;

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry != null) {
			commercePriceEntryId = commercePriceEntry.getCommercePriceEntryId();
		}

		return commercePriceEntryId;
	}

	public CommerceTierPriceEntry getCommerceTierPriceEntry() {
		if (_commerceTierPriceEntry != null) {
			return _commerceTierPriceEntry;
		}

		_commerceTierPriceEntry =
			_commercePriceListActionHelper.getCommerceTierPriceEntry(
				cpRequestHelper.getRenderRequest());

		return _commerceTierPriceEntry;
	}

	public long getCommerceTierPriceEntryId() {
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

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		return _commercePriceFormatter.format(
			getCommercePriceListCurrency(
				commercePriceEntry.getCommercePriceList()),
			commerceTierPriceEntry.getPrice());
	}

	public String getContextTitle() throws PortalException {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		StringBundler sb = new StringBundler(5);

		CommercePriceEntry commercePriceEntry = getCommercePriceEntry();

		if (commercePriceEntry != null) {
			CPInstance cpInstance = commercePriceEntry.getCPInstance();

			if (cpInstance != null) {
				CPDefinition cpDefinition = cpInstance.getCPDefinition();

				if (cpDefinition != null) {
					sb.append(
						cpDefinition.getTitle(themeDisplay.getLanguageId()));
					sb.append(" - ");
					sb.append(cpInstance.getSku());

					CommercePriceList commercePriceList =
						commercePriceEntry.getCommercePriceList();

					if (commercePriceList != null) {
						sb.append(" - ");
						sb.append(commercePriceList.getName());
					}
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

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance != null) {
			return _cpInstance;
		}

		_cpInstance = actionHelper.getCPInstance(
			cpRequestHelper.getRenderRequest());

		return _cpInstance;
	}

	public long getCPInstanceId() throws PortalException {
		long cpInstanceId = 0;

		CPInstance cpInstance = getCPInstance();

		if (cpInstance != null) {
			cpInstanceId = cpInstance.getCPInstanceId();
		}

		return cpInstanceId;
	}

	public PortletURL getInstancePriceListURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter("mvcRenderCommandName", "editProductInstance");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(getCPInstanceId()));
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPInstanceScreenNavigationConstants.CATEGORY_KEY_DETAILS);
		portletURL.setParameter(
			"screenNavigationEntryKey", getScreenNavigationEntryKey());

		return portletURL;
	}

	public PortletURL getInstanceTierPriceEntriesURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCPInstanceCommerceTierPriceEntries");
		portletURL.setParameter(
			"commercePriceEntryId", String.valueOf(getCommercePriceEntryId()));
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(getCPInstanceId()));

		String instancePriceEntryToolbarItem = ParamUtil.getString(
			httpServletRequest, "instancePriceEntryToolbarItem",
			"view-tier-price-entries");

		portletURL.setParameter(
			"instancePriceEntryToolbarItem", instancePriceEntryToolbarItem);

		return portletURL;
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewCPInstanceCommerceTierPriceEntries");
		portletURL.setParameter(
			"commercePriceEntryId", String.valueOf(getCommercePriceEntryId()));
		portletURL.setParameter(
			"cpInstanceId", String.valueOf(getCPInstanceId()));

		return portletURL;
	}

	public PortletURL getProductSkusURL() throws PortalException {
		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");
		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(getCPDefinitionId()));
		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS);

		return portletURL;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return "price-lists";
	}

	public String getScreenNavigationEntryKey() {
		return "price-lists";
	}

	@Override
	public SearchContainer<CommerceTierPriceEntry> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			cpRequestHelper.getRenderRequest(), getPortletURL(), null,
			"there-are-no-tier-price-entries");

		OrderByComparator<CommerceTierPriceEntry> orderByComparator =
			CommerceUtil.getCommerceTierPriceEntryOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceTierPriceEntryService.getCommerceTierPriceEntriesCount(
				getCommercePriceEntryId());

		searchContainer.setTotal(total);

		List<CommerceTierPriceEntry> results =
			_commerceTierPriceEntryService.getCommerceTierPriceEntries(
				getCommercePriceEntryId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

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

	protected CommerceCurrency getCommercePriceListCurrency(
			CommercePriceList commercePriceList)
		throws PortalException {

		if (_commerceCurrency != null) {
			return _commerceCurrency;
		}

		return commercePriceList.getCommerceCurrency();
	}

	private CommerceCurrency _commerceCurrency;
	private final CommercePriceFormatter _commercePriceFormatter;
	private final CommercePriceListActionHelper _commercePriceListActionHelper;
	private CommerceTierPriceEntry _commerceTierPriceEntry;
	private final CommerceTierPriceEntryService _commerceTierPriceEntryService;
	private CPInstance _cpInstance;

}
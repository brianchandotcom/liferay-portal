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

package com.liferay.commerce.tax.engine.fixed.web.internal.display.context;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.model.CommerceTaxCategory;
import com.liferay.commerce.model.CommerceTaxMethod;
import com.liferay.commerce.service.CommerceTaxMethodService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRate;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateService;
import com.liferay.commerce.tax.engine.fixed.util.CommerceTaxEngineFixedUtil;
import com.liferay.commerce.tax.engine.fixed.web.internal.FixedCommerceTaxEngine;
import com.liferay.commerce.tax.engine.fixed.web.internal.servlet.taglib.ui.CommerceTaxMethodFixedRatesScreenNavigationEntry;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRatesDisplayContext
	extends BaseCommerceTaxFixedRateDisplayContext<CommerceTaxFixedRate> {

	public CommerceTaxFixedRatesDisplayContext(
		CommerceCurrencyService commerceCurrencyService,
		CommerceTaxFixedRateService commerceTaxFixedRateService,
		CommerceTaxMethodService commerceTaxMethodService,
		RenderRequest renderRequest, RenderResponse renderResponse) {

		super(
			commerceCurrencyService, commerceTaxMethodService, renderRequest,
			renderResponse);

		_commerceTaxFixedRateService = commerceTaxFixedRateService;
	}

	public String getAddTaxRateURL(
		String functionName, boolean isNew, String url) {

		StringBundler sb = new StringBundler(11);

		sb.append("javascript:");
		sb.append(renderResponse.getNamespace());
		sb.append(functionName);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(isNew);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(StringPool.APOSTROPHE);
		sb.append(HtmlUtil.escapeJS(url));
		sb.append(StringPool.APOSTROPHE);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
	}

	public List<CommerceTaxCategory> getAvailableCommerceTaxCategories()
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _commerceTaxFixedRateService.getAvailableCommerceTaxCategories(
			themeDisplay.getScopeGroupId());
	}

	public CommerceTaxFixedRate getCommerceTaxFixedRate() {
		long commerceTaxFixedRateId = ParamUtil.getLong(
			renderRequest, "commerceTaxFixedRateId");

		return _commerceTaxFixedRateService.fetchCommerceTaxFixedRate(
			commerceTaxFixedRateId);
	}

	@Override
	public String getScreenNavigationEntryKey() {
		return CommerceTaxMethodFixedRatesScreenNavigationEntry.ENTRY_KEY;
	}

	@Override
	public SearchContainer<CommerceTaxFixedRate> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			renderRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("there-are-no-tax-rates");

		OrderByComparator<CommerceTaxFixedRate> orderByComparator =
			CommerceTaxEngineFixedUtil.getCommerceTaxFixedRateOrderByComparator(
				getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total = _commerceTaxFixedRateService.getCommerceTaxFixedRatesCount(
			getCommerceTaxMethodId());

		searchContainer.setTotal(total);

		List<CommerceTaxFixedRate> results =
			_commerceTaxFixedRateService.getCommerceTaxFixedRates(
				getCommerceTaxMethodId(), searchContainer.getStart(),
				searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	public String getTaglibOnClick(String functionName) {
		StringBundler sb = new StringBundler(9);

		sb.append("javascript:");
		sb.append(renderResponse.getNamespace());
		sb.append(functionName);
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(StringPool.APOSTROPHE);

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"commerceAdminModuleKey",
			CommerceConstants.TAXES_COMMERCE_ADMIN_MODULE_KEY);
		portletURL.setParameter("screenNavigationEntryKey", "tax-categories");

		sb.append(HtmlUtil.escapeJS(portletURL.toString()));

		sb.append(StringPool.APOSTROPHE);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
	}

	public boolean isFixed() throws PortalException {
		CommerceTaxMethod commerceTaxMethod = getCommerceTaxMethod();

		String engineKey = commerceTaxMethod.getEngineKey();

		if (engineKey.equals(FixedCommerceTaxEngine.KEY)) {
			return true;
		}

		return false;
	}

	private final CommerceTaxFixedRateService _commerceTaxFixedRateService;

}
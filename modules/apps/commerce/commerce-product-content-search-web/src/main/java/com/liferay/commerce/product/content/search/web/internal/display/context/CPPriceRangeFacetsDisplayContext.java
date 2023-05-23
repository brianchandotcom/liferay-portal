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

package com.liferay.commerce.product.content.search.web.internal.display.context;

import com.liferay.commerce.constants.CommerceWebKeys;
import com.liferay.commerce.context.CommerceContext;
import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.product.content.search.web.internal.configuration.CPPriceRangeFacetsPortletInstanceConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.facet.Facet;
import com.liferay.portal.kernel.search.facet.util.RangeParserUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.math.BigDecimal;

import java.util.Optional;

import javax.portlet.RenderRequest;

/**
 * @author Alec Sloan
 */
public class CPPriceRangeFacetsDisplayContext {

	public CPPriceRangeFacetsDisplayContext(
			CommercePriceFormatter commercePriceFormatter,
			RenderRequest renderRequest, Facet facet,
			String paginationStartParameterName,
			PortletSharedSearchResponse portletSharedSearchResponse)
		throws PortalException {

		_commercePriceFormatter = commercePriceFormatter;
		_renderRequest = renderRequest;
		_facet = facet;
		_paginationStartParameterName = paginationStartParameterName;
		_portletSharedSearchResponse = portletSharedSearchResponse;

		_themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = _themeDisplay.getPortletDisplay();

		_cpPriceRangeFacetsPortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				CPPriceRangeFacetsPortletInstanceConfiguration.class);
	}

	public String getCurrentCommerceCurrencySymbol() throws PortalException {
		CommerceContext commerceContext =
			(CommerceContext)_renderRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		return commerceCurrency.getSymbol();
	}

	public Facet getFacet() {
		return _facet;
	}

	public String getPaginationStartParameterName() {
		return _paginationStartParameterName;
	}

	public String getPriceRangeLabel(String facetTerm) throws PortalException {
		CommerceContext commerceContext =
			(CommerceContext)_renderRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		CommerceCurrency commerceCurrency =
			commerceContext.getCommerceCurrency();

		String[] priceRange = RangeParserUtil.parserRange(facetTerm);

		String formattedRangeLow = _commercePriceFormatter.format(
			commerceCurrency, new BigDecimal(priceRange[0]),
			_themeDisplay.getLocale());

		if (Double.valueOf(priceRange[1]) == Double.MAX_VALUE) {
			return formattedRangeLow + StringPool.PLUS;
		}

		String formattedRangeHigh = _commercePriceFormatter.format(
			commerceCurrency, new BigDecimal(priceRange[1]),
			_themeDisplay.getLocale());

		return StringBundler.concat(
			formattedRangeLow, " - ", formattedRangeHigh);
	}

	public String getRangesJSONArrayString() {
		return _cpPriceRangeFacetsPortletInstanceConfiguration.
			rangesJSONArrayString();
	}

	public boolean hasCommerceChannel() throws PortalException {
		CommerceContext commerceContext =
			(CommerceContext)_renderRequest.getAttribute(
				CommerceWebKeys.COMMERCE_CONTEXT);

		long commerceChannelId = commerceContext.getCommerceChannelId();

		if (commerceChannelId > 0) {
			return true;
		}

		return false;
	}

	public boolean isCPPriceRangeValueSelected(
			String fieldName, String fieldValue)
		throws PortalException {

		Optional<String[]> parameterValuesOptional =
			_portletSharedSearchResponse.getParameterValues(
				fieldName, _renderRequest);

		if (parameterValuesOptional.isPresent()) {
			String[] parameterValues = parameterValuesOptional.get();

			return ArrayUtil.contains(parameterValues, fieldValue);
		}

		return false;
	}

	public boolean isShowClear(String fieldName) {
		Optional<String[]> parameterValuesOptional =
			_portletSharedSearchResponse.getParameterValues(
				fieldName, _renderRequest);

		return parameterValuesOptional.isPresent();
	}

	public boolean isStagingEnabled() {
		Group group = _themeDisplay.getScopeGroup();

		return group.isStaged();
	}

	public boolean showInputRange() {
		return _cpPriceRangeFacetsPortletInstanceConfiguration.showInputRange();
	}

	private final CommercePriceFormatter _commercePriceFormatter;
	private final CPPriceRangeFacetsPortletInstanceConfiguration
		_cpPriceRangeFacetsPortletInstanceConfiguration;
	private final Facet _facet;
	private final String _paginationStartParameterName;
	private final PortletSharedSearchResponse _portletSharedSearchResponse;
	private final RenderRequest _renderRequest;
	private final ThemeDisplay _themeDisplay;

}
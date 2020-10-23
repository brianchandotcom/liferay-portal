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

package com.liferay.commerce.product.definitions.web.internal.frontend;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.currency.util.CommercePriceFormatter;
import com.liferay.commerce.product.definitions.web.internal.frontend.constants.CommerceProductDataSetConstants;
import com.liferay.commerce.product.definitions.web.internal.model.ProductOptionValue;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceProductDataSetConstants.COMMERCE_DATA_SET_KEY_PRODUCT_OPTION_VALUES,
	service = ClayDataSetDataProvider.class
)
public class CommerceProductOptionValueDataSetDataProvider
	implements ClayDataSetDataProvider<ProductOptionValue> {

	@Override
	public List<ProductOptionValue> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<ProductOptionValue> productOptionValues = new ArrayList<>();

		long cpDefinitionOptionRelId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionOptionRelId");

		CommerceCurrency commerceCurrency = _getCommerceCurrency(
			cpDefinitionOptionRelId);

		Locale locale = _portal.getLocale(httpServletRequest);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		BaseModelSearchResult<CPDefinitionOptionValueRel>
			cpDefinitionOptionValueRelBaseModelSearchResult =
				_cpDefinitionOptionValueRelService.
					searchCPDefinitionOptionValueRels(
						cpDefinitionOptionRel.getCompanyId(),
						cpDefinitionOptionRel.getGroupId(),
						cpDefinitionOptionRelId, filter.getKeywords(),
						pagination.getStartPosition(),
						pagination.getEndPosition(), new Sort[] {sort});

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRelBaseModelSearchResult.
					getBaseModels()) {

			productOptionValues.add(
				new ProductOptionValue(
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId(),
					_commercePriceFormatter.format(
						commerceCurrency, _getPrice(cpDefinitionOptionValueRel),
						locale),
					cpDefinitionOptionValueRel.getKey(),
					cpDefinitionOptionValueRel.getName(
						LanguageUtil.getLanguageId(locale)),
					cpDefinitionOptionValueRel.getPriority(),
					LanguageUtil.get(
						locale,
						cpDefinitionOptionValueRel.isPreselected() ? "yes" :
							"no"),
					_getSku(cpDefinitionOptionValueRel)));
		}

		return productOptionValues;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long cpDefinitionOptionRelId = ParamUtil.getLong(
			httpServletRequest, "cpDefinitionOptionRelId");

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		return _cpDefinitionOptionValueRelService.
			searchCPDefinitionOptionValueRelsCount(
				cpDefinitionOptionRel.getCompanyId(),
				cpDefinitionOptionRel.getGroupId(),
				cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
				filter.getKeywords());
	}

	private CommerceCurrency _getCommerceCurrency(long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
				cpDefinitionOptionRelId);

		CommerceCatalog commerceCatalog =
			_commerceCatalogService.fetchCommerceCatalogByGroupId(
				cpDefinitionOptionRel.getGroupId());

		return _commerceCurrencyService.getCommerceCurrency(
			commerceCatalog.getCompanyId(),
			commerceCatalog.getCommerceCurrencyCode());
	}

	private BigDecimal _getPrice(
			CPDefinitionOptionValueRel cpDefinitionOptionValueRel)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionValueRel.getCPDefinitionOptionRel();

		if (!cpDefinitionOptionRel.isPriceTypeStatic() ||
			(cpDefinitionOptionValueRel.getPrice() == null)) {

			return BigDecimal.ZERO;
		}

		if (cpDefinitionOptionValueRel.getQuantity() == 0) {
			return cpDefinitionOptionValueRel.getPrice();
		}

		BigDecimal quantity = new BigDecimal(
			cpDefinitionOptionValueRel.getQuantity());

		return quantity.multiply(cpDefinitionOptionValueRel.getPrice());
	}

	private String _getSku(
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel) {

		if (Validator.isNull(cpDefinitionOptionValueRel.getCPInstanceUuid())) {
			return StringPool.BLANK;
		}

		CPInstance cpInstance = cpDefinitionOptionValueRel.fetchCPInstance();

		if (cpInstance == null) {
			return StringPool.BLANK;
		}

		return cpInstance.getSku();
	}

	@Reference
	private CommerceCatalogService _commerceCatalogService;

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference
	private CommercePriceFormatter _commercePriceFormatter;

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

	@Reference
	private Portal _portal;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0;

import com.liferay.commerce.currency.model.CommerceCurrency;
import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Currency;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.CurrencyResource;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.SearchUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Michele Vigilante
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/currency.properties",
	scope = ServiceScope.PROTOTYPE, service = CurrencyResource.class
)
public class CurrencyResourceImpl extends BaseCurrencyResourceImpl {

	@Override
	public Page<Currency> getChannelByExternalReferenceCodeCurrenciesPage(
			String channelExternalReferenceCode, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.
				getCommerceChannelByExternalReferenceCode(
					channelExternalReferenceCode,
					contextCompany.getCompanyId());

		return _getChannelCurrenciesPage(
			commerceChannel, search, filter, pagination, sorts);
	}

	@Override
	public Page<Currency> getChannelCurrenciesPage(
			Long channelId, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		return _getChannelCurrenciesPage(
			_commerceChannelLocalService.getCommerceChannel(channelId), search,
			filter, pagination, sorts);
	}

	private Page<Currency> _getChannelCurrenciesPage(
			CommerceChannel commerceChannel, String search, Filter filter,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		return SearchUtil.search(
			null,
			booleanQuery -> {
			},
			filter, CommerceCurrency.class.getName(), search, pagination,
			queryConfig -> queryConfig.setSelectedFieldNames(
				Field.ENTRY_CLASS_PK),
			searchContext -> {
				int count =
					_commerceChannelRelService.
						getCommerceCurrencyCommerceChannelRelsCount(
							commerceChannel.getCommerceChannelId(), null);

				if (count > 0) {
					searchContext.setAttribute(
						CPField.CHANNEL_IDS,
						new long[] {commerceChannel.getCommerceChannelId()});
				}

				searchContext.setCompanyId(commerceChannel.getCompanyId());

				if (Validator.isNotNull(search)) {
					searchContext.setKeywords(search);
				}
			},
			sorts,
			document -> _toCurrency(
				_commerceCurrencyService.getCommerceCurrency(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)))));
	}

	private Currency _toCurrency(CommerceCurrency commerceCurrency)
		throws Exception {

		return _toCurrency(commerceCurrency.getCommerceCurrencyId());
	}

	private Currency _toCurrency(Long commerceCurrencyId) throws Exception {
		return _currencyDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceCurrencyId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceChannelRelService _commerceChannelRelService;

	@Reference
	private CommerceCurrencyService _commerceCurrencyService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.CurrencyDTOConverter)"
	)
	private DTOConverter<CommerceCurrency, Currency> _currencyDTOConverter;

}
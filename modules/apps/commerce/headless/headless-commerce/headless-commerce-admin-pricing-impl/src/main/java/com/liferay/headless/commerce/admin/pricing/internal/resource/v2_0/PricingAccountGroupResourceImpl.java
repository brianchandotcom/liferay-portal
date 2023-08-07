/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.account.model.AccountGroup;
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelService;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccountGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccountGroup;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PricingAccountGroup;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PricingAccountGroupResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/pricing-account-group.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = PricingAccountGroupResource.class
)
public class PricingAccountGroupResourceImpl
	extends BasePricingAccountGroupResourceImpl {

	@NestedField(
		parentClass = DiscountAccountGroup.class, value = "accountGroup"
	)
	@Override
	public PricingAccountGroup getDiscountAccountGroupAccountGroup(Long id)
		throws Exception {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				_commerceDiscountCommerceAccountGroupRelService.
					getCommerceDiscountCommerceAccountGroupRel(id);

		return _pricingAccountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceDiscountCommerceAccountGroupRel.
					getCommerceAccountGroupId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(
		parentClass = PriceListAccountGroup.class, value = "accountGroup"
	)
	@Override
	public PricingAccountGroup getPriceListAccountGroupAccountGroup(Long id)
		throws Exception {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				_commercePriceListCommerceAccountGroupRelService.
					getCommercePriceListCommerceAccountGroupRel(id);

		return _pricingAccountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePriceListCommerceAccountGroupRel.
					getCommerceAccountGroupId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CommerceDiscountCommerceAccountGroupRelService
		_commerceDiscountCommerceAccountGroupRelService;

	@Reference
	private CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.PricingAccountGroupDTOConverter)"
	)
	private DTOConverter<AccountGroup, PricingAccountGroup>
		_pricingAccountGroupDTOConverter;

}
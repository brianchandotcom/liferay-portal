/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.account.model.AccountEntry;
import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.commerce.discount.service.CommerceDiscountAccountRelService;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Account;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccount;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.AccountResource;
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
	properties = "OSGI-INF/liferay/rest/v2_0/account.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = AccountResource.class
)
public class AccountResourceImpl extends BaseAccountResourceImpl {

	@NestedField(parentClass = DiscountAccount.class, value = "account")
	@Override
	public Account getDiscountAccountAccount(Long id) throws Exception {
		CommerceDiscountAccountRel commerceDiscountAccountRel =
			_commerceDiscountAccountRelService.getCommerceDiscountAccountRel(
				id);

		return _accountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceDiscountAccountRel.getCommerceAccountId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = PriceListAccount.class, value = "account")
	@Override
	public Account getPriceListAccountAccount(Long id) throws Exception {
		CommercePriceListAccountRel commercePriceListAccountRel =
			_commercePriceListAccountRelService.getCommercePriceListAccountRel(
				id);

		return _accountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePriceListAccountRel.getCommerceAccountId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.AccountDTOConverter)"
	)
	private DTOConverter<AccountEntry, Account> _accountDTOConverter;

	@Reference
	private CommerceDiscountAccountRelService
		_commerceDiscountAccountRelService;

	@Reference
	private CommercePriceListAccountRelService
		_commercePriceListAccountRelService;

}
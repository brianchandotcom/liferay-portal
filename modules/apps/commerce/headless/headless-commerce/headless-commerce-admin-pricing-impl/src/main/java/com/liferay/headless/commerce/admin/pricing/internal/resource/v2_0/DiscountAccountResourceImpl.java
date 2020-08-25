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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.commerce.account.service.CommerceAccountService;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.commerce.discount.service.CommerceDiscountAccountRelService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccount;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.DiscountAccountDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.DiscountAccountUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountAccountResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Alberti
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/discount-account.properties",
	scope = ServiceScope.PROTOTYPE, service = DiscountAccountResource.class
)
public class DiscountAccountResourceImpl
	extends BaseDiscountAccountResourceImpl {

	@Override
	public void deleteDiscountAccount(Long id) throws Exception {
		_commerceDiscountAccountRelService.deleteCommerceDiscountAccountRel(id);
	}

	@Override
	public Page<DiscountAccount>
			getDiscountByExternalReferenceCodeDiscountAccountsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find Discount with externalReferenceCode: " +
					externalReferenceCode);
		}

		List<CommerceDiscountAccountRel> commerceDiscountAccountRels =
			_commerceDiscountAccountRelService.getCommerceDiscountAccountRels(
				commerceDiscount.getCommerceDiscountId(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems =
			_commerceDiscountAccountRelService.
				getCommerceDiscountAccountRelsCount(
					commerceDiscount.getCommerceDiscountId());

		return Page.of(
			_toDiscountAccounts(commerceDiscountAccountRels), pagination,
			totalItems);
	}

	@Override
	public Page<DiscountAccount> getDiscountIdDiscountAccountsPage(
			Long id, Pagination pagination)
		throws Exception {

		List<CommerceDiscountAccountRel> commerceDiscountAccountRels =
			_commerceDiscountAccountRelService.getCommerceDiscountAccountRels(
				id, pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems =
			_commerceDiscountAccountRelService.
				getCommerceDiscountAccountRelsCount(id);

		return Page.of(
			_toDiscountAccounts(commerceDiscountAccountRels), pagination,
			totalItems);
	}

	@Override
	public DiscountAccount postDiscountByExternalReferenceCodeDiscountAccount(
			String externalReferenceCode, DiscountAccount discountAccount)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find Discount with externalReferenceCode: " +
					externalReferenceCode);
		}

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			DiscountAccountUtil.addCommerceDiscountAccountRel(
				_commerceAccountService, _commerceDiscountAccountRelService,
				discountAccount, commerceDiscount, _serviceContextHelper);

		return _toDiscountAccount(
			commerceDiscountAccountRel.getCommerceDiscountAccountRelId());
	}

	@Override
	public DiscountAccount postDiscountIdDiscountAccount(
			Long id, DiscountAccount discountAccount)
		throws Exception {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			DiscountAccountUtil.addCommerceDiscountAccountRel(
				_commerceAccountService, _commerceDiscountAccountRelService,
				discountAccount,
				_commerceDiscountService.getCommerceDiscount(id),
				_serviceContextHelper);

		return _toDiscountAccount(
			commerceDiscountAccountRel.getCommerceDiscountAccountRelId());
	}

	private DiscountAccount _toDiscountAccount(
			Long commerceDiscountAccountRelId)
		throws Exception {

		return _discountAccountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceDiscountAccountRelId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<DiscountAccount> _toDiscountAccounts(
			List<CommerceDiscountAccountRel> commerceDiscountAccountRels)
		throws Exception {

		List<DiscountAccount> discountAccounts = new ArrayList<>();

		for (CommerceDiscountAccountRel commerceDiscountAccountRel :
				commerceDiscountAccountRels) {

			discountAccounts.add(
				_toDiscountAccount(
					commerceDiscountAccountRel.
						getCommerceDiscountAccountRelId()));
		}

		return discountAccounts;
	}

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference
	private CommerceDiscountAccountRelService
		_commerceDiscountAccountRelService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private DiscountAccountDTOConverter _discountAccountDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
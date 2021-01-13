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
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.service.CommercePriceListAccountRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccount;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.PriceListAccountDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceListAccountUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListAccountResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v2_0/price-list-account.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, PriceListAccountResource.class}
)
public class PriceListAccountResourceImpl
	extends BasePriceListAccountResourceImpl implements NestedFieldSupport {

	@Override
	public void deletePriceListAccount(Long id) throws Exception {
		_commercePriceListAccountRelService.deleteCommercePriceListAccountRel(
			id);
	}

	@Override
	public Page<PriceListAccount>
			getPriceListByExternalReferenceCodePriceListAccountsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with externalReferenceCode: " +
					externalReferenceCode);
		}

		List<CommercePriceListAccountRel> commercePriceListAccountRels =
			_commercePriceListAccountRelService.getCommercePriceListAccountRels(
				commercePriceList.getCommercePriceListId(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems =
			_commercePriceListAccountRelService.
				getCommercePriceListAccountRelsCount(
					commercePriceList.getCommercePriceListId());

		return Page.of(
			_toPriceListAccounts(commercePriceListAccountRels), pagination,
			totalItems);
	}

	@NestedField(parentClass = PriceList.class, value = "priceListAccounts")
	@Override
	public Page<PriceListAccount> getPriceListIdPriceListAccountsPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<CommercePriceListAccountRel> commercePriceListAccountRels =
			_commercePriceListAccountRelService.getCommercePriceListAccountRels(
				id, search, pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems =
			_commercePriceListAccountRelService.
				getCommercePriceListAccountRelsCount(id, search);

		return Page.of(
			_toPriceListAccounts(commercePriceListAccountRels), pagination,
			totalItems);
	}

	@Override
	public PriceListAccount
			postPriceListByExternalReferenceCodePriceListAccount(
				String externalReferenceCode, PriceListAccount priceListAccount)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with externalReferenceCode: " +
					externalReferenceCode);
		}

		CommercePriceListAccountRel commercePriceListAccountRel =
			PriceListAccountUtil.addCommercePriceListAccountRel(
				_commerceAccountService, _commercePriceListAccountRelService,
				priceListAccount, commercePriceList, _serviceContextHelper);

		return _toPriceListAccount(
			commercePriceListAccountRel.getCommercePriceListAccountRelId());
	}

	@Override
	public PriceListAccount postPriceListIdPriceListAccount(
			Long id, PriceListAccount priceListAccount)
		throws Exception {

		CommercePriceListAccountRel commercePriceListAccountRel =
			PriceListAccountUtil.addCommercePriceListAccountRel(
				_commerceAccountService, _commercePriceListAccountRelService,
				priceListAccount,
				_commercePriceListService.getCommercePriceList(id),
				_serviceContextHelper);

		return _toPriceListAccount(
			commercePriceListAccountRel.getCommercePriceListAccountRelId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommercePriceListAccountRel commercePriceListAccountRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE",
				commercePriceListAccountRel.getCommercePriceListAccountRelId(),
				"deletePriceListAccount",
				_commercePriceListAccountRelModelResourcePermission)
		).build();
	}

	private PriceListAccount _toPriceListAccount(
			Long commercePriceListAccountRelId)
		throws Exception {

		CommercePriceListAccountRel commercePriceListAccountRel =
			_commercePriceListAccountRelService.getCommercePriceListAccountRel(
				commercePriceListAccountRelId);

		return _priceListAccountDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commercePriceListAccountRel), _dtoConverterRegistry,
				commercePriceListAccountRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<PriceListAccount> _toPriceListAccounts(
			List<CommercePriceListAccountRel> commercePriceListAccountRels)
		throws Exception {

		List<PriceListAccount> priceListAccounts = new ArrayList<>();

		for (CommercePriceListAccountRel commercePriceListAccountRel :
				commercePriceListAccountRels) {

			priceListAccounts.add(
				_toPriceListAccount(
					commercePriceListAccountRel.
						getCommercePriceListAccountRelId()));
		}

		return priceListAccounts;
	}

	@Reference
	private CommerceAccountService _commerceAccountService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceListAccountRel)"
	)
	private ModelResourcePermission<CommercePriceListAccountRel>
		_commercePriceListAccountRelModelResourcePermission;

	@Reference
	private CommercePriceListAccountRelService
		_commercePriceListAccountRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private PriceListAccountDTOConverter _priceListAccountDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
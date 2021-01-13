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

import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.commerce.price.list.exception.NoSuchPriceListException;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.service.CommercePriceListCommerceAccountGroupRelService;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceList;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceListAccountGroup;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.PriceListAccountGroupDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceListAccountGroupUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceListAccountGroupResource;
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
	properties = "OSGI-INF/liferay/rest/v2_0/price-list-account-group.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, PriceListAccountGroupResource.class}
)
public class PriceListAccountGroupResourceImpl
	extends BasePriceListAccountGroupResourceImpl
	implements NestedFieldSupport {

	@Override
	public void deletePriceListAccountGroup(Long id) throws Exception {
		_commercePriceListCommerceAccountGroupRelService.
			deleteCommercePriceListCommerceAccountGroupRel(id);
	}

	@Override
	public Page<PriceListAccountGroup>
			getPriceListByExternalReferenceCodePriceListAccountGroupsPage(
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

		List<CommercePriceListCommerceAccountGroupRel>
			commercePriceListCommerceAccountGroupRels =
				_commercePriceListCommerceAccountGroupRelService.
					getCommercePriceListCommerceAccountGroupRels(
						commercePriceList.getCommercePriceListId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(), null);

		int totalItems =
			_commercePriceListCommerceAccountGroupRelService.
				getCommercePriceListCommerceAccountGroupRelsCount(
					commercePriceList.getCommercePriceListId());

		return Page.of(
			_toPriceListAccountGroups(
				commercePriceListCommerceAccountGroupRels),
			pagination, totalItems);
	}

	@NestedField(
		parentClass = PriceList.class, value = "priceListAccountGroups"
	)
	@Override
	public Page<PriceListAccountGroup> getPriceListIdPriceListAccountGroupsPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchCommercePriceList(id);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with id: " + id);
		}

		List<CommercePriceListCommerceAccountGroupRel>
			commercePriceListCommerceAccountGroupRels =
				_commercePriceListCommerceAccountGroupRelService.
					getCommercePriceListCommerceAccountGroupRels(
						id, search, pagination.getStartPosition(),
						pagination.getEndPosition());

		int totalItems =
			_commercePriceListCommerceAccountGroupRelService.
				getCommercePriceListCommerceAccountGroupRelsCount(id, search);

		return Page.of(
			_toPriceListAccountGroups(
				commercePriceListCommerceAccountGroupRels),
			pagination, totalItems);
	}

	@Override
	public PriceListAccountGroup
			postPriceListByExternalReferenceCodePriceListAccountGroup(
				String externalReferenceCode,
				PriceListAccountGroup priceListAccountGroup)
		throws Exception {

		CommercePriceList commercePriceList =
			_commercePriceListService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceList == null) {
			throw new NoSuchPriceListException(
				"Unable to find Price List with externalReferenceCode: " +
					externalReferenceCode);
		}

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				PriceListAccountGroupUtil.
					addCommercePriceListCommerceAccountGroupRel(
						_commerceAccountGroupService,
						_commercePriceListCommerceAccountGroupRelService,
						priceListAccountGroup, commercePriceList,
						_serviceContextHelper);

		return _toPriceListAccountGroup(
			commercePriceListCommerceAccountGroupRel.
				getCommercePriceListCommerceAccountGroupRelId());
	}

	@Override
	public PriceListAccountGroup postPriceListIdPriceListAccountGroup(
			Long id, PriceListAccountGroup priceListAccountGroup)
		throws Exception {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				PriceListAccountGroupUtil.
					addCommercePriceListCommerceAccountGroupRel(
						_commerceAccountGroupService,
						_commercePriceListCommerceAccountGroupRelService,
						priceListAccountGroup,
						_commercePriceListService.getCommercePriceList(id),
						_serviceContextHelper);

		return _toPriceListAccountGroup(
			commercePriceListCommerceAccountGroupRel.
				getCommercePriceListCommerceAccountGroupRelId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE",
				commercePriceListCommerceAccountGroupRel.
					getCommercePriceListCommerceAccountGroupRelId(),
				"deletePriceListAccountGroup",
				_commercePriceListAccountGroupRelModelResourcePermission)
		).build();
	}

	private PriceListAccountGroup _toPriceListAccountGroup(
			Long commercePriceListCommerceAccountGroupRelId)
		throws Exception {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				_commercePriceListCommerceAccountGroupRelService.
					getCommercePriceListCommerceAccountGroupRel(
						commercePriceListCommerceAccountGroupRelId);

		return _priceListAccountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commercePriceListCommerceAccountGroupRel),
				_dtoConverterRegistry,
				commercePriceListCommerceAccountGroupRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<PriceListAccountGroup> _toPriceListAccountGroups(
			List<CommercePriceListCommerceAccountGroupRel>
				commercePriceListCommerceAccountGroupRels)
		throws Exception {

		List<PriceListAccountGroup> priceListAccountGroups = new ArrayList<>();

		for (CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel :
					commercePriceListCommerceAccountGroupRels) {

			priceListAccountGroups.add(
				_toPriceListAccountGroup(
					commercePriceListCommerceAccountGroupRel.
						getCommercePriceListCommerceAccountGroupRelId()));
		}

		return priceListAccountGroups;
	}

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel)"
	)
	private ModelResourcePermission<CommercePriceListCommerceAccountGroupRel>
		_commercePriceListAccountGroupRelModelResourcePermission;

	@Reference
	private CommercePriceListCommerceAccountGroupRelService
		_commercePriceListCommerceAccountGroupRelService;

	@Reference
	private CommercePriceListService _commercePriceListService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private PriceListAccountGroupDTOConverter
		_priceListAccountGroupDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
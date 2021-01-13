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
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel;
import com.liferay.commerce.discount.service.CommerceDiscountCommerceAccountGroupRelService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Discount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountAccountGroup;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.DiscountAccountGroupDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.DiscountAccountGroupUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountAccountGroupResource;
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
	properties = "OSGI-INF/liferay/rest/v2_0/discount-account-group.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {DiscountAccountGroupResource.class, NestedFieldSupport.class}
)
public class DiscountAccountGroupResourceImpl
	extends BaseDiscountAccountGroupResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteDiscountAccountGroup(Long id) throws Exception {
		_commerceDiscountCommerceAccountGroupRelService.
			deleteCommerceDiscountCommerceAccountGroupRel(id);
	}

	@Override
	public Page<DiscountAccountGroup>
			getDiscountByExternalReferenceCodeDiscountAccountGroupsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find Discount with externalReferenceCode: " +
					externalReferenceCode);
		}

		List<CommerceDiscountCommerceAccountGroupRel>
			commerceDiscountCommerceAccountGroupRels =
				_commerceDiscountCommerceAccountGroupRelService.
					getCommerceDiscountCommerceAccountGroupRels(
						commerceDiscount.getCommerceDiscountId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(), null);

		int totalItems =
			_commerceDiscountCommerceAccountGroupRelService.
				getCommerceDiscountCommerceAccountGroupRelsCount(
					commerceDiscount.getCommerceDiscountId());

		return Page.of(
			_toDiscountAccountGroups(commerceDiscountCommerceAccountGroupRels),
			pagination, totalItems);
	}

	@NestedField(parentClass = Discount.class, value = "discountAccountGroups")
	@Override
	public Page<DiscountAccountGroup> getDiscountIdDiscountAccountGroupsPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<CommerceDiscountCommerceAccountGroupRel>
			commerceDiscountCommerceAccountGroupRels =
				_commerceDiscountCommerceAccountGroupRelService.
					getCommerceDiscountCommerceAccountGroupRels(
						id, search, pagination.getStartPosition(),
						pagination.getEndPosition());

		int totalItems =
			_commerceDiscountCommerceAccountGroupRelService.
				getCommerceDiscountCommerceAccountGroupRelsCount(id, search);

		return Page.of(
			_toDiscountAccountGroups(commerceDiscountCommerceAccountGroupRels),
			pagination, totalItems);
	}

	@Override
	public DiscountAccountGroup
			postDiscountByExternalReferenceCodeDiscountAccountGroup(
				String externalReferenceCode,
				DiscountAccountGroup discountAccountGroup)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find Discount with externalReferenceCode: " +
					externalReferenceCode);
		}

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				DiscountAccountGroupUtil.
					addCommerceDiscountCommerceAccountGroupRel(
						_commerceAccountGroupService,
						_commerceDiscountCommerceAccountGroupRelService,
						discountAccountGroup, commerceDiscount,
						_serviceContextHelper);

		return _toDiscountAccountGroup(
			commerceDiscountCommerceAccountGroupRel.
				getCommerceDiscountCommerceAccountGroupRelId());
	}

	@Override
	public DiscountAccountGroup postDiscountIdDiscountAccountGroup(
			Long id, DiscountAccountGroup discountAccountGroup)
		throws Exception {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				DiscountAccountGroupUtil.
					addCommerceDiscountCommerceAccountGroupRel(
						_commerceAccountGroupService,
						_commerceDiscountCommerceAccountGroupRelService,
						discountAccountGroup,
						_commerceDiscountService.getCommerceDiscount(id),
						_serviceContextHelper);

		return _toDiscountAccountGroup(
			commerceDiscountCommerceAccountGroupRel.
				getCommerceDiscountCommerceAccountGroupRelId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE",
				commerceDiscountCommerceAccountGroupRel.
					getCommerceDiscountCommerceAccountGroupRelId(),
				"deleteDiscountAccountGroup",
				_commerceDiscountCommerceAccountGroupRelModelResourcePermission)
		).build();
	}

	private DiscountAccountGroup _toDiscountAccountGroup(
			Long commerceDiscountCommerceAccountGroupRelId)
		throws Exception {

		CommerceDiscountCommerceAccountGroupRel
			commerceDiscountCommerceAccountGroupRel =
				_commerceDiscountCommerceAccountGroupRelService.
					getCommerceDiscountCommerceAccountGroupRel(
						commerceDiscountCommerceAccountGroupRelId);

		return _discountAccountGroupDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceDiscountCommerceAccountGroupRel),
				_dtoConverterRegistry,
				commerceDiscountCommerceAccountGroupRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	private List<DiscountAccountGroup> _toDiscountAccountGroups(
			List<CommerceDiscountCommerceAccountGroupRel>
				commerceDiscountCommerceAccountGroupRels)
		throws Exception {

		List<DiscountAccountGroup> discountAccountGroups = new ArrayList<>();

		for (CommerceDiscountCommerceAccountGroupRel
				commerceDiscountCommerceAccountGroupRel :
					commerceDiscountCommerceAccountGroupRels) {

			discountAccountGroups.add(
				_toDiscountAccountGroup(
					commerceDiscountCommerceAccountGroupRel.
						getCommerceDiscountCommerceAccountGroupRelId()));
		}

		return discountAccountGroups;
	}

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.discount.model.CommerceDiscountCommerceAccountGroupRel)"
	)
	private ModelResourcePermission<CommerceDiscountCommerceAccountGroupRel>
		_commerceDiscountCommerceAccountGroupRelModelResourcePermission;

	@Reference
	private CommerceDiscountCommerceAccountGroupRelService
		_commerceDiscountCommerceAccountGroupRelService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private DiscountAccountGroupDTOConverter _discountAccountGroupDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
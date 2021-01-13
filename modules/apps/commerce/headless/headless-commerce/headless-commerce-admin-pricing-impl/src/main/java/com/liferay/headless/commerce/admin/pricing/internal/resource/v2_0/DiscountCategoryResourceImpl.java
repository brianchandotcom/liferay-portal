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

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Discount;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountCategory;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.DiscountCategoryDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.DiscountCategoryUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.DiscountCategoryResource;
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
	properties = "OSGI-INF/liferay/rest/v2_0/discount-category.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {DiscountCategoryResource.class, NestedFieldSupport.class}
)
public class DiscountCategoryResourceImpl
	extends BaseDiscountCategoryResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteDiscountCategory(Long id) throws Exception {
		_commerceDiscountRelService.deleteCommerceDiscountRel(id);
	}

	@Override
	public Page<DiscountCategory>
			getDiscountByExternalReferenceCodeDiscountCategoriesPage(
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

		List<CommerceDiscountRel> commerceDiscountRels =
			_commerceDiscountRelService.getCommerceDiscountRels(
				commerceDiscount.getCommerceDiscountId(),
				AssetCategory.class.getName(), pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		int totalItems =
			_commerceDiscountRelService.getCommerceDiscountRelsCount(
				commerceDiscount.getCommerceDiscountId(),
				AssetCategory.class.getName());

		return Page.of(
			_toDiscountCategories(commerceDiscountRels), pagination,
			totalItems);
	}

	@NestedField(parentClass = Discount.class, value = "discountCategories")
	@Override
	public Page<DiscountCategory> getDiscountIdDiscountCategoriesPage(
			Long id, String search, Filter filter, Pagination pagination,
			Sort[] sorts)
		throws Exception {

		List<CommerceDiscountRel> commerceDiscountRels =
			_commerceDiscountRelService.getCategoriesByCommerceDiscountId(
				id, search, pagination.getStartPosition(),
				pagination.getEndPosition());

		int totalItems =
			_commerceDiscountRelService.getCategoriesByCommerceDiscountIdCount(
				id, search);

		return Page.of(
			_toDiscountCategories(commerceDiscountRels), pagination,
			totalItems);
	}

	@Override
	public DiscountCategory postDiscountByExternalReferenceCodeDiscountCategory(
			String externalReferenceCode, DiscountCategory discountCategory)
		throws Exception {

		CommerceDiscount commerceDiscount =
			_commerceDiscountService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commerceDiscount == null) {
			throw new NoSuchDiscountException(
				"Unable to find Discount with externalReferenceCode: " +
					externalReferenceCode);
		}

		CommerceDiscountRel commerceDiscountRel =
			DiscountCategoryUtil.addCommerceDiscountRel(
				_assetCategoryLocalService, _commerceDiscountRelService,
				discountCategory, commerceDiscount, _serviceContextHelper);

		return _toDiscountCategory(
			commerceDiscountRel.getCommerceDiscountRelId());
	}

	@Override
	public DiscountCategory postDiscountIdDiscountCategory(
			Long id, DiscountCategory discountCategory)
		throws Exception {

		CommerceDiscountRel commerceDiscountRel =
			DiscountCategoryUtil.addCommerceDiscountRel(
				_assetCategoryLocalService, _commerceDiscountRelService,
				discountCategory,
				_commerceDiscountService.getCommerceDiscount(id),
				_serviceContextHelper);

		return _toDiscountCategory(
			commerceDiscountRel.getCommerceDiscountRelId());
	}

	private Map<String, Map<String, String>> _getActions(
			CommerceDiscountRel commerceDiscountRel)
		throws Exception {

		return HashMapBuilder.<String, Map<String, String>>put(
			"delete",
			addAction(
				"UPDATE", commerceDiscountRel.getCommerceDiscountRelId(),
				"deleteDiscountCategory",
				_commerceDiscountRelModelResourcePermission)
		).build();
	}

	private List<DiscountCategory> _toDiscountCategories(
			List<CommerceDiscountRel> commerceDiscountRels)
		throws Exception {

		List<DiscountCategory> discountCategories = new ArrayList<>();

		for (CommerceDiscountRel commerceDiscountRel : commerceDiscountRels) {
			discountCategories.add(
				_toDiscountCategory(
					commerceDiscountRel.getCommerceDiscountRelId()));
		}

		return discountCategories;
	}

	private DiscountCategory _toDiscountCategory(Long commerceDiscountRelId)
		throws Exception {

		CommerceDiscountRel commerceDiscountRel =
			_commerceDiscountRelService.getCommerceDiscountRel(
				commerceDiscountRelId);

		return _discountCategoryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(),
				_getActions(commerceDiscountRel), _dtoConverterRegistry,
				commerceDiscountRelId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.commerce.discount.model.CommerceDiscountRel)"
	)
	private ModelResourcePermission<CommerceDiscountRel>
		_commerceDiscountRelModelResourcePermission;

	@Reference
	private CommerceDiscountRelService _commerceDiscountRelService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private DiscountCategoryDTOConverter _discountCategoryDTOConverter;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v1_0;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountCategory;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter.DiscountCategoryDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v1_0.DiscountCategoryUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.DiscountCategoryResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/discount-category.properties",
	scope = ServiceScope.PROTOTYPE, service = DiscountCategoryResource.class
)
public class DiscountCategoryResourceImpl
	extends BaseDiscountCategoryResourceImpl {

	@Override
	public Response deleteDiscountCategory(Long id) throws Exception {
		_commerceDiscountRelService.deleteCommerceDiscountRel(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
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
				"Unable to find discount with external reference code " +
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

	@Override
	public Page<DiscountCategory> getDiscountIdDiscountCategoriesPage(
			Long id, Pagination pagination)
		throws Exception {

		List<CommerceDiscountRel> commerceDiscountRels =
			_commerceDiscountRelService.getCommerceDiscountRels(
				id, AssetCategory.class.getName(),
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		int totalItems =
			_commerceDiscountRelService.getCommerceDiscountRelsCount(
				id, AssetCategory.class.getName());

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
				"Unable to find discount with external reference code " +
					externalReferenceCode);
		}

		CommerceDiscountRel commerceDiscountRel =
			DiscountCategoryUtil.addCommerceDiscountRel(
				_assetCategoryLocalService, _commerceDiscountRelService,
				discountCategory, commerceDiscount,
				_serviceContextHelper.getServiceContext());

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
				_serviceContextHelper.getServiceContext());

		return _toDiscountCategory(
			commerceDiscountRel.getCommerceDiscountRelId());
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

		return _discountCategoryDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceDiscountRelId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private CommerceDiscountRelService _commerceDiscountRelService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private DiscountCategoryDTOConverter _discountCategoryDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
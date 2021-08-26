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

import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.model.CommerceDiscount;
import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.discount.service.CommerceDiscountService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.headless.commerce.admin.pricing.dto.v1_0.DiscountProduct;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v1_0.converter.DiscountProductDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v1_0.DiscountProductUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v1_0.DiscountProductResource;
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
	properties = "OSGI-INF/liferay/rest/v1_0/discount-product.properties",
	scope = ServiceScope.PROTOTYPE, service = DiscountProductResource.class
)
public class DiscountProductResourceImpl
	extends BaseDiscountProductResourceImpl {

	@Override
	public Response deleteDiscountProduct(Long id) throws Exception {
		_commerceDiscountRelService.deleteCommerceDiscountRel(id);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Page<DiscountProduct>
			getDiscountByExternalReferenceCodeDiscountProductsPage(
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
				CPDefinition.class.getName(), pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		int totalItems =
			_commerceDiscountRelService.getCommerceDiscountRelsCount(
				commerceDiscount.getCommerceDiscountId(),
				CPDefinition.class.getName());

		return Page.of(
			_toDiscountProducts(commerceDiscountRels), pagination, totalItems);
	}

	@Override
	public Page<DiscountProduct> getDiscountIdDiscountProductsPage(
			Long id, Pagination pagination)
		throws Exception {

		List<CommerceDiscountRel> commerceDiscountRels =
			_commerceDiscountRelService.getCommerceDiscountRels(
				id, CPDefinition.class.getName(), pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		int totalItems =
			_commerceDiscountRelService.getCommerceDiscountRelsCount(
				id, CPDefinition.class.getName());

		return Page.of(
			_toDiscountProducts(commerceDiscountRels), pagination, totalItems);
	}

	@Override
	public DiscountProduct postDiscountByExternalReferenceCodeDiscountProduct(
			String externalReferenceCode, DiscountProduct discountProduct)
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
			DiscountProductUtil.addCommerceDiscountRel(
				_cProductLocalService, _commerceDiscountRelService,
				discountProduct, commerceDiscount,
				_serviceContextHelper.getServiceContext());

		return _toDiscountProduct(
			commerceDiscountRel.getCommerceDiscountRelId());
	}

	@Override
	public DiscountProduct postDiscountIdDiscountProduct(
			Long id, DiscountProduct discountProduct)
		throws Exception {

		CommerceDiscountRel commerceDiscountRel =
			DiscountProductUtil.addCommerceDiscountRel(
				_cProductLocalService, _commerceDiscountRelService,
				discountProduct,
				_commerceDiscountService.getCommerceDiscount(id),
				_serviceContextHelper.getServiceContext());

		return _toDiscountProduct(
			commerceDiscountRel.getCommerceDiscountRelId());
	}

	private DiscountProduct _toDiscountProduct(Long commerceDiscountRelId)
		throws Exception {

		return _discountProductDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceDiscountRelId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<DiscountProduct> _toDiscountProducts(
			List<CommerceDiscountRel> commerceDiscountRels)
		throws Exception {

		List<DiscountProduct> discountProducts = new ArrayList<>();

		for (CommerceDiscountRel commerceDiscountRel : commerceDiscountRels) {
			discountProducts.add(
				_toDiscountProduct(
					commerceDiscountRel.getCommerceDiscountRelId()));
		}

		return discountProducts;
	}

	@Reference
	private CommerceDiscountRelService _commerceDiscountRelService;

	@Reference
	private CommerceDiscountService _commerceDiscountService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private DiscountProductDTOConverter _discountProductDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
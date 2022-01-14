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

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.pricing.exception.NoSuchPricingClassException;
import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelService;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductGroupProduct;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductGroupProductDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.ProductGroupProductUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductGroupProductResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.change.tracking.CTAware;
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
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/product-group-product.properties",
	scope = ServiceScope.PROTOTYPE, service = ProductGroupProductResource.class
)
@CTAware
public class ProductGroupProductResourceImpl
	extends BaseProductGroupProductResourceImpl {

	@Override
	public void deleteProductGroupProduct(Long id) throws Exception {
		_commercePricingClassCPDefinitionRelService.
			deleteCommercePricingClassCPDefinitionRel(id);
	}

	@Override
	public Page<ProductGroupProduct>
			getProductGroupByExternalReferenceCodeProductGroupProductsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommercePricingClass commercePricingClass =
			_commercePricingClassService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePricingClass == null) {
			throw new NoSuchPricingClassException(
				"Unable to find product group with external reference code " +
					externalReferenceCode);
		}

		List<CommercePricingClassCPDefinitionRel>
			commercePricingClassCPDefinitionRels =
				_commercePricingClassCPDefinitionRelService.
					getCommercePricingClassCPDefinitionRels(
						commercePricingClass.getCommercePricingClassId(),
						pagination.getStartPosition(),
						pagination.getEndPosition(), null);

		int totalItems =
			_commercePricingClassCPDefinitionRelService.
				getCommercePricingClassCPDefinitionRelsCount(
					commercePricingClass.getCommercePricingClassId());

		return Page.of(
			_toProductGroupProducts(commercePricingClassCPDefinitionRels),
			pagination, totalItems);
	}

	@Override
	public Page<ProductGroupProduct> getProductGroupIdProductGroupProductsPage(
			Long id, Pagination pagination)
		throws Exception {

		List<CommercePricingClassCPDefinitionRel>
			commercePricingClassCPDefinitionRels =
				_commercePricingClassCPDefinitionRelService.
					getCommercePricingClassCPDefinitionRels(
						id, pagination.getStartPosition(),
						pagination.getEndPosition(), null);

		int totalItems =
			_commercePricingClassCPDefinitionRelService.
				getCommercePricingClassCPDefinitionRelsCount(id);

		return Page.of(
			_toProductGroupProducts(commercePricingClassCPDefinitionRels),
			pagination, totalItems);
	}

	@Override
	public ProductGroupProduct
			postProductGroupByExternalReferenceCodeProductGroupProduct(
				String externalReferenceCode,
				ProductGroupProduct productGroupProduct)
		throws Exception {

		CommercePricingClass commercePricingClass =
			_commercePricingClassService.fetchByExternalReferenceCode(
				externalReferenceCode, contextCompany.getCompanyId());

		if (commercePricingClass == null) {
			throw new NoSuchPricingClassException(
				"Unable to find product group with external reference code " +
					externalReferenceCode);
		}

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel =
				ProductGroupProductUtil.addCommercePricingClassCPDefinitionRel(
					_cProductLocalService,
					_commercePricingClassCPDefinitionRelService,
					productGroupProduct, commercePricingClass,
					_serviceContextHelper);

		return _toProductGroupProduct(
			commercePricingClassCPDefinitionRel.
				getCommercePricingClassCPDefinitionRelId());
	}

	@Override
	public ProductGroupProduct postProductGroupIdProductGroupProduct(
			Long id, ProductGroupProduct productGroupProduct)
		throws Exception {

		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel =
				ProductGroupProductUtil.addCommercePricingClassCPDefinitionRel(
					_cProductLocalService,
					_commercePricingClassCPDefinitionRelService,
					productGroupProduct,
					_commercePricingClassService.getCommercePricingClass(id),
					_serviceContextHelper);

		return _toProductGroupProduct(
			commercePricingClassCPDefinitionRel.
				getCommercePricingClassCPDefinitionRelId());
	}

	private ProductGroupProduct _toProductGroupProduct(
			Long commercePricingClassCPDefinitionRelId)
		throws Exception {

		return _productGroupProductDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePricingClassCPDefinitionRelId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<ProductGroupProduct> _toProductGroupProducts(
			List<CommercePricingClassCPDefinitionRel>
				commercePricingClassCPDefinitionRels)
		throws Exception {

		List<ProductGroupProduct> productGroupProducts = new ArrayList<>();

		for (CommercePricingClassCPDefinitionRel
				commercePricingClassCPDefinitionRel :
					commercePricingClassCPDefinitionRels) {

			productGroupProducts.add(
				_toProductGroupProduct(
					commercePricingClassCPDefinitionRel.
						getCommercePricingClassCPDefinitionRelId()));
		}

		return productGroupProducts;
	}

	@Reference
	private CommercePricingClassCPDefinitionRelService
		_commercePricingClassCPDefinitionRelService;

	@Reference
	private CommercePricingClassService _commercePricingClassService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private ProductGroupProductDTOConverter _productGroupProductDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
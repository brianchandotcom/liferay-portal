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

import com.liferay.commerce.pricing.exception.NoSuchPriceModifierException;
import com.liferay.commerce.pricing.model.CommercePriceModifier;
import com.liferay.commerce.pricing.model.CommercePriceModifierRel;
import com.liferay.commerce.pricing.service.CommercePriceModifierRelService;
import com.liferay.commerce.pricing.service.CommercePriceModifierService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CProductLocalService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceModifierProduct;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.PriceModifierProductDTOConverter;
import com.liferay.headless.commerce.admin.pricing.internal.util.v2_0.PriceModifierProductUtil;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.PriceModifierProductResource;
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
	properties = "OSGI-INF/liferay/rest/v2_0/price-modifier-product.properties",
	scope = ServiceScope.PROTOTYPE, service = PriceModifierProductResource.class
)
public class PriceModifierProductResourceImpl
	extends BasePriceModifierProductResourceImpl {

	@Override
	public void deletePriceModifierProduct(Long id) throws Exception {
		_commercePriceModifierRelService.deleteCommercePriceModifierRel(id);
	}

	@Override
	public Page<PriceModifierProduct>
			getPriceModifierByExternalReferenceCodePriceModifierProductsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CommercePriceModifier commercePriceModifier =
			_commercePriceModifierService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceModifier == null) {
			throw new NoSuchPriceModifierException(
				"Unable to find Price Modifier with externalReferenceCode: " +
					externalReferenceCode);
		}

		List<CommercePriceModifierRel> commercePriceModifierRels =
			_commercePriceModifierRelService.getCommercePriceModifierRels(
				commercePriceModifier.getCommercePriceModifierId(),
				CPDefinition.class.getName(), pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		int totalItems =
			_commercePriceModifierRelService.getCommercePriceModifierRelsCount(
				commercePriceModifier.getCommercePriceModifierId(),
				CPDefinition.class.getName());

		return Page.of(
			_toPriceModifierProducts(commercePriceModifierRels), pagination,
			totalItems);
	}

	@Override
	public Page<PriceModifierProduct>
			getPriceModifierIdPriceModifierProductsPage(
				Long id, Pagination pagination)
		throws Exception {

		List<CommercePriceModifierRel> commercePriceModifierRels =
			_commercePriceModifierRelService.getCommercePriceModifierRels(
				id, CPDefinition.class.getName(), pagination.getStartPosition(),
				pagination.getEndPosition(), null);

		int totalItems =
			_commercePriceModifierRelService.getCommercePriceModifierRelsCount(
				id, CPDefinition.class.getName());

		return Page.of(
			_toPriceModifierProducts(commercePriceModifierRels), pagination,
			totalItems);
	}

	@Override
	public PriceModifierProduct
			postPriceModifierByExternalReferenceCodePriceModifierProduct(
				String externalReferenceCode,
				PriceModifierProduct priceModifierProduct)
		throws Exception {

		CommercePriceModifier commercePriceModifier =
			_commercePriceModifierService.fetchByExternalReferenceCode(
				contextCompany.getCompanyId(), externalReferenceCode);

		if (commercePriceModifier == null) {
			throw new NoSuchPriceModifierException(
				"Unable to find Price Modifier with externalReferenceCode: " +
					externalReferenceCode);
		}

		CommercePriceModifierRel commercePriceModifierRel =
			PriceModifierProductUtil.addCommercePriceModifierRel(
				_cProductLocalService, _commercePriceModifierRelService,
				priceModifierProduct, commercePriceModifier,
				_serviceContextHelper);

		return _toPriceModifierProduct(
			commercePriceModifierRel.getCommercePriceModifierRelId());
	}

	@Override
	public PriceModifierProduct postPriceModifierIdPriceModifierProduct(
			Long id, PriceModifierProduct priceModifierProduct)
		throws Exception {

		CommercePriceModifierRel commercePriceModifierRel =
			PriceModifierProductUtil.addCommercePriceModifierRel(
				_cProductLocalService, _commercePriceModifierRelService,
				priceModifierProduct,
				_commercePriceModifierService.getCommercePriceModifier(id),
				_serviceContextHelper);

		return _toPriceModifierProduct(
			commercePriceModifierRel.getCommercePriceModifierRelId());
	}

	private PriceModifierProduct _toPriceModifierProduct(
			Long commercePriceModifierRelId)
		throws Exception {

		return _priceModifierProductDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commercePriceModifierRelId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<PriceModifierProduct> _toPriceModifierProducts(
			List<CommercePriceModifierRel> commercePriceModifierRels)
		throws Exception {

		List<PriceModifierProduct> priceModifierProducts = new ArrayList<>();

		for (CommercePriceModifierRel commercePriceModifierRel :
				commercePriceModifierRels) {

			priceModifierProducts.add(
				_toPriceModifierProduct(
					commercePriceModifierRel.getCommercePriceModifierRelId()));
		}

		return priceModifierProducts;
	}

	@Reference
	private CommercePriceModifierRelService _commercePriceModifierRelService;

	@Reference
	private CommercePriceModifierService _commercePriceModifierService;

	@Reference
	private CProductLocalService _cProductLocalService;

	@Reference
	private PriceModifierProductDTOConverter _priceModifierProductDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
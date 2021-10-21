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

package com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.RelatedProduct;
import com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.RelatedProductDTOConverter;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.RelatedProductResource;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Andrea Sbarra
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/related-product.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, RelatedProductResource.class}
)
@CTAware
public class RelatedProductResourceImpl
	extends BaseRelatedProductResourceImpl implements NestedFieldSupport {

	@NestedField(parentClass = Product.class, value = "relatedProducts")
	@Override
	public Page<RelatedProduct> getChannelProductRelatedProductsPage(
			Long channelId, @NestedFieldId(value = "productId") Long productId,
			String type, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionLocalService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + productId);
		}

		return _getRelatedProductPage(cpDefinition, type, pagination);
	}

	private Page<RelatedProduct> _getRelatedProductPage(
			CPDefinition cpDefinition, String type, Pagination pagination)
		throws Exception {

		List<CPDefinitionLink> cpDefinitionLinks;
		int totalItems;

		if (Validator.isNull(type)) {
			cpDefinitionLinks =
				_cpDefinitionLinkLocalService.getCPDefinitionLinks(
					cpDefinition.getCPDefinitionId(),
					pagination.getStartPosition(), pagination.getEndPosition());

			totalItems =
				_cpDefinitionLinkLocalService.getCPDefinitionLinksCount(
					cpDefinition.getCPDefinitionId());
		}
		else {
			cpDefinitionLinks =
				_cpDefinitionLinkLocalService.getCPDefinitionLinks(
					cpDefinition.getCPDefinitionId(), type,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);

			totalItems =
				_cpDefinitionLinkLocalService.getCPDefinitionLinksCount(
					cpDefinition.getCPDefinitionId(), type);
		}

		return Page.of(
			_toRelatedProducts(cpDefinitionLinks), pagination, totalItems);
	}

	private List<RelatedProduct> _toRelatedProducts(
			List<CPDefinitionLink> cpDefinitionLinks)
		throws Exception {

		List<RelatedProduct> relatedProducts = new ArrayList<>();

		for (CPDefinitionLink cpDefinitionLink : cpDefinitionLinks) {
			relatedProducts.add(
				_relatedProductDTOConverter.toDTO(
					new DefaultDTOConverterContext(
						cpDefinitionLink.getCPDefinitionLinkId(),
						contextAcceptLanguage.getPreferredLocale())));
		}

		return relatedProducts;
	}

	@Reference
	private CPDefinitionLinkLocalService _cpDefinitionLinkLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private RelatedProductDTOConverter _relatedProductDTOConverter;

}
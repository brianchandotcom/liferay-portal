/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.delivery.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalService;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.RelatedProduct;
import com.liferay.headless.commerce.delivery.catalog.resource.v1_0.RelatedProductResource;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
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
	properties = "OSGI-INF/liferay/rest/v1_0/related-product.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = RelatedProductResource.class
)
@CTAware
public class RelatedProductResourceImpl extends BaseRelatedProductResourceImpl {

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
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition());

			totalItems =
				_cpDefinitionLinkLocalService.getCPDefinitionLinksCount(
					cpDefinition.getCPDefinitionId(),
					WorkflowConstants.STATUS_APPROVED);
		}
		else {
			cpDefinitionLinks =
				_cpDefinitionLinkLocalService.getCPDefinitionLinks(
					cpDefinition.getCPDefinitionId(), type,
					WorkflowConstants.STATUS_APPROVED,
					pagination.getStartPosition(), pagination.getEndPosition(),
					null);

			totalItems =
				_cpDefinitionLinkLocalService.getCPDefinitionLinksCount(
					cpDefinition.getCPDefinitionId(), type,
					WorkflowConstants.STATUS_APPROVED);
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

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter.RelatedProductDTOConverter)"
	)
	private DTOConverter<CPDefinitionLink, RelatedProduct>
		_relatedProductDTOConverter;

}
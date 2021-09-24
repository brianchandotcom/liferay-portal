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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.MappedProduct;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.MappedProductDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.MappedProductUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.MappedProductResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/mapped-product.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {MappedProductResource.class, NestedFieldSupport.class}
)
public class MappedProductResourceImpl
	extends BaseMappedProductResourceImpl implements NestedFieldSupport {

	@Override
	public void deleteMappedProduct(Long mappedProductId) throws Exception {
		_csDiagramEntryService.deleteCSDiagramEntry(mappedProductId);
	}

	@Override
	public MappedProduct
			getProductByExternalReferenceCodeMappedProductBySequence(
				String externalReferenceCode, String sequence)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		CSDiagramEntry csDiagramEntry =
			_csDiagramEntryService.fetchCSDiagramEntry(
				cpDefinition.getCPDefinitionId(), sequence);

		return _toMappedProduct(csDiagramEntry.getCSDiagramEntryId());
	}

	@Override
	public Page<MappedProduct>
			getProductByExternalReferenceCodeMappedProductsPage(
				String externalReferenceCode, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		return _getMappedProductsPage(
			cpDefinition.getCPDefinitionId(), pagination);
	}

	@NestedField(parentClass = Product.class, value = "mappedProducts")
	@Override
	public Page<MappedProduct> getProductIdMappedProductsPage(
			Long productId, Pagination pagination)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		return _getMappedProductsPage(
			cpDefinition.getCPDefinitionId(), pagination);
	}

	@Override
	public MappedProduct getProductMappedProductBySequence(
			Long productId, String sequence)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		CSDiagramEntry csDiagramEntry =
			_csDiagramEntryService.fetchCSDiagramEntry(
				cpDefinition.getCPDefinitionId(), sequence);

		return _toMappedProduct(csDiagramEntry.getCSDiagramEntryId());
	}

	@Override
	public MappedProduct patchMappedProduct(
			Long mappedProductId, MappedProduct mappedProduct)
		throws Exception {

		CSDiagramEntry csDiagramEntry =
			_csDiagramEntryService.getCSDiagramEntry(mappedProductId);

		CPDefinition cpDefinition = csDiagramEntry.getCPDefinition();

		MappedProductUtil.updateCSDiagramEntry(
			contextCompany.getCompanyId(), csDiagramEntry,
			_csDiagramEntryService, cpDefinition.getGroupId(),
			contextAcceptLanguage.getPreferredLocale(), mappedProduct,
			_serviceContextHelper);

		return _toMappedProduct(mappedProductId);
	}

	@Override
	public MappedProduct postProductByExternalReferenceCodeMappedProduct(
			String externalReferenceCode, MappedProduct mappedProduct)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with external reference code " +
					externalReferenceCode);
		}

		CSDiagramEntry csDiagramEntry = MappedProductUtil.addCSDiagramEntry(
			contextCompany.getCompanyId(), cpDefinition.getCPDefinitionId(),
			_cpDefinitionService, _cpInstanceService, _csDiagramEntryService,
			cpDefinition.getGroupId(),
			contextAcceptLanguage.getPreferredLocale(), mappedProduct,
			_serviceContextHelper);

		return _toMappedProduct(csDiagramEntry.getCSDiagramEntryId());
	}

	@Override
	public MappedProduct postProductIdMappedProduct(
			Long productId, MappedProduct mappedProduct)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(productId);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find product with ID " + productId);
		}

		CSDiagramEntry csDiagramEntry = MappedProductUtil.addCSDiagramEntry(
			contextCompany.getCompanyId(), cpDefinition.getCPDefinitionId(),
			_cpDefinitionService, _cpInstanceService, _csDiagramEntryService,
			cpDefinition.getGroupId(),
			contextAcceptLanguage.getPreferredLocale(), mappedProduct,
			_serviceContextHelper);

		return _toMappedProduct(csDiagramEntry.getCSDiagramEntryId());
	}

	private Page<MappedProduct> _getMappedProductsPage(
			long cpDefinitionId, Pagination pagination)
		throws Exception {

		return Page.of(
			transform(
				_csDiagramEntryService.getCSDiagramEntries(
					cpDefinitionId, pagination.getStartPosition(),
					pagination.getEndPosition()),
				csDiagramEntry -> _toMappedProduct(
					csDiagramEntry.getCSDiagramEntryId())),
			pagination,
			_csDiagramEntryService.getCSDiagramEntriesCount(cpDefinitionId));
	}

	private MappedProduct _toMappedProduct(long csDiagramEntryId)
		throws Exception {

		return _mappedProductDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				csDiagramEntryId, contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private CSDiagramEntryService _csDiagramEntryService;

	@Reference
	private MappedProductDTOConverter _mappedProductDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
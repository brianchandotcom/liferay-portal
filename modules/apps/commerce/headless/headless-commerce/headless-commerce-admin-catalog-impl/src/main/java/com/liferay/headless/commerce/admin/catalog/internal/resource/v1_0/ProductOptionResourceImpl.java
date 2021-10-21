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
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOption;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductOptionDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.ProductOptionUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductOptionResource;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/product-option.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, ProductOptionResource.class}
)
@CTAware
public class ProductOptionResourceImpl
	extends BaseProductOptionResourceImpl implements NestedFieldSupport {

	@Override
	public Response deleteProductOption(Long id) throws Exception {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRel(id);

		_cpDefinitionOptionRelService.deleteCPDefinitionOptionRel(
			cpDefinitionOptionRel.getCPDefinitionOptionRelId());

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Page<ProductOption>
			getProductByExternalReferenceCodeProductOptionsPage(
				String externalReferenceCode, String search,
				Pagination pagination, Sort[] sorts)
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

		BaseModelSearchResult<CPDefinitionOptionRel>
			cpDefinitionOptionRelBaseModelSearchResult =
				_cpDefinitionOptionRelService.searchCPDefinitionOptionRels(
					cpDefinition.getCompanyId(), cpDefinition.getGroupId(),
					cpDefinition.getCPDefinitionId(), search,
					pagination.getStartPosition(), pagination.getEndPosition(),
					sorts);

		int totalItems =
			_cpDefinitionOptionRelService.searchCPDefinitionOptionRelsCount(
				cpDefinition.getCompanyId(), cpDefinition.getGroupId(),
				cpDefinition.getCPDefinitionId(), search);

		return Page.of(
			_toProductOptions(
				cpDefinitionOptionRelBaseModelSearchResult.getBaseModels()),
			pagination, totalItems);
	}

	@NestedField(parentClass = Product.class, value = "productOptions")
	@Override
	public Page<ProductOption> getProductIdProductOptionsPage(
			@NestedFieldId(value = "productId") Long id, String search,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		BaseModelSearchResult<CPDefinitionOptionRel>
			cpDefinitionOptionRelBaseModelSearchResult =
				_cpDefinitionOptionRelService.searchCPDefinitionOptionRels(
					cpDefinition.getCompanyId(), cpDefinition.getGroupId(),
					cpDefinition.getCPDefinitionId(), search,
					pagination.getStartPosition(), pagination.getEndPosition(),
					sorts);

		int totalItems =
			_cpDefinitionOptionRelService.searchCPDefinitionOptionRelsCount(
				cpDefinition.getCompanyId(), cpDefinition.getGroupId(),
				cpDefinition.getCPDefinitionId(), search);

		return Page.of(
			_toProductOptions(
				cpDefinitionOptionRelBaseModelSearchResult.getBaseModels()),
			pagination, totalItems);
	}

	@Override
	public ProductOption getProductOption(Long id) throws Exception {
		return _toProductOption(GetterUtil.getLong(id));
	}

	@Override
	public Response patchProductOption(Long id, ProductOption productOption)
		throws Exception {

		_updateProductOption(id, productOption);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Page<ProductOption>
			postProductByExternalReferenceCodeProductOptionsPage(
				String externalReferenceCode, ProductOption[] productOptions)
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

		return Page.of(
			_addOrUpdateProductOptions(cpDefinition, productOptions));
	}

	@Override
	public Page<ProductOption> postProductIdProductOptionsPage(
			Long id, ProductOption[] productOptions)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return Page.of(
			_addOrUpdateProductOptions(cpDefinition, productOptions));
	}

	private List<ProductOption> _addOrUpdateProductOptions(
			CPDefinition cpDefinition, ProductOption[] productOptions)
		throws Exception {

		for (ProductOption productOption : productOptions) {
			ProductOptionUtil.addOrUpdateCPDefinitionOptionRel(
				_cpDefinitionOptionRelService, _cpOptionService, productOption,
				cpDefinition.getCPDefinitionId(),
				_serviceContextHelper.getServiceContext(
					cpDefinition.getGroupId()));
		}

		List<ProductOption> productOptionList = new ArrayList<>();

		cpDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinition.getCPDefinitionId());

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinition.getCPDefinitionOptionRels()) {

			productOptionList.add(
				_toProductOption(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId()));
		}

		return productOptionList;
	}

	private ProductOption _toProductOption(Long cpDefinitionOptionRelId)
		throws Exception {

		return _productOptionDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionOptionRelId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<ProductOption> _toProductOptions(
			List<CPDefinitionOptionRel> cpDefinitionOptionRels)
		throws Exception {

		List<ProductOption> productOptions = new ArrayList<>();

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			productOptions.add(
				_toProductOption(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId()));
		}

		return productOptions;
	}

	private ProductOption _updateProductOption(
			long id, ProductOption productOption)
		throws Exception {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRel(id);

		cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.updateCPDefinitionOptionRel(
				cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
				productOption.getOptionId(),
				LanguageUtils.getLocalizedMap(productOption.getName()),
				LanguageUtils.getLocalizedMap(productOption.getDescription()),
				GetterUtil.get(
					productOption.getFieldType(),
					cpDefinitionOptionRel.getDDMFormFieldTypeName()),
				GetterUtil.get(
					productOption.getPriority(),
					cpDefinitionOptionRel.getPriority()),
				GetterUtil.get(
					productOption.getFacetable(),
					cpDefinitionOptionRel.isFacetable()),
				GetterUtil.get(
					productOption.getRequired(),
					cpDefinitionOptionRel.isRequired()),
				GetterUtil.get(
					productOption.getSkuContributor(),
					cpDefinitionOptionRel.isSkuContributor()),
				_serviceContextHelper.getServiceContext(
					cpDefinitionOptionRel.getGroupId()));

		return _toProductOption(
			cpDefinitionOptionRel.getCPDefinitionOptionRelId());
	}

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPOptionService _cpOptionService;

	@Reference
	private ProductOptionDTOConverter _productOptionDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
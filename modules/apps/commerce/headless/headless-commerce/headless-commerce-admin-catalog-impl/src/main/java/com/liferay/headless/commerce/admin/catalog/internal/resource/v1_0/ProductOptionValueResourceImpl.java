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

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelService;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductOptionValue;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductOptionValueDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.ProductOptionValueUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductOptionValueResource;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/product-option-value.properties",
	scope = ServiceScope.PROTOTYPE, service = ProductOptionValueResource.class
)
public class ProductOptionValueResourceImpl
	extends BaseProductOptionValueResourceImpl {

	@Override
	public Page<ProductOptionValue> getProductOptionIdProductOptionValuesPage(
			Long id, Pagination pagination)
		throws Exception {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRel(id);

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			_cpDefinitionOptionValueRelService.getCPDefinitionOptionValueRels(
				cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
				pagination.getStartPosition(), pagination.getEndPosition());

		int totalItems =
			_cpDefinitionOptionValueRelService.
				getCPDefinitionOptionValueRelsCount(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId());

		return Page.of(
			_toProductOptionValues(cpDefinitionOptionValueRels), pagination,
			totalItems);
	}

	@Override
	public ProductOptionValue postProductOptionIdProductOptionValue(
			Long id, ProductOptionValue productOptionValue)
		throws Exception {

		return _upsertProductOptionValue(id, productOptionValue);
	}

	private ProductOptionValue _toProductOptionValue(
			Long cpDefinitionOptionValueRelId)
		throws Exception {

		return _productOptionValueDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionOptionValueRelId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private List<ProductOptionValue> _toProductOptionValues(
			List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels)
		throws Exception {

		List<ProductOptionValue> productOptionValues = new ArrayList<>();

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			productOptionValues.add(
				_toProductOptionValue(
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId()));
		}

		return productOptionValues;
	}

	private ProductOptionValue _upsertProductOptionValue(
			long productOptionId, ProductOptionValue productOptionValue)
		throws Exception {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			_cpDefinitionOptionRelService.getCPDefinitionOptionRel(
				productOptionId);

		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			ProductOptionValueUtil.upsertCPDefinitionOptionValueRel(
				_cpDefinitionOptionValueRelService, productOptionValue,
				cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
				_serviceContextHelper.getServiceContext(
					cpDefinitionOptionRel.getGroupId()));

		return _toProductOptionValue(
			cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId());
	}

	@Reference
	private CPDefinitionOptionRelService _cpDefinitionOptionRelService;

	@Reference
	private CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

	@Reference
	private ProductOptionValueDTOConverter _productOptionValueDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
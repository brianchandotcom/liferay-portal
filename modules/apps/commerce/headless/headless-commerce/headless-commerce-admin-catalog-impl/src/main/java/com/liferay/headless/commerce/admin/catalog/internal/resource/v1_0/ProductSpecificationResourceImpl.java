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

import com.liferay.commerce.product.exception.NoSuchCPDefinitionSpecificationOptionValueException;
import com.liferay.commerce.product.model.CPDefinitionSpecificationOptionValue;
import com.liferay.commerce.product.service.CPDefinitionSpecificationOptionValueService;
import com.liferay.commerce.product.service.CPSpecificationOptionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSpecification;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductSpecificationDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.helper.v1_0.ProductSpecificationHelper;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.ProductSpecificationUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductSpecificationResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
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
	properties = "OSGI-INF/liferay/rest/v1_0/product-specification.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {NestedFieldSupport.class, ProductSpecificationResource.class}
)
@CTAware
public class ProductSpecificationResourceImpl
	extends BaseProductSpecificationResourceImpl implements NestedFieldSupport {

	@NestedField(parentClass = Product.class, value = "productSpecifications")
	@Override
	public Page<ProductSpecification> getProductIdProductSpecificationsPage(
			@NestedFieldId(value = "productId") Long id, Pagination pagination)
		throws Exception {

		return _productSpecificationHelper.getProductSpecificationsPage(
			id, contextAcceptLanguage.getPreferredLocale(), pagination);
	}

	@Override
	public ProductSpecification postProductIdProductSpecification(
			Long id, ProductSpecification productSpecification)
		throws Exception {

		return _addOrUpdateProductSpecification(id, productSpecification);
	}

	private ProductSpecification _addOrUpdateProductSpecification(
			Long id, ProductSpecification productSpecification)
		throws Exception {

		Long productSpecificationId = productSpecification.getId();

		if (productSpecificationId != null) {
			try {
				CPDefinitionSpecificationOptionValue
					cpDefinitionSpecificationOptionValue =
						_updateProductSpecification(
							productSpecificationId, productSpecification);

				return _toProductSpecification(
					cpDefinitionSpecificationOptionValue.
						getCPDefinitionSpecificationOptionValueId());
			}
			catch (NoSuchCPDefinitionSpecificationOptionValueException
						noSuchCPDefinitionSpecificationOptionValueException) {

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Unable to find productSpecification with ID: " +
							productSpecificationId,
						noSuchCPDefinitionSpecificationOptionValueException);
				}
			}
		}

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				ProductSpecificationUtil.
					addCPDefinitionSpecificationOptionValue(
						_cpDefinitionSpecificationOptionValueService,
						_cpSpecificationOptionService, id, productSpecification,
						_serviceContextHelper.getServiceContext());

		return _toProductSpecification(
			cpDefinitionSpecificationOptionValue.
				getCPDefinitionSpecificationOptionValueId());
	}

	private ProductSpecification _toProductSpecification(
			Long cpDefinitionSpecificationOptionValueId)
		throws Exception {

		return _productSpecificationDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionSpecificationOptionValueId,
				contextAcceptLanguage.getPreferredLocale()));
	}

	private CPDefinitionSpecificationOptionValue _updateProductSpecification(
			Long id, ProductSpecification productSpecification)
		throws PortalException {

		CPDefinitionSpecificationOptionValue
			cpDefinitionSpecificationOptionValue =
				_cpDefinitionSpecificationOptionValueService.
					getCPDefinitionSpecificationOptionValue(id);

		return ProductSpecificationUtil.
			updateCPDefinitionSpecificationOptionValue(
				_cpDefinitionSpecificationOptionValueService,
				cpDefinitionSpecificationOptionValue, productSpecification,
				_serviceContextHelper.getServiceContext());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProductSpecificationResourceImpl.class);

	@Reference
	private CPDefinitionSpecificationOptionValueService
		_cpDefinitionSpecificationOptionValueService;

	@Reference
	private CPSpecificationOptionService _cpSpecificationOptionService;

	@Reference
	private ProductSpecificationDTOConverter _productSpecificationDTOConverter;

	@Reference
	private ProductSpecificationHelper _productSpecificationHelper;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
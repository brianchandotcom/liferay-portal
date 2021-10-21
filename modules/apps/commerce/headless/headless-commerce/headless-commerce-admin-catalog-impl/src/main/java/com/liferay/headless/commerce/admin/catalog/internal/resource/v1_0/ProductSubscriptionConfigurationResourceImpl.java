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
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.ProductSubscriptionConfiguration;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.ProductSubscriptionConfigurationDTOConverter;
import com.liferay.headless.commerce.admin.catalog.internal.util.v1_0.ProductSubscriptionConfigurationUtil;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.ProductSubscriptionConfigurationResource;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	properties = "OSGI-INF/liferay/rest/v1_0/product-subscription-configuration.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {
		NestedFieldSupport.class, ProductSubscriptionConfigurationResource.class
	}
)
@CTAware
public class ProductSubscriptionConfigurationResourceImpl
	extends BaseProductSubscriptionConfigurationResourceImpl
	implements NestedFieldSupport {

	@Override
	public ProductSubscriptionConfiguration
			getProductByExternalReferenceCodeSubscriptionConfiguration(
				String externalReferenceCode)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					externalReferenceCode, contextCompany.getCompanyId());

		return _toProductSubscriptionConfiguration(
			cpDefinition.getCPDefinitionId());
	}

	@NestedField(
		parentClass = Product.class, value = "subscriptionConfiguration"
	)
	@Override
	public ProductSubscriptionConfiguration
			getProductIdSubscriptionConfiguration(
				@NestedFieldId(value = "productId") Long id)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		return _toProductSubscriptionConfiguration(
			cpDefinition.getCPDefinitionId());
	}

	@Override
	public Response
			patchProductByExternalReferenceCodeSubscriptionConfiguration(
				String externalReferenceCode,
				ProductSubscriptionConfiguration
					productSubscriptionConfiguration)
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

		_updateProductSubscriptionConfiguration(
			cpDefinition, productSubscriptionConfiguration);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	@Override
	public Response patchProductIdSubscriptionConfiguration(
			Long id,
			ProductSubscriptionConfiguration productSubscriptionConfiguration)
		throws Exception {

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(id);

		if (cpDefinition == null) {
			throw new NoSuchCPDefinitionException(
				"Unable to find Product with ID: " + id);
		}

		_updateProductSubscriptionConfiguration(
			cpDefinition, productSubscriptionConfiguration);

		Response.ResponseBuilder responseBuilder = Response.ok();

		return responseBuilder.build();
	}

	private ProductSubscriptionConfiguration
			_toProductSubscriptionConfiguration(Long cpDefinitionId)
		throws Exception {

		return _productSubscriptionConfigurationDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpDefinitionId, contextAcceptLanguage.getPreferredLocale()));
	}

	private ProductSubscriptionConfiguration
			_updateProductSubscriptionConfiguration(
				CPDefinition cpDefinition,
				ProductSubscriptionConfiguration
					productSubscriptionConfiguration)
		throws Exception {

		cpDefinition =
			ProductSubscriptionConfigurationUtil.
				updateCPDefinitionSubscriptionInfo(
					_cpDefinitionService, productSubscriptionConfiguration,
					cpDefinition,
					_serviceContextHelper.getServiceContext(
						cpDefinition.getGroupId()));

		return _toProductSubscriptionConfiguration(
			cpDefinition.getCPDefinitionId());
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private ProductSubscriptionConfigurationDTOConverter
		_productSubscriptionConfigurationDTOConverter;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}
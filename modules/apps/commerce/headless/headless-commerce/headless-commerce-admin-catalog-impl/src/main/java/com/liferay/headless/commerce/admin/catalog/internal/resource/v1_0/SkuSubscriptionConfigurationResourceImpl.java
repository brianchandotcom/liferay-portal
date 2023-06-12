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

import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuSubscriptionConfiguration;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.SkuSubscriptionConfigurationDTOConverter;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.SkuSubscriptionConfigurationResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;
import com.liferay.portal.vulcan.fields.NestedFieldSupport;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Crescenzo Rega
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/sku-subscription-configuration.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {
		NestedFieldSupport.class, SkuSubscriptionConfigurationResource.class
	}
)
public class SkuSubscriptionConfigurationResourceImpl
	extends BaseSkuSubscriptionConfigurationResourceImpl
	implements NestedFieldSupport {

	@Override
	public SkuSubscriptionConfiguration
			getSkuByExternalReferenceCodeSkuSubscriptionConfiguration(
				String externalReferenceCode)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.fetchByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		if (cpInstance == null) {
			throw new NoSuchCPInstanceException(
				"Unable to find sku with external reference code: " +
					externalReferenceCode);
		}

		return _toSkuSubscriptionConfiguration(cpInstance.getCPInstanceId());
	}

	@NestedField(
		parentClass = Sku.class, value = "skuSubscriptionConfiguration"
	)
	@Override
	public SkuSubscriptionConfiguration getSkuIdSkuSubscriptionConfiguration(
			@NestedFieldId(value = "id") Long id)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(id);

		return _toSkuSubscriptionConfiguration(cpInstance.getCPInstanceId());
	}

	private SkuSubscriptionConfiguration _toSkuSubscriptionConfiguration(
			Long cpInstanceId)
		throws Exception {

		return _skuSubscriptionConfigurationDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				contextAcceptLanguage.isAcceptAllLanguages(), null,
				_dtoConverterRegistry, cpInstanceId,
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser));
	}

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private SkuSubscriptionConfigurationDTOConverter
		_skuSubscriptionConfigurationDTOConverter;

}
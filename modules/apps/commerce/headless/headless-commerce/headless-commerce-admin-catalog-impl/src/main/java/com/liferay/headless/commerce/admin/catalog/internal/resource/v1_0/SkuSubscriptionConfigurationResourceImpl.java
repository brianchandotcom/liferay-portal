/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Crescenzo Rega
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/sku-subscription-configuration.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = SkuSubscriptionConfigurationResource.class
)
public class SkuSubscriptionConfigurationResourceImpl
	extends BaseSkuSubscriptionConfigurationResourceImpl {

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
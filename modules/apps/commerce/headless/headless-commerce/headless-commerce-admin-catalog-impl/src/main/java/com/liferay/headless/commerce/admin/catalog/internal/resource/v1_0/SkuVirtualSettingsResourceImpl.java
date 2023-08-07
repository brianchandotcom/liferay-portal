/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.resource.v1_0;

import com.liferay.commerce.product.exception.NoSuchCPInstanceException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Sku;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.SkuVirtualSettings;
import com.liferay.headless.commerce.admin.catalog.resource.v1_0.SkuVirtualSettingsResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/sku-virtual-settings.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = SkuVirtualSettingsResource.class
)
public class SkuVirtualSettingsResourceImpl
	extends BaseSkuVirtualSettingsResourceImpl {

	@Override
	public SkuVirtualSettings getSkuByExternalReferenceCodeSkuVirtualSettings(
			String externalReferenceCode)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.fetchByExternalReferenceCode(
			externalReferenceCode, contextCompany.getCompanyId());

		if (cpInstance == null) {
			throw new NoSuchCPInstanceException(
				"Unable to find SKU with external reference code " +
					externalReferenceCode);
		}

		return _skuVirtualSettingsDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpInstance.getCPInstanceId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = Sku.class, value = "skuVirtualSettings")
	@Override
	public SkuVirtualSettings getSkuIdSkuVirtualSettings(
			@NestedFieldId(value = "id") Long id)
		throws Exception {

		CPInstance cpInstance = _cpInstanceService.getCPInstance(id);

		return _skuVirtualSettingsDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpInstance.getCPInstanceId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter.SkuVirtualSettingsDTOConverter)"
	)
	private DTOConverter<CPInstance, SkuVirtualSettings>
		_skuVirtualSettingsDTOConverter;

}
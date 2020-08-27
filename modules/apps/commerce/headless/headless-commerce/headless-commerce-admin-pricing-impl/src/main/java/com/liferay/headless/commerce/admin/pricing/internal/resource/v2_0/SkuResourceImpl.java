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

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Sku;
import com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.SkuDTOConverter;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.SkuResource;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;
import com.liferay.portal.vulcan.fields.NestedFieldId;

import javax.validation.constraints.NotNull;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/sku.properties",
	scope = ServiceScope.PROTOTYPE, service = SkuResource.class
)
public class SkuResourceImpl extends BaseSkuResourceImpl {

	@NestedField(parentClass = PriceEntry.class, value = "sku")
	@Override
	public Sku getPriceEntryIdSku(
			@NestedFieldId(value = "skuId") @NotNull Long id)
		throws Exception {

		return _skuDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				id, contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private SkuDTOConverter _skuDTOConverter;

}
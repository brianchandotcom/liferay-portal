/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.resource.v2_0;

import com.liferay.commerce.discount.model.CommerceDiscountRel;
import com.liferay.commerce.discount.service.CommerceDiscountRelService;
import com.liferay.commerce.price.list.model.CommercePriceEntry;
import com.liferay.commerce.price.list.service.CommercePriceEntryService;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.DiscountSku;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.PriceEntry;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.Sku;
import com.liferay.headless.commerce.admin.pricing.resource.v2_0.SkuResource;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DefaultDTOConverterContext;
import com.liferay.portal.vulcan.fields.NestedField;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Zoltán Takács
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v2_0/sku.properties",
	property = "nested.field.support=true", scope = ServiceScope.PROTOTYPE,
	service = SkuResource.class
)
public class SkuResourceImpl extends BaseSkuResourceImpl {

	@NestedField(parentClass = DiscountSku.class, value = "sku")
	@Override
	public Sku getDiscountSkuSku(Long id) throws Exception {
		CommerceDiscountRel commerceDiscountRel =
			_commerceDiscountRelService.getCommerceDiscountRel(id);

		return _skuDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				commerceDiscountRel.getClassPK(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@NestedField(parentClass = PriceEntry.class, value = "sku")
	@Override
	public Sku getPriceEntryIdSku(Long id) throws Exception {
		CommercePriceEntry commercePriceEntry =
			_commercePriceEntryService.getCommercePriceEntry(id);

		CPInstance cpInstance = commercePriceEntry.getCPInstance();

		return _skuDTOConverter.toDTO(
			new DefaultDTOConverterContext(
				cpInstance.getCPInstanceId(),
				contextAcceptLanguage.getPreferredLocale()));
	}

	@Reference
	private CommerceDiscountRelService _commerceDiscountRelService;

	@Reference
	private CommercePriceEntryService _commercePriceEntryService;

	@Reference(
		target = "(component.name=com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter.SkuDTOConverter)"
	)
	private DTOConverter<CPInstance, Sku> _skuDTOConverter;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.pricing.internal.dto.v2_0.converter;

import com.liferay.commerce.pricing.model.CommercePricingClass;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelService;
import com.liferay.commerce.pricing.service.CommercePricingClassService;
import com.liferay.headless.commerce.admin.pricing.dto.v2_0.ProductGroup;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.pricing.model.CommercePricingClass",
	service = DTOConverter.class
)
public class ProductGroupDTOConverter
	implements DTOConverter<CommercePricingClass, ProductGroup> {

	@Override
	public String getContentType() {
		return ProductGroup.class.getSimpleName();
	}

	@Override
	public ProductGroup toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CommercePricingClass commercePricingClass =
			_commercePricingClassService.getCommercePricingClass(
				(Long)dtoConverterContext.getId());

		return new ProductGroup() {
			{
				id = commercePricingClass.getCommercePricingClassId();
				productsCount = _getProductsCount(
					commercePricingClass.getCommercePricingClassId());
				title = LanguageUtils.getLanguageIdMap(
					commercePricingClass.getTitleMap());
			}
		};
	}

	private int _getProductsCount(long commercePricingClassId)
		throws Exception {

		return _commercePricingClassCPDefinitionRelService.
			getCommercePricingClassCPDefinitionRelsCount(
				commercePricingClassId);
	}

	@Reference
	private CommercePricingClassCPDefinitionRelService
		_commercePricingClassCPDefinitionRelService;

	@Reference
	private CommercePricingClassService _commercePricingClassService;

}
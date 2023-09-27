/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPOptionService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Option;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.model.CPOption",
	service = DTOConverter.class
)
public class OptionDTOConverter implements DTOConverter<CPOption, Option> {

	@Override
	public String getContentType() {
		return Option.class.getSimpleName();
	}

	@Override
	public Option toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPOption cpOption = _cpOptionService.getCPOption(
			(Long)dtoConverterContext.getId());

		return new Option() {
			{
				actions = dtoConverterContext.getActions();
				description = LanguageUtils.getLanguageIdMap(
					cpOption.getDescriptionMap());
				externalReferenceCode = cpOption.getExternalReferenceCode();
				facetable = cpOption.isFacetable();
				fieldType = Option.FieldType.create(
					cpOption.getCommerceOptionTypeKey());
				id = cpOption.getCPOptionId();
				key = cpOption.getKey();
				name = LanguageUtils.getLanguageIdMap(cpOption.getNameMap());
				required = cpOption.isRequired();
				skuContributor = cpOption.isSkuContributor();
			}
		};
	}

	@Reference
	private CPOptionService _cpOptionService;

}
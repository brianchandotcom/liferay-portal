/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.GroupedProduct;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry",
	service = DTOConverter.class
)
public class GroupedProductDTOConverter
	implements DTOConverter<CPDefinitionGroupedEntry, GroupedProduct> {

	@Override
	public String getContentType() {
		return GroupedProduct.class.getSimpleName();
	}

	@Override
	public GroupedProduct toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
			_cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntry(
				(Long)dtoConverterContext.getId());

		CPDefinition cpDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinitionGroupedEntry.getCPDefinitionId());

		CProduct cProduct = cpDefinition.getCProduct();

		CPDefinition entryCPDefinition = _cpDefinitionService.getCPDefinition(
			cpDefinitionGroupedEntry.getEntryCPDefinitionId());

		CProduct entryCProduct = entryCPDefinition.getCProduct();

		return new GroupedProduct() {
			{
				entryProductExternalReferenceCode =
					entryCProduct.getExternalReferenceCode();
				entryProductId = entryCProduct.getCProductId();
				entryProductName = LanguageUtils.getLanguageIdMap(
					entryCPDefinition.getNameMap());
				id = cpDefinitionGroupedEntry.getCPDefinitionGroupedEntryId();
				priority = cpDefinitionGroupedEntry.getPriority();
				productExternalReferenceCode =
					cProduct.getExternalReferenceCode();
				productId = cProduct.getCProductId();
				productName = LanguageUtils.getLanguageIdMap(
					cpDefinition.getNameMap());
				quantity = cpDefinitionGroupedEntry.getQuantity();
			}
		};
	}

	@Reference
	private CPDefinitionGroupedEntryService _cpDefinitionGroupedEntryService;

	@Reference
	private CPDefinitionService _cpDefinitionService;

}
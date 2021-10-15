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

package com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.converter;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.MappedProduct;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry",
	service = {DTOConverter.class, MappedProductDTOConverter.class}
)
public class MappedProductDTOConverter
	implements DTOConverter<CSDiagramEntry, MappedProduct> {

	@Override
	public String getContentType() {
		return MappedProduct.class.getSimpleName();
	}

	@Override
	public MappedProduct toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		CSDiagramEntry csDiagramEntry =
			_csDiagramEntryService.getCSDiagramEntry(
				(Long)dtoConverterContext.getId());

		CPDefinition cpDefinition =
			_cpDefinitionService.fetchCPDefinitionByCProductId(
				csDiagramEntry.getCProductId());

		return new MappedProduct() {
			{
				actions = dtoConverterContext.getActions();
				customFields = CustomFieldsUtil.toCustomFields(
					dtoConverterContext.isAcceptAllLanguages(),
					CSDiagramEntry.class.getName(),
					csDiagramEntry.getCSDiagramEntryId(),
					csDiagramEntry.getCompanyId(),
					dtoConverterContext.getLocale());
				id = csDiagramEntry.getCSDiagramEntryId();
				productId = csDiagramEntry.getCProductId();
				quantity = csDiagramEntry.getQuantity();
				sequence = csDiagramEntry.getSequence();
				sku = csDiagramEntry.getSku();
				skuId = GetterUtil.getLong(csDiagramEntry.getCPInstanceId());

				setProductExternalReferenceCode(
					() -> {
						if (cpDefinition == null) {
							return StringPool.BLANK;
						}

						CProduct cProduct = cpDefinition.getCProduct();

						return cProduct.getExternalReferenceCode();
					});
				setProductName(
					() -> {
						if (cpDefinition == null) {
							return null;
						}

						return LanguageUtils.getLanguageIdMap(
							cpDefinition.getNameMap());
					});
				setSkuExternalReferenceCode(
					() -> {
						CPInstance cpInstance =
							_cpInstanceService.fetchCPInstance(
								GetterUtil.getLong(
									csDiagramEntry.getCPInstanceId()));

						if (cpInstance == null) {
							return StringPool.BLANK;
						}

						return cpInstance.getExternalReferenceCode();
					});
				setType(
					() -> {
						if (csDiagramEntry.isDiagram()) {
							return MappedProduct.Type.create(
								Type.DIAGRAM.getValue());
						}

						if (csDiagramEntry.getCPInstanceId() > 0) {
							return MappedProduct.Type.create(
								Type.SKU.getValue());
						}

						return MappedProduct.Type.create(
							Type.EXTERNAL.getValue());
					});
			}
		};
	}

	@Reference
	private CPDefinitionService _cpDefinitionService;

	@Reference
	private CPInstanceService _cpInstanceService;

	@Reference
	private CSDiagramEntryService _csDiagramEntryService;

}
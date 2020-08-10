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

package com.liferay.headless.commerce.delivery.catalog.internal.dto.v1_0.converter;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "model.class.name=CPDefinition",
	service = {DTOConverter.class, ProductDTOConverter.class}
)
public class ProductDTOConverter
	implements DTOConverter<CPDefinition, Product> {

	@Override
	public String getContentType() {
		return Product.class.getSimpleName();
	}

	public Product toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		ProductDTOConverterContext cpCatalogEntryDTOConverterConvertContext =
			(ProductDTOConverterContext)dtoConverterContext;

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			(Long)cpCatalogEntryDTOConverterConvertContext.getId());

		String languageId = LanguageUtil.getLanguageId(
			cpCatalogEntryDTOConverterConvertContext.getLocale());

		ExpandoBridge expandoBridge = cpDefinition.getExpandoBridge();

		Company company = _companyLocalService.getCompany(
			cpDefinition.getCompanyId());

		String portalURL = company.getPortalURL(0);

		return new Product() {
			{
				createDate = cpDefinition.getCreateDate();
				description = cpDefinition.getDescription();
				expando = expandoBridge.getAttributes();
				id = cpDefinition.getCPDefinitionId();
				metaDescription = cpDefinition.getMetaDescription(languageId);
				metaKeyword = cpDefinition.getMetaKeywords(languageId);
				metaTitle = cpDefinition.getMetaTitle(languageId);
				modifiedDate = cpDefinition.getModifiedDate();
				name = cpDefinition.getName();
				productId = cpDefinition.getCProductId();
				productType = cpDefinition.getProductTypeName();
				shortDescription = cpDefinition.getShortDescription();
				slug = cpDefinition.getURL(languageId);
				tags = _getTags(cpDefinition);
				urlImage = portalURL + cpDefinition.getDefaultImageFileURL();
			}
		};
	}

	private String[] _getTags(CPDefinition cpDefinition) {
		List<AssetTag> assetEntryAssetTags = _assetTagLocalService.getTags(
			cpDefinition.getModelClassName(), cpDefinition.getCPDefinitionId());

		Stream<AssetTag> stream = assetEntryAssetTags.stream();

		return stream.map(
			AssetTag::getName
		).toArray(
			String[]::new
		);
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

}
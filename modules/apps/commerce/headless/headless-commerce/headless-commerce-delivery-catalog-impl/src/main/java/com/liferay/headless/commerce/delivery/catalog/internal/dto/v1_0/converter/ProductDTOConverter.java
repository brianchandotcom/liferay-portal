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
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.util.CPDefinitionHelper;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.commerce.util.CommerceUtil;
import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.headless.commerce.core.util.LanguageUtils;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.Product;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.ProductConfiguration;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(
	property = "dto.class.name=CPDefinition", service = DTOConverter.class
)
public class ProductDTOConverter
	implements DTOConverter<CPDefinition, Product> {

	@Override
	public String getContentType() {
		return Product.class.getSimpleName();
	}

	@Override
	public Product toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		ProductDTOConverterContext productDTOConverterContext =
			(ProductDTOConverterContext)dtoConverterContext;

		CPDefinition cpDefinition = _cpDefinitionLocalService.getCPDefinition(
			(Long)productDTOConverterContext.getId());

		String languageId = _language.getLanguageId(
			productDTOConverterContext.getLocale());

		ExpandoBridge expandoBridge = cpDefinition.getExpandoBridge();

		Product product = new Product() {
			{
				createDate = cpDefinition.getCreateDate();
				description = cpDefinition.getDescription(languageId);
				expando = expandoBridge.getAttributes();
				id = cpDefinition.getCPDefinitionId();
				metaDescription = cpDefinition.getMetaDescription(languageId);
				metaKeyword = cpDefinition.getMetaKeywords(languageId);
				metaTitle = cpDefinition.getMetaTitle(languageId);
				modifiedDate = cpDefinition.getModifiedDate();
				name = cpDefinition.getName(languageId);
				productId = cpDefinition.getCProductId();
				productType = cpDefinition.getProductTypeName();
				shortDescription = cpDefinition.getShortDescription(languageId);
				slug = cpDefinition.getURL(languageId);
				tags = TransformUtil.transformToArray(
					_assetTagLocalService.getTags(
						cpDefinition.getModelClassName(),
						cpDefinition.getCPDefinitionId()),
					AssetTag::getName, String.class);
				urls = LanguageUtils.getLanguageIdMap(
					_cpDefinitionLocalService.getUrlTitleMap(
						cpDefinition.getCPDefinitionId()));

				setExternalReferenceCode(
					() -> {
						CProduct cProduct = cpDefinition.getCProduct();

						return cProduct.getExternalReferenceCode();
					});
				setUrlImage(
					() -> {
						Company company = _companyLocalService.getCompany(
							cpDefinition.getCompanyId());

						String portalURL = _portal.getPortalURL(
							company.getVirtualHostname(),
							_portal.getPortalServerPort(false), true);

						String defaultImageFileURL =
							_cpDefinitionHelper.getDefaultImageFileURL(
								CommerceUtil.getCommerceAccountId(
									productDTOConverterContext.
										getCommerceContext()),
								cpDefinition.getCPDefinitionId());

						return portalURL + defaultImageFileURL;
					});
			}
		};

		CPDefinitionInventory cpDefinitionInventory =
			_cpDefinitionInventoryLocalService.
				fetchCPDefinitionInventoryByCPDefinitionId(
					(Long)productDTOConverterContext.getId());

		if (cpDefinitionInventory != null) {
			ProductConfiguration productConfiguration =
				new ProductConfiguration() {
					{
						allowBackOrder = cpDefinitionInventory.isBackOrders();
						allowedOrderQuantities = ArrayUtil.toArray(
							cpDefinitionInventory.
								getAllowedOrderQuantitiesArray());
						inventoryEngine =
							cpDefinitionInventory.
								getCPDefinitionInventoryEngine();
						maxOrderQuantity =
							cpDefinitionInventory.getMaxOrderQuantity();
						minOrderQuantity =
							cpDefinitionInventory.getMinOrderQuantity();
						multipleOrderQuantity =
							cpDefinitionInventory.getMultipleOrderQuantity();
					}
				};

			product.setProductConfiguration(productConfiguration);
		}

		return product;
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CPDefinitionHelper _cpDefinitionHelper;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}
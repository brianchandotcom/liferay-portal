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

import com.liferay.commerce.product.exception.CPDefinitionProductTypeNameException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CProduct;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.permission.CommerceProductViewPermission;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CommerceChannelLocalService;
import com.liferay.commerce.product.type.grouped.constants.GroupedCPTypeConstants;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalService;
import com.liferay.commerce.shop.by.diagram.constants.CSDiagramCPTypeConstants;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.headless.commerce.delivery.catalog.dto.v1_0.LinkedProduct;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = "dto.class.name=com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry",
	service = DTOConverter.class
)
public class LinkedProductDTOConverter
	implements DTOConverter<CPDefinitionGroupedEntry, LinkedProduct> {

	@Override
	public String getContentType() {
		return LinkedProduct.class.getSimpleName();
	}

	@Override
	public LinkedProduct toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		LinkedProductDTOConverterContext linkedProductDTOConverterContext =
			(LinkedProductDTOConverterContext)dtoConverterContext;

		CommerceChannel commerceChannel =
			_commerceChannelLocalService.getCommerceChannel(
				linkedProductDTOConverterContext.getChannelId());

		CPDefinition cpDefinition = _getCPDefinition(
			linkedProductDTOConverterContext.getAccountId(),
			commerceChannel.getGroupId(),
			(Long)linkedProductDTOConverterContext.getId(),
			linkedProductDTOConverterContext.getProductTypeName());

		if (cpDefinition == null) {
			return null;
		}

		CProduct cProduct = cpDefinition.getCProduct();

		return new LinkedProduct() {
			{
				productExternalReferenceCode =
					cProduct.getExternalReferenceCode();
				productId = cProduct.getCProductId();
				type = cpDefinition.getProductTypeName();
			}
		};
	}

	private CPDefinition _getCPDefinition(
			long accountId, long commerceChannelGroupId, long id,
			String productTypeName)
		throws Exception {

		if (CSDiagramCPTypeConstants.NAME.equals(productTypeName)) {
			CSDiagramEntry csDiagramEntry =
				_csDiagramEntryLocalService.getCSDiagramEntry(id);

			if (_commerceProductViewPermission.contains(
					PermissionThreadLocal.getPermissionChecker(), accountId,
					commerceChannelGroupId,
					csDiagramEntry.getCPDefinitionId())) {

				return _cpDefinitionLocalService.getCPDefinition(
					csDiagramEntry.getCPDefinitionId());
			}

			return null;
		}

		if (GroupedCPTypeConstants.NAME.equals(productTypeName)) {
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
				_cpDefinitionGroupedEntryLocalService.
					getCPDefinitionGroupedEntry(id);

			if (_commerceProductViewPermission.contains(
					PermissionThreadLocal.getPermissionChecker(), accountId,
					commerceChannelGroupId,
					cpDefinitionGroupedEntry.getCPDefinitionId())) {

				return _cpDefinitionLocalService.getCPDefinition(
					cpDefinitionGroupedEntry.getCPDefinitionId());
			}

			return null;
		}

		throw new CPDefinitionProductTypeNameException();
	}

	@Reference
	private CommerceChannelLocalService _commerceChannelLocalService;

	@Reference
	private CommerceProductViewPermission _commerceProductViewPermission;

	@Reference
	private CPDefinitionGroupedEntryLocalService
		_cpDefinitionGroupedEntryLocalService;

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CSDiagramEntryLocalService _csDiagramEntryLocalService;

}
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

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.MappedProduct;
import com.liferay.headless.commerce.admin.catalog.internal.dto.v1_0.util.CustomFieldsUtil;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Alessio Antonio Rendina
 */
public class MappedProductUtil {

	public static CSDiagramEntry addCSDiagramEntry(
			long companyId, long cpDefinitionId,
			CPDefinitionService cpDefinitionService,
			CPInstanceService cpInstanceService,
			CSDiagramEntryService csDiagramEntryService, long groupId,
			Locale locale, MappedProduct mappedProduct,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		long skuId = GetterUtil.getLong(mappedProduct.getSkuId());

		CPInstance cpInstance = cpInstanceService.fetchByExternalReferenceCode(
			mappedProduct.getSkuExternalReferenceCode(), companyId);

		if (cpInstance != null) {
			skuId = cpInstance.getCPInstanceId();
		}

		long productId = GetterUtil.getLong(mappedProduct.getProductId());

		CPDefinition cpDefinition =
			cpDefinitionService.
				fetchCPDefinitionByCProductExternalReferenceCode(
					mappedProduct.getProductExternalReferenceCode(), companyId);

		if (cpDefinition != null) {
			productId = cpDefinition.getCProductId();
		}

		ServiceContext serviceContext = serviceContextHelper.getServiceContext(
			groupId);

		Map<String, Serializable> expandoBridgeAttributes =
			getExpandoBridgeAttributes(companyId, locale, mappedProduct);

		if (expandoBridgeAttributes != null) {
			serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);
		}

		return csDiagramEntryService.addCSDiagramEntry(
			cpDefinitionId, skuId, productId, isDiagram(null, mappedProduct),
			GetterUtil.getInteger(mappedProduct.getQuantity()),
			GetterUtil.getString(mappedProduct.getSequence()),
			GetterUtil.getString(mappedProduct.getSku()), serviceContext);
	}

	public static CSDiagramEntry addOrUpdateCSDiagramEntry(
			long companyId, long cpDefinitionId,
			CPDefinitionService cpDefinitionService,
			CPInstanceService cpInstanceService,
			CSDiagramEntryService csDiagramEntryService, long groupId,
			Locale locale, MappedProduct mappedProduct,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		CSDiagramEntry csDiagramEntry =
			csDiagramEntryService.fetchCSDiagramEntry(
				cpDefinitionId, mappedProduct.getSequence());

		if (csDiagramEntry == null) {
			return addCSDiagramEntry(
				companyId, cpDefinitionId, cpDefinitionService,
				cpInstanceService, csDiagramEntryService, groupId, locale,
				mappedProduct, serviceContextHelper);
		}

		return updateCSDiagramEntry(
			companyId, csDiagramEntry, csDiagramEntryService, groupId, locale,
			mappedProduct, serviceContextHelper);
	}

	public static Map<String, Serializable> getExpandoBridgeAttributes(
		long companyId, Locale locale, MappedProduct mappedProduct) {

		return CustomFieldsUtil.toMap(
			CSDiagramEntry.class.getName(), companyId,
			mappedProduct.getCustomFields(), locale);
	}

	public static boolean isDiagram(
		CSDiagramEntry csDiagramEntry, MappedProduct mappedProduct) {

		if ((csDiagramEntry == null) && (mappedProduct.getType() == null)) {
			return false;
		}

		if (mappedProduct.getType() != null) {
			return Objects.equals(
				MappedProduct.Type.DIAGRAM.getValue(),
				mappedProduct.getTypeAsString());
		}

		return csDiagramEntry.isDiagram();
	}

	public static CSDiagramEntry updateCSDiagramEntry(
			long companyId, CSDiagramEntry csDiagramEntry,
			CSDiagramEntryService csDiagramEntryService, long groupId,
			Locale locale, MappedProduct mappedProduct,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext = serviceContextHelper.getServiceContext(
			groupId);

		Map<String, Serializable> expandoBridgeAttributes =
			getExpandoBridgeAttributes(companyId, locale, mappedProduct);

		if (expandoBridgeAttributes != null) {
			serviceContext.setExpandoBridgeAttributes(expandoBridgeAttributes);
		}

		return csDiagramEntryService.updateCSDiagramEntry(
			csDiagramEntry.getCSDiagramEntryId(),
			GetterUtil.get(
				mappedProduct.getSkuId(), csDiagramEntry.getCPInstanceId()),
			GetterUtil.get(
				mappedProduct.getProductId(), csDiagramEntry.getCProductId()),
			isDiagram(csDiagramEntry, mappedProduct),
			GetterUtil.get(
				mappedProduct.getQuantity(), csDiagramEntry.getQuantity()),
			GetterUtil.get(
				mappedProduct.getSequence(), csDiagramEntry.getSequence()),
			GetterUtil.get(mappedProduct.getSku(), csDiagramEntry.getSku()),
			serviceContext);
	}

}
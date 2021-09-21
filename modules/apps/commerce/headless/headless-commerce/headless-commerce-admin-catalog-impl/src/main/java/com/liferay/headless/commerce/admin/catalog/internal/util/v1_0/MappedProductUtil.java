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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class MappedProductUtil {

	public static CSDiagramEntry addCSDiagramEntry(
			long companyId, long cpDefinitionId,
			CPDefinitionService cpDefinitionService,
			CPInstanceService cpInstanceService,
			CSDiagramEntryService csDiagramEntryService,
			MappedProduct mappedProduct)
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

		return csDiagramEntryService.addCSDiagramEntry(
			cpDefinitionId, skuId, productId,
			GetterUtil.getBoolean(mappedProduct.getDiagram()),
			GetterUtil.getInteger(mappedProduct.getQuantity()),
			GetterUtil.getString(mappedProduct.getSequence()),
			GetterUtil.getString(mappedProduct.getSku()), new ServiceContext());
	}

	public static CSDiagramEntry addOrUpdateCSDiagramEntry(
			long companyId, long cpDefinitionId,
			CPDefinitionService cpDefinitionService,
			CPInstanceService cpInstanceService,
			CSDiagramEntryService csDiagramEntryService,
			MappedProduct mappedProduct)
		throws PortalException {

		CSDiagramEntry csDiagramEntry =
			csDiagramEntryService.fetchCSDiagramEntry(
				cpDefinitionId, mappedProduct.getSequence());

		if (csDiagramEntry == null) {
			return addCSDiagramEntry(
				companyId, cpDefinitionId, cpDefinitionService,
				cpInstanceService, csDiagramEntryService, mappedProduct);
		}

		return updateCSDiagramEntry(
			csDiagramEntry, csDiagramEntryService, mappedProduct);
	}

	public static CSDiagramEntry updateCSDiagramEntry(
			CSDiagramEntry csDiagramEntry,
			CSDiagramEntryService csDiagramEntryService,
			MappedProduct mappedProduct)
		throws PortalException {

		return csDiagramEntryService.updateCSDiagramEntry(
			csDiagramEntry.getCSDiagramEntryId(),
			GetterUtil.get(
				mappedProduct.getSkuId(), csDiagramEntry.getCPInstanceId()),
			GetterUtil.get(
				mappedProduct.getProductId(), csDiagramEntry.getCProductId()),
			GetterUtil.get(
				mappedProduct.getDiagram(), csDiagramEntry.isDiagram()),
			GetterUtil.get(
				mappedProduct.getQuantity(), csDiagramEntry.getQuantity()),
			GetterUtil.get(
				mappedProduct.getSequence(), csDiagramEntry.getSequence()),
			GetterUtil.get(mappedProduct.getSku(), csDiagramEntry.getSku()),
			new ServiceContext());
	}

}
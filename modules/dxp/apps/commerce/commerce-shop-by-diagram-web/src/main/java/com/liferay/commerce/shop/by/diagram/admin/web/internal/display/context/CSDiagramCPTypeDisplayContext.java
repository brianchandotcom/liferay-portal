/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.shop.by.diagram.admin.web.internal.display.context;

import com.liferay.commerce.product.catalog.CPCatalogEntry;
import com.liferay.commerce.shop.by.diagram.admin.web.internal.util.CSDiagramSettingUtil;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingService;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramType;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramTypeRegistry;
import com.liferay.document.library.util.DLURLHelper;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Andrea Sbarra
 */
public class CSDiagramCPTypeDisplayContext {

	public CSDiagramCPTypeDisplayContext(
		CSDiagramSettingService csDiagramSettingService,
		CSDiagramTypeRegistry csDiagramTypeRegistry, DLURLHelper dlURLHelper) {

		_csDiagramSettingService = csDiagramSettingService;
		_csDiagramTypeRegistry = csDiagramTypeRegistry;
		_dlURLHelper = dlURLHelper;
	}

	public String getCSDiagramMappedProductsAPIURL(
		CPCatalogEntry cpCatalogEntry) {

		return "/o/headless-commerce-admin-catalog/v1.0/products/" +
			cpCatalogEntry.getCProductId() + "/mapped-products";
	}

	public CSDiagramSetting getCSDiagramSetting(long cpDefinitionId)
		throws PortalException {

		return _csDiagramSettingService.fetchCSDiagramSettingByCPDefinitionId(
			cpDefinitionId);
	}

	public CSDiagramType getCSDiagramType(String type) {
		return _csDiagramTypeRegistry.getCSDiagramType(type);
	}

	public String getImageURL(long cpDefinitionId) throws Exception {
		return CSDiagramSettingUtil.getImageURL(
			getCSDiagramSetting(cpDefinitionId), _dlURLHelper);
	}

	private final CSDiagramSettingService _csDiagramSettingService;
	private final CSDiagramTypeRegistry _csDiagramTypeRegistry;
	private final DLURLHelper _dlURLHelper;

}
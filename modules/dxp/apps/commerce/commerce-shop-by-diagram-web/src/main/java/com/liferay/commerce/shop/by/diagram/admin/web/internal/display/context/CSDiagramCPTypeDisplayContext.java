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
import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramType;
import com.liferay.commerce.shop.by.diagram.util.CSDiagramCPTypeHelper;
import com.liferay.portal.kernel.exception.PortalException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Sbarra
 */
public class CSDiagramCPTypeDisplayContext {

	public CSDiagramCPTypeDisplayContext(
		CSDiagramCPTypeHelper csDiagramCPTypeHelper,
		HttpServletRequest httpServletRequest) {

		_csDiagramCPTypeHelper = csDiagramCPTypeHelper;

		cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public String getCSDiagramMappedProductsAPIURL(
		CPCatalogEntry cpCatalogEntry) {

		return "/o/headless-commerce-admin-catalog/v1.0/products/" +
			cpCatalogEntry.getCProductId() + "/mapped-products";
	}

	public CSDiagramSetting getCSDiagramSetting(long cpDefinitionId)
		throws PortalException {

		return _csDiagramCPTypeHelper.getCSDiagramSetting(
			cpDefinitionId, cpRequestHelper);
	}

	public CSDiagramType getCSDiagramType(String type) {
		return _csDiagramCPTypeHelper.getCSDiagramType(type);
	}

	public String getImageURL(long cpDefinitionId) throws Exception {
		return _csDiagramCPTypeHelper.getImageURL(
			cpDefinitionId, cpRequestHelper);
	}

	protected final CPRequestHelper cpRequestHelper;

	private final CSDiagramCPTypeHelper _csDiagramCPTypeHelper;

}
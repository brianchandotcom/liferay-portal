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

package com.liferay.commerce.shop.by.diagram.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.display.context.helper.CPRequestHelper;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramType;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Danny Situ
 */
@ProviderType
public interface CSDiagramCPTypeHelper {

	public CSDiagramSetting getCSDiagramSetting(
			long cpDefinitionId, CPRequestHelper cpRequestHelper)
		throws PortalException;

	public CSDiagramType getCSDiagramType(String type);

	public String getImageURL(
			long cpDefinitionId, CPRequestHelper cpRequestHelper)
		throws Exception;

}
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

import com.liferay.commerce.account.model.CommerceAccount;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.type.CSDiagramType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Danny Situ
 * @author Crescenzo Rega
 */
@ProviderType
public interface CSDiagramCPTypeHelper {

	public FileVersion getCPDiagramImageFileVersion(
			long cpDefinitionId, CSDiagramSetting csDiagramSetting,
			HttpServletRequest httpServletRequest)
		throws Exception;

	public CSDiagramSetting getCSDiagramSetting(
			CommerceAccount commerceAccount, long cpDefinitionId,
			PermissionChecker permissionChecker)
		throws PortalException;

	public CSDiagramType getCSDiagramType(String type);

	public String getImageURL(CSDiagramSetting csDiagramSetting)
		throws Exception;

}
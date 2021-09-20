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

import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramSettingService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.Diagram;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Alessio Antonio Rendina
 */
public class DiagramUtil {

	public static CSDiagramSetting addCSDiagramSetting(
			long cpDefinitionId,
			CSDiagramSettingService csDiagramSettingService, Diagram diagram)
		throws PortalException {

		return csDiagramSettingService.addCSDiagramSetting(
			cpDefinitionId, GetterUtil.getLong(diagram.getImageId()),
			GetterUtil.getString(diagram.getColor()),
			GetterUtil.getDouble(diagram.getRadius()),
			GetterUtil.getString(diagram.getType()));
	}

	public static CSDiagramSetting addOrUpdateCSDiagramSetting(
			long cpDefinitionId,
			CSDiagramSettingService csDiagramSettingService, Diagram diagram)
		throws PortalException {

		CSDiagramSetting csDiagramSetting =
			csDiagramSettingService.fetchCSDiagramSettingByCPDefinitionId(
				cpDefinitionId);

		if (csDiagramSetting == null) {
			addOrUpdateCSDiagramSetting(
				cpDefinitionId, csDiagramSettingService, diagram);
		}

		return updateCSDiagramSetting(
			csDiagramSetting, csDiagramSettingService, diagram);
	}

	public static CSDiagramSetting updateCSDiagramSetting(
			CSDiagramSetting csDiagramSetting,
			CSDiagramSettingService csDiagramSettingService, Diagram diagram)
		throws PortalException {

		return csDiagramSettingService.updateCSDiagramSetting(
			csDiagramSetting.getCSDiagramSettingId(),
			GetterUtil.get(
				diagram.getImageId(),
				csDiagramSetting.getCPAttachmentFileEntryId()),
			GetterUtil.getString(
				diagram.getColor(), csDiagramSetting.getColor()),
			GetterUtil.getDouble(
				diagram.getRadius(), csDiagramSetting.getRadius()),
			GetterUtil.getString(
				diagram.getType(), csDiagramSetting.getType()));
	}

}
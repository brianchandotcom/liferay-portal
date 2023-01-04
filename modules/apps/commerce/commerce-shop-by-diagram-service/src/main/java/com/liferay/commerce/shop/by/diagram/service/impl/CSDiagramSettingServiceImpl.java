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

package com.liferay.commerce.shop.by.diagram.service.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.base.CSDiagramSettingServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CSDiagramSetting"
	},
	service = AopService.class
)
public class CSDiagramSettingServiceImpl
	extends CSDiagramSettingServiceBaseImpl {

	@Override
	public CSDiagramSetting addCSDiagramSetting(
			long cpDefinitionId, long cpAttachmentFileEntryId, String color,
			double radius, String type)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return csDiagramSettingLocalService.addCSDiagramSetting(
			getUserId(), cpDefinitionId, cpAttachmentFileEntryId, color, radius,
			type);
	}

	@Override
	public CSDiagramSetting fetchCSDiagramSettingByCPDefinitionId(
			long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return csDiagramSettingLocalService.
			fetchCSDiagramSettingByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public CSDiagramSetting getCSDiagramSetting(long csDiagramSettingId)
		throws PortalException {

		CSDiagramSetting csDiagramSetting =
			csDiagramSettingLocalService.getCSDiagramSetting(
				csDiagramSettingId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), csDiagramSetting.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return csDiagramSetting;
	}

	@Override
	public CSDiagramSetting getCSDiagramSettingByCPDefinitionId(
			long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return csDiagramSettingLocalService.getCSDiagramSettingByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CSDiagramSetting updateCSDiagramSetting(
			long csDiagramSettingId, long cpAttachmentFileEntryId, String color,
			double radius, String type)
		throws PortalException {

		CSDiagramSetting csDiagramSetting =
			csDiagramSettingLocalService.getCSDiagramSetting(
				csDiagramSettingId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), csDiagramSetting.getCPDefinitionId(),
			ActionKeys.UPDATE);

		return csDiagramSettingLocalService.updateCSDiagramSetting(
			csDiagramSetting.getCSDiagramSettingId(), cpAttachmentFileEntryId,
			color, radius, type);
	}

	@Reference(
		target = "(model.class.name=com.liferay.commerce.product.model.CPDefinition)"
	)
	private ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission;

}
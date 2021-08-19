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

package com.liferay.commerce.shop.by.diagram.service.impl;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSetting;
import com.liferay.commerce.shop.by.diagram.service.base.CPDefinitionDiagramSettingServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;

import org.osgi.service.component.annotations.Component;

/**
 * @author Andrea Sbarra
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = {
		"json.web.service.context.name=commerce",
		"json.web.service.context.path=CPDefinitionDiagramSetting"
	},
	service = AopService.class
)
public class CPDefinitionDiagramSettingServiceImpl
	extends CPDefinitionDiagramSettingServiceBaseImpl {

	@Override
	public CPDefinitionDiagramSetting addCPDefinitionDiagramSetting(
			long cpDefinitionId, long cpAttachmentFileEntryId, String color,
			double radius, String type)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramSettingLocalService.
			addCPDefinitionDiagramSetting(
				getUserId(), cpDefinitionId, cpAttachmentFileEntryId, color,
				radius, type);
	}

	@Override
	public CPDefinitionDiagramSetting
			fetchCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramSettingLocalService.
			fetchCPDefinitionDiagramSettingByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public CPDefinitionDiagramSetting getCPDefinitionDiagramSetting(
			long cpDefinitionDiagramSettingId)
		throws PortalException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			cpDefinitionDiagramSettingLocalService.
				getCPDefinitionDiagramSetting(cpDefinitionDiagramSettingId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramSetting.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionDiagramSetting;
	}

	@Override
	public CPDefinitionDiagramSetting
			getCPDefinitionDiagramSettingByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramSettingLocalService.
			getCPDefinitionDiagramSettingByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public CPDefinitionDiagramSetting updateCPDefinitionDiagramSetting(
			long cpDefinitionDiagramSettingId, long cpAttachmentFileEntryId,
			String color, double radius, String type)
		throws PortalException {

		CPDefinitionDiagramSetting cpDefinitionDiagramSetting =
			cpDefinitionDiagramSettingLocalService.
				getCPDefinitionDiagramSetting(cpDefinitionDiagramSettingId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramSetting.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionDiagramSettingLocalService.
			updateCPDefinitionDiagramSetting(
				cpDefinitionDiagramSetting.getCPDefinitionDiagramSettingId(),
				cpAttachmentFileEntryId, color, radius, type);
	}

	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPDefinitionDiagramSettingServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

}
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
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.commerce.shop.by.diagram.service.base.CPDefinitionDiagramSettingsServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionDiagramSettingsServiceImpl
	extends CPDefinitionDiagramSettingsServiceBaseImpl {

	@Override
	public CPDefinitionDiagramSettings addCPDefinitionDiagramSettings(
			long userId, long cpDefinitionId, long assetCategoryId, String name)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramSettingsLocalService.
			addCPDefinitionDiagramSettings(
				userId, cpDefinitionId, assetCategoryId, name);
	}

	@Override
	public CPDefinitionDiagramSettings
			fetchCPDefinitionDiagramSettingsByCPDefinitionId(
				long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramSettingsLocalService.
			fetchCPDefinitionDiagramSettingsByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public CPDefinitionDiagramSettings getCPDefinitionDiagramSettings(
			long cpDefinitionDiagramSettingsId)
		throws PortalException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramSettingsLocalService.
				getCPDefinitionDiagramSettings(cpDefinitionDiagramSettingsId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramSettings.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionDiagramSettings;
	}

	@Override
	public CPDefinitionDiagramSettings
			getCPDefinitionDiagramSettingsByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(), cpDefinitionId, ActionKeys.UPDATE);

		return cpDefinitionDiagramSettingsLocalService.
			getCPDefinitionDiagramSettingsByCPDefinitionId(cpDefinitionId);
	}

	@Override
	public List<CPDefinitionDiagramSettings> getCPDefinitionDiagramSettingsList(
		long assetCategoryId, int start, int end) {

		//TODO permission check

		return cpDefinitionDiagramSettingsPersistence.findByAssetCategoryId(
			assetCategoryId, start, end);
	}

	@Override
	public int getCPDefinitionDiagramSettingsListCount(long assetCategoryId) {

		//TODO permission check

		return cpDefinitionDiagramSettingsPersistence.countByAssetCategoryId(
			assetCategoryId);
	}

	@Override
	public CPDefinitionDiagramSettings updateCPDefinitionDiagramSettings(
			long cpDefinitionDiagramSettingsId, long assetCategoryId,
			String name)
		throws PortalException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramSettingsLocalService.
				getCPDefinitionDiagramSettings(cpDefinitionDiagramSettingsId);

		_cpDefinitionModelResourcePermission.check(
			getPermissionChecker(),
			cpDefinitionDiagramSettings.getCPDefinitionId(), ActionKeys.UPDATE);

		return cpDefinitionDiagramSettingsLocalService.
			updateCPDefinitionDiagramSettings(
				cpDefinitionDiagramSettings.getCPDefinitionDiagramSettingsId(),
				assetCategoryId, name);
	}

	private static volatile ModelResourcePermission<CPDefinition>
		_cpDefinitionModelResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				CPDefinitionDiagramSettingsServiceImpl.class,
				"_cpDefinitionModelResourcePermission", CPDefinition.class);

}
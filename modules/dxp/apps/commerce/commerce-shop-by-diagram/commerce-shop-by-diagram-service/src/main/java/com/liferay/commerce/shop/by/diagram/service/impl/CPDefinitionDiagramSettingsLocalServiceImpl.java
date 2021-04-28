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

import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.commerce.shop.by.diagram.model.CPDefinitionDiagramSettings;
import com.liferay.commerce.shop.by.diagram.service.base.CPDefinitionDiagramSettingsLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;

import java.util.List;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionDiagramSettingsLocalServiceImpl
	extends CPDefinitionDiagramSettingsLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionDiagramSettings addCPDefinitionDiagramSettings(
			long userId, long cpDefinitionId, long assetCategoryId, String name)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long cpDefinitionDiagramSettingsId = counterLocalService.increment();

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramSettingsPersistence.create(
				cpDefinitionDiagramSettingsId);

		cpDefinitionDiagramSettings.setCompanyId(user.getCompanyId());
		cpDefinitionDiagramSettings.setUserId(user.getUserId());
		cpDefinitionDiagramSettings.setUserName(user.getFullName());
		cpDefinitionDiagramSettings.setCPDefinitionId(cpDefinitionId);
		cpDefinitionDiagramSettings.setAssetCategoryId(assetCategoryId);
		cpDefinitionDiagramSettings.setName(name);

		return cpDefinitionDiagramSettingsPersistence.update(
			cpDefinitionDiagramSettings);
	}

	@Override
	public CPDefinitionDiagramSettings deleteCPDefinitionDiagramSettings(
			long cpDefinitionDiagramSettingsId)
		throws PortalException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramSettingsPersistence.findByPrimaryKey(
				cpDefinitionDiagramSettingsId);

		return cpDefinitionDiagramSettingsLocalService.
			deleteCPDefinitionDiagramSettings(cpDefinitionDiagramSettings);
	}

	@Override
	public CPDefinitionDiagramSettings
			deleteCPDefinitionDiagramSettingsByCPDefinitionId(
				long cpDefinitionId)
		throws PortalException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramSettingsPersistence.findByCPDefinitionId(
				cpDefinitionId);

		return cpDefinitionDiagramSettingsLocalService.
			deleteCPDefinitionDiagramSettings(cpDefinitionDiagramSettings);
	}

	@Override
	public void deleteCPDefinitionDiagramSettingsCategory(long assetCategoryId)
		throws PortalException {

		List<CPDefinitionDiagramSettings> cpDefinitionDiagramSettingsList =
			cpDefinitionDiagramSettingsPersistence.findByAssetCategoryId(
				assetCategoryId);

		for (CPDefinitionDiagramSettings cpDefinitionDiagramSettings :
				cpDefinitionDiagramSettingsList) {

			cpDefinitionDiagramSettingsLocalService.
				updateCPDefinitionDiagramSettings(
					cpDefinitionDiagramSettings.
						getCPDefinitionDiagramSettingsId(),
					AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
					cpDefinitionDiagramSettings.getName());
		}
	}

	@Override
	public CPDefinitionDiagramSettings
		fetchCPDefinitionDiagramSettingsByCPDefinitionId(long cpDefinitionId) {

		return cpDefinitionDiagramSettingsPersistence.fetchByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public CPDefinitionDiagramSettings
			getCPDefinitionDiagramSettingsByCPDefinitionId(long cpDefinitionId)
		throws PortalException {

		return cpDefinitionDiagramSettingsPersistence.findByCPDefinitionId(
			cpDefinitionId);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionDiagramSettings updateCPDefinitionDiagramSettings(
			long cpDefinitionDiagramSettingsId, long assetCategoryId,
			String name)
		throws PortalException {

		CPDefinitionDiagramSettings cpDefinitionDiagramSettings =
			cpDefinitionDiagramSettingsLocalService.
				getCPDefinitionDiagramSettings(cpDefinitionDiagramSettingsId);

		cpDefinitionDiagramSettings.setAssetCategoryId(assetCategoryId);
		cpDefinitionDiagramSettings.setName(name);

		return cpDefinitionDiagramSettingsPersistence.update(
			cpDefinitionDiagramSettings);
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.categories.admin.web.internal.exportimport.staged.model.repository;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Akos Thurzo
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetCategory",
	service = StagedModelRepository.class
)
public class AssetCategoryStagedModelRepository
	implements StagedModelRepository<AssetCategory> {

	@Override
	public AssetCategory addStagedModel(
			PortletDataContext portletDataContext, AssetCategory assetCategory)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(AssetCategory assetCategory)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public AssetCategory fetchMissingReference(String uuid, long groupId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AssetCategory fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public List<AssetCategory> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public AssetCategory getStagedModel(long categoryId)
		throws PortalException {

		return _assetCategoryLocalService.getAssetCategory(categoryId);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, AssetCategory assetCategory)
		throws PortletDataException {

		throw new UnsupportedOperationException();
	}

	@Override
	public AssetCategory saveStagedModel(AssetCategory assetCategory)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Override
	public AssetCategory updateStagedModel(
			PortletDataContext portletDataContext, AssetCategory assetCategory)
		throws PortalException {

		throw new UnsupportedOperationException();
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

}
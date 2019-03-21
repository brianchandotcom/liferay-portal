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

package com.liferay.asset.list.internal.exportimport.staged.model.repository;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.list.model.AssetEntryAssetListEntryRel;
import com.liferay.asset.list.service.AssetEntryAssetListEntryRelLocalService;
import com.liferay.asset.util.StagingAssetEntryHelper;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.asset.list.model.AssetEntryAssetListEntryRel",
	service = StagedModelRepository.class
)
public class AssetEntryAssetListEntryRelStagedModelRepository
	implements StagedModelRepository<AssetEntryAssetListEntryRel> {

	@Override
	public AssetEntryAssetListEntryRel addStagedModel(
			PortletDataContext portletDataContext,
			AssetEntryAssetListEntryRel assetEntryAssetListEntryRel)
		throws PortalException {

		AssetEntry assetEntry = _stagingAssetEntryHelper.fetchAssetEntry(
			portletDataContext.getScopeGroupId(),
			assetEntryAssetListEntryRel.getAssetEntryUuid());

		if (assetEntry == null) {
			return null;
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetEntryAssetListEntryRel);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(assetEntryAssetListEntryRel.getUuid());
		}

		return _assetEntryAssetListEntryRelLocalService.
			addAssetEntryAssetListEntryRel(
				assetEntry.getEntryId(),
				assetEntryAssetListEntryRel.getAssetListEntryId(),
				assetEntryAssetListEntryRel.getPosition(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			AssetEntryAssetListEntryRel assetEntryAssetListEntryRel)
		throws PortalException {

		_assetEntryAssetListEntryRelLocalService.
			deleteAssetEntryAssetListEntryRel(assetEntryAssetListEntryRel);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetEntryAssetListEntryRel assetEntryAssetListEntryRel =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (assetEntryAssetListEntryRel != null) {
			deleteStagedModel(assetEntryAssetListEntryRel);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public AssetEntryAssetListEntryRel fetchMissingReference(
		String uuid, long groupId) {

		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public AssetEntryAssetListEntryRel fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _assetEntryAssetListEntryRelLocalService.
			fetchAssetEntryAssetListEntryRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<AssetEntryAssetListEntryRel>
		fetchStagedModelsByUuidAndCompanyId(String uuid, long companyId) {

		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRelsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _assetEntryAssetListEntryRelLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public AssetEntryAssetListEntryRel getStagedModel(long id)
		throws PortalException {

		return _assetEntryAssetListEntryRelLocalService.
			getAssetEntryAssetListEntryRel(id);
	}

	@Override
	public AssetEntryAssetListEntryRel saveStagedModel(
			AssetEntryAssetListEntryRel assetEntryAssetListEntryRel)
		throws PortalException {

		return _assetEntryAssetListEntryRelLocalService.
			updateAssetEntryAssetListEntryRel(assetEntryAssetListEntryRel);
	}

	@Override
	public AssetEntryAssetListEntryRel updateStagedModel(
			PortletDataContext portletDataContext,
			AssetEntryAssetListEntryRel assetEntryAssetListEntryRel)
		throws PortalException {

		return _assetEntryAssetListEntryRelLocalService.
			updateAssetEntryAssetListEntryRel(
				assetEntryAssetListEntryRel.getAssetEntryAssetListEntryRelId(),
				assetEntryAssetListEntryRel.getAssetEntryId(),
				assetEntryAssetListEntryRel.getAssetListEntryId(),
				assetEntryAssetListEntryRel.getPosition());
	}

	@Reference
	private AssetEntryAssetListEntryRelLocalService
		_assetEntryAssetListEntryRelLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

	@Reference
	private StagingAssetEntryHelper _stagingAssetEntryHelper;

}
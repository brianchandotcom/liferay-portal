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
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalService;
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
	property = "model.class.name=com.liferay.asset.list.model.AssetListEntryAssetEntryRel",
	service = StagedModelRepository.class
)
public class AssetListEntryAssetEntryRelStagedModelRepository
	implements StagedModelRepository<AssetListEntryAssetEntryRel> {

	@Override
	public AssetListEntryAssetEntryRel addStagedModel(
			PortletDataContext portletDataContext,
			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel)
		throws PortalException {

		AssetEntry assetEntry = _stagingAssetEntryHelper.fetchAssetEntry(
			portletDataContext.getScopeGroupId(),
			assetListEntryAssetEntryRel.getAssetEntryUuid());

		if (assetEntry == null) {
			return null;
		}

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetListEntryAssetEntryRel);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(assetListEntryAssetEntryRel.getUuid());
		}

		return _assetListEntryAssetEntryRelLocalService.
			addAssetListEntryAssetEntryRel(
				assetListEntryAssetEntryRel.getAssetListEntryId(),
				assetEntry.getEntryId(),
				assetListEntryAssetEntryRel.getSegmentsEntryId(),
				assetListEntryAssetEntryRel.getPosition(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel)
		throws PortalException {

		_assetListEntryAssetEntryRelLocalService.
			deleteAssetListEntryAssetEntryRel(assetListEntryAssetEntryRel);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetListEntryAssetEntryRel assetListEntryAssetEntryRel =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (assetListEntryAssetEntryRel != null) {
			deleteStagedModel(assetListEntryAssetEntryRel);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public AssetListEntryAssetEntryRel fetchMissingReference(
		String uuid, long groupId) {

		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public AssetListEntryAssetEntryRel fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _assetListEntryAssetEntryRelLocalService.
			fetchAssetListEntryAssetEntryRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<AssetListEntryAssetEntryRel>
		fetchStagedModelsByUuidAndCompanyId(String uuid, long companyId) {

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRelsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _assetListEntryAssetEntryRelLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public AssetListEntryAssetEntryRel getStagedModel(long id)
		throws PortalException {

		return _assetListEntryAssetEntryRelLocalService.
			getAssetListEntryAssetEntryRel(id);
	}

	@Override
	public AssetListEntryAssetEntryRel saveStagedModel(
			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel)
		throws PortalException {

		return _assetListEntryAssetEntryRelLocalService.
			updateAssetListEntryAssetEntryRel(assetListEntryAssetEntryRel);
	}

	@Override
	public AssetListEntryAssetEntryRel updateStagedModel(
			PortletDataContext portletDataContext,
			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel)
		throws PortalException {

		return _assetListEntryAssetEntryRelLocalService.
			updateAssetListEntryAssetEntryRel(
				assetListEntryAssetEntryRel.getAssetListEntryAssetEntryRelId(),
				assetListEntryAssetEntryRel.getAssetListEntryId(),
				assetListEntryAssetEntryRel.getAssetEntryId(),
				assetListEntryAssetEntryRel.getSegmentsEntryId(),
				assetListEntryAssetEntryRel.getPosition());
	}

	@Reference
	private AssetListEntryAssetEntryRelLocalService
		_assetListEntryAssetEntryRelLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

	@Reference
	private StagingAssetEntryHelper _stagingAssetEntryHelper;

}
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

import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.StagedModelRepositoryHelper;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel",
	service = StagedModelRepository.class
)
public class AssetListEntrySegmentsEntryRelStagedModelRepository
	implements StagedModelRepository<AssetListEntrySegmentsEntryRel> {

	@Override
	public AssetListEntrySegmentsEntryRel addStagedModel(
			PortletDataContext portletDataContext,
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetListEntrySegmentsEntryRel);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(assetListEntrySegmentsEntryRel.getUuid());
		}

		return _assetListEntrySegmentsEntryRelLocalService.
			addAssetListEntrySegmentsEntryRel(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				assetListEntrySegmentsEntryRel.getAssetListEntryId(),
				assetListEntrySegmentsEntryRel.getSegmentsEntryId(),
				assetListEntrySegmentsEntryRel.getTypeSettings(),
				serviceContext);
	}

	@Override
	public void deleteStagedModel(
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel)
		throws PortalException {

		_assetListEntrySegmentsEntryRelLocalService.
			deleteAssetListEntrySegmentsEntryRel(
				assetListEntrySegmentsEntryRel);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (assetListEntrySegmentsEntryRel != null) {
			deleteStagedModel(assetListEntrySegmentsEntryRel);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public AssetListEntrySegmentsEntryRel fetchMissingReference(
		String uuid, long groupId) {

		return _stagedModelRepositoryHelper.fetchMissingReference(
			uuid, groupId, this);
	}

	@Override
	public AssetListEntrySegmentsEntryRel fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _assetListEntrySegmentsEntryRelLocalService.
			fetchAssetListEntrySegmentsEntryRelByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<AssetListEntrySegmentsEntryRel>
		fetchStagedModelsByUuidAndCompanyId(String uuid, long companyId) {

		return _assetListEntrySegmentsEntryRelLocalService.
			getAssetListEntrySegmentsEntryRelsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _assetListEntrySegmentsEntryRelLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public AssetListEntrySegmentsEntryRel getStagedModel(long id)
		throws PortalException {

		return _assetListEntrySegmentsEntryRelLocalService.
			getAssetListEntrySegmentsEntryRel(id);
	}

	@Override
	public AssetListEntrySegmentsEntryRel saveStagedModel(
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel)
		throws PortalException {

		return _assetListEntrySegmentsEntryRelLocalService.
			updateAssetListEntrySegmentsEntryRel(
				assetListEntrySegmentsEntryRel);
	}

	@Override
	public AssetListEntrySegmentsEntryRel updateStagedModel(
			PortletDataContext portletDataContext,
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel)
		throws PortalException {

		if (assetListEntrySegmentsEntryRel.getTypeSettings() == null) {
			return assetListEntrySegmentsEntryRel;
		}

		return _assetListEntrySegmentsEntryRelLocalService.
			updateAssetListEntrySegmentsEntryRelTypeSettings(
				assetListEntrySegmentsEntryRel.getAssetListEntryId(),
				assetListEntrySegmentsEntryRel.getSegmentsEntryId(),
				assetListEntrySegmentsEntryRel.getTypeSettings());
	}

	@Reference
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}
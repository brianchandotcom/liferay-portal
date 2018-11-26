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

package com.liferay.asset.display.page.internal.exportimport.staged.model.repository;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
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
	property = "model.class.name=com.liferay.asset.display.page.model.AssetDisplayPageEntry",
	service = StagedModelRepository.class
)
public class AssetDisplayPageStagedModelRepository
	implements StagedModelRepository<AssetDisplayPageEntry> {

	@Override
	public AssetDisplayPageEntry addStagedModel(
			PortletDataContext portletDataContext,
			AssetDisplayPageEntry assetDisplayPageEntry)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			assetDisplayPageEntry.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetDisplayPageEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(assetDisplayPageEntry.getUuid());
		}

		return _assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			userId, serviceContext.getScopeGroupId(),
			assetDisplayPageEntry.getClassNameId(),
			assetDisplayPageEntry.getClassPK(),
			assetDisplayPageEntry.getLayoutPageTemplateEntryId(),
			assetDisplayPageEntry.getType(), serviceContext);
	}

	@Override
	public void deleteStagedModel(AssetDisplayPageEntry assetDisplayPageEntry)
		throws PortalException {

		_assetDisplayPageEntryLocalService.deleteAssetDisplayPageEntry(
			assetDisplayPageEntry);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetDisplayPageEntry assetDisplayPageEntry =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (assetDisplayPageEntry != null) {
			deleteStagedModel(assetDisplayPageEntry);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public AssetDisplayPageEntry fetchMissingReference(
		String uuid, long groupId) {

		return (AssetDisplayPageEntry)
			_stagedModelRepositoryHelper.fetchMissingReference(
				uuid, groupId, this);
	}

	@Override
	public AssetDisplayPageEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _assetDisplayPageEntryLocalService.
			fetchAssetDisplayPageEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<AssetDisplayPageEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _assetDisplayPageEntryLocalService.
			getAssetDisplayPageEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _assetDisplayPageEntryLocalService.
			getExportActionableDynamicQuery(portletDataContext);
	}

	@Override
	public AssetDisplayPageEntry getStagedModel(long id)
		throws PortalException {

		return _assetDisplayPageEntryLocalService.getAssetDisplayPageEntry(id);
	}

	@Override
	public AssetDisplayPageEntry saveStagedModel(
			AssetDisplayPageEntry assetDisplayPageEntry)
		throws PortalException {

		return _assetDisplayPageEntryLocalService.updateAssetDisplayPageEntry(
			assetDisplayPageEntry);
	}

	@Override
	public AssetDisplayPageEntry updateStagedModel(
			PortletDataContext portletDataContext,
			AssetDisplayPageEntry assetDisplayPageEntry)
		throws PortalException {

		return _assetDisplayPageEntryLocalService.updateAssetDisplayPageEntry(
			assetDisplayPageEntry);
	}

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private StagedModelRepositoryHelper _stagedModelRepositoryHelper;

}
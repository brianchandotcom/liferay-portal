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

package com.liferay.asset.list.internal.exportimport.data.handler;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.list.model.AssetEntryAssetListEntryRel;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.util.StagingAssetEntryHelper;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AssetEntryAssetListEntryRelStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetEntryAssetListEntryRel> {

	public static final String[] CLASS_NAMES = {
		AssetEntryAssetListEntryRel.class.getName()
	};

	@Override
	public void deleteStagedModel(
			AssetEntryAssetListEntryRel assetEntryAssetListEntryRel)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(assetEntryAssetListEntryRel);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			uuid, groupId, className, extraData);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			AssetEntryAssetListEntryRel assetEntryAssetListEntryRel)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(
			assetEntryAssetListEntryRel);

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetEntryAssetListEntryRel.getAssetEntryId());

		_stagingAssetEntryHelper.addAssetReference(
			portletDataContext, assetEntryAssetListEntryRel, entryElement,
			assetEntry);

		portletDataContext.addClassedModel(
			entryElement,
			ExportImportPathUtil.getModelPath(assetEntryAssetListEntryRel),
			assetEntryAssetListEntryRel);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long fragmentEntryId)
		throws Exception {

		AssetEntryAssetListEntryRel existingAssetEntryAssetListEntryRel =
			fetchMissingReference(uuid, groupId);

		if (existingAssetEntryAssetListEntryRel == null) {
			return;
		}

		Map<Long, Long> assetEntryAssetListEntryRelIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetEntryAssetListEntryRel.class);

		assetEntryAssetListEntryRelIds.put(
			fragmentEntryId,
			existingAssetEntryAssetListEntryRel.
				getAssetEntryAssetListEntryRelId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			AssetEntryAssetListEntryRel assetEntryAssetListEntryRel)
		throws Exception {

		Map<Long, Long> assetListEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetListEntry.class);

		long assetListEntryId = MapUtil.getLong(
			assetListEntryIds,
			assetEntryAssetListEntryRel.getAssetListEntryId(),
			assetEntryAssetListEntryRel.getAssetListEntryId());

		AssetEntryAssetListEntryRel importedAssetEntryAssetListEntryRel =
			(AssetEntryAssetListEntryRel)assetEntryAssetListEntryRel.clone();

		importedAssetEntryAssetListEntryRel.setGroupId(
			portletDataContext.getScopeGroupId());
		importedAssetEntryAssetListEntryRel.setAssetListEntryId(
			assetListEntryId);

		AssetEntryAssetListEntryRel existingAssetEntryAssetListEntryRel =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				assetEntryAssetListEntryRel.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingAssetEntryAssetListEntryRel == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedAssetEntryAssetListEntryRel =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedAssetEntryAssetListEntryRel);
		}
		else {
			importedAssetEntryAssetListEntryRel.setAssetListEntryId(
				existingAssetEntryAssetListEntryRel.getAssetListEntryId());

			importedAssetEntryAssetListEntryRel =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedAssetEntryAssetListEntryRel);
		}

		portletDataContext.importClassedModel(
			assetEntryAssetListEntryRel, importedAssetEntryAssetListEntryRel);
	}

	@Override
	protected StagedModelRepository<AssetEntryAssetListEntryRel>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.list.model.AssetEntryAssetListEntryRel)",
		unbind = "-"
	)
	private StagedModelRepository<AssetEntryAssetListEntryRel>
		_stagedModelRepository;

	@Reference
	private StagingAssetEntryHelper _stagingAssetEntryHelper;

}
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
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.util.StagingAssetEntryHelper;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AssetListEntryAssetEntryRelStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetListEntryAssetEntryRel> {

	public static final String[] CLASS_NAMES = {
		AssetListEntryAssetEntryRel.class.getName()
	};

	@Override
	public void deleteStagedModel(
			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(assetListEntryAssetEntryRel);
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
			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(
			assetListEntryAssetEntryRel);

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			assetListEntryAssetEntryRel.getAssetEntryId());

		if (Validator.isNull(assetListEntryAssetEntryRel.getAssetEntryUuid())) {
			assetListEntryAssetEntryRel.setAssetEntryUuid(
				assetEntry.getClassUuid());
		}

		_stagingAssetEntryHelper.addAssetReference(
			portletDataContext, assetListEntryAssetEntryRel, entryElement,
			assetEntry);

		portletDataContext.addClassedModel(
			entryElement,
			ExportImportPathUtil.getModelPath(assetListEntryAssetEntryRel),
			assetListEntryAssetEntryRel);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long fragmentEntryId)
		throws Exception {

		AssetListEntryAssetEntryRel existingAssetListEntryAssetEntryRel =
			fetchMissingReference(uuid, groupId);

		if (existingAssetListEntryAssetEntryRel == null) {
			return;
		}

		Map<Long, Long> assetListEntryAssetEntryRelIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetListEntryAssetEntryRel.class);

		assetListEntryAssetEntryRelIds.put(
			fragmentEntryId,
			existingAssetListEntryAssetEntryRel.
				getAssetListEntryAssetEntryRelId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			AssetListEntryAssetEntryRel assetListEntryAssetEntryRel)
		throws Exception {

		Map<Long, Long> assetListEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetListEntry.class);

		long assetListEntryId = MapUtil.getLong(
			assetListEntryIds,
			assetListEntryAssetEntryRel.getAssetListEntryId(),
			assetListEntryAssetEntryRel.getAssetListEntryId());

		AssetListEntryAssetEntryRel importedAssetListEntryAssetEntryRel =
			(AssetListEntryAssetEntryRel)assetListEntryAssetEntryRel.clone();

		importedAssetListEntryAssetEntryRel.setGroupId(
			portletDataContext.getScopeGroupId());
		importedAssetListEntryAssetEntryRel.setAssetListEntryId(
			assetListEntryId);
		importedAssetListEntryAssetEntryRel.setAssetEntryUuid(
			assetListEntryAssetEntryRel.getAssetEntryUuid());

		AssetListEntryAssetEntryRel existingAssetListEntryAssetEntryRel =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				assetListEntryAssetEntryRel.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingAssetListEntryAssetEntryRel == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedAssetListEntryAssetEntryRel =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedAssetListEntryAssetEntryRel);
		}
		else {
			importedAssetListEntryAssetEntryRel.setAssetListEntryId(
				existingAssetListEntryAssetEntryRel.getAssetListEntryId());

			importedAssetListEntryAssetEntryRel =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedAssetListEntryAssetEntryRel);
		}

		portletDataContext.importClassedModel(
			assetListEntryAssetEntryRel, importedAssetListEntryAssetEntryRel);
	}

	@Override
	protected StagedModelRepository<AssetListEntryAssetEntryRel>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.list.model.AssetListEntryAssetEntryRel)"
	)
	private StagedModelRepository<AssetListEntryAssetEntryRel>
		_stagedModelRepository;

	@Reference
	private StagingAssetEntryHelper _stagingAssetEntryHelper;

}
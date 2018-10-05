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

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AssetListEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetListEntry> {

	public static final String[] CLASS_NAMES = {AssetListEntry.class.getName()};

	@Override
	public void deleteStagedModel(AssetListEntry assetListEntry)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(assetListEntry);
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
	public String getDisplayName(AssetListEntry assetListEntry) {
		return assetListEntry.getTitle();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			AssetListEntry assetListEntry)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(
			assetListEntry);

		portletDataContext.addClassedModel(
			entryElement, ExportImportPathUtil.getModelPath(assetListEntry),
			assetListEntry);

		List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
			_assetListEntryAssetEntryRelLocalService.
				getAssetListEntryAssetEntryRels(
					assetListEntry.getAssetListEntryId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

		for (AssetListEntryAssetEntryRel assetListEntryAssetEntryRel :
				assetListEntryAssetEntryRels) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, assetListEntry, assetListEntryAssetEntryRel,
				PortletDataContext.REFERENCE_TYPE_CHILD);
		}
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long assetListEntryId)
		throws Exception {

		AssetListEntry existingAssetListEntry = fetchMissingReference(
			uuid, groupId);

		if (existingAssetListEntry == null) {
			return;
		}

		Map<Long, Long> assetListEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetListEntry.class);

		assetListEntryIds.put(
			assetListEntryId, existingAssetListEntry.getAssetListEntryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			AssetListEntry assetListEntry)
		throws Exception {

		AssetListEntry importedAssetListEntry =
			(AssetListEntry)assetListEntry.clone();

		importedAssetListEntry.setGroupId(portletDataContext.getScopeGroupId());

		AssetListEntry existingAssetListEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				assetListEntry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingAssetListEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedAssetListEntry = _stagedModelRepository.addStagedModel(
				portletDataContext, importedAssetListEntry);
		}
		else {
			importedAssetListEntry.setAssetListEntryId(
				existingAssetListEntry.getAssetListEntryId());

			importedAssetListEntry = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedAssetListEntry);
		}

		portletDataContext.importClassedModel(
			assetListEntry, importedAssetListEntry);

		if (existingAssetListEntry != null) {
			_assetListEntryAssetEntryRelLocalService.
				deleteAssetListEntryAssetEntryRelByAssetListEntryId(
					existingAssetListEntry.getAssetListEntryId());
		}

		List<Element> assetEntryListAssetEntryRelElements =
			portletDataContext.getReferenceDataElements(
				assetListEntry, AssetListEntryAssetEntryRel.class,
				PortletDataContext.REFERENCE_TYPE_CHILD);

		for (Element assetEntryListAssetEntryRelElement :
				assetEntryListAssetEntryRelElements) {

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, assetEntryListAssetEntryRelElement);
		}
	}

	@Override
	protected StagedModelRepository<AssetListEntry> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	protected boolean isSkipImportReferenceStagedModels() {
		return true;
	}

	@Reference
	private AssetListEntryAssetEntryRelLocalService
		_assetListEntryAssetEntryRelLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.list.model.AssetListEntry)",
		unbind = "-"
	)
	private StagedModelRepository<AssetListEntry> _stagedModelRepository;

}
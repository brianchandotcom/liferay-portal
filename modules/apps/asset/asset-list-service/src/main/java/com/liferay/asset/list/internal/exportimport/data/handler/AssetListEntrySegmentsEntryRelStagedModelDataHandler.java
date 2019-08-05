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
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AssetListEntrySegmentsEntryRelStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetListEntrySegmentsEntryRel> {

	public static final String[] CLASS_NAMES = {
		AssetListEntrySegmentsEntryRel.class.getName()
	};

	@Override
	public void deleteStagedModel(
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel)
		throws PortalException {

		_stagedModelRepository.deleteStagedModel(
			assetListEntrySegmentsEntryRel);
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
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel)
		throws Exception {

		Element entryElement = portletDataContext.getExportDataElement(
			assetListEntrySegmentsEntryRel);

		if (assetListEntrySegmentsEntryRel.getSegmentsEntryId() !=
				SegmentsEntryConstants.ID_DEFAULT) {

			SegmentsEntry segmentsEntry =
				_segmentsEntryLocalService.fetchSegmentsEntry(
					assetListEntrySegmentsEntryRel.getSegmentsEntryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, assetListEntrySegmentsEntryRel,
				segmentsEntry, PortletDataContext.REFERENCE_TYPE_STRONG);
		}

		String typeSettings =
			_assetListEntryExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, assetListEntrySegmentsEntryRel,
					assetListEntrySegmentsEntryRel.getTypeSettings(), false,
					false);

		assetListEntrySegmentsEntryRel.setTypeSettings(typeSettings);

		portletDataContext.addClassedModel(
			entryElement,
			ExportImportPathUtil.getModelPath(assetListEntrySegmentsEntryRel),
			assetListEntrySegmentsEntryRel);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long fragmentEntryId)
		throws Exception {

		AssetListEntrySegmentsEntryRel existingAssetListEntrySegmentsEntryRel =
			fetchMissingReference(uuid, groupId);

		if (existingAssetListEntrySegmentsEntryRel == null) {
			return;
		}

		Map<Long, Long> assetListEntrySegmentsEntryRelIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetListEntrySegmentsEntryRel.class);

		assetListEntrySegmentsEntryRelIds.put(
			fragmentEntryId,
			existingAssetListEntrySegmentsEntryRel.
				getAssetListEntrySegmentsEntryRelId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel)
		throws Exception {

		Map<Long, Long> assetListEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetListEntry.class);

		long assetListEntryId = MapUtil.getLong(
			assetListEntryIds,
			assetListEntrySegmentsEntryRel.getAssetListEntryId(),
			assetListEntrySegmentsEntryRel.getAssetListEntryId());

		Map<Long, Long> segmentsEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				SegmentsEntry.class);

		long segmentsEntryId = MapUtil.getLong(
			segmentsEntryIds,
			assetListEntrySegmentsEntryRel.getSegmentsEntryId(),
			assetListEntrySegmentsEntryRel.getSegmentsEntryId());

		AssetListEntrySegmentsEntryRel importedAssetListEntrySegmentsEntryRel =
			(AssetListEntrySegmentsEntryRel)
				assetListEntrySegmentsEntryRel.clone();

		importedAssetListEntrySegmentsEntryRel.setGroupId(
			portletDataContext.getScopeGroupId());
		importedAssetListEntrySegmentsEntryRel.setAssetListEntryId(
			assetListEntryId);
		importedAssetListEntrySegmentsEntryRel.setSegmentsEntryId(
			segmentsEntryId);

		AssetListEntrySegmentsEntryRel existingAssetListEntrySegmentsEntryRel =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				assetListEntrySegmentsEntryRel.getUuid(),
				portletDataContext.getScopeGroupId());

		String typeSettings =
			_assetListEntryExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, importedAssetListEntrySegmentsEntryRel,
					importedAssetListEntrySegmentsEntryRel.getTypeSettings());

		importedAssetListEntrySegmentsEntryRel.setTypeSettings(typeSettings);

		if ((existingAssetListEntrySegmentsEntryRel == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedAssetListEntrySegmentsEntryRel =
				_stagedModelRepository.addStagedModel(
					portletDataContext, importedAssetListEntrySegmentsEntryRel);
		}
		else {
			importedAssetListEntrySegmentsEntryRel.setAssetListEntryId(
				existingAssetListEntrySegmentsEntryRel.getAssetListEntryId());

			importedAssetListEntrySegmentsEntryRel =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedAssetListEntrySegmentsEntryRel);
		}

		portletDataContext.importClassedModel(
			assetListEntrySegmentsEntryRel,
			importedAssetListEntrySegmentsEntryRel);
	}

	@Override
	protected StagedModelRepository<AssetListEntrySegmentsEntryRel>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	@Reference(
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.asset.list.model.AssetListEntry)"
	)
	private volatile ExportImportContentProcessor<String>
		_assetListEntryExportImportContentProcessor;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel)",
		unbind = "-"
	)
	private StagedModelRepository<AssetListEntrySegmentsEntryRel>
		_stagedModelRepository;

}
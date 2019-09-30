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
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.model.AssetListEntryAssetEntryRel;
import com.liferay.asset.list.model.AssetListEntrySegmentsEntryRel;
import com.liferay.asset.list.service.AssetListEntryAssetEntryRelLocalService;
import com.liferay.asset.list.service.AssetListEntrySegmentsEntryRelLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.staging.StagingGroupHelper;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		_exportAssetListEntryAssetEntryRels(portletDataContext, assetListEntry);

		_exportAssetListEntrySegmentsEntryRels(
			portletDataContext, assetListEntry);

		_exportAssetObjects(portletDataContext, assetListEntry);
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
			importedAssetListEntry.setMvccVersion(
				existingAssetListEntry.getMvccVersion());
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

			_assetListEntrySegmentsEntryRelLocalService.
				deleteAssetListEntrySegmentsEntryRelByAssetListEntryId(
					existingAssetListEntry.getAssetListEntryId());
		}

		_importAssetObjects(portletDataContext);

		_importAssetEntryListAssetEntryRelElements(
			portletDataContext, assetListEntry);

		_importAssetEntryListSegmentsEntryRelElements(
			portletDataContext, assetListEntry);
	}

	@Override
	protected StagedModelRepository<AssetListEntry> getStagedModelRepository() {
		return _stagedModelRepository;
	}

	@Override
	protected boolean isSkipImportReferenceStagedModels() {
		return true;
	}

	private void _exportAssetListEntryAssetEntryRels(
			PortletDataContext portletDataContext,
			AssetListEntry assetListEntry)
		throws PortletDataException {

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

	private void _exportAssetListEntrySegmentsEntryRels(
			PortletDataContext portletDataContext,
			AssetListEntry assetListEntry)
		throws PortletDataException {

		List<AssetListEntrySegmentsEntryRel> assetListEntrySegmentsEntryRels =
			_assetListEntrySegmentsEntryRelLocalService.
				getAssetListEntrySegmentsEntryRels(
					assetListEntry.getAssetListEntryId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS);

		for (AssetListEntrySegmentsEntryRel assetListEntrySegmentsEntryRel :
				assetListEntrySegmentsEntryRels) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, assetListEntry,
				assetListEntrySegmentsEntryRel,
				PortletDataContext.REFERENCE_TYPE_CHILD);
		}
	}

	private void _exportAssetObjects(
			PortletDataContext portletDataContext,
			AssetListEntry assetListEntry)
		throws Exception {

		if (assetListEntry.getType() ==
				AssetListEntryTypeConstants.TYPE_MANUAL) {

			List<AssetListEntryAssetEntryRel> assetListEntryAssetEntryRels =
				_assetListEntryAssetEntryRelLocalService.
					getAssetListEntryAssetEntryRels(
						assetListEntry.getAssetListEntryId(), QueryUtil.ALL_POS,
						QueryUtil.ALL_POS);

			Stream<AssetListEntryAssetEntryRel> stream =
				assetListEntryAssetEntryRels.stream();

			List<AssetEntry> assetEntries = stream.map(
				assetListEntryAssetEntryRel ->
					_assetEntryLocalService.fetchEntry(
						assetListEntryAssetEntryRel.getAssetEntryId())
			).collect(
				Collectors.toList()
			);

			for (AssetEntry assetEntry : assetEntries) {
				AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

				if ((assetRenderer == null) ||
					!(assetRenderer.getAssetObject() instanceof StagedModel)) {

					continue;
				}

				AssetRendererFactory assetRendererFactory =
					assetRenderer.getAssetRendererFactory();

				if ((assetRendererFactory != null) &&
					ExportImportThreadLocal.isStagingInProcess() &&
					!_stagingGroupHelper.isStagedPortlet(
						assetEntry.getGroupId(),
						assetRendererFactory.getPortletId())) {

					continue;
				}

				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, portletDataContext.getPortletId(),
					(StagedModel)assetRenderer.getAssetObject());
			}
		}
	}

	private void _importAssetEntryListAssetEntryRelElements(
			PortletDataContext portletDataContext,
			AssetListEntry assetListEntry)
		throws PortletDataException {

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

	private void _importAssetEntryListSegmentsEntryRelElements(
			PortletDataContext portletDataContext,
			AssetListEntry assetListEntry)
		throws PortletDataException {

		List<Element> assetEntryListSegmentsEntryRelElements =
			portletDataContext.getReferenceDataElements(
				assetListEntry, AssetListEntrySegmentsEntryRel.class,
				PortletDataContext.REFERENCE_TYPE_CHILD);

		for (Element assetEntryListSegmentsEntryRelElement :
				assetEntryListSegmentsEntryRelElements) {

			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, assetEntryListSegmentsEntryRelElement);
		}
	}

	private void _importAssetObjects(PortletDataContext portletDataContext)
		throws Exception {

		Element importDataRootElement =
			portletDataContext.getImportDataRootElement();

		Element referencesElement = importDataRootElement.element("references");

		if (referencesElement == null) {
			return;
		}

		List<Element> referenceElements = referencesElement.elements();

		for (Element referenceElement : referenceElements) {
			String className = referenceElement.attributeValue("class-name");
			long classPK = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, className, Long.valueOf(classPK));
		}
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private AssetListEntryAssetEntryRelLocalService
		_assetListEntryAssetEntryRelLocalService;

	@Reference
	private AssetListEntrySegmentsEntryRelLocalService
		_assetListEntrySegmentsEntryRelLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.list.model.AssetListEntry)"
	)
	private StagedModelRepository<AssetListEntry> _stagedModelRepository;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}
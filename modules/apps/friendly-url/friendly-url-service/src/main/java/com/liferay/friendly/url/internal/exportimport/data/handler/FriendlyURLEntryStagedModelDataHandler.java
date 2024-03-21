/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.friendly.url.internal.exportimport.data.handler;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.friendly.url.model.FriendlyURLEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = StagedModelDataHandler.class)
public class FriendlyURLEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<FriendlyURLEntry> {

	public static final String[] CLASS_NAMES = {
		FriendlyURLEntry.class.getName()
	};

	@Override
	public FriendlyURLEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _stagedModelRepository.fetchStagedModelByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<FriendlyURLEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _stagedModelRepository.fetchStagedModelsByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			FriendlyURLEntry friendlyURLEntry)
		throws Exception {

		Element friendlyURLEntryElement =
			portletDataContext.getExportDataElement(friendlyURLEntry);

		_exportAssetCategories(portletDataContext, friendlyURLEntry);

		friendlyURLEntryElement.addAttribute(
			"resource-class-name", friendlyURLEntry.getClassName());

		portletDataContext.addZipEntry(
			ExportImportPathUtil.getModelPath(
				friendlyURLEntry, friendlyURLEntry.getUuid()),
			friendlyURLEntry.getUrlTitleMapAsXML());

		FriendlyURLEntry mainFriendlyURLEntry =
			_friendlyURLEntryLocalService.fetchMainFriendlyURLEntry(
				friendlyURLEntry.getClassNameId(),
				friendlyURLEntry.getClassPK());

		if (mainFriendlyURLEntry == null) {
			_friendlyURLEntryLocalService.setMainFriendlyURLEntry(
				friendlyURLEntry);
		}

		if (friendlyURLEntry.isMain()) {
			friendlyURLEntryElement.addAttribute(
				"mainEntry", Boolean.TRUE.toString());
		}

		portletDataContext.addClassedModel(
			friendlyURLEntryElement,
			ExportImportPathUtil.getModelPath(friendlyURLEntry),
			friendlyURLEntry);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			FriendlyURLEntry friendlyURLEntry)
		throws Exception {

		Element friendlyURLEntryElement =
			portletDataContext.getImportDataStagedModelElement(
				friendlyURLEntry);

		String className = friendlyURLEntryElement.attributeValue(
			"resource-class-name");

		long classNameId = _classNameLocalService.getClassNameId(className);

		Map<Long, Long> newPrimaryKeysMap =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(className);

		if (!newPrimaryKeysMap.containsKey(friendlyURLEntry.getClassPK())) {
			portletDataContext.removePrimaryKey(
				ExportImportPathUtil.getModelPath(friendlyURLEntry));

			return;
		}

		FriendlyURLEntry existingFriendlyURLEntry =
			fetchStagedModelByUuidAndGroupId(
				friendlyURLEntry.getUuid(),
				portletDataContext.getScopeGroupId());

		FriendlyURLEntry importedFriendlyURLEntry = null;

		if ((existingFriendlyURLEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedFriendlyURLEntry =
				(FriendlyURLEntry)friendlyURLEntry.clone();

			importedFriendlyURLEntry.setDefaultLanguageId(
				friendlyURLEntry.getDefaultLanguageId());
			importedFriendlyURLEntry.setGroupId(
				portletDataContext.getScopeGroupId());
			importedFriendlyURLEntry.setCompanyId(
				portletDataContext.getCompanyId());
			importedFriendlyURLEntry.setClassNameId(classNameId);
			importedFriendlyURLEntry.setClassPK(
				MapUtil.getLong(
					newPrimaryKeysMap, friendlyURLEntry.getClassPK(),
					friendlyURLEntry.getClassPK()));

			importedFriendlyURLEntry = _stagedModelRepository.addStagedModel(
				portletDataContext, importedFriendlyURLEntry);

			boolean mainEntry = GetterUtil.getBoolean(
				friendlyURLEntryElement.attributeValue("mainEntry"));

			if (mainEntry) {
				_friendlyURLEntryLocalService.setMainFriendlyURLEntry(
					importedFriendlyURLEntry);
			}
		}
		else {
			importedFriendlyURLEntry = _stagedModelRepository.updateStagedModel(
				portletDataContext, friendlyURLEntry);

			boolean mainEntry = GetterUtil.getBoolean(
				friendlyURLEntryElement.attributeValue("mainEntry"));

			if (mainEntry) {
				_friendlyURLEntryLocalService.setMainFriendlyURLEntry(
					existingFriendlyURLEntry);
			}
		}

		portletDataContext.importClassedModel(
			friendlyURLEntry, importedFriendlyURLEntry);
	}

	@Override
	protected StagedModelRepository<FriendlyURLEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	private void _exportAssetCategories(
			PortletDataContext portletDataContext,
			FriendlyURLEntry friendlyURLEntry)
		throws Exception {

		if (!_featureFlagManager.isEnabled("LPD-11147")) {
			return;
		}

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			FriendlyURLEntry.class.getName(),
			friendlyURLEntry.getFriendlyURLEntryId());

		if (assetEntry == null) {
			return;
		}

		List<AssetCategory> assetCategories = assetEntry.getCategories();

		if (ListUtil.isEmpty(assetCategories)) {
			return;
		}

		for (AssetCategory assetCategory : assetCategories) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, friendlyURLEntry, assetCategory,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private FeatureFlagManager _featureFlagManager;

	@Reference
	private FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.friendly.url.model.FriendlyURLEntry)"
	)
	private StagedModelRepository<FriendlyURLEntry> _stagedModelRepository;

}
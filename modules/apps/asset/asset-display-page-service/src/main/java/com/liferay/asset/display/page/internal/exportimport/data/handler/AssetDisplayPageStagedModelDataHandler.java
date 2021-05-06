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

package com.liferay.asset.display.page.internal.exportimport.data.handler;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AssetDisplayPageStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetDisplayPageEntry> {

	public static final String[] CLASS_NAMES = {
		AssetDisplayPageEntry.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			AssetDisplayPageEntry assetDisplayPageEntry)
		throws Exception {

		Element assetDisplayPageElement =
			portletDataContext.getExportDataElement(assetDisplayPageEntry);

		portletDataContext.addClassedModel(
			assetDisplayPageElement,
			ExportImportPathUtil.getModelPath(assetDisplayPageEntry),
			assetDisplayPageEntry);

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.fetchLayoutPageTemplateEntry(
				assetDisplayPageEntry.getLayoutPageTemplateEntryId());

		if (layoutPageTemplateEntry != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, assetDisplayPageEntry,
				layoutPageTemplateEntry,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			AssetDisplayPageEntry assetDisplayPageEntry)
		throws Exception {

		AssetDisplayPageEntry importedAssetDisplayPageEntry =
			(AssetDisplayPageEntry)assetDisplayPageEntry.clone();

		long layoutPageTemplateEntryId = 0;

		if (importedAssetDisplayPageEntry.getLayoutPageTemplateEntryId() > 0) {
			Map<Long, Long> layoutPageTemplateEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					LayoutPageTemplateEntry.class);

			layoutPageTemplateEntryId = MapUtil.getLong(
				layoutPageTemplateEntryIds,
				assetDisplayPageEntry.getLayoutPageTemplateEntryId(),
				assetDisplayPageEntry.getLayoutPageTemplateEntryId());
		}

		Map<Long, Long> plids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		long plid = MapUtil.getLong(
			plids, assetDisplayPageEntry.getPlid(),
			assetDisplayPageEntry.getPlid());

		importedAssetDisplayPageEntry.setGroupId(
			portletDataContext.getScopeGroupId());
		importedAssetDisplayPageEntry.setCompanyId(
			portletDataContext.getCompanyId());
		importedAssetDisplayPageEntry.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
		importedAssetDisplayPageEntry.setPlid(plid);

		Map<Long, Long> newClassPKsMap =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				importedAssetDisplayPageEntry.getClassName());

		long existingClassPK = MapUtil.getLong(
			newClassPKsMap, importedAssetDisplayPageEntry.getClassPK(),
			importedAssetDisplayPageEntry.getClassPK());

		AssetDisplayPageEntry existingAssetDisplayPageEntry =
			_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
				portletDataContext.getScopeGroupId(),
				importedAssetDisplayPageEntry.getClassNameId(),
				existingClassPK);

		if ((existingAssetDisplayPageEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedAssetDisplayPageEntry.setClassPK(existingClassPK);

			try {
				importedAssetDisplayPageEntry =
					_stagedModelRepository.addStagedModel(
						portletDataContext, importedAssetDisplayPageEntry);
			}
			catch (Exception exception) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to add asset display page", exception);
				}

				portletDataContext.removePrimaryKey(
					ExportImportPathUtil.getModelPath(assetDisplayPageEntry));

				return;
			}
		}
		else {
			importedAssetDisplayPageEntry.setMvccVersion(
				existingAssetDisplayPageEntry.getMvccVersion());
			importedAssetDisplayPageEntry.setAssetDisplayPageEntryId(
				existingAssetDisplayPageEntry.getAssetDisplayPageEntryId());
			importedAssetDisplayPageEntry.setClassPK(
				existingAssetDisplayPageEntry.getClassPK());

			importedAssetDisplayPageEntry =
				_stagedModelRepository.updateStagedModel(
					portletDataContext, importedAssetDisplayPageEntry);
		}

		portletDataContext.importClassedModel(
			assetDisplayPageEntry, importedAssetDisplayPageEntry);
	}

	@Override
	protected StagedModelRepository<AssetDisplayPageEntry>
		getStagedModelRepository() {

		return _stagedModelRepository;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetDisplayPageStagedModelDataHandler.class);

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.display.page.model.AssetDisplayPageEntry)"
	)
	private StagedModelRepository<AssetDisplayPageEntry> _stagedModelRepository;

}
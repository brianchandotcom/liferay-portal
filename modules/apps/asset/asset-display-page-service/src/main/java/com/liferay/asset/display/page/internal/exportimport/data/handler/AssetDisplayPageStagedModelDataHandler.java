/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.display.page.internal.exportimport.data.handler;

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.staging.StagingGroupHelper;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(service = StagedModelDataHandler.class)
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

		_exportAssetObject(portletDataContext, assetDisplayPageEntry);
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

		importedAssetDisplayPageEntry.setGroupId(
			portletDataContext.getScopeGroupId());
		importedAssetDisplayPageEntry.setCompanyId(
			portletDataContext.getCompanyId());
		importedAssetDisplayPageEntry.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
		importedAssetDisplayPageEntry.setPlid(
			MapUtil.getLong(
				plids, assetDisplayPageEntry.getPlid(),
				assetDisplayPageEntry.getPlid()));

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

		if (existingAssetDisplayPageEntry == null) {
			existingAssetDisplayPageEntry =
				_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
					assetDisplayPageEntry.getUuid(),
					portletDataContext.getScopeGroupId());
		}

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

	private void _exportAssetObject(
			PortletDataContext portletDataContext,
			AssetDisplayPageEntry assetDisplayPageEntry)
		throws Exception {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetDisplayPageEntry.getClassName());

		if (assetRendererFactory == null) {
			return;
		}

		AssetRenderer<?> assetRenderer = assetRendererFactory.getAssetRenderer(
			assetDisplayPageEntry.getClassPK());

		if ((assetRenderer == null) ||
			(assetRenderer.getAssetObject() == null) ||
			!(assetRenderer.getAssetObject() instanceof StagedModel)) {

			return;
		}

		if (ExportImportThreadLocal.isStagingInProcess() &&
			!_stagingGroupHelper.isStagedPortlet(
				assetRenderer.getGroupId(),
				assetRendererFactory.getPortletId())) {

			return;
		}

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, assetDisplayPageEntry,
			(StagedModel)assetRenderer.getAssetObject(),
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY,
			portletDataContext.getPortletId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetDisplayPageStagedModelDataHandler.class);

	@Reference
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.display.page.model.AssetDisplayPageEntry)"
	)
	private StagedModelRepository<AssetDisplayPageEntry> _stagedModelRepository;

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

}
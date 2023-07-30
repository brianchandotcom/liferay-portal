/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.link.internal.exportimport.data.handler;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.asset.link.internal.exportimport.staged.model.repository.StagedAssetLinkStagedModelRepositoryUtil;
import com.liferay.asset.link.model.adapter.StagedAssetLink;
import com.liferay.asset.util.StagingAssetEntryHelper;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.xml.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Máté Thurzó
 */
@Component(service = StagedModelDataHandler.class)
public class StagedAssetLinkStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedAssetLink> {

	public static final String[] CLASS_NAMES = {
		StagedAssetLink.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			StagedAssetLink stagedAssetLink)
		throws Exception {

		Element stagedAssetLinkElement =
			portletDataContext.getExportDataElement(stagedAssetLink);

		AssetEntry assetEntry1 = _assetEntryLocalService.fetchEntry(
			stagedAssetLink.getEntryId1());

		_stagingAssetEntryHelper.addAssetReference(
			portletDataContext, stagedAssetLink, stagedAssetLinkElement,
			assetEntry1);

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			stagedAssetLink.getEntryId2());

		_stagingAssetEntryHelper.addAssetReference(
			portletDataContext, stagedAssetLink, stagedAssetLinkElement,
			assetEntry2);

		portletDataContext.addClassedModel(
			stagedAssetLinkElement,
			ExportImportPathUtil.getModelPath(stagedAssetLink),
			stagedAssetLink);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			StagedAssetLink stagedAssetLink)
		throws Exception {

		StagedAssetLink existingStagedAssetLink =
			StagedAssetLinkStagedModelRepositoryUtil.fetchExistingAssetLink(
				portletDataContext.getScopeGroupId(),
				stagedAssetLink.getEntry1Uuid(),
				stagedAssetLink.getEntry2Uuid());

		if ((existingStagedAssetLink == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			_stagedAssetLinkStagedModelRepository.addStagedModel(
				portletDataContext, stagedAssetLink);
		}
		else {
			_stagedAssetLinkStagedModelRepository.updateStagedModel(
				portletDataContext, existingStagedAssetLink);
		}
	}

	@Override
	protected StagedModelRepository<StagedAssetLink>
		getStagedModelRepository() {

		return _stagedAssetLinkStagedModelRepository;
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.link.model.adapter.StagedAssetLink)"
	)
	private StagedModelRepository<StagedAssetLink>
		_stagedAssetLinkStagedModelRepository;

	@Reference
	private StagingAssetEntryHelper _stagingAssetEntryHelper;

}
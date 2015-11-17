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

package com.liferay.asset.categories.admin.web.lar;

import com.liferay.asset.exportimport.staged.model.repository.StagedAssetLinkStagedModelRepository;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.adapter.StagedAssetLink;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.exportimport.lar.ExportImportPathUtil;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandler;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class StagedAssetLinkStagedModelDataHandler
	extends BaseStagedModelDataHandler<StagedAssetLink> {

	public static final String[] CLASS_NAMES =
		{StagedAssetLink.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	protected void addAssetReference(
		PortletDataContext portletDataContext, StagedAssetLink stagedAssetLink,
		Element stagedAssetLinkElement, AssetEntry assetEntry) {

		AssetRenderer<? extends StagedModel> assetRenderer = null;
		StagedModel stagedModel = null;

		try {
			assetRenderer =
				(AssetRenderer<? extends StagedModel>)
					assetEntry.getAssetRenderer();

			stagedModel = assetRenderer.getAssetObject();
		}
		catch (Exception e) {
			return;
		}

		if (stagedModel == null) {
			return;
		}

		portletDataContext.addReferenceElement(
			stagedAssetLink, stagedAssetLinkElement, stagedModel,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY_DISPOSABLE, true);
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

		addAssetReference(
			portletDataContext, stagedAssetLink, stagedAssetLinkElement,
			assetEntry1);

		AssetEntry assetEntry2 = _assetEntryLocalService.fetchEntry(
			stagedAssetLink.getEntryId2());

		addAssetReference(
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
			_stagedAssetLinkStagedModelRepository.fetchExistingAssetLink(
				portletDataContext.getScopeGroupId(),
				stagedAssetLink.getEntry1Uuid(),
				stagedAssetLink.getEntry2Uuid());

		if (existingStagedAssetLink != null) {
			_stagedAssetLinkStagedModelRepository.updateStagedModel(
				portletDataContext, existingStagedAssetLink);

			return;
		}

		_stagedAssetLinkStagedModelRepository.addStagedModel(
			portletDataContext, stagedAssetLink);
	}

	@Override
	protected StagedModelRepository<StagedAssetLink>
		getStagedModelRepository() {

		return _stagedAssetLinkStagedModelRepository;
	}

	@Reference(unbind = "-")
	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	@Reference(
		target =
			"(model.class.name=" +
				"com.liferay.asset.exportimport.staged.model.repository." +
					"StagedAssetLinkStagedModelRepository)",
		unbind = "-"
	)
	protected void setStagedAssetLinkStagedModelRepository(
		StagedAssetLinkStagedModelRepository
			stagedAssetLinkStagedModelRepository) {

		_stagedAssetLinkStagedModelRepository =
			stagedAssetLinkStagedModelRepository;
	}

	private volatile AssetEntryLocalService _assetEntryLocalService;
	private volatile StagedAssetLinkStagedModelRepository
		_stagedAssetLinkStagedModelRepository;

}
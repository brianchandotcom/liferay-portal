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

import com.liferay.asset.categories.admin.web.constants.AssetCategoriesAdminPortletKeys;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.model.adapter.StagedAssetLink;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;
import com.liferay.portlet.asset.service.AssetLinkLocalService;
import com.liferay.portlet.asset.service.AssetVocabularyLocalService;
import com.liferay.portlet.exportimport.lar.BasePortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.PortletDataHandler;
import com.liferay.portlet.exportimport.lar.PortletDataHandlerBoolean;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;
import com.liferay.portlet.exportimport.lar.StagedModelType;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + AssetCategoriesAdminPortletKeys.ASSET_CATEGORIES_ADMIN
	},
	service = PortletDataHandler.class
)
public class AssetCategoryPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "asset_category";

	@Activate
	protected void activate() {
		setDataAlwaysStaged(true);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(AssetCategory.class),
			new StagedModelType(AssetVocabulary.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "categories", true, false, null,
				AssetCategory.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "vocabularies", true, false, null,
				AssetVocabulary.class.getName()));
		setPublishToLiveByDefault(true);
		setRank(110);
	}

	@Override
	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (portletDataContext.addPrimaryKey(
				AssetCategoryPortletDataHandler.class, "deleteData")) {

			return portletPreferences;
		}

		_assetVocabularyLocalService.deleteVocabularies(
			portletDataContext.getScopeGroupId());

		return portletPreferences;
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		Element rootElement = addExportDataRootElement(portletDataContext);

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		if (portletDataContext.getBooleanParameter(NAMESPACE, "categories")) {
			ActionableDynamicQuery categoryActionableDynamicQuery =
				getCategoryActionableDynamicQuery(portletDataContext);

			categoryActionableDynamicQuery.performActions();
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "vocabularies")) {
			ActionableDynamicQuery vocabularyActionableDynamicQuery =
				getVocabularyActionableDynamicQuery(portletDataContext);

			vocabularyActionableDynamicQuery.performActions();
		}

		ActionableDynamicQuery linkActionableDynamicQuery =
			_assetLinkLocalService.getExportActionbleDynamicQuery(
				portletDataContext);

		linkActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		if (portletDataContext.getBooleanParameter(NAMESPACE, "categories")) {
			Element categoriesElement =
				portletDataContext.getImportDataGroupElement(
					AssetCategory.class);

			List<Element> categoryElements = categoriesElement.elements();

			for (Element categoryElement : categoryElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, categoryElement);
			}
		}

		if (portletDataContext.getBooleanParameter(NAMESPACE, "vocabularies")) {
			Element vocabulariesElement =
				portletDataContext.getImportDataGroupElement(
					AssetVocabulary.class);

			List<Element> vocabularyElements = vocabulariesElement.elements();

			for (Element vocabularyElement : vocabularyElements) {
				StagedModelDataHandlerUtil.importStagedModel(
					portletDataContext, vocabularyElement);
			}
		}

		Element linksElement = portletDataContext.getImportDataGroupElement(
			StagedAssetLink.class);

		List<Element> linkElements = linksElement.elements();

		for (Element linkElement : linkElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, linkElement);
		}

		return null;
	}

	@Override
	protected void doPrepareManifestSummary(
			PortletDataContext portletDataContext,
			PortletPreferences portletPreferences)
		throws Exception {

		ActionableDynamicQuery categoryActionableDynamicQuery =
			getCategoryActionableDynamicQuery(portletDataContext);

		categoryActionableDynamicQuery.performCount();

		ActionableDynamicQuery vocabularyActionableDynamicQuery =
			getVocabularyActionableDynamicQuery(portletDataContext);

		vocabularyActionableDynamicQuery.performCount();
	}

	protected ActionableDynamicQuery getCategoryActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ActionableDynamicQuery actionableDynamicQuery =
			_assetCategoryLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		// Override date range criteria

		actionableDynamicQuery.setAddCriteriaMethod(null);

		return actionableDynamicQuery;
	}

	protected ActionableDynamicQuery getVocabularyActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		ActionableDynamicQuery actionableDynamicQuery =
			_assetVocabularyLocalService.getExportActionableDynamicQuery(
				portletDataContext);

		// Override date range criteria

		actionableDynamicQuery.setAddCriteriaMethod(null);

		return actionableDynamicQuery;
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetLinkLocalService(
		AssetLinkLocalService assetLinkLocalService) {

		_assetLinkLocalService = assetLinkLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetVocabularyLocalService(
		AssetVocabularyLocalService assetVocabularyLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private volatile AssetCategoryLocalService _assetCategoryLocalService;
	private volatile AssetLinkLocalService _assetLinkLocalService;
	private volatile AssetVocabularyLocalService _assetVocabularyLocalService;

}
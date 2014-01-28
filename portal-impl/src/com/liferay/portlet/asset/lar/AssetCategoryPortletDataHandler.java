/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.lar;

import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.xml.Element;

import javax.portlet.PortletPreferences;

/**
 * @author Mate Thurzo
 */
public class AssetCategoryPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "asset_category";

	public AssetCategoryPortletDataHandler() {
	}

	@Override
	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (exportPortletDataAll || exportCategories || companyGroup) {
			if (_log.isDebugEnabled()) {
				_log.debug("Export categories");
			}

			List<AssetVocabulary> assetVocabularies =
				AssetVocabularyLocalServiceUtil.getGroupVocabularies(
					portletDataContext.getGroupId());

			for (AssetVocabulary assetVocabulary : assetVocabularies) {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, assetVocabulary);
			}

			List<AssetCategory> assetCategories =
				AssetCategoryUtil.findByGroupId(
					portletDataContext.getGroupId());

			for (AssetCategory assetCategory : assetCategories) {
				StagedModelDataHandlerUtil.exportStagedModel(
					portletDataContext, assetCategory);
			}
		}
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Element assetVocabulariesElement =
			portletDataContext.getImportDataGroupElement(AssetVocabulary.class);

		List<Element> assetVocabularyElements =
			assetVocabulariesElement.elements();

		for (Element assetVocabularyElement : assetVocabularyElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, assetVocabularyElement);
		}

		Element assetCategoriesElement =
			portletDataContext.getImportDataGroupElement(AssetCategory.class);

		List<Element> assetCategoryElements = assetCategoriesElement.elements();

		for (Element assetCategoryElement : assetCategoryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, assetCategoryElement);
		}
	}

}
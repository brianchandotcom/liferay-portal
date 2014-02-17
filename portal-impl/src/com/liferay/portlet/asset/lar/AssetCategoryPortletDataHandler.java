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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;
import com.liferay.portlet.asset.service.persistence.AssetCategoryExportActionableDynamicQuery;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyExportActionableDynamicQuery;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Mate Thurzo
 */
public class AssetCategoryPortletDataHandler extends BasePortletDataHandler {

	public static final String NAMESPACE = "asset_category";

	public AssetCategoryPortletDataHandler() {
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(AssetCategory.class),
			new StagedModelType(AssetVocabulary.class));
		setPublishToLiveByDefault(true);
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

		AssetVocabularyLocalServiceUtil.deleteVocabularies(
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

		ActionableDynamicQuery categoryActionableDynamicQuery =
			getCategoryActionableDynamicQuery(portletDataContext);

		categoryActionableDynamicQuery.performActions();

		ActionableDynamicQuery vocabularyActionableDynamicQuery =
			getVocabularyActionableDynamicQuery(portletDataContext);

		vocabularyActionableDynamicQuery.performActions();

		return getExportDataRootElementString(rootElement);
	}

	@Override
	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		Element categoriesElement =
			portletDataContext.getImportDataGroupElement(AssetCategory.class);

		List<Element> categoryElements = categoriesElement.elements();

		for (Element categoryElement : categoryElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, categoryElement);
		}

		Element vocabulariesElement =
			portletDataContext.getImportDataGroupElement(AssetVocabulary.class);

		List<Element> vocabularyElements = vocabulariesElement.elements();

		for (Element vocabularyElement : vocabularyElements) {
			StagedModelDataHandlerUtil.importStagedModel(
				portletDataContext, vocabularyElement);
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
			final PortletDataContext portletDataContext)
		throws SystemException {

		return new AssetCategoryExportActionableDynamicQuery(
			portletDataContext) {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {

				// Omit date range filter by override

				return;
			}

		};
	}

	protected ActionableDynamicQuery getVocabularyActionableDynamicQuery(
			final PortletDataContext portletDataContext)
		throws SystemException {

		return new AssetVocabularyExportActionableDynamicQuery(
			portletDataContext) {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {

				// Omit date range filter by override

				return;
			}

		};
	}

}
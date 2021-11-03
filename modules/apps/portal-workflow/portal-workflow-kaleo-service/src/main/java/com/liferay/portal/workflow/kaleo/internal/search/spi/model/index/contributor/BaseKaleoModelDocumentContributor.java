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

package com.liferay.portal.workflow.kaleo.internal.search.spi.model.index.contributor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;

import java.util.Locale;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
public abstract class BaseKaleoModelDocumentContributor {

	protected void addAssetEntryAttributes(
		Consumer<AssetEntry> assetEntryConsumer, String className, long classPK,
		Document document, long groupId) {

		AssetEntry assetEntry = _getAssetEntry(className, classPK);

		if (assetEntry != null) {
			document.addLocalizedText(
				"assetDescription",
				LocalizationUtil.populateLocalizationMap(
					assetEntry.getDescriptionMap(),
					assetEntry.getDefaultLanguageId(),
					assetEntry.getGroupId()));
			document.addLocalizedText(
				"assetTitle",
				LocalizationUtil.populateLocalizationMap(
					assetEntry.getTitleMap(), assetEntry.getDefaultLanguageId(),
					assetEntry.getGroupId()));

			assetEntryConsumer.accept(assetEntry);
		}
		else {
			WorkflowHandler<?> workflowHandler =
				WorkflowHandlerRegistryUtil.getWorkflowHandler(className);

			if (workflowHandler == null) {
				return;
			}

			for (Locale availableLocale :
					LanguageUtil.getAvailableLocales(groupId)) {

				document.addText(
					LocalizationUtil.getLocalizedName(
						"assetTitle", availableLocale.getLanguage()),
					workflowHandler.getTitle(classPK, availableLocale));
			}
		}
	}

	protected void addAssetEntryAttributes(
		String className, long classPK, Document document, long groupId) {

		addAssetEntryAttributes(
			assetEntry -> {
			},
			className, classPK, document, groupId);
	}

	@Reference
	protected AssetEntryLocalService assetEntryLocalService;

	private AssetEntry _getAssetEntry(String className, long classPK) {
		try {
			AssetRenderer<?> assetRenderer = _getAssetRenderer(
				className, classPK);

			if (assetRenderer != null) {
				return assetEntryLocalService.getEntry(
					assetRenderer.getClassName(), assetRenderer.getClassPK());
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return null;
	}

	private AssetRenderer<?> _getAssetRenderer(String className, long classPK)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory != null) {
			return assetRendererFactory.getAssetRenderer(classPK);
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseKaleoModelDocumentContributor.class);

}
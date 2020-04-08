/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.helper;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = InstanceIndexerHelper.class)
public class InstanceIndexerHelper {

	public Map<Locale, String> createAssetTitleLocalizationMap(
		KaleoInstance kaleoInstance) {

		AssetRenderer<?> assetRenderer = _getAssetRenderer(
			kaleoInstance.getClassName(), kaleoInstance.getClassPK());

		if (assetRenderer != null) {
			AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
				assetRenderer.getClassName(), assetRenderer.getClassPK());

			if (assetEntry != null) {
				return LocalizationUtil.populateLocalizationMap(
					assetEntry.getTitleMap(), assetEntry.getDefaultLanguageId(),
					assetEntry.getGroupId());
			}
		}

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				kaleoInstance.getClassName());

		if (workflowHandler != null) {
			Map<Locale, String> localizationMap = new HashMap<>();

			for (Locale availableLocale :
					LanguageUtil.getAvailableLocales(
						kaleoInstance.getGroupId())) {

				localizationMap.put(
					availableLocale,
					workflowHandler.getTitle(
						kaleoInstance.getClassPK(), availableLocale));
			}

			return localizationMap;
		}

		return Collections.emptyMap();
	}

	public Map<Locale, String> createAssetTypeLocalizationMap(
		KaleoInstance kaleoInstance) {

		Map<Locale, String> localizationMap = new HashMap<>();

		for (Locale availableLocale :
				LanguageUtil.getAvailableLocales(kaleoInstance.getGroupId())) {

			localizationMap.put(
				availableLocale,
				ResourceActionsUtil.getModelResource(
					availableLocale, kaleoInstance.getClassName()));
		}

		return localizationMap;
	}

	private AssetRenderer<?> _getAssetRenderer(String className, long classPK) {
		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		if (assetRendererFactory != null) {
			try {
				return assetRendererFactory.getAssetRenderer(classPK);
			}
			catch (PortalException portalException) {
				if (_log.isDebugEnabled()) {
					_log.debug(portalException, portalException);
				}
			}
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InstanceIndexerHelper.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

}
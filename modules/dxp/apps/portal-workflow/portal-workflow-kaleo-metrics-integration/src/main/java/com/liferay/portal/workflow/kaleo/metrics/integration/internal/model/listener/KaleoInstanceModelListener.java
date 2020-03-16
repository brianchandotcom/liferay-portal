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

package com.liferay.portal.workflow.kaleo.metrics.integration.internal.model.listener;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandler;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.metrics.search.index.InstanceWorkflowMetricsIndexer;

import java.time.Duration;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = ModelListener.class)
public class KaleoInstanceModelListener
	extends BaseKaleoModelListener<KaleoInstance> {

	@Override
	public void onAfterCreate(KaleoInstance kaleoInstance) {
		KaleoDefinitionVersion kaleoDefinitionVersion =
			getKaleoDefinitionVersion(
				kaleoInstance.getKaleoDefinitionVersionId());

		if (Objects.isNull(kaleoDefinitionVersion)) {
			return;
		}

		_instanceWorkflowMetricsIndexer.addInstance(
			_createAssetTitleLocalizationMap(kaleoInstance),
			_createAssetTypeLocalizationMap(kaleoInstance),
			kaleoInstance.getClassName(), kaleoInstance.getClassPK(),
			kaleoInstance.getCompanyId(), null, kaleoInstance.getCreateDate(),
			kaleoInstance.getKaleoInstanceId(), kaleoInstance.getModifiedDate(),
			kaleoInstance.getKaleoDefinitionId(),
			kaleoDefinitionVersion.getVersion(), kaleoInstance.getUserId(),
			kaleoInstance.getUserName());
	}

	@Override
	public void onAfterRemove(KaleoInstance kaleoInstance) {
		_instanceWorkflowMetricsIndexer.deleteInstance(
			kaleoInstance.getCompanyId(), kaleoInstance.getKaleoInstanceId());
	}

	@Override
	public void onBeforeUpdate(KaleoInstance kaleoInstance) {
		KaleoInstance currentKaleoInstance =
			_kaleoInstanceLocalService.fetchKaleoInstance(
				kaleoInstance.getKaleoInstanceId());

		if (!currentKaleoInstance.isCompleted() &&
			kaleoInstance.isCompleted()) {

			Date createDate = kaleoInstance.getCreateDate();
			Date completionDate = kaleoInstance.getCompletionDate();

			Duration duration = Duration.between(
				createDate.toInstant(), completionDate.toInstant());

			_instanceWorkflowMetricsIndexer.completeInstance(
				kaleoInstance.getCompanyId(), kaleoInstance.getCompletionDate(),
				duration.toMillis(), kaleoInstance.getKaleoInstanceId(),
				kaleoInstance.getModifiedDate());
		}
		else {
			_instanceWorkflowMetricsIndexer.updateInstance(
				_createAssetTitleLocalizationMap(kaleoInstance),
				_createAssetTypeLocalizationMap(kaleoInstance),
				kaleoInstance.getCompanyId(),
				kaleoInstance.getKaleoInstanceId(),
				kaleoInstance.getModifiedDate());
		}
	}

	private Map<Locale, String> _createAssetTitleLocalizationMap(
		KaleoInstance kaleoInstance) {

		try {
			AssetRenderer<?> assetRenderer = _getAssetRenderer(
				kaleoInstance.getClassName(), kaleoInstance.getClassPK());

			if (assetRenderer != null) {
				AssetEntry assetEntry = _assetEntryLocalService.getEntry(
					assetRenderer.getClassName(), assetRenderer.getClassPK());

				return LocalizationUtil.populateLocalizationMap(
					assetEntry.getTitleMap(), assetEntry.getDefaultLanguageId(),
					assetEntry.getGroupId());
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		WorkflowHandler<?> workflowHandler =
			WorkflowHandlerRegistryUtil.getWorkflowHandler(
				kaleoInstance.getClassName());

		Map<Locale, String> localizationMap = new HashMap<>();

		for (Locale availableLocale :
				LanguageUtil.getAvailableLocales(kaleoInstance.getGroupId())) {

			localizationMap.put(
				availableLocale,
				workflowHandler.getTitle(
					kaleoInstance.getClassPK(), availableLocale));
		}

		return localizationMap;
	}

	private Map<Locale, String> _createAssetTypeLocalizationMap(
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

	private AssetRenderer<?> _getAssetRenderer(String className, long classPK)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory = _getAssetRendererFactory(
			className);

		if (assetRendererFactory != null) {
			return assetRendererFactory.getAssetRenderer(classPK);
		}

		return null;
	}

	private AssetRendererFactory<?> _getAssetRendererFactory(String className) {
		return AssetRendererFactoryRegistryUtil.
			getAssetRendererFactoryByClassName(className);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoInstanceModelListener.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private InstanceWorkflowMetricsIndexer _instanceWorkflowMetricsIndexer;

	@Reference
	private KaleoInstanceLocalService _kaleoInstanceLocalService;

}
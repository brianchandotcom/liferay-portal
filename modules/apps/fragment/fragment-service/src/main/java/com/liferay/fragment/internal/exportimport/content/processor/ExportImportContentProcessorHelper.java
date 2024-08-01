/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.content.processor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.info.search.InfoSearchClassMapperRegistry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = ExportImportContentProcessorHelper.class)
public class ExportImportContentProcessorHelper {

	public void exportContentReference(
		String className, long classPK, boolean exportReferencedContent,
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		Object object = _getReferenceObject(
			className, classPK, portletDataContext);

		if (object == null) {
			return;
		}

		if (exportReferencedContent) {
			try {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, stagedModel, (StagedModel)object,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					String errorMessage = StringBundler.concat(
						"Staged model with class name ",
						stagedModel.getModelClassName(), " and primary key ",
						stagedModel.getPrimaryKeyObj(),
						" references asset entry with class primary key ",
						classPK, " and class name ", className,
						" that could not be exported due to ", exception);

					if (Validator.isNotNull(exception.getMessage())) {
						errorMessage = StringBundler.concat(
							errorMessage, ": ", exception.getMessage());
					}

					_log.debug(errorMessage, exception);
				}
			}
		}
		else {
			Element entityElement = portletDataContext.getExportDataElement(
				stagedModel);

			portletDataContext.addReferenceElement(
				stagedModel, entityElement, (ClassedModel)object,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}
	}

	public void replaceImportContentReferences(
		JSONObject jsonObject, PortletDataContext portletDataContext) {

		String className = jsonObject.getString("className");
		long classPK = jsonObject.getLong("classPK");

		if (Validator.isNull(className) || (classPK == 0)) {
			return;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_infoSearchClassMapperRegistry.getSearchClassName(className));

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if ((assetRendererFactory == null) ||
			(ExportImportThreadLocal.isStagingInProcess() &&
			 !stagingGroupHelper.isStagedPortlet(
				 portletDataContext.getScopeGroupId(),
				 assetRendererFactory.getPortletId()))) {

			return;
		}

		jsonObject.put("classNameId", _portal.getClassNameId(className));

		Map<Long, Long> primaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(className);

		jsonObject.put(
			"classPK", MapUtil.getLong(primaryKeys, classPK, classPK));
	}

	private Object _getReferenceObject(
		String className, long classPK, PortletDataContext portletDataContext) {

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			_infoSearchClassMapperRegistry.getSearchClassName(className),
			classPK);

		if (assetEntry == null) {
			return null;
		}

		AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

		if (assetRenderer == null) {
			return null;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (ExportImportThreadLocal.isStagingInProcess() &&
			!stagingGroupHelper.isStagedPortlet(
				portletDataContext.getScopeGroupId(),
				assetRendererFactory.getPortletId())) {

			return null;
		}

		return assetRenderer.getAssetObject();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportContentProcessorHelper.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private InfoSearchClassMapperRegistry _infoSearchClassMapperRegistry;

	@Reference
	private Portal _portal;

}
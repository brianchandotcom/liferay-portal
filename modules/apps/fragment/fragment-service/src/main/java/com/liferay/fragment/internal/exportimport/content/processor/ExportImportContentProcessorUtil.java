/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.content.processor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.info.search.InfoSearchClassMapperRegistryUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.Map;

/**
 * @author Lourdes Fernández Besada
 */
public class ExportImportContentProcessorUtil {

	public static void exportContentReference(
		String className, long classPK, boolean exportReferencedContent,
		InfoItemServiceRegistry infoItemServiceRegistry,
		PortletDataContext portletDataContext, StagedModel stagedModel) {

		Object object = _getReferenceObject(
			className, classPK, infoItemServiceRegistry, portletDataContext);

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

	public static void replaceImportContentReferences(
		JSONObject jsonObject, PortletDataContext portletDataContext) {

		String className = jsonObject.getString("className");
		long classPK = jsonObject.getLong("classPK");

		if (Validator.isNull(className) || (classPK == 0)) {
			return;
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				InfoSearchClassMapperRegistryUtil.getSearchClassName(
					className));

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if ((assetRendererFactory != null) &&
			ExportImportThreadLocal.isStagingInProcess() &&
			!stagingGroupHelper.isStagedPortlet(
				portletDataContext.getScopeGroupId(),
				assetRendererFactory.getPortletId())) {

			return;
		}

		jsonObject.put("classNameId", PortalUtil.getClassNameId(className));

		Map<Long, Long> primaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(className);

		jsonObject.put(
			"classPK", MapUtil.getLong(primaryKeys, classPK, classPK));
	}

	private static Object _getInfoItem(
		String className, long classPK,
		InfoItemServiceRegistry infoItemServiceRegistry) {

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, className);

		if (infoItemObjectProvider != null) {
			try {
				return infoItemObjectProvider.getInfoItem(
					new ClassPKInfoItemIdentifier(classPK));
			}
			catch (NoSuchInfoItemException noSuchInfoItemException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchInfoItemException);
				}
			}
		}

		return null;
	}

	private static Object _getReferenceObject(
		String className, long classPK,
		InfoItemServiceRegistry infoItemServiceRegistry,
		PortletDataContext portletDataContext) {

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			InfoSearchClassMapperRegistryUtil.getSearchClassName(className),
			classPK);

		if (assetEntry == null) {
			return _getInfoItem(className, classPK, infoItemServiceRegistry);
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
		ExportImportContentProcessorUtil.class);

}
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

package com.liferay.fragment.internal.exportimport.content.processor;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRenderer;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.info.search.InfoSearchClassMapperTracker;
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
 * @author Víctor Galán
 */
@Component(
	property = "content.processor.type=FragmentEntryLinkEditableValues",
	service = ExportImportContentProcessor.class
)
public class EditableValuesItemSelectorExportImportContentProcessor
	extends BaseEditableValuesConfigurationExportImportContentProcessor {

	@Override
	protected String getConfigurationType() {
		return "itemSelector";
	}

	@Override
	protected FragmentEntryConfigurationParser
		getFragmentEntryConfigurationParser() {

		return _fragmentEntryConfigurationParser;
	}

	@Override
	protected void replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject configurationValueJSONObject,
			boolean exportReferencedContent)
		throws Exception {

		long classNameId = configurationValueJSONObject.getLong("classNameId");
		long classPK = configurationValueJSONObject.getLong("classPK");

		if ((classNameId == 0) || (classPK == 0)) {
			return;
		}

		_exportDDMTemplateReference(
			portletDataContext, stagedModel, configurationValueJSONObject);

		String className = _infoSearchClassMapperTracker.getSearchClassName(
			_portal.getClassName(classNameId));

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			className, classPK);

		if (assetEntry == null) {
			return;
		}

		AssetRenderer<?> assetRenderer = assetEntry.getAssetRenderer();

		if (assetRenderer == null) {
			return;
		}

		AssetRendererFactory<?> assetRendererFactory =
			assetRenderer.getAssetRendererFactory();

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (ExportImportThreadLocal.isStagingInProcess() &&
			!stagingGroupHelper.isStagedPortlet(
				portletDataContext.getScopeGroupId(),
				assetRendererFactory.getPortletId())) {

			return;
		}

		configurationValueJSONObject.put(
			"className", _portal.getClassName(classNameId));

		if (exportReferencedContent) {
			try {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, stagedModel,
					(StagedModel)assetRenderer.getAssetObject(),
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					String errorMessage = StringBundler.concat(
						"Staged model with class name ",
						stagedModel.getModelClassName(), " and primary key ",
						stagedModel.getPrimaryKeyObj(),
						" references asset entry with class primary key ",
						classPK, " and class name ",
						_portal.getClassName(classNameId),
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
				stagedModel, entityElement,
				(ClassedModel)assetRenderer.getAssetObject(),
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}
	}

	@Override
	protected void replaceImportContentReferences(
		PortletDataContext portletDataContext,
		JSONObject configurationValueJSONObject) {

		String className = configurationValueJSONObject.getString("className");

		if (Validator.isNull(className)) {
			return;
		}

		if (configurationValueJSONObject.has("template")) {
			JSONObject templateJSONObject =
				configurationValueJSONObject.getJSONObject("template");

			String ddmTemplateKey = templateJSONObject.getString("templateKey");

			if (Validator.isNull(ddmTemplateKey)) {
				return;
			}

			Map<String, String> ddmTemplateKeys =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					DDMTemplate.class + ".ddmTemplateKey");

			String importedDDMTemplateKey = MapUtil.getString(
				ddmTemplateKeys, ddmTemplateKey, ddmTemplateKey);

			templateJSONObject.put("templateKey", importedDDMTemplateKey);
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				_infoSearchClassMapperTracker.getSearchClassName(className));

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (ExportImportThreadLocal.isStagingInProcess() &&
			!stagingGroupHelper.isStagedPortlet(
				portletDataContext.getScopeGroupId(),
				assetRendererFactory.getPortletId())) {

			return;
		}

		long classPK = configurationValueJSONObject.getLong("classPK");

		if (classPK == 0) {
			return;
		}

		configurationValueJSONObject.put(
			"classNameId", _portal.getClassNameId(className));

		Map<Long, Long> primaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(className);

		classPK = MapUtil.getLong(primaryKeys, classPK, classPK);

		configurationValueJSONObject.put("classPK", classPK);
	}

	private void _exportDDMTemplateReference(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject configurationValueJSONObject)
		throws Exception {

		if (!configurationValueJSONObject.has("template")) {
			return;
		}

		JSONObject templateJSONObject =
			configurationValueJSONObject.getJSONObject("template");

		String ddmTemplateKey = templateJSONObject.getString("templateKey");

		if (Validator.isNull(ddmTemplateKey)) {
			return;
		}

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
			portletDataContext.getScopeGroupId(),
			_portal.getClassNameId(DDMStructure.class), ddmTemplateKey);

		if (ddmTemplate != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, stagedModel, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditableValuesItemSelectorExportImportContentProcessor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private InfoSearchClassMapperTracker _infoSearchClassMapperTracker;

	@Reference
	private Portal _portal;

}
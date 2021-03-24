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
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	implements ExportImportContentProcessor<JSONObject> {

	@Override
	public JSONObject replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableValuesJSONObject,
			boolean exportReferencedContent, boolean escapeContent)
		throws Exception {

		List<FragmentConfigurationField> fragmentConfigurationFields =
			_getItemSelectorFragmentConfigurationFields(
				(FragmentEntryLink)stagedModel);

		if (ListUtil.isEmpty(fragmentConfigurationFields)) {
			return editableValuesJSONObject;
		}

		JSONObject editableProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (editableProcessorJSONObject == null) {
			return editableValuesJSONObject;
		}

		for (FragmentConfigurationField fragmentConfigurationField :
				fragmentConfigurationFields) {

			JSONObject jsonObject = editableProcessorJSONObject.getJSONObject(
				fragmentConfigurationField.getName());

			_exportContentReferences(
				portletDataContext, stagedModel, jsonObject,
				exportReferencedContent);
		}

		return editableValuesJSONObject;
	}

	@Override
	public JSONObject replaceImportContentReferences(
		PortletDataContext portletDataContext, StagedModel stagedModel,
		JSONObject editableValuesJSONObject) {

		List<FragmentConfigurationField> fragmentConfigurationFields =
			_getItemSelectorFragmentConfigurationFields(
				(FragmentEntryLink)stagedModel);

		if (ListUtil.isEmpty(fragmentConfigurationFields)) {
			return editableValuesJSONObject;
		}

		JSONObject editableProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				_KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (editableProcessorJSONObject == null) {
			return editableValuesJSONObject;
		}

		for (FragmentConfigurationField fragmentConfigurationField :
				fragmentConfigurationFields) {

			JSONObject jsonObject = editableProcessorJSONObject.getJSONObject(
				fragmentConfigurationField.getName());

			_replaceImportContentReferences(portletDataContext, jsonObject);
		}

		return editableValuesJSONObject;
	}

	@Override
	public void validateContentReferences(long groupId, JSONObject jsonObject) {
	}

	private void _exportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableJSONObject, boolean exportReferencedContent)
		throws Exception {

		long classNameId = editableJSONObject.getLong("classNameId");
		long classPK = editableJSONObject.getLong("classPK");

		if ((classNameId == 0) || (classPK == 0)) {
			return;
		}

		_exportDDMTemplateReference(
			portletDataContext, stagedModel, editableJSONObject);

		// LPS-111037

		String className = _portal.getClassName(classNameId);

		if (Objects.equals(className, FileEntry.class.getName())) {
			className = DLFileEntry.class.getName();
		}

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

		editableJSONObject.put("className", _portal.getClassName(classNameId));

		if (exportReferencedContent) {
			try {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, stagedModel,
					(StagedModel)assetRenderer.getAssetObject(),
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					StringBundler messageSB = new StringBundler(11);

					messageSB.append("Staged model with class name ");
					messageSB.append(stagedModel.getModelClassName());
					messageSB.append(" and primary key ");
					messageSB.append(stagedModel.getPrimaryKeyObj());
					messageSB.append(" references asset entry with class ");
					messageSB.append("primary key ");
					messageSB.append(classPK);
					messageSB.append(" and class name ");
					messageSB.append(_portal.getClassName(classNameId));
					messageSB.append(" that could not be exported due to ");
					messageSB.append(exception);

					String errorMessage = messageSB.toString();

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

	private void _exportDDMTemplateReference(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableJSONObject)
		throws Exception {

		if (!editableJSONObject.has("template")) {
			return;
		}

		JSONObject templateJSONObject = editableJSONObject.getJSONObject(
			"template");

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

	private List<FragmentConfigurationField>
		_getItemSelectorFragmentConfigurationFields(
			FragmentEntryLink fragmentEntryLink) {

		List<FragmentConfigurationField> fragmentConfigurationFields =
			_fragmentEntryConfigurationParser.getFragmentConfigurationFields(
				fragmentEntryLink.getConfiguration());

		Stream<FragmentConfigurationField> stream =
			fragmentConfigurationFields.stream();

		return stream.filter(
			fragmentConfigurationField -> Objects.equals(
				fragmentConfigurationField.getType(), "itemSelector")
		).collect(
			Collectors.toList()
		);
	}

	private void _replaceImportContentReferences(
		PortletDataContext portletDataContext, JSONObject editableJSONObject) {

		String className = editableJSONObject.getString("className");

		if (Validator.isNull(className)) {
			return;
		}

		if (editableJSONObject.has("template")) {
			JSONObject templateJSONObject = editableJSONObject.getJSONObject(
				"template");

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

		// LPS-111037

		String assetRendererFactoryByClassName = className;

		if (Objects.equals(
				assetRendererFactoryByClassName, FileEntry.class.getName())) {

			assetRendererFactoryByClassName = DLFileEntry.class.getName();
		}

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				assetRendererFactoryByClassName);

		StagingGroupHelper stagingGroupHelper =
			StagingGroupHelperUtil.getStagingGroupHelper();

		if (ExportImportThreadLocal.isStagingInProcess() &&
			!stagingGroupHelper.isStagedPortlet(
				portletDataContext.getScopeGroupId(),
				assetRendererFactory.getPortletId())) {

			return;
		}

		long classPK = editableJSONObject.getLong("classPK");

		if (classPK == 0) {
			return;
		}

		editableJSONObject.put(
			"classNameId", _portal.getClassNameId(className));

		Map<Long, Long> primaryKeys =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(className);

		classPK = MapUtil.getLong(primaryKeys, classPK, classPK);

		editableJSONObject.put("classPK", classPK);
	}

	private static final String _KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.freemarker." +
			"FreeMarkerFragmentEntryProcessor";

	private static final Log _log = LogFactoryUtil.getLog(
		EditableValuesItemSelectorExportImportContentProcessor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

	@Reference
	private Portal _portal;

}
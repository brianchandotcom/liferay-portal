/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.content.processor;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.fragment.collection.filter.FragmentCollectionFilter;
import com.liferay.fragment.collection.filter.FragmentCollectionFilterRegistry;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.util.configuration.FragmentConfigurationField;
import com.liferay.fragment.util.configuration.FragmentEntryConfigurationParser;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Víctor Galán
 */
@Component(
	property = "content.processor.type=FragmentEntryLinkEditableValues",
	service = ExportImportContentProcessor.class
)
public class
	CategoryTreeNodeSelectorEditableValuesConfigurationExportImportContentProcessor
		extends BaseEditableValuesConfigurationExportImportContentProcessor {

	@Override
	protected String getConfigurationType() {
		return "categoryTreeNodeSelector";
	}

	@Override
	protected List<FragmentConfigurationField> getFragmentConfigurationFields(
		FragmentEntryLink fragmentEntryLink) {

		JSONObject editableValuesJSONObject =
			fragmentEntryLink.getEditableValuesJSONObject();

		JSONObject editableProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		if (editableProcessorJSONObject == null) {
			return super.getFragmentConfigurationFields(fragmentEntryLink);
		}

		String filterKey = editableProcessorJSONObject.getString("filterKey");

		if (!Objects.equals(filterKey, "category")) {
			return super.getFragmentConfigurationFields(fragmentEntryLink);
		}

		FragmentCollectionFilter fragmentCollectionFilter =
			_fragmentCollectionFilterRegistry.getFragmentCollectionFilter(
				filterKey);

		if (fragmentCollectionFilter == null) {
			return super.getFragmentConfigurationFields(fragmentEntryLink);
		}

		FragmentEntryConfigurationParser fragmentEntryConfigurationParser =
			getFragmentEntryConfigurationParser();

		return ListUtil.filter(
			fragmentEntryConfigurationParser.getFragmentConfigurationFields(
				fragmentCollectionFilter.getConfigurationJSONObject()),
			fragmentConfigurationField -> Objects.equals(
				fragmentConfigurationField.getType(), getConfigurationType()));
	}

	@Override
	protected FragmentEntryConfigurationParser
		getFragmentEntryConfigurationParser() {

		return _fragmentEntryConfigurationParser;
	}

	@Override
	protected void replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel referrerStagedModel,
			JSONObject configurationValueJSONObject,
			boolean exportReferencedContent)
		throws Exception {

		String assetCategoryTreeNodeType =
			configurationValueJSONObject.getString("categoryTreeNodeType");

		if (Validator.isNull(assetCategoryTreeNodeType)) {
			return;
		}

		StagedModel stagedModel = null;

		if (assetCategoryTreeNodeType.equals("Category")) {
			stagedModel = _fetchAssetCategory(
				portletDataContext, configurationValueJSONObject);
		}
		else if (assetCategoryTreeNodeType.equals("Vocabulary")) {
			stagedModel = _fetchAssetVocabulary(
				portletDataContext, configurationValueJSONObject);
		}

		if (stagedModel == null) {
			return;
		}

		if (exportReferencedContent) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, referrerStagedModel, stagedModel,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
		else {
			Element entityElement = portletDataContext.getExportDataElement(
				referrerStagedModel);

			portletDataContext.addReferenceElement(
				referrerStagedModel, entityElement, stagedModel,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY, true);
		}
	}

	@Override
	protected void replaceImportContentReferences(
		PortletDataContext portletDataContext,
		JSONObject configurationValueJSONObject) {

		long assetCategoryTreeNodeId = GetterUtil.getLong(
			configurationValueJSONObject.getString("categoryTreeNodeId"));

		if (assetCategoryTreeNodeId == 0) {
			return;
		}

		String assetCategoryTreeNodeType =
			configurationValueJSONObject.getString("categoryTreeNodeType");

		if (assetCategoryTreeNodeType.equals("Category")) {
			Map<Long, Long> assetVocabularyNewPrimaryKeys =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					AssetCategory.class.getName());

			configurationValueJSONObject.put(
				"categoryTreeNodeId",
				assetVocabularyNewPrimaryKeys.getOrDefault(
					assetCategoryTreeNodeId, 0L));
		}
		else if (assetCategoryTreeNodeType.equals("Vocabulary")) {
			Map<Long, Long> assetVocabularyNewPrimaryKeys =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					AssetVocabulary.class.getName());

			configurationValueJSONObject.put(
				"categoryTreeNodeId",
				assetVocabularyNewPrimaryKeys.getOrDefault(
					assetCategoryTreeNodeId, 0L));
		}
	}

	private AssetCategory _fetchAssetCategory(
		PortletDataContext portletDataContext,
		JSONObject configurationValueJSONObject) {

		if (configurationValueJSONObject.has("categoryTreeNodeId")) {
			return _assetCategoryLocalService.fetchCategory(
				configurationValueJSONObject.getLong("categoryTreeNodeId"));
		}
		else if (configurationValueJSONObject.has("externalReferenceCode")) {
			return _assetCategoryLocalService.
				fetchAssetCategoryByExternalReferenceCode(
					configurationValueJSONObject.getString(
						"externalReferenceCode"),
					getScopeGroupId(
						portletDataContext,
						configurationValueJSONObject.getString(
							"scopeExternalReferenceCode")));
		}

		return null;
	}

	private AssetVocabulary _fetchAssetVocabulary(
		PortletDataContext portletDataContext,
		JSONObject configurationValueJSONObject) {

		if (configurationValueJSONObject.has("categoryTreeNodeId")) {
			return _assetVocabularyLocalService.fetchAssetVocabulary(
				configurationValueJSONObject.getLong("categoryTreeNodeId"));
		}
		else if (configurationValueJSONObject.has("externalReferenceCode")) {
			return _assetVocabularyLocalService.
				fetchAssetVocabularyByExternalReferenceCode(
					configurationValueJSONObject.getString(
						"externalReferenceCode"),
					getScopeGroupId(
						portletDataContext,
						configurationValueJSONObject.getString(
							"scopeExternalReferenceCode")));
		}

		return null;
	}

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	@Reference
	private FragmentCollectionFilterRegistry _fragmentCollectionFilterRegistry;

	@Reference
	private FragmentEntryConfigurationParser _fragmentEntryConfigurationParser;

}
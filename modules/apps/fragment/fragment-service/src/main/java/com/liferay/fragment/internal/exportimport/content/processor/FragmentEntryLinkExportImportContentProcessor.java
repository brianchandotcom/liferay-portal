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
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.util.SegmentsExperiencePortletUtil;
import com.liferay.staging.StagingGroupHelper;
import com.liferay.staging.StagingGroupHelperUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(
	property = "model.class.name=com.liferay.fragment.model.FragmentEntryLink",
	service = {
		ExportImportContentProcessor.class,
		FragmentEntryLinkExportImportContentProcessor.class
	}
)
public class FragmentEntryLinkExportImportContentProcessor
	implements ExportImportContentProcessor<String> {

	@Override
	public String replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content, boolean exportReferencedContent,
			boolean escapeContent)
		throws Exception {

		content =
			_dlReferencesExportImportContentProcessor.
				replaceExportContentReferences(
					portletDataContext, stagedModel, content, true, false);

		if (!content.startsWith("{")) {
			return content;
		}

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			content);

		Iterator<String> keysIterator = editableValuesJSONObject.keys();

		while (keysIterator.hasNext()) {
			String key = keysIterator.next();

			JSONObject editableProcessorJSONObject =
				editableValuesJSONObject.getJSONObject(key);

			if (editableProcessorJSONObject == null) {
				continue;
			}

			if (Objects.equals(key, _KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR)) {
				Iterator<String> editableKeysIterator =
					editableProcessorJSONObject.keys();

				while (editableKeysIterator.hasNext()) {
					String editableKey = editableKeysIterator.next();

					JSONObject editableJSONObject =
						editableProcessorJSONObject.getJSONObject(editableKey);

					_replaceMappedFieldExportContentReferences(
						portletDataContext, stagedModel, editableJSONObject,
						exportReferencedContent);

					_replaceSegmentsExperienceExportContentReferences(
						portletDataContext, stagedModel, editableJSONObject);
				}
			}
			else if (Objects.equals(
						key, _KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR)) {

				_replaceSegmentsExperienceExportContentReferences(
					portletDataContext, stagedModel,
					editableProcessorJSONObject);
			}
		}

		if (stagedModel instanceof FragmentEntryLink) {
			FragmentEntryLink fragmentEntryLink =
				(FragmentEntryLink)stagedModel;

			if (fragmentEntryLink.getClassNameId() == _portal.getClassNameId(
					Layout.class)) {

				_exportPortletPreferencesSegmentsExperience(
					portletDataContext, fragmentEntryLink);
			}
		}

		return editableValuesJSONObject.toString();
	}

	@Override
	public String replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			String content)
		throws Exception {

		content =
			_dlReferencesExportImportContentProcessor.
				replaceImportContentReferences(
					portletDataContext, stagedModel, content);

		if (!content.startsWith("{")) {
			return content;
		}

		JSONObject editableValuesJSONObject = JSONFactoryUtil.createJSONObject(
			content);

		Iterator<String> keysIterator = editableValuesJSONObject.keys();

		while (keysIterator.hasNext()) {
			String key = keysIterator.next();

			JSONObject editableProcessorJSONObject =
				editableValuesJSONObject.getJSONObject(key);

			if (editableProcessorJSONObject == null) {
				continue;
			}

			if (Objects.equals(key, _KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR)) {
				Iterator<String> editableKeysIterator =
					editableProcessorJSONObject.keys();

				while (editableKeysIterator.hasNext()) {
					String editableKey = editableKeysIterator.next();

					JSONObject editableJSONObject =
						editableProcessorJSONObject.getJSONObject(editableKey);

					_replaceMappedFieldImportContentReferences(
						portletDataContext, editableJSONObject);

					_replaceSegmentsExperienceImportContentReferences(
						portletDataContext, editableJSONObject);
				}
			}
			else {
				if (Objects.equals(
						key, _KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR)) {

					_replaceSegmentsExperienceImportContentReferences(
						portletDataContext, editableProcessorJSONObject);
				}
			}
		}

		if (stagedModel instanceof FragmentEntryLink) {
			FragmentEntryLink fragmentEntryLink =
				(FragmentEntryLink)stagedModel;

			if (fragmentEntryLink.getClassNameId() == _portal.getClassNameId(
					Layout.class)) {

				_importPortletPreferencesSegmentsExperience(
					portletDataContext, fragmentEntryLink.getClassPK());
			}
		}

		return editableValuesJSONObject.toString();
	}

	@Override
	public void validateContentReferences(long groupId, String content)
		throws PortalException {
	}

	private void _exportPortletPreferencesSegmentsExperience(
			PortletDataContext portletDataContext,
			FragmentEntryLink fragmentEntryLink)
		throws PortalException {

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
				fragmentEntryLink.getClassPK());

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			long segmentsExperienceId =
				SegmentsExperiencePortletUtil.getSegmentsExperienceId(
					portletPreferences.getPortletId());

			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					segmentsExperienceId);

			if (segmentsExperience == null) {
				continue;
			}

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fragmentEntryLink, segmentsExperience,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	private void _importPortletPreferencesSegmentsExperience(
			PortletDataContext portletDataContext, long plid)
		throws PortalException {

		Map<Long, Long> plids =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Layout.class);

		Map<Long, Long> segmentsExperienceIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				SegmentsExperience.class);

		long importedPlid = MapUtil.getLong(plids, plid, plid);

		List<PortletPreferences> portletPreferencesList =
			_portletPreferencesLocalService.getPortletPreferences(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, importedPlid);

		for (PortletPreferences portletPreferences : portletPreferencesList) {
			String portletId = portletPreferences.getPortletId();

			long segmentsExperienceId =
				SegmentsExperiencePortletUtil.getSegmentsExperienceId(
					portletId);

			if (segmentsExperienceId > 0) {
				long importedSegmentsExperienceId = MapUtil.getLong(
					segmentsExperienceIds, segmentsExperienceId,
					segmentsExperienceId);

				if (importedSegmentsExperienceId != segmentsExperienceId) {
					portletPreferences.setPortletId(
						SegmentsExperiencePortletUtil.setSegmentsExperienceId(
							portletId, importedSegmentsExperienceId));

					_portletPreferencesLocalService.deletePortletPreferences(
						portletPreferences.getPortletPreferencesId());

					_portletPreferencesLocalService.updatePreferences(
						PortletKeys.PREFS_OWNER_ID_DEFAULT,
						PortletKeys.PREFS_OWNER_TYPE_LAYOUT, importedPlid,
						portletPreferences.getPortletId(),
						portletPreferences.getPreferences());
				}
			}
		}
	}

	private void _replaceMappedFieldExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableJSONObject, boolean exportReferencedContent)
		throws Exception {

		long classNameId = editableJSONObject.getLong("classNameId");
		long classPK = editableJSONObject.getLong("classPK");

		if ((classNameId == 0) || (classPK == 0)) {
			return;
		}

		String mappedField = editableJSONObject.getString(
			"mappedField", editableJSONObject.getString("fieldId"));

		if (mappedField.startsWith(_DDM_TEMPLATE)) {
			String ddmTemplateKey = mappedField.substring(
				_DDM_TEMPLATE.length());

			DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
				portletDataContext.getScopeGroupId(),
				_portal.getClassNameId(DDMStructure.class), ddmTemplateKey);

			if (ddmTemplate != null) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, stagedModel, ddmTemplate,
					PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
			}
		}

		AssetEntry assetEntry = _assetEntryLocalService.getEntry(
			_portal.getClassName(classNameId), classPK);

		AssetRenderer assetRenderer = assetEntry.getAssetRenderer();

		if (assetRenderer == null) {
			return;
		}

		AssetRendererFactory assetRendererFactory =
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
			catch (Exception e) {
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
					messageSB.append(e);

					String errorMessage = messageSB.toString();

					if (Validator.isNotNull(e.getMessage())) {
						errorMessage = StringBundler.concat(
							errorMessage, ": ", e.getMessage());
					}

					_log.debug(errorMessage, e);
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

	private void _replaceMappedFieldImportContentReferences(
		PortletDataContext portletDataContext, JSONObject editableJSONObject) {

		String className = GetterUtil.getString(
			editableJSONObject.remove("className"));

		if (Validator.isNull(className)) {
			return;
		}

		String mappedField = editableJSONObject.getString(
			"mappedField", editableJSONObject.getString("fieldId"));

		if (mappedField.startsWith(_DDM_TEMPLATE)) {
			String ddmTemplateKey = mappedField.substring(
				_DDM_TEMPLATE.length());

			Map<String, String> ddmTemplateKeys =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					DDMTemplate.class + ".ddmTemplateKey");

			String importedDDMTemplateKey = MapUtil.getString(
				ddmTemplateKeys, ddmTemplateKey, ddmTemplateKey);

			if (editableJSONObject.has("mappedField")) {
				editableJSONObject.put(
					"mappedField", _DDM_TEMPLATE + importedDDMTemplateKey);
			}
			else {
				editableJSONObject.put(
					"fieldId", _DDM_TEMPLATE + importedDDMTemplateKey);
			}
		}

		AssetRendererFactory assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

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

	private void _replaceSegmentsExperienceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableJSONObject)
		throws Exception {

		Iterator<String> editableKeysIterator = editableJSONObject.keys();

		while (editableKeysIterator.hasNext()) {
			String editableKey = editableKeysIterator.next();

			if (!editableKey.startsWith(
					SegmentsExperienceConstants.ID_PREFIX)) {

				continue;
			}

			long segmentsExperienceId = GetterUtil.getLong(
				editableKey.substring(
					SegmentsExperienceConstants.ID_PREFIX.length()));

			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					segmentsExperienceId);

			if (segmentsExperience == null) {
				continue;
			}

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, stagedModel, segmentsExperience,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	private void _replaceSegmentsExperienceImportContentReferences(
		PortletDataContext portletDataContext, JSONObject editableJSONObject) {

		Iterator<String> editableKeysIterator = editableJSONObject.keys();

		Set<String> editableKeys = new HashSet<>();

		editableKeysIterator.forEachRemaining(editableKeys::add);

		for (String editableKey : editableKeys) {
			if (!editableKey.startsWith(
					SegmentsExperienceConstants.ID_PREFIX)) {

				continue;
			}

			long segmentsExperienceId = GetterUtil.getLong(
				editableKey.substring(
					SegmentsExperienceConstants.ID_PREFIX.length()));

			Map<Long, Long> segmentsExperienceIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					SegmentsExperience.class);

			long importedSegmentsExperienceId = MapUtil.getLong(
				segmentsExperienceIds, segmentsExperienceId,
				segmentsExperienceId);

			JSONObject segmentsExperienceJSONObject =
				editableJSONObject.getJSONObject(editableKey);

			editableJSONObject.remove(editableKey);

			editableJSONObject.put(
				SegmentsExperienceConstants.ID_PREFIX +
					importedSegmentsExperienceId,
				segmentsExperienceJSONObject);
		}
	}

	private static final String _DDM_TEMPLATE = "ddmTemplate_";

	private static final String _KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.editable." +
			"EditableFragmentEntryProcessor";

	private static final String _KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR =
		"com.liferay.fragment.entry.processor.freemarker." +
			"FreeMarkerFragmentEntryProcessor";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkExportImportContentProcessor.class);

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference(target = "(content.processor.type=DLReferences)")
	private ExportImportContentProcessor<String>
		_dlReferencesExportImportContentProcessor;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}
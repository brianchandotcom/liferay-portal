/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.content.processor;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.exportimport.content.processor.ExportImportContentProcessor;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.service.TemplateEntryLocalService;

import java.util.Iterator;
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
public class EditableValuesMappingExportImportContentProcessor
	implements ExportImportContentProcessor<JSONObject> {

	@Override
	public JSONObject replaceExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableValuesJSONObject,
			boolean exportReferencedContent, boolean escapeContent)
		throws Exception {

		_replaceAllEditableExportContentReferences(
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR),
			exportReferencedContent, portletDataContext, stagedModel);

		_replaceAllEditableExportContentReferences(
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR),
			exportReferencedContent, portletDataContext, stagedModel);

		return editableValuesJSONObject;
	}

	@Override
	public JSONObject replaceImportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableValuesJSONObject)
		throws Exception {

		_replaceAllEditableImportContentReferences(
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_BACKGROUND_IMAGE_FRAGMENT_ENTRY_PROCESSOR),
			portletDataContext);

		_replaceAllEditableImportContentReferences(
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR),
			portletDataContext);

		return editableValuesJSONObject;
	}

	@Override
	public void validateContentReferences(long groupId, JSONObject jsonObject) {
	}

	private void _exportDDMTemplateReference(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableJSONObject)
		throws Exception {

		String mappedField = editableJSONObject.getString(
			"mappedField", editableJSONObject.getString("fieldId"));

		if (!mappedField.startsWith(
				PortletDisplayTemplate.DISPLAY_STYLE_PREFIX)) {

			return;
		}

		String ddmTemplateKey = mappedField.substring(
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX.length());

		DDMTemplate ddmTemplate = _ddmTemplateLocalService.fetchTemplate(
			portletDataContext.getScopeGroupId(),
			_portal.getClassNameId(DDMStructure.class), ddmTemplateKey);

		if (ddmTemplate != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, stagedModel, ddmTemplate,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	private void _exportTemplateReference(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableJSONObject)
		throws Exception {

		String mappedField = editableJSONObject.getString(
			"collectionFieldId",
			editableJSONObject.getString(
				"mappedField", editableJSONObject.getString("fieldId")));

		if (!mappedField.startsWith(_TEMPLATE)) {
			return;
		}

		String templateEntryId = mappedField.substring(_TEMPLATE.length());

		TemplateEntry templateEntry =
			_templateEntryLocalService.fetchTemplateEntry(
				GetterUtil.getLong(templateEntryId));

		if (templateEntry != null) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, stagedModel, templateEntry,
				PortletDataContext.REFERENCE_TYPE_DEPENDENCY);
		}
	}

	private void _replaceAllEditableExportContentReferences(
			JSONObject editableValuesJSONObject,
			boolean exportReferencedContent,
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws Exception {

		if ((editableValuesJSONObject == null) ||
			(editableValuesJSONObject.length() <= 0)) {

			return;
		}

		_replaceMappedFieldExportContentReferences(
			portletDataContext, stagedModel, editableValuesJSONObject,
			exportReferencedContent);

		Iterator<String> editableKeysIterator = editableValuesJSONObject.keys();

		while (editableKeysIterator.hasNext()) {
			String editableKey = editableKeysIterator.next();

			JSONObject editableJSONObject =
				editableValuesJSONObject.getJSONObject(editableKey);

			_replaceAllEditableExportContentReferences(
				editableJSONObject, exportReferencedContent, portletDataContext,
				stagedModel);
		}
	}

	private void _replaceAllEditableImportContentReferences(
		JSONObject editableValuesJSONObject,
		PortletDataContext portletDataContext) {

		if ((editableValuesJSONObject == null) ||
			(editableValuesJSONObject.length() <= 0)) {

			return;
		}

		_replaceMappedFieldImportContentReferences(
			portletDataContext, editableValuesJSONObject);

		Iterator<String> editableKeysIterator = editableValuesJSONObject.keys();

		while (editableKeysIterator.hasNext()) {
			String editableKey = editableKeysIterator.next();

			JSONObject editableJSONObject =
				editableValuesJSONObject.getJSONObject(editableKey);

			_replaceAllEditableImportContentReferences(
				editableJSONObject, portletDataContext);
		}
	}

	private void _replaceMappedFieldExportContentReferences(
			PortletDataContext portletDataContext, StagedModel stagedModel,
			JSONObject editableJSONObject, boolean exportReferencedContent)
		throws Exception {

		long classNameId = editableJSONObject.getLong("classNameId");
		long classPK = editableJSONObject.getLong("classPK");
		String collectionFieldId = editableJSONObject.getString(
			"collectionFieldId");
		String mappedField = editableJSONObject.getString("mappedField");

		if (((classNameId == 0) || (classPK == 0)) &&
			Validator.isNull(collectionFieldId) &&
			Validator.isNull(mappedField)) {

			return;
		}

		_exportDDMTemplateReference(
			portletDataContext, stagedModel, editableJSONObject);
		_exportTemplateReference(
			portletDataContext, stagedModel, editableJSONObject);

		if ((classNameId == 0) || (classPK == 0)) {
			return;
		}

		String className = _portal.getClassName(classNameId);

		editableJSONObject.put("className", className);

		ExportImportContentProcessorUtil.exportContentReference(
			className, classPK, exportReferencedContent,
			_infoItemServiceRegistry, portletDataContext, stagedModel);
	}

	private void _replaceMappedFieldImportContentReferences(
		PortletDataContext portletDataContext, JSONObject editableJSONObject) {

		String mappedField = editableJSONObject.getString(
			"collectionFieldId",
			editableJSONObject.getString(
				"mappedField", editableJSONObject.getString("fieldId")));

		if (mappedField.startsWith(_TEMPLATE)) {
			long templateEntryId = GetterUtil.getLong(
				mappedField.substring(_TEMPLATE.length()));

			Map<Long, Long> templateEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					TemplateEntry.class);

			long importedTemplateEntryId = MapUtil.getLong(
				templateEntryIds, templateEntryId, templateEntryId);

			if (editableJSONObject.has("collectionFieldId")) {
				editableJSONObject.put(
					"collectionFieldId", _TEMPLATE + importedTemplateEntryId);
			}
			else if (editableJSONObject.has("mappedField")) {
				editableJSONObject.put(
					"mappedField", _TEMPLATE + importedTemplateEntryId);
			}
			else {
				editableJSONObject.put(
					"fieldId", _TEMPLATE + importedTemplateEntryId);
			}
		}
		else if (mappedField.startsWith(
					PortletDisplayTemplate.DISPLAY_STYLE_PREFIX)) {

			String ddmTemplateKey = mappedField.substring(
				PortletDisplayTemplate.DISPLAY_STYLE_PREFIX.length());

			Map<String, String> ddmTemplateKeys =
				(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
					DDMTemplate.class + ".ddmTemplateKey");

			String importedDDMTemplateKey = MapUtil.getString(
				ddmTemplateKeys, ddmTemplateKey, ddmTemplateKey);

			if (editableJSONObject.has("mappedField")) {
				editableJSONObject.put(
					"mappedField",
					PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
						importedDDMTemplateKey);
			}
			else {
				editableJSONObject.put(
					"fieldId",
					PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
						importedDDMTemplateKey);
			}
		}

		ExportImportContentProcessorUtil.replaceImportContentReferences(
			editableJSONObject, portletDataContext);

		if (editableJSONObject.has("fileEntryId")) {
			editableJSONObject.put(
				"fileEntryId", editableJSONObject.getLong("classPK"));
		}
	}

	private static final String _TEMPLATE =
		PortletDisplayTemplate.DISPLAY_STYLE_PREFIX + StringPool.UNDERLINE +
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

	@Reference
	private Portal _portal;

	@Reference
	private TemplateEntryLocalService _templateEntryLocalService;

}